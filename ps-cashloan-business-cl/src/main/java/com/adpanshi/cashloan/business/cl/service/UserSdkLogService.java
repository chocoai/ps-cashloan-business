package com.adpanshi.cashloan.business.cl.service;

import com.adpanshi.cashloan.business.cl.domain.UserSdkLog;
import com.adpanshi.cashloan.business.core.common.service.BaseService;


/**
 * sdk识别记录表Service
 * 
 * @author
 * @version 1.0.0
 * @date 2017-04-20 09:47:04
 *
 *
 *
 *
 */
public interface UserSdkLogService extends BaseService<UserSdkLog, Long>{

	/**
	 * 保存
	 * @param usl
	 * @return
	 */
	int save(UserSdkLog usl);

}
