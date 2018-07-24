package com.adpanshi.cashloan.business.rule.controller;

import com.adpanshi.cashloan.business.core.common.context.Constant;
import com.adpanshi.cashloan.business.core.common.enums.ManageExceptionEnum;
import com.adpanshi.cashloan.business.core.common.util.RdPage;
import com.adpanshi.cashloan.business.rule.domain.BorrowRuleConfig;
import com.adpanshi.cashloan.business.rule.domain.BorrowRuleEngine;
import com.adpanshi.cashloan.business.rule.domain.RuleEngine;
import com.adpanshi.cashloan.business.rule.domain.RuleEngineConfig;
import com.adpanshi.cashloan.business.rule.enums.RuleEngineEnum;
import com.adpanshi.cashloan.business.rule.model.BorrowRuleConfigModel;
import com.adpanshi.cashloan.business.rule.model.ResultModel;
import com.adpanshi.cashloan.business.rule.service.BorrowRuleConfigService;
import com.adpanshi.cashloan.business.rule.service.BorrowRuleEngineService;
import com.adpanshi.cashloan.business.rule.service.RuleEngineConfigService;
import com.adpanshi.cashloan.business.rule.service.RuleEngineService;
import com.adpanshi.cashloan.business.system.service.SysDictService;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.*;

/**
 * 规则类型绑定Controller
 *
 * @version 1.0.0
 * @date 2016-12-20 10:22:30
 * @updateDate 2017/12/28
 * @updator huangqin
 */
@Scope("prototype")
@Controller
@RequestMapping("/manage/ruleType/")
public class BorrowRuleTypeController extends RuleBaseController {

    @Resource
    private BorrowRuleEngineService borrowRuleEngineService;
    @Resource
    private RuleEngineService ruleEngineService;
    @Resource
    private SysDictService sysDictService;
    @Resource
    private RuleEngineConfigService ruleEngineConfigService;
    @Resource
    private BorrowRuleConfigService borrowRuleConfigService;


    /**
     * 规则类型绑定新增编辑下拉列表选项
     *
     * @return ResponseEntity<ResultModel
     */
    @RequestMapping(value = "findRuleList.htm", method = {RequestMethod.POST})
    public ResponseEntity<ResultModel> findRuleList() throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("state", RuleEngine.STATE_ENABLE);
        List<RuleEngine> engineList = ruleEngineService.selectList(params);
        // 返回的规则列表list
        List<Map<String, Object>> list = new ArrayList<>();
        for (RuleEngine rule : engineList) {
            Map<String, Object> search = new HashMap<>();
            search.put("ruleEnginId", rule.getId());
            List<RuleEngineConfig> configs = ruleEngineConfigService.findByMap(search);
            Map<String, Object> map = new HashMap<>();
            map.put("com/adpanshi/cashloan/api/rule", rule);
            map.put("configList", configs);
            list.add(map);
        }
        // 获取借款类型数据字典 borrowList
        List<Map<String, Object>> borrowList = new ArrayList<>();
        List<Map<String, Object>> dicList = sysDictService.getDictsCache("PRODUCT_TYPE");
        for (Map<String, Object> dic : dicList) {
            Map<String, Object> borrows = new HashMap<>();
            borrows.put("borrowType", dic.get("value"));
            borrows.put("borrowTypeName", dic.get("text"));
            borrowList.add(borrows);
        }
        params = new HashMap<>();
        // 获取场景类型 adaptedList
        params.put("adaptedList", RuleEngineEnum.ADAPTED_STATE.EnumValueS());
        params.put("rules", list);
        params.put("borrows", borrowList);
        return new ResponseEntity<>(ResultModel.ok(params), HttpStatus.OK);
    }


    /**
     * 规则类型绑定-查询
     *
     * @param searchParams
     * @param current
     * @param pageSize
     * @return ResponseEntity<ResultModel
     */
    @RequestMapping(value = "list.htm", method = {RequestMethod.POST})
    public ResponseEntity<ResultModel> list(@RequestParam(value = "searchParams", required = false) String searchParams,
                                            @RequestParam(value = "current") Integer current,
                                            @RequestParam(value = "pageSize") Integer pageSize) throws Exception {
        Map<String, Object> params = parseToMap(searchParams, true);
        Page<BorrowRuleEngine> page = borrowRuleEngineService.page(params, current, pageSize);
        Map<String, Object> result = new HashMap<>();
        result.put(Constant.RESPONSE_DATA, page);
        result.put(Constant.RESPONSE_DATA_PAGE, new RdPage(page));
        return new ResponseEntity<>(ResultModel.ok(result), HttpStatus.OK);
    }

    /**
     * 删除规则数
     *
     * @param id
     * @return ResponseEntity<ResultModel>
     */
    @RequestMapping(value = "delete.htm", method = {RequestMethod.POST})
    public ResponseEntity<ResultModel> delete(@RequestParam(value = "id") Long id) {
        if (borrowRuleEngineService.deleteById(id) <= 0) {
            return new ResponseEntity<>(ResultModel.error(ManageExceptionEnum.FAIL_UPDATE_CODE_VALUE), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    /**
     * 编辑规则树-查询
     *
     * @param id
     * @return ResponseEntity<ResultModel>
     */
    @RequestMapping(value = "detail.htm", method = {RequestMethod.POST})
    public ResponseEntity<ResultModel> detail(@RequestParam(value = "id") long id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        BorrowRuleEngine bre = borrowRuleEngineService.getById(id);
        params.clear();
        params.put("borrowRuleId", id);
        List<BorrowRuleConfigModel> configs = borrowRuleConfigService.findConfig(params);
        Map<String, Object> result = new HashMap<>();
        result.put("borrowRuleConfig", configs);
        result.put("borrowRule", bre);
        return new ResponseEntity<>(ResultModel.ok(result), HttpStatus.OK);
    }

    /**
     * 新增/编辑借款规则树
     *
     * @param brc
     * @param ruleConfigList
     * @throws Exception
     */
    @RequestMapping(value = "saveOrUpdateRuleConfig.htm")
    @ResponseBody
    public ResponseEntity<ResultModel> saveOrUpdateRuleConfig(BorrowRuleEngine brc,
                                                              @RequestParam(value = "ruleConfigList") String ruleConfigList) throws Exception {
        List list = parseToList(ruleConfigList,LinkedHashMap.class, true);
        List<BorrowRuleConfig> configlist = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Map link = (LinkedHashMap) list.get(i);
                String value = JSON.toJSONString(link);
                BorrowRuleConfigModel allList = parseToBean(value, BorrowRuleConfigModel.class, true);
                if (null == allList) {
                    break;
                }
                BorrowRuleConfig rule = allList.getRule();
                for (BorrowRuleConfig config : allList.getConfigList()) {
                    BorrowRuleConfig brconfig = new BorrowRuleConfig();
                    brconfig.setRuleId(rule.getRuleId());
                    brconfig.setRuleSort(rule.getRuleSort());
                    brconfig.setConfigId(config.getConfigId());
                    brconfig.setConfigSort(config.getConfigSort());
                    brconfig.setId(config.getId()); // 修改是配置的id不可少
                    configlist.add(brconfig);
                }
            }
        }
        if (null != brc && StringUtils.isNotEmpty(brc.getBorrowType())) {
            // 获取借款类型数据字典
            List<Map<String, Object>> dicList = sysDictService.getDictsCache("PRODUCT_TYPE");
            for (Map<String, Object> dic : dicList) {
                if (brc.getBorrowType().equals(String.valueOf(dic.get("value")))) {
                    brc.setBorrowTypeName(String.valueOf(dic.get("text")));
                    break;
                }
            }
        }
        //保存/更新
        if (borrowRuleEngineService.update(brc, configlist) <= 0) {
            return new ResponseEntity<>(ResultModel.error(ManageExceptionEnum.FAIL_UPDATE_CODE_VALUE), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }
}
