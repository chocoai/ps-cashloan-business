package com.adpanshi.cashloan.business.cl.service;

import com.adpanshi.cashloan.business.core.common.service.BaseService;
import com.adpanshi.cashloan.business.rule.domain.UserContacts;

import java.util.List;
import java.util.Map;

/**
 * 用户资料--联系人Service
 * 

 * @version 1.0.0
 * @date 2017-03-04 11:52:26
 *
 *
 * 
 *
 */
public interface UserContactsService extends BaseService<UserContacts, Long>{

	/**
	 * 保存前删除原有记录
	 * @param userId 
	 * @param userId
	 * @return
	 */
	boolean deleteAndSave(List<Map<String, Object>> infos, String userId);

}
