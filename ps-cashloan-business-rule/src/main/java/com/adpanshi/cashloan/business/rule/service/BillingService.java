package com.adpanshi.cashloan.business.rule.service;

import com.adpanshi.cashloan.business.core.common.service.BaseService;
import com.adpanshi.cashloan.business.rule.domain.Billing;

/**
 * @Description: 记账service
 * @author ppchen
 * @date  2017年8月9日 下午2:31:01
 *
 *
 */
public interface BillingService extends BaseService<Billing, Long> {


	/**
	 * 保存账单信息并到saas平台扣费
	 * @param userId 用户ID
	 * @param type 账单类型
	 * @return
	 */
	int saveBillingInfoAndImmeCharging2Sass(long userId, String type);

}
