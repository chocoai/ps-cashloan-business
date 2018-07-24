package com.adpanshi.cashloan.business.system.service;

import com.adpanshi.cashloan.business.core.common.model.AuthUserRole;
import com.adpanshi.cashloan.business.system.domain.SysRole;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/**
 * 系统角色Service
 * @version 1.0
 * @created 2014年9月23日 上午9:55:16
 */
public interface SysRoleService {

    /**
     * 查询查询用户所拥有的角色
     * @param userId 用户ID
     * @return List<SysRole>
     */
    List<SysRole> getRoleListByUserId(long userId);

    /**
     * 查询角色
     * @param id 主键ID
     * @return SysRole
     */
    SysRole getRoleById(long id);

    /**
     * @param paramMap
     * @return List<SysRole>
     * @auther huangqin
     * @description 查询角色列表
     * @data 2017-12-19
     */
    List<SysRole> getList(Map<String, Object> paramMap);

    /**
     * @param paramMap
     * @return List<SysRole>
     * @auther huangqin
     * @description 查询角色列表
     * @data 2017-12-19
     */
    Page<SysRole> getList(Map<String, Object> paramMap, int current, int pageSize);

    /**
     * @param sysRole
     * @return int
     * @auther huangqin
     * @description 根据nid判断是否有重复的用户
     * @data 2017-12-19
     */
    boolean isExistByNid(SysRole sysRole);

    /**
     * @param loginUser
     * @param sysRole
     * @return int
     * @auther huangqin
     * @description 新增角色
     * @data 2017-12-19
     */
    int addRole(AuthUserRole loginUser, SysRole sysRole);

    /**
     * @param sysRole
     * @return int
     * @auther huangqin
     * @description 更新角色信息
     * @data 2017-12-19
     */
    int updateRole(AuthUserRole loginUser, SysRole sysRole);

    /**
     * @param id
     * @param isDelete
     * @return int
     * @auther huangqin
     * @description 更新角色状态
     * @data 2017-12-19
     */
    int updateState(Long id, Integer isDelete);
}
