package com.adpanshi.cashloan.business.rule.service;

import com.adpanshi.cashloan.business.core.common.service.BaseService;
import com.adpanshi.cashloan.business.rule.domain.BorrowRuleConfig;
import com.adpanshi.cashloan.business.rule.domain.BorrowRuleEngine;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/**
 * 借款规则管理Service
 *
 * @author
 * @version 1.0.0
 * @date 2016-12-20 10:22:30
 */
public interface BorrowRuleEngineService extends BaseService<BorrowRuleEngine, Long> {

    /**
     * 查询借款规则
     *
     * @param params
     * @param currentPage
     * @param pageSize
     * @return
     */
    Page<BorrowRuleEngine> page(Map<String, Object> params, int currentPage, int pageSize);

    int insert(BorrowRuleEngine bre);

    int updateSelective(Map<String, Object> params);

    int deleteById(long id);

    /**
     * 新增或修改借款規則信息
     *
     * @param brc
     * @param configlist
     * @return
     */
    int update(BorrowRuleEngine brc, List<BorrowRuleConfig> configlist);
}
