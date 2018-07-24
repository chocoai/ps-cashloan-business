package com.adpanshi.cashloan.business.rule.controller;

import com.adpanshi.cashloan.business.core.common.context.Constant;
import com.adpanshi.cashloan.business.core.common.db.DatabaseContextHolder;
import com.adpanshi.cashloan.business.core.common.enums.ManageExceptionEnum;
import com.adpanshi.cashloan.business.core.common.util.RdPage;
import com.adpanshi.cashloan.business.rule.domain.RuleInfo;
import com.adpanshi.cashloan.business.rule.enums.RuleEngineEnum;
import com.adpanshi.cashloan.business.rule.model.ResultModel;
import com.adpanshi.cashloan.business.rule.model.RuleInfoDetail;
import com.adpanshi.cashloan.business.rule.service.RuleEngineConfigService;
import com.adpanshi.cashloan.business.rule.service.RuleInfoService;
import com.alibaba.fastjson.JSONArray;
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
 * 表字段维护Controller
 *
 * @version 1.0.0
 * @date 2016-12-20 13:58:48
 * @updateDate 2017/12/28
 * @updator huangqin
 */
@Controller
@Scope("prototype")
@RequestMapping("/manage/ruleInfo/")
public class RuleInfoController extends RuleBaseController{

    @Resource
    private RuleInfoService ruleInfoService;
    @Resource
    private RuleEngineConfigService ruleEngineConfigService;

    /**
     * 表字段维护-下拉列表查询
     * @return ResponseEntity<ResultModel>
     * @author huangqin
     * @date 2018-01-01 15:04
     * */
    @RequestMapping(value = "dropDownList.htm", method = {RequestMethod.POST})
    public ResponseEntity<ResultModel> roleDropDownList() {
        this.logger.info("------规则引擎-规则配置-下拉列表查询------");
        //读库标志
        DatabaseContextHolder.setDbName(DatabaseContextHolder.READ_DB);
        Map<String, Object> result = new HashMap<>();
        result.put("tableNameList", ruleInfoService.findAllTbName());
        result.put("ruleState", RuleEngineEnum.RULE_STATE.EnumValueS());
        return new ResponseEntity<>(ResultModel.ok(result), HttpStatus.OK);
    }

    /**
     * 查询借款规则列表,表字段维护查询
     *
     * @param searchParams
     * @param currentPage
     * @param pageSize
     * @return ResponseEntity<ResultModel>
     * @date 2017/12/28
     * @auther huangqin
     */
    @RequestMapping(value = "ruleList.htm", method = {RequestMethod.POST})
    public ResponseEntity<ResultModel> ruleList(@RequestParam(value = "searchParams", required = false) String searchParams,
                                                @RequestParam(value = "current") Integer currentPage,
                                                @RequestParam(value = "pageSize") Integer pageSize) throws Exception{
        Map<String, Object> params = parseToMap(searchParams,true);
        Page<RuleInfo> page = ruleInfoService.ruleList(params, currentPage, pageSize);
        Map<String, Object> result = new HashMap<>();
        result.put(Constant.RESPONSE_DATA, page);
        result.put(Constant.RESPONSE_DATA_PAGE, new RdPage(page));
        return new ResponseEntity<>(ResultModel.ok(result), HttpStatus.OK);
    }

    /**
     * 查询规则配置信息
     *
     * @param id
     * @return ResponseEntity<ResultModel>
     * @date 2017/12/28
     * @auther huangqin
     */
    @RequestMapping(value = "getRuleInfo.htm", method = {RequestMethod.POST})
    public ResponseEntity<ResultModel> getRuleInfo(@RequestParam(value = "id") Long id) {
        Map<String, Object> data = new HashMap<>();
        RuleInfo ruleInfo = ruleInfoService.getById(id);
        if (ruleInfo != null) {
            data.put("id", ruleInfo.getId());
            data.put("tbNid", ruleInfo.getTbNid());
            data.put("tbName", ruleInfo.getTbName());
            data.put("detail", ruleInfo.getDetail());
            if (StringUtils.isNotEmpty(ruleInfo.getDetail())) {
                List<RuleInfoDetail> rules = JSONArray.parseArray(ruleInfo.getDetail(), RuleInfoDetail.class);
                if (null != rules && !rules.isEmpty()) {
                    data.put("detail", rules);
                }
            }
        }
        return new ResponseEntity<>(ResultModel.ok(data), HttpStatus.OK);
    }

    /**
     * 规则配置信息启用/停用
     *
     * @param id
     * @param state
     * @return ResponseEntity<ResultModel>
     * @date 2017/12/28
     * @auther huangqin
     */
    @RequestMapping(value = "modifyInfoState.htm", method = {RequestMethod.POST})
    public ResponseEntity<ResultModel> modifyInfoState(@RequestParam(value = "id") Long id, @RequestParam(value = "state") String state) {
        if (ruleInfoService.modifyInfoState(id, state) <= 0) {
            return new ResponseEntity<>(ResultModel.error(ManageExceptionEnum.FAIL_UPDATE_CODE_VALUE), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    /**
     * 添加/修改规则配置信息
     *
     * @param ruleInfo
     * @retuen
     */
    @RequestMapping(value = "saveOrUpdateRuleInfo.htm", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseEntity<ResultModel> saveOrUpdateRuleInfo(RuleInfo ruleInfo) {
        if (null == ruleInfo) {
            return new ResponseEntity<>(ResultModel.error(ManageExceptionEnum.FAIL_CODE_PARAM_INSUFFICIENT), HttpStatus.OK);
        }
        //保存
        if (null == ruleInfo.getId() || ruleInfo.getId().compareTo(0L) <= 0) {
            //校验参数
            List<RuleInfoDetail> rules = JSONArray.parseArray(ruleInfo.getDetail(), RuleInfoDetail.class);
            if (!new RuleInfoDetail().validData(rules)) {
                return new ResponseEntity<>(ResultModel.error(ManageExceptionEnum.RULE_INFO_PARAM_ERROR_CODE_VALUE), HttpStatus.OK);
            }
            //保存
            ruleInfo.setAddTime(new Date());
            ruleInfo.setState("10");
            if (ruleInfoService.insert(ruleInfo) <= 0) {
                return new ResponseEntity<>(ResultModel.error(ManageExceptionEnum.FAIL_INSERT_CODE_VALUE), HttpStatus.OK);
            }
        } else {
            //更新(只更新Detail)
            RuleInfo ruleInfoUpdate = ruleInfoService.getById(ruleInfo.getId());
            ruleInfoUpdate.setDetail(ruleInfo.getDetail());
            if (ruleInfoService.updateById(ruleInfoUpdate) <= 0) {
                return new ResponseEntity<>(ResultModel.error(ManageExceptionEnum.FAIL_UPDATE_CODE_VALUE), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    /**
     * 查询数据库中所有表和字段
     *
     * @return ResponseEntity<ResultModel>
     * @date 2017/12/28
     * @auther huangqin
     */
    @RequestMapping(value = "findAllDataTable.htm", method = {RequestMethod.POST})
    public ResponseEntity<ResultModel> findAllDataTable() {
        List<Map<String, Object>> data = new ArrayList<>();
        //查询所有表和描述
        List<Map<String, Object>> list = ruleEngineConfigService.findTable();
        Map<String, Object> map = new HashMap<>();
        //查询所有字段和描述
        List<Map<String, Object>> columnList = ruleEngineConfigService.findColumnByName(map);
        if (null != list && list.size() > 0) {
            List<RuleInfo> infolist = ruleInfoService.findAll(map);
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> result = list.get(i);
                //是否已选中
                result.put("checked", ruleInfoService.checkTable(infolist, (String) result.get("tableName")));
                List<Map<String, Object>> children = new ArrayList<>();
                if (null != columnList && columnList.size() > 0) {
                    for (int j = 0; j < columnList.size(); j++) {
                        Map<String, Object> childrenmap = new HashMap<>();
                        Map<String, Object> column = columnList.get(j);
                        if (column.get("tableName").equals(result.get("tableName"))) {
                            childrenmap.put("columnName", column.get("columnName"));
                            String columnComment = ((String) column.get("columnComment"));
                            childrenmap.put("columnComment", columnComment);
                            //类型
                            childrenmap.put("type", Constant.MYSQL_DATATYPE_INT.contains((String) column.get("data_type")) ? "int" : "string");
                            //是否已选中
                            childrenmap.put("checked", ruleInfoService.checkColumn(infolist, (String) column.get("tableName"), (String) column.get("columnName")));
                            children.add(childrenmap);
                        }
                    }
                }
                result.put("children", children);
                data.add(result);
            }
        }
        return new ResponseEntity<>(ResultModel.ok(data), HttpStatus.OK);
    }
}