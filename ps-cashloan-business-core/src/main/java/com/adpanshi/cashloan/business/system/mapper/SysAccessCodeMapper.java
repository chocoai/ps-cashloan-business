package com.adpanshi.cashloan.business.system.mapper;

import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;
import com.adpanshi.cashloan.business.system.domain.SysAccessCode;
import com.adpanshi.cashloan.business.system.domain.SysUser;
import com.adpanshi.cashloan.business.system.model.SysAccessCodeModel;

import java.util.List;
import java.util.Map;

/**
 * 访问码Dao
 * 

 * @version 1.0.0
 * @date 2017-03-24 17:37:49
 *
 *
 * 
 *
 */
@RDBatisDao
public interface SysAccessCodeMapper extends BaseMapper<SysAccessCode,Long> {
	
	/**
	 * 访问码信息列表查询
	 * @param params
	 * return
	 */
	List<SysAccessCodeModel> listAccessCodeModel(Map<String, Object> params);

	/**
	 * 保存新增用户
	 * @param ac
	 * @return
	 */
	int save(SysAccessCode ac);
	
	/**
	 * 根据ID更新
	 * @return
	 */
	int update(SysAccessCode ac);
	
	/**
	 * 查询某用户相同code数
	 * @param data
	 * @return
	 */
	int countCode(Map<String, Object> data);
	
	/**
	 * 查询访问码列表
	 * @param sysUserId
	 * @return
	 */
	List<SysAccessCode> listSysAccessCode(Long sysUserId);
	
	/**
	 * 查询访问码
	 * @param map
	 * @return
	 */
	SysAccessCode findSysAccessCode(Map<String, Object> map);
	
	/**
	 * 用户名列表
	 * @return
	 */
	List<SysUser> listUserName();
}
