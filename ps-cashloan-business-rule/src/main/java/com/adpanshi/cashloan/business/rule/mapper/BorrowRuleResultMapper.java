package com.adpanshi.cashloan.business.rule.mapper;

import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;
import com.adpanshi.cashloan.business.rule.domain.BorrowRuleResult;
import com.adpanshi.cashloan.business.rule.model.ManageReviewModel;
import com.adpanshi.cashloan.business.rule.model.ManageRuleResultModel;

import java.util.List;
import java.util.Map;

/**
 * 规则匹配结果Dao
 * 

 * @version 1.0.0
 * @date 2016-12-21 15:04:28
 *
 *
 * 
 *
 */
@RDBatisDao
public interface BorrowRuleResultMapper extends BaseMapper<BorrowRuleResult,Long> {

	/**
	 * 查询规则名称
	 * @param borrowId
	 * @return
	 */
	List<ManageReviewModel> findRuleResult(long borrowId);

	/**
	 * 查询审核信息
	 * @param borrowId
	 * @return
	 */
	List<ManageRuleResultModel> findResult(long borrowId);


	List<BorrowRuleResult> findRule(Map<String, Object> search);

	int deleteByBorrowId(long borrowId);


}
