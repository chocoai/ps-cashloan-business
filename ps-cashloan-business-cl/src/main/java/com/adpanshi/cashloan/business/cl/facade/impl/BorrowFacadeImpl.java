package com.adpanshi.cashloan.business.cl.facade.impl;

import com.adpanshi.cashloan.business.cl.constant.LoanCityConstant;
import com.adpanshi.cashloan.business.cl.domain.BorrowUserExamine;
import com.adpanshi.cashloan.business.cl.domain.PayLog;
import com.adpanshi.cashloan.business.cl.domain.PettyLoanScene;
import com.adpanshi.cashloan.business.cl.domain.loancity.LoanCityLog;
import com.adpanshi.cashloan.business.cl.enums.LianlianPayTypeEnum;
import com.adpanshi.cashloan.business.cl.enums.PettyloanSceneEnum;
import com.adpanshi.cashloan.business.cl.facade.BorrowFacade;
import com.adpanshi.cashloan.business.cl.mapper.BorrowRepayMapper;
import com.adpanshi.cashloan.business.cl.mapper.BorrowUserExamineMapper;
import com.adpanshi.cashloan.business.cl.mapper.PayLogMapper;
import com.adpanshi.cashloan.business.cl.mapper.PettyLoanSceneMapper;
import com.adpanshi.cashloan.business.cl.service.*;
import com.adpanshi.cashloan.business.core.common.context.Global;
import com.adpanshi.cashloan.business.core.common.context.MessageConstant;
import com.adpanshi.cashloan.business.core.common.enums.ManageExceptionEnum;
import com.adpanshi.cashloan.business.core.common.exception.BussinessException;
import com.adpanshi.cashloan.business.core.common.exception.ManageException;
import com.adpanshi.cashloan.business.core.common.exception.SimpleMessageException;
import com.adpanshi.cashloan.business.core.common.util.DateUtil;
import com.adpanshi.cashloan.business.core.common.util.EnumUtil;
import com.adpanshi.cashloan.business.core.common.util.OrderNoUtil;
import com.adpanshi.cashloan.business.core.common.util.StringUtil;
import com.adpanshi.cashloan.business.core.domain.BorrowMain;
import com.adpanshi.cashloan.business.core.domain.BorrowMainModel;
import com.adpanshi.cashloan.business.core.domain.User;
import com.adpanshi.cashloan.business.core.domain.UserBaseInfo;
import com.adpanshi.cashloan.business.core.mapper.UserBaseInfoMapper;
import com.adpanshi.cashloan.business.core.mapper.UserMapper;
import com.adpanshi.cashloan.business.core.model.BorrowModel;
import com.adpanshi.cashloan.business.core.model.UserBaseInfoModel;
import com.adpanshi.cashloan.business.cr.domain.Credit;
import com.adpanshi.cashloan.business.cr.service.CreditService;
import com.adpanshi.cashloan.business.rule.domain.BorrowRuleResult;
import com.adpanshi.cashloan.business.rule.domain.BorrowTemplate;
import com.adpanshi.cashloan.business.rule.mapper.BorrowRuleResultMapper;
import com.adpanshi.cashloan.business.rule.mapper.BorrowTemplateMapper;
import com.adpanshi.cashloan.business.rule.model.BorrowTemplateModel;
import com.adpanshi.cashloan.business.rule.service.BorrowRiskRuleEngineService;
import com.adpanshi.cashloan.business.system.domain.SysUser;
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
import java.text.MessageFormat;
import java.util.*;

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
    private PayLogMapper payLogMapper;
    @Resource
    private ClSmsService clSmsService;
    @Resource
    private BorrowUserExamineMapper borrowUserExamineMapper;
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
    private LianlianOrderRecordService lianlianOrderRecordService;
    @Resource
    private LoanCityLogService loanCityLogService;
    @Resource
    private LoanCityService loanCityService;
    @Resource
    private BorrowRiskRuleEngineService borrowRiskRuleEngineService;
    @Resource
    private BorrowRuleResultMapper borrowRuleResultMapper;
    @Resource
    PettyLoanSceneMapper pettyLoanSceneMapper;

    @Override
    public BorrowMain rcBorrowApplyForAPI(BorrowMain borrow, String tradePwd, String mobileType) throws Exception {
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
        //订单状态,默认人审核
        String borrowState=BorrowModel.STATE_NEED_REVIEW;
        //执行风控返回风控结果
        String resultCode=borrowRiskRuleEngineService.borrowRiskRule(borrow,mobileType,reBorrowUser);
        //拒绝直接拒绝返回
        if(StringUtils.isNotEmpty(resultCode)&&BorrowRuleResult.RESULT_TYPE_REFUSED.equals(resultCode)){
            borrowState=BorrowModel.STATE_AUTO_REFUSED;
            updateProcess(borrow,resultCode,reBorrowUser,null);
            borrow.setState(borrowState);
            return borrow;
        }
        //所有未拒绝的订单都转入人工复审
        resultCode=BorrowRuleResult.RESULT_TYPE_REVIEW;
        //更新订单和进度状态
        borrowState = updateProcess(borrow,resultCode,reBorrowUser,null);
        borrow.setState(borrowState);
        return borrow;
    }

    @Override
    public String reVerifyBorrowDataForRisk(Long borrowId) {
        BorrowMain borrow = borrowMainService.getById(borrowId);
        String resultState="";
        if(borrow == null){
            logger.info("重新发起风控审核--未找到借款订单");
            return resultState;
        }
        //是否复借用户
        boolean reBorrowUser= isBorrowFuJie(borrow.getUserId());
        //删除之前的规则结果信息
        borrowRuleResultMapper.deleteByBorrowId(borrowId.longValue());
        //执行风控返回风控结果
        String resultCode=borrowRiskRuleEngineService.borrowRiskRule(borrow,"0",reBorrowUser);
        //拒绝直接拒绝返回
        if(StringUtils.isNotEmpty(resultCode)&&BorrowRuleResult.RESULT_TYPE_REFUSED.equals(resultCode)){
            updateProcess(borrow,resultCode,reBorrowUser,"reVerify");
            return resultState;
        }
        //重新风控审核的订单需同步更新我的信审订单
        BorrowUserExamine1(borrowId);
        return resultState;
    }

    private boolean isCanBorrow(BorrowMain borrow, String tradePwd) {

        Long userId = borrow.getUserId();
        User user = userMapper.findByPrimary(userId);
        //1、校验用户是否符合借款条件
        //1.1 校验是否有对应的用户信息
        if(user==null || userId<1){
            throw new SimpleMessageException(MessageConstant.USER_NOT_EXIST);
        }

        //1.2 校验交易密码是否正确
//        if(!"dev".equals(Global.getValue("app_environment")) && !user.getTradePwd().equals(tradePwd)){
//            throw new SimpleMessageException(String.valueOf(Constant.FAIL_CODE_PWD),"交易密码不正确!");
//        }


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
            Double realAmount = amount - fee * cutType;
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
                //修改为临时状态wwpwan
                borrowMainService.modifyState(borrow.getId(), resultType);
            } else {
                if(isReborrow||"reVerify".equals(operate)){
                    resultType = BorrowModel.STATE_NEED_REVIEW;
                }else{
                    if(isReborrow){
                        resultType = BorrowModel.STATE_AUTO_PASS;
                    }else{
                        resultType = BorrowModel.STATE_TEMPORARY_AUTO_PASS;
                    }
                    if ("on".equals(Global.getValue("not_allowed_auto_pass"))){
                        resultType = BorrowModel.STATE_TEMPORARY_NEED_REVIEW;
                    }
                    //修改为临时状态wwpwan
                    borrowMainProgressService.savePressState(borrow, resultType);
                }
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

    //重新风控审核的订单需同步更新我的信审订单
    private void BorrowUserExamine1(Long borrowId){
        BorrowMain borrow = borrowMainService.getById(borrowId);
        if(borrow != null){
            //TODO 数据问题
            if(!borrow.getState().equals(BorrowModel.STATE_NEED_REVIEW) ){
                Map<String, Object> borrmap = new HashMap<String, Object>();
                borrmap.put("borrowId", borrowId);
                BorrowUserExamine borrowUserExamine =  borrowUserExamineMapper.findSelective(borrmap);
                if(borrowUserExamine != null){
                    //更新我的信审订单表
                    borrowUserExamine.setOrderStatus(borrow.getState());
                    borrowUserExamine.setStatus("1");
                    borrowUserExamineMapper.update(borrowUserExamine);
                }
            }
        }
    }

    private boolean isBorrowFuJie(Long userId) {
        //begin pantheon 20170623  判断是否是复借用户，走复借规则
        return CollectionUtils.isNotEmpty(borrowRepayMapper.findRepayWithUser(userId))?true:false;
    }

    /**
     * @description 人工复审(严重重构)
     * @date : 2017-12-30 17:22
     * @author: nmnl
     * @throws Exception
     */
    @Override
    public String manualVerifyBorrow(Long borrowMainId, String state, String remark, Long auditId, String auditName, String orderView) throws ManageException {
        //修改返回值为订单状态，如果返回为空，则代表修改不成功；否则返回对应的状态值 @author yecy 20180302
        String resultState = "";
        BorrowMain borrowMain = borrowMainService.getById(borrowMainId);
        if(null == borrowMain)
            throw new ManageException(ManageExceptionEnum.MANAGE_LOAN_OTHER_NOT_BORROW);
        Long borrowId = borrowMain.getId();
        String borrowMainRemark = borrowMain.getRemark();
        //待审核22 -> 人审挂起25 -> 人审通过26 -> 放款成功30／放款失败31
        //待审核22 -> 人审挂起25 -> 人审通过27
        String borrowMainState = borrowMain.getState();
        //校验当前传入状态.只有这些状态才允许处理.否则不允许
        /*if (state.equals(BorrowModel.STATE_HANGUP) || state.equals(BorrowModel.STATE_PASS) || state.equals(BorrowModel.STATE_REFUSED)){*/
        String newRemark = "borrowMainId: " + borrowMainId + " 现在状态: " + borrowMainState + " 更改为: " + state + " 操作人ID: " + auditId + " 操作人名称: " +auditName + " 订单原备注" + borrowMainRemark;
        //初始化状态 [待审核22] [人审挂起25]
        if (borrowMainState.equals(BorrowModel.STATE_NEED_REVIEW) || borrowMainState.equals(BorrowModel.STATE_HANGUP)){
            //人审挂起
            if (state.equals(BorrowModel.STATE_HANGUP)){
                //修改主订单状态
                borrowMainService.modifyStateAndRemark(borrowId,BorrowModel.STATE_HANGUP,newRemark + " 审核数据 " +remark);
                //增加进程
                borrowMainProgressService.savePressState(borrowMain,state);
                resultState = state;
            }else if(state.equals(BorrowModel.STATE_PASS)){//人审通过
                //修改主订单状态
                if (StringUtils.isNotEmpty(remark)) {
                    borrowMainService.modifyStateAndRemark(borrowId, state, borrowMain.getRemark()!=null?borrowMain.getRemark():""
                            + " ;人审备注：" + remark);
                } else {
                    borrowMainService.modifyState(borrowId, state);
                }
                borrowMainProgressService.savePressState(borrowMain,state);
                resultState = state;
                // 人审通过后的自动放款去除，改为经直融后，满标放款 @author yecy 20180302
            }else if (state.equals(BorrowModel.STATE_REFUSED)){//人审拒绝
                //停用当前合同模版.
                userAuthService.updateLoanSceneStateRefusedWithLoanScene(Integer.parseInt(state), borrowMain.getUserId(), orderView);
                // 增加消贷同城用户判断，推送审核失败给消贷同城 @author yecy 20180110
                refusedByManual(borrowMain,newRemark + " 审核数据 " +remark);
                resultState = state;
            }
        }
        return resultState;
    }

    /**
     * 批量支付
     * @date : 20180101
     * @author : nmnl
     * @param borrowMainIds
     * @param sysUser
     * @return
     */
    @Override
    public Map<String,Object> batchPayAgain(Long[] borrowMainIds, SysUser sysUser) {
        Map<String,Object> outMap = new HashMap<>();
        List<BorrowMainModel> sucList = new ArrayList<>();
        List<BorrowMainModel> failList = new ArrayList<>();
        // 查询所有
        Map<String,Object> inMap = new HashMap<>();
        inMap.put("ids",borrowMainIds);
        List<BorrowMainModel> borrowMainModelList = borrowMainService.selectBorrowListByMap(inMap);
        for (BorrowMainModel borrowMainModel : borrowMainModelList){
            boolean bl = true;
            Long borrowMainId = borrowMainModel.getId();
            if (borrowMainModel.getState().equals(BorrowModel.STATE_REPAY)){
                failList.add(borrowMainModel);
                continue;
            }
            inMap.remove("ids");
            inMap.put("borrowMainId", borrowMainId);
            List<PayLog> plist = payLogMapper.listSelective(inMap);
            //验证本地是否放款成功
            for (PayLog payLog : plist) {
                if ("40".equals(payLog.getState())) {
                    failList.add(borrowMainModel);
                    bl = false;
                    break;
                }
            }
            //验证连连
            if (bl && borrowMainModel.getState().equals(BorrowModel.STATE_REPAY_FAIL)){
                bl = lianlianOrderRecordService.getQueryLocalInLianlianByOrderNo( LianlianPayTypeEnum.INSTANT_PAY, sysUser, borrowMainModel.getOrderNo(), true);
            }
            //以上都成功了.调用放款
            if (bl){
                //修改放款接口，由银程端满标后再放款，此处放款集中处理，放在controller层调用。 @author yecy 20180304
//                borrowLoan(borrowMainModel,new Date());
                sucList.add(borrowMainModel);
            }
        }
        outMap.put("sucList",sucList);
        outMap.put("failList",failList);
        return outMap;
    }

    @Override
    public Boolean isCanCreateBorrow(Long userId) {
        //@remark 代码从isCanBorrow()中拷贝出来，去除订单相关部分的校验，保留其他校验 @author yecy 20180202

        User user = userMapper.findByPrimary(userId);
        //1、校验用户是否符合借款条件
        //1.1 校验是否有对应的用户信息
        if(user==null || userId<1){
            throw new SimpleMessageException("找不到对应的用户信息");
        }

        UserBaseInfo userBaseInfo = userBaseInfoMapper.findByUserId(userId);
        if(userBaseInfo != null && UserBaseInfoModel.USER_STATE_BLACK.equals(userBaseInfo.getState())){
            throw new SimpleMessageException("由于信用问题，您暂时不能申请贷款");
        }
        //1.4 校验用户是否通过各项认证
        Map<String, Object> authMap = userAuthService.getUserAuthWithConfigByUserId(userId);
        if (MapUtils.isNotEmpty(authMap)) {
            Boolean qualified = MapUtils.getBoolean(authMap, "qualified", false);
            Boolean choose = MapUtils.getBoolean(authMap, "choose", false);
            if (!(qualified && choose)) {
                throw new SimpleMessageException("您的资料未认证完善，请前往认证后再来");
            }
        }

        //1.5 用户是否有未结束的借款
        List<BorrowMain> list = borrowMainService.findUserUnFinishedBorrow(userId);
        if (CollectionUtils.isNotEmpty(list)) {
            throw new SimpleMessageException("您的上一笔借款还未还清，请完成订单后再来");
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
                //新增逻辑，消贷同城开关开启，且是消贷同城用户(曾经发布过需求)，且存在状态为‘发布’的需求，则不判断借款天数限制 @author yecy 20180109
                String loanCityOpen = Global.getValue(LoanCityConstant.LOANCITY_OPEN);
                String phone = user.getLoginName();
                LoanCityLog loanCityLog = loanCityLogService.countPublishLogByMobile(phone);
                if (!"on".equals(loanCityOpen) || loanCityLog == null) {
                    throw new SimpleMessageException("由于审核原因，您暂时不能申请借款，请" + day + "日后再来");
                }
            }
        }
        return true;
    }

    private Boolean hadnotPublishOnLoanCity(BorrowMain realBorrow, Long userId) {
        String loanCityOpen = Global.getValue(LoanCityConstant.LOANCITY_OPEN);
        User user = userMapper.findByPrimary(userId);
        String phone = user.getLoginName();
        boolean isLoanCityUser = loanCityLogService.isLoanCityUser(phone);
        LoanCityLog loanCityLog = loanCityLogService.countPublishLogByMobile(phone);
        if ("on".equals(loanCityOpen) && isLoanCityUser) {
            if (loanCityLog == null) {
                loanCityService.unPublish(realBorrow.getId(), phone);
                return true;
            } else {
                loanCityLog.setBorrowMainId(realBorrow.getId());
                loanCityLogService.updateById(loanCityLog);
            }
        }
        return false;
    }

    private BorrowMain refused(BorrowMain borrow) {
        return refused(borrow, null, null,false);
    }

    private BorrowMain refused(BorrowMain borrow, String remark) {
        return refused(borrow, remark, null,false);
    }

    private BorrowMain refusedByManual(BorrowMain borrow, String remark) {
        return refused(borrow, remark, null,true);
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
        clSmsService.refuse(userId,day,borrow.getOrderNo());//审核不通过发送短信通知
        borrow.setState(autoRefusedState);

        User user = userMapper.findByPrimary(userId);
        String phone = user.getLoginName();
        boolean isLoanCityUser = loanCityLogService.isLoanCityUser(phone);
        if (isLoanCityUser){
            loanCityService.auditFail(borrowMainId);
        }
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
