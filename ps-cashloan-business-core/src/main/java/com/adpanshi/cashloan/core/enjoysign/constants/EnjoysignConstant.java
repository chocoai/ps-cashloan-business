package com.adpanshi.cashloan.core.enjoysign.constants;
/***
 ** @category 1号签常量...
 ** @author qing.yunhui
 ** @email: qingyunhui@smoney.cc
 ** @createTime: 2017年9月26日上午9:33:54
 **/
public class EnjoysignConstant {
	
	// 请求成功
	public static final String SUCCESS = "0";

	// 服务器出错
	public static final String FAIL = "-1";
	
	/**重复签章(订单在1号签服务器已签署成功)-标识*/
	public static final String REPEATED_SIGNATURE_STATUS="40004";
	
	/**1号签-订单号前缀*/
	public static final String ORDER_PREFIX="N";

	/**1号签合同下载存放目录(sys_config)标识*/
	public static final String ENJOYSIGN_PDF_DIR="enjoysign_pdf_dir";
	
	/**1号签合同-定时JOB-key(不可配置)*/
	public static final String ENJOYSIGN_DOWNLOAD ="enjoysignDownload";
	
	/**1号签合同-定时JOB-key(不可配置)-重试签章*/
	public static final String ENJOYSIGN_RETRY_SIGNATURE="enjoysignRetrySignature";
	
	/**1号签-总开关标志(sys_config)*/
	public static final String ENJOYSIGN_SWITCHS="enjoysign_switch";
	
	/**
	 * 企业-社会统一信用代码(1号签-企业签章时需要用到)
	 * */
	public static final String UNIFIED_SOCIAL_CREDIT_CODE ="unified_social_credit_code";
	
	/**借款年率华(sys_config)*/
	public static final String BOR_ANNUAL_RATE="bor_annual_rate";
	
	/**服务费率(sys_config)*/
	public static final String BOR_SERVICE_FEE="bor_service_fee";
	
	/**违约金利率（sys_config）*/
	public static final String PENALTY_RATE="penalty_rate";
}
