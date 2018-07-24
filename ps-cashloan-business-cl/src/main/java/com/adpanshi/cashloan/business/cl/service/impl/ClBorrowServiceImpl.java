package com.adpanshi.cashloan.business.cl.service.impl;

import com.adpanshi.cashloan.business.cl.domain.BorrowRepay;
import com.adpanshi.cashloan.business.cl.domain.UserAuth;
import com.adpanshi.cashloan.business.cl.mapper.*;
import com.adpanshi.cashloan.business.cl.model.BorrowProgressModel;
import com.adpanshi.cashloan.business.cl.service.ClBorrowService;
import com.adpanshi.cashloan.business.cl.service.CreditsUpgradeService;
import com.adpanshi.cashloan.business.cl.service.UserAuthService;
import com.adpanshi.cashloan.business.core.common.context.Global;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.service.impl.BaseServiceImpl;
import com.adpanshi.cashloan.business.core.common.util.DateUtil;
import com.adpanshi.cashloan.business.core.common.util.StringUtil;
import com.adpanshi.cashloan.business.core.domain.Borrow;
import com.adpanshi.cashloan.business.core.domain.BorrowMain;
import com.adpanshi.cashloan.business.core.domain.User;
import com.adpanshi.cashloan.business.core.mapper.BorrowMainMapper;
import com.adpanshi.cashloan.business.core.mapper.UserBaseInfoMapper;
import com.adpanshi.cashloan.business.core.mapper.UserMapper;
import com.adpanshi.cashloan.business.core.model.BorrowModel;
import com.adpanshi.cashloan.business.cr.domain.Credit;
import com.adpanshi.cashloan.business.cr.domain.CreditLog;
import com.adpanshi.cashloan.business.cr.mapper.CreditLogMapper;
import com.adpanshi.cashloan.business.cr.mapper.CreditMapper;
import com.adpanshi.cashloan.business.rule.constant.TCWindConstant;
import com.adpanshi.cashloan.business.rule.domain.BorrowTemplate;
import com.adpanshi.cashloan.business.rule.domain.TcwindReqLog;
import com.adpanshi.cashloan.business.rule.domain.TongdunReqLog;
import com.adpanshi.cashloan.business.rule.mapper.*;
import com.adpanshi.cashloan.business.rule.model.ClBorrowModel;
import com.adpanshi.cashloan.business.rule.model.IndexModel;
import com.adpanshi.cashloan.business.rule.model.ManageBorrowModel;
import com.adpanshi.cashloan.business.rule.service.TongdunReqLogService;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * 借款信息表ServiceImpl
 *
 * @author
 * @version 1.0.0
 * @date 2017-02-14 10:36:53
 * Copyright 粉团网路 arc All Rights Reserved
 */
@SuppressWarnings({"unchecked", "rawtypes", "unused"})
@Service("clBorrowService")
@Transactional(rollbackFor = Exception.class)
public class ClBorrowServiceImpl extends BaseServiceImpl<Borrow, Long> implements ClBorrowService {

    private static final Logger logger = LoggerFactory.getLogger(ClBorrowServiceImpl.class);

    @Resource
    private CreditsUpgradeService creditsUpgradeService;
    @Resource
    private UserAuthService userAuthService;
    @Resource
    private TongdunReqLogService tongdunReqLogService;

    @Resource
    private ClBorrowMapper clBorrowMapper;
    @Resource
    private BorrowProgressMapper borrowProgressMapper;
    @Resource
    private BorrowRepayMapper borrowRepayMapper;
    @Resource
    private BorrowRepayLogMapper borrowRepayLogMapper;
    @Resource
    private UserBaseInfoMapper userBaseInfoMapper;
    @Resource
    private UrgeRepayOrderMapper urgeRepayOrderMapper;
    @Resource
    private BorrowUserExamineMapper borrowUserExamineMapper;
    @Resource
    private CreditMapper creditMapper;
    @Resource
    private BankCardMapper bankCardMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private UserAuthMapper userAuthMapper;
    @Resource
    private BorrowMainMapper borrowMainMapper;
    @Resource
    private BorrowRuleResultMapper borrowRuleResultMapper;
    @Resource
    private TcwindReqLogMapper tcwindReqLogMapper;
    @Resource
    private BorrowTemplateMapper borrowTemplateMapper;
    @Resource
    private CreditLogMapper creditLogMapper;
    @Override
    public BaseMapper<Borrow, Long> getMapper() {
        return clBorrowMapper;
    }


    @Override
    public List<BorrowMain> findBorrow(Map<String, Object> searchMap) {
        List<BorrowMain> borrowMainList = borrowMainMapper.listSelective(searchMap);
        for(BorrowMain borrowMain : borrowMainList){
            //如果存在放款的订单则判断是否逾期
            if(borrowMain.getState().equals(BorrowModel.STATE_REPAY)){
                Map<String,Object> params = new HashMap<String,Object>();
                params.put("borrowMainId",borrowMain.getId());
                params.put("userId",borrowMain.getUserId());
                Borrow borrow =clBorrowMapper.findSelective(params);
                //如果存在订单且已经逾期(50)
                if(borrow != null && borrow.getState().equals(BorrowModel.STATE_DELAY)){
                    borrowMain.setState(BorrowModel.STATE_DELAY);
                }
            }
        }
        return borrowMainList;
    }

    @Override
    public Page<ClBorrowModel> page(Map<String, Object> searchMap, int current,
                                    int pageSize) {
        PageHelper.startPage(current, pageSize);
        List<ClBorrowModel> list = clBorrowMapper.listAll(searchMap);
        for (ClBorrowModel clBorrowModel : list) {
            clBorrowModel.setCreditTimeStr(DateUtil.dateStr(
                    clBorrowModel.getCreateTime(), "yyyy-MM-dd HH:mm"));
            clBorrowModel.setStateStr(clBorrowModel.getState());
            if ("审核通过".equals(clBorrowModel.getStateStr()) || "放款失败".equals(clBorrowModel.getStateStr())) {
                clBorrowModel.setState("打款中");
                clBorrowModel.setStateStr("打款中");
            }
            if ("还款中".equals(clBorrowModel.getStateStr())) {
                clBorrowModel.setState("待还款");
                clBorrowModel.setStateStr("待还款");
            }
        }
        return (Page<ClBorrowModel>) list;
    }

    @Override
    public List<IndexModel> listIndex() {
        List<IndexModel> list = clBorrowMapper.listIndex();
        List indexList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            String cardNo = list.get(i).getCardNo();
            if (StringUtil.isBlank(cardNo)) {
                continue;
            }
            cardNo = StringUtil.substring(cardNo, cardNo.length() - 4);
            double money = list.get(i).getAmount();
            int time = 0;
            if (list.get(i) != null && list.get(i).getCreateTime() != null
                    && list.get(i).getLoanTime() != null) {
                time = DateUtil.minuteBetween(list.get(i).getCreateTime(), list
                        .get(i).getLoanTime());
            }
            //String value = "尾号" + cardNo + " " + "成功借款" + (int) (money) + "元 用时" + time + "分钟";
            String value = "尾号" + cardNo + " " + "成功借款" + (int) (money) + "元";  //wangxb修改文案
            Map<String, Object> map = new HashMap<>();
            map.put("id", i);
            map.put("value", value);
            indexList.add(map);
        }
        return indexList;
    }

    private void addList(BorrowProgressModel bpModel) {
        if (bpModel.getState().equals(BorrowModel.STATE_PRE)
                || bpModel.getState().equals(BorrowModel.STATE_NEED_REVIEW)) {
            bpModel.setMsg("系统审核中,请耐心等待");
            bpModel.setType("10");
        }
        if (bpModel.getState().equals(BorrowModel.STATE_AUTO_PASS)
                || bpModel.getState().equals(BorrowModel.STATE_PASS)) {
            bpModel.setMsg("恭喜通过风控审核");
            bpModel.setType("10");
        }
        if (bpModel.getState().equals(BorrowModel.STATE_AUTO_REFUSED)
                || bpModel.getState().equals(BorrowModel.STATE_REFUSED)) {
            bpModel.setMsg("很遗憾,您未通过审核");
            bpModel.setType("20");
        }
        if (bpModel.getState().equals(BorrowModel.STATE_REPAY)
                || bpModel.getState().equals(BorrowModel.STATE_REPAY_FAIL)) {
            bpModel.setMsg("打款中,请注意查收短信");
            bpModel.setType("10");
        }
        if (bpModel.getState().equals(BorrowModel.STATE_FINISH)
                || bpModel.getState()
                .equals(BorrowModel.STATE_REMISSION_FINISH)) {
            bpModel.setMsg("已还款");
            bpModel.setType("30");
        }
        if (bpModel.getState().equals(BorrowModel.STATE_DELAY)) {
            bpModel.setMsg("已逾期,请尽快还款");
            bpModel.setType("20");
        }
        if (bpModel.getState().equals(BorrowModel.STATE_BAD)) {
            bpModel.setMsg("已坏账");
            bpModel.setType("20");
        }
    }

    @Override
    public Page<ManageBorrowModel> listModel(Map<String, Object> params,
                                             int currentPage, int pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<ManageBorrowModel> list = clBorrowMapper.listModel(params);
        return (Page<ManageBorrowModel>) list;
    }

    @Override
    public int updateSelective(Map<String, Object> data) {
        return clBorrowMapper.updateSelective(data);
    }

    private String isRiskButtonShow(long borrowId) {
        String riskButtonShow = "0";
        BorrowMain borrow = borrowMainMapper.findByPrimary(borrowId);
        if (borrow == null) {
            logger.info("人工复审信息查询-未查询到订单");
            return riskButtonShow;
        }
        //判断是否是复借用户，是则不显示
        List<BorrowRepay> borrowRepayList = borrowRepayMapper.findRepayWithUser(borrow.getUserId());
        if (borrowRepayList != null && borrowRepayList.size() > 0) {
            return riskButtonShow;
        }
        //寻找最后一次决策响应的数据
        Map<String, Object> params = new HashMap<>();
        params.put("userId", borrow.getUserId());
        params.put("com.adpanshi.cashloan.api.cr.service", TCWindConstant.SERVICE_REVIEW);
        TcwindReqLog tcwindReqLog = tcwindReqLogMapper.findLastOneByUserId(params);
        TongdunReqLog tongdunReqLog = tongdunReqLogService.getReqLoglByBorrowId(borrow);
        //天创结果为空或者失败/同盾分为空或者<0则需要重新发起风控
        if (null == tcwindReqLog || null == tcwindReqLog.getSuccess() || !tcwindReqLog.getSuccess() ||
                null == tongdunReqLog || null == tongdunReqLog.getRsScore() || tongdunReqLog.getRsScore().compareTo(0) < 0) {
            riskButtonShow = "1";
        }
        return riskButtonShow;
    }

    /**
     *
     * @param userId
     * @return
     */
    @Override
    public Map<String, Object> findIndex(String userId) {
        // 综合费用
        String fee = Global.getValue("fee");
        String[] fees = fee.split(",");
        // 借款天数
        String borrowDay = Global.getValue("borrow_day");
        String[] days = borrowDay.split(",");
        // 最大借款期限
        int maxDays = Integer.parseInt(days[days.length - 1]);
        // 最大借款期限
        int minDays = Integer.parseInt(days[0]);
        // 借款额度
        String borrowCredit = Global.getValue("borrow_credit");
        String[] credits = borrowCredit.split(",");
        // 最大借款额度
        double maxCredit = Double.parseDouble(credits[credits.length - 1]);
        // 最小借款额度
        double minCredit = Double.parseDouble(credits[0]);
        // 标题
        String title = Global.getValue("title");
        int authTotal = Global.getInt("auth_total");

        Credit credit;
        long count;
        Map<String, Object> result = new HashMap<>(16);
        Map<String, Object> auth = new HashMap<>(16);
        result.put("total", Global.getValue("init_credit"));
        auth.put("total", authTotal);
        auth.put("result", 0);
        auth.put("qualified", 0);
        //非用户id
        String userIdNone = "0";
        if (StringUtil.isNotBlank(userId) && !StringUtil.equals(userId, userIdNone)) {
            String testWhiteListStr = Global.getValue("test_white_list");
            if(StringUtil.isNotBlank(testWhiteListStr)) {
                String[] userIds = testWhiteListStr.split(",");
                if (!Arrays.asList(userIds).contains(userId)) {
                    result.put("isTest","true");
                }
            }
            User user = userMapper.findByPrimary(Long.parseLong(userId));
            boolean isPwd = false;
            if (StringUtil.isNotBlank(user) && StringUtil.isNotBlank(user.getTradePwd())) {
                isPwd = true;
            }
            result.put("isPwd", isPwd);
            //借款次数
            count = clBorrowMapper.countBorrow(Long.parseLong(userId));
            List<BorrowMain> borrowMainList = borrowMainMapper.selectBorrowByUserId(Long.parseLong(userId));

            if(borrowMainList.size()>0){
                BorrowMain borrowMain = borrowMainList.get(0);
                Map<String, Object> borrowMap = new HashMap<>(16);
                borrowMap.put("orderNo",borrowMain.getOrderNo());
                borrowMap.put("amount",borrowMain.getAmount());
                borrowMap.put("stateStr",borrowMain.getStateStr());
                borrowMap.put("createTime",borrowMain.getCreateTime());
                borrowMap.put("loanTime",borrowMain.getLoanTime());
                borrowMap.put("repayTime",borrowMain.getRepayTime());
                borrowMap.put("id",borrowMain.getId());
                String state = borrowMain.getState();
                borrowMap.put("state",state);
                boolean isShowBorrow = true;
                if(BorrowModel.STATE_REPAY.equals(state)||BorrowModel.STATE_FINISH.equals(state) || BorrowModel.STATE_REPAY_FAIL.equals(state)){
                    Map borrow = clBorrowMapper.queryBorrowPenaltyByBorrowId(borrowMain.getId());
                    if (borrow != null) {
                        borrowMap.put("penaltyAmount",borrow.get("penaltyAmount"));
                        borrowMap.put("reAmount",borrow.get("amount"));
                        borrowMap.put("state",borrow.get("state"));
                    }
                } else if(BorrowModel.STATE_REFUSED.equals(state)||BorrowModel.STATE_AUTO_REFUSED.equals(state)){
                    int againBorrow = Global.getInt("again_borrow");
                    Date now = DateUtil.getNow();
                    int dayCount = DateUtil.daysBetween(borrowMain.getCreateTime(), now);
                    if(dayCount>=againBorrow){
                        isShowBorrow = false;
                    }
                }
                if(isShowBorrow){
                    result.put("borrow", borrowMap);
                }
            }

            Map<String, Object> authMap = new HashMap<>(16);
            authMap.put("userId", userId);
            authMap.put("total", authTotal);
            auth = userAuthService.getUserAuthWithConfigByUserId(Long.parseLong(userId));
            //不是自己的代码，防止出现意外就不用业务异常了
            boolean flag = false;
            authMap.clear();
            authMap.put("userId", userId);
            UserAuth tempUserAuth = userAuthMapper.findSelectiveWithVersion(authMap);

            Map<String, Object> creditMap = new HashMap<>(16);
            creditMap.put("consumerNo", userId + "");
            credit = creditMapper.findSelective(creditMap);
            if (StringUtil.isNotBlank(credit) && "10".equals(credit.getState())) {
                //对没有额度对进行额度评估
                if(credit.getTotal()==0){
                    Map<String,Object> tem = assessment(userId);
                    credit.setTotal((Double) tem.get("total"));
                    credit.setUsed(0.00);
                    credit.setUnuse(0.00);
                }
                if (credit.getTotal() - credit.getUsed() < 100) {
                    minCredit = credit.getTotal() - credit.getUsed();
                }
                Double tmpCredits = creditsUpgradeService.getCreditsByUserId(Long.parseLong(userId));
                maxCredit = credit.getUnuse().doubleValue() + tmpCredits.doubleValue();
                logger.info("------------------------------>userId={},总额度:{}=用户信用额度:{}+临时额度:{}", new Object[]{userId, maxCredit, credit.getUnuse(), tmpCredits});
                result.put("total", maxCredit);
            }

            result.put("count", count);

        }
        result.put("auth", auth);
        result.put("maxCredit", maxCredit);
        result.put("minCredit", minCredit);
        List creditList = new ArrayList<>();
        List dayList = new ArrayList<>();
        List interests = new ArrayList<>();
        for (int i = 0; i < credits.length; i++) {
            creditList.add(credits[i]);
        }
        for (int i = 0; i < days.length; i++) {
            dayList.add(days[i]);
        }
        for (int i = 0; i < fees.length; i++) {
            interests.add(fees[i]);
        }
        result.put("creditList", creditList);
        result.put("dayList", dayList);
        result.put("interests", interests);
        result.put("maxDays", maxDays);
        result.put("minDays", minDays);
        result.put("title", title);
        return result;
    }

    @Override
    public Map<String, Object> assessment(String userId) {
        Map<String, Object> map = new HashMap<>(16);
        List<BorrowTemplate> templates = borrowTemplateMapper.findSelectiveOrderByTimeLimit(new HashMap<String, Object>(16));
        if (CollectionUtils.isNotEmpty(templates)) {
            BorrowTemplate maxTemp = templates.get(templates.size() - 1);
            String maxRule = maxTemp.getBorrowRule();
            if (StringUtils.isNotEmpty(maxRule)) {
                JSONObject rule = JSONObject.parseObject(maxRule);
                Double maxAmount = rule.getDouble("maxAmount");
                // 修改用户额度表
                Map<String, Object> creditMap = new HashMap<>();
                creditMap.put("consumerNo", userId);
                Credit credit = creditMapper.findSelective(creditMap);
                credit.setTotal(maxAmount);
                credit.setUnuse(maxAmount-credit.getUsed());
                creditMapper.update(credit);
                CreditLog creditLog = new CreditLog();
                //用户userId
                creditLog.setConsumerNo(credit.getConsumerNo());
                //变动前额度
                creditLog.setPre(credit.getTotal());
                //变动后额度
                creditLog.setNow(maxAmount);
                //变动额度
                creditLog.setModifyTotal(maxAmount-credit.getTotal());
                //变动类型 10-增加 20-减少 30-冻结 40-解冻
                creditLog.setType("10");
                //变更时间
                creditLog.setModifyTime(DateTime.now().toDate());
                //变更人
                creditLog.setModifyUser("System");
                //变更内容
                creditLog.setRemark("额度初始化");
                creditLogMapper.save(creditLog);
                map.put("total",maxAmount);
                map.put("unused",credit.getUnuse());
            }
        }
        return map;
    }

    @Override
    public int saveAll(List<Borrow> borrowList) {
        if (CollectionUtils.isEmpty(borrowList)) {
            return 0;
        }
        return clBorrowMapper.saveAll(borrowList);
    }

    @Override
    public Borrow findBorrowByMainId(long borrowId) {
        Map<String,Object> map = new HashMap<>();
        map.put("borrowMainId",borrowId);
        return clBorrowMapper.findSelective(map);
    }
}
