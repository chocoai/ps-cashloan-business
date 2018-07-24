package com.adpanshi.cashloan.business.rule.mapper;

import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;
import com.adpanshi.cashloan.business.rule.domain.RuleEngineConfig;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 规则引擎管理Dao
 * 

 * @version 1.0.0
 * @date 2016-12-12 17:25:31
 *
 *
 * 
 *
 */
@RDBatisDao
public interface RuleEngineConfigMapper extends BaseMapper<RuleEngineConfig,Long> {

	List<Map<String, Object>> findTable();

	List<Map<String, Object>> findColumnByName(Map<String, Object> map);

	int updateSelective(Map<String, Object> map);
	
	List<RuleEngineConfig> listSelective(Map<String, Object> search);

	int deleteByRuleId(Long id);

	int insert(RuleEngineConfig config);

	int updateByRuleEnginId(Map<String, Object> configMap);
	
	List<RuleEngineConfig> findRuleEnginConfigForBorrow(@Param("adaptedId") String adaptedId);

	int deleteById(Long id);
	
}
