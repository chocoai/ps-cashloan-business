package com.adpanshi.cashloan.business.system.constant;
/***
 ** @category 通用配置...
 ** @author qing.yunhui
 ** @email: 280672161@qq.com
 ** @createTime: 2017年12月4日上午10:23:50
 **/
public class SysConfigConstant {

	/**关于我们-sys_config*/
	public static final String H5_ABOUT_US="h5_about_us";
	
	/**关于我们-需要传透的参数 (?userId=?) */
	public static final String BINDING_PARAMS="?userId="; 
	
	/**单日每人每个高级认证项最大认证次数(如果值<=0则无限次)*/
	public static final String ADVANCED_AUTH_MAX_TIMES="advanced_auth_max_times";
	
	/**还款期数(如果不传、大风策审批接口默认给12)*/
	public static final String DEFAULT_REPAYMENT_PERIOD="default_repayment_period";
	
}
