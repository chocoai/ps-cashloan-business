package com.adpanshi.cashloan.business.cl.mapper;

import com.adpanshi.cashloan.business.cl.domain.OperatorRespDetail;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;

import java.util.List;

/**
 * 运营商认证通知详情表Dao
 * 

 * @version 1.0.0
 * @date 2017-05-17 12:38:22
 * Copyright 粉团网路  com.adpanshi.cashloan All Rights Reserved
 *
 *
 */
@RDBatisDao
public interface OperatorRespDetailMapper extends BaseMapper<OperatorRespDetail,Long> {

	/**
	 * 删除通过请求id
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	int deleteByReqId(List<Long> list);

}
