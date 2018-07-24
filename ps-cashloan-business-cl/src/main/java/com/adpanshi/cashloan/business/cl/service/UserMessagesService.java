package com.adpanshi.cashloan.business.cl.service;


import com.adpanshi.cashloan.business.cl.domain.UserMessages;
import com.adpanshi.cashloan.business.core.common.service.BaseService;
import com.github.pagehelper.Page;

/**
 * 用户资料--联系人Service
 * 

 * @version 1.0.0
 * @date 2017-03-04 11:54:57
 *
 *
 * 
 *
 */
public interface UserMessagesService extends BaseService<UserMessages, Long> {

	/**
	 * 短信查询
	 * @param userId
	 * @param current
	 * @param pageSize
	 * @return
	 */
	Page<UserMessages> listMessages(long userId, int current, int pageSize);

}
