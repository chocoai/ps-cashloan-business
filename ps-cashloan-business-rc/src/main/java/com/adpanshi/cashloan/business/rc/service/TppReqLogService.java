package com.adpanshi.cashloan.business.rc.service;

import com.adpanshi.cashloan.business.core.common.service.BaseService;
import com.adpanshi.cashloan.business.rc.domain.TppReqLog;

import java.util.Map;

/**
 * 第三方征信请求记录Service
 * 

 * @version 1.0.0
 * @date 2017-03-20 13:50:34
 *
 *
 *
 *
 */
public interface TppReqLogService extends BaseService<TppReqLog, Long>{

	/**
	 * 根据订单号修改记录
	 * @param params
	 * @return
	 */
	int modifyTppReqLog(TppReqLog log);
	
	/**
	 * 根据参数查询
	 * @param params
	 * @return
	 */
	TppReqLog findSelective(Map<String, Object> params);
	
}
