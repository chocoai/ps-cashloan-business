package com.adpanshi.cashloan.business.cl.mapper;

import com.adpanshi.cashloan.business.cl.domain.OperatorVoicesVo;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;
import com.adpanshi.cashloan.business.rule.domain.OperatorVoices;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * 运营商信息-通话记录Dao
 * 

 * @version 1.0.0
 * @date 2017-03-13 16:44:01
 *
 *
 * 
 *
 */
@RDBatisDao
public interface OperatorVoicesMapper extends BaseMapper<OperatorVoices,Long> {

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
	 * (分表)新增
	 * @param tableName
	 * @param operatorVoices
	 * @return
	 */
	int saveShard(@Param("tableName") String tableName, @Param("item") OperatorVoices operatorVoices);

	/**
	 * 根据参数(分表)查询
	 * @param tableName
	 * @param tableName
	 * @param params
	 * @param params
	 * @return
	 */
	List<OperatorVoices> listShardSelective(
            @Param("tableName") String tableName,
            @Param("params") Map<String, Object> params);

	/**
	 * 根据参数(电话号码和通话号码)查询

	 * @param tableName
	 * @param tableName
	 * @param params
	 * @param params
	 * @return
	 */
	List<OperatorVoices> listShardWithPhoneAndVoicePhone(
            @Param("tableName") String tableName,
            @Param("params") Map<String, Object> params);

	/**
	 * 查询通讯记录
	 * @param tableName
	 * @param tableName
	 * @param params
	 * @param params
	 * @return
	 */
	List<OperatorVoicesVo> listShardSelectiveVo(
            @Param("tableName") String tableName,
            @Param("contractTableName") String contractTableName,
            @Param("params") Map<String, Object> params);

	/**
	 * 通过时间删除数据
	 * @param userId
	 * @param tableName
	 * @return
	 */
	int deleteByUserId(@Param("userId") Long userId, @Param("tableName") String tableName);
}
