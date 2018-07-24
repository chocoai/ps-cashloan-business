package com.adpanshi.cashloan.business.cl.service;

import com.adpanshi.cashloan.business.cl.domain.ProfitCashLog;
import com.adpanshi.cashloan.business.core.common.service.BaseService;
import com.github.pagehelper.Page;

import java.util.Map;

/**
 * 分润提现记录Service
 * 
 * @author
 * @version 1.0.0
 * @date 2017-02-18 16:51:48
 *
 *
 * 
 *
 */
public interface ProfitCashLogService extends BaseService<ProfitCashLog, Long>{

	/**
	 * 提现记录查看
	 * @param searchMap
	 * @param current
	 * @param pageSize
	 * @return
	 */
	Page<ProfitCashLog> page(Map<String, Object> searchMap, int current,
                             int pageSize);

}
