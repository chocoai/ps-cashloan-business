package com.adpanshi.cashloan.business.rule.mapper;

import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;
import com.adpanshi.cashloan.business.rule.domain.RuleEngine;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 规则引擎管理Dao
 * 

 * @version 1.0.0
 * @date 2016-12-12 17:24:27
 *
 *
 * 
 *
 * 
 * 
 */
@RDBatisDao
public interface RuleEngineMapper extends BaseMapper<RuleEngine,Long> {
	/**
	 *  查询信息
	 */
    List<RuleEngine> listSelective(Map<String, Object> map);

	int insertId(RuleEngine rule);

	int updateSelective(Map<String, Object> map);

	/**
     * 自动审批查找需要比对的值
     * @param sql
     * @return
     */
    String findValidValue(@Param("statement") String statement);

	List<RuleEngine> listByPage(Map<String, Object> params);

	List<String> findAllName();
}
