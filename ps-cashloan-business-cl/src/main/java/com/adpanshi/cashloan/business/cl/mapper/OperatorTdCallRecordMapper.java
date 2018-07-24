package com.adpanshi.cashloan.business.cl.mapper;

import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;
import com.adpanshi.cashloan.business.rule.domain.OperatorTdCallRecord;
import com.adpanshi.cashloan.business.rule.domain.OperatorVoices;
import com.adpanshi.cashloan.business.rule.model.OperatorTdCallModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 通话记录具体详细Dao
 * 

 * @version 1.0.0
 * @date 2017-05-24 09:31:37
 * Copyright 粉团网路  com.adpanshi.cashloan All Rights Reserved
 *
 *
 */
@RDBatisDao
public interface OperatorTdCallRecordMapper  {

	
	List<OperatorVoices> listOperatorVoicesModel(@Param("tableName") String tableName, @Param("contractTableName") String contractTableName, @Param("params") Map<String, Object> params);

	/**
	 *
	 * @param tableName
	 * @param contractTableName
	 * @param params
	 * @return
	 */
	List<OperatorTdCallModel> listOperatorVoicesModelMatchOverduePhone(@Param("tableName") String tableName, @Param("contractTableName") String contractTableName, @Param("params") Map<String, Object> params);

	/**
	 * 查询同盾运营商通话记录表
	 * @param params
	 * @param tableName
	 * @return
	 */
	List<OperatorTdCallRecord> listTdCallRecordWithPhoneAndVoicePhone(@Param("tableName") String tableName, @Param("params") Map<String, Object> params);

	/**
	 * <p>如果要调用接口请调用对应的service接口(会对表名做处理)、而不是调用Dao层接口。</p>
	 * <p>待操作的对象</p>
	 * @param operatorTdCallRecord
	 * @param return int 受影响的行数
	 * */
	int save(@Param("tableName") String tableName, @Param("item") OperatorTdCallRecord operatorTdCallRecord);

	/**
	 * 根据表名创建表
	 * @param tableName
	 */
	void createTable(@Param("tableName") String tableName);

	/**
	 * 根据表名查询表数量
	 * @param tableName
	 * @return
	 */
	int countTable(String tableName);

	/**
	 * 通过删除数据
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	int deleteByReqId(@Param("list") List<Long> list, @Param("tableName") String tableName);

    String sunCallTime(@Param("tableName") String tableName, @Param("params") Map<String, Object> params);

	/**
	 * 通过删除数据
	 * @param list
	 * @param tableName
	 * @return
	 */
	int deleteByUserId(@Param("userId") Long userId, @Param("tableName") String tableName);

	/**
	 * 查询同盾通话记录
	 * @param tableName
	 * @param params
	 * @return
	 */
	List<OperatorTdCallRecord> listOperatorTdCall(@Param("tableName") String tableName, @Param("params") Map<String, Object> params);

	/**
	 * 单表查询同盾通话记录
	 */
	List<OperatorTdCallRecord> listSelectivelist(@Param("tableName") String tableName, @Param("params") Map<String, Object> params);

}
