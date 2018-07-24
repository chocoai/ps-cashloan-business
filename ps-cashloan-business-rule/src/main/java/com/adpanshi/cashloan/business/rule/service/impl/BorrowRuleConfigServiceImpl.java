package com.adpanshi.cashloan.business.rule.service.impl;

import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.service.impl.BaseServiceImpl;
import com.adpanshi.cashloan.business.rule.domain.BorrowRuleConfig;
import com.adpanshi.cashloan.business.rule.mapper.BorrowRuleConfigMapper;
import com.adpanshi.cashloan.business.rule.model.BorrowRuleConfigModel;
import com.adpanshi.cashloan.business.rule.service.BorrowRuleConfigService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 借款规则详细配置表ServiceImpl
 *
 * @version 1.0.0
 * @date 2017-04-21 15:23:19
 */

@Service("borrowRuleConfigService")
public class BorrowRuleConfigServiceImpl extends BaseServiceImpl<BorrowRuleConfig, Long> implements BorrowRuleConfigService {

    @Resource
    private BorrowRuleConfigMapper borrowRuleConfigMapper;

    @Override
    public BaseMapper<BorrowRuleConfig, Long> getMapper() {
        return borrowRuleConfigMapper;
    }

    @Override
    public List<BorrowRuleConfigModel> findConfig(Map<String, Object> params) {
        // TODO Auto-generated method stub
        List<BorrowRuleConfig> list = borrowRuleConfigMapper.listSelective(params);
        List<BorrowRuleConfigModel> result = new ArrayList<>();
        String ruleIds = ";";
        for (BorrowRuleConfig config : list) {
            BorrowRuleConfigModel model = new BorrowRuleConfigModel();
            BorrowRuleConfig rule = new BorrowRuleConfig();
            rule.setRuleId(config.getRuleId());
            rule.setRuleSort(config.getRuleSort());
            model.setRule(rule);
            if (!ruleIds.contains(";" + String.valueOf(model.getRule().getRuleId()) + ";")) {
                result.add(model);
                ruleIds = ruleIds + config.getRuleId() + ";";
            }
        }
        if (result.size() > 0) {
            for (int i = 0; i < result.size(); i++) {
                List<BorrowRuleConfig> configList = new ArrayList<BorrowRuleConfig>();
                for (BorrowRuleConfig config : list) {
                    if (result.get(i).getRule().getRuleId().equals(config.getRuleId())) {
                        BorrowRuleConfig c = new BorrowRuleConfig();
                        c.setConfigId(config.getConfigId());
                        c.setConfigSort(config.getConfigSort());
                        c.setId(config.getId());
                        if (!configList.contains(c)) {
                            configList.add(c);
                        }
                    }
                }
                result.get(i).setConfigList(configList);
            }
        }
        return result;
    }

    @Override
    public void deleteByMap(Map<String, Object> map) {
        borrowRuleConfigMapper.deleteByMap(map);
    }

    @Override
    public List<BorrowRuleConfig> findBorrowRuleId(Map<String, Object> paramMap) {
        return borrowRuleConfigMapper.findBorrowRuleId(paramMap);
    }
}