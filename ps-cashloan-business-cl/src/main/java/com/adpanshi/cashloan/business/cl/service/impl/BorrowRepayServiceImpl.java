package com.adpanshi.cashloan.business.cl.service.impl;

import com.adpanshi.cashloan.business.cl.domain.*;
import com.adpanshi.cashloan.business.cl.mapper.*;
import com.adpanshi.cashloan.business.cl.model.*;
import com.adpanshi.cashloan.business.cl.model.pay.lianlian.AuthApplyModel;
import com.adpanshi.cashloan.business.cl.model.pay.lianlian.RepaymentPlan;
import com.adpanshi.cashloan.business.cl.model.pay.lianlian.SmsParams;
import com.adpanshi.cashloan.business.cl.model.pay.lianlian.util.LianLianHelper;
import com.adpanshi.cashloan.business.cl.service.*;
import com.adpanshi.cashloan.business.core.common.context.Global;
import com.adpanshi.cashloan.business.core.common.enums.ManageExceptionEnum;
import com.adpanshi.cashloan.business.core.common.exception.BussinessException;
import com.adpanshi.cashloan.business.core.common.exception.ManageException;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.service.impl.BaseServiceImpl;
import com.adpanshi.cashloan.business.core.common.util.DateUtil;
import com.adpanshi.cashloan.business.core.common.util.OrderNoUtil;
import com.adpanshi.cashloan.business.core.domain.Borrow;
import com.adpanshi.cashloan.business.core.domain.BorrowMain;
import com.adpanshi.cashloan.business.core.domain.User;
import com.adpanshi.cashloan.business.core.mapper.BorrowMainMapper;
import com.adpanshi.cashloan.business.core.mapper.UserMapper;
import com.adpanshi.cashloan.business.core.model.BorrowModel;
import com.adpanshi.cashloan.business.cr.domain.Credit;
import com.adpanshi.cashloan.business.cr.mapper.CreditMapper;
import com.adpanshi.cashloan.business.rule.domain.BankCard;
import com.adpanshi.cashloan.business.rule.mapper.BankCardMapper;
import com.adpanshi.cashloan.business.rule.mapper.ClBorrowMapper;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tool.util.BigDecimalUtil;
import tool.util.NumberUtil;
import tool.util.StringUtil;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 还款计划ServiceImpl
 *
 * @author
 * @version 1.0.0
 * @date 2017-02-14 13:42:32
 * Copyright 粉团网路 arc All Rights Reserved
 */

@Service("borrowRepayService")
public class BorrowRepayServiceImpl extends BaseServiceImpl<BorrowRepay, Long> implements BorrowRepayService {

    private static final Logger logger = LoggerFactory.getLogger(BorrowRepayServiceImpl.class);

    @Resource
    private BorrowRepayMapper borrowRepayMapper;
    @Resource
    private BorrowRepayLogMapper borrowRepayLogMapper;
    @Resource
    private ClBorrowMapper clBorrowMapper;
    @Resource
    private BorrowProgressMapper borrowProgressMapper;
    @Resource
    private CreditMapper creditMapper;
    @Resource
    private UrgeRepayOrderService urgeRepayOrderService;
    @Resource
    private UrgeRepayOrderLogService urgeRepayOrderLogService;
    @Resource
    private ProfitLogService profitLogService;
    @Resource
    private UserInviteMapper userInviteMapper;
    @Resource
    private ProfitLogMapper profitLogMapper;
    @Resource
    private BankCardMapper bankCardMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private CreditsUpgradeService creditsUpgradeService;
    @Resource
    private BorrowMainMapper borrowMainMapper;
    @Resource
    private BorrowMainProgressService borrowMainProgressService;
    @Resource
    private PayLogMapper payLogMapper;
    @Resource
    private ClSmsService clSmsService;

    @Override
    public BaseMapper<BorrowRepay, Long> getMapper() {
        return borrowRepayMapper;
    }

    @Override
    public int save(BorrowRepay borrowRepay) {
        return borrowRepayMapper.save(borrowRepay);
    }

    @Override
    public void genRepayPlans(List<Borrow> borrowList, BorrowMain borrowMain, Boolean isMockData) {
        if (CollectionUtils.isEmpty(borrowList)) {
            return;
        }
        List<BorrowRepay> repayList = new ArrayList<>(borrowList.size());
        for (Borrow borrow : borrowList) {
            BorrowRepay br = new BorrowRepay();
            Double amount = BigDecimalUtil.add(borrow.getRealAmount(), borrow.getFee());
            br.setAmount(amount);
            br.setBorrowId(borrow.getId());
            br.setUserId(borrow.getUserId());
            String repay = DateUtil.dateStr2(DateUtil.rollDay(borrow.getCreateTime(), Integer.parseInt(borrow
                    .getTimeLimit()) - 1));
            br.setRepayTime(DateUtil.valueOf(repay + " 23:59:59", "yyyy-MM-dd HH:mm:ss"));
            br.setState(BorrowRepayModel.STATE_REPAY_NO);
            br.setPenaltyAmout(0.0);
            br.setPenaltyDay("0");
            repayList.add(br);
        }
        borrowRepayMapper.saveAll(repayList);
        if (!"dev".equals(Global.getValue("app_environment")) && !isMockData) {
            authApply(repayList, borrowMain);
        }
    }


    @Override
    public void authSignApply(Long userId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("userId", userId);
        paramMap.put("state", BorrowRepayModel.STATE_REPAY_NO);
        List<BorrowRepay> borrowRepayList = borrowRepayMapper.findUnRepay(paramMap);

        // 若未还款列表不为null 并列表数目大于0 则进行授权
        if (null != borrowRepayList && !borrowRepayList.isEmpty()) {
            for (BorrowRepay borrowRepay : borrowRepayList) {
                authApply(borrowRepay);
            }
        }
    }

    private void authApply(final List<BorrowRepay> borrowRepayList, final BorrowMain borrowMain) {
        // 查询用户信息及银行卡信息 用于授权
        new Thread() {
            @Override
            public void run() {
                BankCard bankCard = bankCardMapper.findByPrimary(borrowMain.getCardId());

                String orderNo = OrderNoUtil.genPayOrderNo();
                AuthApplyModel authApply = new AuthApplyModel(orderNo);

                User user = userMapper.findByPrimary(borrowMain.getUserId());
                authApply.setUser_id(user.getUuid());
                Map<String, Object> repaymentPlanMap = new HashMap<String, Object>();
                List<RepaymentPlan> list = new ArrayList<RepaymentPlan>();
                for (BorrowRepay borrowRepay : borrowRepayList) {
                    RepaymentPlan plan = new RepaymentPlan();
                    if ("dev".equals(Global.getValue("app_environment"))) {
                        plan.setDate(DateUtil.dateStr2(new Date()));
                    } else {
                        plan.setDate(DateUtil.dateStr2(borrowRepay.getRepayTime()));
                    }
                    plan.setAmount(StringUtil.isNull(borrowRepay.getAmount()));
                    list.add(plan);
                }
                repaymentPlanMap.put("repaymentPlan", list);
                authApply.setRepayment_plan(JSONObject.toJSONString(repaymentPlanMap));
                authApply.setRepayment_no(borrowMain.getOrderNo());
                SmsParams smsParams = new SmsParams();
                smsParams.setContract_type(Global.getValue("title"));
                smsParams.setContact_way(Global.getValue("customer_hotline"));
                authApply.setSms_param(JSONObject.toJSONString(smsParams));
                authApply.setNo_agree(bankCard.getAgreeNo());
                LianLianHelper helper = new LianLianHelper();
                authApply = (AuthApplyModel) helper.authApply(authApply);
                if (authApply.checkReturn()) {
                    logger.info("BorrowMain授权成功.orderNo:{}", borrowMain.getOrderNo());
                } else {
                    logger.info("BorrowMain授权失败.orderNo:{},原因：{}", borrowMain.getOrderNo(), authApply
                            .getRet_msg());
                }

            }
        }.start();

    }

    /**
     * 调用连连支付 分期付 授权接口 为用户授权
     *
     * @param borrowRepay
     */
    private void authApply(final BorrowRepay borrowRepay) {
        // 查询用户信息及银行卡信息 用于授权
        new Thread() {
            @Override
            public void run() {
                Map<String, Object> paramMap = new HashMap<String, Object>();
                paramMap.put("userId", borrowRepay.getUserId());
                BankCard bankCard = bankCardMapper.findSelective(paramMap);

                User user = userMapper.findByPrimary(borrowRepay.getUserId()); // 用户UUID
                Borrow borrow = clBorrowMapper.findByPrimary(borrowRepay.getBorrowId()); // 借款标识 OrderNo 作为还款编号

                String orderNo = OrderNoUtil.genPayOrderNo();
                AuthApplyModel authApply = new AuthApplyModel(orderNo);
                authApply.setUser_id(user.getUuid());
                Map<String, Object> repaymentPlanMap = new HashMap<String, Object>();
                List<RepaymentPlan> list = new ArrayList<RepaymentPlan>();
                RepaymentPlan plan = new RepaymentPlan();
                if ("dev".equals(Global.getValue("app_environment"))) {
                    plan.setDate(DateUtil.dateStr2(new Date()));
                } else {
                    plan.setDate(DateUtil.dateStr2(borrowRepay.getRepayTime()));
                }
                plan.setAmount(StringUtil.isNull(borrowRepay.getAmount()));
                list.add(plan);
                repaymentPlanMap.put("repaymentPlan", list);
                authApply.setRepayment_plan(JSONObject.toJSONString(repaymentPlanMap));
                authApply.setRepayment_no(borrow.getOrderNo());
                SmsParams smsParams = new SmsParams();
                smsParams.setContract_type(Global.getValue("title"));
                smsParams.setContact_way(Global.getValue("customer_hotline"));
                authApply.setSms_param(JSONObject.toJSONString(smsParams));
                authApply.setNo_agree(bankCard.getAgreeNo());
                LianLianHelper helper = new LianLianHelper();
                authApply = (AuthApplyModel) helper.authApply(authApply);
                if (authApply.checkReturn()) {
                    logger.info("Borrow", borrow.getOrderNo(), "授权成功");
                } else {
                    logger.info("Borrow" + borrow.getOrderNo() + "授权失败,原因：" + authApply.getRet_msg());
                }
            }
        }.start();
    }


    @Override
    public Page<BorrowRepayModel> listModel(Map<String, Object> params, int currentPage, int pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        return (Page<BorrowRepayModel>) borrowRepayMapper.listModelNew(params);
    }

    @Override
    public void confirmRepayNew(Map<String, Object> param) throws Exception {
        logger.debug("确认还款...");
        Long id = (Long) param.get("id");
        BorrowRepay br = borrowRepayMapper.findByPrimary(id);
        String state = (String) param.get("state");
        Date repayTime = (Date) param.get("repayTime");
        SimpleDateFormat time1 = new SimpleDateFormat("yyyy-MM-dd");
        Date repayPlanTime1 = DateUtil.valueOf(time1.format(br.getRepayTime()));//计划还款日期
        Date repay_time1 = DateUtil.valueOf(time1.format(repayTime));//还款时间
        Date today_time = DateUtil.valueOf(time1.format(new Date()));//今日日期
        Double amount = (Double) (param.get("amount") != null ? param.get("amount") : 0.0);
        Double penaltyAmoutIn = (Double) (param.get("penaltyAmout") != null ? param.get("penaltyAmout") : 0.0);
        if (BorrowRepayModel.NORMAL_REPAYMENT.equals(state)) {
            //正常还款
            //还款时间已逾期
            if (repay_time1.after(repayPlanTime1)) {
                throw new ManageException(ManageExceptionEnum.CLIENT_EXCEPTION_CODE_VALUE);
            }
            //还款金额不匹配
            if (!Objects.equals(amount, br.getAmount())) {
                throw new ManageException(ManageExceptionEnum.MANAGE_REPAY_REPAY_AMOUNT_ERROR_CODE_VALUE);
            }
            state = BorrowModel.STATE_FINISH;
            param.put("penaltyDay", String.valueOf(0));
            param.put("penaltyAmout", 0.00);
        } else if (BorrowRepayModel.OVERDUE_REPAYMENT.equals(state)) {
            //还款时间还未逾期
            if (!repay_time1.after(repayPlanTime1)) {
                throw new ManageException(ManageExceptionEnum.MANAGE_REPAY_NOT_OVERDUE_CODE_VALUE);
            }
            //还款金额不匹配
            if (!Objects.equals(amount, br.getAmount())) {
                throw new ManageException(ManageExceptionEnum.MANAGE_REPAY_REPAY_AMOUNT_ERROR_CODE_VALUE);
            }
            state = BorrowModel.STATE_FINISH;
            //逾期还款
            if (repay_time1.equals(today_time)) {
                //逾期利息不匹配应还利息
                if (!Objects.equals(penaltyAmoutIn, br.getPenaltyAmout())) {
                    throw new ManageException(ManageExceptionEnum.MANAGE_REPAY_INTREST_ERROR_CODE_VALUE);
                }
                param.put("penaltyAmout", br.getPenaltyAmout());
                param.put("penaltyDay", br.getPenaltyDay());
            } else {
                //非当天的做逾息计算
                double penaltyAmout = br.getPenaltyAmout();
                double penaltyDay = Double.parseDouble(br.getPenaltyDay());
                double simplyAmout = penaltyAmout / penaltyDay;
                long day = (repay_time1.getTime() - repayPlanTime1.getTime()) / (24 * 60 * 60 * 1000);
                double sum = day * simplyAmout;
                //逾期利息不匹配应还利息
                if (!Objects.equals(penaltyAmoutIn, sum)) {
                    throw new ManageException(ManageExceptionEnum.MANAGE_REPAY_INTREST_ERROR_CODE_VALUE);
                }
                param.put("penaltyAmout", sum);
                param.put("penaltyDay", String.valueOf(day));
            }
        } else if (BorrowRepayModel.OVERDUE_RELIEF.equals(state)) {
            //逾期减免
            state = BorrowModel.STATE_REMISSION_FINISH;
            //还款时间还未逾期
            if (!repay_time1.after(repayPlanTime1)) {
                throw new ManageException(ManageExceptionEnum.MANAGE_REPAY_NOT_OVERDUE_CODE_VALUE);
            }
            //还款金额不能大于应还金额
            if (br.getAmount() < amount) {
                throw new ManageException(ManageExceptionEnum.MANAGE_REPAY_MORE_REPAY_AMOUNT_CODE_VALUE);
            }
            //减免金额
            Double derateAmount = (Double) (param.get("derateAmount") != null ? param.get("derateAmount") : 0.0);
            if (repay_time1.equals(today_time)) {
                //当天还款取还款计划表中数据
                Double difAmount = penaltyAmoutIn + derateAmount + amount - br.getPenaltyAmout() - br.getAmount();
                //实际还款+减免金额与应还金额不匹配
                if (difAmount != 0.0) {
                    throw new ManageException(ManageExceptionEnum.MANAGE_REPAY_REMISSION_AMOUNT_ERROR_CODE_VALUE);
                }
                param.put("penaltyDay", br.getPenaltyDay());
                param.put("overduePenaltyAmout", br.getPenaltyAmout());
            } else {
                //非当天更新逾期日期
                double penaltyAmout = br.getPenaltyAmout();
                double penaltyDay = Double.parseDouble(br.getPenaltyDay());
                double simplyAmout = penaltyAmout / penaltyDay;
                long day = (repay_time1.getTime() - repayPlanTime1.getTime()) / (24 * 60 * 60 * 1000);
                Double difAmount = penaltyAmoutIn + derateAmount + amount - simplyAmout * day - br.getAmount();
                //实际还款+减免金额与应还金额不匹配
                if (difAmount != 0.0) {
                    throw new ManageException(ManageExceptionEnum.MANAGE_REPAY_REMISSION_AMOUNT_ERROR_CODE_VALUE);
                }
                param.put("penaltyDay", String.valueOf(day));
                param.put("overduePenaltyAmout", simplyAmout * day);
            }
        }

        /*更新还款信息*/
        if (updateBorrowReplayNew(br, repayTime, param, state) < 0) {
            throw new ManageException(ManageExceptionEnum.MANAGE_REPAY_UPDATE_REPAY_ERROR_CODE_VALUE);
        }
        /*更新借款表和借款进度状态*/
        if (updateBorrow(br.getBorrowId(), br.getUserId(), state) < 0) {
            throw new ManageException(ManageExceptionEnum.MANAGE_REPAY_UPDATE_PROGRESS_ERROR_CODE_VALUE);
        }
        handleAllFinishByBorrowId(br.getBorrowId(), br.getUserId());
        Borrow borrow = clBorrowMapper.findByPrimary(br.getBorrowId());
        /*信用额度修改*/
        Map<String, Object> creditMap = new HashMap<>();
        creditMap.put("consumerNo", br.getUserId() + "");
        Credit credit = creditMapper.findSelective(creditMap);
        //用户信用额度信息不存在
        if (null == credit) {
            throw new ManageException(ManageExceptionEnum.MANAGE_REPAY_NO_CREDIT_CODE_VALUE);
        }
        credit.setUnuse(credit.getUnuse() + borrow.getAmount());
        credit.setUsed(credit.getUsed() > borrow.getAmount() ? credit.getUsed() - borrow.getAmount() : 0);
        creditMapper.update(credit);
        // 更新催收订单中的状态
        Map<String, Object> orderMap = new HashMap<>();
        orderMap.put("borrowId", br.getBorrowId());
        UrgeRepayOrder order = urgeRepayOrderService.findOrderByMap(orderMap);
        if (null != order) {
            SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd");
            Date repayPlanTime = DateUtil.valueOf(time.format(br.getRepayTime()));
            Date repay_time = DateUtil.valueOf(time.format(repayTime));
            // 实际还款时间在应还款时间之前或当天（不对比时分秒），删除催收记录
            if (!repay_time.after(repayPlanTime)) {
                urgeRepayOrderLogService.deleteByOrderId(order.getId());
                urgeRepayOrderService.deleteByBorrowId(br.getBorrowId());
            } else {
                logger.debug("更新存在的催收订单中的状态");
                UrgeRepayOrderLog orderLog = new UrgeRepayOrderLog();
                orderLog.setRemark("用户还款成功");
                orderLog.setWay("10");
                orderLog.setCreateTime(DateUtil.getNow());
                orderLog.setState(UrgeRepayOrderModel.STATE_ORDER_SUCCESS);
                urgeRepayOrderLogService.saveOrderInfo(orderLog, order);
            }
        }
        //发送短信还款短信
        clSmsService.activePayment(br.getUserId(),borrow.getBorrowMainId(),repayTime,amount,borrow.getOrderNo());
        // 判断是否有邀请人,若有邀请人则更新借款人的代理商资金奖励
        Map<String, Object> inviteMap = new HashMap<>();
        inviteMap.put("inviteId", br.getUserId());
        UserInvite invite = userInviteMapper.findSelective(inviteMap);
        if (StringUtil.isNotBlank(invite)) {
            // 判断是否已分配奖金
            Map<String, Object> profitMap = new HashMap<>();
            profitMap.put("borrowId", br.getBorrowId());
            int count = profitLogMapper.count(profitMap);
            if (count == 0) {
                profitLogService.save(br.getBorrowId(), DateUtil.getNow());
            }
        }
    }


    /**
     * 更新借款表和借款进度状态
     *
     * @param borrowId
     * @param userId
     * @param state
     * @return
     */
    public int updateBorrow(long borrowId, long userId, String state) {
        int i = 0;
        // 更新借款状态
        Map<String, Object> stateMap = new HashMap<String, Object>();
        stateMap.put("id", borrowId);
        stateMap.put("state", state);
        i = clBorrowMapper.updateSelective(stateMap);
        if (i > 0) {
            // 添加借款进度
            BorrowProgress bp = new BorrowProgress();
            bp.setBorrowId(borrowId);
            bp.setUserId(userId);
            bp.setRemark(BorrowModel.convertBorrowRemark(state));
            bp.setState(state);
            bp.setCreateTime(DateUtil.getNow());
            return borrowProgressMapper.save(bp);
        }
        return i;
    }

    /**
     * 更新还款计划和还款记录表(确认还款用)
     *
     * @param br
     * @param repayTime
     * @param param
     * @return
     */
    public int updateBorrowReplayNew(BorrowRepay br, Date repayTime,
                                     Map<String, Object> param, String state) {
        // 更新还款计划状态
        int i = 0;
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", br.getId());
        paramMap.put("state", BorrowRepayModel.STATE_REPAY_YES);
        if (BorrowModel.STATE_REMISSION_FINISH.equals(state)) {
            paramMap.put("penaltyAmout", param.get("overduePenaltyAmout"));
        } else {
            paramMap.put("penaltyAmout", param.get("penaltyAmout"));
        }
        paramMap.put("penaltyDay", param.get("penaltyDay"));


        i = borrowRepayMapper.updateParam(paramMap);
        if (i > 0) {
            // 生成还款记录
            BorrowRepayLog log = new BorrowRepayLog();
            log.setBorrowId(br.getBorrowId());
            log.setRepayId(br.getId());
            log.setUserId(br.getUserId());
            log.setAmount((Double) param.get("amount"));// 实际还款金额
            log.setRepayTime(repayTime);// 实际还款时间
            log.setPenaltyDay((String) param.get("penaltyDay"));
            log.setPenaltyAmout((Double) param.get("penaltyAmout"));
            //计算提前还款手续费
            double fee=getFee(br.getBorrowId(),br.getUserId(), br.getRepayTime(), repayTime);
            log.setFee(fee);
            log.setSerialNumber((String) param.get("serialNumber"));
            log.setRepayAccount((String) param.get("repayAccount"));
            log.setRepayWay((String) param.get("repayWay"));
            log.setCreateTime(DateUtil.getNow());
            log.setConfirmId((Long) param.get("confirmId"));
            log.setConfirmTime((Date) param.get("confirmTime"));
            return borrowRepayLogMapper.save(log);
        }
        return i;
    }

    
    /**
     * 更新还款计划和还款记录表
     *
     * @param br
     * @param repayTime
     * @param param
     * @return
     */
    public int updateBorrowReplay(BorrowRepay br, Date repayTime,
                                  Map<String, Object> param) {
        // 更新还款计划状态
        int i = 0;
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", br.getId());
        paramMap.put("state", BorrowRepayModel.STATE_REPAY_YES);
        SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd");
        Date repayPlanTime = DateUtil.valueOf(time.format(br.getRepayTime()));
        Date repay_time = DateUtil.valueOf(time.format(repayTime));
        if (StringUtil.isNotBlank(br.getPenaltyDay()) && br.getPenaltyAmout() > 0) {
            //实际还款时间在应还款时间之前或当天（不对比时分秒），重置逾期金额和天数
            if (!repay_time.after(repayPlanTime)) {
                br.setPenaltyDay(String.valueOf(0));
                br.setPenaltyAmout(Double.valueOf(0));
                paramMap.put("penaltyDay", String.valueOf(0));
                paramMap.put("penaltyAmout", 0.00);

            }
        }
        i = borrowRepayMapper.updateParam(paramMap);
        if (i > 0) {
            // 生成还款记录
            BorrowRepayLog log = new BorrowRepayLog();
            log.setBorrowId(br.getBorrowId());
            log.setRepayId(br.getId());
            log.setUserId(br.getUserId());
            log.setAmount(Double.valueOf((String) param.get("amount")));// 实际还款金额
            log.setRepayTime(repayTime);// 实际还款时间
            log.setPenaltyDay(br.getPenaltyDay());
            // 实际还款时间在应还款时间之前或当天（不对比时分秒），重置逾期金额和天数
            if (!repay_time.after(repayPlanTime)) {
                log.setPenaltyAmout(0.00);
                log.setPenaltyDay("0");
            } else {
                // 金额减免时 罚金可页面填写
                String penaltyAmout = StringUtil.isNull(param.get("penaltyAmout"));
                if (StringUtil.isNotBlank(penaltyAmout)) {
                    log.setPenaltyAmout(NumberUtil.getDouble(penaltyAmout));
                } else {
                    log.setPenaltyAmout(br.getPenaltyAmout());
                }
            }
            //计算提前还款手续费
            double fee=getFee(br.getBorrowId(),br.getUserId(), br.getRepayTime(), repayTime);
            log.setFee(fee);
            log.setSerialNumber((String) param.get("serialNumber"));
            log.setRepayAccount((String) param.get("repayAccount"));
            log.setRepayWay((String) param.get("repayWay"));
            log.setCreateTime(DateUtil.getNow());
            return borrowRepayLogMapper.save(log);
        }
        return i;
    }

    @Override
    public List<BorrowRepay> listSelective(Map<String, Object> paramMap) {
        return borrowRepayMapper.listSelective(paramMap);
    }

    @Override
    public int updateSelective(Map<String, Object> paramMap) {
        return borrowRepayMapper.updateSelective(paramMap);
    }


    @Override
    public BorrowRepay findSelective(Map<String, Object> paramMap) {
        return borrowRepayMapper.findSelective(paramMap);
    }


    //FIXME 可以提取到clBorrowService中，然后把该service改为facade，因为改动较大，所有后期处理 @author yecy 20171226
    private Boolean isAllFinished(Long mainId) {
        Map<String, Object> map = new HashMap<>();
        map.put("borrowMainId", mainId);
        List<Borrow> borrowList = clBorrowMapper.listSelective(map);
        Boolean isAllFinish = true;
        for (Borrow temp : borrowList) {
            String borrowState = temp.getState();
            if (!BorrowModel.STATE_FINISH.equals(borrowState) && !BorrowModel.STATE_REMISSION_FINISH.equals
                    (borrowState)) {
                isAllFinish = false;
                break;
            }
        }
        return isAllFinish;
    }
    
    /**
     * <p>所有分期订单都还款完成后的后续处理</p>
     * @param borrowId
     * @param userId
     * */
    private void handleAllFinishByBorrowId(Long borrowId,Long userId){
    	Borrow borrow=clBorrowMapper.findByPrimary(borrowId);
    	handleAllFinishByBorrowMainId(borrow.getBorrowMainId(),borrowId,userId);
    }
    
    /**
     * <p>所有分期订单都还款完成后的后续处理</p>
     * @param borrowMainId
     * @param borrowId
     * @param userId
     * */
    private void handleAllFinishByBorrowMainId(Long borrowMainId,Long borrowId,Long userId){
    	// 因为cl_borrow_main表是后期添加的，所以cl_borrow表中之前的数据，没有main_id,这些数据只有单期，且id与main表中的id相同，所以直接用id操作 @author yecy 20171225
        Long mainId = borrowMainId;
        // 此处如果所有子账单全部还款完成，则需要修改主账单的状态为40
        boolean isAllFinish;
        if (mainId != null && !mainId.equals(0L)) {
            isAllFinish = isAllFinished(mainId);
        } else {
            mainId = borrowId;
            isAllFinish = true;
        }
        if (isAllFinish) {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("state", BorrowModel.STATE_FINISH);
            paramMap.put("id", mainId);
            int msg=borrowMainMapper.updateSelective(paramMap);
            if (msg < 1) {
                throw new BussinessException("未找到cl_borrow_main表中id为" + mainId + "的记录");
            }
            BorrowMain borrowMain = borrowMainMapper.findById(mainId);
            //borrowMainProcess保存成功的记录
            borrowMainProgressService.savePressState(borrowMain, BorrowProgressModel.PROGRESS_REPAY_SUCCESS);
            logger.info("*************************>usreId={}的用户额度提升业务逻辑处理结果，处理结果:{}.", new Object[]{userId > 0});
        }
    }
    
    /**
     * <p>根据给定参数求提前手续费</p>
     * @param borrowId 子订单id
     * @param userId   用户id
     * @param repayTime
     * @param reallyTime
     * @return 手续费
     * */
    protected double getFee(Long borrowId,Long userId,Date repayTime,Date reallyTime){
    	BorrowMain borrowMain=borrowMainMapper.getBowMainByBorrowIdWithUserId(borrowId, userId);
    	return getFee(borrowMain.getAmount(), borrowMain.getTemplateInfo(), repayTime, reallyTime);
    }
    
    /**
     * <p>根据给定参数求提前手续费</p>
     * @param borrowAmount 借款金额
     * @param templateInfoJSON 借款模板JSON串
     * @param repayTime
     * @param reallyTime
     * @return 手续费
     * */
    protected double getFee(Double borrowAmount, String templateInfoJSON,Date repayTime,Date reallyTime){
    	TemplateInfoModel templateInfo= new TemplateInfoModel().parseTemplateInfo(borrowAmount, templateInfoJSON);
		return StaginRepaymentPlanData.getPrepayment(repayTime, reallyTime, templateInfo.getInterest(),
				templateInfo.getCycle());
    }

}
