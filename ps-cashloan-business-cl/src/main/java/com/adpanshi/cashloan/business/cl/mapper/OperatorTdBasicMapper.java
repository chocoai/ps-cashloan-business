package com.adpanshi.cashloan.business.cl.mapper;

import com.adpanshi.cashloan.business.cl.domain.OperatorTdBasic;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;

import java.util.List;
import java.util.Map;

/**
 * 同盾运营商认证基本信息表Dao
 * 

 * @version 1.0.0
 * @date 2017-05-24 09:20:04
 * Copyright 粉团网路  com.adpanshi.cashloan All Rights Reserved
 *
 *
 */
@RDBatisDao
public interface OperatorTdBasicMapper extends BaseMapper<OperatorTdBasic, Long> {

	/**
	 * 删除通过请求id
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	int deleteByReqId(List<Long> list);

	/**
	 * 删除
	 * @param list
	 * @return
	 */
	int deleteByUserId(List<Long> list);

	/**
	 * 获取最近一条记录
	 *
	 * @param conditions
	 *            查询条件
	 * @return 查询结果
	 */
	OperatorTdBasic findOne(Map<String, Object> paramMap);
	
}
