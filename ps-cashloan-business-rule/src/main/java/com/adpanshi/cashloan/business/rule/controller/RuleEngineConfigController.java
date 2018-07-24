package com.adpanshi.cashloan.business.rule.controller;

import com.adpanshi.cashloan.business.core.common.context.Constant;
import com.adpanshi.cashloan.business.core.common.db.DatabaseContextHolder;
import com.adpanshi.cashloan.business.core.common.enums.ManageExceptionEnum;
import com.adpanshi.cashloan.business.core.common.model.AuthUserRole;
import com.adpanshi.cashloan.business.core.common.util.RdPage;
import com.adpanshi.cashloan.business.rule.domain.BorrowRuleConfig;
import com.adpanshi.cashloan.business.rule.domain.RuleEngine;
import com.adpanshi.cashloan.business.rule.domain.RuleEngineConfig;
import com.adpanshi.cashloan.business.rule.domain.RuleEngineInfo;
import com.adpanshi.cashloan.business.rule.enums.RuleEngineEnum;
import com.adpanshi.cashloan.business.rule.model.ResultModel;
import com.adpanshi.cashloan.business.rule.service.*;
import com.github.pagehelper.Page;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 规则配置Controller
 *
 * @version 1.0.0
 * @date 2016-12-12 17:25:31
 * @updateDate 2017/12/28
 * @updator huangqin
 */
@Controller
@Scope("prototype")
@RequestMapping("/manage/ruleConfig/")
public class RuleEngineConfigController extends RuleBaseController {
    private static final Logger logger = LoggerFactory.getLogger(RuleEngineConfigController.class);
    @Resource
    private RuleEngineConfigService ruleEngineConfigService;
    @Resource
    private RuleEngineService ruleEngineService;
    @Resource
    private RuleEngineInfoService ruleEngineInfoService;
    @Resource
    private BorrowRuleConfigService borrowRuleConfigService;
    @Resource
    private BorrowRuleEngineService borrowRuleEngineService;


    /**
     * 规则配置-下拉列表查询
     * @return ResponseEntity<ResultModel>
     * @author huangqin
     * @date 2018-01-01 15:04
     * */
    @RequestMapping(value = "dropDownList.htm", method = {RequestMethod.POST})
    public ResponseEntity<ResultModel> roleDropDownList() {
        logger.info("------规则引擎-规则配置-下拉列表查询------");
        //读库标志
        DatabaseContextHolder.setDbName(DatabaseContextHolder.READ_DB);
        Map<String, Object> result = new HashMap<>();
        result.put("ruleNameState", ruleEngineService.findAllName());
        result.put("ruleState", RuleEngineEnum.RULE_STATE.EnumValueS());
        return new ResponseEntity<>(ResultModel.ok(result), HttpStatus.OK);
    }

    /**
     * 查询规则引擎表信息
     *
     * @return ResponseEntity<ResultModel>
     */
    @RequestMapping(value = "findTable.htm", method = {RequestMethod.POST})
    public ResponseEntity<ResultModel> findTable() {
        Map<String, Object> map = new HashMap<>();
        map.put("state", "10");
        List<Map<String, Object>> data = ruleEngineConfigService.findAllInfo(map);
        return new ResponseEntity<>(ResultModel.ok(data), HttpStatus.OK);
    }

    /**
     * 规则配置查询
     *
     * @param searchParams
     * @param current
     * @param pageSize
     * @return ResponseEntity<ResultModel>
     */
    @RequestMapping(value = "ruleList.htm", method = {RequestMethod.POST})
    public ResponseEntity<ResultModel> list(@RequestParam(value = "searchParams", required = false) String searchParams,
                                            @RequestParam(value = "current") Integer current,
                                            @RequestParam(value = "pageSize") Integer pageSize) throws Exception{
        Map<String, Object> params = parseToMap(searchParams,true);
        Page<RuleEngine> page = ruleEngineService.findListByPage(params, current, pageSize);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put(Constant.RESPONSE_DATA, page);
        result.put(Constant.RESPONSE_DATA_PAGE, new RdPage(page));
        return new ResponseEntity<>(ResultModel.ok(result), HttpStatus.OK);
    }

    /**
     * 修改规则状态,启用/禁用
     *
     * @param state
     * @param id
     * @return ResponseEntity<ResultModel>
     */
    @RequestMapping(value = "modifyRuleState.htm", method = {RequestMethod.POST})
    public ResponseEntity<ResultModel> modifyState(@RequestParam(value = "state") String state,
                                                   @RequestParam(value = "id") Long id) {
        int resCode = 0;
        if (StringUtils.isNotBlank(String.valueOf(id)) && StringUtils.isNotBlank(state)) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", id);
            map.put("state", state);
            resCode = ruleEngineService.updateByRule(map);
            map.clear();
            if ("20".equals(state)) {
                //关联的借款规则信息修改
                map.put("ruleId", id);
                List<BorrowRuleConfig> borrowRuleConfig = borrowRuleConfigService.findBorrowRuleId(map);
                map.clear();
                RuleEngine rule = ruleEngineService.findById(id);
                for (BorrowRuleConfig br : borrowRuleConfig) {
                    int count = br.getConfigSort() - rule.getConfigCount();//ruleCount赋值到ConfigSort
                    if (count <= 0) {
                        borrowRuleEngineService.deleteById(br.getBorrowRuleId());
                    } else {
                        map.put("id", br.getBorrowRuleId());
                        map.put("ruleCount", count);
                        borrowRuleEngineService.updateSelective(map);
                        map.clear();
                    }
                }
                map.put("ruleId", id);
                borrowRuleConfigService.deleteByMap(map);
            }
        }
        if (resCode <= 0) {
            return new ResponseEntity<>(ResultModel.error(ManageExceptionEnum.FAIL_INSERT_CODE_VALUE), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    /**
     * 查询规则详细信息-编辑/新增查询
     *
     * @param id
     * @return ResponseEntity<ResultModel>
     */
    @RequestMapping(value = "getRuleConfig.htm", method = {RequestMethod.POST})
    public ResponseEntity<ResultModel> getConfig(@RequestParam(value = "id") Long id) {
        Map<String, Object> search = new HashMap<>();
        search.put("ruleEnginId", id);
        List<RuleEngineConfig> configs = ruleEngineConfigService.findByMap(search);
        List<RuleEngineInfo> reulst = ruleEngineInfoService.findByMap(search);
        RuleEngine rule = ruleEngineService.findById(id);
        Map<String, Object> map = new HashMap<>();
        map.put("com/adpanshi/cashloan/api/rule", rule);
        map.put("configList", configs);
        map.put("infoList", reulst);
        return new ResponseEntity<>(ResultModel.ok(map), HttpStatus.OK);
    }

    /**
     * 新增/编辑规则设置信息
     * @param name
     * @param type
     * @param typeResultStatus
     * @param id
     * @param configList
     * @param infoList
     */
    @RequestMapping("saveOrUpdateRuleConfig.htm")
    public ResponseEntity<ResultModel> saveOrUpdateRuleConfig(
                               @RequestParam(value = "name") String name,
                               @RequestParam(value = "type") String type,
                               @RequestParam(value = "typeResultStatus") String typeResultStatus,
                               @RequestParam(value = "configList") String configList,
                               @RequestParam(value = "id", required = false) Long id,
                               @RequestParam(value = "infoList", required = false) String infoList) throws Exception{
        AuthUserRole sysUser = getAuthUserRole();
        logger.info("编辑规则设置信息:id=" + id + ",sysUser=" + sysUser.getUserName() + ",name=" + name + ",type=" + type + ",typeResultStatus=" + typeResultStatus + ",configList=" + configList + ",infoList=" + infoList);
        List list =parseToList(configList,LinkedHashMap.class,true);
        Map<String, Object> rule = new HashMap<>();
        if (null != id && id.compareTo(0L) > 0) {
            rule.put("id", id);
        }
        rule.put("name", name);
        rule.put("type", type);
        rule.put("typeResultStatus", typeResultStatus);
        if (ruleEngineConfigService.saveOrUpate(rule, list, infoList, request) <= 0) {
            return new ResponseEntity<>(ResultModel.error(ManageExceptionEnum.FAIL_INSERT_CODE_VALUE), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }
}
