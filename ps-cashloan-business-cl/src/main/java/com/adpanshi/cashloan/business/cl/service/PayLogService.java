package com.adpanshi.cashloan.business.cl.service;

import com.adpanshi.cashloan.business.cl.domain.PayLog;
import com.adpanshi.cashloan.business.cl.model.ManagePayLogModel;
import com.adpanshi.cashloan.business.core.common.service.BaseService;
import com.github.pagehelper.Page;

import java.util.Map;

/**
 * 支付记录Service
 * 

 * @version 1.0.0
 * @date 2017-03-07 16:18:56
 *
 *
 * 
 *
 */
public interface PayLogService extends BaseService<PayLog, Long>{

	/**
	 * 保存支付记录
	 * 
	 * @param payLog
	 * @return
	 */
	boolean save(PayLog payLog);

	/**
	 * 分页查询支付记录
	 * 
	 * @param current
	 * @param pageSize
	 * @param searchMap
	 * @return
	 */
	Page<ManagePayLogModel> page(int current, int pageSize,
                                 Map<String, Object> searchMap);
	/**
	 * 订单号查询支付记录
	 *
	 * @param orderNo
	 * @return
	 */
	PayLog findByOrderNo(String orderNo);

	/**
	 * 更新
	 *
	 * @param paramMap
	 * @return
	 */
	boolean updateSelective(Map<String, Object> paramMap);

	/**
	 * 条件查询
	 *
	 * @param paramMap
	 * @return
	 */
	PayLog findSelective(Map<String, Object> paramMap);

	/**
	 * 条件查询
	 *
	 * @param paramMap
	 * @return
	 */
	PayLog findSelectiveOne(Map<String, Object> paramMap);

	/**
	 * <p>根据给定条件查询最近一条支付记录</p>
	 * @param paramMap
	 * */
	PayLog getPayLogByLateOrderNo(Map<String, Object> paramMap);

	/**
	 * <p>根据orderNo、borrowId查找最近(创建时间:create_time倒序)的一条支付记录</p>
	 * @param orderNo  订单号(必填)
	 * @param borrowId 借款id(非必填)
	 * @return PayLog
	 * */
	PayLog findPayLogByLastOrderNoWithBorrowId(String orderNo, String borrowId);

}
