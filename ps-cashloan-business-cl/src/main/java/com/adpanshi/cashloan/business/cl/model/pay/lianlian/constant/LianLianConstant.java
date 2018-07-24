package com.adpanshi.cashloan.business.cl.model.pay.lianlian.constant;

/**
 * 常量
 * 

 * @version 1.0.0
 * @date 2017年3月6日 上午11:04:10
 * Copyright 粉团网路 All Rights Reserved
 *
 * 
 *
 */
public class LianLianConstant {

	/**
	 * 系统设置表Code - 商户号: 特殊需求. 小额放款
	 */
	public static final String BUSINESS_NO = "lianlian_business_no";

	/**
	 * 系统设置表Code - 商户号: 特殊需求. 齐融还钱、放款
	 */
	public static final String BUSINESS_NO_R = "lianlian_business_no_r";

	/**
	 * 系统设置表Code -连连银通公钥: 特殊需求. 小额放款
	 */
	public static final String PUBLIC_KEY = "lianlian_public_key";

	/**
	 * 系统设置表Code -连连银通公钥: 特殊需求. 齐融还钱
	 */
	public static final String PUBLIC_KEY_R = "lianlian_public_key_r";

	/**
	 * 系统设置表Code -商户私钥: 特殊需求. 小额放款
	 */
	public static final String PRIVATE_KEY = "lianlian_private_key";

	/**
	 * 系统设置表Code -商户私钥: 特殊需求. 齐融还钱
	 */
	public static final String PRIVATE_KEY_R = "lianlian_private_key_r";

	/**
	 * 对账文件名称前缀 -  付款明细
	 */
	public static final String CHECK_PREFIX_PAY = "FKMX_";
	
	/**
	 * 对账文件名称前缀 -  交易明细
	 */
	public static final String CHECK_PREFIX_CHARGE = "JYMX_";
	
	public static final int CHECK_DAY = -5;
	
	/**
	 * API版本
	 */
	public static final String API_VERSION = "1.0";

	/**
	 * 签名方式
	 */
	public static final String SIGN_TYPE = "RSA";

	/******* 银行卡账户 标识 *******/
	/** 对公账户 */
	public static final String COMPANY_CARD = "1";
	/** 对私账户 */
	public static final String PERSONAL_CARD = "0";

	/** 处理结果 */
	/** 处理结果 - 成功 */
	public static final String RESULT_SUCCESS = "SUCCESS";
	/** 处理结果 - 失败 */
	public static final String RESULT_FAILURE = "FAILURE";
	/** 处理结果 - 处理中 */
	public static final String RESULT_PROCESSING = "PROCESSING ";

	/**
	 * 商户业务类型 - 虚拟商品
	 */
	public static final String GOODS_VIRTUAL = "101001";

	/**
	 * 支付方式 认证支付（借记卡）
	 */
	public static final String PAY_TYPE_CERTIFIED = "D";

	/**
	 * 交易成功
	 */
	public static final String RESPONSE_SUCCESS_CODE = "0000";
	/**
	 * 交易成功
	 */
	public static final String RESPONSE_SUCCESS_VALUE = "交易成功";

	/** 异步通知 响应使用 */
	public static final String RESPONSE_CODE = "ret_code";
	
	public static final String RESPONSE_MSG = "ret_msg";
	
	
	/** 状态 - 启用 */
	public static final String STATE_ENABLE = "10";

	/** 状态 - 禁用 */
	public static final String STATE_DISABLE = "20";

	/**
	 * 交易失败
	 */
	public static final String RESPONSE_FAIL_CODE = "9999";
	/**
	 * 交易失败
	 */
	public static final String RESPONSE_FAIL_VALUE = "交易失败";
	
	/**
	 * 没有记录
	 * */
	public static final String RESPONSE_UN_RECORD_CODE="8901";
}

