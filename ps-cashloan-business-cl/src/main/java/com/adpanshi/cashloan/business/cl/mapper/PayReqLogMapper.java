package com.adpanshi.cashloan.business.cl.mapper;

import com.adpanshi.cashloan.business.cl.domain.PayReqLog;
import com.adpanshi.cashloan.business.cl.model.ManagePayReqLogModel;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 支付请求记录Mapper
 * 

 * @version 1.0.0
 * @date 2017-03-07 16:18:30
 *
 *
 * 
 *
 */
@RDBatisDao
public interface PayReqLogMapper extends BaseMapper<PayReqLog,Long> {

	/**
	 * 分页查询
	 * 
	 * @param searchMap
	 * @return
	 */
	List<ManagePayReqLogModel> page(Map<String, Object> searchMap);

	/**
	 * 查询详情
	 * 
	 * @param id
	 * @return
	 */
	ManagePayReqLogModel findDetail(Long id);

	/**
	 *
	 * @remarks: 获取一条最新的记录
	 * @date: 2017-0823
	 * @author:nmnl
	 * @param searchMap
	 * @return
	 */
	PayReqLog findSelectiveOne(Map<String, Object> searchMap);
	
	/**
	 * <p>根据给定orderNo查找支付记录<p>
	 * @param orderNo
	 * @return PayReqLog
	 * */
	PayReqLog findPayReqLogByLastOrderNo(@Param("orderNo") String orderNo);
	

}
