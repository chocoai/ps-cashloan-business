package com.adpanshi.cashloan.business.cl.facade.impl;

import com.adpanshi.cashloan.business.cl.domain.BorrowRepay;
import com.adpanshi.cashloan.business.cl.domain.PettyLoanScene;
import com.adpanshi.cashloan.business.cl.domain.UserAuth;
import com.adpanshi.cashloan.business.cl.enums.PettyloanSceneEnum;
import com.adpanshi.cashloan.business.cl.enums.UserAuthEnum;
import com.adpanshi.cashloan.business.cl.extra.BorrowIntent;
import com.adpanshi.cashloan.business.cl.extra.HandleBorrowIntent;
import com.adpanshi.cashloan.business.cl.facade.IndexFacade;
import com.adpanshi.cashloan.business.cl.mapper.BorrowMainModelMapper;
import com.adpanshi.cashloan.business.cl.mapper.BorrowRepayMapper;
import com.adpanshi.cashloan.business.cl.mapper.PettyLoanSceneMapper;
import com.adpanshi.cashloan.business.cl.model.*;
import com.adpanshi.cashloan.business.cl.service.BankCardService;
import com.adpanshi.cashloan.business.cl.service.UserAuthService;
import com.adpanshi.cashloan.business.cl.util.DateFormatUtil;
import com.adpanshi.cashloan.business.core.common.context.Global;
import com.adpanshi.cashloan.business.core.common.exception.BussinessException;
import com.adpanshi.cashloan.business.core.common.util.DateUtil;
import com.adpanshi.cashloan.business.core.common.util.EnumUtil;
import com.adpanshi.cashloan.business.core.common.util.NumberToCNUtil;
import com.adpanshi.cashloan.business.core.domain.Borrow;
import com.adpanshi.cashloan.business.core.domain.BorrowMain;
import com.adpanshi.cashloan.business.core.mapper.BorrowMainMapper;
import com.adpanshi.cashloan.business.core.model.BorrowModel;
import com.adpanshi.cashloan.business.core.umeng.beans.Extra;
import com.adpanshi.cashloan.business.cr.domain.Credit;
import com.adpanshi.cashloan.business.cr.service.CreditService;
import com.adpanshi.cashloan.business.rule.constant.TCWindConstant;
import com.adpanshi.cashloan.business.rule.domain.BankCard;
import com.adpanshi.cashloan.business.rule.domain.BorrowTemplate;
import com.adpanshi.cashloan.business.rule.domain.TcwindReqLog;
import com.adpanshi.cashloan.business.rule.enums.PayType;
import com.adpanshi.cashloan.business.rule.mapper.BorrowTemplateMapper;
import com.adpanshi.cashloan.business.rule.mapper.ClBorrowMapper;
import com.adpanshi.cashloan.business.rule.mapper.TcwindReqLogMapper;
import com.adpanshi.cashloan.business.rule.model.IndexModel;
import com.adpanshi.cashloan.business.rule.model.ScaleShowModel;
import com.adpanshi.cashloan.business.system.domain.SysDictDetail;
import com.adpanshi.cashloan.business.system.mapper.SysDictDetailMapper;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.TypeUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tool.util.BigDecimalUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

/**
 * @author yecy
 * @date 2017/12/16 11:36
 */
@Service
public class IndexFacadeImpl implements IndexFacade {

    private BorrowTemplateMapper borrowTemplateMapper;
    private BorrowMainModelMapper borrowMainModelMapper;
    private BorrowRepayMapper borrowRepayMapper;
    private SysDictDetailMapper sysDictDetailMapper;
    private BorrowMainMapper borrowMainMapper;
    private UserAuthService userAuthService;
    private BankCardService bankCardService;
    private CreditService creditService;
    private PettyLoanSceneMapper pettyLoanSceneMapper;
    private ClBorrowMapper clBorrowMapper;
    private TcwindReqLogMapper tcwindReqLogMapper;

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#0.00");
    private static final String DATE_FORMAT = "yyyy年MM月dd日";

    @Autowired
    public IndexFacadeImpl(BorrowTemplateMapper borrowTemplateMapper,
                           BorrowMainModelMapper borrowMainModelMapper, BorrowRepayMapper borrowRepayMapper,
                           SysDictDetailMapper sysDictDetailMapper, BorrowMainMapper borrowMainMapper,
                           UserAuthService userAuthService, BankCardService bankCardService, CreditService creditService,
                           PettyLoanSceneMapper pettyLoanSceneMapper, ClBorrowMapper clBorrowMapper,TcwindReqLogMapper tcwindReqLogMapper) {
        this.borrowTemplateMapper = borrowTemplateMapper;
        this.borrowMainModelMapper = borrowMainModelMapper;
        this.borrowRepayMapper = borrowRepayMapper;
        this.sysDictDetailMapper = sysDictDetailMapper;
        this.borrowMainMapper = borrowMainMapper;
        this.userAuthService = userAuthService;
        this.bankCardService = bankCardService;
        this.creditService = creditService;
        this.pettyLoanSceneMapper = pettyLoanSceneMapper;
        this.clBorrowMapper = clBorrowMapper;
        this.tcwindReqLogMapper=tcwindReqLogMapper;
    }

    @Override
    public IndexPageModel getIndex(Long userId) {
        IndexPageModel model = new IndexPageModel();
        String banner = Global.getValue("banner");
        if (StringUtils.isNotEmpty(banner)) {
            List<IndexPageModel.Banner> bannerList = JSONArray.parseArray(banner, IndexPageModel.Banner.class);
            model.setBanners(bannerList);
        }
        List<ScaleShowModel> scaleList = borrowTemplateMapper.getScaleList();
        model.setScales(scaleList);

        // 用于首页展示的最大值
        Double maxAmount = Global.getDouble("maxAmount");
        model.setMaxAmount(DECIMAL_FORMAT.format(maxAmount));
        Long defaultTimeLimit = scaleList.get(0).getTimeLimit();
        Map<String, String> result = getMinAmountAndInterval(defaultTimeLimit);
        model.setMinAmount(result.get("minAmount"));
        model.setInterval(result.get("interval"));
        model.setAskedQuestions(getAskedQuestions());
        if (userId != null) {
            List<BorrowMain> borrowMainList = borrowMainMapper.findUserUnFinishedBorrow(userId);
            if (CollectionUtils.isNotEmpty(borrowMainList)) {
                BorrowMain borrowMain = borrowMainList.get(0);
                Long mainId = borrowMain.getId();
                model.setBorrowMainId(mainId);
                model.setState(borrowMain.getState());

                // 判断是否存在逾期订单
                if (BorrowModel.STATE_REPAY.equals(borrowMain.getState())){
                    HashMap<String, Object> borrowParam = new HashMap<>();
                    borrowParam.put("borrowMainId",mainId);
                    borrowParam.put("state", BorrowModel.STATE_DELAY);
                    List<Borrow> borrows = clBorrowMapper.listSelective(borrowParam);
                    if (CollectionUtils.isNotEmpty(borrows)){
                        model.setState(BorrowModel.STATE_DELAY);
                        model.setPenaltyPeriods(borrows.size());
                    }
                }

                Map<String, Object> repayParams = new HashMap<>();
                repayParams.put("state", BorrowRepayModel.STATE_REPAY_NO);
                repayParams.put("userId", borrowMain.getUserId());
                List<BorrowRepay> repays = borrowRepayMapper.findUnRepay(repayParams);
                if (CollectionUtils.isNotEmpty(repays)) {
                    Date repayDate = repays.get(0).getRepayTime();
                    model.setRecentRepayDate(DateFormatUtil.formatDateYMD(repayDate));
                    Double penaltyAmount = repays.get(0).getPenaltyAmout();
                    Double repayAmount = repays.get(0).getAmount();
                    model.setRecentRepayAmount(DECIMAL_FORMAT.format(BigDecimalUtil.add(penaltyAmount, repayAmount)));

                    Double totalRepayAmount = 0d;
                    for (BorrowRepay repay : repays) {
                        Double penaltyAmountTemp = repay.getPenaltyAmout();
                        Double repayAmountTemp = repay.getAmount();
                        totalRepayAmount = BigDecimalUtil.add(penaltyAmountTemp, repayAmountTemp, totalRepayAmount);
                    }
                    model.setTotalRepayAmount(DECIMAL_FORMAT.format(totalRepayAmount));
                }
            }

            UserAuth userAuth = userAuthService.findSelective(userId);
            String tenementIncomeState = userAuth.getTenementIncomeState();
            if (UserAuthEnum.TENEMENT_INCOME_STATE.AUTHENTICATED.getCode().equals(tenementIncomeState)) {
                model.setAuthToTenement(true);
            } else {
                model.setAuthToTenement(false);
            }
            Map<String, Object> auth = userAuthService.getUserAuthWithConfigByUserId(userId);
            int intervalDay=getTcWindServiceDay(userId);
            model.setIntervalDay(intervalDay);
            if (MapUtils.isNotEmpty(auth)) {
                Integer authNum = MapUtils.getInteger(auth, "result", 0);
                model.setAuthNum(authNum);

                Integer authTotal = MapUtils.getInteger(auth, "total", 0);
                model.setAuthTotalNum(authTotal);

                Boolean qualified = MapUtils.getBoolean(auth, "qualified", false);
                Boolean choose = MapUtils.getBoolean(auth, "choose", false);
                model.setQualified(qualified);
                model.setChoose(choose);
                // 如果存在用户，且认证全部完成，则显示用户的真实额度 @author yecy 20180411
                if (qualified && choose){
                    Credit credit = creditService.findByConsumerNo(userId.toString());
                    if (credit != null){
                        model.setMaxAmount(credit.getUnuse().toString());
                        //credit.getTotal().toString()
                    }
                }
            } else {
                model.setAuthNum(0);
                model.setQualified(false);
                model.setChoose(false);
            }
        }

        return model;
    }

    /**
     * <p>判断距离上次调用大风策审核接口间隔多长时间</p>
     * @param userId
     * @return 
     * */
    public int getTcWindServiceDay(Long userId){
    	int defaultDay=0;
    	DateTime now = DateTime.now();
    	int interval = Global.getInt(TCWindConstant.TCWIND_REQ_REVIEW_INTERVAL);
    	DateTime lastTime = now.minusDays(interval);
        Map<String, Object> searchParams = new HashMap<>();
        searchParams.put("lastTime", lastTime.toString(DateUtil.YYYY_MM_DD_HH_MM_SS));
        searchParams.put("userId", userId);
        searchParams.put("com.adpanshi.cashloan.api.cr.service", TCWindConstant.SERVICE_REVIEW);
        //如果最后一条请求记录在有效期内，则不再进行请求
        TcwindReqLog tcwindReqLog= tcwindReqLogMapper.findLastSuccessOne(searchParams);
        if(null!=tcwindReqLog){
        	int day=DateUtil.daysBetween(tcwindReqLog.getRespTime(), new Date());
        	defaultDay=interval-day;
        }
        return defaultDay;
    }
    
    @Override
    public RepayShowModel getRepayPlan(Double amount, Integer timeLimit) {
        return getRepayPlan(amount, timeLimit, null);
    }

    @Override
    public RepayShowModel getRepayPlan(Double amount, Integer timeLimit, Long userId) {
        RepayShowModel repayShow = new RepayShowModel();

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("timeLimit", timeLimit);
        BorrowTemplate template = borrowTemplateMapper.findSelective(paramMap);
        Double fee;
        Integer cycle;
        Double realAmount = amount;
        if (template == null) {
            cycle = getIndexCycle();
            Double penalty = getPenalty();
            fee = BigDecimalUtil.mul(timeLimit, amount, penalty);
            repayShow.setPayType(PayType.ACPI.getName());
        } else {
            repayShow.setPayType(template.getPayType().getName());

            String detailStr = template.getFeeDetail();
            JSONObject oldDetail = JSONObject.parseObject(detailStr);
            fee = 0d;
            for (JSONObject.Entry<String, Object> fd : oldDetail.entrySet()) {
                Double dFeePer = TypeUtils.castToDouble(fd.getValue()); //费用百分比
                Double dFee = BigDecimalUtil.mul(dFeePer, amount);
                fee += dFee;
            }
            fee = BigDecimalUtil.mul(timeLimit, fee);
            cycle = Integer.parseInt(template.getCycle());
            Integer cutType = template.getCutType();

            if (userId != null) {
                List<BorrowRepay> borrowRepayList = borrowRepayMapper.findRepayWithUser(userId);
                if (CollectionUtils.isNotEmpty(borrowRepayList)) {
                    cutType >>= 1;
                }
                cutType &= 0x1;
                if (cutType.equals(1)) {
                    realAmount = BigDecimalUtil.sub(amount, fee);
                }
            }
        }

        repayShow.setRealAmount(DECIMAL_FORMAT.format(realAmount));
        repayShow.setAllFee(DECIMAL_FORMAT.format(fee));
        Integer repaySize = timeLimit / cycle;

        List<RepayDetailShowModel> repayList = createRepayList(amount, fee, timeLimit, repaySize);
        repayShow.setRepayDetail(repayList);
        return repayShow;
    }


    @Override
    public List<IndexModel> listIndexWithPhone() {
        List<IndexModel> indexModels = borrowMainModelMapper.listIndexWithPhone();
        for (IndexModel indexModel : indexModels) {
            String phone = indexModel.getPhone();
            Integer phoneLength = phone.length();
            phone = phone.substring(phoneLength - 4, phoneLength);

            indexModel.setPhone(phone);
        }
        return indexModels;
    }

    @Override
    public BorrowConfirmModel getConfirmBorrow(Long userId, Double maxAmount) {
        BorrowConfirmModel model = new BorrowConfirmModel();
        BankCard bankCard = bankCardService.getBankCardByUserId(userId);
        if (bankCard == null) {
            throw new BussinessException("userId" + userId + "为的用户，银行卡未认证。");
        }
        String bank = bankCard.getBank();
        model.setBank(bank);
        model.setCardNo(bankCard.getCardNo());
        model.setCardId(bankCard.getId());

        String serverHost = Global.getValue("server_host");
        StringBuilder icon = new StringBuilder(serverHost);
        if (!serverHost.endsWith("/")) {
            icon.append("/");
        }

        String bankCode = sysDictDetailMapper.getCodeByValue(bank, "BANK_TYPE");
        icon.append("static/images/bankicon/").append(bankCode).append(".png");

        model.setBankIcon(icon.toString());
        List<SysDictDetail> dictDetails = sysDictDetailMapper.listByTypeCode(new Extra("typeCode", "BORROW_TYPE"));
        List<BorrowIntent> borrowIntents = HandleBorrowIntent.handlDictDetail(dictDetails, userId);
        model.setBorrowIntents(borrowIntents);

        // 用户可借最大额度为用户信用额度（未加提额额度） @author yecy 20180205
        Credit credit = creditService.findByConsumerNo(userId.toString());
        model.setMaxAmount(DECIMAL_FORMAT.format(credit.getUnuse()));


        List<ScaleShowModel> scaleList = borrowTemplateMapper.getScaleList();
        model.setScales(scaleList);
        Long defaultTimeLimit = scaleList.get(0).getTimeLimit();
        Map<String, String> result = getMinAmountAndInterval(defaultTimeLimit);
        model.setMinAmount(result.get("minAmount"));
        model.setInterval(result.get("interval"));

        RepayShowModel defaultRepayDetail = getRepayPlan(maxAmount, defaultTimeLimit.intValue(), userId);
        BeanUtils.copyProperties(defaultRepayDetail, model);
        PettyLoanScene pettyLoanScene= pettyLoanSceneMapper.queryPettyLoanSceneByBeforBorrow(userId, EnumUtil.getKeysByClz(PettyloanSceneEnum.SCENE_TYPE.class));
        if(null!=pettyLoanScene){
        	model.setSceneUpload(true);
        }
        return model;
    }

    private List<RepayDetailShowModel> createRepayList(Double sumAmount, Double sumFee, Integer sumTimeLimit, Integer
            repaySize) {
        DateTime now = DateTime.now();
        List<RepayDetailShowModel> repayList = new ArrayList<>(repaySize);
        RepayDetailShowModel firstRepay = new RepayDetailShowModel();
        Integer otherSize = repaySize - 1;

        Double otherAmount = div(sumAmount, repaySize.doubleValue());
        Double amount = BigDecimalUtil.sub(sumAmount, BigDecimalUtil.mul(otherAmount, otherSize.doubleValue()));

        Double otherFee = div(sumFee, repaySize.doubleValue());
        Double fee = BigDecimalUtil.sub(sumFee, BigDecimalUtil.mul(otherFee, otherSize.doubleValue()));

        firstRepay.setAmount(DECIMAL_FORMAT.format(BigDecimalUtil.add(amount, fee)));
        firstRepay.setInterest(DECIMAL_FORMAT.format(fee));


        Integer otherTimeLimit = sumTimeLimit / repaySize;
        Integer timeLimit = sumTimeLimit - otherTimeLimit * otherSize;
        DateTime repayTime = now.plusDays(timeLimit - 1);
        firstRepay.setRepayTime(repayTime.toString(DATE_FORMAT));

        firstRepay.setTitle("第一期");
        repayList.add(firstRepay);

        for (int i = 1; i < repaySize; i++) {
            RepayDetailShowModel repay = new RepayDetailShowModel();
            repay.setInterest(DECIMAL_FORMAT.format(otherFee));
            repay.setAmount(DECIMAL_FORMAT.format(BigDecimalUtil.add(otherAmount, otherFee)));

            repayTime = repayTime.plusDays(otherTimeLimit);
            repay.setRepayTime(repayTime.toString(DATE_FORMAT));
            StringBuilder title = new StringBuilder();
            title.append("第").append(NumberToCNUtil.toHanStr(i + 1)).append("期");
            repay.setTitle(title.toString());
            repayList.add(repay);
        }
        return repayList;
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

    private int getIndexCycle() {
        int cycle = Global.getInt("index_cycle");
        if (cycle <= 0) {
            cycle = 7;
        }
        return cycle;
    }

    private double getPenalty() {
        double penalty = Global.getDouble("index_penalty");
        if (penalty <= 0) {
            penalty = 0.001;
        }
        return penalty;
    }

    private Map<String, String> getMinAmountAndInterval(Long timeLimit) {
        Map<String, String> result = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        map.put("timeLimit", timeLimit.toString());
        //模板中如果未设置，则最低金额设为5000，金额间隔设为1000
        Double minAmount = 5000.00;
        Long interval = 1000L;
        // 最小可借额度，和可借额度间隔，从借款模板中获取
        BorrowTemplate template = borrowTemplateMapper.findSelective(map);
        if (template != null) {
            String ruleStr = template.getBorrowRule();
            if (StringUtils.isNotEmpty(ruleStr)) {
                JSONObject rule = JSONObject.parseObject(ruleStr);
                Double tempMinAmount = rule.getDouble("minAmount");
                if (tempMinAmount != null) {
                    minAmount = tempMinAmount;
                }
                Long tempInterval = rule.getLong("interval");
                if (tempInterval != null) {
                    interval = tempInterval;
                }
            }
        }

        result.put("minAmount", DECIMAL_FORMAT.format(minAmount));
        result.put("interval", interval.toString());
        return result;
    }
    
    /**
     * <p>获取常见问题</p>
     * */
    private List<AskedQuestionsModel> getAskedQuestions(){
    	List<AskedQuestionsModel> askedQuestions=new ArrayList<AskedQuestionsModel>();
    	List<SysDictDetail> dictDetails = sysDictDetailMapper.listByTypeCode(new Extra("typeCode", "ASKED_QUESTIONS"));
    	if(null==dictDetails||dictDetails.size()==0)return askedQuestions;
    	String apiHost=Global.getValue("server_host");
    	for(SysDictDetail dict:dictDetails){
    		askedQuestions.add(new AskedQuestionsModel(dict.getItemValue(), apiHost+dict.getItemCode()));
    	}
    	return askedQuestions;
    }
}
