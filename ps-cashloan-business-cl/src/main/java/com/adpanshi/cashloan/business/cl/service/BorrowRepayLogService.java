package com.adpanshi.cashloan.business.cl.service;

import com.adpanshi.cashloan.business.cl.domain.BorrowRepayLog;
import com.adpanshi.cashloan.business.cl.model.BorrowRepayLogModel;
import com.adpanshi.cashloan.business.cl.model.ManageBRepayLogModel;
import com.adpanshi.cashloan.business.core.common.service.BaseService;
import com.github.pagehelper.Page;

import java.util.Map;

/**
 * 还款记录Service
 * 
 * @author
 * @version 1.0.0
 * @date 2017-02-14 13:48:56
 *
 *
 * 
 *
 */
public interface BorrowRepayLogService extends BaseService<BorrowRepayLog, Long>{

	/**
	 * 保存还款记录
	 * @param brl
	 * @return
	 */
	int save(BorrowRepayLog brl);

	/**
	 * 查询还款记录
	 * @param searchMap
	 * @param current
	 * @param pageSize
	 * @return
	 */
	Page<BorrowRepayLogModel> page(Map<String, Object> searchMap, int current,
                                   int pageSize);

	/**
	 * 后台列表还款记录
	 * @param params
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	Page<ManageBRepayLogModel> listModel(Map<String, Object> params,
                                         int currentPage, int pageSize);
 
	/**
	 * 查询还款记录
	 * 
	 * @param paramMap
	 * @return
	 */
	BorrowRepayLog findSelective(Map<String, Object> paramMap);
	
	/**
	 * 修改还款记录
	 * 
	 * @param paramMap
	 * @return
	 */
	boolean updateSelective(Map<String, Object> paramMap);

	/**
	 * 退还补扣修改修改还款记录
	 *
	 * @param paramMap
	 * @return
	 */
	boolean refundDeduction(Map<String, Object> paramMap);
}
