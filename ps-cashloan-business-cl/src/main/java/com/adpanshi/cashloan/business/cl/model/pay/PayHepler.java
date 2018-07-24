package com.adpanshi.cashloan.business.cl.model.pay;

import com.adpanshi.cashloan.business.cl.model.pay.lianlian.*;

/**
 * 支付接口
 * 

 * @version 1.0.0
 * @date 2017年4月19日 下午4:09:30 
 * Copyright 粉团网路 All Rights Reserved
 *
 *
 */
public interface PayHepler {

	/**
	 * 签约
	 * 
	 * @param model
	 * @return
	 */
	public BasePaymentModel authSign(AuthSignModel model);

	/**
	 * 授权
	 * 
	 * @return
	 */
	public BasePaymentModel authApply(AuthApplyModel model);

	/**
	 * 取消签约
	 * 
	 * @param model
	 * @return
	 */
	public BasePaymentModel cancelAuthSign(CancelAuthSignModel model);

	/**
	 * 付款
	 * 
	 * @param model
	 * @return
	 */
	public BasePaymentModel payment(PaymentModel model);

	/**
	 * 确认付款
	 * 
	 * @param model
	 * @return
	 */
	public BasePaymentModel confirmPayment(ConfirmPaymentModel model);

	/**
	 * 扣款还款
	 * 
	 * @return
	 */
	public BasePaymentModel repayment(RepaymentModel model);
	
	/**
	 * 查询签约结果
	 * 
	 * @param model
	 * @return
	 */
	public BasePaymentModel queryAuthSign(QueryAuthSignModel model);

	
	/**
	 * 查询付款交易
	 * 
	 * @param model
	 * @return
	 */
	public BasePaymentModel queryPayment(QueryPaymentModel model);


	/**
	 * 扣款还款查询
	 * 
	 * @param model
	 * @return
	 */
	public BasePaymentModel queryRepayment(QueryRepaymentModel model);
	
	/**
	 * 对账
	 */
	
	
	
}
