package com.adpanshi.cashloan.business.cl.mapper;

import com.adpanshi.cashloan.business.cl.domain.OperatorTdSmsRecord;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 短信记录具体记录Dao
 * 

 * @version 1.0.0
 * @date 2017-05-24 09:29:24
 * Copyright 粉团网路  com.adpanshi.cashloan All Rights Reserved
 *
 *
 */
@RDBatisDao
public interface OperatorTdSmsRecordMapper{

	/**
	 * <p>新增</p>
	 * <p>待操作的对象</p>
	 * @param operatorTdSmsRecord
	 * @param tableName 表名
	 * @param return int 受影响的行数
	 * */
	int save(@Param("tableName") String tableName, @Param("item") OperatorTdSmsRecord operatorTdSmsRecord);

	/**
	 * 根据表名查询表数量
	 * @param tableName
	 * @return
	 */
	int countTable(String tableName);

	/**
	 * 根据表名创建表
	 * @param tableName
	 */
	void createTable(@Param("tableName") String tableName);

	/**
	 * 通过时间删除数据
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	int deleteByReqId(@Param("list") List<Long> list, @Param("tableName") String tableName);

	/**
	 * 通过时间删除数据
	 * @param list
	 * @param tableName
	 * @return
	 */
	int deleteByUserId(@Param("userId") Long userId, @Param("tableName") String tableName);

	/**
	 * 查询同盾短信记录
	 * @param tableName
	 * @param params
	 * @return
	 */
	List<OperatorTdSmsRecord> listTdSmsRecords(@Param("tableName") String tableName, @Param("params") Map<String, Object> params);

	/**
	 * 单表查询同盾短信记录
	 */
	List<OperatorTdSmsRecord> listSelectivelist(@Param("tableName") String tableName, @Param("params") Map<String, Object> params);
}
