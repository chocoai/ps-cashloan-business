package com.adpanshi.cashloan.business.cl.service;

import com.adpanshi.cashloan.business.cl.domain.Sms;
import com.adpanshi.cashloan.business.core.common.service.BaseService;

import java.util.Date;

/**
 * 短信记录Service
 */
public interface ClSmsService extends BaseService<Sms, Long>{

	/**
	 * 查询与最近一条短信的时间差（秒）
	 */
	long findTimeDifference(String phone, String type);

	/**
	 * 根据手机号码、短信验证码类型查询今日可获取次数，防短信轰炸
	 */
	int countDayTime(String phone, String email, String type);

	/**
	 * 发送短信
	 */
	long sendSms(String phone, String type);

	/**
	 * 发送邮箱信息
	 */
	long sendEmailSms(String email, String type);

	/**
	 * 验证短信
	 */
	int verifySms(String phone, String email, String type, String code);
	/**
	 * 查询用户
	 */
	int findUser(String phone);
	/**
	 * 查询用户
	 */
	int findUser(String key, String type);

	/**
	 * 逾期发送通知短信
	 */
	int overdue(long borrowId, long userId);

	/**
	 * 审核不通过通知
	 */
	int refuse(long userId, int day, String orderNo);

	/**
	 * 立即还款成功
	 *
	 * @param userId 用户id
	 * @param borrowMainId 主借款订单id
	 * @param repayAmount 还款金额
	 * @param repayOrderNo 还款单号（计算还款期数）
	 */
	int activePayment(long userId, long borrowMainId, Date settleTime, Double repayAmount, String repayOrderNo);

	/**
	 * 老用户提额短信&消息推送
	 * @param userId	用户ID
	 * @param credits 提额的额度
	 * @param validPeriod 有效期
	 * */
	int creditsUpgrade(Long userId, String credits, String validPeriod);

	/**
	 * 创泰短信发送成功通知
	 * */
    int updateByMsgid(String msgid, String status);
	/**
	 * 放款成功短信发送
	 * */
    int payment(Long userId, Long borrowMainId, Date date, Double amount, String orderNo);
}
