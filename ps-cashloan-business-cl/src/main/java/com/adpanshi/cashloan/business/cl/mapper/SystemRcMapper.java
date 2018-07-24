package com.adpanshi.cashloan.business.cl.mapper;

import com.adpanshi.cashloan.business.cl.model.DayPassApr;
import com.adpanshi.cashloan.business.cl.model.SystemDayData;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;

import java.util.List;
import java.util.Map;

/**
 * 平台数据日报

 * @version 1.0
 * @date 2017年3月20日下午5:18:19
 * Copyright 粉团网路 现金贷  All Rights Reserved
 *
 * 
 *
 */
@RDBatisDao
public interface SystemRcMapper {

	/**
	 * 平台数据日报
	 * @return
	 */
	List<SystemDayData> dayData(Map<String, Object> params);

	/**
	 * 每日通过率
	 */
	List<DayPassApr> dayApr(Map<String, Object> params);
}
