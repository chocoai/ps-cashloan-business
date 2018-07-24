package com.adpanshi.cashloan.business.api.controller;

import com.adpanshi.cashloan.business.cl.domain.*;
import com.adpanshi.cashloan.business.cl.model.PayLogModel;
import com.adpanshi.cashloan.business.cl.model.PayRespLogModel;
import com.adpanshi.cashloan.business.cl.model.pay.lianlian.PaymentModel;
import com.adpanshi.cashloan.business.cl.model.pay.lianlian.constant.LianLianConstant;
import com.adpanshi.cashloan.business.cl.service.*;
import com.adpanshi.cashloan.business.core.common.context.Global;
import com.adpanshi.cashloan.business.core.common.enums.OrderPrefixEnum;
import com.adpanshi.cashloan.business.core.common.util.ServletUtils;
import com.adpanshi.cashloan.business.core.common.web.controller.BaseController;
import com.adpanshi.cashloan.business.core.domain.Borrow;
import com.adpanshi.cashloan.business.core.domain.BorrowMain;
import com.adpanshi.cashloan.business.core.model.BorrowModel;
import com.adpanshi.cashloan.business.rule.model.BorrowTemplateModel;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import tool.util.BigDecimalUtil;
import tool.util.DateUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * 连连实时付款(代付)异步通知
 * @version 1.0.0
 * @date 2017年3月24日 下午2:49:56
 * Copyright 粉团网路 All Rights Reserved
 */
@Controller
@Scope("prototype")
public class PayController extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(PayController.class);

	@Resource
	private PayReqLogService payReqLogService;
	@Resource
	private PayRespLogService payRespLogService;
	@Resource
	private PayLogService payLogService;
	@Resource
	private ClBorrowService clBorrowService;
	@Resource
	private BorrowProgressService borrowProgressService;
	@Resource
	private BorrowRepayService borrowRepayService;
	@Resource
	private BorrowRepayLogService borrowRepayLogService;
	@Resource
	private ProfitAmountService profitAmountService;
	@Resource
	private EnjoysignRecordService enjoysignRecordService;
	@Resource
	private CreditsUpgradeService creditsUpgradeService;
	@Resource
	private BorrowMainService borrowMainService;
	@Resource
	private BorrowMainProgressService borrowMainProgressService;
	@Resource
	private LoanCityLogService loanCityLogService;
	@Resource
	private LoanCityService loanCityService;
	@Resource
	private ClSmsService clSmsService;


	/**
	 * 放款付款 - 异步通知处理
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value = "/pay/lianlian/paymentNotify.htm")
	public void paymentNotify(HttpServletRequest request) throws Exception {
		String params = getRequestParams(request);
		logger.info("实时付款(放款)- 异步通知" + params);
		PaymentModel model = JSONObject.parseObject(params, PaymentModel.class);
		if(!"dev".equals(Global.getValue("app_environment"))){
			boolean verifySignFlag = model.checkSign(model);
			if (!verifySignFlag) {
				logger.error("验签失败" + model.getNo_order());
				return;
			}
		}
		logger.debug("进入订单" + model.getNo_order() + "处理中.....");
		PayReqLog payReqLog = payReqLogService.findPayReqLogByLastOrderNo(model.getNo_order());
		if (payReqLog != null) {
			PayRespLog payRespLog = new PayRespLog(model.getNo_order(),PayRespLogModel.RESP_LOG_TYPE_NOTIFY,params);
			payRespLogService.save(payRespLog);
			// 更新reqLog
			modifyPayReqLog(payReqLog,params);
		}
		//这里需要查询最近一条记录(放款时主订单中的订单号要与支付订单保持一致)
		PayLog payLog=payLogService.findPayLogByLastOrderNoWithBorrowId(model.getNo_order(), null);
		if(null  == payLog ){
			logger.warn("未查询到对应的支付订单");
			return ;
		}
		if (PayLogModel.STATE_PAYMENT_WAIT.equals(payLog.getState())|| PayLogModel.STATE_AUDIT_PASSED.equals(payLog.getState())) {
			Long mainBorrowId = payLog.getBorrowMainId();
			// 代付成功，更新借款状态及支付订单 ，否则只更新订单状态
			if (LianLianConstant.RESULT_SUCCESS.equals(model.getResult_pay())) {
				// 修改借款状态
				// 添加放款时间和还款时间 @author yecy 20171224
				BorrowMain borrowMain = borrowMainService.getById(mainBorrowId);
				String settleDate = model.getSettle_date();
				DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyyMMdd");
				DateTime now = DateTime.now();
				DateTime payTime;
				if (StringUtils.isNotEmpty(settleDate)) {
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
				bp.setBorrowId(mainBorrowId);
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
				List<Borrow> borrowList = createBorrows(borrowMain, payTime.toDate(), borrowSize);
				//根据主借款流程，生成子借款流程
				createBorrowProcessList(borrowList,borrowMain);
				// 根据借款账单生成还款计划并授权(应还时间根据借款账单的创建时间生成)
				borrowRepayService.genRepayPlans(borrowList,borrowMain, false);
				//@remarks:以连连支付时间为准.然后算出用户逾期时间.@date:20170829 @author:nmnl
				// 生成还款计划并授权
//				borrowRepayService.genRepayPlan(borrow,com.adpanshi.cashloan.api.cr.model.getSettle_date());
				// 更新订单状态
				Map<String,Object> paramMap = new HashMap<String, Object>();
				paramMap.put("state", PayLogModel.STATE_PAYMENT_SUCCESS);
				paramMap.put("updateTime",DateUtil.getNow());
				paramMap.put("id",payLog.getId());
				payLogService.updateSelective(paramMap);

				logger.info("***********************************>放款异步回调，开始更新用户信用额度.....");
				
				//@remarks: 只有真正放款.才会更改额度. @date: 20170724 @author: nmnl
				int TMPcount=creditsUpgradeService.modifyCreditAfterLoan(borrowMain.getUserId(),borrowMain.getAmount());
				
				logger.info("***********************************>修改用户信用额度结果:{}......",new Object[]{TMPcount>0});
				
				//就目前而言、有可能出现回调多次、并且还是付款成功的、这里先查询如果已有订单且是成功的、则跳过自动签章流程...
//				String orderId=OrderNoUtil.getSerialNumber(EnjoysignConstant.ORDER_PREFIX);
//				logger.info("----------------------->借款人borId={},开始发起签署并进行后台自动签章...",new Object[]{borrowMain.getId()});
//				borrowMain.setLoanTime(payTime.toDate());//合同中的签署日期-应该是实际放款时间
//				int count=enjoysignRecordService.startSignWithAutoSilentSign(borrowMain, orderId);
//				logger.info("----------------------->签章结束，签章结果：{}....",new Object[]{count>0});

				// 放款成功，通知消贷同城 @author yecy 20180111
//				logger.info("***********************************>放款异步回调，通知消贷同城放款成功.....");
//				LoanCityLog log = loanCityLogService.findLogByBorrowId(mainBorrowId);
//				if (log != null) {
//					loanCityService.loanSuccess(mainBorrowId);
//				}
				//发送放款成功短信
//				TaskFactory factory = new TaskFactory();
//				Task task = factory.getTask("paymentTask");
//				String resultJson = task.sendSMS(borrowMain.getOrderNo());
//				clSmsService.chuanglanSms(resultJson,"payment");
				clSmsService.payment(payLog.getUserId(), payLog.getBorrowMainId(), payTime.toDate(), payLog.getAmount(), borrowList.get(0).getOrderNo());
			}else if(LianLianConstant.RESULT_FAILURE.equals(model.getResult_pay())){
				borrowMainService.updatePayState(mainBorrowId,BorrowModel.STATE_REPAY_FAIL);
				
				Map<String,Object> paramMap = new HashMap<String, Object>();
				paramMap.put("state", PayLogModel.STATE_PAYMENT_FAILED);
				paramMap.put("updateTime",DateUtil.getNow());
				paramMap.put("remark",model.getInfo_order());
				paramMap.put("id",payLog.getId());
				payLogService.updateSelective(paramMap);
				
			}
	
			Map<String, Object> result = new HashMap<String, Object>();
			result.put(LianLianConstant.RESPONSE_CODE,LianLianConstant.RESPONSE_SUCCESS_CODE);
			result.put(LianLianConstant.RESPONSE_MSG,LianLianConstant.RESPONSE_SUCCESS_VALUE);
			ServletUtils.writeToResponse(response, result);
		}else{
			logger.info("订单" + payLog.getOrderNo() + "已处理");
		}
	}

	/**
	 * 奖励发放 - 异步通知处理
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value = "/pay/lianlian/profitNotify.htm")
	public void profitNotify(HttpServletRequest request) throws Exception {
		String params = getRequestParams(request);
		logger.info("实时付款(取现) - 异步通知:" + params);

		PaymentModel model = JSONObject.parseObject(params, PaymentModel.class);
		boolean verifySignFlag = model.checkSign(model);

		if (!verifySignFlag) {
			logger.error("验签失败"+ model.getNo_order());
			return;
		}

		logger.debug("进入订单" + model.getNo_order() + "处理中.....");
		
		PayReqLog payReqLog = payReqLogService.findPayReqLogByLastOrderNo(model.getNo_order());
		
		if (payReqLog != null) {
			// 保存respLog
			PayRespLog payRespLog = new PayRespLog(model.getNo_order(),PayRespLogModel.RESP_LOG_TYPE_NOTIFY,params);
			payRespLogService.save(payRespLog);
			
			// 更新reqLog
			modifyPayReqLog(payReqLog,params);
		}
		
		PayLog payLog = payLogService.findByOrderNo(model.getNo_order());
		
		if(null  == payLog ){
			logger.warn("未查询到对应的支付订单");
			return ;
		}
		
		if (PayLogModel.STATE_PAYMENT_WAIT.equals(payLog.getState())
				|| PayLogModel.STATE_AUDIT_PASSED.equals(payLog.getState())) {

			// 代付成功，更新借款状态及支付订单 ，否则只更新订单状态
			if (LianLianConstant.RESULT_SUCCESS.equals(model.getResult_pay())) {
				// 更新取现金额， 添加取现记录
				profitAmountService.cash(payLog.getUserId(), payLog.getAmount());
				
				// 更新订单状态
				Map<String,Object> paramMap = new HashMap<String, Object>();
				paramMap.put("state", PayLogModel.STATE_PAYMENT_SUCCESS);
				paramMap.put("updateTime",DateUtil.getNow());
				paramMap.put("id",payLog.getId());
				payLogService.updateSelective(paramMap);
				
			}else if(LianLianConstant.RESULT_FAILURE.equals(model.getResult_pay())){
				Map<String,Object> paramMap = new HashMap<String, Object>();
				paramMap.put("state", PayLogModel.STATE_PAYMENT_FAILED);
				paramMap.put("updateTime",DateUtil.getNow());
				paramMap.put("id",payLog.getId());
				payLogService.updateSelective(paramMap);
			}
	
			Map<String, Object> result = new HashMap<String, Object>();
			result.put(LianLianConstant.RESPONSE_CODE,LianLianConstant.RESPONSE_SUCCESS_CODE);
			result.put(LianLianConstant.RESPONSE_MSG,LianLianConstant.RESPONSE_SUCCESS_VALUE);
			ServletUtils.writeToResponse(response, result);
		}else{
			logger.info("订单" + payLog.getOrderNo() + "已处理");
		}
	}
	
	
	
	/**
	 * 退还 - 异步通知处理
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value = "/pay/lianlian/refundNotify.htm")
	public void refundNotify(HttpServletRequest request) throws Exception {
		String params = getRequestParams(request);
		logger.info("实时付款(退还) - 异步通知:" + params);

		PaymentModel model = JSONObject.parseObject(params, PaymentModel.class);
		boolean verifySignFlag = model.checkSign(model);

		if (!verifySignFlag) {
			logger.error("验签失败" + model.getNo_order());
			return;
		}

		logger.debug("进入订单" + model.getNo_order() + "处理中.....");
		
		PayReqLog payReqLog = payReqLogService.findPayReqLogByLastOrderNo(model.getNo_order());
		
		if (payReqLog != null) {
			// 保存respLog
			PayRespLog payRespLog = new PayRespLog(model.getNo_order(),PayRespLogModel.RESP_LOG_TYPE_NOTIFY,params);
			payRespLogService.save(payRespLog);
			
			// 更新reqLog
			modifyPayReqLog(payReqLog,params);
		}
		
		PayLog payLog = payLogService.findByOrderNo(model.getNo_order());
		
		if(null  == payLog ){
			logger.warn("未查询到对应的支付订单");
			return ;
		}
		
		if (PayLogModel.STATE_PAYMENT_WAIT.equals(payLog.getState())
				|| PayLogModel.STATE_AUDIT_PASSED.equals(payLog.getState())) {

			// 代付成功 ，否则只更新订单状态
			if (LianLianConstant.RESULT_SUCCESS.equals(model.getResult_pay())) {
				
				// 查询还款记录
				Map<String, Object> repayLogMap =  new HashMap<String, Object>();
				repayLogMap.put("borrowId", payLog.getBorrowMainId());
				repayLogMap.put("userId", payLog.getUserId());
				BorrowRepayLog repayLog = borrowRepayLogService.findSelective(repayLogMap);
				
				// 更新还款记录
				Map<String, Object> refundDeductionMap = new HashMap<String, Object>();
				refundDeductionMap.put("id", repayLog.getId());
				refundDeductionMap.put("refundDeduction", - payLog.getAmount());
				refundDeductionMap.put("payTime", DateUtil.getNow());
				borrowRepayLogService.refundDeduction(refundDeductionMap);
				
				// 更新订单状态
				Map<String,Object> paramMap = new HashMap<String, Object>();
				paramMap.put("state", PayLogModel.STATE_PAYMENT_SUCCESS);
				paramMap.put("updateTime",DateUtil.getNow());
				paramMap.put("id",payLog.getId());
				payLogService.updateSelective(paramMap);
				
			}else if(LianLianConstant.RESULT_FAILURE.equals(model.getResult_pay())){
				Map<String,Object> paramMap = new HashMap<String, Object>();
				paramMap.put("state", PayLogModel.STATE_PAYMENT_FAILED);
				paramMap.put("updateTime",DateUtil.getNow());
				paramMap.put("id",payLog.getId());
				payLogService.updateSelective(paramMap);
			}
			
			Map<String, Object> result = new HashMap<String, Object>();
			result.put(LianLianConstant.RESPONSE_CODE,LianLianConstant.RESPONSE_SUCCESS_CODE);
			result.put(LianLianConstant.RESPONSE_MSG,LianLianConstant.RESPONSE_SUCCESS_VALUE);
			ServletUtils.writeToResponse(response, result);
		}else{
			logger.info("订单" + payLog.getOrderNo() + "已处理");
		}
	}
	
	private void modifyPayReqLog (PayReqLog payReqLog,String params){
		payReqLog.setNotifyParams(params);
		payReqLog.setNotifyTime(DateUtil.getNow());
		payReqLogService.updateById(payReqLog);
	}

	private List<Borrow> createBorrows(BorrowMain borrowMain, Date payTime, Long borrowSize) {
		List<Borrow> borrowList = new ArrayList<>(borrowSize.intValue());
		Borrow firstBorrow = getFirstBorrow(borrowMain, borrowSize.doubleValue(), payTime);
		borrowList.add(0, firstBorrow);


		Double otherAmount = div(borrowMain.getAmount(),borrowSize.doubleValue());
		Double otherRealAmount = div(borrowMain.getRealAmount(),borrowSize.doubleValue());
		Double otherFee = div(borrowMain.getFee(),borrowSize.doubleValue());
		Long otherTimeLimit = Long.valueOf(borrowMain.getTimeLimit())/borrowSize;

		Date beginTime = DateUtil.rollDay(payTime,Integer.valueOf(firstBorrow.getTimeLimit()));
		for (int i = 1; i < borrowSize; i++) {
			Borrow borrow = new Borrow();
			borrow.setAmount(otherAmount);
			borrow.setRealAmount(otherRealAmount);
			borrow.setFee(otherFee);
			borrow.setTimeLimit(String.valueOf(otherTimeLimit));

			Date createTime =  DateUtil.rollDay(beginTime,(i-1)*otherTimeLimit.intValue());
			borrow.setCreateTime(createTime);
			String orderNo = getOrderNo(borrowMain,i + 1);
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
		for (Borrow borrow : borrowList){
			for (BorrowMainProgress mainProcess : mainProcessList){
				if (BorrowModel.STATE_WAIT_AGREE.equals(mainProcess.getState())
						|| BorrowModel.STATE_WAIT_REPAY.equals(mainProcess.getState())){
					// 28,29状态不保存到cl_borrow_progress表中
					continue;
				}
				BorrowProgress bp = new BorrowProgress();
				BeanUtils.copyProperties(mainProcess,bp,"id","borrowId");
				bp.setBorrowId(borrow.getId());

				processList.add(bp);
			}
		}
		borrowProgressService.saveAll(processList);
	}

	/**
	 * 保留两位小数，向下舍入
	 * @param v1
	 * @param v2
	 * @return
	 */
	private double div(Double v1,Double v2){
		BigDecimal d1 = new BigDecimal(v1);
		BigDecimal d2 = new BigDecimal(v2);
		BigDecimal result = d1.divide(d2,2, RoundingMode.DOWN);
		return result.doubleValue();
	}

	private Borrow getFirstBorrow(BorrowMain borrowMain, Double borrowSize, Date createTime){
		Borrow borrow = new Borrow();

		Double otherSize = BigDecimalUtil.sub(borrowSize,1d);
		Double mainAmount = borrowMain.getAmount();
		Double otherAmount = div(mainAmount,borrowSize);
		Double amount = BigDecimalUtil.sub(mainAmount,BigDecimalUtil.mul(otherAmount,otherSize));
		borrow.setAmount(amount);

		Double mainRealAmount = borrowMain.getRealAmount();
		Double otherRealAmount = div(mainRealAmount,borrowSize);
		Double realAmount =  BigDecimalUtil.sub(mainRealAmount,BigDecimalUtil.mul(otherRealAmount,otherSize));
		borrow.setRealAmount(realAmount);

		Double mainFee = borrowMain.getFee();
		Double otherFee = div(mainFee,borrowSize);
		Double fee =  BigDecimalUtil.sub(mainFee,BigDecimalUtil.mul(otherFee,otherSize));
		borrow.setFee(fee);

		Long mainTimeLimit = Long.valueOf(borrowMain.getTimeLimit());
		Long otherTimeLimit = mainTimeLimit/borrowSize.longValue();
		Long timeLimit = mainTimeLimit - otherTimeLimit * otherSize.longValue();
		borrow.setTimeLimit(String.valueOf(timeLimit));

		borrow.setCreateTime(createTime);

		String orderNo = getOrderNo(borrowMain,1);
		borrow.setOrderNo(orderNo);

		fillBorrow(borrow,borrowMain);
		return borrow;
	}

	private String getOrderNo(BorrowMain borrowMain,Integer num){
		StringBuilder orderNo = new StringBuilder();
		orderNo.append(OrderPrefixEnum.REPAYMENT.getCode()).append(borrowMain.getOrderNo().substring(1)).append("X").append(num);
		return orderNo.toString();
	}

	private void fillBorrow(Borrow borrow,BorrowMain borrowMain){
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
