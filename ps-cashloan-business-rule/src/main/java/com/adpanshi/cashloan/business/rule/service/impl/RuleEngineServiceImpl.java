package com.adpanshi.cashloan.business.rule.service.impl;

import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.service.impl.BaseServiceImpl;
import com.adpanshi.cashloan.business.rule.domain.RuleEngine;
import com.adpanshi.cashloan.business.rule.mapper.RuleEngineConfigMapper;
import com.adpanshi.cashloan.business.rule.mapper.RuleEngineMapper;
import com.adpanshi.cashloan.business.rule.service.RuleEngineService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 规则引擎管理ServiceImpl
 *
 * @version 1.0.0
 * @date 2016-12-12 17:24:27
 */

@Service("ruleEngineService")
public class RuleEngineServiceImpl extends BaseServiceImpl<RuleEngine, Long> implements RuleEngineService {

    @Resource
    private RuleEngineConfigMapper ruleEngineConfigMapper;
    @Resource
    private RuleEngineMapper ruleEngineMapper;

    @Override
    public BaseMapper<RuleEngine, Long> getMapper() {
        return ruleEngineMapper;
    }

    /**
     * 分页查询
     */
    @Override
    public Page<RuleEngine> findListByPage(Map<String, Object> params, int currentPage, int pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        return (Page<RuleEngine>) ruleEngineMapper.listByPage(params);
    }

    /**
     * 获取单条信息
     */
    @Override
    public RuleEngine findById(Long id) {
        return ruleEngineMapper.findByPrimary(id);
    }

    /**
     * 修改
     */
    @Override
    public int updateByRule(Map<String, Object> map) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("ruleEnginId", map.get("id"));
        paramMap.put("state", map.get("state"));
        ruleEngineConfigMapper.updateByRuleEnginId(paramMap);
        return ruleEngineMapper.updateSelective(map);
    }

    /**
     * 查询
     */
    @Override
    public List<RuleEngine> selectList(Map<String, Object> params) {
        return ruleEngineMapper.listSelective(params);
    }

    public List<String> findAllName(){
        return ruleEngineMapper.findAllName();
    }
}