package com.adpanshi.cashloan.business.cl.mapper;

import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;
import com.adpanshi.cashloan.business.rule.model.tianchuang.MobileUser;

import java.util.List;

@RDBatisDao
public interface TCMobileUserMapper {
	
	void insert(MobileUser mobileUser);

	/**
	 * 删除通过请求id
	 * @param list
	 * @return
	 */
	int deleteByOrderId(List<String> list);
}
