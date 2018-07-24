package com.adpanshi.cashloan.business.cl.mapper;

import com.adpanshi.cashloan.business.cl.model.OverdueAnalisisModel;
import com.adpanshi.cashloan.business.cl.model.RepayAnalisisModel;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;

import java.util.List;
import java.util.Map;

/**
 * 运营分析
 * @version 1.0
 * @date 2017年3月21日下午3:01:07
 */
@RDBatisDao
public interface OperateDataMapper {
	
	/**
	 * 还款统计
	 * @param params
	 * @return List<RepayAnalisisModel>
	 */
	List<RepayAnalisisModel> repayAnalisis(Map<String, Object> params);

	/**
	 * 每月逾期统计
	 * @param params
	 * @return List<OverdueAnalisisModel>
	 */
	List<OverdueAnalisisModel> overdueAnalisis(Map<String, Object> params);
}
