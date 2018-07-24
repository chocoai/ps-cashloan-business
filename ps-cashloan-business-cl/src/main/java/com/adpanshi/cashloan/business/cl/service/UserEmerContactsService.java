package com.adpanshi.cashloan.business.cl.service;

import com.adpanshi.cashloan.business.core.common.service.BaseService;
import com.adpanshi.cashloan.business.rule.domain.UserEmerContacts;

import java.util.List;
import java.util.Map;


/**
 * 紧急联系人表Service
 * 
 * @author
 * @version 1.0.0
 * @date 2017-02-14 11:24:05
 *
 *
 * 
 *
 */
public interface UserEmerContactsService extends BaseService<UserEmerContacts, Long>{

	public List<UserEmerContacts> getUserEmerContactsByUserId(Map<String, Object> paramMap);

	/**
	 * 保存并更新联系人
	 *
	 * @return
	 */
	public Map<String, Object> saveOrUpdate(String name, String phone, String relation, String type, String userId,
                                            String operatingSystem, String systemVersions, String phoneType, String phoneBrand, String phoneMark,
                                            String versionName, String versionCode, String mac);
 
}
