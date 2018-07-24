package com.adpanshi.cashloan.business.rule.controller;


import com.adpanshi.cashloan.business.core.common.enums.ManageExceptionEnum;
import com.adpanshi.cashloan.business.core.common.exception.ManageException;
import com.adpanshi.cashloan.business.core.common.exception.ServiceException;
import com.adpanshi.cashloan.business.core.common.model.AuthUserRole;
import com.adpanshi.cashloan.business.core.common.token.TokenManager;
import com.adpanshi.cashloan.business.core.common.util.JsonUtil;
import com.adpanshi.cashloan.business.system.domain.SysRole;
import com.adpanshi.cashloan.business.system.domain.SysUser;
import com.adpanshi.cashloan.business.system.service.SysRoleService;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 基类action
 *
 * @version 1.0
 * @created 2017年12月18日 下午22:11:21
 */
@Controller
public abstract class RuleBaseController {

    final Logger logger = LoggerFactory.getLogger(getClass());

    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected HttpSession session;

    @Resource
    protected SysRoleService roleService;
    @Resource
    protected TokenManager manager;

    @ModelAttribute
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.session = request.getSession();
    }

    /**
     * @return SysUser
     * @auther huangqin
     * @description 获取当前登录用户的用户
     * @data 2017-12-16
     */
    protected SysUser getLoginUser() {
        SysUser sysUser = manager.getSysUser(manager.getToken(request));
        if (sysUser != null) {
            return sysUser;
        }
        return null;
    }

    /**
     * @return AuthUserRole
     * @auther huangqin
     * @description 获取当前登录用户的自定义数据
     * @data 2017-12-16
     */
    protected AuthUserRole getAuthUserRole() {
        String token = manager.getToken(request);
        AuthUserRole authUserRole = manager.getAuthUserRole(token);
        if (authUserRole != null) {
            return authUserRole;
        }
        return null;
    }


    /**
     * @return SysRole
     * @auther huangqin
     * @description 获取当前登录用户的角色信息
     * @data 2017-12-16
     */
    public SysRole getRoleForLoginUser() throws ServiceException {
        AuthUserRole authUserRole = getAuthUserRole();
        Long roleId = authUserRole.getRoleId();
        SysRole role = roleService.getRoleById(roleId);
        return role;
    }

    /**
     * @return List<Long>
     * @auther huangqin
     * @description 获取当前登录用户的用户
     * @data 2017-12-16
     */
    public List<Long> getRole() {
        List<Long> roles = new ArrayList<Long>();
        AuthUserRole authUserRole = getAuthUserRole();
        roles.add(authUserRole.getRoleId());
        return roles;
    }

    /**
     * @auther huangqin
     * @description 将Sring类型的数据转成Map
     * @param data
     * @param canEmpty 是否可以为空 true: 可以为空; false: 不可以为空,抛出异常
     * @return Map
     * @data 2017-12-20
     */
    public static Map<String, Object> parseToMap(String data, boolean canEmpty )throws ManageException {
        Map<String, Object> map= new HashMap<>();
        if (StringUtils.isNotEmpty(data)) {
            try {
                map = JsonUtil.parse(data, Map.class);
                if (null != map && map.size() > 0){
                    // 集合不为空则开始递归去除字符串两端的空格
                    for(Map.Entry<String, Object> entry : map.entrySet()) {
                        if(null != map.get(entry.getKey()) && !"".equals(map.get(entry.getKey())))
                            map.put(entry.getKey(), map.get(entry.getKey()).toString().trim());
                    }
                }
            }catch (Exception ex){
                throw new ManageException(ManageExceptionEnum.FAIL_CODE_VALUE);
            }
        }else{
            //是否可以为空，不可以则抛出异常
            if(!canEmpty)
                throw new ManageException(ManageExceptionEnum.FAIL_CODE_PARAM_INSUFFICIENT);
        }
        return map;
    }

    /**
     * @auther huangqin
     * @description 将Sring类型的数据转成List
     * @param data
     * @param canEmpty 是否可以为空 true: 可以为空; false: 不可以为空,抛出异常
     * @return List
     * @data 2017-12-20
     */
    public static List parseToList(String data,Class clz,boolean canEmpty )throws ManageException{
        List list= null;
        if (StringUtils.isNotEmpty(data)) {
            try {
                list = JSONObject.parseArray(data,clz);
            }catch (Exception ex){
                throw new ManageException(ManageExceptionEnum.FAIL_CODE_VALUE);
            }
        }else{
            //是否可以为空，不可以则抛出异常
            if(!canEmpty)
                throw new ManageException(ManageExceptionEnum.FAIL_CODE_PARAM_INSUFFICIENT);
        }
        return list;
    }

    /**
     * @auther huangqin
     * @description 将Sring类型的数据转成Bean对象
     * @param data
     * @param clz
     * @param canEmpty 是否可以为空 true: 可以为空; false: 不可以为空,抛出异常
     * @return T
     * @data 2017-12-21
     */
    public static <T> T parseToBean(String data, Class<T> clz,boolean canEmpty )throws ManageException {
        T obj= null;
        if (StringUtils.isNotEmpty(data)) {
            try {
                obj = JsonUtil.parse(data, clz);
            }catch (Exception ex){
                throw new ManageException(ManageExceptionEnum.FAIL_CODE_VALUE);
            }
        }else{
            //是否可以为空，不可以则抛出异常
            if(!canEmpty)
                throw new ManageException(ManageExceptionEnum.FAIL_CODE_PARAM_INSUFFICIENT);
        }
        return obj;
    }

}
