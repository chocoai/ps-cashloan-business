package com.adpanshi.cashloan.business.rule.service;

import com.adpanshi.cashloan.business.core.common.service.BaseService;
import com.adpanshi.cashloan.business.rule.domain.RuleInfo;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/**
 * 规则信息Service
 *
 * @version 1.0.0
 * @date 2016-12-20 13:58:48
 */
public interface RuleInfoService extends BaseService<RuleInfo, Long> {
    /**
     * 查询
     *
     * @param map
     * @return
     */
    List<RuleInfo> findAll(Map<String, Object> map);

    /**
     * 分页查询
     *
     * @param params
     * @param currentPage
     * @param pageSize
     * @return
     */
    Page<RuleInfo> ruleList(Map<String, Object> params, int currentPage, int pageSize);

    /**
     * 检验表是否已经保存过
     *
     * @param list
     * @param table
     * @return
     */
    boolean checkTable(List<RuleInfo> list, String table);

    /**
     * 检验表字段是否已经保存过
     *
     * @param list
     * @param table
     * @param column
     * @return
     */
    boolean checkColumn(List<RuleInfo> list, String table, String column);

    /**
     * 编辑状态
     *
     * @param id
     * @param state
     * @return
     */
    int modifyInfoState(Long id, String state);

    /**
     * 查询规则信息所有的表名
     * */
    List<String> findAllTbName();
}
