package com.adpanshi.cashloan.business.cl.facade.impl;

import com.adpanshi.cashloan.business.cl.constant.LoanCityConstant;
import com.adpanshi.cashloan.business.cl.domain.*;
import com.adpanshi.cashloan.business.cl.domain.loancity.LoanCityCodeLog;
import com.adpanshi.cashloan.business.cl.domain.loancity.LoanCityLog;
import com.adpanshi.cashloan.business.cl.extra.HandleProgress;
import com.adpanshi.cashloan.business.cl.extra.OrderByExtra;
import com.adpanshi.cashloan.business.cl.facade.StaginFacade;
import com.adpanshi.cashloan.business.cl.mapper.*;
import com.adpanshi.cashloan.business.cl.model.*;
import com.adpanshi.cashloan.business.cl.model.loancity.BaseLoanCityModel;
import com.adpanshi.cashloan.business.cl.model.pay.lianlian.constant.LianLianConstant;
import com.adpanshi.cashloan.business.cl.model.pay.lianlian.enums.LianlianParameterEnum;
import com.adpanshi.cashloan.business.cl.model.pay.lianlian.util.ActiveSignUtil;
import com.adpanshi.cashloan.business.cl.model.stagin.BorrowData;
import com.adpanshi.cashloan.business.cl.model.stagin.BorrowExtra;
import com.adpanshi.cashloan.business.cl.model.stagin.StaginPaymentData;
import com.adpanshi.cashloan.business.cl.util.StaginOrderUtil;
import com.adpanshi.cashloan.business.core.common.context.Constant;
import com.adpanshi.cashloan.business.core.common.context.Global;
import com.adpanshi.cashloan.business.core.common.enums.BorrowRepayLogEnum;
import com.adpanshi.cashloan.business.core.common.enums.OrderPrefixEnum;
import com.adpanshi.cashloan.business.core.common.exception.BussinessException;
import com.adpanshi.cashloan.business.core.common.util.*;
import com.adpanshi.cashloan.business.core.domain.Borrow;
import com.adpanshi.cashloan.business.core.domain.BorrowMain;
import com.adpanshi.cashloan.business.core.mapper.BorrowMainMapper;
import com.adpanshi.cashloan.business.core.mapper.UserMapper;
import com.adpanshi.cashloan.business.core.model.BorrowModel;
import com.adpanshi.cashloan.business.core.model.CloanUserModel;
import com.adpanshi.cashloan.business.core.model.StaginDetailModel;
import com.adpanshi.cashloan.business.rule.domain.BankCard;
import com.adpanshi.cashloan.business.rule.mapper.BankCardMapper;
import com.adpanshi.cashloan.business.rule.mapper.ClBorrowMapper;
import com.adpanshi.cashloan.business.rule.model.StaginRepaymentModel;
import com.adpanshi.cashloan.business.rule.model.StaginRepaymentPlanModel;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/***
 ** @category 请用一句话来描述其用途...
 ** @author qing.yunhui
 ** @email: 280672161@qq.com
 ** @createTime: 2017年12月24日下午4:15:15
 **/
@Service
public class StaginFacadeImpl implements StaginFacade{


	Logger logger=LoggerFactory.getLogger(getClass());

	@Autowired
	BorrowMainMapper borrowMainMapper;
	@Autowired
	BorrowMainProgressMapper borrowMainProgressMapper;
	@Autowired
	BorrowProgressMapper borrowProgressMapper;
	@Autowired
	BorrowRepayMapper borrowRepayMapper;
	@Autowired
    ClBorrowMapper clBorrowMapper;
	@Autowired
	UserMapper userMapper;
	@Autowired
	PayLogMapper payLogMapper;
	@Autowired
    BankCardMapper bankCardMapper;
	@Autowired
	PayReqLogMapper payReqLogMapper;
	@Autowired
	private LoanCityLogMapper loanCityLogMapper;
	@Autowired
	private LoanCityCodeLogMapper loanCityCodeLogMapper;

	@Override
	public StaginDetailModel getStaginDetailBy(Long userId, Long borMainId) {
		if(StringUtil.isEmpty(userId,borMainId)) {
			throw  new BussinessException(Constant.FAIL_CODE_PARAM_INSUFFICIENT+"", "缺少必要参数");
		}
		//@1.分期明细 (a.查询主订单)
		BorrowMain borMain= borrowMainMapper.findById(borMainId);
		if(null==borMain) throw  new BussinessException(Constant.FAIL_CODE_PARAM_INSUFFICIENT+"", "订单不存在");
		StaginModel staginModel=new StaginModel(borMain.getAmount(), borMain.getBank(), borMain.getCardNo(), borMain.getFee(), borMain.getId(), borMain.getOrderNo());
		TemplateInfoModel template=new TemplateInfoModel().parseTemplateInfo(borMain.getAmount(), borMain.getTemplateInfo());
		staginModel.setPayType(template.getPayType());
		String startDate= DateUtil.dateToString(borMain.getLoanTime(), DateUtil.YYYY_MM_DD_CN);
		String endDate=DateUtil.dateToString(borMain.getRepayTime(), DateUtil.YYYY_MM_DD_CN);
		if(StringUtil.isEmpty(startDate,endDate)){
			//审核失败情况下-重新计算
			startDate=DateUtil.dateToString(borMain.getCreateTime(), DateUtil.YYYY_MM_DD_CN);
			endDate=DateUtil.addDay(DateUtil.dateToString(borMain.getCreateTime(), DateUtil.YYYY_MM_DD), Integer.parseInt(borMain.getTimeLimit())-1);
			endDate=DateUtil.dateStr6(DateUtil.parse(endDate, DateUtil.YYYY_MM_DD));
		}
		staginModel.setContractLimit(startDate+"至"+endDate);
		StaginDetailModel staginDetail=new StaginDetailModel(Global.getValue(Constant.SERVICE_CALL));
		//@2.分期进度 (a.未放款或放款失败,只需要查询主订单借款进度即可. b.放款成功,需要查询子订单借款进度最新的一条还款记录)
		Map<String,Object> paramMap=OrderByExtra.getInstance().orderByDESC("create_time");
		paramMap.put("borrowId", borMainId);
		paramMap.put("userId",userId);
		List<ProgressModel> localProgressList= borrowMainProgressMapper.queryProgressByBorIdWithUserId(paramMap);
		String state=borMain.getState();
		if(state.equals(BorrowModel.STATE_REPAY)){
			//@1.如果有逾期的:查询子订单最近一条逾期进度(子进度)
			//@2.如果没 逾期的:查询最近一条还款记录进度表(子进度)
			BorrowProgress progres= borrowProgressMapper.findLateByBorMainIdWithUserId(borMainId, userId,BorrowModel.STATE_DELAY);
			if(null!=progres) state=BorrowModel.STATE_DELAY;
			if(null==progres)progres=borrowProgressMapper.findLateByBorMainIdWithUserId(borMainId, userId,BorrowModel.STATE_FINISH);
			if(null==progres) progres=borrowProgressMapper.findLateByBorMainIdWithUserId(borMainId, userId,BorrowModel.STATE_REPAY);
			String createTime=DateUtil.dateToString(progres.getCreateTime(),DateUtil.YYYY_MM_DD);
			String stageName=progres.getState().equals(BorrowModel.STATE_DELAY)?"逾期中":"还款中";
			localProgressList.add(new ProgressModel(progres.getState(), progres.getRemark(), createTime, stageName));
		}
		HandleProgress progressData=HandleProgress.magic(localProgressList, state);
		List<ProgressModel> resultProgressList =progressData.getProgress();
		//这里需要重置staginModel状态-进行覆盖
		staginModel.setState(progressData.getState());
		staginDetail.setStagin(staginModel);
		staginDetail.setStaginProgress(resultProgressList);
		StaginRepayMode staginRepayMode =borrowRepayMapper.getStaginRepayDetail(userId, borMainId);;
		if(null==staginRepayMode) staginRepayMode=new StaginRepayMode();
		staginDetail.setRepayment(staginRepayMode);

		//添加是否需要显示验证码提示框（需求已支付且验证码未输入正确，输入次数小于等于5）  @author yecy 20180111
		canEnterCode(borMainId,staginDetail);
		return staginDetail;
	}

	@Override
	public List<StaginRepaymentModel> queryRepaymentsByUserIdWithBorMainId(Long userId, Long borrowMainId) {
		List<StaginRepaymentModel> staginRepayments=clBorrowMapper.queryRepaymentsByUserIdWithBorMainId(userId, borrowMainId);
		for(StaginRepaymentModel staginRepay:staginRepayments){
			String orderNo=staginRepay.getOrderNo();
			String str=orderNo.substring(orderNo.lastIndexOf("X")+1);
			staginRepay.setByStages("第"+ NumberToCNUtil.toHanStr(Integer.parseInt(str))+"期");
			staginRepay.setRepayWay(EnumUtil.getNameByCode(BorrowRepayLogEnum.REPAY_WAY.class, staginRepay.getRepayWay()));
		}
		return staginRepayments;
	}

	@Override
	public StaginRepaymentPlanData queryRepaymentPlanByUserIdWithBorMainId(Long userId, Long borrowMainId, Boolean repayment) {
		StaginRepaymentPlanData staginRepayment=null;
		try {
			BorrowMain borrowMain= borrowMainMapper.findByPrimary(borrowMainId);
			if(null==borrowMain) throw new BussinessException(userId + "-未查询到borrowManId="+borrowMainId+"的订单号!");
			TemplateInfoModel templateInfo=new TemplateInfoModel().parseTemplateInfo(borrowMain.getAmount(), borrowMain.getTemplateInfo());
			if(null==templateInfo) throw new BussinessException(userId + "-未查询到borrowManId="+borrowMainId+"的模板号!");
			List<StaginRepaymentPlanModel> dataList=clBorrowMapper.queryRepaymentPlanByUserIdWithBorMainId(userId, borrowMainId);
			staginRepayment= StaginRepaymentPlanData.handle(dataList,borrowMain.getAmount(),templateInfo.getDayRate(), repayment,templateInfo.getCycle());
		} catch (Exception e) {
			logger.error("调用分期还款计划接口异常!",e);
			throw  new BussinessException(Constant.OTHER_CODE_VALUE+"", "系统异常!");
		}
		return staginRepayment;
	}

	@Override
	public Object activePayment(long userId, String[] borrowOrderNos) {
		logger.info("--------------->主动还款borrowOrderNos={}、userId={}.",new Object[]{JSONObject.toJSONString(borrowOrderNos),userId});
//		if(StringUtil.isEmpty(userId,borrowOrderNos))throw  new BussinessException(Constant.FAIL_CODE_PARAM_INSUFFICIENT+"", "分期还款-缺少必要参数!");
		if(StringUtil.isEmpty(userId,borrowOrderNos))throw  new BussinessException(Constant.FAIL_CODE_PARAM_INSUFFICIENT+"", "缺少必要参数!");
		CloanUserModel cloanUserModel = userMapper.getModel(userId);
		if(null==cloanUserModel) throw new BussinessException("userId="+userId+"不存在!");
		Date now = new Date();
		if(null==borrowOrderNos||borrowOrderNos.length==0)throw new BussinessException("借款订单号格式异常!");
		//@1.查询用户勾选的订单号在数据库中是否存在
		List<Borrow> borrows=clBorrowMapper.queryBorrowByOrderNosWithUserId(userId, null, Arrays.asList(borrowOrderNos));
		List<BorrowRepay> borrowRepays= borrowRepayMapper.queryBorrowRepayByOrderNosWithUserIdState(userId, "20", Arrays.asList(borrowOrderNos));
//		if(borrows.size()!=borrowOrderNos.length)throw new BussinessException("勾选的借款订单号在数据库中不存在.");
		if(borrows.size()!=borrowOrderNos.length)throw new BussinessException("借款订单号在数据库中不存在.");
		//@2.查询勾选的订单号是否有已还款的
		BorrowMain borrowMain=borrowMainMapper.findByPrimary(borrows.get(0).getBorrowMainId());
		//@1.如果是提前还款的、则需要另外计算    @2.订单号生成修改(先查询payLog中是否已存在、如果没有正常生成、否则append)
		BorrowData borData= BorrowData.getBorrowData(borrows,borrowRepays,borrowMain, userId);
//		if(!borData.isPass())throw new BussinessException("勾选的借款订单号已有已还款的订单.");
		if(!borData.isPass())throw new BussinessException("借款订单号已有已还款的订单.");
		borData.setOrderNos(borrowOrderNos);
		StaginOrderModel staginOrder= StaginOrderUtil.buildStaginOrder(borrowOrderNos);
		List<String> queryOrderNos= StaginOrderUtil.logicReckonDecreasing(staginOrder);
		//判断所勾选分期之前的分期是否已还款
		if(null!=queryOrderNos&&queryOrderNos.size()>0){
			List<Borrow> repayBorrows= clBorrowMapper.queryBorrowByOrderNosWithUserId(userId, BorrowModel.STATE_FINISH, queryOrderNos);
			if(null==repayBorrows||repayBorrows.size()!=queryOrderNos.size()){
				logger.error("userId={}的用户勾选的分期订单号={}、数据库查询到有未还款的分期订单包含={}",new Object[]{userId,JSONObject.toJSONString(borrowOrderNos),JSONObject.toJSONString(queryOrderNos)});
				throw  new BussinessException(Constant.OTHER_CODE_VALUE+"", "勾选的分期不正确!");
			}
		}
		// 此处将main表中订单以'J'开头的orderNo改为'K'开头（因为子订单对应的orderNo为'R'开头） @author yecy 20180306
		String mainOrderNo = borrowMain.getOrderNo();
		String repayOrderNo = mainOrderNo.replaceAll(OrderPrefixEnum.PAY.getCode(),OrderPrefixEnum.REPAYMENT.getCode());
		borData.setGenOrderNo(StaginOrderUtil.genRepayOrderNo(repayOrderNo, staginOrder));
		Map<String, Object> rtMap = syncQueryVerification(borData,userId,now);
		if(null == rtMap){
			logger.error("userId={}的借款详情->验证失败，主订单id={}、连连订单号={}.",new Object[]{userId,borrowMain.getId(),borData.getGenOrderNo()});
			throw new BussinessException(" 借款详情异常,请联系客服人员,谢谢！");
		}
		BankCard bc = (BankCard) rtMap.get("bc");
		String orderNo = borData.getGenOrderNo();
		//统一转换..... end
		if(null != rtMap.get("type")){
			logger.error("userId={}的主订单id={}的连连订单号={}的订单已完成支付,支付方式={}.",new Object[]{userId,borData.getBorrowMainId(),borData.getGenOrderNo(),rtMap.get("type")});
			throw new BussinessException(" 当前订单已经完成支付,请联系客服人员,谢谢！");
		}
		//用户在你们平台的注册时间
		String registTime=null!=cloanUserModel.getRegistTime()?DateUtil.dateStr(cloanUserModel.getRegistTime(),DateUtil.DATEFORMAT_STR_011):DateUtil.dateStr(new Date(),DateUtil.DATEFORMAT_STR_011);
		//本金 - 实际到账金额
		BorrowExtra borExtra=borData.getBorrowExtra();
		//服务费 = 原服务费 + 原信息认证费
		double serviceAmount= MathUtil.add(borExtra.getServiceFees(),borExtra.getInfoAuthFees());
		//总计  
		double totalAmount= MathUtil.add(borExtra.getRealAmounts(),borExtra.getFees(),borExtra.getPenaltyAmounts(),borExtra.getEarlyFee());
		//异步地址 -支持测试
		String notifyUrl=Global.getValue("server_host")+"/com.adpanshi.com.adpanshi.cashloan.api/active/payment/asynNotify.htm";
		//商户系统单次成功操作唯一 -- 由于和代扣. 代发 怕引起冲突. 所有这里用主动扣款orderno
		String businessNo=Global.getValue(LianLianConstant.BUSINESS_NO_R);
		StaginPaymentData staginPayment=new StaginPaymentData(cloanUserModel.getIdNo(), cloanUserModel.getPhone(), serviceAmount, borExtra.getRealAmounts(), bc.getCardNo(),
				orderNo, businessNo, borExtra.getFees(), totalAmount, borExtra.getPenaltyAmounts(), cloanUserModel.getRealName(), notifyUrl);
		staginPayment.setEarlyFee(borExtra.getEarlyFee());
		String checkedStageRange=staginOrder.getArrays().get(0)+"-"+staginOrder.getArrays().get(staginOrder.getArrays().size()-1);
		//分期费用详情
		StaginCostInfoModel costinfo=new StaginCostInfoModel(staginPayment.getRealAmount(), staginPayment.getInterest(),
				staginPayment.getPenaltyAmout(), staginPayment.getEarlyFee(), staginPayment.getTotalAmount(), borData.getTemplateInfoModel().getStages(), checkedStageRange);
		staginPayment.setCostInfo(costinfo);
		staginPayment.setRegistTime(registTime);
		Map<String, Object> result = ReflectUtil.clzToMap(staginPayment);
//		String sign = syncQuerySign(result,now,userId);
//		logger.info("orderNo " + orderNo + " 借款详情 : sign " + sign);JSONObject.toJSONString(result);
//		result.put("sign",sign);
		return result;
	}

	/**
	 * 同步查询验证
	 * @param borrData
	 * @param userId
	 * @param now
	 * @return
	 */
	private Map<String, Object> syncQueryVerification(BorrowData borrData,long userId,Date now) {
		Map<String, Object> rtMap =  new HashMap<String, Object>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("borrowMainId",borrData.getBorrowMainId());
		paramMap.put("userId",userId);
		//兼容 -放款,代扣,主动还款
		List<PayLog> payLogList = payLogMapper.listSelective(paramMap);
		String[] orderNos = borrData.getOrderNos();
		// 此处应该比较payLog表中的orderNo与支付的orderNo,并非全部 @author yecy 20180306
		for(PayLog payLog : payLogList) {
			String payLogType = payLog.getType();
			String payLogState = payLog.getState();
			if (cotainSuccessPay(payLog.getOrderNo(),orderNos)){
				if (PayLogModel.TYPE_COLLECT.equals(payLogType) && PayLogModel.STATE_PAYMENT_SUCCESS.equals(payLogState)) {
					rtMap.put("type", PayLogModel.TYPE_COLLECT);
					return rtMap;
				}
				if (PayLogModel.TYPE_OFFLINE_COLLECT.equals(payLogType) && PayLogModel.STATE_PAYMENT_SUCCESS.equals(payLogState)) {
					rtMap.put("type", PayLogModel.TYPE_OFFLINE_COLLECT);
					return rtMap;
				}
				if (PayLogModel.TYPE_LINE_PAYMENT.equals(payLogType) && PayLogModel.STATE_PAYMENT_SUCCESS.equals(payLogState)) {
					rtMap.put("type", PayLogModel.TYPE_LINE_PAYMENT);
					return rtMap;
				}
			}
		}
		paramMap.clear();
		paramMap.put("userId", userId);
		BankCard bc = bankCardMapper.findSelective(paramMap);
		if(null == bc)throw new BussinessException(userId + "借款详情 :查询的用户银行卡不存在"+borrData.getBorrowMainId());
		BorrowExtra borrowExtra= borrData.getBorrowExtra();
		double waitRepayAmount=MathUtil.add(borrowExtra.getRealAmounts(),borrowExtra.getFees());
		Double totalAmount =MathUtil.add(waitRepayAmount,borrowExtra.getPenaltyAmounts(),borrowExtra.getEarlyFee());
		//查出最近的一个单子.
		paramMap.clear();
		paramMap.put("borrowMainId",borrData.getBorrowMainId());
		paramMap.put("userId",userId);
//		paramMap.put("type",PayLogModel.TYPE_LINE_PAYMENT);
		paramMap.put("typeList", Arrays.asList(PayLogModel.TYPE_LINE_PAYMENT,PayLogModel.TYPE_COLLECT));
		// 此处需要对orderNo进行筛选，应该是查找相同前缀的orderNo('Y'前半部分) @author yecy 20180306
		paramMap.put("matchOrderNo",borrData.getGenOrderNo());
		PayLog newPayLog = payLogMapper.findSelectiveOne(paramMap);
		int row = 0;
		String orderNo = "";
		if(null != newPayLog && cotainSuccessPay(newPayLog.getOrderNo(),orderNos)){
			logger.info("--------------->勾选的订单{}在支付记录PayLog.orderNo={}中存在.",new Object[]{JSONObject.toJSONString(orderNos),newPayLog.getOrderNo()});
			orderNo = newPayLog.getOrderNo();//需要-对比
			paramMap = new HashMap<>();
			paramMap.put("orderNo",orderNo);
			PayReqLog payReqLog = payReqLogMapper.findSelectiveOne(paramMap);
			//payreq为空有三种情况.
			//1.用户点击查询之后.没有在此点击支付
			//2.app未同步请求.
			//3.连连未异步请求
			if(null != payReqLog) {
				logger.info("----------->payReqLog:{},borrData={}.",new Object[]{JSONObject.toJSONString(payReqLog),JSONObject.toJSONString(borrData)});
				int result = (null==payReqLog.getResult()?0:payReqLog.getResult());//目前代扣不支持-result有可能为NULl
				if(1 == result){//已经成功,不允许走下去
					rtMap.put("type", PayLogModel.TYPE_LINE_PAYMENT);
					return rtMap;
				}
				if (-1 == result || 0==result) {//必须新建
//					orderNo = StaginOrderUtil.genSequenceRepayOrderNo(borrData.getGenOrderNo(), payReqLog.getOrderNo());
					orderNo=StaginOrderUtil.genSequenceRepayOrderNo(orderNo, orderNo);
					row = saveNewPayLog(orderNo,borrData.getBorrowMainId(),userId,totalAmount, bc.getCardNo(),bc.getBank(),now);
					if (row != 1) {
						logger.error(userId + " 借款详情 : 支付订单创建失败 " + borrData.getBorrowMainId() + " 订单号: " + orderNo);
						return null;
					}
				}
			}else{
				Map<String, Object> payLogMap = new HashMap<String, Object>();
				payLogMap.put("orderNo", orderNo);
				payLogMap.put("id", newPayLog.getId());
				payLogMap.put("amount", totalAmount.doubleValue());
				payLogMap.put("payReqTime", now);
				payLogMap.put("updateTime", now);
				row = payLogMapper.updateSelective(payLogMap);
				if (row != 1) {
					logger.error(userId + " 借款详情 : 支付订单更新失败 " + borrData.getBorrowMainId() + " 订单号: " + orderNo);
					return null;
				}
			}
		}else{
			orderNo = borrData.getGenOrderNo();
			row = saveNewPayLog(orderNo,borrData.getBorrowMainId(),userId,totalAmount,bc.getCardNo(),bc.getBank(),now);
			if(row != 1){
				logger.error(userId + " 借款详情 : 支付订单首次创建失败 " + borrData.getBorrowMainId() + " 订单号: " +orderNo);
				return null;
			}
		}
		if(StringUtils.isBlank(orderNo)) {
			logger.error(userId + " 借款详情 : 支付订单,单号异常 " + borrData.getBorrowMainId() + " 订单号: " + orderNo);
			return null;
		}
		borrData.setGenOrderNo(orderNo);
		rtMap.put("bc",bc);
		return rtMap;
	}

	/**
	 * 同步查询验证
	 * @param result
	 * @param now
	 * @param userId
	 * @return
	 */
	private String syncQuerySign(Map<String, Object> result,Date now,long userId) {
		PayOrder payOrder = new PayOrder();
		payOrder.setBusi_partner("101001");
		payOrder.setNo_order(String.valueOf(result.get("orderNo")));
		SimpleDateFormat time = new SimpleDateFormat(DateUtil.DATEFORMAT_STR_011);
		payOrder.setDt_order(time.format(now));
		payOrder.setName_goods(Global.getValue("title")+"还款支付");
		payOrder.setNotify_url(String.valueOf(result.get("notifyUrl")));
		payOrder.setSign_type(PayOrder.SIGN_TYPE_RSA);
		payOrder.setValid_order("100");
		payOrder.setUser_id(String.valueOf(userId));
		payOrder.setId_no(String.valueOf(result.get("idNo")));
		payOrder.setAcct_name(String.valueOf(result.get("realName")));
		String appEnvironment = Global.getValue("app_environment");
		//支持测试环境
		payOrder.setMoney_order("dev".equals(appEnvironment)?"0.01":String.valueOf(result.get("totalAmount")));
		// 银行卡卡号，该卡首次支付时必填
		payOrder.setCard_no(String.valueOf(result.get("cardNo")));
		// 银行卡历次支付时填写，可以查询得到，协议号匹配会进入SDK，
		payOrder.setNo_agree("");
		payOrder.setId_type("0");
		// 风险控制参数
		payOrder.setRisk_item(constructRiskItem(result,userId));
		payOrder.setOid_partner(String.valueOf(result.get("businessNo")));
		String content = ActiveSignUtil.sortParam(payOrder);
		logger.info(" userId " + userId + " 借款详情 : content " + content);
		result.put("payOrder",JSONObject.toJSON(payOrder));
		return ActiveSignUtil.sign(content, Global.getValue(LianLianConstant.PRIVATE_KEY_R));
	}

	private String constructRiskItem(Map<String, Object> result,long userId) {
		JSONObject mRiskItem = new JSONObject();
		try {
			mRiskItem.put("frms_ware_category", LianlianParameterEnum.REAL_NAME_CLASS.CONSUMPTION_PERIOD.getKey());
			mRiskItem.put("user_info_mercht_userno", ""+userId);
			mRiskItem.put("user_info_dt_register", result.get("registTime"));
			mRiskItem.put("user_info_bind_phone", result.get("phone"));
			mRiskItem.put("user_info_identify_state", "1");
			mRiskItem.put("user_info_identify_type", "1");
			mRiskItem.put("user_info_full_name", ""+result.get("realName"));
			mRiskItem.put("user_info_id_no", ""+result.get("idNo"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return mRiskItem.toString();
	}

	private int saveNewPayLog(String orderNo,Long borrowMainId,Long userId,double totalAmount,String cardNo,String bank,Date now){
		PayLog payLog=new PayLog(orderNo,borrowMainId,userId,totalAmount,cardNo,bank,now);
		return payLogMapper.save(payLog);
	}

	private void canEnterCode(Long borMainId, StaginDetailModel staginDetail) {
		//添加是否需要显示验证码提示框的判断（需求已支付且验证码未输入正确,则提示框是否显示返回true）  @author yecy 20180111
		Map<String, Object> map = new HashMap<>();
		map.put("borrowMainId", borMainId);
		LoanCityLog loanCityLog = loanCityLogMapper.findSelective(map);
		Map<String,Object> codeMap = new HashMap<>();
		codeMap.put("borrowMainId",borMainId);
		codeMap.put("success",true);
		LoanCityCodeLog codeLog = loanCityCodeLogMapper.findSelective(codeMap);
		int maxCount = Global.getInt(LoanCityConstant.LOANCITY_MAX_CODE_RETRIES);

		if (loanCityLog != null){
			Integer count = loanCityLog.getCodeRetryCount();
			if(BaseLoanCityModel.LoanCityStateEnum.PAID.getCode().equals(loanCityLog.getState())
					&& codeLog == null) {
				staginDetail.setNeedCode(true);
			}
			staginDetail.setTotalCount(maxCount);
			staginDetail.setRemainCount(maxCount-count);
		}
	}

	private boolean cotainSuccessPay(String payOrderNo,String[] orderNos) {
		String[] payOrderNos = payOrderNo.split("X");
		if (payOrderNos.length <= 1) {
			return false;
		}
		String pre = payOrderNos[0];
		String stages = payOrderNos[1].split("Y")[0];
		String[] stage = stages.split("N");
		Integer startStage = Integer.parseInt(stage[0]);
		Integer endStage = Integer.parseInt(stage[1]);
		pre = pre.startsWith(OrderPrefixEnum.PAY.getCode()) ? pre.replaceFirst(OrderPrefixEnum.PAY.getCode(), OrderPrefixEnum.REPAYMENT.getCode()) : pre;
		List<String> borrowOrderNos = new ArrayList<>();
		for (int i = startStage; i <= endStage; i++) {
			StringBuilder borrowOrderNo = new StringBuilder(pre);
			borrowOrderNo.append("X").append(i);
			borrowOrderNos.add(borrowOrderNo.toString());
		}
		borrowOrderNos.retainAll(Arrays.asList(orderNos));
		if (CollectionUtils.isNotEmpty(borrowOrderNos)) {
			return true;
		}
		return false;

	}
}
