package com.adpanshi.cashloan.business.system.mapper;

import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;
import com.adpanshi.cashloan.business.system.domain.SysRole;

import java.util.List;
import java.util.Map;

/**
 * 
 * 角色DAO
 * 
 * @version 1.0

 * @created 2014年9月22日 下午2:51:25
 */
@RDBatisDao
public interface SysRoleMapper extends BaseMapper<SysRole,Long> {
	List<? extends SysRole> getRolePageList(Map<String, Object> data);

	List<SysRole> getRoleListByUserId(Long userId);

	int deleteById(long id);

	int updateByMap(Map<String, Object> arg);
	
	List<Map<String, Object>> getByUserPassRolesList(Map<String, Object> data);

	int getRolecount(Map<String, Object> mapdata);

	long insertByMap(Map<String, Object> data);

}
