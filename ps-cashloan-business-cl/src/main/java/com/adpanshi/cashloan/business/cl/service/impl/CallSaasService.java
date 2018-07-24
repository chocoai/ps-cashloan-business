package com.adpanshi.cashloan.business.cl.service.impl;

import com.adpanshi.cashloan.business.rule.domain.SaasRespRecord;

/***
 ** @category 统一调用saas服务接口...
 ** @author qing.yunhui
 ** @email: qingyunhui@fentuan360.com
 ** @createTime: 2018年4月3日下午2:23:56
 **/
public interface CallSaasService {

	/**
	 * <p>把saas响应的数据进行封装</p>
	 * @param userId
	 * @param reqId
	 * @param resId
	 * @param taskId
	 * @param code
	 * @param msg
	 * @param data
	 * @return SaasRespRecord
	 * */
	SaasRespRecord getSaasRespRecord(Long userId, String reqId, String resId, String taskId, String code, String msg, String data, Integer type);


}
