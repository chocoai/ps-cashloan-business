package com.adpanshi.cashloan.business.cl.service;

import com.adpanshi.cashloan.business.cl.domain.ProfitLevel;
import com.adpanshi.cashloan.business.core.common.service.BaseService;

import java.util.List;

/**
 * 分润等级Service
 * 
 * @author
 * @version 1.0.0
 * @date 2017-02-18 16:58:10
 *
 *
 * 
 *
 */
public interface ProfitLevelService extends BaseService<ProfitLevel, Long>{

	/**
	 * 列表查询
	 * @return
	 */
	List<ProfitLevel> find();

	/**
	 * 修改分润率
	 * @param id
	 * @param rate
	 * @return
	 */
	int update(long id, double rate);

}
