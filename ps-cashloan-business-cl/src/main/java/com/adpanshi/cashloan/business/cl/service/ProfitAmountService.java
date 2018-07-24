package com.adpanshi.cashloan.business.cl.service;

import com.adpanshi.cashloan.business.cl.domain.ProfitAmount;
import com.adpanshi.cashloan.business.core.common.service.BaseService;

/**
 * 分润资金Service
 * 
 * @author
 * @version 1.0.0
 * @date 2017-02-18 16:29:51
 *
 *
 * 
 *
 */
public interface ProfitAmountService extends BaseService<ProfitAmount, Long>{

	/**
	 * 提现修改资金
	 * @param userId
	 * @param money
	 * @return
	 */
	int cash(long userId, double money);

	/**
	 * 查看我的奖金
	 * @param userId
	 * @return
	 */
	ProfitAmount find(long userId);


}
