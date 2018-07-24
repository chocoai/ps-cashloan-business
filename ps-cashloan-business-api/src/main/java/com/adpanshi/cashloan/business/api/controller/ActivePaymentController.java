package com.adpanshi.cashloan.business.api.controller;

import com.adpanshi.cashloan.business.cl.domain.SyncPaySub;
import com.adpanshi.cashloan.business.cl.model.pay.lianlian.RepaymentModel;
import com.adpanshi.cashloan.business.cl.model.pay.lianlian.constant.LianLianConstant;
import com.adpanshi.cashloan.business.cl.service.ActivePaymentService;
import com.adpanshi.cashloan.business.cl.service.ClSmsService;
import com.adpanshi.cashloan.business.core.common.cache.RedissonClientUtil;
import com.adpanshi.cashloan.business.core.common.context.Constant;
import com.adpanshi.cashloan.business.core.common.context.Global;
import com.adpanshi.cashloan.business.core.common.util.ServletUtils;
import com.adpanshi.cashloan.business.core.common.util.StringUtil;
import com.adpanshi.cashloan.business.core.common.web.controller.BaseController;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.MapUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.redisson.core.RLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import tool.util.IPUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 连连认证支付 同步查询,更新 && 异步通知
 * @version 1.0.0
 * @date 2017年7月13日 下午19:27
 * Copyright 粉团网路 All Rights Reserved
 */
@Controller
@Scope("prototype")
public class ActivePaymentController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ActivePaymentController.class);

	@Resource
	private ClSmsService clSmsService;

	@Resource
	private ActivePaymentService activePaymentService;
	/**
	 * @deprecated 已切换至分期-主动还款接口
	 * 主动还款(认证支付) - app 同步查询订单, borrow
	 * 始终成功,如果data是null那么主动还款拒绝
	 * @date : 20170713
	 * @author : nmnl
	 * @param userId
	 * @param borrowId
	 * @throws Exception
	 */
	/*@RequestMapping(value = "/com.adpanshi.com.adpanshi.cashloan.api/act/active/payment/syncQuery.htm", method = RequestMethod.POST)
	public void syncQuery(@RequestParam(value = "userId") long userId,
						  @RequestParam(value = "borrowId") long borrowId) {
		logger.info(userId + " 主动还款(认证支付) - app 同步查询订单 " + borrowId);
		//返回父map
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		//返回子集map
		result.put(Constant.RESPONSE_DATA, activePaymentService.syncQueryInfo(borrowId,userId));
		result.put(Constant.RESPONSE_CODE_MSG, "获取成功");
		ServletUtils.writeToResponse(response, result);
	}*/

	/**
	 * 主动还款(认证支付) - app 同步修改日志 pay,req,resp
	 */
	@RequestMapping(value = "/com/adpanshi/cashloan/api/act/active/payment/syncNotify.htm", method = RequestMethod.POST)
	public void syncUpdate(@RequestParam(value = "returnParams") String returnParams,
						   @RequestParam(value = "returnTime") String returnTime,
						   @RequestParam(value = "reqDetailParams") String reqDetailParams,
						   @RequestParam(value = "com.adpanshi.cashloan.api.cr.service") String service,
						   @RequestParam(value = "orderNo") String orderNo,
						   @RequestParam(value = "result") String result,
						   @RequestParam(value = "params") String params,
						   @RequestParam(value = "createTime") String createTime){
		SyncPaySub syncPaySub = new SyncPaySub(returnParams, returnTime, reqDetailParams, service, orderNo, result, params, createTime);
		logger.info(" 主动还款(认证支付) - app 同步修改日志 syncPaySub " + syncPaySub.toString());
		if (null != ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())) {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
			syncPaySub.setIp(IPUtil.getRemortIP(request));
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		RLock rlock=RedissonClientUtil.getInstance().getLock("syncNotify"+orderNo);
		boolean flag=false;
		try {
			rlock.lock(30, TimeUnit.SECONDS);
			logger.info("---------------->[RLock]进入主动还款,订单号:{},加锁成功...",new Object[]{orderNo});
			flag=activePaymentService.syncNotifyInfo(syncPaySub);
		}finally {
			rlock.unlock();
			logger.info("---------------->[RLock]进入主动还款,订单号:{},锁释放成功...",new Object[]{orderNo});
		}
		resultMap.put(Constant.RESPONSE_DATA, flag);
		resultMap.put(Constant.RESPONSE_CODE_MSG, "获取成功");
		ServletUtils.writeToResponse(response, resultMap);
	}

	/**
	 * 主动还款(认证支付) - 异步通知处理
	 * 第三方调用. 连连
	 * @date : 20170713
	 * @author : nmnl
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value = "/com/adpanshi/cashloan/api/active/payment/asynNotify.htm",method = RequestMethod.POST)
	public void asynNotify(HttpServletRequest request) throws Exception {
		String reqStr = getRequestParams(request);
		boolean bl = true;
		//orderNo
		String orderNo = "";
		//用一部分字段  PayDataBean -- 验签
		RepaymentModel model = null;
		logger.info(" 主动还款(认证支付) 异步通知：" + reqStr);
		if (StringUtil.isBlank(reqStr)){
			logger.info(" 主动还款(认证支付) 异步通知 -> 获取数据失败 " + reqStr);
			bl = false;
		}else{
			//用一部分字段  PayDataBean -- 验签
			model = JSONObject.parseObject(reqStr,RepaymentModel.class);
			orderNo = model.getNo_order();
			if(!"dev".equals(Global.getValue("app_environment"))){
				boolean verifySignFlag  = model.checkSign(model);
				if (!verifySignFlag) {
					logger.error(" 主动还款(认证支付) 异步通知 -> 验签失败 " + orderNo);
					bl = false;
				}
			}

			if (!LianLianConstant.RESULT_SUCCESS.equals(model.getResult_pay())) {
				logger.info(" 连连认证支付 -> 异步信息 失败!!!! " + orderNo);
				//需要修改支付日志. 暂时不修改~~~ 20170718
				bl = false;
			}
		}

		//返回信息
		Map<String, Object> result = new HashMap<>();
		if(!bl){
			result.put(LianLianConstant.RESPONSE_CODE,LianLianConstant.RESPONSE_FAIL_CODE);
			result.put(LianLianConstant.RESPONSE_MSG,LianLianConstant.RESPONSE_FAIL_VALUE);
		}else {
			logger.info("进入订单 "+ orderNo +" 处理中.....");

			Map<String, Object> map;
			RLock rlock= RedissonClientUtil.getInstance().getLock("syncNotify"+orderNo);
			try {
				rlock.lock(30, TimeUnit.SECONDS);
				logger.info("---------------->[RLock]进入主动还款-异步通知处理,订单号:{},加锁成功...",orderNo);
				map=activePaymentService.asynNotifyInfo(model, reqStr);
			}finally {
				rlock.unlock();
				logger.info("---------------->[RLock]进入主动还款-异步通知处理,订单号:{},锁释放成功...",orderNo);
			}
			if (null != map){
				//修改短信发送内容 @author yecy 20171223
				Long userId = MapUtils.getLong(map, "userId");
				Long borrowMainId = MapUtils.getLong(map, "borrowMainId");
				Double totalAmount = MapUtils.getDouble(map, "totalAmount");
//				之前还款时间为borrowRepayLog记录的创建时间，因为增加分期业务，borrowRepayLog记录可能会有多条，时间不好取，
//				 所以给用户显示的还款时间直接为异步返回的时间 @author yecy 20171223
				String settleDate = model.getSettle_date();
				DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyyMMdd");
				DateTime repayTime = DateTime.parse(settleDate, dtf);

				clSmsService.activePayment(userId, borrowMainId, repayTime.toDate(), totalAmount, orderNo);
//				TaskFactory factory = new TaskFactory();
//				Task task = factory.getTask("repayTask");
//				String resultJson = task.sendSMS(orderNo);
//				clSmsService.chuanglanSms(resultJson,"activePayment");

			}

			result.put(LianLianConstant.RESPONSE_CODE,LianLianConstant.RESPONSE_SUCCESS_CODE);
			result.put(LianLianConstant.RESPONSE_MSG,LianLianConstant.RESPONSE_SUCCESS_VALUE);
		}
		ServletUtils.writeToResponse(response, result);
	}
	
	
}
