package com.adpanshi.cashloan.business.rule.service.impl;

import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.service.impl.BaseServiceImpl;
import com.adpanshi.cashloan.business.rule.domain.RuleEngineInfo;
import com.adpanshi.cashloan.business.rule.mapper.RuleEngineInfoMapper;
import com.adpanshi.cashloan.business.rule.service.RuleEngineInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * 规则评分设置管理ServiceImpl
 *
 * @version 1.0.0
 * @date 2017-01-03 17:28:16
 */

@Service("ruleEngineInfoService")
public class RuleEngineInfoServiceImpl extends BaseServiceImpl<RuleEngineInfo, Long> implements RuleEngineInfoService {

    @Resource
    private RuleEngineInfoMapper ruleEngineInfoMapper;

    @Override
    public BaseMapper<RuleEngineInfo, Long> getMapper() {
        return ruleEngineInfoMapper;
    }

    /**
     * 查询
     */
    @Override
    public List<RuleEngineInfo> findByMap(Map<String, Object> search) {
        return ruleEngineInfoMapper.listSelective(search);
    }
}