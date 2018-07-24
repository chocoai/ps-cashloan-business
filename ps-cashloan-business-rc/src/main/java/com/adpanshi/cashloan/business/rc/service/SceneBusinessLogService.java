package com.adpanshi.cashloan.business.rc.service;

import com.adpanshi.cashloan.business.core.common.service.BaseService;
import com.adpanshi.cashloan.business.rc.domain.SceneBusinessLog;

/**
 * 场景与第三方征信接口执行记录

 * @version 1.0
 * @date 2017年4月11日上午11:51:09
 * Copyright 粉团网路 现金贷  All Rights Reserved
 *
 * 研发中心：rdc@fentuan123.com
 *
 */
public interface SceneBusinessLogService extends BaseService<SceneBusinessLog, Long> {

	
	/**
	 * 是否有未执行完的接口
	 * @return
	 */
	boolean haveNeedExcuteService(Long borrowId);
	
	/**
	 * 是否需要执行
	 * @param userId
	 * @param info
	 * @return true  有未完成的接口，false 没有未完成的接口
	 * @throws Exception
	 */
	boolean needExcute(Long userId, Long busId, String getWay, String period);
}
