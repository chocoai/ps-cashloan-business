package com.adpanshi.cashloan.business.rule.service.impl;

import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.service.impl.BaseServiceImpl;
import com.adpanshi.cashloan.business.core.common.util.JsonUtil;
import com.adpanshi.cashloan.business.rule.domain.BorrowRuleConfig;
import com.adpanshi.cashloan.business.rule.domain.BorrowRuleEngine;
import com.adpanshi.cashloan.business.rule.enums.RuleEngineEnum;
import com.adpanshi.cashloan.business.rule.mapper.BorrowRuleConfigMapper;
import com.adpanshi.cashloan.business.rule.mapper.BorrowRuleEngineMapper;
import com.adpanshi.cashloan.business.rule.service.BorrowRuleEngineService;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 借款规则管理ServiceImpl
 *
 * @author
 * @version 1.0.0
 * @date 2016-12-20 10:22:30
 */

@Service("borrowRuleEngineService")
public class BorrowRuleEngineServiceImpl extends BaseServiceImpl<BorrowRuleEngine, Long> implements BorrowRuleEngineService {

    @Resource
    private BorrowRuleEngineMapper borrowRuleEngineMapper;
    @Resource
    private BorrowRuleConfigMapper borrowRuleConfigMapper;

    @Override
    public BaseMapper<BorrowRuleEngine, Long> getMapper() {
        return borrowRuleEngineMapper;
    }

    @Override
    public Page<BorrowRuleEngine> page(Map<String, Object> params, int currentPage, int pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        return (Page<BorrowRuleEngine>) borrowRuleEngineMapper.listSelective(params);
    }

    @Override
    public int insert(BorrowRuleEngine bre) {
        return borrowRuleEngineMapper.save(bre);
    }

    @Override
    public int updateSelective(Map<String, Object> params) {
        return borrowRuleEngineMapper.updateSelective(params);
    }


    public int deleteById(long id) {
        borrowRuleEngineMapper.deleteById(id);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("borrowRuleId", id);
        return borrowRuleConfigMapper.deleteByBorrowRuleId(params);
    }

    @Override
    public int update(BorrowRuleEngine brc, List<BorrowRuleConfig> configlist) {
        // TODO Auto-generated method stub
        brc.setAdaptedName(RuleEngineEnum.ADAPTED_STATE.getByEnumKey(brc.getAdaptedId()).EnumValue());
        int count = 0;
        brc.setRuleCount(configlist != null ? configlist.size() : 0);
        if (brc.getId() != null) {
            Map<String, Object> params = new HashMap<String, Object>();
            params = changeObject(brc);
            count = borrowRuleEngineMapper.updateSelective(params);
        } else {
            brc.setAddTime(new Date());
            brc.setReqExt("");
            count = borrowRuleEngineMapper.save(brc);
        }
        if (configlist != null) {
            String ids = ";";
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("borrowRuleId", brc.getId());
            List<BorrowRuleConfig> oldList = borrowRuleConfigMapper.listSelective(params);

            for (BorrowRuleConfig c : configlist) {
                if (c.getId() != null && c.getId() != 0) {
                    ids = ids + c.getId() + ";";
                    params = new HashMap<String, Object>();
                    params = changeObject(c);
                    count = borrowRuleConfigMapper.updateSelective(params);
                } else {
                    c.setBorrowRuleId(brc.getId());
                    count = borrowRuleConfigMapper.save(c);
                }
            }
            if (oldList != null && oldList.size() > 0) {
                for (BorrowRuleConfig c : oldList) {
                    if (!ids.contains(";" + String.valueOf(c.getId() + ";"))) {
                        //logger.info("存在已删除的id"+c.getId());
                        borrowRuleConfigMapper.deleteById(c.getId());
                    }
                }
            }
        } else {
            //如果没有传入子集就将已有数据的删除
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("borrowRuleId", brc.getId());
            borrowRuleConfigMapper.deleteByBorrowRuleId(params);
        }
        return count;
    }

    public Map<String, Object> changeObject(Object c) {
        String str = JSONObject.toJSONString(c);
        Map<String, Object> params = JsonUtil.parse(str, Map.class);
        return params;
    }
}