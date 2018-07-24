package com.adpanshi.cashloan.business.cl.mapper;

import com.adpanshi.cashloan.business.cl.domain.PayRespLog;
import com.adpanshi.cashloan.business.cl.model.ManagePayRespLogModel;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;

import java.util.List;
import java.util.Map;

/**
 * 支付响应记录Mapper
 * 

 * @version 1.0.0
 * @date 2017-03-07 16:18:10
 *
 *
 * 
 *
 */
@RDBatisDao
public interface PayRespLogMapper extends BaseMapper<PayRespLog,Long> {

	/**
	 * 分页查询
	 * 
	 * @param searchMap
	 * @return
	 */
	List<ManagePayRespLogModel> page(Map<String, Object> searchMap);

	/**
	 * 查询详情
	 * 
	 * @param id
	 * @return
	 */
	ManagePayRespLogModel findDetail(Long id);
}
