package com.adpanshi.cashloan.business.system.service.impl;

import com.adpanshi.cashloan.business.core.common.exception.ServiceException;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.service.impl.BaseServiceImpl;
import com.adpanshi.cashloan.business.system.domain.SysUserRole;
import com.adpanshi.cashloan.business.system.mapper.SysUserRoleMapper;
import com.adpanshi.cashloan.business.system.service.SysUserRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(value = "sysUserRoleServiceImpl")
public class SysUserRoleServiceImpl extends BaseServiceImpl<SysUserRole, Long> implements SysUserRoleService {

	@Resource
	private SysUserRoleMapper sysUserRoleMapper;
	
	@Override
	public List<SysUserRole> getSysUserRoleList(Long userId) throws ServiceException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		return sysUserRoleMapper.getItemListByMap(map);
	}

	@Override
	public List<SysUserRole> getSysUserRoleList(Map<String, Object> map) throws ServiceException {
		return sysUserRoleMapper.getItemListByMap(map);
	}


	@Override
	public BaseMapper<SysUserRole, Long> getMapper() {
		return sysUserRoleMapper;
	}
}
