package com.adpanshi.cashloan.business.system.service.impl;

import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.model.AuthUserRole;
import com.adpanshi.cashloan.business.core.common.service.impl.BaseServiceImpl;
import com.adpanshi.cashloan.business.system.domain.SysRole;
import com.adpanshi.cashloan.business.system.mapper.SysRoleMapper;
import com.adpanshi.cashloan.business.system.service.SysRoleService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("rawtypes")
@Service(value = "sysRoleServiceImpl")
public class SysRoleServiceImpl extends BaseServiceImpl implements SysRoleService {
    @Resource
    private SysRoleMapper sysRoleMapper;

    @Override
    public List<SysRole> getRoleListByUserId(long userId) {
        return this.sysRoleMapper.getRoleListByUserId(userId);
    }

    @Override
    public SysRole getRoleById(long id) {
        return this.sysRoleMapper.findByPrimary(id);
    }

    public boolean isExistByNid(SysRole sysRole) {
        Map<String,Object> params=new HashMap<>();
        params.put("nid",sysRole.getNid());
        return sysRoleMapper.getRolecount(params) > 0;
    }

    @Override
    public int addRole(AuthUserRole loginUser, SysRole sysRole) {
        sysRole.setAddTime(new Date());
        sysRole.setAddUser(loginUser.getUserName());
        sysRole.setUpdateTime(new Date());
        sysRole.setUpdateUser(loginUser.getUserName());
        if(sysRole.getIsDelete() == null ){
            sysRole.setIsDelete((byte) 0);
        }
        return this.sysRoleMapper.save(sysRole);
    }

    @Override
    public int updateRole(AuthUserRole loginUser,SysRole sysRole) {
        sysRole.setUpdateTime(new Date());
        sysRole.setUpdateUser(loginUser.getUserName());
        return this.sysRoleMapper.update(sysRole);
    }

    @Override
    public int updateState(Long id, Integer isDelete) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("id", id.longValue());
        data.put("isDelete", isDelete.intValue());
        return this.sysRoleMapper.updateByMap(data);
    }

    @Override
    public List<SysRole> getList(Map<String, Object> paramMap) {
        return sysRoleMapper.listSelective(paramMap);
    }

    @Override
    public Page<SysRole>getList(Map<String, Object> paramMap, int current,int pageSize) {
        PageHelper.startPage(current, pageSize);
        return (Page<SysRole>)sysRoleMapper.listSelective(paramMap);
    }

    @Override
    public BaseMapper getMapper() {
        return null;
    }

}
