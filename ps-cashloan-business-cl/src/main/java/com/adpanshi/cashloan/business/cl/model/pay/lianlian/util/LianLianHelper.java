package com.adpanshi.cashloan.business.cl.model.pay.lianlian.util;

import com.adpanshi.cashloan.business.cl.domain.PayReqLog;
import com.adpanshi.cashloan.business.cl.model.pay.lianlian.*;
import com.adpanshi.cashloan.business.cl.model.pay.lianlian.constant.LianLianConstant;
import com.adpanshi.cashloan.business.cl.service.PayReqLogService;
import com.adpanshi.cashloan.business.core.common.context.Global;
import com.adpanshi.cashloan.business.core.common.util.HttpsUtil;
import com.adpanshi.cashloan.business.core.common.util.ReflectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import tool.util.BeanUtil;
import tool.util.DateUtil;
import tool.util.IPUtil;
import tool.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * 连连支付
 * 

 * @version 1.0.0
 * @date 2017年3月6日 下午4:36:16
 * Copyright 粉团网路 All Rights Reserved
 *
 * 
 *
 */

public class LianLianHelper {

	public static final Logger logger = LoggerFactory.getLogger(LianLianHelper.class);
	
	private BasePaymentModel doSubmit(BasePaymentModel model,boolean instantResultPay) {
		//@remarks: 增加日志输出. @date:20170918 @author:nmnl
		logger.info(" 20170918 追加支付日志 " + model.toString());
		// 保存请求记录
		saveReqLog(model,instantResultPay);

		Map<String, String> map = ReflectUtil.fieldValueToMap(model,model.reqParamNames());
		
		String jsonStr = JSONObject.toJSONString(map);
		String resp = null;
		try {
			// 获取系统参数中连连支付启用状态
			String lianlianSwitch = Global.getValue("lianlian_switch");

			if (StringUtil.isNotBlank(lianlianSwitch) && "1".equals(lianlianSwitch)) {
				logger.info("请求地址：" + model.getSubUrl());
				resp = HttpsUtil.postStrClient(model.getSubUrl(), jsonStr);
			} else {
				logger.info("关闭连连支付,模拟返回结果");
				resp = "{\"ret_code\":\"0000\",\"ret_msg\":\"模拟交易成功\"}";
			}

			model.response(resp);
			 
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}catch (Exception e){
			//@remarks: 增加连连请求接口异常日志 @date:20170829 @author:nmnl
			logger.error("请求连连异常...: " + model.toString());
			logger.error(e.getMessage(), e);
		}

		// 更新请求记录
		modifyReqLog(model, resp);
		//@remarks: 增加日志输出. @date:20170918 @author:nmnl
		logger.info(" 20170918 追加支付日志 " + resp);
		return model;
	}
	
	/**
	 * 连连支付 实时付款 - 付款交易
	 * 
	 * @param model
	 * @return
	 */
	public BasePaymentModel payment(PaymentModel model) {
		model.sign();
		doSubmit(model,true);
		return model;
	}

	/**
	 * 连连支付 实时付款 - 付款交易
	 * @author: nmnl
	 * @date: 20180316
	 * @param model
	 * @return
	 */
	public BasePaymentModel newPayment(PaymentModel model) {
//		com.adpanshi.cashloan.api.com.adpanshi.cashloan.api.cr.model.newSign();
		doSubmit(model,true);
		return model;
	}

	/**
	 * 连连支付 实时付款 - 确认付款
	 * 
	 * @param model
	 * @return
	 */
	public BasePaymentModel confirmPayment(ConfirmPaymentModel model) {
		model.newSign();
		doSubmit(model,true);
		return model;
	}

	/**
	 * 连连支付 实时付款 - 查询付款交易
	 * 
	 * @param model
	 * @return
	 */
	public BasePaymentModel queryPayment(QueryPaymentModel model) {
		model.sign();
		doSubmit(model,true);
		return model;
	}

	

	/**
	 * 连连支付 分期付 - 授权申请
	 * 
	 * @param model
	 * @return
	 */
	public BasePaymentModel authApply(AuthApplyModel model) {
		model.sign();
		doSubmit(model,false);
		return model;
	}

	/**
	 * 查询签约结果
	 * @param model
	 * @return
	 */
	public BasePaymentModel queryAuthSign(QueryAuthSignModel model){
		model.sign();
		doSubmit(model,false);
		return model;
	}
	
	/**
	 * 取消签约授权
	 * @param model
	 * @return
	 */
	public BasePaymentModel cancelAuthSign(CancelAuthSignModel model){
		model.sign();
		doSubmit(model,false);
		return model;
	}
	

	/**
	 * 连连支付 分期付 - 还款计划变更
	 * 
	 * @param model
	 * @return
	 */
	public BasePaymentModel repaymentPlanChange(RepaymentPlanModel model) {
		model.sign();
		doSubmit(model,false);
		return model;
	}

	/**
	 * 连连支付 分期付 - 银行还款扣款
	 * 
	 * @param model
	 * @return
	 */
	public BasePaymentModel repayment(RepaymentModel model) {
		model.sign();
		doSubmit(model,false);
		return model;
	}

	/**
	 * 连连支付 分期付 - 银行还款扣款查询
	 * 
	 * @param model
	 * @return
	 */
	public BasePaymentModel queryRepayment(QueryRepaymentModel model) {
		model.sign();
		doSubmit(model,false);
		return model;
	}
	
	/**
	 * <p>连连订单查询  - 商户支付结果查询 </p>
	 * @param model
	 * @return BasePaymentModel
	 * */
	public BasePaymentModel orderQuery(StagesOrderRecordModel model){
		model.sign();
		Map<String, String> map = ReflectUtil.fieldValueToMap(model,model.reqParamNames());
		String jsonStr = JSONObject.toJSONString(map);
		String resp = null;
		try {
			// 获取系统参数中连连支付启用状态
			String lianlianSwitch = Global.getValue("lianlian_switch");
			if (StringUtil.isNotBlank(lianlianSwitch) && "1".equals(lianlianSwitch)) {
				logger.info("请求地址：" + model.getSubUrl());
				resp = HttpsUtil.postStrClient(model.getSubUrl(), jsonStr);
			} else {
				logger.info("关闭连连支付,模拟返回结果");
				resp = "{\"ret_code\":\"0000\",\"ret_msg\":\"模拟交易成功\"}";
			}
			model.response(resp);
			model.setReturnParams(resp);
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}catch (Exception e){
			logger.error("", e);
		}
		return model;
	}
	
	/**
	 * <p>连连订单查询  - 商户支付结果查询 </p>
	 * @param model
	 * @return BasePaymentModel
	 * */
	public BasePaymentModel orderQuery(InstantOrderRecordModel model){
		model.sign();
		Map<String, String> map = ReflectUtil.fieldValueToMap(model,model.reqParamNames());
		String jsonStr = JSONObject.toJSONString(map);
		String resp = null;
		try {
			// 获取系统参数中连连支付启用状态
			String lianlianSwitch = Global.getValue("lianlian_switch");
			if (StringUtil.isNotBlank(lianlianSwitch) && "1".equals(lianlianSwitch)) {
				logger.info("请求地址：" + model.getSubUrl());
				resp = HttpsUtil.postStrClient(model.getSubUrl(), jsonStr);
			} else {
				logger.info("关闭连连支付,模拟返回结果");
				resp = "{\"ret_code\":\"0000\",\"ret_msg\":\"模拟交易成功\"}";
			}
			model.response(resp);
			model.setReturnParams(resp);
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}catch (Exception e){
			logger.error("", e);
		}
		return model;
	}
	
	/**
	 * 保存请求记录
	 * 
	 * @param model
	 * @param instantResultPay (true:实时付，false:分期付)
	 */
	private void saveReqLog(BasePaymentModel model,boolean instantResultPay) {
		PayReqLogService payReqLogService = (PayReqLogService) BeanUtil.getBean("payReqLogService");
		PayReqLog exits=payReqLogService.findPayReqLogByLastOrderNo(model.getOrderNo());
		if(instantResultPay && null!=exits){
			logger.info("------------------->同步请求记录PayReqLog={}.",new Object[]{JSONObject.toJSONString(model)});
			logger.info("------------------->orderNo={}的支付同步请求记录(PayReqLog)已经存在，不做数据存储。",new Object[]{model.getOrderNo()});
			return ;
		}
		
		/*RAtomicLong atomicLong = RedissonClientUtil.getInstance().getAtomicLong("saveReqLogOrderNo_"+com.adpanshi.cashloan.api.com.adpanshi.cashloan.api.cr.model.getOrderNo());
		atomicLong.expire(2, TimeUnit.MINUTES);
		logger.info("---------->LianLianHelper.saveReqLogOrderNo__{}",new Object[]{com.adpanshi.cashloan.api.com.adpanshi.cashloan.api.cr.model.getOrderNo()});
		if(atomicLong.get()>0){
			logger.error("------------------>警告!出现并发请求-已跳过业务处理!");
			return;
		}*/
		PayReqLog payReqLog = new PayReqLog();
		payReqLog.setOrderNo(model.getOrderNo());
		payReqLog.setService(model.getService());
		payReqLog.setCreateTime(DateUtil.getNow());
		payReqLog.setParams(ReflectUtil.fieldValueToJson(model,model.signParamNames()));
		payReqLog.setReqDetailParams(ReflectUtil.fieldValueToJson(model,model.reqParamNames()));
		
		if (null != ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())){
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
			String ip = IPUtil.getRemortIP(request);
			payReqLog.setIp(ip);
		}
		payReqLogService.save(payReqLog);
//		atomicLong.getAndIncrement();
	}

	/**
	 * 更新返回数据
	 * 
	 * @param model
	 * @param resp
	 */
	private void modifyReqLog(BasePaymentModel model, String resp) {
		PayReqLogService payReqLogService = (PayReqLogService) BeanUtil.getBean("payReqLogService");
		PayReqLog reqLog = payReqLogService.findPayReqLogByLastOrderNo(model.getOrderNo());
		if (null == reqLog) {
			return;
		}
		reqLog.setReturnParams(resp);
		reqLog.setReturnTime(DateUtil.getNow());
		payReqLogService.updateById(reqLog);
	}

	/**
	 * <p>连连绑卡: 查询当前卡号属于哪家银行 </p>
	 * @param cardNo
	 * @param cardName
	 * */
	public boolean vBinCard(String cardNo,String cardName){
		boolean bl = false;
		Map<String,String> signMap = new HashMap();
		signMap.put("api_version","1.0");
		signMap.put("pay_type","2");
		signMap.put("flag_amt_limit","0");
		signMap.put("oid_partner",Global.getValue(LianLianConstant.BUSINESS_NO));
		signMap.put("card_no",cardNo);
		signMap.put("sign_type","RSA");
		signMap.put("sign",SignUtil.genRSASign(JSON.toJSONString(signMap)));
		String jsonStr = JSONObject.toJSONString(signMap);
		String resp = null;
		try {
			logger.info("请求参数: "+jsonStr);
			// 获取系统参数中连连支付启用状态
			String lianlianSwitch = Global.getValue("lianlian_switch");
			if (StringUtil.isNotBlank(lianlianSwitch) && "1".equals(lianlianSwitch)) {
				String bankcardbin = Global.getValue("lianlian_bankcardbin");
				if(StringUtils.isNotEmpty(bankcardbin)){
					logger.info(" 请求地址 " + bankcardbin);
					resp = HttpsUtil.postStrClient(bankcardbin, jsonStr);
				}
			} else {
				logger.info("关闭连连支付,模拟返回结果");
				resp = "{\"ret_code\":\"0000\",\"ret_msg\":\"模拟交易成功\"}";
			}
			if(null != resp) {
				logger.info("返回结果 " + resp);
				JSONObject resultArray = JSON.parseObject(resp);
				String bankName = resultArray.getString("bank_name");
				if (StringUtils.isNotEmpty(bankName) && bankName.equals(cardName)) {
					bl = true;
				}
			}
		} catch (Exception e){
			logger.error(" 连连绑卡: 查询当前卡号属于哪家银行 ", e);
		}
		return bl;
	}
	/**
	 * <p>查询当前卡号属于哪家银行 </p>
	 * @param cardNo
	 * */
	public Map queryCardBin(String cardNo){
		Map map = new HashMap(16);
		Map<String,String> signMap = new HashMap();

		map.put("cardBankId","303100000006");
		map.put("cardBankName","光大银行");

		return map;
	}
}

