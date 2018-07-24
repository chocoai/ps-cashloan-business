package com.adpanshi.cashloan.business.rule.mapper;

import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;
import com.adpanshi.cashloan.business.rule.domain.RuleEngineInfo;

/**
 * 规则评分设置管理Dao
 * 

 * @version 1.0.0
 * @date 2017-01-03 17:28:16
 *
 *
 * 
 *
 */
@RDBatisDao
public interface RuleEngineInfoMapper extends BaseMapper<RuleEngineInfo,Long> {

	int insert(RuleEngineInfo info);

	int deleteInfoByRuleId(long id);

    

}
