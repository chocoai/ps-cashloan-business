package com.adpanshi.cashloan.business.rule.mapper;

import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;
import com.adpanshi.cashloan.business.rule.domain.RuleInfo;

import java.util.List;
import java.util.Map;

/**
 * 规则信息Dao
 * 

 * @version 1.0.0
 * @date 2016-12-20 13:58:48
 *
 *
 * 
 *
 */
@RDBatisDao
public interface RuleInfoMapper extends BaseMapper<RuleInfo,Long> {

	int delInfoById(Long id);

	int updateSelective(Map<String, Object> paramMap);

	List<String> findAllTbName();
}
