package com.adpanshi.cashloan.rule.service;

import com.adpanshi.cashloan.core.common.service.BaseService;
import com.adpanshi.cashloan.core.domain.BorrowMain;
import com.adpanshi.cashloan.rule.domain.BorrowRuleResult;
import com.adpanshi.cashloan.rule.domain.UserContactsMatch;
import com.adpanshi.cashloan.rule.domain.equifaxReport.base.Envelope;
import com.adpanshi.cashloan.rule.extra.BorRuleResultExtra;

import java.math.BigDecimal;
import java.util.List;

/**
 * 借款规则结果记录表Service
 *
 * @version 1.0.0
 * @date 2018-03-01 14:39:19
 * @author
 */
public interface BorrowRuleResultService extends BaseService<BorrowRuleResult, Long>{

    BorrowRuleResult save(long borrowId, String resultType, String resultMsg, BigDecimal loanLimit);

    /**
     * 借款规则返回结果类型
     * 10 不通过
     * 20 需人工复审
     * 30 通过
     * @param borrow
     * @return  String
     * */
    BorrowRuleResult ruleEngine(BorrowMain borrow, Envelope envelope, List<UserContactsMatch> list);

    /**
     * 根据borrowId获得唯一规则引擎结果
     * @param borrowId
     * @return
     */
    BorrowRuleResult findByBorrowMainId(Long borrowId);
}
