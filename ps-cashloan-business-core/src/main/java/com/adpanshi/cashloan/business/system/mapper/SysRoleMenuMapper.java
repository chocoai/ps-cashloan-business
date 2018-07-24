package com.adpanshi.cashloan.business.system.mapper;

import com.adpanshi.cashloan.business.core.common.exception.PersistentDataException;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;
import com.adpanshi.cashloan.business.system.domain.SysRoleMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * 角色菜单关系DAO
 * @version 1.0

 * @created 2014年9月22日 下午2:48:38
 */
@RDBatisDao
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu,Long> {
	
	/**
	 * 删除角色菜单关联表信息（物理删除）
	 * @param roleId 角色ID
	 * @version 1.0

	 * @created 2014年9月22日
	 */
	void deleteByRoleId(long roleId);
	
	/**
	 * 角色菜单关联信息查询 LIST
	 * @param roleId  角色ID
	 * @return 角色菜单关系列表
	 * @version 1.0

	 * @throws PersistentDataException 
	 * @created 2014年9月22日
	 */
	List<SysRoleMenu> getRoleMenuList(long roleId) throws PersistentDataException;
	
	void addRoleMenu(long roleId, Long menuId);

	/**
	 * <p>根据角色id、菜单id删除角色菜单关联表</p>
	 * @param map
	 * @return void
	 * */
	void deleteByRoleIdWithMenuIds(Map<String, Object> map);

	/**
	 * 批量分配角色权限
	 * @param roleId
	 * @param menuList
	 * @return int
	 * */
	int saveBantch(@Param("roleId") long roleId, @Param("menuList") Long[] menuList);
}
