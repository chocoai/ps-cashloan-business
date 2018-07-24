package com.adpanshi.cashloan.business.cl.mapper;

import com.adpanshi.cashloan.business.cl.domain.UserExamine;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;

import java.util.List;
import java.util.Map;

@RDBatisDao
public interface UserExamineMapper extends BaseMapper<UserExamine,Long> {
	/**
	 * 查询全部信审人信息
	 * @param userlist
	 * @return
	 */
	List<UserExamine> listUserExamineInfo(Map<String, Object> userlist);
	/**
	 * 添加信审人信息
	 * @return
	 */
	List<Map<String, Object>> listadduser();
	
}
