package com.adpanshi.cashloan.business.cl.service;

import com.adpanshi.cashloan.business.cl.domain.PayRespLog;
import com.adpanshi.cashloan.business.cl.model.ManagePayRespLogModel;
import com.adpanshi.cashloan.business.core.common.service.BaseService;
import com.github.pagehelper.Page;

import java.util.Map;

/**
 * 支付响应记录Service
 * 

 * @version 1.0.0
 * @date 2017-03-07 16:18:10
 *
 *
 * 
 *
 */
public interface PayRespLogService extends BaseService<PayRespLog, Long>{

	/**
	 * 保存响应记录
	 * 
	 * @param log
	 * @return
	 */
	boolean save(PayRespLog log);

	/**
	 * 分页查询
	 * 
	 * @param current
	 * @param pageSize
	 * @param searchMap
	 * @return
	 */
	Page<ManagePayRespLogModel> page(int current, int pageSize,
                                     Map<String, Object> searchMap);


}
