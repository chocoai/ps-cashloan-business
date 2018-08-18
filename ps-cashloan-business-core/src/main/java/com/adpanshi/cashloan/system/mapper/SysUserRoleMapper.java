package com.adpanshi.cashloan.system.mapper;

import com.adpanshi.cashloan.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.core.common.mapper.RDBatisDao;
import com.adpanshi.cashloan.system.domain.SysUserRole;

import java.util.List;
import java.util.Map;

/**
 * 
 * 用户角色DAO
 * @version 1.0
 * @author 8452
 * @created 2014年9月22日 下午2:47:14
 */
@RDBatisDao
public interface SysUserRoleMapper extends BaseMapper<SysUserRole, Long> {
	
	/**
	 * 根据用户ID删除
	 * @param userId 
	 * @version 1.0

	 * @created 2014年9月22日
	 */
	void deleteByUserId(long userId);

	/**
	 * getItemListByMap
	 * @param param
	 * @return
	 */
	List<SysUserRole> getItemListByMap(Map<String, Object> param);
	
}
