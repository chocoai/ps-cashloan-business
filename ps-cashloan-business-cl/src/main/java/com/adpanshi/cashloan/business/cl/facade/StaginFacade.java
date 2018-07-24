package com.adpanshi.cashloan.business.cl.facade;

import com.adpanshi.cashloan.business.cl.model.StaginRepaymentPlanData;
import com.adpanshi.cashloan.business.core.model.StaginDetailModel;
import com.adpanshi.cashloan.business.rule.model.StaginRepaymentModel;

import java.util.List;

/***
 ** @category 分期服务接口...
 ** @author qing.yunhui
 ** @email: 280672161@qq.com
 ** @createTime: 2017年12月24日下午4:14:28
 **/
public interface StaginFacade {
	
	/**
	 * <p>根据给定参数查询分期详情</p>
	 * @param userId
	 * @param borMainId
	 * @return 
	 * */
	StaginDetailModel getStaginDetailBy(Long userId, Long borMainId);


	/**
	 * <p>(分期还款明细列表)  根据给定userId及borrowMainId 查询还款明细列表 </p>
	 * @param userId
	 * @param borrowMainId
	 * @return
	 * */
	List<StaginRepaymentModel> queryRepaymentsByUserIdWithBorMainId(Long userId, Long borrowMainId);

	/**
	 * <p>(分期还款-还款计划列表)  根据给定userId及borrowMainId 查询还款计划列表 </p>
	 * @param userId
	 * @param borrowMainId
	 * @param repayment (可选项:true 已还款、false 未还款)
	 * @return
	 * */
	StaginRepaymentPlanData queryRepaymentPlanByUserIdWithBorMainId(Long userId, Long borrowMainId, Boolean repayment);

	/**
	 * <p>分期主动还款</p>
	 * @param userId 待还款的用户
	 * @param borrowOrderNos 待还款的订单号集
	 * */
	Object activePayment(long userId, String[] borrowOrderNos);
	
}
