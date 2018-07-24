package com.adpanshi.cashloan.business.rule.mapper;

import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;
import com.adpanshi.cashloan.business.rule.domain.BorrowRuleEngine;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 借款规则管理Dao
 * 
 * @author
 * @version 1.0.0
 * @date 2016-12-20 10:22:30
 */
@RDBatisDao
public interface BorrowRuleEngineMapper extends BaseMapper<BorrowRuleEngine,Long> {

	int deleteById(long id);
	
	List<BorrowRuleEngine> listByBorrowType(@Param("borrowType") String borrowType);

}
