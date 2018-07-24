package com.adpanshi.cashloan.business.rule.service.impl;

import com.adpanshi.cashloan.business.core.common.context.Global;
import com.adpanshi.cashloan.business.core.common.exception.SimpleMessageException;
import com.adpanshi.cashloan.business.core.common.util.DateUtil;
import com.adpanshi.cashloan.business.core.domain.BorrowMain;
import com.adpanshi.cashloan.business.core.mapper.UserBaseInfoMapper;
import com.adpanshi.cashloan.business.core.mapper.UserOtherInfoMapper;
import com.adpanshi.cashloan.business.cr.domain.Credit;
import com.adpanshi.cashloan.business.cr.domain.CreditLog;
import com.adpanshi.cashloan.business.cr.mapper.CreditLogMapper;
import com.adpanshi.cashloan.business.cr.mapper.CreditMapper;
import com.adpanshi.cashloan.business.rule.constant.TCWindConstant;
import com.adpanshi.cashloan.business.rule.domain.BorrowRuleResult;
import com.adpanshi.cashloan.business.rule.domain.BorrowTemplate;
import com.adpanshi.cashloan.business.rule.domain.TcwindReqLog;
import com.adpanshi.cashloan.business.rule.domain.UserBankBillResp;
import com.adpanshi.cashloan.business.rule.enums.SaasServiceEnum;
import com.adpanshi.cashloan.business.rule.enums.TCRiskManage;
import com.adpanshi.cashloan.business.rule.mapper.*;
import com.adpanshi.cashloan.business.rule.service.TCWindService;
import com.adpanshi.cashloan.business.system.mapper.SysDictDetailMapper;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tool.util.BigDecimalUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

//import com.fans.api.tcwind.RiskAssmentReqParams;
//import com.fans.api.tcwind.RiskReviewReqParams;
//import com.fans.api.tcwind.TCWindClient;

/**
 * @author yecy
 * @date 2018/2/6 14:42
 */
@Service("tcWindService")
public class TCWindServiceImpl implements TCWindService {

    public static final Logger logger = LoggerFactory.getLogger(TCWindServiceImpl.class);

    private TcwindReqLogMapper tcwindReqLogMapper;
    private TCWindModelMapper tcWindModelMapper;
    private UserBankBillRespMapper userBankBillRespMapper;
    private TCOrderLogMapper tcOrderLogMapper;
    private CreditMapper creditMapper;
    private CreditLogMapper creditLogMapper;
    private BorrowTemplateMapper borrowTemplateMapper;
    private UserBaseInfoMapper userBaseInfoMapper;
    private BankCardMapper bankCardMapper;
    private UserEmerContactsMapper userEmerContactsMapper;
    private UserOtherInfoMapper userOtherInfoMapper;
    private UserEquipmentInfoMapper userEquipmentInfoMapper;
    private ZhimaMapper zhimaMapper;
    private UserAppsMapper userAppsMapper;
    private TCCallingRecordMapper tCCallingRecordMapper;
    private TCMessageBillMapper tCMessageBillMapper;
    private UserContactsMapper userContactsMapper;
    private SysDictDetailMapper sysDictDetailMapper;
    private SaasRespRecordMapper saasRespRecordMapper;


    @Autowired
    public TCWindServiceImpl(TcwindReqLogMapper tcwindReqLogMapper, TCWindModelMapper tcWindModelMapper, UserBankBillRespMapper
            userBankBillRespMapper, TCOrderLogMapper tcOrderLogMapper, CreditMapper creditMapper, CreditLogMapper creditLogMapper,
                             BorrowTemplateMapper borrowTemplateMapper, UserBaseInfoMapper userBaseInfoMapper,
                             BankCardMapper bankCardMapper, UserEmerContactsMapper userEmerContactsMapper,
                             UserOtherInfoMapper userOtherInfoMapper, UserEquipmentInfoMapper userEquipmentInfoMapper,
                             ZhimaMapper zhimaMapper, UserAppsMapper userAppsMapper, TCCallingRecordMapper tCCallingRecordMapper,
                             TCMessageBillMapper tCMessageBillMapper, UserContactsMapper userContactsMapper, SysDictDetailMapper sysDictDetailMapper, SaasRespRecordMapper saasRespRecordMapper) {
        this.tcwindReqLogMapper = tcwindReqLogMapper;
        this.tcWindModelMapper = tcWindModelMapper;
        this.userBankBillRespMapper = userBankBillRespMapper;
        this.tcOrderLogMapper = tcOrderLogMapper;
        this.creditMapper = creditMapper;
        this.creditLogMapper = creditLogMapper;
        this.borrowTemplateMapper = borrowTemplateMapper;
        this.userBaseInfoMapper = userBaseInfoMapper;
        this.bankCardMapper = bankCardMapper;
        this.userEmerContactsMapper = userEmerContactsMapper;
        this.userOtherInfoMapper = userOtherInfoMapper;
        this.userEquipmentInfoMapper = userEquipmentInfoMapper;
        this.zhimaMapper = zhimaMapper;
        this.userAppsMapper = userAppsMapper;
        this.tCCallingRecordMapper = tCCallingRecordMapper;
        this.tCMessageBillMapper = tCMessageBillMapper;
        this.userContactsMapper = userContactsMapper;
        this.sysDictDetailMapper = sysDictDetailMapper;
        this.saasRespRecordMapper = saasRespRecordMapper;
    }

    @Override
    public Double assessmentAmount_(Long userId) {
        String tcreditOpen = Global.getValue(TCWindConstant.TCREDIT_OPEN);
        if (!"on".equals(tcreditOpen) && "dev".equals(Global.getValue("app_environment"))) {
            Double nowCredit = Global.getDouble("max_borrow_amount");
            modifyCredit(userId, nowCredit);
            return nowCredit;
        }

        //如果最后一条请求成功的记录在有效期内，则不再进行请求 @author yecy 20180207
        TcwindReqLog reqLog = getPreAuthReqLog(userId, TCWindConstant.SERVICE_PREAUTH);
        if (reqLog != null) {
            JSONObject json = JSONObject.parseObject(reqLog.getRespParams());
            return getAssessmentAmount(json);
        }

        // 大风策预授信接口请求参数拼装

//        RiskAssmentReqParams riskAssmentReqParams = tcWindModelMapper.getParamsByUserId(userId);
//        if (riskAssmentReqParams == null) {
//            logger.error("未找到用户信息，userId{}", userId);
//            throw new SimpleMessageException("未找到用户信息。");
//        }


        DateTime appleTime = DateTime.now();
//        riskAssmentReqParams.setAccount_info(getAccountInfo(userId));
//        riskAssmentReqParams.setProd_code("");
//        riskAssmentReqParams.setApply_time(appleTime.toString(DateUtil.YYYY_MM_DD_HH_MM_SS));
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
//        riskAssmentReqParams.setOut_id(uuid);

        String account = Global.getValue("sms_account");// 用户名（必填）
        String secret = Global.getValue("sms_secret");// 密码（必填）
        TcwindReqLog tcwindReqLog = new TcwindReqLog();
//        tcwindReqLog.setParams(JSONObject.toJSONString(riskAssmentReqParams));
        tcwindReqLog.setService(TCWindConstant.SERVICE_PREAUTH);
        tcwindReqLog.setCreateTime(DateTime.now().toDate());
        tcwindReqLog.setUserId(userId);
        String result;
//        TCWindClient client = getClient();
//        logger.info("天创大风策预授信接口请求开始；out_id:{}", riskAssmentReqParams.getOut_id());
        try {
//            result = client.CreditMoney(account, secret, riskAssmentReqParams);
            result = "";
        } catch (Exception e) {
//            logger.info("天创大风策预授信接口请求失败；out_id:{}", riskAssmentReqParams.getOut_id(), e);
            tcwindReqLog.setSuccess(false);
            tcwindReqLog.setRespParams(e.getMessage());
            tcwindReqLogMapper.save(tcwindReqLog);
            throw new SimpleMessageException("系统异常，请联系开发人员");
        }
        if (StringUtils.isEmpty(result)) {
            tcwindReqLog.setSuccess(false);
            tcwindReqLogMapper.save(tcwindReqLog);
            logger.info("天创大风策预授信接口返回数据为空；out_id:{}", uuid);
            throw new SimpleMessageException("系统异常，请联系开发人员");
        }
        tcwindReqLog.setRespParams(result);
        tcwindReqLog.setRespTime(DateTime.now().toDate());
        JSONObject json;
        try {
            json = JSONObject.parseObject(result);
        } catch (Exception e) {
            tcwindReqLogMapper.save(tcwindReqLog);
            logger.info("天创大风策预授信接口返回数据不为json格式；out_id:{}；result:{}", uuid, result);
            throw new SimpleMessageException("系统异常，请联系开发人员");
        }
        String code = json.getString("code");//状态码
        if (TCWindConstant.SUCCESS_CODE.equals(code)) {
            tcwindReqLog.setSuccess(true);
        } else {
            tcwindReqLog.setSuccess(false);
        }
        tcwindReqLogMapper.save(tcwindReqLog);

        if (tcwindReqLog.getSuccess()) {
            Double nowCredit = getAssessmentAmount(json);
            return modifyCredit(userId, nowCredit);
        } else {
            throw new SimpleMessageException("很抱歉，由于系统原因获取失败，请重新提交");
        }
    }

    @Override
    public String review(BorrowMain borrowMain, String mobileType) {
        //如果最后一条请求成功的记录在有效期内，则不再进行请求 @author yecy 20180207
        TcwindReqLog reqLog = getPreAuthReqLog(borrowMain.getUserId(), TCWindConstant.SERVICE_REVIEW);
        if (reqLog != null) {
            return getReviewResult(reqLog.getRespParams());
        }
//        RiskReviewReqParams reqParams = initTCRiskManageReqParams(borrowMain, mobileType);
        String account = Global.getValue("sms_account");// 用户名（必填）
        String secret = Global.getValue("sms_secret");// 密码（必填）
        TcwindReqLog tcwindReqLog = new TcwindReqLog();
//        tcwindReqLog.setParams(JSONObject.toJSONString(reqParams));
        tcwindReqLog.setService(TCWindConstant.SERVICE_REVIEW);
        tcwindReqLog.setCreateTime(DateTime.now().toDate());
        tcwindReqLog.setUserId(borrowMain.getUserId());
        String result;
//        logger.info("天创大风策审批接口请求开始；out_id:{}", reqParams.getOut_id());
        try {
//            TCWindClient client = getClient();
//            result = client.RightCreditMoney(account, secret, reqParams);
            result = "";
        } catch (Exception e) {
            tcwindReqLog.setSuccess(false);
            tcwindReqLog.setRespParams(e.getMessage());
            tcwindReqLogMapper.save(tcwindReqLog);
//            logger.error("【返回建议审核】天创大风策审批接口请求失败；out_id:{}", reqParams.getOut_id(), e);
            return BorrowRuleResult.RESULT_TYPE_REVIEW;
        }
        if (StringUtils.isEmpty(result)) {
            tcwindReqLog.setSuccess(false);
            tcwindReqLogMapper.save(tcwindReqLog);
//            logger.info("【返回建议审核】天创大风策审批接口返回数据为空；out_id:{}", reqParams.getOut_id());
            return BorrowRuleResult.RESULT_TYPE_REVIEW;
        }
        tcwindReqLog.setRespParams(result);
        tcwindReqLog.setRespTime(DateTime.now().toDate());
        JSONObject json;
        try {
            json = JSONObject.parseObject(result);
        } catch (Exception e) {
            tcwindReqLogMapper.save(tcwindReqLog);
//            logger.error("【返回建议审核】天创大风策预授信接口返回数据不为json格式；out_id:{}；result:{}", reqParams.getOut_id(), result);
            return BorrowRuleResult.RESULT_TYPE_REVIEW;
        }
        String code = json.getString("code");//状态码
        if (TCWindConstant.SUCCESS_CODE.equals(code)) {
            tcwindReqLog.setSuccess(true);
        } else {
            tcwindReqLog.setSuccess(false);
        }
        tcwindReqLogMapper.save(tcwindReqLog);
        if (tcwindReqLog.getSuccess()) {
            return getReviewResult(result);
        } else {
            logger.error("【返回建议审核】userId:{}审批接口访问失败", borrowMain.getUserId());
            return BorrowRuleResult.RESULT_TYPE_REVIEW;
        }
    }
    
    @Override
	public Double review(Long userId,String ip,String address,String mobileType,String loanReason) {
    	 //如果最后一条请求成功的记录在有效期内，则不再进行请求 @author yecy 20180207
    	logger.info("天创大风策审批接口请求:userId:{},ip:{},address:{},mobileType:{},loanReason:{}",new Object[]{userId,ip,address,mobileType,loanReason});
        TcwindReqLog reqLog = getPreAuthReqLog(userId, TCWindConstant.SERVICE_REVIEW);
        if (reqLog != null) {
        	Double nowCredit=getAssessmentAmount(JSONObject.parseObject(reqLog.getRespParams()));
            return modifyCredit(userId, nowCredit);
        }
//        RiskReviewReqParams reqParams = initTCRiskManageReqParams(userId, ip, address, mobileType, loanReason);
        String account = Global.getValue("sms_account");// 用户名（必填）
        String secret = Global.getValue("sms_secret");// 密码（必填）
        TcwindReqLog tcwindReqLog = new TcwindReqLog();
//        tcwindReqLog.setParams(JSONObject.toJSONString(reqParams));
        tcwindReqLog.setService(TCWindConstant.SERVICE_REVIEW);
        tcwindReqLog.setCreateTime(DateTime.now().toDate());
        tcwindReqLog.setUserId(userId);
        String result;
//        logger.info("天创大风策审批接口请求开始；out_id:{}", reqParams.getOut_id());
        try {
//            TCWindClient client = getClient();
//            result = client.RightCreditMoney(account, secret, reqParams);
            result = "";
        } catch (Exception e) {
            tcwindReqLog.setSuccess(false);
            tcwindReqLog.setRespParams(e.getMessage());
            tcwindReqLogMapper.save(tcwindReqLog);
            modifyCredit(userId, 0.d);
//            logger.error("天创大风策审批接口请求失败；out_id:{}", reqParams.getOut_id(), e);
            throw new SimpleMessageException("很抱歉，由于系统原因获取失败，请重新提交");
        }
        if (StringUtils.isEmpty(result)) {
            tcwindReqLog.setSuccess(false);
            tcwindReqLogMapper.save(tcwindReqLog);
            modifyCredit(userId, 0.d);
//            logger.info("天创大风策审批接口返回数据为空；out_id:{}", reqParams.getOut_id());
            throw new SimpleMessageException("很抱歉，天创大风策审批接口返回数据为空");
        }
        tcwindReqLog.setRespParams(result);
        tcwindReqLog.setRespTime(DateTime.now().toDate());
        JSONObject json;
        try {
            json = JSONObject.parseObject(result);
        } catch (Exception e) {
        	tcwindReqLog.setSuccess(false);
            tcwindReqLogMapper.save(tcwindReqLog);
            modifyCredit(userId, 0.d);
//            logger.error("天创大风策预授信接口返回数据不为json格式；out_id:{}；result:{}", reqParams.getOut_id(), result);
            throw new SimpleMessageException("很抱歉，天创大风策预授信接口返回数据不为json格式");
        }
        String code = json.getString("code");//状态码
        tcwindReqLog.setSuccess(TCWindConstant.SUCCESS_CODE.equals(code));
        logger.info("data:{}.",new Object[]{JSONObject.toJSONString(tcwindReqLog)});
        tcwindReqLogMapper.save(tcwindReqLog);
        if (tcwindReqLog.getSuccess()) {
            Double nowCredit = getAssessmentAmount(json);
            return modifyCredit(userId, nowCredit) ;
        } else {
        	modifyCredit(userId, 0.d);
            throw new SimpleMessageException("很抱歉，由于系统原因获取失败，请重新提交");
        }
	}

//    private TCWindClient getClient() {
//        String masterUrl = Global.getValue("sms_masterUrl");
//        try {
//            return new TCWindClient(masterUrl);
//        } catch (URIException e) {
//            logger.error("创建客户端请求异常，url为{}", masterUrl, e);
//            throw new SimpleMessageException("系统参数异常，请联系开发人员");
//        }
//    }

    private TcwindReqLog getPreAuthReqLog(Long userId, String service) {
        DateTime now = DateTime.now();
        int interval;
        if (TCWindConstant.SERVICE_PREAUTH.equals(service)) {
            interval = Global.getInt(TCWindConstant.TCWIND_REQ_AUTH_INTERVAL);
        } else if (TCWindConstant.SERVICE_REVIEW.equals(service)) {
            interval = Global.getInt(TCWindConstant.TCWIND_REQ_REVIEW_INTERVAL);
        } else {
            interval = 0;
        }
        DateTime lastTime = now.minusDays(interval);
        Map<String, Object> searchParams = new HashMap<>();
        searchParams.put("lastTime", lastTime.toString(DateUtil.YYYY_MM_DD_HH_MM_SS));
        searchParams.put("userId", userId);
        searchParams.put("service", service);
        //如果最后一条请求记录在有效期内，则不再进行请求
        return tcwindReqLogMapper.findLastSuccessOne(searchParams);
    }

    private Double getAssessmentAmount(JSONObject json) {
        Double reviewAmount;
        try {
            JSONObject data = json.getJSONObject("data");
            JSONObject amtCl = data.getJSONObject("amt_cl");
            reviewAmount = amtCl.getDouble("value");
        } catch (Exception e) {
            logger.error("授信额度解析失败,resp:{}", json.toJSONString());
            return 0d;
//            throw new SimpleMessageException("Please try again later.");
        }
        //用户可借额度 必须是1000的倍数；且额度不低于最低额度，不高于最高额度
        List<BorrowTemplate> templates = borrowTemplateMapper.findSelectiveOrderByTimeLimit(new HashMap<String, Object>());
        if (CollectionUtils.isNotEmpty(templates)) {
            BorrowTemplate minTemp = templates.get(0);
            String minRule = minTemp.getBorrowRule();
            if (StringUtils.isNotEmpty(minRule)) {
                JSONObject rule = JSONObject.parseObject(minRule);
                Double minAmount = rule.getDouble("minAmount");
                if (minAmount != null && reviewAmount < minAmount) {
                    logger.info("借款金额{}小于最少分期模板中的最小金额{}，所以将可借金额置为0", reviewAmount, minAmount);
                    reviewAmount = 0d;
                }
                Long interval = rule.getLong("interval");
                if (interval != null && Math.abs(reviewAmount) % interval != 0) {
                    logger.info("借款金额不是{}的倍数", reviewAmount, interval);
                    Long temp = reviewAmount.longValue() / interval * interval;
                    reviewAmount = temp.doubleValue();
                }
            }

            BorrowTemplate maxTemp = templates.get(templates.size() - 1);
            String maxRule = maxTemp.getBorrowRule();
            if (StringUtils.isNotEmpty(maxRule)) {
                JSONObject rule = JSONObject.parseObject(minRule);
                Double maxAmount = rule.getDouble("maxAmount");
                if (maxAmount != null && reviewAmount > maxAmount) {
                    logger.info("借款金额{}大于最多分期模板中的最大金额{}，所以将可借金额设为模板中的金额", reviewAmount, maxAmount);
                    reviewAmount = maxAmount;
                }
                Long interval = rule.getLong("interval");
                if (interval != null && Math.abs(reviewAmount) % interval != 0) {
                    logger.info("借款金额不是{}的倍数", reviewAmount, interval);
                    Long temp = reviewAmount.longValue() / interval * interval;
                    reviewAmount = temp.doubleValue();
                }
            }
        }

        return reviewAmount;
    }


    private String getReviewResult(String respParams) {
        String auditValue;
        try {
            JSONObject json = JSONObject.parseObject(respParams);
            JSONObject data = json.getJSONObject("data");
            JSONObject audit = data.getJSONObject("res_audit");
            auditValue = audit.getString("value");
        } catch (Exception e) {
            logger.error("【返回建议审核】审批接口返回解析失败,resp:{}", respParams);
            return BorrowRuleResult.RESULT_TYPE_REVIEW;
        }
        for (TCRiskManage tcRiskManage : TCRiskManage.values()) {
            if (tcRiskManage.getRiskManage().equals(auditValue)) {
                return changeTCRiskManageToBorrowResultType(tcRiskManage);
            }
        }
        logger.error("【返回建议审核】枚举值不存在，返回的审核建议值：{}", auditValue);
        return BorrowRuleResult.RESULT_TYPE_REVIEW;
    }

    private Double getReviewAmount(String respParams) {
        Double defaultAmount=0d;
        try {
            JSONObject json = JSONObject.parseObject(respParams);
            JSONObject data = json.getJSONObject("data");
            JSONObject audit = data.getJSONObject("res_audit");
            String auditValue = audit.getString("value");
            if(TCRiskManage.ACCEPT.getRiskManage().equals(auditValue)){
            	JSONObject amtObj = data.getJSONObject("amt_cl"); //data.amt_cl-用户授信额度
            	String amount=amtObj.getString("value");
            	defaultAmount=StringUtils.isBlank(amount)?defaultAmount:Double.parseDouble(amount);
            }
        } catch (Exception e) {
            logger.error("【返回建议审核】审批接口返回解析失败,resp:{}", respParams);
        }
        return defaultAmount;
    }
    
    /**
     * 将TCRiskManage结果转成BorrowResultType结果
     * */
    private String changeTCRiskManageToBorrowResultType(TCRiskManage tcRiskManage){
        if(TCRiskManage.ACCEPT.getRiskManage().equals(tcRiskManage.getRiskManage())){
            return BorrowRuleResult.RESULT_TYPE_PASS;
        }
        if(TCRiskManage.REVIEW.getRiskManage().equals(tcRiskManage.getRiskManage())){
            return BorrowRuleResult.RESULT_TYPE_REVIEW;
        }
        return BorrowRuleResult.RESULT_TYPE_REFUSED;
    }
    
    private Double modifyCredit(Long userId, Double nowCredit) {

        // 修改用户额度表
        Map<String, Object> creditMap = new HashMap<>();
        creditMap.put("consumerNo", userId.toString());
        Credit credit = creditMapper.findSelective(creditMap);
        //用户信用额度信息不存在
        if (null == credit) {
            logger.error("userId:{}用户信用额度信息不存在", userId);
            throw new SimpleMessageException("用户信用额度信息不存在");
        }

        Double used = credit.getUsed();
        if (used != null && used.doubleValue() != 0) {
            logger.error("userId:{}用户已使用额度不为0，不能修改额度", userId);
            throw new SimpleMessageException("用户信用额度异常，请联系客服人员");
        }
        Double pre = credit.getTotal();
        double modify = BigDecimalUtil.sub(nowCredit, pre);
        if (modify == 0d) {
            logger.info("userId:{}用户额度未修改，直接返回", userId);
            return nowCredit;
        }

        CreditLog creditLog = new CreditLog();
        creditLog.setConsumerNo(credit.getConsumerNo());
        creditLog.setPre(pre);
        creditLog.setNow(nowCredit);

        if (modify > 0) {
            // 10-增加 20-减少 30-冻结 40-解冻
            creditLog.setType("10");
        } else {
            creditLog.setType("20");
        }
        creditLog.setCreditType(credit.getCreditType());
        creditLog.setModifyTotal(Math.abs(modify));
        creditLog.setModifyUser("TCWind");
        creditLog.setModifyTime(DateTime.now().toDate());
        creditLog.setRemark("天创大风策预授信额度评定，用户额度修改");
        creditLogMapper.save(creditLog);
        credit.setTotal(nowCredit);
        credit.setUnuse(nowCredit);
        creditMapper.update(credit);
        return nowCredit;
    }

    /**
     * 拼装saas风控请求参数
     * 直接从TianChuangService类中拷贝，增加了zhima_score,prod_code,account_info三个参数
     *
     * @param borrow     借款订单
     * @param mobileType
     */
//    private RiskReviewReqParams initTCRiskManageReqParams(BorrowMain borrow, String mobileType) {
//        Long userId = borrow.getUserId();
//        Map<String, Object> paramMap = new HashMap<>();
//        paramMap.put("userId", userId);
//
//        RiskReviewReqParams riskReviewReqParams = new RiskReviewReqParams();
//        UserBaseInfo info = userBaseInfoMapper.findByUserId(userId);
//        riskReviewReqParams.setOut_id(borrow.getId().toString());
//        riskReviewReqParams.setAge(info.getAge().toString()); // 年龄
//        riskReviewReqParams.setApply_addr(borrow.getAddress()); // 申请地址(移动应用为GPS地址)
//        riskReviewReqParams.setApply_money(borrow.getAmount().toString()); // 申请额度
//        riskReviewReqParams.setApply_source("网络申请"); // 申请渠道
//        riskReviewReqParams.setApply_time(DateTime.now().toString("yyyy-MM-dd HH:mm:ss")); // 申请时间
//        BankCard card = bankCardMapper.findSelective(paramMap);
//        if (card != null) {
//            riskReviewReqParams.setBankcard_num(card.getCardNo()); // 银行卡号
//        }
//        riskReviewReqParams.setBiz_addr(info.getCompanyAddr()); // 公司地址
//        riskReviewReqParams.setBiz_addr_longitude_latitude(info.getCompanyCoordinate()); // 公司坐标
//        riskReviewReqParams.setBiz_company(info.getCompanyName()); // 公司名称
//        riskReviewReqParams.setBiz_industry(""); // 借款人公司所属行业
//        riskReviewReqParams.setBiz_positon(""); // 借款人公司职位
//        riskReviewReqParams.setBiz_type(""); // 公司性质
//        //联系人信息
//        List<UserEmerContacts> contacts = userEmerContactsMapper.listSelective(paramMap);
//        if (contacts != null) {
//            for (int i = 0; i < contacts.size(); i++) {
//                UserEmerContacts c = contacts.get(i);
//                if (i == 0) {
//                    riskReviewReqParams.setContacts1_addr(""); // 第一联系人地址
//                    riskReviewReqParams.setContacts1_name(c.getName()); // 第一联系人姓名
//                    riskReviewReqParams.setContacts1_num(c.getPhone()); // 第一联系人手机号码
//                    riskReviewReqParams.setContacts1_relation(c.getRelation()); // 与第一联系人关系
//                } else if (i == 1) {
//                    riskReviewReqParams.setContacts2_name(c.getName()); // 第二联系人姓名
//                    riskReviewReqParams.setContacts2_num(c.getName()); // 第二联系人手机号码
//                    riskReviewReqParams.setContacts2_relation(c.getRelation()); // 与第一联系人关系
//                } else {
//                    break;
//                }
//            }
//        }
//        riskReviewReqParams.setEducation_level(dealEducation(info.getEducation())); // 学历
//        UserOtherInfo otherInfo = userOtherInfoMapper.findSelective(paramMap);
//        if (otherInfo != null) {
//            riskReviewReqParams.setEmail(otherInfo.getEmail()); // 常用邮箱
//        }
//        riskReviewReqParams.setGender(info.getSex()); // 学历
//        riskReviewReqParams.setHome_addr(info.getLiveAddr() + info.getLiveDetailAddr()); // 家庭地址
//        riskReviewReqParams.setHome_addr_longitude_latitude(info.getLiveCoordinate()); // 家庭地址经纬度
//        riskReviewReqParams.setHouse_type(""); // 住房性质
//        riskReviewReqParams.setIdcard(info.getIdNo());
//        riskReviewReqParams.setImsi("");
//        riskReviewReqParams.setIp(borrow.getIp()); // IP地址
//        //贷款原因填充为借款用途 @author yecy 20180209
//        Integer borrowType = borrow.getBorrowType();
//        SysDictDetail dictDetail = sysDictDetailMapper.findDetail(null == borrowType ? "null" : borrowType.toString(), "BORROW_TYPE");
//        if (dictDetail != null) {
//            riskReviewReqParams.setLoan_reason(dictDetail.getItemValue()); // 贷款原因
//        }
//        UserEquipmentInfo equipmentInfo = userEquipmentInfoMapper.findSelective(paramMap);
//        if (equipmentInfo != null) {
//            riskReviewReqParams.setImei(equipmentInfo.getPhoneMark()); // IMEI号(移动应用)
//            riskReviewReqParams.setMac(equipmentInfo.getMac()); // mac
//            riskReviewReqParams.setMobile_type(equipmentInfo.getPhoneType()); // 手机品牌(移动应用)
//        }
//        riskReviewReqParams.setMarriage(info.getMarryState()); // 婚姻状况    数据库没有数据
//        riskReviewReqParams.setMobile(info.getPhone()); // 手机号
//        riskReviewReqParams.setName(info.getRealName()); // 姓名
//        String operating_system = "others";
//        switch (mobileType) {
//            case "1":
//                operating_system = "ios";
//                break;
//            case "2":
//                operating_system = "android";
//                break;
//            default:
//                break;
//        }
//        riskReviewReqParams.setOperating_system(operating_system); // 终端操作系统类型
//        riskReviewReqParams.setOth_addr(""); // 其他地址
//        riskReviewReqParams.setPer_addr(info.getAddrCard()); // 户籍地址
//        riskReviewReqParams.setPostalcode(""); // 邮政编码
//        riskReviewReqParams.setProduct_type("现金分期"); // 申请产品的类型
//        //分期贷款，所以还款期数非固定的1 @author yecy 20180209
//        String templateInfo = borrow.getTemplateInfo();
//        BorrowTemplateModel template = JSONObject.parseObject(templateInfo, BorrowTemplateModel.class);
//        riskReviewReqParams.setRefund_periods(template.getStage().toString()); // 还款期数
//        riskReviewReqParams.setReg_addr(info.getRegisterAddr()); // 注册地址
//        riskReviewReqParams.setReg_addr_longitude_latitude(info.getRegisterCoordinate()); // 注册地址坐标
//        riskReviewReqParams.setTel_biz(info.getCompanyPhone()); // 公司座机号
//        riskReviewReqParams.setTel_home(""); // 家庭座机号
//        riskReviewReqParams.setWorking_seniority(info.getWorkingYears()); // 工作年限
//        riskReviewReqParams.setYearly_income(info.getSalary()); // 年收入
//
//        //设置天创添加新参数
//        String orderId = tcOrderLogMapper.findNewOrderIdByUserId(userId);
//        Map<String, Object> oerderMap = new HashMap<>();
//        oerderMap.put("orderId", orderId);
//        List<CallingRecord> callreclist = tCCallingRecordMapper.selectByOrderIdRecord(oerderMap);
//        JSONArray callrecarray = new JSONArray();
//        for (CallingRecord callrec : callreclist) {
//            JSONObject callrecjson = new JSONObject();
//            callrecjson.put("tel_num", callrec.getCallingNumber());//对方号码
//            //是否接通。1：已接通；2.未接通
//            if (callrec.getTalkTime() != null && !callrec.getTalkTime().equals("") && !callrec.getTalkTime().equals("0")) {
//                callrecjson.put("connected", "已接通");//是否接通
//            } else {
//                callrecjson.put("connected", "未接通");
//            }
//            callrecjson.put("direction", callrec.getCallingType());//通话方向主叫被叫
//            callrecjson.put("duration", callrec.getTalkTime());//通话时长
//            callrecjson.put("hpn_tm", callrec.getHoldingTime());//通话发生时间
//            callrecarray.add(callrecjson);
//        }
//        riskReviewReqParams.setCallrec_list(callrecarray.toString());//通话记录详单
//
//        List<MessageBill> bills = tCMessageBillMapper.selectByOrderIdRecord(oerderMap);
//        JSONArray messbillarray = new JSONArray();
//        for (MessageBill mess : bills) {
//            JSONObject smsrecjson = new JSONObject();
//            smsrecjson.put("tel_num", mess.getOtherNum());
//            smsrecjson.put("direction", mess.getSmsSaveType());
//            smsrecjson.put("content", mess.getBusiName());
//            smsrecjson.put("hpn_tm", mess.getSmsTime());
//            messbillarray.add(smsrecjson);
//        }
//        riskReviewReqParams.setSms_list(messbillarray.toString());//短信记录详单
//
//
//        // 分表
//        String tableName = ShardTableUtil.generateTableNameById("cl_user_contacts", userId, 30000);
//        int countTable = userContactsMapper.countTable(tableName);
//        if (countTable == 0) {
//            userContactsMapper.createTable(tableName);
//        }
//
//        Map<String, Object> params = new HashMap<>();
//        params.put("userId", userId);
//        List<UserContacts> contactlist = userContactsMapper.listShardSelective(tableName, params);
//        JSONArray contactarray = new JSONArray();
//        for (UserContacts usercontact : contactlist) {
//            JSONObject contactjson = new JSONObject();
//            contactjson.put("name", usercontact.getName());
//            contactjson.put("tel_name", usercontact.getPhone());
//            contactarray.add(contactjson);
//        }
//        riskReviewReqParams.setContacts_list(contactarray.toString());//联系人详单
//
//        //@1.先查询-记录是否存在
//        String tableName1 = ShardTableUtil.getTableNameByParam(userId, TableDataEnum.TABLE_DATA.CL_USER_APPS);
//        List<UserApps> userapplist = userAppsMapper.listSelective(tableName1, params);
//        StringBuilder app_list = new StringBuilder();
//        Date app_create_time = new Date();
//        if (CollectionUtils.isNotEmpty(userapplist)) {
//            if (userapplist.get(0).getGmtCreateTime() != null) {
//                app_create_time = userapplist.get(0).getGmtCreateTime();
//            }
//
//            for (UserApps userapp : userapplist) {
//                app_list.append(userapp.getAppName());
//            }
//        }
//
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//        riskReviewReqParams.setCrawl_app_create_time(sdf.format(app_create_time));//app爬取时间
//        riskReviewReqParams.setApp_list(app_list.toString());//app名单
//
//        //新增的请求参数 @author yecy 20180209
//        Zhima zhima = zhimaMapper.findSelective(paramMap);
//        if (zhima != null) {
//            riskReviewReqParams.setZhima_score(zhima.getScore().toString());
//        }
//        riskReviewReqParams.setAccount_info(getAccountInfo(userId));
//        riskReviewReqParams.setProd_code("");
//
//        return riskReviewReqParams;
//    }

    /**
     * 拼装saas风控请求参数
     * 直接从TianChuangService类中拷贝，增加了zhima_score,prod_code,account_info三个参数
     * @param userId 用户id
     */
//    private RiskReviewReqParams initTCRiskManageReqParams(Long userId,String ip,String address, String mobileType,String loanReason) {
//        Map<String, Object> paramMap = new HashMap<>();
//        paramMap.put("userId", userId);
//        RiskReviewReqParams riskReviewReqParams = new RiskReviewReqParams();
//        UserBaseInfo info = userBaseInfoMapper.findByUserId(userId);
//        riskReviewReqParams.setOut_id(OrderNoUtil.geUUIDOrderNo());//TODO  borrow.getId().toString()
//        riskReviewReqParams.setAge(info.getAge().toString()); // 年龄
//        riskReviewReqParams.setApply_addr(address); // 申请地址(移动应用为GPS地址)
////        riskReviewReqParams.setApply_money(borrow.getAmount().toString()); // 申请额度 (非必填)
//        riskReviewReqParams.setApply_source("网络申请"); // 申请渠道
//        riskReviewReqParams.setApply_time(DateTime.now().toString("yyyy-MM-dd HH:mm:ss")); // 申请时间
//        BankCard card = bankCardMapper.findSelective(paramMap);
//        if (card != null) {
//            riskReviewReqParams.setBankcard_num(card.getCardNo()); // 银行卡号
//        }
//        riskReviewReqParams.setBiz_addr(info.getCompanyAddr()); // 公司地址
//        riskReviewReqParams.setBiz_addr_longitude_latitude(info.getCompanyCoordinate()); // 公司坐标
//        riskReviewReqParams.setBiz_company(info.getCompanyName()); // 公司名称
//        riskReviewReqParams.setBiz_industry(""); // 借款人公司所属行业
//        riskReviewReqParams.setBiz_positon(""); // 借款人公司职位
//        riskReviewReqParams.setBiz_type(""); // 公司性质
//        //联系人信息
//        List<UserEmerContacts> contacts = userEmerContactsMapper.listSelective(paramMap);
//        if (contacts != null) {
//            for (int i = 0; i < contacts.size(); i++) {
//                UserEmerContacts c = contacts.get(i);
//                if (i == 0) {
//                    riskReviewReqParams.setContacts1_addr(""); // 第一联系人地址
//                    riskReviewReqParams.setContacts1_name(c.getName()); // 第一联系人姓名
//                    riskReviewReqParams.setContacts1_num(c.getPhone()); // 第一联系人手机号码
//                    riskReviewReqParams.setContacts1_relation(c.getRelation()); // 与第一联系人关系
//                } else if (i == 1) {
//                    riskReviewReqParams.setContacts2_name(c.getName()); // 第二联系人姓名
//                    riskReviewReqParams.setContacts2_num(c.getName()); // 第二联系人手机号码
//                    riskReviewReqParams.setContacts2_relation(c.getRelation()); // 与第一联系人关系
//                } else {
//                    break;
//                }
//            }
//        }
//        riskReviewReqParams.setEducation_level(dealEducation(info.getEducation())); // 学历
//        UserOtherInfo otherInfo = userOtherInfoMapper.findSelective(paramMap);
//        if (otherInfo != null) {
//            riskReviewReqParams.setEmail(otherInfo.getEmail()); // 常用邮箱
//        }
//        riskReviewReqParams.setGender(info.getSex()); // 学历
//        riskReviewReqParams.setHome_addr(info.getLiveAddr() + info.getLiveDetailAddr()); // 家庭地址
//        riskReviewReqParams.setHome_addr_longitude_latitude(info.getLiveCoordinate()); // 家庭地址经纬度
//        riskReviewReqParams.setHouse_type(""); // 住房性质
//        riskReviewReqParams.setIdcard(info.getIdNo());
//        riskReviewReqParams.setImsi("");
//        riskReviewReqParams.setIp(ip); // IP地址
//        riskReviewReqParams.setLoan_reason(loanReason); // 贷款原因-默认为租房贷款
//        UserEquipmentInfo equipmentInfo = userEquipmentInfoMapper.findSelective(paramMap);
//        if (equipmentInfo != null) {
//            riskReviewReqParams.setImei(equipmentInfo.getPhoneMark()); // IMEI号(移动应用)
//            riskReviewReqParams.setMac(equipmentInfo.getMac()); // mac
//            riskReviewReqParams.setMobile_type(equipmentInfo.getPhoneType()); // 手机品牌(移动应用)
//        }
//        riskReviewReqParams.setMarriage(info.getMarryState()); // 婚姻状况    数据库没有数据
//        riskReviewReqParams.setMobile(info.getPhone()); // 手机号
//        riskReviewReqParams.setName(info.getRealName()); // 姓名
//        String operating_system = "others";
//        switch (mobileType) {
//            case "1":
//                operating_system = "ios";
//                break;
//            case "2":
//                operating_system = "android";
//                break;
//            default:
//                break;
//        }
//        riskReviewReqParams.setOperating_system(operating_system); // 终端操作系统类型
//        riskReviewReqParams.setOth_addr(""); // 其他地址
//        riskReviewReqParams.setPer_addr(info.getAddrCard()); // 户籍地址
//        riskReviewReqParams.setPostalcode(""); // 邮政编码
//        riskReviewReqParams.setProduct_type("现金分期"); // 申请产品的类型
//        riskReviewReqParams.setRefund_periods(Global.getValue(SysConfigConstant.DEFAULT_REPAYMENT_PERIOD)); // 还款期数-默认
//        riskReviewReqParams.setReg_addr(info.getRegisterAddr()); // 注册地址
//        riskReviewReqParams.setReg_addr_longitude_latitude(info.getRegisterCoordinate()); // 注册地址坐标
//        riskReviewReqParams.setTel_biz(info.getCompanyPhone()); // 公司座机号
//        riskReviewReqParams.setTel_home(""); // 家庭座机号
//        riskReviewReqParams.setWorking_seniority(info.getWorkingYears()); // 工作年限
//        riskReviewReqParams.setYearly_income(info.getSalary()); // 年收入
//        //设置天创添加新参数
//        String orderId = tcOrderLogMapper.findNewOrderIdByUserId(userId);
//        Map<String, Object> oerderMap = new HashMap<>();
//        oerderMap.put("orderId", orderId);
//        List<CallingRecord> callreclist = tCCallingRecordMapper.selectByOrderIdRecord(oerderMap);
//        JSONArray callrecarray = new JSONArray();
//        for (CallingRecord callrec : callreclist) {
//            JSONObject callrecjson = new JSONObject();
//            callrecjson.put("tel_num", callrec.getCallingNumber());//对方号码
//            //是否接通。1：已接通；2.未接通
//            if (callrec.getTalkTime() != null && !callrec.getTalkTime().equals("") && !callrec.getTalkTime().equals("0")) {
//                callrecjson.put("connected", "已接通");//是否接通
//            } else {
//                callrecjson.put("connected", "未接通");
//            }
//            callrecjson.put("direction", callrec.getCallingType());//通话方向主叫被叫
//            callrecjson.put("duration", callrec.getTalkTime());//通话时长
//            callrecjson.put("hpn_tm", callrec.getHoldingTime());//通话发生时间
//            callrecarray.add(callrecjson);
//        }
//        riskReviewReqParams.setCallrec_list(callrecarray.toString());//通话记录详单
//        List<MessageBill> bills = tCMessageBillMapper.selectByOrderIdRecord(oerderMap);
//        JSONArray messbillarray = new JSONArray();
//        for (MessageBill mess : bills) {
//            JSONObject smsrecjson = new JSONObject();
//            smsrecjson.put("tel_num", mess.getOtherNum());
//            smsrecjson.put("direction", mess.getSmsSaveType());
//            smsrecjson.put("content", mess.getBusiName());
//            smsrecjson.put("hpn_tm", mess.getSmsTime());
//            messbillarray.add(smsrecjson);
//        }
//        riskReviewReqParams.setSms_list(messbillarray.toString());//短信记录详单
//        // 分表
//        String tableName = ShardTableUtil.generateTableNameById("cl_user_contacts", userId, 30000);
//        int countTable = userContactsMapper.countTable(tableName);
//        if (countTable == 0) {
//            userContactsMapper.createTable(tableName);
//        }
//        Map<String, Object> params = new HashMap<>();
//        params.put("userId", userId);
//        List<UserContacts> contactlist = userContactsMapper.listShardSelective(tableName, params);
//        JSONArray contactarray = new JSONArray();
//        for (UserContacts usercontact : contactlist) {
//            JSONObject contactjson = new JSONObject();
//            contactjson.put("name", usercontact.getName());
//            contactjson.put("tel_name", usercontact.getPhone());
//            contactarray.add(contactjson);
//        }
//        riskReviewReqParams.setContacts_list(contactarray.toString());//联系人详单
//        //@1.先查询-记录是否存在
//        String tableName1 = ShardTableUtil.getTableNameByParam(userId, TableDataEnum.TABLE_DATA.CL_USER_APPS);
//        List<UserApps> userapplist = userAppsMapper.listSelective(tableName1, params);
//        StringBuilder app_list = new StringBuilder();
//        Date app_create_time = new Date();
//        if (CollectionUtils.isNotEmpty(userapplist)) {
//            if (userapplist.get(0).getGmtCreateTime() != null) {
//                app_create_time = userapplist.get(0).getGmtCreateTime();
//            }
//            for (UserApps userapp : userapplist) {
//                app_list.append(userapp.getAppName());
//            }
//        }
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        riskReviewReqParams.setCrawl_app_create_time(sdf.format(app_create_time));//app爬取时间
//        riskReviewReqParams.setApp_list(app_list.toString());//app名单
//        //新增的请求参数 @author yecy 20180209
//        Zhima zhima = zhimaMapper.findSelective(paramMap);
//        if (zhima != null) {
//            riskReviewReqParams.setZhima_score(zhima.getScore().toString());
//        }
//        riskReviewReqParams.setAccount_info(getAccountInfo(userId));
//        riskReviewReqParams.setProd_code("");
//        return riskReviewReqParams;
//    }
    
    private String getAccountInfo(Long userId) {
        JSONObject accountInfo = new JSONObject();
        // 运营商
        String orderId = tcOrderLogMapper.findNewOrderIdByUserId(userId);
        if (StringUtils.isNotEmpty(orderId)) {
            accountInfo.put(TCWindConstant.OPERATOR_TID, orderId);
        }

        // 网银账单
        UserBankBillResp billResp = userBankBillRespMapper.findLastBillRespByUserId(userId);
        if (billResp != null) {
            accountInfo.put(TCWindConstant.ONLINE_BANK_TID, billResp.getResId());
        }

        // 社保
        String shebaoTaskId = saasRespRecordMapper.findLastSuccessTaskId(SaasServiceEnum.TYPE.SHE_BAO.getKey(), userId);
        if (shebaoTaskId != null) {
            accountInfo.put(TCWindConstant.SOCIAL_SECURITY_TID, shebaoTaskId);
        }

        // 公积金
        String gongjijinTaskId = saasRespRecordMapper.findLastSuccessTaskId(SaasServiceEnum.TYPE.GONG_JI_JIN.getKey(), userId);
        if (gongjijinTaskId != null) {
            accountInfo.put(TCWindConstant.HOUSING_FUND_TID, gongjijinTaskId);
        }

        if (accountInfo.isEmpty()) {
            logger.error("未获取到用户的账户信息，无法请求大风策接口。userId{}", userId);
            throw new SimpleMessageException("您的资料未认证完善，请前往认证后再来。");
        }
        return accountInfo.toJSONString();
    }

    /**
     * 对学历 数据进行处理  符合 saas风控接口需求
     *
     * @param education
     * @return
     */
    private String dealEducation(String education) {
        if (StringUtils.isEmpty(education)) {
            return "其他";
        }
        if (education.contains("博士")) {
            return "博士";
        } else if (education.contains("硕士")) {
            return "硕士";
        } else if (education.contains("大专")) {
            return "大专";
        } else if (education.contains("高中") || education.contains("初中")) {
            return "高中及以下";
        } else {
            return "其他";
        }
    }

}
