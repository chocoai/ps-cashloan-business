package com.adpanshi.cashloan.business.cl.service;


import com.adpanshi.cashloan.business.cl.domain.SyncPaySub;
import com.adpanshi.cashloan.business.cl.model.pay.lianlian.RepaymentModel;
import com.adpanshi.cashloan.business.core.common.service.BaseService;
import com.adpanshi.cashloan.business.core.domain.Borrow;

import java.util.Map;

/**
 * 借款信息表Service
 * 
 * @author
 * @version 1.0.0
 * @date 2017-02-14 10:13:31
 *
 *
 * 
 *
 */
public interface ActivePaymentService extends BaseService<Borrow, Long> {

	/**
	 * 借款详情.借款信息
	 * @date 20170714
	 * @author nmnl
	 * @param borrowId
	 * @param userId
	 * @return
	 */
	Map<String, Object> syncQueryInfo(long borrowId, long userId);

	/**
	 * 借款信息.同步修改日志 pay,req,resp
	 * @date 20170714
	 * @author nmnl
	 * @param syncPaySub
	 * @return
	 */
	boolean syncNotifyInfo(SyncPaySub syncPaySub);

	/**
	 * 借款信息.异步修改日志 pay,req,resp
	 * @date 20170714
	 * @author nmnl
	 * @param model
	 * @param reqStr
	 * @return
	 */
	Map<String,Object> asynNotifyInfo(RepaymentModel model, String reqStr);
}
