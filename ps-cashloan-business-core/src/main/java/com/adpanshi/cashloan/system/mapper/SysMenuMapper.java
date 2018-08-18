package com.adpanshi.cashloan.system.mapper;

import com.adpanshi.cashloan.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.core.common.mapper.RDBatisDao;
import com.adpanshi.cashloan.system.domain.SysMenu;
import com.adpanshi.cashloan.system.model.MenuModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * 系统菜单DAO
 * @version 1.0

 * @created 2014年9月22日 下午2:57:58
 */
@RDBatisDao
public interface SysMenuMapper extends BaseMapper<SysMenu,Long>{
	
	/**
	 * 根据角色ids查询菜单model list信息
	 * @param roleIds 角色ids
	 * @return 菜单model list
	 */
	List<MenuModel> getMenuListByRoleIds(List<Long> roleIds);

	/**
	 * 根系菜单
	 * @param menu
	 * @return
	 */
	int updateMenu(Map<String, Object> menu);
	
	/**
	 * 添加
	 * @param menu
	 * @return
	 */
	int insertmap(Map<String, Object> menu);
	
	List<Map<String, Object>> fetchRoleMenuHas(Long roleId);

	List<Map<String, Object>> fetchRolebtnHas(Long roleId);
	
	List<Map<String,Object>> getMenuParentId(@Param(value = "menuLeafIds") String[] menuLeafIds);

	List<Map<String,Object>> getMenuByRoleId(Map<String, Object> serchParam);

	List<Map<String,Object>> selectBtnPermByMenuIdWithPermLevel(Map<String, Object> map);
}
