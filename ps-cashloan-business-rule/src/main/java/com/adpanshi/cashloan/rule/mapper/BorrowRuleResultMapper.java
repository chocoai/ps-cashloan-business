package com.adpanshi.cashloan.rule.mapper;

import com.adpanshi.cashloan.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.core.common.mapper.RDBatisDao;
import com.adpanshi.cashloan.rule.domain.BorrowRuleResult;
import com.adpanshi.cashloan.rule.model.ManageReviewModel;
import com.adpanshi.cashloan.rule.model.ManageRuleResultModel;

import java.util.List;
import java.util.Map;

/**
 * 规则匹配结果Dao
 *
 * @version 1.0.0
 * @date 2016-12-21 15:04:28
 * @author 8452
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

	/**
	 * deleteByBorrowId
	 * @param borrowId
	 * @return
	 */
	int deleteByBorrowId(long borrowId);

	/**
	 * findByBorrowMainId
	 * @param params
	 * @return
	 */
    BorrowRuleResult findByBorrowMainId(Map<String, Object> params);
}
