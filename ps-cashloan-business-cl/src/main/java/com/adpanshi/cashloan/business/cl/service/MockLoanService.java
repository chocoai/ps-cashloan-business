package com.adpanshi.cashloan.business.cl.service;

import com.adpanshi.cashloan.business.cl.domain.BorrowMainProgress;
import com.adpanshi.cashloan.business.cl.domain.BorrowProgress;
import com.adpanshi.cashloan.business.cl.domain.PayLog;
import com.adpanshi.cashloan.business.cl.model.PayLogModel;
import com.adpanshi.cashloan.business.core.common.enums.OrderPrefixEnum;
import com.adpanshi.cashloan.business.core.common.util.OrderNoUtil;
import com.adpanshi.cashloan.business.core.domain.Borrow;
import com.adpanshi.cashloan.business.core.domain.BorrowMain;
import com.adpanshi.cashloan.business.core.enjoysign.constants.EnjoysignConstant;
import com.adpanshi.cashloan.business.core.mapper.BorrowMainMapper;
import com.adpanshi.cashloan.business.core.model.BorrowModel;
import com.adpanshi.cashloan.business.rule.domain.BankCard;
import com.adpanshi.cashloan.business.rule.mapper.BankCardMapper;
import com.adpanshi.cashloan.business.rule.model.BorrowTemplateModel;
import com.alibaba.fastjson.JSONObject;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tool.util.BigDecimalUtil;
import tool.util.DateUtil;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * 此类用于模拟放款，从manage中的managePayLogController中全部拷贝，用于临时伪造数据使用，请不要在其他地方使用！！！！
 *
 * @author yecy
 * @date 2018/3/16 12:01
 */
@Service
public class MockLoanService {
    private static final Logger logger = LoggerFactory.getLogger(MockLoanService.class);

    @Resource
    private PayLogService payLogService;
    @Resource
    private ClBorrowService clBorrowService;
    @Resource
    private BorrowProgressService borrowProgressService;
    @Resource
    private BorrowRepayService borrowRepayService;
    @Resource
    private BorrowMainService borrowMainService;
    @Resource
    private BorrowMainProgressService borrowMainProgressService;
    @Resource
    private CreditsUpgradeService creditsUpgradeService;
    @Resource
    private EnjoysignRecordService enjoysignRecordService;
    @Resource
    private BorrowMainMapper borrowMainMapper;
    @Resource
    private BankCardMapper bankCardMapper;

    /**
     * 模拟付款成功 - 异步回调
     *
     * @param orderNo
     * @throws Exception
     * @update: nmnl...授权模拟
     */
    public void testPaymentNotify(String orderNo) throws Exception {
        logger.info("模拟数据中，调用模拟付款成功---------");
        Map<String, Object> orderMap = new HashMap<>();
        orderMap.put("orderNo", orderNo);
        BorrowMain borrowMain = borrowMainMapper.findSelective(orderMap);

        if (borrowMain == null){
            logger.error("orderNo:{} 借款订单不存在。",orderNo);
            return;
        }
        PayLog payLog = payLogService.findPayLogByLastOrderNoWithBorrowId(orderNo, null);
        if (null == payLog) {
            payLog = new PayLog();
            payLog.setOrderNo(orderNo);
            payLog.setUserId(borrowMain.getUserId());
            payLog.setBorrowMainId(borrowMain.getId());
            payLog.setAmount(borrowMain.getAmount());

            BankCard bankCard = bankCardMapper.findByPrimary(borrowMain.getCardId());
            if (bankCard != null){
                payLog.setBank(bankCard.getBank());
                payLog.setCardNo(bankCard.getCardNo());
            }else {
                payLog.setBank("建设银行");
                payLog.setCardNo("11111111");
            }

            payLog.setSource(PayLogModel.SOURCE_FUNDS_OWN);
            payLog.setType(PayLogModel.TYPE_PAYMENT);
            payLog.setScenes(PayLogModel.SCENES_LOANS);
            payLog.setState(PayLogModel.STATE_PAYMENT_WAIT);
            payLog.setRemark("导入数据放款成功");
            payLog.setPayReqTime(DateUtil.getNow());
            payLog.setCreateTime(DateUtil.getNow());
            payLogService.save(payLog);
        }

        if (PayLogModel.STATE_PAYMENT_WAIT.equals(payLog.getState()) ||
                PayLogModel.STATE_AUDIT_PASSED.equals(payLog.getState()) ||
                PayLogModel.STATE_PAYMENT_FAILED.equals(payLog.getState())) {
            if (PayLogModel.SCENES_LOANS.equals(payLog.getScenes())) {
                // 修改借款状态

                Long mainBorrowId = borrowMain.getId();
                if (BorrowModel.STATE_REPAY.equals(borrowMain.getState())) {
                    logger.info("已经成功放款，不需要重复放款。mainBorrowId:{}", mainBorrowId);
                    return;
                }
                String settleDate = "";
                DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyyMMdd");
                DateTime now = DateTime.now();
                DateTime payTime;
                if (org.apache.commons.lang3.StringUtils.isNotEmpty(settleDate)) {
                    payTime = DateTime.parse(settleDate, dtf);
                    payTime = now.withDate(payTime.getYear(), payTime.getMonthOfYear(), payTime.getDayOfMonth());
                } else {
                    payTime = now;
                }
                DateTime repayTime = payTime.plusDays(Integer.parseInt(borrowMain.getTimeLimit()) - 1);
                repayTime = repayTime.withTime(23, 59, 59, 0);

                borrowMainService.updatePayState(mainBorrowId, BorrowModel.STATE_REPAY, payTime.toDate(), repayTime.toDate());
                borrowMain.setState(BorrowModel.STATE_REPAY);

                // 放款进度添加
                BorrowMainProgress bp = new BorrowMainProgress();
                bp.setUserId(payLog.getUserId());
                bp.setBorrowId(payLog.getBorrowMainId());
                bp.setState(BorrowModel.STATE_REPAY);
                bp.setRemark(BorrowModel.convertBorrowRemark(bp.getState()));
                bp.setCreateTime(DateUtil.getNow());
                borrowMainProgressService.insert(bp);

                String templateInfo = borrowMain.getTemplateInfo();
                BorrowTemplateModel template = JSONObject.parseObject(templateInfo, BorrowTemplateModel.class);
                Long cycle = Long.parseLong(template.getCycle());
                Long timeLimit = Long.parseLong(template.getTimeLimit());
                Long borrowSize = timeLimit / cycle;

                // 创建借款账单（第一条借款账单创建时间以连连支付时间为准，之后的为前一条创建时间+前一条期限） @author yecy 20171215
                List<Borrow> borrowList = createBorrows(borrowMain, "", borrowSize);
                //根据主借款流程，生成子借款流程
                createBorrowProcessList(borrowList, borrowMain);
                // 根据借款账单生成还款计划并授权(应还时间根据借款账单的创建时间生成)
                borrowRepayService.genRepayPlans(borrowList, borrowMain,true);

//					// 生成还款计划并授权
//					borrowRepayService.genRepayPlan(borrow,"");
                // 更新订单状态
                Map<String, Object> paramMap = new HashMap<String, Object>();
                paramMap.put("state", PayLogModel.STATE_PAYMENT_SUCCESS);
                paramMap.put("updateTime", DateUtil.getNow());
                paramMap.put("id", payLog.getId());
                payLogService.updateSelective(paramMap);
                logger.info("***********************************>放款异步回调，开始更新用户信用额度.....");

                //@remarks: 只有真正放款.才会更改额度. @date: 20170724 @author: nmnl
                int TMPcount = creditsUpgradeService.modifyCreditAfterLoan(borrowMain.getUserId(), borrowMain.getAmount());

                logger.info("***********************************>修改用户信用额度结果:{}......", new Object[]{TMPcount > 0});

                //就目前而言、有可能出现回调多次、并且还是付款成功的、这里先查询如果已有订单且是成功的、则跳过自动签章流程...
                String orderId = OrderNoUtil.getSerialNumber(EnjoysignConstant.ORDER_PREFIX);
                logger.info("----------------------->借款人borId={},开始发起签署并进行后台自动签章...", new Object[]{borrowMain.getId()});
                int count = enjoysignRecordService.startSignWithAutoSilentSign(borrowMain, orderId);
                logger.info("----------------------->签章结束，签章结果：{}....", new Object[]{count > 0});

            }

        } else {
            logger.info("订单状态错误");
        }
    }

    private List<Borrow> createBorrows(BorrowMain borrowMain, String settleDate, Long borrowSize) {
        List<Borrow> borrowList = new ArrayList<>(borrowSize.intValue());
        Date payTime;
        if (StringUtils.isEmpty(settleDate)) {
            payTime = DateUtil.getNow();
        } else {
            payTime = DateUtil.valueOf(settleDate, DateUtil.DATEFORMAT_STR_012);
        }
        Borrow firstBorrow = getFirstBorrow(borrowMain, borrowSize.doubleValue(), payTime);
        borrowList.add(0, firstBorrow);


        Double otherAmount = div(borrowMain.getAmount(), borrowSize.doubleValue());
        Double otherRealAmount = div(borrowMain.getRealAmount(), borrowSize.doubleValue());
        Double otherFee = div(borrowMain.getFee(), borrowSize.doubleValue());
        Long otherTimeLimit = Long.valueOf(borrowMain.getTimeLimit()) / borrowSize;

        Date beginTime = DateUtil.rollDay(payTime, Integer.valueOf(firstBorrow.getTimeLimit()));
        for (int i = 1; i < borrowSize; i++) {
            Borrow borrow = new Borrow();
            borrow.setAmount(otherAmount);
            borrow.setRealAmount(otherRealAmount);
            borrow.setFee(otherFee);
            borrow.setTimeLimit(String.valueOf(otherTimeLimit));

            Date createTime = DateUtil.rollDay(beginTime, (i - 1) * otherTimeLimit.intValue());
            borrow.setCreateTime(createTime);
            String orderNo = getOrderNo(borrowMain, i + 1);
            borrow.setOrderNo(orderNo);
            fillBorrow(borrow, borrowMain);
            borrowList.add(borrow);
        }
        clBorrowService.saveAll(borrowList);
        return borrowList;
    }

    private void createBorrowProcessList(List<Borrow> borrowList, BorrowMain borrowMain) {
        List<BorrowProgress> processList = new ArrayList<>();
        List<BorrowMainProgress> mainProcessList = borrowMainProgressService.getProcessByMainId(borrowMain.getId());
        for (Borrow borrow : borrowList) {
            for (BorrowMainProgress mainProcess : mainProcessList) {
                BorrowProgress bp = new BorrowProgress();
                BeanUtils.copyProperties(mainProcess, bp, "id", "borrowId");
                bp.setBorrowId(borrow.getId());

                processList.add(bp);
            }
        }
        borrowProgressService.saveAll(processList);
    }

    /**
     * 保留两位小数，向下舍入
     *
     * @param v1
     * @param v2
     * @return
     */
    private double div(Double v1, Double v2) {
        BigDecimal d1 = new BigDecimal(v1);
        BigDecimal d2 = new BigDecimal(v2);
        BigDecimal result = d1.divide(d2, 2, RoundingMode.DOWN);
        return result.doubleValue();
    }

    private Borrow getFirstBorrow(BorrowMain borrowMain, Double borrowSize, Date createTime) {
        Borrow borrow = new Borrow();

        Double otherSize = BigDecimalUtil.sub(borrowSize, 1d);
        Double mainAmount = borrowMain.getAmount();
        Double otherAmount = div(mainAmount, borrowSize);
        Double amount = BigDecimalUtil.sub(mainAmount, BigDecimalUtil.mul(otherAmount, otherSize));
        borrow.setAmount(amount);

        Double mainRealAmount = borrowMain.getRealAmount();
        Double otherRealAmount = div(mainRealAmount, borrowSize);
        Double realAmount = BigDecimalUtil.sub(mainRealAmount, BigDecimalUtil.mul(otherRealAmount, otherSize));
        borrow.setRealAmount(realAmount);

        Double mainFee = borrowMain.getFee();
        Double otherFee = div(mainFee, borrowSize);
        Double fee = BigDecimalUtil.sub(mainFee, BigDecimalUtil.mul(otherFee, otherSize));
        borrow.setFee(fee);

        Long mainTimeLimit = Long.valueOf(borrowMain.getTimeLimit());
        Long otherTimeLimit = mainTimeLimit / borrowSize.longValue();
        Long timeLimit = mainTimeLimit - otherTimeLimit * otherSize.longValue();
        borrow.setTimeLimit(String.valueOf(timeLimit));

        borrow.setCreateTime(createTime);

        String orderNo = getOrderNo(borrowMain, 1);
        borrow.setOrderNo(orderNo);

        fillBorrow(borrow, borrowMain);
        return borrow;
    }

    private String getOrderNo(BorrowMain borrowMain, Integer num) {
        StringBuilder orderNo = new StringBuilder();
        orderNo.append(OrderPrefixEnum.REPAYMENT.getCode()).append(borrowMain.getOrderNo().substring(1)).append("X").append(num);
        return orderNo.toString();
    }

    private void fillBorrow(Borrow borrow, BorrowMain borrowMain) {
        borrow.setBorrowMainId(borrowMain.getId());
        borrow.setState(borrowMain.getState());
        borrow.setUserId(borrowMain.getUserId());
        borrow.setCardId(borrowMain.getCardId());
        borrow.setClient(borrowMain.getClient());
        borrow.setAddress(borrowMain.getAddress());
        borrow.setCoordinate(borrowMain.getCoordinate());
        borrow.setIp(borrowMain.getIp());
    }
}
