package com.adpanshi.cashloan.business.cl.mapper;

import com.adpanshi.cashloan.business.cl.domain.OperatorTdCallInfo;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 通话记录详单Dao
 * 

 * @version 1.0.0
 * @date 2017-05-24 09:32:41
 * Copyright 粉团网路  com.adpanshi.cashloan All Rights Reserved
 *
 *
 */
@RDBatisDao
public interface OperatorTdCallInfoMapper extends BaseMapper<OperatorTdCallInfo, Long> {

	/**
	 * 删除通过请求id
	 * @param list
	 * @return
	 */
	int deleteByReqId(List<Long> list);

	/**
	 * 删除通过请求id
	 * @param list
	 * @return
	 */
	int deleteByUserId(List<Long> list);

	/**
	 * 数据查询
	 *
	 * @param userId
	 *            查询条件
	 * @return 结果集
	 */
	List<OperatorTdCallInfo> listByUserId(@Param("userId") Long userId);
	
}
