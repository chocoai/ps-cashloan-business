package com.adpanshi.cashloan.business.rule.service;

import com.adpanshi.cashloan.business.core.common.service.BaseService;
import com.adpanshi.cashloan.business.rule.domain.RuleEngineInfo;

import java.util.List;
import java.util.Map;

/**
 * 规则评分设置管理Service
 *
 * @version 1.0.0
 * @date 2017-01-03 17:28:16
 */
public interface RuleEngineInfoService extends BaseService<RuleEngineInfo, Long> {
    /**
     * 查询
     *
     * @param search
     * @return
     */
    List<RuleEngineInfo> findByMap(Map<String, Object> search);
}
