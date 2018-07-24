package com.adpanshi.cashloan.business.rule.service;

import com.adpanshi.cashloan.business.core.common.service.BaseService;
import com.adpanshi.cashloan.business.rule.domain.BorrowRuleResult;

/**
 * 借款规则结果记录表Service
 *
 * @version 1.0.0
 * @date 2018-03-01 14:39:19
 */
public interface BorrowRuleResultService extends BaseService<BorrowRuleResult, Long>{

    int saveResultFilled(long borrowId, long ruleId, String reviewResult, String tbNid, String tbName, String colNid, String colName, String rule);

}
