package com.adpanshi.cashloan.cl.service;

import com.adpanshi.cashloan.cl.domain.CreditsUpgrade;
import com.adpanshi.cashloan.core.common.enums.CreditsUpgradeEnum.SEND_STATUS;
import com.adpanshi.cashloan.core.common.service.BaseService;

import java.util.List;

/**
 * @author qing.yunhui 
 * @Since 2011-2017
 * @create 2017-10-30 20:46:14
 * @history
 */
public interface CreditsUpgradeService extends BaseService<CreditsUpgrade,Long>{

	/**
	 * <p>查询临时额度正常的数据并且以userId进行分组、最后以等级进行倒序排序
	 *    (需要查询临时额度过期前一天的用户进行短信及推送通知...)
	 * </p>
	 * @param borrowRepayState 还款计划中的状态
	 * @param creditsUpgradeStatus 临时额度中的状态
	 * @param sendStatusList 发送状态集
	 * @return List<CreditsUpgrade>
	 * */
	List<CreditsUpgrade> queryCreditsByStatusWithGroupUserId(Integer borrowRepayState, Integer creditsUpgradeStatus, List<Integer> sendStatusList);

	/**
	 * <p>根据给定userId查找用户临时额度并计算用户剩余的临时额度之和</p>
	 * <p>可使用临时额度=临时额度总和  - 已使用临时额度总和</p>
	 * @param userId
	 * @return Double
	 * */
	Double getCreditsByUserId(Long userId);

	/**
	 * <p>用户临时额度修改</p>
	 * @param userId
	 * @param amount 已使用的临时额度值
	 * @param repay 还款or放款标志(true:还款、false:放款)
	 * @return
	 * */
	int modifyTmpCredits(Long userId, Double amount, boolean repay);

	/**
	 * <p> 额度提升记录作废</p>
	 * @param userId
	 * @param remarks   额度表中的说明(什么原因做废的)
	 * @return int
	 * */
	int updateCreditUpgradeExpired(Long userId, String remarks);

	/**
	 * <p> 临时额度前一天的提额数据进行消息推送后，修改发送状态 </p>
	 * @param userId
	 * @param remarks   额度表中的说明
	 * @param sendStatus
	 * @return int
	 * */
	int updateCreditUpgradeBySendStatus(Long userId, String remarks, SEND_STATUS sendStatus);

	/***
	 * <p>查询临时额度已失效的用户并进行去重处理(前置条件是用户没有未还的订单)、按阶段、过期时间倒序排序最后对user_id进行分组 </p>
	 * @param borrowRepayState
	 * @param creditsUpgradeStatus
	 * @return List<CreditsUpgrade>
	 * */
	List<CreditsUpgrade> queryCreditsInvalidByParams(Integer borrowRepayState, Integer creditsUpgradeStatus);
	
	/**
	 * <p>查询指定用户是否是提过额度，如果提额开关关闭，直接返回false</p>
	 * @param userId
	 * @return boolean
	 * */
	boolean isCreditUpgradeUser(Long userId);

	/**
	 * <p>查询指定用户是否提过额度,不加入提额开关判断</p>
	 *
	 * @param userId
	 * @return boolean
	 * @author yecy 20171225
	 */
	boolean isCreditUpgradeUserWithoutSwitch(Long userId);

	/**
	 * 放款后信用额度修改
	 *
	 * @param userId
	 * @param amount
	 */
	int modifyCreditAfterLoan(Long userId, double amount);

	/**
	 * 还款时，信用额度修改
	 *
	 * @param userId
	 * @param borrowAmount
	 */
	void modifyCreditAfterRepay(Long userId, Double borrowAmount);

}
