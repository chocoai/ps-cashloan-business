package com.adpanshi.cashloan.business.core.umeng.constants;
/***
 ** @category 请用一句话来描述其用途...
 ** @author qing.yunhui
 ** @email: qingyunhui@smoney.cc
 ** @createTime: 2017年8月30日下午4:18:00
 **/
public class UmConstant {
	
	/**应用唯一标识。友盟消息推送服务提供的appkey和友盟统计分析平台使用的同一套appkey*/
	public static final String AppKey ="591d35af6e27a41d7d001759";
	
	public static final String UmengMessageSecret ="0469e6f2dee97873edc79ad4b823604f";
	
	/**服务器秘钥，用于服务器端调用API请求时对发送内容做签名验证。*/
	public static final String AppMasterSecret="coyvrlysw99jjhxcp8uyzoyilwvbmtho";
	
	/**App应用的包名*/
	public static final String packName="com.adpanshi.com.adpanshi.cashloan";
	
	/**友盟消息推送服务器url*/
	public static final String host = "http://msg.umeng.com";
	
	/***友盟消息推送接口*/
	public static final String postPath = "/api/send";
	
	/**
	 * ios 正式环境开关 (sys_config)
	 * */
	public static final String IOS_SWITCH="um_ios_switch";


}
