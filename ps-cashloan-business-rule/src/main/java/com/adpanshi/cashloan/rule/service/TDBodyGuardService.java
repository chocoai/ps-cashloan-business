package com.adpanshi.cashloan.rule.service;


public interface TDBodyGuardService {

	/**
	 * saas风控登录认证
	 * @param userId 手机号
	 * @param mobileType 手机类型
	 */
	String tdBodyGuardLogin(String userId, String mobileType);

	/**
	 * 尝试拉取报告
	 * @param userId 用户id
	 * @param orderId 订单号
	 * 拉取状态。200：成功，500：异常， other：同盾状态
	 */
	String tryGetReport(String userId, String orderId);
}
