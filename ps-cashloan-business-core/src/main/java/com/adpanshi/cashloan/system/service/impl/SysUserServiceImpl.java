package com.adpanshi.cashloan.system.service.impl;

import com.adpanshi.cashloan.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.core.common.model.AuthUserRole;
import com.adpanshi.cashloan.core.common.service.impl.BaseServiceImpl;
import com.adpanshi.cashloan.core.common.util.code.MD5;
import com.adpanshi.cashloan.system.constant.SystemConstant;
import com.adpanshi.cashloan.system.domain.SysUser;
import com.adpanshi.cashloan.system.domain.SysUserRole;
import com.adpanshi.cashloan.system.mapper.SysUserMapper;
import com.adpanshi.cashloan.system.mapper.SysUserRoleMapper;
import com.adpanshi.cashloan.system.service.SysUserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(value = "sysUserService")
public class SysUserServiceImpl extends BaseServiceImpl<SysUser, Long> implements SysUserService {

    public static final Logger logger = LoggerFactory.getLogger(SysUserServiceImpl.class);

    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public boolean editUserPassWord(SysUser sysUser)  {
        return sysUserMapper.editUserPassWord(sysUser);
    }

    @Override
    public SysUser getUserById(Long id)  {
        return sysUserMapper.findByPrimary(id.longValue());
    }

    @Override
    public int userUpdate(SysUser sysUser)  {
        return this.sysUserMapper.update(sysUser);
    }

    @Override
    public Page<Map<String, Object>> getUserPageList(int currentPage, int pageSize, Map<String, Object> params)  {
        PageHelper.startPage(currentPage, pageSize);
        return (Page<Map<String, Object>>) sysUserMapper.listUserInfo(params);
    }

    @Override
    public void addUser( AuthUserRole userinfo,SysUser sysUser,Long... roleIds)  {
        //添加时间
        Date curDate = new Date();
        //因为从数据库取AddTime时，没有毫秒所以我们在存的时候就把毫秒去掉
        //为防止在存入数据库时，毫秒值会四舍五入影响到秒，在存入之前就去掉毫秒
        curDate = new Date(curDate.getTime()/1000*1000);
        //添加人
        String loginUserName = null != userinfo ? userinfo.getRealName() :"";
        //职位
        sysUser.setPosition((byte)0);
        sysUser.setAddTime(curDate);
        sysUser.setAddUser(loginUserName);
        sysUser.setUpdateTime(curDate);
        sysUser.setUpdateUser(loginUserName);
        // 账号初始密码
        sysUser.setPassword(MD5.encode(curDate.getTime()+SystemConstant.SYSTEM_PASSWORD_DEFAULT));
        // 用户状态：正常
        sysUser.setStatus(SystemConstant.USER_STATUS_NORMAL);
        // 增加用户
        sysUserMapper.save(sysUser);
        //保存用户角色
        for (int i = 0; i < roleIds.length; i++) {
            Long role = roleIds[i];
            // 增加用户角色关系
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setRoleId(role.longValue());
            sysUserRole.setUserId(sysUser.getId().longValue());
            sysUserRoleMapper.save(sysUserRole);
        }
    }

    @Override
    public void addUser( SysUser sysUser, Long roleId)  {
        //保存用户
        sysUserMapper.save(sysUser);
        // 增加用户角色关系
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setRoleId(roleId.longValue());
        sysUserRole.setUserId(sysUser.getId().longValue());
        sysUserRoleMapper.save(sysUserRole);
    }

    @Override
    public boolean updateSysUserById(AuthUserRole userinfo,SysUser sysUser,Long... roleIds)  {
        //首先删除角色关系
        Long userId = sysUser.getId();
        sysUserRoleMapper.deleteByUserId(userId);
        for (int i = 0; i < roleIds.length; i++) {
            Long roleId = roleIds[i];
            // 增加用户角色关系
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setRoleId(roleId.longValue());
            sysUserRole.setUserId(userId.longValue());
            sysUserRoleMapper.save(sysUserRole);
        }
        return sysUserMapper.update(sysUser) >0;
    }

    @Override
    public SysUser getUserByUserName(String userName)  {
        return sysUserMapper.getUserByUserName(userName);
    }

    @Override
    public SysUser getUserByNumber(String number)  {
        return sysUserMapper.getUserByNumber(number);
    }

    @Override
    public int queryRoleUserIsUse(Long id) {
        Map<String, Object> param = new HashMap<>();
        param.put("roleId", id.longValue());
        return sysUserMapper.queryRoleUserIsUse(param);

    }

    @Override
    public List<Map<String, Object>> getUserInfo(Map<String, Object> params)  {
        return sysUserMapper.getUserInfo(params);
    }

    @Override
    public BaseMapper<SysUser, Long> getMapper() {
        return sysUserMapper;
    }

    @Override
    public List<SysUser> getUserName(Map<String, Object> paramMap)  {
        return sysUserMapper.getSysUserByMap(paramMap);
    }
}
