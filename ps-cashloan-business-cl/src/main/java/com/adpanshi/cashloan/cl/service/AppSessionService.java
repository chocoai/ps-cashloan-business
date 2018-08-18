package com.adpanshi.cashloan.cl.service;

import com.adpanshi.cashloan.cl.domain.AppSession;
import com.adpanshi.cashloan.cl.model.user.AppSessionBean;
import com.adpanshi.cashloan.core.common.service.BaseService;

/**
 * @author qing.yunhui 
 * @Since 2011-2017
 * @create 2017-12-18 16:42:01
 * @history
 */
public interface AppSessionService extends BaseService<AppSession,Long>{
	
	/** 
	 * 创建表
	 * @method: create
	 * @param loginname
	 * @param loginType 
	 * @return: com.adpanshi.cashloan.cl.model.user.AppSessionBean 
	 * @throws
	 * @Author: Mr.Wange
	 * @Date: 2018/7/31 16:01
	 */ 
	AppSessionBean create(String loginname, int loginType);
	/** 
	 *  access
	 * @method: access
	 * @param token 
	 * @return: java.lang.Object 
	 * @throws
	 * @Author: Mr.Wange
	 * @Date: 2018/7/31 16:02
	 */ 
	Object access(String token);
 
	/** 
	 *  remove
	 * @method: remove
	 * @param token 
	 * @return: boolean 
	 * @throws
	 * @Author: Mr.Wange
	 * @Date: 2018/7/31 16:02
	 */ 
	boolean remove(String token);
}