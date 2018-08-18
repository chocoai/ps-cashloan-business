package com.adpanshi.cashloan.cl.mapper;

import com.adpanshi.cashloan.cl.domain.OperatorReqLog;
import com.adpanshi.cashloan.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.core.common.mapper.RDBatisDao;

import java.util.List;
import java.util.Map;

/**
 * 运营商认证中间表Dao
 * 
 * @author
 * @version 1.0.0
 * @date 2017-03-01 16:27:59
 *
 */
@RDBatisDao
public interface OperatorReqLogMapper extends BaseMapper<OperatorReqLog,Long> {

	/**
	 * 根据用户查找认证订单号
	 * @param userId
	 * @return
	 */
	String findOrderByUserId(Long userId);
	/**
	 * 根据用户查找当天认证记录
	 * @param paramMap
	 * @return
	 */
	List<OperatorReqLog> listByUserId(Map<String, Object> paramMap);
	
	/**
	 * 查询最后一条符合条件的记录
	 * @param paramMap
	 * @return
	 */
	OperatorReqLog findLastRecord(Map<String, Object> paramMap);
	
	/**
	 * 删除
	 * @param list
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
	 * 请求id
	 * @param list
	 * @return
	 */
	List<Long> listReqIdByUserId(List<Long> list);

	String findOrdersByTaskId(String taskId);
}
