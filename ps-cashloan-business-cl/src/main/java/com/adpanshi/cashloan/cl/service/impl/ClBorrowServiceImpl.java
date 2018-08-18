package com.adpanshi.cashloan.cl.service.impl;

import com.adpanshi.cashloan.cl.domain.UserAuth;
import com.adpanshi.cashloan.cl.mapper.BorrowRepayLogMapper;
import com.adpanshi.cashloan.cl.mapper.BorrowRepayMapper;
import com.adpanshi.cashloan.cl.mapper.UrgeRepayOrderMapper;
import com.adpanshi.cashloan.cl.mapper.UserAuthMapper;
import com.adpanshi.cashloan.cl.model.BorrowProgressModel;
import com.adpanshi.cashloan.cl.service.ClBorrowService;
import com.adpanshi.cashloan.cl.service.CreditsUpgradeService;
import com.adpanshi.cashloan.cl.service.UserAuthService;
import com.adpanshi.cashloan.cl.service.UserEquipmentInfoService;
import com.adpanshi.cashloan.core.common.context.Global;
import com.adpanshi.cashloan.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.core.common.service.impl.BaseServiceImpl;
import com.adpanshi.cashloan.core.common.util.DateUtil;
import com.adpanshi.cashloan.core.common.util.StringUtil;
import com.adpanshi.cashloan.core.domain.Borrow;
import com.adpanshi.cashloan.core.domain.BorrowMain;
import com.adpanshi.cashloan.core.domain.User;
import com.adpanshi.cashloan.core.domain.UserBaseInfo;
import com.adpanshi.cashloan.core.mapper.BorrowMainMapper;
import com.adpanshi.cashloan.core.mapper.UserBaseInfoMapper;
import com.adpanshi.cashloan.core.mapper.UserMapper;
import com.adpanshi.cashloan.core.model.BorrowModel;
import com.adpanshi.cashloan.core.model.ManagerUserModel;
import com.adpanshi.cashloan.core.service.UserBaseInfoService;
import com.adpanshi.cashloan.dispatch.run.bo.DispatchRunResponseBo;
import com.adpanshi.cashloan.dispatch.run.domain.DispatchRunDomain;
import com.adpanshi.cashloan.rule.domain.BankCard;
import com.adpanshi.cashloan.rule.domain.BorrowRuleResult;
import com.adpanshi.cashloan.rule.domain.BorrowTemplate;
import com.adpanshi.cashloan.rule.domain.UserContactsMatch;
import com.adpanshi.cashloan.rule.domain.UserEmerContacts;
import com.adpanshi.cashloan.rule.domain.UserEquipmentInfo;
import com.adpanshi.cashloan.rule.domain.equifaxReport.base.Envelope;
import com.adpanshi.cashloan.rule.mapper.BankCardMapper;
import com.adpanshi.cashloan.rule.mapper.BorrowRuleResultMapper;
import com.adpanshi.cashloan.rule.mapper.BorrowTemplateMapper;
import com.adpanshi.cashloan.rule.mapper.ClBorrowMapper;
import com.adpanshi.cashloan.rule.mapper.UserEmerContactsMapper;
import com.adpanshi.cashloan.rule.model.ClBorrowModel;
import com.adpanshi.cashloan.rule.model.IndexModel;
import com.adpanshi.cashloan.rule.model.ManageBorrowModel;
import com.adpanshi.cashloan.rule.model.ManageBorrowTestModel;
import com.adpanshi.cashloan.rule.service.EquifaxReportService;
import com.adpanshi.cashloan.rule.service.UserContactsService;
import com.adpanshi.cashloan.rule.util.BorrowRiskRuleMatch;
import com.adpanshi.creditrank.cr.domain.Credit;
import com.adpanshi.creditrank.cr.domain.CreditLog;
import com.adpanshi.creditrank.cr.mapper.CreditLogMapper;
import com.adpanshi.creditrank.cr.mapper.CreditMapper;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private ClBorrowMapper clBorrowMapper;
    @Resource
    private BorrowRepayMapper borrowRepayMapper;
    @Resource
    private BorrowRepayLogMapper borrowRepayLogMapper;
    @Resource
    private UserBaseInfoMapper userBaseInfoMapper;
    @Resource
    private UrgeRepayOrderMapper urgeRepayOrderMapper;
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
    private BorrowTemplateMapper borrowTemplateMapper;
    @Resource
    private CreditLogMapper creditLogMapper;
    @Resource
    private EquifaxReportService equifaxReportService;
    @Resource
    private UserBaseInfoService userBaseInfoService;
    @Resource
    private UserContactsService userContactsService;
    @Resource
    private UserEmerContactsMapper userEmerContactsMapper;

    @Resource
    private UserEquipmentInfoService userEquipmentInfoService;
    @Resource
    private DispatchRunDomain dispatchRunDomain;


    @Override
    public BaseMapper<Borrow, Long> getMapper() {
        return clBorrowMapper;
    }


    @Override
    public List<BorrowMain> findBorrow(Map<String, Object> searchMap) {
        List<BorrowMain> borrowMainList = borrowMainMapper.listSelective(searchMap);
        for (BorrowMain borrowMain : borrowMainList) {
            //如果存在放款的订单则判断是否逾期
            if (borrowMain.getState().equals(BorrowModel.STATE_REPAY)) {
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("borrowMainId", borrowMain.getId());
                params.put("userId", borrowMain.getUserId());
                Borrow borrow = clBorrowMapper.findSelective(params);
                //如果存在订单且已经逾期(50)
                if (borrow != null && borrow.getState().equals(BorrowModel.STATE_DELAY)) {
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
            //wangxb修改文案
            String value = "尾号" + cardNo + " " + "成功借款" + (int) (money) + "元";
            Map<String, Object> map = new HashMap<>();
            map.put("id", i);
            map.put("value", value);
            indexList.add(map);
        }
        return indexList;
    }

    @Override
    public List<Borrow> findBorrowByMap(Map<String, Object> searchMap) {
        List<Borrow> list = clBorrowMapper.listSelective(searchMap);
        return list;
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

    /**
     * 关联用户的借款分页查询后台列表显示 && 机审通过的所有订单
     *
     * @param params
     * @param currentPage
     * @param pageSize
     * @return
     * @author:M
     * @date:2017-07-10
     */
    @Override
    public Page<ManageBorrowModel> automaticListModel(Map<String, Object> params,
                                                      int currentPage, int pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<ManageBorrowModel> list = clBorrowMapper.automaticListModel(params);
        return (Page<ManageBorrowModel>) list;
    }

    @Override
    public int updateSelective(Map<String, Object> data) {
        return clBorrowMapper.updateSelective(data);
    }

    /**
     * 修改标的状态
     *
     * @param id
     * @param state
     */
    @Override
    public int modifyState(long id, String state) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("state", state);
        paramMap.put("id", id);
        return clBorrowMapper.updateSelective(paramMap);
    }

    @Override
    public List<ManageBorrowTestModel> seleteUser() {
        List<ManageBorrowTestModel> list = clBorrowMapper.seleteUser();
        List<ManageBorrowTestModel> userList = new ArrayList<>();
        for (ManageBorrowTestModel user : list) {
            boolean type = true;
            Map<String, Object> searchMap = new HashMap<>();
            searchMap.put("userId", user.getUserId());
            List<Borrow> borrowList = clBorrowMapper.listSelective(searchMap);
            for (Borrow borrow : borrowList) {
                if (!borrow.getState().equals(BorrowModel.STATE_AUTO_REFUSED)
                        & !borrow.getState().equals(BorrowModel.STATE_REFUSED)
                        & !borrow.getState().equals(BorrowModel.STATE_FINISH)
                        & !borrow.getState().equals(
                        BorrowModel.STATE_REMISSION_FINISH)) {
                    type = false;
                }
            }
            if (type) {
                userList.add(user);
            }
        }
        return userList;
    }

    /**
     * 首页所有数据
     *
     * @param userId
     * @return
     */
    @Override
    public Map<String, Object> findIndex(String userId) {
        // 综合费用 这个是什么费用
        String fee = Global.getValue("fee");
        String[] fees = fee.split(",");
        // 借款天数 这个是什么
        String borrowDay = Global.getValue("borrow_day");
        String[] days = borrowDay.split(",");
        // 最大借款期限
        int maxDays = Integer.parseInt(days[days.length - 1]);
        // 最小借款期限
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
            if (StringUtil.isNotBlank(testWhiteListStr)) {
                String[] userIds = testWhiteListStr.split(",");
                if (!Arrays.asList(userIds).contains(userId)) {
                    result.put("isTest", "true");
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

            if (borrowMainList.size() > 0) {
                BorrowMain borrowMain = borrowMainList.get(0);
                Map<String, Object> borrowMap = new HashMap<>(16);
                borrowMap.put("orderNo", borrowMain.getOrderNo());
                borrowMap.put("amount", borrowMain.getAmount());
                borrowMap.put("stateStr", borrowMain.getStateStr());
                borrowMap.put("createTime", borrowMain.getCreateTime());
                borrowMap.put("loanTime", borrowMain.getLoanTime());
                borrowMap.put("repayTime", borrowMain.getRepayTime());
                borrowMap.put("id", borrowMain.getId());
                borrowMap.put("remark",borrowMain.getRemark());
                String state = borrowMain.getState();
                borrowMap.put("state", state);
                boolean isShowBorrow = true;
                if (BorrowModel.STATE_REPAY.equals(state) || BorrowModel.STATE_FINISH.equals(state) || BorrowModel.STATE_REPAY_FAIL.equals(state)) {
                    Map borrow = clBorrowMapper.queryBorrowPenaltyByBorrowId(borrowMain.getId());
                    if (borrow != null) {
                        borrowMap.put("penaltyAmount", borrow.get("penaltyAmount"));
                        borrowMap.put("reAmount", borrow.get("amount"));
                        borrowMap.put("state", borrow.get("state"));
                    }
                } else if (BorrowModel.STATE_REFUSED.equals(state) || BorrowModel.STATE_AUTO_REFUSED.equals(state)) {
                    int againBorrow = Global.getInt("again_borrow");
                    Date now = DateUtil.getNow();
                    int dayCount = DateUtil.daysBetween(borrowMain.getCreateTime(), now);
                    if (dayCount >= againBorrow) {
                        isShowBorrow = false;
                    }
                }
                if (isShowBorrow) {
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
//                if(credit.getTotal()==0){
                Map<String, Object> tem = assessment(userId);
                if (user == null) {
                    throw new RuntimeException("相关用户不存在");
                }
                Long userIdLong = Long.parseLong(userId);
                //==============================开始==================================
                //验证用户信息是否提交完全
                boolean infoIntegrity  = true;
                //获取个人信息
                UserBaseInfo userBaseInfo = userBaseInfoService.findByUserId(userIdLong);
                if(userBaseInfo == null){
                    infoIntegrity = false;
                }
                //获取用户紧急联系人
                Map<String,Object> paramMap =new HashMap<>();
                paramMap.put("userId",userIdLong);
                List<UserEmerContacts> userEmerContactsList = userEmerContactsMapper.listSelective(paramMap);
                if(userEmerContactsList == null || userEmerContactsList .size() == 0){
                    infoIntegrity = false;
                }
                //获取用户银行卡列表
                List<BankCard> bankCardList = bankCardMapper.listSelective(paramMap);
                if(bankCardList == null || bankCardList.size() == 0){
                    infoIntegrity = false;
                }
                if(infoIntegrity){

                    //获取通讯录
//                    Page<UserContactsMatch> page = userContactsService.listContactsNew(userIdLong, 1, 30);
//                    //获得用户信用报告
//                    Envelope envelope = equifaxReportService.getEquifaxReportDetail(userIdLong);
                    //调用规则引擎，获得结果
                    JSONObject borrowRuleResult = (JSONObject) tem.get("result");
                    credit.setTotal(borrowRuleResult.getDouble("loanLimit"));
                    result.put("resultType",borrowRuleResult.get("resultType"));
                    result.put("resultMsg",borrowRuleResult.get("resultMsg"));
                }else {
                    credit.setTotal(3000.0);
                }
                credit.setUnuse(credit.getTotal() - credit.getUsed());

                if (credit.getTotal() - credit.getUsed() < 100) {
                    minCredit = credit.getTotal() - credit.getUsed();
                }
                Double tmpCredits = creditsUpgradeService.getCreditsByUserId(Long.parseLong(userId));
                maxCredit = credit.getUnuse().doubleValue() + tmpCredits.doubleValue();
                //防止负数出现
                maxCredit = maxCredit > 0 ? maxCredit : 0;
                minCredit = maxCredit > minCredit ? minCredit : maxCredit;
                logger.info("------------------------------>userId={},总额度:{}=用户信用额度:{}+临时额度:{}", new Object[]{userId, maxCredit, credit.getUnuse(), tmpCredits});
                result.put("total", maxCredit);
                result.put("infoIntegrity",infoIntegrity);

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
            BigDecimal bigDecimal = new BigDecimal(credits[i]);
            //這個是用來控制額度選定的
            if(bigDecimal.doubleValue() <= maxCredit){
                creditList.add(credits[i]);
            }
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


                //获取用户的全名/邮箱号
                ManagerUserModel managerUserModel = userBaseInfoService.getBaseModelByUserId(Long.parseLong(userId));
                //获取设备指纹
                UserEquipmentInfo userEquipmentInfo = userEquipmentInfoService.findSelective(Long.parseLong(userId));
                String blackBox = "null";
                if (userEquipmentInfo!=null){
                    blackBox = userEquipmentInfo.getDeviceFinger();
                }
                Map rawDataMap = new HashMap(3);
                //调起节点,调用规则引擎，获得结果
                DispatchRunResponseBo<String> dispatchRunResponseBo = dispatchRunDomain.startNode(
                        managerUserModel.getUserDataId(),"india_oloan_loanApply",managerUserModel.getPhone(),
                        managerUserModel.getLoginNameEmail(),managerUserModel.getIdNo(),managerUserModel.getRealName(),
                        blackBox,rawDataMap);

                logger.info("+++++++节点返回结果："+JSONObject.toJSONString(dispatchRunResponseBo));
                String result = dispatchRunResponseBo.getData();
                JSONObject resultJson = JSONObject.parseObject(result);
                Double maxAmount = Double.parseDouble(resultJson.getString("loanLimit"));
                // 修改用户额度表
                Map<String, Object> creditMap = new HashMap<>(3);
                creditMap.put("consumerNo", userId);
                Credit credit = creditMapper.findSelective(creditMap);
                //兼容借款中的老用户
                if(!maxAmount.equals(credit.getTotal())){
                    if(maxAmount>credit.getTotal() || credit.getUsed()==0){
                        credit.setTotal(maxAmount);
                        double unUsed = maxAmount - credit.getUsed();
                        unUsed = unUsed >= 0?unUsed:0;
                        credit.setUnuse(unUsed);
                        creditMapper.update(credit);
                    }
                }
                CreditLog creditLog = new CreditLog();
                //用户userId
                creditLog.setConsumerNo(credit.getConsumerNo());
                //变动前额度
                creditLog.setPre(credit.getTotal());
                //变动后额度
                creditLog.setNow(maxAmount);
                //变动额度
                creditLog.setModifyTotal(maxAmount - credit.getTotal());
                //变动类型 10-增加 20-减少 30-冻结 40-解冻
                creditLog.setType("10");
                //变更时间
                creditLog.setModifyTime(DateTime.now().toDate());
                //变更人
                creditLog.setModifyUser("System");
                //变更内容
                creditLog.setRemark("初始化");
                creditLogMapper.save(creditLog);
                map.put("total", maxAmount);
                map.put("unused", credit.getUnuse());
                map.put("result",resultJson);
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
    public Boolean isAllFinished(Long mainId) {
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

    @Override
    public Borrow findBorrowByMainId(long borrowId) {
        Map<String, Object> map = new HashMap<>();
        map.put("borrowMainId", borrowId);
        return clBorrowMapper.findSelective(map);
    }
}
