package com.adpanshi.cashloan.business.system.service;

import com.adpanshi.cashloan.business.core.common.model.AuthUserRole;
import com.adpanshi.cashloan.business.system.domain.SysUser;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;


/**
 * 系统用户Service
 * @version 1.0
 * @created 2014年9月22日 下午3:48:21
 */
public interface SysUserService {

    /**
     * 根据用户ID修改用户密码
     * @param sysUser 用户
     * @return boolean
     */
    boolean editUserPassWord(SysUser sysUser) ;

    /**
     * @auther huangqin
     * @description 新增一个用户
     * @param sysUser
     * @param roleId
     * @return
     * @data 2017-12-20
     */
    void addUser(SysUser sysUser, Long roleId) ;

    /**
     * @auther huangqin
     * @description 新增一个用户
     * @param userinfo
     * @param sysUser
     * @param roleIds
     * @return
     * @data 2017-12-20
     */
    void addUser(AuthUserRole userinfo, SysUser sysUser, Long... roleIds) ;

    /**
     * @auther huangqin
     * @description 根据id找到SysUser
     * @param id
     * @return SysUser
     * @data 2017-12-20
     */
    SysUser getUserById(Long id) ;

    /**
     * @auther huangqin
     * @description 修改用户
     * @param user
     * @return int
     * @data 2017-12-20
     */
    int userUpdate(SysUser user) ;

    /**
     * 根据用户名查询用户信息
     * @param userName 用户名
     * @return SysUser
     */
    SysUser getUserByUserName(String userName) ;

    /**
     * @auther huangqin
     * @description 根据工号查询SysUsers
     * @param number
     * @return SysUser
     * @data 2017-12-20
     */
    SysUser getUserByNumber(String number) ;

    /**
     * @auther huangqin
     * @description 查询用户信息
     * @param currentPage
     * @param pageSize
     * @param params
     * @return age<Map<String, Object>>
     * @data 2017-12-20
     */
    Page<Map<String, Object>> getUserPageList(int currentPage, int pageSize, Map<String, Object> params) ;

    /**
     * @param userinfo
     * @param sysUser
     * @param roleIds
     * @return Boolean
     * @auther huangqin
     * @description 更新用户信息及对应的角色信息
     * @data 2017-12-19
     */
    boolean updateSysUserById(AuthUserRole userinfo, SysUser sysUser, Long... roleIds) ;

    /**
     * @param id
     * @return int
     * @auther huangqin
     * @description 判断用户是否还在使用这个角色
     * @data 2017-12-19
     */
    int queryRoleUserIsUse(Long id);

    /**
     * @param params
     * @return List<Map<String, Object>>
     * @description 获取用户信息
     */
    List<Map<String, Object>> getUserInfo(Map<String, Object> params) ;

    /**
     * @param params
     * @return List<SysUser>
     * @description 获取用户名称
     */
    List<SysUser> getUserName(Map<String, Object> params) ;

}
