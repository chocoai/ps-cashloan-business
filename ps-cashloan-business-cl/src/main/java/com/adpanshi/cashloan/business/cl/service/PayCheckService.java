package com.adpanshi.cashloan.business.cl.service;

import com.adpanshi.cashloan.business.cl.domain.PayCheck;
import com.adpanshi.cashloan.business.cl.model.ManagePayCheckModel;
import com.adpanshi.cashloan.business.core.common.service.BaseService;
import com.github.pagehelper.Page;

import java.util.Map;

/**
 * 资金对账记录Service
 * 

 * @version 1.0.0
 * @date 2017-04-13 17:12:20
 *
 *
 *
 */
public interface PayCheckService extends BaseService<PayCheck, Long>{

	/**
	 * 保存对账记录
	 * @param payCheck
	 * @return
	 */
	boolean save(PayCheck payCheck);

	/**
	 * 列表搜索资金对账记录
	 * @param current
	 * @param pageSize
	 * @param searchMap
	 * @return
	 */
	Page<ManagePayCheckModel> page(int current, int pageSize,
                                   Map<String, Object> searchMap);

	/**
	 * 条件查询单条对账记录
	 * 
	 * @param searchMap
	 * @return
	 */
	PayCheck findSelective(Map<String, Object> searchMap);

}
