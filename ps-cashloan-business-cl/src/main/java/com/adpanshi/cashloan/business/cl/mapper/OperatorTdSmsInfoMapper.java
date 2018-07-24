package com.adpanshi.cashloan.business.cl.mapper;

import com.adpanshi.cashloan.business.cl.domain.OperatorTdSmsInfo;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;

import java.util.List;

/**
 * 短信详单Dao
 * 

 * @version 1.0.0
 * @date 2017-05-24 09:29:45
 * Copyright 粉团网路  com.adpanshi.cashloan All Rights Reserved
 *
 *
 */
@RDBatisDao
public interface OperatorTdSmsInfoMapper extends BaseMapper<OperatorTdSmsInfo, Long> {


	/**
	 * 删除通过请求id
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	int deleteByReqId(List<Long> list);

	/**
	 * 删除通过请求id
	 * @param list
	 * @return
	 */
	int deleteByUserId(List<Long> list);
	
}
