package com.adpanshi.cashloan.business.cl.service;

import com.adpanshi.cashloan.business.cl.domain.CreditsUpgrade;
import com.adpanshi.cashloan.business.core.common.enums.CreditsUpgradeEnum.CreditsLeve;
import com.adpanshi.cashloan.business.core.common.service.BaseService;

/**
 * @author qing.yunhui 
 * @Since 2011-2017
 * @create 2017-10-30 20:46:14
 * @history
 */
public interface CreditsUpgradeService extends BaseService<CreditsUpgrade,Long>{
	

	/**
	 * <p>给用户按阶段提升额度</p>
	 * @param userId
	 * @param zhimafenAssert 芝麻分是否达标(true:芝麻分已达标,false:芝麻分未达标)
	 * @param overdueAssert  是否逾期(true:是逾期用户,false:不是逾期用户)
	 * @param creditsLeve 额度提升阶段、级别
	 * @return int
	 * */
	int creditsUpgradeStage(Long userId, boolean zhimafenAssert, boolean overdueAssert, CreditsLeve creditsLeve);

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
