package com.adpanshi.cashloan.business.rule.service;

import com.adpanshi.cashloan.business.core.common.service.BaseService;
import com.adpanshi.cashloan.business.rule.domain.RuleEngineConfig;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 规则引擎管理Service
 *
 * @version 1.0.0
 * @date 2016-12-12 17:25:31
 */
public interface RuleEngineConfigService extends BaseService<RuleEngineConfig, Long> {

    List<RuleEngineConfig> findByMap(Map<String, Object> search);

    List<Map<String, Object>> findColumnByName(Map<String, Object> map);

    List<Map<String, Object>> findTable();

    List<Map<String, Object>> findAllInfo(Map<String, Object> map);

    int saveOrUpate(Map<String, Object> rule, List list, String datalist, HttpServletRequest request);

}
