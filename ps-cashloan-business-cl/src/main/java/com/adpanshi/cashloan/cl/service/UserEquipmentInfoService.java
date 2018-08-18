package com.adpanshi.cashloan.cl.service;

import com.adpanshi.cashloan.core.common.service.BaseService;
import com.adpanshi.cashloan.rule.domain.UserEquipmentInfo;


/**
 * 用户设备信息表Service
 * 

 * @version 1.0.0
 * @date 2017-04-17 17:32:05
 *
 *
 *
 *
 */
public interface UserEquipmentInfoService extends BaseService<UserEquipmentInfo, Long>{

	/**
	 * 信息入库
	 * @param uei
	 * @return
	 */
	int saveOrUpdate(UserEquipmentInfo uei);

	/**
	 * 查询
	 * @param userId
	 * @return
	 */
	UserEquipmentInfo findSelective(long userId);

	/**
	 * 保存设备指纹
	 * @param loginName
	 * @param blackBox
	 */
	void save(String loginName, String blackBox);

}
