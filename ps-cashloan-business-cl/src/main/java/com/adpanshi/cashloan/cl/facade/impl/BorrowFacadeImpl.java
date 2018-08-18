package com.adpanshi.cashloan.cl.facade.impl;

import com.adpanshi.cashloan.cl.domain.PettyLoanScene;
import com.adpanshi.cashloan.cl.enums.PettyloanSceneEnum;
import com.adpanshi.cashloan.cl.facade.BorrowFacade;
import com.adpanshi.cashloan.cl.mapper.BorrowRepayMapper;
import com.adpanshi.cashloan.cl.mapper.PettyLoanSceneMapper;
import com.adpanshi.cashloan.cl.service.BorrowMainProgressService;
import com.adpanshi.cashloan.cl.service.BorrowMainService;
import com.adpanshi.cashloan.cl.service.ClNoticesService;
import com.adpanshi.cashloan.cl.service.ClSmsService;
import com.adpanshi.cashloan.cl.service.CreditsUpgradeService;
import com.adpanshi.cashloan.cl.service.PettyLoanSceneBorrowService;
import com.adpanshi.cashloan.cl.service.UserAuthService;
import com.adpanshi.cashloan.cl.service.UserEquipmentInfoService;
import com.adpanshi.cashloan.core.common.context.Global;
import com.adpanshi.cashloan.core.common.context.MessageConstant;
import com.adpanshi.cashloan.core.common.exception.BussinessException;
import com.adpanshi.cashloan.core.common.exception.SimpleMessageException;
import com.adpanshi.cashloan.core.common.util.DateUtil;
import com.adpanshi.cashloan.core.common.util.EnumUtil;
import com.adpanshi.cashloan.core.common.util.OrderNoUtil;
import com.adpanshi.cashloan.core.common.util.StringUtil;
import com.adpanshi.cashloan.core.domain.BorrowMain;
import com.adpanshi.cashloan.core.domain.User;
import com.adpanshi.cashloan.core.domain.UserBaseInfo;
import com.adpanshi.cashloan.core.mapper.UserBaseInfoMapper;
import com.adpanshi.cashloan.core.mapper.UserMapper;
import com.adpanshi.cashloan.core.model.BorrowModel;
import com.adpanshi.cashloan.core.model.ManagerUserModel;
import com.adpanshi.cashloan.core.model.UserBaseInfoModel;
import com.adpanshi.cashloan.core.service.UserBaseInfoService;
import com.adpanshi.cashloan.dispatch.run.bo.DispatchRunResponseBo;
import com.adpanshi.cashloan.dispatch.run.domain.DispatchRunDomain;
import com.adpanshi.cashloan.rule.domain.BorrowRuleResult;
import com.adpanshi.cashloan.rule.domain.BorrowTemplate;
import com.adpanshi.cashloan.rule.domain.UserEquipmentInfo;
import com.adpanshi.cashloan.rule.mapper.BorrowTemplateMapper;
import com.adpanshi.cashloan.rule.model.BorrowTemplateModel;
import com.adpanshi.cashloan.rule.service.BorrowRiskRuleEngineService;
import com.adpanshi.creditrank.cr.domain.Credit;
import com.adpanshi.creditrank.cr.service.CreditService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.TypeUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import tool.util.BigDecimalUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 将clBorrowServiceImpl中将下单相关内容抽离出来
 *
 * @author yecy
 * @date 2017/12/12 10:53
 */
@Service
public class BorrowFacadeImpl implements BorrowFacade {

    public static final Logger logger = LoggerFactory.getLogger(BorrowFacadeImpl.class);


    @Resource
    private BorrowRepayMapper borrowRepayMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private UserBaseInfoMapper userBaseInfoMapper;
    @Resource
    private ClSmsService clSmsService;
    @Resource
    private ClNoticesService clNoticesService;
    @Resource
    private BorrowTemplateMapper borrowTemplateMapper;
    @Resource
    private BorrowMainService borrowMainService;
    @Resource
    private BorrowMainProgressService borrowMainProgressService;
    @Resource
    private CreditService creditService;
    @Resource
    private CreditsUpgradeService creditsUpgradeService;
    @Resource
    private UserAuthService userAuthService;
    @Resource
    private PettyLoanSceneBorrowService pettyLoanSceneBorrowService;
    @Resource
    private BorrowRiskRuleEngineService borrowRiskRuleEngineService;
    @Resource
    private PettyLoanSceneMapper pettyLoanSceneMapper;
    @Resource
    private UserBaseInfoService userBaseInfoService;
    @Resource
    private UserEquipmentInfoService userEquipmentInfoService;
    @Resource
    private DispatchRunDomain dispatchRunDomain;

    @Override
    public BorrowMain rcBorrowApplyForAPI(BorrowMain borrow, String tradePwd, String mobileType,
                                          HttpServletRequest request) throws Exception {
        logger.info("rcBorrowApplyForAPI-进入借款申请审核");
        Long userId = borrow.getUserId();
        //校验用户是否符合借款条件
        //@remark 不能借款的直接抛出异常所以不会继续往下走,所以往下走的肯定是可以创建订单的 @author yecy
        isCanBorrow(borrow,tradePwd);

        logger.info("用户"+userId+"开始创建订单=======================");
        saveBorrow(borrow);
        logger.info("符合借款条件开始借款审核============>userId:"+userId);

        //是否复借用户
        boolean reBorrowUser= isBorrowFuJie(borrow.getUserId());
        //执行风控返回风控结果
//        String resultCode=borrowRiskRuleEngineService.borrowRiskRule(borrow,mobileType,reBorrowUser);


        //获取用户的全名/邮箱号
        ManagerUserModel managerUserModel = userBaseInfoService.getBaseModelByUserId(userId);
        //获取设备指纹
        Map<String,Object> userIdMap = new HashMap<>();
        userIdMap.put("userId", userId);
        UserEquipmentInfo userEquipmentInfo = userEquipmentInfoService.findSelective(userId);
        String blackBox = "null";
        if (userEquipmentInfo!=null){
            blackBox = userEquipmentInfo.getDeviceFinger();
        }
        Map rawDataMap = new HashMap();
        Enumeration enuma = request.getParameterNames();
        while (enuma.hasMoreElements()) {
            String paramName = (String) enuma.nextElement();

            String paramValue = request.getParameter(paramName);
            //形成键值对应的map
            rawDataMap.put(paramName, paramValue);
        }
        //调起节点
        DispatchRunResponseBo<String> dispatchRunResponseBo = dispatchRunDomain.startNode(
                managerUserModel.getUserDataId(),"india_oloan_loanApply",managerUserModel.getPhone(),
                managerUserModel.getLoginNameEmail(),managerUserModel.getIdNo(),managerUserModel.getRealName(),
                blackBox,rawDataMap);
        String result = dispatchRunResponseBo.getData();
        JSONObject resultJson = JSONObject.parseObject(result);
        String borrowState=updateProcess(borrow,resultJson.getString("resultType"),reBorrowUser,null);
        borrow.setState(borrowState);
        return borrow;
    }

    private boolean isCanBorrow(BorrowMain borrow, String tradePwd) {

        Long userId = borrow.getUserId();
        User user = userMapper.findByPrimary(userId);
        //1、校验用户是否符合借款条件
        //1.1 校验是否有对应的用户信息
        if(user==null || userId<1){
            throw new SimpleMessageException(MessageConstant.USER_NOT_EXIST);
        }

        UserBaseInfo userBaseInfo = userBaseInfoMapper.findByUserId(userId);
        if(userBaseInfo != null && UserBaseInfoModel.USER_STATE_BLACK.equals(userBaseInfo.getState())){
            throw new SimpleMessageException(MessageConstant.NOT_BORROW);
        }
        //1.4 校验用户是否通过各项认证
        Map<String, Object> authMap = userAuthService.getUserAuthWithConfigByUserId(userId);
        if (MapUtils.isNotEmpty(authMap)) {
            Boolean qualified = MapUtils.getBoolean(authMap, "qualified", false);
            Boolean choose = MapUtils.getBoolean(authMap, "choose", false);
            if (!(qualified && choose)) {
                throw new SimpleMessageException(MessageConstant.NOT_CERTIFICATION);
            }
        }

        //1.5 用户是否有未结束的借款
        List<BorrowMain> list = borrowMainService.findUserUnFinishedBorrow(userId);
        if (CollectionUtils.isNotEmpty(list)) {
            throw new SimpleMessageException(MessageConstant.AN_UNFINISHED_LOAN);
        }

        //1.7 借款天数限制
        Integer againBorrow = Global.getInt("again_borrow");
        BorrowMain borrowTemp = borrowMainService.findLast(userId);
        //@remarks: 查看第一个订单与第二个订单的创建时间 @date:20170930 @author:nmnl
        if (null != borrowTemp) {
            Date dt = new Date();
            logger.info(" 借款天数限制: "+againBorrow+ " 旧订单时间: "+ DateUtil.dateToString(borrowTemp.getCreateTime(),DateUtil.DATEFORMAT_STR_001)
                    + " 新订单时间: " + DateUtil.dateToString(dt,DateUtil.DATEFORMAT_STR_001) );
            int day = DateUtil.daysBetween(borrowTemp.getCreateTime(),dt);
            day = againBorrow - day;
            if (day > 0) {
                throw new SimpleMessageException(MessageFormat.format(MessageConstant.DAYS_BORROW,day));
            }
        }

        //1.8 借款金额格式是否正确
        //判断模板中是否存在借款期限，并且符合模板中的金额限制（最小值，金额间隔）；若不符合，则认为金额格式不正确 @author yecy 20171214
        Double amount = borrow.getAmount();
        Map<String,Object> map = new HashMap<>();
        map.put("timeLimit",borrow.getTimeLimit());
        BorrowTemplate template = borrowTemplateMapper.findSelective(map);
        if (template == null) {
            throw new SimpleMessageException(MessageConstant.MATURITY_LOAN_ERR);
        }
        String testWhiteListStr = Global.getValue("test_white_list");
        if(StringUtil.isNotBlank(testWhiteListStr)) {
            String[] userIds = testWhiteListStr.split(",");
            if (!Arrays.asList(userIds).contains(borrow.getUserId())) {
                checkBorrowAmount(amount,template.getBorrowRule());
            }
        }else{
            checkBorrowAmount(amount,template.getBorrowRule());
        }

        //1.10 信用额度是否足够
        // 用户的借款额度在用户进行额度评估时进行设置，此处只做判断 @author yecy 20180205
        Credit credit = creditService.findByConsumerNo(userId.toString());
        Double tmpCredits=creditsUpgradeService.getCreditsByUserId(userId);
        //最大额度=用户信用额度+临时额度
        Double userCredit= BigDecimalUtil.sub(credit.getUnuse(),tmpCredits);
        logger.info("******************************>userId={}用户额度详情:borrow.amount()={},tmpCredits={},userCredit={}",new
                Object[]{userId,borrow.getAmount(),tmpCredits,userCredit});
        if (StringUtil.isBlank(credit) || userCredit < borrow.getAmount()) {
            throw new SimpleMessageException(MessageConstant.QUOTA_INSUFFICIENT);
        }

        return true;
    }

    private BorrowMain saveBorrow(BorrowMain borrow) {
        //@change 借款信息原来从系统配置中获取，现从借款模板中获取 @author yecy 20171212
        getBorrowByTemplate(borrow);
        borrow.setOrderNo(OrderNoUtil.genPayOrderNo());
        borrow.setState(BorrowModel.STATE_PRE);
        borrow.setCreateTime(DateUtil.getNow());
        PettyLoanScene lastePettyLoanScene=pettyLoanSceneMapper.getPettyLoanSceneLastByUserId(borrow.getUserId(), EnumUtil.getKeysByClz(PettyloanSceneEnum.SCENE_TYPE.class));
        if(null!=lastePettyLoanScene){
        	borrow.setBorrowType(lastePettyLoanScene.getSceneType());
        	logger.info("----------->实时borrowType:{}.",new Object[]{lastePettyLoanScene.getSceneType()});
        }
        logger.info("---------->borrowType:{}.",new Object[]{borrow.getBorrowType()});
        borrowMainService.insert(borrow);
        pettyLoanSceneBorrowService.savePettyLoanSceneBorrow(borrow.getUserId(), borrow.getId());
        borrowMainProgressService.savePressState(borrow, BorrowModel.STATE_PRE);
        return borrow;
    }

    private BorrowMain getBorrowByTemplate(BorrowMain borrow) {
        //借款期限(天)
        String timeLimit = borrow.getTimeLimit();
        //借款金额
        double amount = borrow.getAmount();
        Map<String, Object> map = new HashMap<>(16);
        map.put("timeLimit", timeLimit);
        BorrowTemplate borrowTemplate = borrowTemplateMapper.findSelective(map);

        if (borrowTemplate == null) {
            throw new BussinessException(MessageFormat.format(MessageConstant.LOAN_LIMIT_TEMPLATE, timeLimit));
        } else {
            checkBorrowAmount(amount,borrowTemplate.getBorrowRule());
            BorrowTemplateModel templateModel = new BorrowTemplateModel();
            //feeDetail 字段模板中存的是百分比；借款订单中改为实际金额 @author yecy 20180210
            BeanUtils.copyProperties(borrowTemplate, templateModel,"feeDetail");

            String detailStr = borrowTemplate.getFeeDetail();
            JSONObject oldDetail = JSONObject.parseObject(detailStr);
            JSONObject newDetail = new JSONObject();
            Double sumFee = 0d;
            for (JSONObject.Entry<String, Object> fd : oldDetail.entrySet()) {
                //费用百分比
                Double dFeePer = TypeUtils.castToDouble(fd.getValue());
                Double dFee = BigDecimalUtil.mul(dFeePer, amount, Double.parseDouble(timeLimit));
                newDetail.put(fd.getKey(), dFee);
                sumFee += dFee;
            }
            templateModel.setFee(sumFee);
            templateModel.setFeeDetail(newDetail.toJSONString());

            boolean isfujie = isBorrowFuJie(borrow.getUserId());
            templateModel.setOldUser(isfujie);

            Integer cutType = templateModel.getCutType();
            Integer penaltyType = templateModel.getPenaltyType();
            if (isfujie) {
                cutType >>= 1;
                penaltyType >>= 1;
            }
            cutType &= 0x1;
            penaltyType &= 0x1;
            templateModel.setCutOpen(cutType.equals(1));
            templateModel.setMaxPenaltyOpen(penaltyType.equals(1));

            borrow.setTemplateInfo(JSON.toJSONString(templateModel));

            //综合费用
            Double fee = templateModel.getFee();
            borrow.setFee(fee);

            //实际金额 = 借款金额 - 综合费用 * 是否启用砍头息
            String beheadFee = Global.getValue("behead_fee");
            //获取砍头息百分比
            String[] headFees = Global.getValue("head_fee").split(",");
            //砍头息启用状态-开
            String beheadFeeOn = "10";
            double realAmount = 0;
            //由于7/14的砍头息砍头息相同5%,这里随意取一个值
            if (beheadFeeOn.equals(beheadFee)) {
                realAmount = amount - BigDecimalUtil.mul(Double.parseDouble(headFees[0]),amount);
            }else{
                realAmount = amount - fee * cutType;
            }
            borrow.setRealAmount(realAmount);

        }
        return borrow;
    }

    /***
     * 更新订单状态和借款进度
     * @param borrow 主订单
     * @param stateResult 规则状态
     * @param isReborrow 是否复借
     * @param operate 操作：reVerify：：重新风控复审
     * @return String 订单状态
     * */
    private String updateProcess(BorrowMain borrow, String stateResult,boolean isReborrow,String operate){
        //wwpwan
        String resultType = stateResult;
        //logger.info("最终规则执行结束resultType="+resultType);
        if(StringUtil.isNotBlank(resultType)){
            // 规则匹配结果命中不通过，直接修改借款申请状态为审核失败并更新借款进度,释放额度
            if (BorrowRuleResult.RESULT_TYPE_REFUSED.equals(resultType)) {
                refused(borrow,null,operate,false);
                resultType =  BorrowModel.STATE_AUTO_REFUSED;
                // 规则匹配结果命中需要人工复审，直接修改借款申请状态为需要人工复审并更新借款进度
            } else if (BorrowRuleResult.RESULT_TYPE_REVIEW.equals(resultType)) {
                resultType = BorrowModel.STATE_NEED_REVIEW;
                borrowMainProgressService.savePressState(borrow, resultType);
                //修改为临时状态wwpwan
                borrowMainService.modifyState(borrow.getId(), resultType);
            } else {
//                if(isReborrow||"reVerify".equals(operate)){
//                    resultType = BorrowModel.STATE_NEED_REVIEW;
//                }else{
//                    if(isReborrow){
                        resultType = BorrowModel.STATE_AUTO_PASS;
//                    }else{
//                        resultType = BorrowModel.STATE_TEMPORARY_AUTO_PASS;
//                    }
//                    if ("on".equals(Global.getValue("not_allowed_auto_pass"))){
//                        resultType = BorrowModel.STATE_TEMPORARY_NEED_REVIEW;
//                    }
//                    //修改为临时状态wwpwan
                    borrowMainProgressService.savePressState(borrow, resultType);
//                }
                //修改为临时状态wwpwan
                borrowMainService.modifyState(borrow.getId(), resultType);
            }
        } else {
            //借款审核失败
            refused(borrow,null,operate,false);
            //wwpwan
            resultType =  BorrowModel.STATE_AUTO_REFUSED;
        }
        return resultType;
    }

    private boolean isBorrowFuJie(Long userId) {
        //begin pantheon 20170623  判断是否是复借用户，走复借规则
        return CollectionUtils.isNotEmpty(borrowRepayMapper.findRepayWithUser(userId))?true:false;
    }

    private BorrowMain refused(BorrowMain borrow, String remark, String operate, Boolean isManual) {
        String autoRefusedState ;
        if (isManual){ //是否为人工操作
            autoRefusedState  = BorrowModel.STATE_REFUSED;
        }else {
            autoRefusedState  = BorrowModel.STATE_AUTO_REFUSED;
        }
        Long borrowMainId = borrow.getId();
        Long userId = borrow.getUserId();
        if (StringUtils.isEmpty(remark)) {
            borrowMainService.modifyState(borrowMainId, autoRefusedState);
        } else {
            borrowMainService.modifyStateAndRemark(borrowMainId, autoRefusedState, remark);
        }
        if ("reVerify".equals(operate)) {
            borrowMainProgressService.updateNearestPressState(borrow, autoRefusedState);
        } else {
            borrowMainProgressService.savePressState(borrow, autoRefusedState);
        }
        Integer againBorrow = Global.getInt("again_borrow");
        Date dt = new Date();
        int day = DateUtil.daysBetween(borrow.getCreateTime(),dt);
        day = againBorrow - day;
        //审核不通过发送短信通知
        clSmsService.refuse(userId,day,borrow.getOrderNo());
        //审核不通过消息通知
        clNoticesService.refuse(userId,day);
        borrow.setState(autoRefusedState);

        return borrow;
    }

    private void checkBorrowAmount(Double amount, String ruleStr){
        if (StringUtils.isNotEmpty(ruleStr)){
            JSONObject rule = JSONObject.parseObject(ruleStr);
            List amountList = rule.getJSONArray("amountList");
            if(StringUtil.isNotEmptys(amountList)){
                boolean flg = true;
                for (int i=0; i<amountList.size(); i++) {
                    if (amount - Double.parseDouble(amountList.get(i).toString()) == 0 ) {
                        flg = false;
                        break;
                    }
                }
                if(flg){
                    logger.info("借款金额{}不在模板中允许的金额{}内", amount, amountList.toString());
                    throw new SimpleMessageException("借款金额格式不正确");
                }
            }else{
                Long interval = rule.getLong("interval");
                Double minAmount = rule.getDouble("minAmount");
                Double maxAmount = rule.getDouble("maxAmount");
                if (minAmount != null && amount < minAmount) {
                    logger.info("借款金额{}小于模板中的最小金额{}", amount, minAmount);
                    throw new SimpleMessageException("借款金额格式不正确");
                }
                if (maxAmount != null && amount > maxAmount) {
                    logger.info("借款金额{}大于模板中的最大金额{}", amount, maxAmount);
                    throw new SimpleMessageException("借款金额格式不正确");
                }
                if (interval != null && Math.abs(amount) % interval != 0) {
                    logger.info("借款金额不是{}的倍数", amount, interval);
                    throw new SimpleMessageException("借款金额格式不正确");
                }
            }
        }else {
            logger.info("模板中未配置借款金额规则，认为所有金额都符合规定，全部通过");
        }
    }
}
