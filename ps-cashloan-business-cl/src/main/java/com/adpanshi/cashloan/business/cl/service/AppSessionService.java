package com.adpanshi.cashloan.business.cl.service;

import com.adpanshi.cashloan.business.cl.domain.AppSession;
import com.adpanshi.cashloan.business.cl.model.user.AppSessionBean;
import com.adpanshi.cashloan.business.core.common.service.BaseService;

/**
 * @author qing.yunhui 
 * @Since 2011-2017
 * @create 2017-12-18 16:42:01
 * @history
 */
public interface AppSessionService extends BaseService<AppSession,Long>{
	
	AppSessionBean create(String loginname, int loginType);
	
	Object access(String token);
 
	boolean remove(String token);
}