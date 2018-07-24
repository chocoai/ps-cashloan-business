package com.adpanshi.cashloan.business.cl.model;

/**
 * 短信model

 * @version 1.0.0
 * @date 2017年3月15日 下午6:39:20
 * Copyright 粉团网路 金融创新事业部 此处填写项目英文简称  All Rights Reserved
 *
 *
 */
public class SmsModel {
	
	/**
	 * 注册验证码-register
	 */
	public static final String SMS_TYPE_REGISTER = "register";
	
	/**
	 * 绑卡-bindCard
	 */
	public static final String SMS_TYPE_BINDCARD = "bindCard";
	
	/**
	 * 找回登录密码-findReg
	 */
	public static final String SMS_TYPE_FINDREG = "findReg";
	
	/**
	 * 找回交易密码-findPay
	 */
	public static final String SMS_TYPE_FINDPAY = "findPay";
	/**
	 * 响应状态-未响应
	 */
	public static final String RESEND_STATE_INIT = "0";
	/**
	 * 响应状态-成功
	 */
	public static final String RESEND_STATE_SUCCESS = "1";
	/**
	 * 响应状态-失败
	 */
	public static final String RESEND_STATE_FAIL = "2";
	/**
	 * 响应状态-结束
	 */
	public static final String RESEND_STATE_FINAL = "3";

}
