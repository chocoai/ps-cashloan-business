package com.adpanshi.cashloan.business.cl.service;

import com.adpanshi.cashloan.business.cl.domain.PayReqLog;
import com.adpanshi.cashloan.business.cl.model.ManagePayReqLogModel;
import com.adpanshi.cashloan.business.core.common.service.BaseService;
import com.github.pagehelper.Page;

import java.util.Map;

/**
 * 支付请求记录Service
 * 

 * @version 1.0.0
 * @date 2017-03-07 16:18:30
 *
 *
 * 
 *
 */
public interface PayReqLogService extends BaseService<PayReqLog, Long>{

	/**
	 * 保存请求记录
	 * 
	 * @param log
	 * @return
	 */
	boolean save(PayReqLog log);

	/**
	 * 分页查询
	 * 
	 * @param current
	 * @param pageSize
	 * @param searchMap
	 * @return
	 */
	Page<ManagePayReqLogModel> page(int current, int pageSize,
                                    Map<String, Object> searchMap);

	/**
	 * <p>根据给定orderNo查找支付记录<p>
	 * @param orderNo
	 * @return PayReqLog
	 * */
	PayReqLog findPayReqLogByLastOrderNo(String orderNo);

}
