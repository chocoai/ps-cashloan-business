package com.adpanshi.cashloan.business.rule.service;

import com.adpanshi.cashloan.business.core.common.service.BaseService;
import com.adpanshi.cashloan.business.rule.domain.BorrowRuleConfig;
import com.adpanshi.cashloan.business.rule.model.BorrowRuleConfigModel;

import java.util.List;
import java.util.Map;

/**
 * 借款规则详细配置表Service
 *
 * @version 1.0.0
 * @date 2017-04-21 15:23:19
 */
public interface BorrowRuleConfigService extends BaseService<BorrowRuleConfig, Long> {

    List<BorrowRuleConfigModel> findConfig(Map<String, Object> params);

    void deleteByMap(Map<String, Object> map);

    List<BorrowRuleConfig> findBorrowRuleId(Map<String, Object> paramMap);

}
