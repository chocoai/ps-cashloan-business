package com.adpanshi.cashloan.business.rule.service;


import com.adpanshi.cashloan.business.core.domain.BorrowMain;

/**
 * 借款风控规则
 *
 * @version 1.0.0
 * @date 2018-02-27 15:23:19
 */
public interface BorrowRiskRuleEngineService {

    /**
     * 借款规则返回结果类型
     * 10 不通过
     * 20 需人工复审
     * 30 通过
     * @param borrow
     * @return  String
     * */
    String borrowRiskRule(BorrowMain borrow, String mobileType, boolean reBorrowUser);
}
