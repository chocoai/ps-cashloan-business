package com.adpanshi.cashloan.business.cl.service;

import com.adpanshi.cashloan.business.cl.domain.ProfitAgent;
import com.adpanshi.cashloan.business.core.common.service.BaseService;

/**
 * 代理用户信息Service
 * 
 * @author
 * @version 1.0.0
 * @date 2017-02-18 16:24:45
 *
 *
 * 
 *
 */
public interface ProfitAgentService extends BaseService<ProfitAgent, Long>{


	/**
	 * 代理商审核
	 * @param isUse
	 * @param  
	 */
	int pass(int isUse, long id);

}
