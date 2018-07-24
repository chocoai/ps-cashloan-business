package com.adpanshi.cashloan.business.rule.service;

import com.adpanshi.cashloan.business.core.common.service.BaseService;
import com.adpanshi.cashloan.business.core.domain.BorrowMain;
import com.adpanshi.cashloan.business.rc.domain.TppBusiness;
import com.adpanshi.cashloan.business.rule.domain.TongdunReqLog;

/**
 * 同盾第三方请求记录Service
 * 

 * @version 1.0.0
 * @date 2017-04-26 15:26:56
 *
 *
 *
 *
 */
public interface TongdunReqLogService extends BaseService<TongdunReqLog, Long>{

	/**
	 * 同盾决策引擎接口
	 *

	 * @param userId
	 * @param bussiness
	 * @param mobileType
	 * @param reBorrowUser
	 * @return
	 */
	String extraTdRiskRequest(Long userId, BorrowMain borrow, TppBusiness bussiness, String mobileType, boolean reBorrowUser);


	/**
	 * 根据borrow 查找cl_tongdun_req_log记录
	 * @param borrow
	 * @return
	 */
	TongdunReqLog getReqLoglByBorrowId(BorrowMain borrow);

}
