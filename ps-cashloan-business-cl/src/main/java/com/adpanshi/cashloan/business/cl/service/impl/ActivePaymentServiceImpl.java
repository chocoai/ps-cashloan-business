package com.adpanshi.cashloan.business.cl.service.impl;

import com.adpanshi.cashloan.business.cl.domain.*;
import com.adpanshi.cashloan.business.cl.mapper.*;
import com.adpanshi.cashloan.business.cl.model.*;
import com.adpanshi.cashloan.business.cl.model.pay.lianlian.RepaymentModel;
import com.adpanshi.cashloan.business.cl.model.pay.lianlian.constant.LianLianConstant;
import com.adpanshi.cashloan.business.cl.model.pay.lianlian.enums.LianlianParameterEnum;
import com.adpanshi.cashloan.business.cl.model.pay.lianlian.util.ActiveSignUtil;
import com.adpanshi.cashloan.business.cl.service.ActivePaymentService;
import com.adpanshi.cashloan.business.cl.service.BorrowMainProgressService;
import com.adpanshi.cashloan.business.cl.service.CreditsUpgradeService;
import com.adpanshi.cashloan.business.core.common.context.Global;
import com.adpanshi.cashloan.business.core.common.enums.OrderPrefixEnum;
import com.adpanshi.cashloan.business.core.common.exception.BussinessException;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.service.impl.BaseServiceImpl;
import com.adpanshi.cashloan.business.core.common.util.DateUtil;
import com.adpanshi.cashloan.business.core.common.util.OrderNoUtil;
import com.adpanshi.cashloan.business.core.domain.Borrow;
import com.adpanshi.cashloan.business.core.domain.BorrowMain;
import com.adpanshi.cashloan.business.core.mapper.BorrowMainMapper;
import com.adpanshi.cashloan.business.core.mapper.UserMapper;
import com.adpanshi.cashloan.business.core.model.BorrowModel;
import com.adpanshi.cashloan.business.core.model.CloanUserModel;
import com.adpanshi.cashloan.business.rule.domain.BankCard;
import com.adpanshi.cashloan.business.rule.mapper.BankCardMapper;
import com.adpanshi.cashloan.business.rule.mapper.ClBorrowMapper;
import com.adpanshi.cashloan.business.rule.model.BorrowTemplateModel;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tool.util.BigDecimalUtil;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 借款信息表ServiceImpl
 * 
 * @author
 * @version 1.0.0
 * @date 2017-07-14 10:36:53
 * Copyright 粉团网路 arc All Rights Reserved
 *
 *
 */
@Service("activePaymentBorrowService")
@Transactional(rollbackFor = Exception.class)
public class ActivePaymentServiceImpl extends BaseServiceImpl<Borrow, Long> implements ActivePaymentService {

	private static final Logger logger = LoggerFactory.getLogger(ActivePaymentServiceImpl.class);

	@Resource
	private ClBorrowMapper clBorrowMapper;
	@Resource
	private BorrowProgressMapper borrowProgressMapper;
	@Resource
	private BorrowRepayMapper borrowRepayMapper;
	@Resource
	private BorrowRepayLogMapper borrowRepayLogMapper;
	@Resource
	private BankCardMapper bankCardMapper;
	@Resource
	private UserMapper userMapper;
	@Resource
	private PayLogMapper payLogMapper;
	@Resource
	private PayReqLogMapper payReqLogMapper;
	@Resource
	private PayRespLogMapper payRespLogMapper;
	@Resource
	private UrgeRepayOrderMapper urgeRepayOrderMapper;
	@Resource
	private UrgeRepayOrderLogMapper urgeRepayOrderLogMapper;
	@Resource
	private CreditsUpgradeService creditsUpgradeService;
	@Resource
	private BorrowMainMapper borrowMainMapper;
	@Resource
	private BorrowMainProgressService borrowMainProgressService;

	public BaseMapper<Borrow, Long> getMapper() {
		return clBorrowMapper;
	}
    

	/**
	 * 借款详情.借款信息
	 * @date 20170714
	 * @author nmnl
	 * @param borrowId
	 * @param userId
	 * @return
	 */
	public Map<String, Object> syncQueryInfo(long borrowId,long userId){
		Date dt = new Date();
		Map<String, Object> rtMap = syncQueryVerification(borrowId,userId,dt);
		if(null == rtMap){
			logger.error(userId + " 借款详情 -> 验证失败! " + borrowId);
			throw new BussinessException(" 借款详情异常,请联系客服人员,谢谢！");
		}

		//统一转换..... start
		CloanUserModel cloanUserModel = (CloanUserModel) rtMap.get("cloanUserModel");
		BankCard bc = (BankCard) rtMap.get("bc");
		Borrow borrow = (Borrow) rtMap.get("borrow");
		BorrowRepay borrowRepay = (BorrowRepay) rtMap.get("borrowRepay");
		String orderNo = String.valueOf( rtMap.get("orderNo"));
		//统一转换..... end
		if(null != rtMap.get("type")){
			logger.error(userId + " 当前订单已经完成支付 -> 请查证! " + borrowId + " 支付方式 " + rtMap.get("type"));
			throw new BussinessException(" 当前订单已经完成支付,请联系客服人员,谢谢！");
		}

		//return map
		Map<String, Object> result = new HashMap<String, Object>();
		//query map
		//用户在你们平台绑定的手机号
		result.put("phone",cloanUserModel.getPhone());
		//用户在你们平台的注册时间
		if(null != cloanUserModel.getRegistTime())
			result.put("registTime",DateUtil.dateStr(cloanUserModel.getRegistTime(),DateUtil.DATEFORMAT_STR_011));
		else
			result.put("registTime",DateUtil.dateStr(new Date(),DateUtil.DATEFORMAT_STR_011));
		//身份证号
		result.put("idNo",cloanUserModel.getIdNo());
		//身份证姓名
		result.put("realName",cloanUserModel.getRealName());
		//银行卡号
		result.put("cardNo",bc.getCardNo());
		//本金 - 实际到账金额
		result.put("realAmount",borrow.getRealAmount());
		//服务费 = 原服务费 + 原信息认证费
		result.put("serviceAmount",new BigDecimal(borrow.getServiceFee())
				.add(new BigDecimal(borrow.getInfoAuthFee())));
		//利息
		result.put("interest",borrow.getInterest());
		//逾期费用
		result.put("penaltyAmout",borrowRepay.getPenaltyAmout());
		//总计
		BigDecimal totalAmount = new BigDecimal(borrow.getRealAmount())
				.add(new BigDecimal(borrow.getFee()))
				.add(new BigDecimal(borrowRepay.getPenaltyAmout()));
		result.put("totalAmount",totalAmount);
		//异步地址 .  测试使用
		result.put("notifyUrl",Global.getValue("server_host")+"/api/active/payment/asynNotify.htm");

		//商户系统单次成功操作唯一 -- 由于和代扣. 代发 怕引起冲突. 所有这里用主动扣款orderno
		result.put("orderNo",orderNo);
		//result.put("businessNo",Global.getValue(LianLianConstant.BUSINESS_NO));
		result.put("businessNo",Global.getValue(LianLianConstant.BUSINESS_NO_R));
		//result.put("privateKey",Global.getValue(LianLianConstant.PRIVATE_KEY));
		String sign = syncQuerySign(result,dt,userId);
		logger.info(" orderNo " + orderNo + " 借款详情 : sign " + sign);
		result.put("sign",sign);
		return result;
	}

	/**
	 * 同步查询验证
	 * @date 20170714
	 * @author nmnl
	 * @param result
	 * @return
	 */
	private String syncQuerySign(Map<String, Object> result,Date dt,long userId) {
		PayOrder payOrder = new PayOrder();
		payOrder.setBusi_partner("101001");
		payOrder.setNo_order(String.valueOf(result.get("orderNo")));
		SimpleDateFormat time = new SimpleDateFormat(DateUtil.DATEFORMAT_STR_011);
		payOrder.setDt_order(time.format(dt));
		//@remark:获取公司名称. @date:20171024 @author:nmnl
		payOrder.setName_goods(Global.getValue("title")+"还款支付");
		payOrder.setNotify_url(String.valueOf(result.get("notifyUrl")));
		payOrder.setSign_type(PayOrder.SIGN_TYPE_RSA);
		payOrder.setValid_order("100");
		payOrder.setUser_id(String.valueOf(userId));
		payOrder.setId_no(String.valueOf(result.get("idNo")));
		payOrder.setAcct_name(String.valueOf(result.get("realName")));
		//@remarks:测试环境与生产环境不一致.所以着么搞 @date:2017-08-22 @author:nmnl
		String appEnvironment = Global.getValue("app_environment");
		if("dev".equals(appEnvironment))
			payOrder.setMoney_order("0.01");
		else
			payOrder.setMoney_order(String.valueOf(result.get("totalAmount")));
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

	/*  实名类：
        需要传 基本参数中的，
        frms_ware_category 商品类目传 2010
        user_info_mercht_userno 你们平台的用户id
        user_info_dt_register 用户在你们平台的注册时间
        user_info_bind_phone 用户在你们平台绑定的手机号
        实名类参数中的
        user_info_identify_state  是否实名认证（）
        user_info_identify_type 实名认证方式
        user_info_full_name 用户姓名
        user_info_id_no  用户身份证号
    */
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


	/**
	 * 同步查询验证
	 * @date 20170714
	 * @author nmnl
	 * @param borrowId
	 * @param userId
	 * @param dt
	 * @return
	 */
	private Map<String, Object> syncQueryVerification(long borrowId,long userId,Date dt) {
		Map<String, Object> rtMap =  new HashMap<String, Object>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("borrowId",borrowId);
		paramMap.put("userId",userId);
		//兼容 - , 放款,代扣,主动还款
		//校验 . 需要考虑并发 20170714
		List<PayLog> payLogList = payLogMapper.listSelective(paramMap);
		for(PayLog payLog : payLogList){
			String payLogType = payLog.getType();
			String payLogState = payLog.getState();
			if(PayLogModel.TYPE_COLLECT.equals(payLogType) &&
					PayLogModel.STATE_PAYMENT_SUCCESS.equals(payLogState)) {
				rtMap.put("type", PayLogModel.TYPE_COLLECT);
				return rtMap;
			}
			if(PayLogModel.TYPE_OFFLINE_COLLECT.equals(payLogType) &&
					PayLogModel.STATE_PAYMENT_SUCCESS.equals(payLogState)){
				rtMap.put("type", PayLogModel.TYPE_OFFLINE_COLLECT);
				return rtMap;
			}
			if(PayLogModel.TYPE_LINE_PAYMENT.equals(payLogType) &&
					PayLogModel.STATE_PAYMENT_SUCCESS.equals(payLogState)){
				rtMap.put("type", PayLogModel.TYPE_LINE_PAYMENT);
				return rtMap;
			}
		}
		//提取用户所有信息
		CloanUserModel cloanUserModel = userMapper.getModel(userId);
		if(null == cloanUserModel){
			logger.error(userId + " 借款详情 : 查询的用户不存在" + borrowId);
			return null;
		}
		paramMap.clear();
		paramMap.put("userId", userId);
		BankCard bc = bankCardMapper.findSelective(paramMap);
		if(null == bc){
			logger.error(userId + " 借款详情 : 查询的用户银行卡不存在" + borrowId);
			return null;
		}
		Borrow borrow = getById(borrowId);
		if (borrow == null) {
			logger.error(userId + " 借款详情 : 查询的借款不存在" + borrowId);
			return null;
		}
		if( borrow.getUserId().compareTo(userId) != 0){
			logger.error(userId + " 借款详情 : 查询的借款不属于当前用户" + borrowId);
			return null;
		}
		paramMap.clear();
		paramMap.put("borrowId", borrowId);
		paramMap.put("state","20");
		BorrowRepay borrowRepay = borrowRepayMapper.findSelective(paramMap);
		if (null == borrowRepay) {
			logger.error(userId + " 借款详情 : 查询还款计划不合法" + borrowId);
			return null;
		}

		//@remarks: 认证支付根据用户行为.新增单子的生产 @date: 20170822 @author: nmnl
		//-----------------------------------------------------------------------------
		//总计
		BigDecimal totalAmount = new BigDecimal(borrow.getRealAmount())
				.add(new BigDecimal(borrow.getFee()))
				.add(new BigDecimal(borrowRepay.getPenaltyAmout()));
		//查出最近的一个单子.
		//@remarks: 绑定当前borrow_id.绑定用户 @date: 20170826 @author: nmnl
		paramMap.clear();
		paramMap.put("borrowId",borrowId);
		paramMap.put("userId",userId);
		paramMap.put("type",PayLogModel.TYPE_LINE_PAYMENT);
		PayLog newPayLog = payLogMapper.findSelectiveOne(paramMap);
		int row = 0;
		String orderNo = "";
		//判断需要新建.还是沿用老的paylog
		if(null != newPayLog){//非第一次进入
			orderNo = newPayLog.getOrderNo();
			paramMap = new HashMap<>();
			paramMap.put("orderNo",orderNo);
			PayReqLog payReqLog = payReqLogMapper.findSelectiveOne(paramMap);
			//payreq为空有三种情况.
            //1.用户点击查询之后.没有在此点击支付
            //2.app未同步请求.
            //3.连连未异步请求
			if(null != payReqLog) {
				int result = payReqLog.getResult();
				if(1 == result){//已经成功,不允许走下去
					rtMap.put("type", PayLogModel.TYPE_LINE_PAYMENT);
					return rtMap;
				}
                if (-1 == result) {//必须新建
                    orderNo = OrderNoUtil.genRepaymentOrderNo(); 
                    row = newPayLog(orderNo, borrowId, userId, totalAmount, bc, dt);
                    if (row != 1) {
                        logger.error(userId + " 借款详情 : 支付订单创建失败 " + borrowId + " 订单号: " + orderNo);
                        return null;
                    }
                }
            }else{
				Map<String, Object> payLogMap = new HashMap<String, Object>();
				payLogMap.put("orderNo", orderNo);
				payLogMap.put("id", newPayLog.getId());
				payLogMap.put("amount", totalAmount.doubleValue());
				payLogMap.put("payReqTime", dt);
				payLogMap.put("updateTime", dt);
				row = payLogMapper.updateSelective(payLogMap);
				if (row != 1) {
					logger.error(userId + " 借款详情 : 支付订单更新失败 " + borrowId + " 订单号: " + orderNo);
					return null;
				}
			}
		}else{
			orderNo = OrderNoUtil.genRepaymentOrderNo();
			row = newPayLog(orderNo,borrowId,userId,totalAmount,bc,dt);
			if(row != 1){
				logger.error(userId + " 借款详情 : 支付订单首次创建失败 " + borrowId + " 订单号: " +orderNo);
				return null;
			}
		}
		if(StringUtils.isBlank(orderNo)) {
			logger.error(userId + " 借款详情 : 支付订单,单号异常 " + borrowId + " 订单号: " + orderNo);
			return null;
		}
		rtMap.put("orderNo",orderNo);
		rtMap.put("borrowRepay",borrowRepay);
		rtMap.put("cloanUserModel",cloanUserModel);
		rtMap.put("borrow",borrow);
		rtMap.put("bc",bc);
		return rtMap;
	}
	private int newPayLog(String orderNo,Long borrowId,Long userId,
							  BigDecimal totalAmount,BankCard bc,Date dt){
		PayLog payLog = new PayLog();
		payLog.setOrderNo(orderNo);
		payLog.setBorrowMainId(borrowId);
		payLog.setUserId(userId);
		payLog.setAmount(totalAmount.doubleValue());
		payLog.setCardNo(bc.getCardNo());
		payLog.setBank(bc.getBank());
		payLog.setSource(PayLogModel.SOURCE_FUNDS_OWN);
		payLog.setType(PayLogModel.TYPE_LINE_PAYMENT);
		payLog.setState(PayLogModel.STATE_PAYMENT_WAIT);
		payLog.setScenes(PayLogModel.SCENES_REPAYMENT);
		payLog.setPayReqTime(dt);
		payLog.setCreateTime(dt);
		payLog.setUpdateTime(dt);
		return payLogMapper.save(payLog);
	}

	/**
	 * 借款信息.同步修改日志 pay,req,resp
	 * @date 20170714
	 * @author nmnl
	 * @param syncPaySub
	 * @return
	 */
	public boolean syncNotifyInfo(SyncPaySub syncPaySub) {
		logger.info(" 连连认证支付->app同步信息 "+ syncPaySub.toString());
		String orderNo = syncPaySub.getOrderNo();
		//query map
		Map<String,Object> iMap = new HashMap<String,Object>();
		iMap.put("orderNo",orderNo);
		PayLog PayLog = payLogMapper.findSelectiveOne(iMap);
		//验证是否存在主动还款订单.
		if(null == PayLog){
			logger.info(" 连连认证支付->app同步信息 暴力失败!!!!");
			return false;
		}
		if(payReqLogAddOrUpd(orderNo,syncPaySub,null,null) == 1)
			return true;
		else
			return false;
	}

	private int payReqLogAddOrUpd(String orderNo,SyncPaySub syncPaySub,String reqStr,Date reqDt ){
		int index = 0;
		//时间确定在统一时间
		try{
			logger.info("订单"+orderNo+"进入借款申请审核");
			Map<String,Object> iMap = new HashMap<String,Object>();
			iMap.put("orderNo",orderNo);
			PayReqLog payReqLog = payReqLogMapper.findSelective(iMap);
			Date now = new Date();
			/*RAtomicLong atomicLong = RedissonClientUtil.getInstance().getAtomicLong("saveReqLogOrderNo_"+orderNo);
			atomicLong.expire(2, TimeUnit.MINUTES);*/
//			logger.info("----------->payReqLog.data={},atomicLong={}.",new Object[]{payReqLog,atomicLong.get()});
			//防止同步比异步快
			if(null == payReqLog) {
				payReqLog = new PayReqLog();
				payReqLog.setOrderNo(orderNo);
				if (null != syncPaySub){
					payReqLog.setService(syncPaySub.getService());
					payReqLog.setParams(syncPaySub.getParams());
					payReqLog.setReqDetailParams(syncPaySub.getReqDetailParams());
					payReqLog.setReturnParams(syncPaySub.getReturnParams());
					payReqLog.setReturnTime(DateUtil.valueOf(syncPaySub.getReturnTime(), DateUtil.DATEFORMAT_STR_001));
					payReqLog.setResult(Integer.valueOf(syncPaySub.getResult()));
					payReqLog.setIp(syncPaySub.getIp());
				}
				if(null != reqStr && null != reqDt){
					payReqLog.setNotifyParams(reqStr);
					payReqLog.setNotifyTime(reqDt);
				}
				payReqLog.setReturnTime(now);
				payReqLog.setCreateTime(now);
//				atomicLong.getAndIncrement();
				index = payReqLogMapper.save(payReqLog);
			}else{
				Map<String, Object> payReqLogMap = new HashMap<String, Object>();
				payReqLogMap.put("id",payReqLog.getId());
				if (null != syncPaySub) {
					payReqLogMap.put("service", syncPaySub.getService());
					payReqLogMap.put("params", syncPaySub.getParams());
					payReqLogMap.put("reqDetailParams", syncPaySub.getReqDetailParams());
					payReqLogMap.put("returnParams", syncPaySub.getReturnParams());
					payReqLogMap.put("returnTime", syncPaySub.getReturnTime());
					payReqLogMap.put("result", syncPaySub.getResult());
					payReqLogMap.put("ip", syncPaySub.getIp());
				}
				if(null != reqStr && null != reqDt){
					payReqLogMap.put("notifyParams", reqStr);
					payReqLogMap.put("notifyTime", reqDt);
				}
				index = payReqLogMapper.updateSelective(payReqLogMap);
			}
		}finally{
			logger.info("订单"+orderNo+"-进入借款申请审核");
		}
		return index;
	}

	/**
	 * 借款信息.异步修改日志 pay,req,resp
	 * 因为是异步通知.所以用户看不到反馈信息.以下异常直接抛出.方便it找到出错原因
	 * @date 20170714
	 * @author nmnl
	 * @param model
	 * @param reqStr
	 * @return
	 */
	@Override
	public Map<String,Object> asynNotifyInfo(RepaymentModel model, String reqStr) {
		String orderNo = model.getNo_order();
		Date dt = new Date();
		logger.info(" 连连认证支付 -> 异步信息 " + dt);
		logger.info(" 连连认证支付 -> 异步信息 " + orderNo);
		logger.info(" 连连认证支付 -> 异步信息 " + model.toString());
		//query map
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("orderNo", orderNo);
		//支付信息
		PayLog payLog = payLogMapper.findSelective(paramMap);
		if(null  == payLog ){
			logger.info(" 连连认证支付 -> 异步信息 未找到支付记录 " + orderNo);
			return null;
		}
		if (!PayLogModel.STATE_PAYMENT_WAIT.equals(payLog.getState())) {
			logger.info(" 连连认证支付 -> 异步信息 当前支付状态不正确! " + orderNo);
			return null;
		}
		Long borrowId = payLog.getBorrowMainId();
		Long borrowMainId;
		Long userId = payLog.getUserId();
		//更新支付日志
		int msg = updateLog(payLog.getId(),orderNo,reqStr,dt);
		if (msg < 3) {
			throw new BussinessException(userId + " 连连认证支付 -> 异步信息 更新日志出错 " + borrowId);
		}

		boolean isAllFinish; //所有分期是否全部还清
		//查询贷款表
		//如果orderNo订单中包含N,则代表还款多期；否则为单期还款 @author yecy 20171225
		if (orderNo.contains("N")){
			//根据orderNo拆解获取cl_borrow中的orderNo
			List<Borrow> borrowList = getBorrowOrderNos(orderNo);
			logger.info("--------------->异步还款回调成功、开始更新子订单,子订单{}条.",new Object[]{borrowList.size()});
			for (Borrow borrow : borrowList){
				updateBorrowAndRepay(borrow,payLog);
			}
			borrowMainId = payLog.getBorrowMainId();
			isAllFinish = isAllFinished(borrowMainId);
		}else {
			Borrow borrow = clBorrowMapper.findByPrimary(borrowId);
			logger.info("--------------->异步还款回调成功、开始更新子订单,子订单1条订单号:{}.",new Object[]{borrow.getOrderNo()});
			updateBorrowAndRepay(borrow,payLog);
			// 因为cl_borrow_main表是后期添加的，所以cl_borrow表中之前的数据，没有main_id,这些数据只有单期，且id与main表中的id相同，所以直接用id操作 @author yecy 20171225
			borrowMainId = borrow.getBorrowMainId();
			if (borrowMainId == null || borrowMainId.equals(0L)){
				borrowMainId = borrowId;
				isAllFinish = true;
			}else {
				isAllFinish = isAllFinished(borrowMainId);
			}
		}

		// 更新总订单状态
		if (isAllFinish) {
			logger.info("--------------------->开始更新总订单状态,主订单id={}",new Object[]{borrowMainId});
			Map<String, Object> mainMap = new HashMap<>();
			mainMap.put("state", BorrowModel.STATE_FINISH);
			mainMap.put("id", borrowMainId);
			msg = borrowMainMapper.updateSelective(mainMap);
			if (msg < 1){
				throw new BussinessException("未找到cl_borrow_main表中id为" + borrowMainId + "的记录");
			}
			BorrowMain borrowMain = borrowMainMapper.findById(borrowMainId);
			//borrowMainProcess保存成功的记录
			borrowMainProgressService.savePressState(borrowMain, BorrowProgressModel.PROGRESS_REPAY_SUCCESS);
			//用户额度提升
//			int count = creditsUpgradeService.handleCreditsUpgrade(userId, ZHIMA_FEN.Zhimafen_620);
//			logger.info("*************************>usreId={}的用户额度提升业务逻辑处理结果，处理结果:{}.", new Object[]{userId, count > 0});
		}

		//修改返回值，borrowId 改为 borrowMainId @author yecy 20171226
		Map<String, Object> result = new HashMap<>();
		result.put("userId",userId);
		result.put("borrowMainId",borrowMainId);
		result.put("totalAmount",payLog.getAmount());
		return result;
	}

	/**
	 * 更新resp log 以及paylog
	 * @param payLogId
	 * @param orderNo
	 * @param reqStr
	 * @param dt
	 * @return
	 */
	private int updateLog(Long payLogId,String orderNo,String reqStr,Date dt){
		int i = 0;
		//更新 or 修改 req log
		i = payReqLogAddOrUpd(orderNo,null,reqStr,dt);
		//新增resp log.
		PayRespLog payRespLog = new PayRespLog(orderNo, PayRespLogModel.RESP_LOG_TYPE_NOTIFY,reqStr);
		i += payRespLogMapper.save(payRespLog);
		//更新pay log
		Map<String, Object> payLogMap = new HashMap<String, Object>();
		payLogMap.put("id",payLogId);
		payLogMap.put("state",PayLogModel.STATE_PAYMENT_SUCCESS);
		payLogMap.put("remark",PayLogModel.STATE_PAYMENT_SUCCESS_CN);
		i += payLogMapper.updateSelective(payLogMap);
		//----  日志更新 ----  日志更新 ----  日志更新 ----  日志更新 ----  日志更新
		return i;
	}

	/**
	 * 逻辑运算
	 * 根据还款日志的金额. 算出逾期天数。逾期金额 .
	 * 这样算的理由是. 如果用户晚上12点前主动还款. 但是12点后计划表会更新的.所以依靠连连的支付日志为主
	 * @param br
	 * @param payLog
	 * @param borrow
	 * @return
	 */
	private Map<String, String> returnLogical(BorrowRepay br,PayLog payLog,Borrow borrow) {
		Map<String, String> borrowRepayMap = new HashMap<String, String>();
		//默认正常还款
		borrowRepayMap.put("borrowRepayAmout",""+br.getAmount());
		borrowRepayMap.put("borrowAmout",""+borrow.getAmount());
		borrowRepayMap.put("borrowRepayId",""+br.getId());
		borrowRepayMap.put("userId",""+payLog.getUserId());
		//合并付款时，borrowId可能为空，所以取borrow的id @author yecy 20171227
		borrowRepayMap.put("borrowId",""+borrow.getId());
		borrowRepayMap.put("amout",""+payLog.getAmount());
		borrowRepayMap.put("orderNo",payLog.getOrderNo());
		borrowRepayMap.put("bank",payLog.getBank());
		borrowRepayMap.put("repayTime",""+DateUtil.dateStr(payLog.getUpdateTime(),DateUtil.DATEFORMAT_STR_001));
		borrowRepayMap.put("state", BorrowRepayModel.NORMAL_REPAYMENT);
		borrowRepayMap.put("penaltyDay", "0");
		borrowRepayMap.put("penaltyAmout", "0.00");
		//判断是逾期还款.还是正常还款
		//--------------------------  对比. 获取state
		SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd");
		//应还款时间.
		Date repayPlanTime = DateUtil.valueOf(time.format(br.getRepayTime()));
		//主动付款最新的还款时间
		Date repayTime = DateUtil.valueOf(time.format(payLog.getUpdateTime()));
		//实际还款时间在应还款时间之前或当天（不对比时分秒），重置逾期金额和天数
		if (repayTime.after(repayPlanTime)) {
			long lTime = repayTime.getTime() - repayPlanTime.getTime();
			long day = lTime / (24 * 60 * 60 * 1000);
			borrowRepayMap.put("state",BorrowRepayModel.OVERDUE_REPAYMENT);
			borrowRepayMap.put("penaltyDay", ""+day);
			BorrowMain borrowMain = borrowMainMapper.findById(borrow.getBorrowMainId());
			String templateInfo = borrowMain.getTemplateInfo();
            BorrowTemplateModel template = JSONObject.parseObject(templateInfo, BorrowTemplateModel.class);
            Double penalty = template.getPenalty();//罚息息率
            String cycle=template.getCycle();//每期的借款天数
			borrowRepayMap.put("penaltyAmout", ""+penaltyAmount(penalty,cycle,borrow.getTimeLimit(), day, borrow.getRealAmount()));//这里固定(day * 20)
		}
		//--------------------------  对比. 获取state
		return borrowRepayMap;
	}
	
	/**
	 * <p>罚金计算</p>
	 * @param penalty 逾期利率
	 * @param cycle   借款周期
	 * @param timeLimt 借款天数
	 * @param penaltyDay 逾期天数
	 * @param amount 借款金额
	 * @return double
	 * */
     double penaltyAmount(Double penalty,String cycle,String timeLimt,long penaltyDay,double amount){
		double totalPenaltyAmount=0;
		try {
			double penaltyAmout = 0.0;
			/*String penaltyFee = Global.getValue("penalty_fee");//逾期费率
			String[] penaltyFees = penaltyFee.split(",");
			//借款天数与逾期利率对应
			final String borrowDay = Global.getValue("borrow_day");//借款天数
			final String[] days = borrowDay.split(",");
			for (int j = 0; j < days.length; j++) {
				if (days[j].equals(timeLimt)) {
					penaltyAmout = BigDecimalUtil.decimal(BigDecimalUtil.mul(amount, Double.parseDouble(penaltyFees[j])),2);
				}
			}*/
			//这里保留5位小数目的就是为了与逾期JOB计算逾期费保持一致!表设置是保留后二位、自动舍弃!
			penaltyAmout = BigDecimalUtil.decimal(BigDecimalUtil.mul(amount, penalty),5);
			totalPenaltyAmount=penaltyDay*penaltyAmout;
		} catch (Exception e) {
			totalPenaltyAmount=penaltyDay*20;		//防止出现异常,给默认值并保证能让程序正常运行。
			logger.error("------------------->逻辑推算，逾期金额计算有误!取默认值:罚金=逾期天数   x 20.",e);
		}
		return totalPenaltyAmount ;
	}
     
	/**
	 * 更新还款计划和还款记录表
	 * @param borrowRepayMap
	 * @param borrowRepay 
	 * @return
	 */
	private int updateBorrowReplay(Map<String, String> borrowRepayMap,BorrowRepay borrowRepay,Long borrowMainId) {
		int i = 0;
		Long userId = MapUtils.getLong(borrowRepayMap, "userId");
		Long borrowId = MapUtils.getLong(borrowRepayMap, "borrowId");

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", borrowRepayMap.get("borrowRepayId"));
		paramMap.put("state", BorrowRepayModel.STATE_REPAY_YES);
		//TODO 不需要进行逻辑推算-直接更新其状态即可.
//		paramMap.put("penaltyDay", borrowRepayMap.get("penaltyDay")); 
//		paramMap.put("penaltyAmout", borrowRepayMap.get("penaltyAmout"));
		i = borrowRepayMapper.updateParam(paramMap);
		if(i > 0){
			// 生成还款记录
			BorrowRepayLog log = new BorrowRepayLog();
			log.setBorrowId(borrowId);
			log.setUserId(userId);
			log.setRepayId(Long.valueOf(borrowRepayMap.get("borrowRepayId")));
			log.setAmount(Double.valueOf(borrowRepayMap.get("borrowRepayAmout")));// 还款本金. 1k + (140 || 80)
			log.setRepayTime(DateUtil.parse(borrowRepayMap.get("repayTime"),DateUtil.DATEFORMAT_STR_001));// 实际还款时间
			log.setPenaltyDay(borrowRepayMap.get("penaltyDay"));
			log.setPenaltyAmout(Double.valueOf(borrowRepayMap.get("penaltyAmout")));
			//计算提前手续费 
			BorrowMain borrowMain = borrowMainMapper.findById(borrowMainId);
			TemplateInfoModel templateInfo= new TemplateInfoModel().parseTemplateInfo(borrowMain.getAmount(),borrowMain
					.getTemplateInfo());
			Double fee=StaginRepaymentPlanData.getPrepayment(borrowRepay.getRepayTime(), log.getRepayTime(), templateInfo.getInterest(),
					templateInfo.getCycle());
			log.setFee(fee);
			log.setSerialNumber(borrowRepayMap.get("bank"));
			log.setRepayAccount(borrowRepayMap.get("orderNo"));
			log.setRepayWay(BorrowRepayLogModel.REPAY_WAY_ACTIVE_TRANSFER);
			log.setCreateTime(DateUtil.getNow());
			i += borrowRepayLogMapper.save(log);
		}
		if (i < 2) {
			throw new BussinessException(userId + " 连连认证支付 -> 异步信息 更新还款信息出错 " + borrowId);
		}
		return i;
	}

	/**
	 * 更新借款表和借款进度状态
	 *
	 * @param borrowRepayMap
	 * @return
	 */
	private void updateBorrow(Map<String, String> borrowRepayMap){
		int i = 0;
		Long userId = MapUtils.getLong(borrowRepayMap, "userId");
		Long borrowId = MapUtils.getLong(borrowRepayMap, "borrowId");
		// 更新借款状态
		Map<String, Object> stateMap = new HashMap<>();
		stateMap.put("id", borrowId);
		stateMap.put("state", BorrowModel.STATE_FINISH);
		i = clBorrowMapper.updateSelective(stateMap);
		if(i > 0){
			// 添加借款进度
			BorrowProgress bp = new BorrowProgress();
			bp.setBorrowId(borrowId);
			bp.setUserId(userId);
			//BorrowModel.convertBorrowRemark(BorrowProgressModel.PROGRESS_REPAY_SUCCESS) 逻辑有问题. 只是恰巧类型相同而已
			//这里不沿用.以上信息.改为规定
			bp.setRemark("还款成功");
			bp.setState(BorrowProgressModel.PROGRESS_REPAY_SUCCESS);
			bp.setCreateTime(DateUtil.getNow());
			i += borrowProgressMapper.save(bp);
		}
		if (i < 2) {
			throw new BussinessException(userId + " 连连认证支付 -> 异步信息 更新借款表和借款进度状态出错 " + borrowId);
		}
	}


	/**
	 * 更新额度以及催收
	 *
	 * @param borrowRepayMap
	 * @return
	 */
	private void updateCrAndUrOrder(Map<String, String> borrowRepayMap){
		Long borrowId = MapUtils.getLong(borrowRepayMap, "borrowId");
		Long penaltyDay = MapUtils.getLong(borrowRepayMap, "penaltyDay");
		Long userId = MapUtils.getLong(borrowRepayMap, "userId");
		Double borrowAmout = MapUtils.getDouble(borrowRepayMap, "borrowAmout");

		// 信用额度修改，代码提取，并修改逻辑，去除额度开关，系统配置最小额度判断。这些都应该是下单的时候判断，不应该在此处判断 @author yecy 20171225
		creditsUpgradeService.modifyCreditAfterRepay(userId, borrowAmout);

		//更新催收订单中的状态
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("borrowId",borrowId);
		UrgeRepayOrder urgeRepayOrder = urgeRepayOrderMapper.findSelective(paramMap);
		if (null != urgeRepayOrder) {
			logger.debug("更新存在的催收订单中的状态");
			//如果没有逾期
			if (penaltyDay.equals(0L)) {
				urgeRepayOrderLogMapper.deleteByOrderId(urgeRepayOrder.getId());
				urgeRepayOrderMapper.deleteByBorrowId(borrowId);
			} else {
				UrgeRepayOrderLog orderLog = new UrgeRepayOrderLog();
				orderLog.setDueId(urgeRepayOrder.getId());
				orderLog.setBorrowId(urgeRepayOrder.getBorrowId());
				orderLog.setUserId(urgeRepayOrder.getUserId());
				orderLog.setRemark("用户还款成功");
				orderLog.setWay("10");
				orderLog.setCreateTime(DateUtil.getNow());
				orderLog.setState(UrgeRepayOrderModel.STATE_ORDER_SUCCESS);
				//新插入一条.催款记录日志
				urgeRepayOrderLogMapper.save(orderLog);
				//更新催收订单进度
				urgeRepayOrder.setCount(urgeRepayOrder.getCount()+1);
				urgeRepayOrder.setState(UrgeRepayOrderModel.STATE_ORDER_SUCCESS);
				urgeRepayOrderMapper.update(urgeRepayOrder);
			}
		}
	}

	private List<Borrow> getBorrowOrderNos(String payOrderNo){
		String[] orderNos = payOrderNo.split("X");
		String pre = orderNos[0];
		String stages = orderNos[1].split("Y")[0];
		String[] stage = stages.split("N");
		Integer startStage = Integer.parseInt(stage[0]);
		Integer endStage = Integer.parseInt(stage[1]);
		pre=pre.startsWith(OrderPrefixEnum.PAY.getCode())?pre.replaceFirst(OrderPrefixEnum.PAY.getCode(), OrderPrefixEnum.REPAYMENT.getCode()):pre;
		List<String> borrowOrderNos = new ArrayList<>();
		for (int i = startStage; i <= endStage; i++){
			StringBuilder borrowOrderNo = new StringBuilder(pre);
			borrowOrderNo.append("X").append(i);
			borrowOrderNos.add(borrowOrderNo.toString());
		}
		logger.info("主动付款，合并订单号为{}，包含的付款订单号为{}", payOrderNo, JSONObject.toJSONString(borrowOrderNos));
		return clBorrowMapper.findBorrowsByOrderNo(borrowOrderNos);
	}

	private void updateBorrowAndRepay(Borrow borrow,PayLog payLog){
		Long borrowId = borrow.getId();
		Long userId = borrow.getUserId();
		//查询还款进度
		HashMap<String, Object> paramMap = new HashMap<>();
		paramMap.put("userId", userId);
		paramMap.put("borrowId",borrowId);
		BorrowRepay borrowRepay = borrowRepayMapper.findSelective(paramMap);
		//逻辑推理
		Map<String, String> borrowRepayMap = returnLogical(borrowRepay,payLog,borrow);
		// 更新还款信息
		updateBorrowReplay(borrowRepayMap,borrowRepay,payLog.getBorrowMainId());
		//更新借款表和借款进度状态
		updateBorrow(borrowRepayMap);
		//更新额度以及催收
		updateCrAndUrOrder(borrowRepayMap);
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

}
