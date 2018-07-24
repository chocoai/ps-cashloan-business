package com.adpanshi.cashloan.business.system.service;

import com.adpanshi.cashloan.business.core.common.exception.ServiceException;
import com.adpanshi.cashloan.business.system.domain.SysUserRole;

import java.util.List;
import java.util.Map;


/**
 * 
 * 用户角色关联信息service接口
 * @version 1.0

 * @created 2014年9月23日 上午9:55:01
 */
public interface SysUserRoleService {
	/**
	 * 用户角色关联信息查询
	 * @param userId 角色ID
	 * @return 关联信息List
	 * @throws ServiceException 
	 */
	List<SysUserRole> getSysUserRoleList(Long userId) throws ServiceException;

	/**
	 * 用户角色关联信息查询
	 * @param map 角色ID
	 * @return 关联信息List
	 * @throws ServiceException
	 */
	List<SysUserRole> getSysUserRoleList(Map<String, Object> map) throws ServiceException;
	
}
