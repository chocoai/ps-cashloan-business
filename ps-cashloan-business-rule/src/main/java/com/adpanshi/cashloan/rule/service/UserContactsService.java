package com.adpanshi.cashloan.rule.service;

import com.adpanshi.cashloan.core.common.service.BaseService;
import com.adpanshi.cashloan.rule.domain.UserContacts;
import com.adpanshi.cashloan.rule.domain.UserContactsMatch;
import com.github.pagehelper.Page;

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
	 * 查询通讯录
	 * @param userId
	 * @param current
	 * @param pageSize
	 * @return
	 */
	Page<UserContacts> listContacts(long userId, int current, int pageSize);

	/**
	 * 保存前删除原有记录
	 * @param userId 
	 * @param userId
	 * @return
	 */
	boolean deleteAndSave(List<Map<String, Object>> infos, String userId);

	Page<UserContactsMatch> listContactsNew(long userId, int current, int pageSize);
}
