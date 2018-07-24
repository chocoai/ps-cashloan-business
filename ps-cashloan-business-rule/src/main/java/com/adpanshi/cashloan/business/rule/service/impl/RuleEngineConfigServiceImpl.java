package com.adpanshi.cashloan.business.rule.service.impl;

import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.service.impl.BaseServiceImpl;
import com.adpanshi.cashloan.business.core.common.util.JsonUtil;
import com.adpanshi.cashloan.business.core.common.util.ServletUtils;
import com.adpanshi.cashloan.business.rule.domain.*;
import com.adpanshi.cashloan.business.rule.mapper.*;
import com.adpanshi.cashloan.business.rule.service.RuleEngineConfigService;
import org.springframework.stereotype.Service;
import tool.util.StringUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.Map.Entry;

/**
 * 规则引擎管理ServiceImpl
 *
 * @version 1.0.0
 * @date 2016-12-12 17:25:31 Copyright 粉团网路 arc All Rights Reserved
 */

@Service("ruleEngineConfigService")
public class RuleEngineConfigServiceImpl extends BaseServiceImpl<RuleEngineConfig, Long> implements RuleEngineConfigService {

    @Resource
    private RuleEngineConfigMapper ruleEngineConfigMapper;

    @Resource
    private RuleEngineMapper ruleEngineMapper;

    @Resource
    private RuleInfoMapper ruleInfoMapper;

    @Resource
    private RuleEngineInfoMapper ruleEngineInfoMapper;

    @Resource
    private BorrowRuleConfigMapper borrowRuleConfigMapper;

    @Resource
    private BorrowRuleEngineMapper borrowRuleEngineMapper;

    @Override
    public BaseMapper<RuleEngineConfig, Long> getMapper() {
        return ruleEngineConfigMapper;
    }

    /**
     * 查询规则配置关联信息
     */
    @Override
    public List<RuleEngineConfig> findByMap(Map<String, Object> search) {
        return ruleEngineConfigMapper.listSelective(search);
    }

    /**
     * 查询数据表信息
     */
    @Override
    public List<Map<String, Object>> findTable() {
        return ruleEngineConfigMapper.findTable();
    }

    /**
     * 查询数据库表字段信息
     */
    @Override
    public List<Map<String, Object>> findColumnByName(Map<String, Object> map) {
        return ruleEngineConfigMapper.findColumnByName(map);
    }

    /**
     * 整合表和字段关系集合
     */
    public List<Map<String, Object>> findAllInfo(Map<String, Object> map) {
        List<RuleInfo> list = ruleInfoMapper.listSelective(map);
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                RuleInfo rule = list.get(i);
                Map<String, Object> result = new HashMap<String, Object>();
                result.put("tableName", rule.getTbNid());
                result.put("tableComment", rule.getTbName());
                List<Map<String, Object>> children = new ArrayList<Map<String, Object>>();
                if (rule.getDetail() != null) {
                    List childrenlist = JsonUtil.parse(rule.getDetail(), List.class);
                    for (int j = 0; j < childrenlist.size(); j++) {
                        Map<String, Object> childrenMap = new HashMap<String, Object>();
                        Map link = (LinkedHashMap) childrenlist.get(j);
                        for (Iterator it = link.entrySet().iterator(); it.hasNext(); ) {
                            Entry<String, String> entry = (Entry<String, String>) it.next();
                            if (!"".equals(entry.getValue())) {
                                if ("nid".equals(entry.getKey())) {
                                    childrenMap.put("columnName", entry.getValue());
                                }
                                if ("name".equals(entry.getKey())) {
                                    childrenMap.put("columnComment", entry.getValue());
                                }
                                if ("type".equals(entry.getKey())) {
                                    childrenMap.put("type", entry.getValue());
                                }
                            }
                        }
                        children.add(childrenMap);
                    }
                    result.put("children", children);
                    data.add(result);
                }
            }
        }
        return data;
    }

    /**
     * 规则设置保存信息 分值结果模式
     */
    @Override
    public int saveOrUpate(Map<String, Object> map, List list, String datalist, HttpServletRequest request) {
        int resCode;
        RuleEngine rule = new RuleEngine();
        // 修改规则表
        if (null != map.get("id") && ((Long)map.get("id")).compareTo(0L) > 0) {
            rule.setId(Long.valueOf(map.get("id").toString()));
            if (!RuleEngine.TYPE_RESULT_ENABLE.equals(String.valueOf(map.get("typeResultStatus")))) {
                map.put("typeResultStatus", RuleEngine.TYPE_RESULT_DISABLE);
            }
            map.put("configCount", list != null ? list.size() : 0);
            resCode = ruleEngineMapper.updateSelective(map);
        } else {
            // 新增规则表
            rule.setAddTime(new Date());
            rule.setAddIp(ServletUtils.getIpAddress(request));
            rule.setState("10");
            rule.setName(String.valueOf(map.get("name")));
            rule.setIntegral((Integer) map.get("integral"));
            rule.setType(String.valueOf(map.get("type")));
            rule.setTypeResultStatus(String.valueOf(map.get("typeResultStatus")));
            if (!RuleEngine.TYPE_RESULT_ENABLE.equals(rule.getTypeResultStatus())) {
                rule.setTypeResultStatus(RuleEngine.TYPE_RESULT_DISABLE);
            }
            rule.setAddIp(ServletUtils.getIpAddress(request));
            rule.setConfigCount(list != null ? list.size() : 0);
            resCode = ruleEngineMapper.insertId(rule);
        }
        // 保存评分模式 分值范围定义的结果信息
        if (null != datalist && !"[{}]".equals(datalist)) {
            List infolist = JsonUtil.parse(datalist, List.class);
            resCode = saveIntegralInfo(rule.getId(), infolist);
        }
        // 保存规则设置信息
        if (null != list && list.size() > 0) {
            resCode = saveConfig(list, rule, request);
        }
        return resCode;
    }


    private int saveConfig(List list, RuleEngine rule, HttpServletRequest request) {
        int resCode = 0;
        if (list != null && list.size() > 0) {
            //查询原来规则的配置信息
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("ruleEnginId", rule.getId());
            List<RuleEngineConfig> oldconfigs = ruleEngineConfigMapper.listSelective(paramMap);
            String olds = ";";
            //查询借款规则是否相关rule的信息
            paramMap = new HashMap<String, Object>();
            paramMap.put("ruleId", rule.getId());
            List<BorrowRuleConfig> borrowRuleConfig = borrowRuleConfigMapper.findBorrowRuleId(paramMap);
            boolean flag = false;
            if (borrowRuleConfig != null && borrowRuleConfig.size() > 0) {
                flag = true;
            }
            int add = 0;
            int plus = 0;
            for (int i = 0; i < list.size(); i++) {
                RuleEngineConfig config = new RuleEngineConfig();
                Map link = (LinkedHashMap) list.get(i);
                for (Iterator it = link.entrySet().iterator(); it.hasNext(); ) {
                    Entry<String, Object> entry = (Entry<String, Object>) it.next();
                    if (!"".equals(entry.getValue())) {
                        if ("id".equals(entry.getKey())) {
                            config.setId(Long.valueOf(String.valueOf(entry.getValue())));
                        }
                        if ("ctable".equals(entry.getKey())) {
                            config.setCtable(StringUtil.isNull(entry.getValue()));
                        }
                        if ("ccolumn".equals(entry.getKey())) {
                            config.setCcolumn(StringUtil.isNull(entry.getValue()));
                        }
                        if ("tableComment".equals(entry.getKey())) {
                            config.setTableComment(StringUtil.isNull(entry.getValue()));
                        }
                        if ("columnComment".equals(entry.getKey())) {
                            config.setColumnComment(StringUtil.isNull(entry.getValue()));
                        }
                        if ("formula".equals(entry.getKey())) {
                            config.setFormula(StringUtil.isNull(entry.getValue()));
                        }
                        if ("cvalue".equals(entry.getKey())) {
                            String cvalue = StringUtil.isNull(entry.getValue());
                            config.setCvalue(cvalue.replaceAll("，", ","));
                        }
                        if ("type".equals(entry.getKey())) {
                            config.setType(StringUtil.isNull(entry.getValue()));
                        }
                        if ("integral".equals(entry.getKey())) {
                            if (!StringUtil.isBlank(entry.getValue())){
                                config.setIntegral(Integer.valueOf(String.valueOf(entry.getValue())));
                            }
                        }
                        if ("result".equals(entry.getKey())) {
                            config.setResult(StringUtil.isNull(entry.getValue()));
                        }
                        if ("resultType".equals(entry.getKey())) {
                            config.setResultType(StringUtil.isNull(entry.getValue()));
                        }
                    }
                }
                // 新增设置信息
                // 前端如果不传值，查询数据保存
                if (config.getType() == "" || config.getType() == null) {
                    Map<String, Object> info = new HashMap<String, Object>();
                    List<Map<String, Object>> infos = findAllInfo(info);
                    if (infos != null) {
                        for (int k = 0; k < infos.size(); k++) {
                            Map<String, Object> table = infos.get(k);
                            if (table.get("tableName").equals(config.getCtable())) {
                                config.setTableComment((String) table.get("tableComment"));
                                List<Map<String, Object>> children = (List<Map<String, Object>>) table.get("children");
                                if (children != null) {
                                    for (Map<String, Object> child : children) {
                                        if (child.get("columnName").equals(config.getCcolumn())) {
                                            config.setColumnComment((String) child.get("columnComment"));
                                            config.setType((String) child.get("type"));
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (config.getId() != null && config.getId() != 0) {
                    olds = olds + config.getId() + ";";
                    Map<String, Object> params = JsonUtil.parse(JsonUtil.toString(config), Map.class);
                    resCode = ruleEngineConfigMapper.updateSelective(params);
                } else {
                    config.setAddTime(new Date());
                    config.setState("10");
                    config.setAddIp(ServletUtils.getIpAddress(request));
                    config.setRuleEnginId(rule.getId());
                    resCode = ruleEngineConfigMapper.insert(config);
                    add++;
                    //如果规则中有相对的规则，新增规则配置信息
                    if (flag) {
                        for (BorrowRuleConfig br : borrowRuleConfig) {
                            BorrowRuleConfig bc = new BorrowRuleConfig();
                            bc.setBorrowRuleId(br.getBorrowRuleId());
                            bc.setRuleId(rule.getId());
                            bc.setConfigId(config.getId());
                            bc.setRuleSort(br.getRuleSort());
                            bc.setConfigSort(0);
                            borrowRuleConfigMapper.save(bc);
                        }
                    }
                }
            }
            for (RuleEngineConfig c : oldconfigs) {
                if (!olds.contains(";" + String.valueOf(c.getId()) + ";")) {
                    ruleEngineConfigMapper.deleteById(c.getId());
                    //删除相关联的借款规则配置
                    paramMap = new HashMap<String, Object>();
                    paramMap.put("ruleId", rule.getId());
                    paramMap.put("configId", c.getId());
                    borrowRuleConfigMapper.deleteByMap(paramMap);
                    plus++;
                }
            }
            if (flag) {
                for (BorrowRuleConfig br : borrowRuleConfig) {
                    int count = br.getConfigSort() + add - plus;
                    if (count > 0) {
                        paramMap = new HashMap<String, Object>();
                        paramMap.put("id", br.getBorrowRuleId());
                        paramMap.put("ruleCount", count);//ruleCount赋值到ConfigSort
                        borrowRuleEngineMapper.updateSelective(paramMap);
                    } else {
                        borrowRuleEngineMapper.deleteById(br.getBorrowRuleId());
                    }
                }
            }
        } else {
            //没有配置任何数据
            ruleEngineConfigMapper.deleteByRuleId(rule.getId());
            //删除相关联的借款规则配置
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("ruleId", rule.getId());
            borrowRuleConfigMapper.deleteByMap(paramMap);
        }
        return resCode;
    }

    /**
     * 保存评分结果模式下 的结果范围
     *
     * @param fid
     * @param list
     * @return
     */
    public int saveIntegralInfo(long fid, List list) {
        int resCode = 0;
        if (StringUtil.isNotBlank(fid)) {
            ruleEngineInfoMapper.deleteInfoByRuleId(fid);
            if (list != null && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    RuleEngineInfo info = new RuleEngineInfo();
                    Map link = (LinkedHashMap) list.get(i);
                    for (Iterator it = link.entrySet().iterator(); it.hasNext(); ) {
                        Entry<String, Object> entry = (Entry<String, Object>) it
                                .next();
                        if (!"".equals(entry.getValue())) {
                            if (entry.getKey().equals("info_formula")) {

                                info.setFormula((String) entry.getValue());
                            }
                            if (entry.getKey().equals("info_integral")) {
                                info.setIntegral((Integer) entry
                                        .getValue());
                            }
                            if (entry.getKey().equals("info_result")) {
                                info.setResult((String) entry.getValue());
                            }
                            if (entry.getKey().equals("info_id")) {
                                info.setId((Long) entry
                                        .getValue());
                            }
                        }
                    }
                    info.setRuleEnginId(fid);
                    resCode = ruleEngineInfoMapper.insert(info);
                }
            }
        }
        return resCode;
    }
}