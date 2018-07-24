package com.adpanshi.cashloan.business.system.service;

import com.adpanshi.cashloan.business.system.domain.SysDict;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/**
 * 数据字典Service
 *
 * @version 1.1
 * @created 2014年9月23日 上午10:03:05
 * @Update_Date 2017/12/21
 * @Updtaor huangqin
 */
public interface SysDictService {

    /**
     * @param sysDict
     * @return boolean
     * @auther huangqin
     * @description 字典管理 字典修改/查询
     * @data 2017-12-21
     */
    boolean addOrModify(SysDict sysDict);

    /**
     * @param id
     * @return boolean
     * @auther huangqin
     * @description 字典管理 字典删除
     * @data 2017-12-21
     */
    boolean deleteDict(Long id);


    /**
     * @param current
     * @param pageSize
     * @param searchMap
     * @return Page<SysDict>
     * @auther huangqin
     * @description 字典管理 字典查询
     * @data 2017-12-21
     */
    Page<SysDict> getDictPageList(int current, int pageSize, Map<String, Object> searchMap);

    /**
     * @param type
     * @return RList<Map<String, Object>>
     * @auther huangqin
     * @description 获取系统参数类型数据字典
     * @data 2017-12-20
     */
    List<Map<String, Object>> getDictsCache(String type);

    /**
     * @param typeDict
     * @return RList<Map<String, Object>>
     * @auther huangqin
     * @description 根据type_code获取系统参数类型数据字典
     * @data 2017-12-20
     */
    SysDict findByTypeCode(String typeDict);
}
