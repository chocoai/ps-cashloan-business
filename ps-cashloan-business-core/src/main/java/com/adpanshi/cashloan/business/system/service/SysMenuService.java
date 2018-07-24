package com.adpanshi.cashloan.business.system.service;

import java.util.List;
import java.util.Map;

/**
 * 菜单服务类
 *
 * @version 1.0
 * @created 2014年9月23日 上午9:54:35
 */
public interface SysMenuService {

    /**
     * 根据角色查询菜单
     * @param sysType
     * @param roleId
     * @return List<Map<String, Object>>
     * */
    List<Map<String, Object>> fetchRoleMenus(String sysType, Long roleId);

    /**
     * 修改用户权限
     * @param roleId
     * @paramlist
     * */
    boolean updateRoleMenuHas(Long roleId, Long... menus);

    /**
     * @param roleId
     * @return List<Map<String, Object>>
     * @auther huangqin
     * @description 获取所有菜单，角色拥有的checked为1，没有的checked 为 0
     * @data 2017-12-19
     */
    List<Map<String, Object>> fetchRoleMenuHas(Long roleId);

    /**
     * @param roleId
     * @return List<Map<String, Object>>
     * @auther huangqin
     * @description 获取用户所有的权限，角色拥有的checked为1，没有的checked 为 0
     * @data 2017-12-19
     */
    List<Map<String, Object>> fetchRolebtnHas(Long roleId);
}
