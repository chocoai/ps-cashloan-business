package com.adpanshi.cashloan.business.cl.mapper;

import com.adpanshi.cashloan.business.cl.domain.OperatorTdBills;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;

import java.util.List;

/**
 * 同盾运营商账单信息Dao
 * 

 * @version 1.0.0
 * @date 2017-05-24 09:21:03
 * Copyright 粉团网路  com.adpanshi.cashloan All Rights Reserved
 *
 *
 */
@RDBatisDao
public interface OperatorTdBillsMapper extends BaseMapper<OperatorTdBills, Long> {

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
