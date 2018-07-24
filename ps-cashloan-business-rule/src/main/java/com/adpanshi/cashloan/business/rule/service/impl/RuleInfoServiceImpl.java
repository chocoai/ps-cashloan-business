package com.adpanshi.cashloan.business.rule.service.impl;

import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.service.impl.BaseServiceImpl;
import com.adpanshi.cashloan.business.rule.domain.RuleInfo;
import com.adpanshi.cashloan.business.rule.mapper.RuleInfoMapper;
import com.adpanshi.cashloan.business.rule.model.RuleInfoDetail;
import com.adpanshi.cashloan.business.rule.service.RuleInfoService;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 规则信息ServiceImpl
 *
 * @version 1.0.0
 * @date 2016-12-20 13:58:48
 */

@Service("ruleInfoService")
public class RuleInfoServiceImpl extends BaseServiceImpl<RuleInfo, Long> implements RuleInfoService {

    @Resource
    private RuleInfoMapper ruleInfoMapper;

    @Override
    public BaseMapper<RuleInfo, Long> getMapper() {
        return ruleInfoMapper;
    }

    /**
     * 查询
     */
    @Override
    public List<RuleInfo> findAll(Map<String, Object> map) {
        return ruleInfoMapper.listSelective(map);
    }

    /**
     * 分页查询
     */
    @Override
    public Page<RuleInfo> ruleList(Map<String, Object> params, int currentPage, int pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        return (Page<RuleInfo>) ruleInfoMapper.listSelective(params);
    }

    /**
     * 检验表是否已经保存过
     */
    @Override
    public boolean checkTable(List<RuleInfo> list, String table) {
        for (RuleInfo info : list) {
            if (info.getTbNid().equals(table)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检验表字段是否已经保存过
     */
    @Override
    public boolean checkColumn(List<RuleInfo> list, String table, String column) {
        // TODO Auto-generated method stub
        for (RuleInfo info : list) {
            if (info.getTbNid().equals(table)) {
                List<RuleInfoDetail> rules = JSONArray.parseArray(info.getDetail(), RuleInfoDetail.class);
                for (RuleInfoDetail d : rules) {
                    if (d.getNid().equals(column)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 编辑状态
     */
    @Override
    public int modifyInfoState(Long id, String state) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", id);
        paramMap.put("state", state);
        return ruleInfoMapper.updateSelective(paramMap);
    }

    @Override
    public List<String> findAllTbName() {
        return ruleInfoMapper.findAllTbName();
    }
}
