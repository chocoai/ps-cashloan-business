package com.adpanshi.cashloan.business.rc.service;

import com.adpanshi.cashloan.business.core.common.service.BaseService;
import com.adpanshi.cashloan.business.rc.domain.StatisticsBusiness;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/**
 * 风控数据统计接口Service
 * 

 * @version 1.0.0
 * @date 2017-04-13 17:57:55
 *
 *
 *
 *
 */
public interface StatisticsBusinessService extends BaseService<StatisticsBusiness, Long>{

	/**
	 * 风控数据统计接口分页查询
	 * @param params
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	Page<StatisticsBusiness> page(Map<String, Object> params, int currentPage, int pageSize);

	/**
	 * 查询风控数据统计接口
	 * @param params
	 * @return
	 */
	List<StatisticsBusiness> listSelective(Map<String, Object> params);
}
