package com.adpanshi.cashloan.business.system.service;

import com.adpanshi.cashloan.business.core.common.exception.PersistentDataException;
import com.adpanshi.cashloan.business.core.common.exception.ServiceException;
import com.adpanshi.cashloan.business.system.domain.SysRoleMenu;

import java.util.List;


/**
 * 
 * 角色菜单关联信息service接口
 * @version 1.0

 * @created 2014年9月23日 上午9:55:37
 */
public interface SysRoleMenuService {
	/**
	 * 角色菜单关联信息查询
	 * @param roleId 角色ID
	 * @return 角色List
	 * @throws ServiceException 
	 * @throws PersistentDataException 
	 */
	List<SysRoleMenu> getRoleMenuList(Long roleId) throws ServiceException, PersistentDataException;
	
}
