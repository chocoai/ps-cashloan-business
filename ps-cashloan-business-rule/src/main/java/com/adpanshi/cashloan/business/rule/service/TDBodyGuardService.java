package com.adpanshi.cashloan.business.rule.service;


public interface TDBodyGuardService {

	/**
	 * saas风控登录认证
	 * @param userId 手机号
	 * @param mobileType 手机类型
	 */
	String tdBodyGuardLogin(String userId, String mobileType);

}
