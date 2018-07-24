package com.adpanshi.cashloan.business.rule.mapper;

import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;
import com.adpanshi.cashloan.business.rule.domain.BorrowRuleConfig;

import java.util.List;
import java.util.Map;

/**
 * 借款规则详细配置表Dao
 * 

 * @version 1.0.0
 * @date 2017-04-21 15:23:19
 *
 *
 *
 *
 */
@RDBatisDao
public interface BorrowRuleConfigMapper extends BaseMapper<BorrowRuleConfig,Long> {

	int deleteByBorrowRuleId(Map<String, Object> params);

	void deleteById(Long id);

	void deleteByMap(Map<String, Object> params);

	List<BorrowRuleConfig> findBorrowRuleId(Map<String, Object> paramMap);

    

}
