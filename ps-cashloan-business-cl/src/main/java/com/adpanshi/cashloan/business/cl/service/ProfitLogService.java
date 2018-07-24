package com.adpanshi.cashloan.business.cl.service;

import com.adpanshi.cashloan.business.cl.domain.ProfitLog;
import com.adpanshi.cashloan.business.cl.model.ProfitLogModel;
import com.adpanshi.cashloan.business.core.common.service.BaseService;
import com.github.pagehelper.Page;

import java.util.Date;
import java.util.Map;

/**
 * 分润记录Service
 * 
 * @author
 * @version 1.0.0
 * @date 2017-02-18 17:04:10
 *
 *
 * 
 *
 */
public interface ProfitLogService extends BaseService<ProfitLog, Long>{

	/**
	 * 邀请明细
	 * @param searchMap
	 * @param current
	 * @param pageSize
	 * @return
	 */
	Page<ProfitLogModel> page(Map<String, Object> searchMap, int current,
                              int pageSize);

	int save(long borrowId, Date date);
}
