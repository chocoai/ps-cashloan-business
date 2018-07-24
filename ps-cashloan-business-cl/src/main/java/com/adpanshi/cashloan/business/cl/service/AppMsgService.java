package com.adpanshi.cashloan.business.cl.service;

public interface AppMsgService {
	
	/**
	 * 放款通知发送短信
	 * @return
	 */
	int loanInform(long userId, String time);

	/**
	 * 扣款(还款后)通知发送短信
	 * @return
	 */
	int repayInform(long userId, String time, String loan);

	/**
	 * 逾期发送通知短信
	 */
	int overdue(long userId, String name, String phone);

	/**
	 * 老用户提额通知
	 * @param userId 用户id
	 * @param credits 提升的额度
	 * @param validPeriod 有效期
	 * @return int
	 * */
	int creditsUpgrade(Long userId, String credits, String validPeriod);

}
