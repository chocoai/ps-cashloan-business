package com.adpanshi.cashloan.cl.facade;

/***
 ** @category 分期服务接口...
 ** @author qing.yunhui
 ** @email: 280672161@qq.com
 ** @createTime: 2017年12月24日下午4:14:28
 **/
public interface StaginFacade {

	/**
	 * <p>分期主动还款</p>
	 * @param userId 待还款的用户
	 * @param borrowOrderNos 待还款的订单号集
	 * */
	Object activePayment(long userId, String[] borrowOrderNos);
	
}
