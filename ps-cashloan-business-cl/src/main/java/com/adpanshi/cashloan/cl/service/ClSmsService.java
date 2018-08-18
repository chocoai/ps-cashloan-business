package com.adpanshi.cashloan.cl.service;
import com.adpanshi.cashloan.cl.domain.Sms;
import com.adpanshi.cashloan.core.common.service.BaseService;
import com.github.pagehelper.Page;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 短信记录Service
 *
 * @author
 * @version 1.0.0
 * @date 2017-03-09 14:48:24
 *
 *
 *
 *
 */
public interface ClSmsService extends BaseService<Sms, Long>{

	int smsBatch(String id);
	/**
	 * 查询与最近一条短信的时间差（秒）
	 * @param phone
	 * @param type
	 * @return
	 */
	long findTimeDifference(String phone, String type);

	/**
	 * 根据手机号码、短信验证码类型查询今日可获取次数，防短信轰炸
	 * @param phone
	 * @param email
	 * @param type
	 * @return
	 */
	int countDayTime(String phone, String email, String type);

	/**
	 * 发送短信
	 * @param phone
	 * @param type
	 * @return
	 */
	long sendSms(String phone, String type);

	/**
	 * 发送邮箱信息
	 * @param type
	 * @param email
	 * @return
	 */
	long sendEmailSms(String email, String type);

	/**
	 * 验证短信
	 * @param phone
	 * @param email
	 * @param type
	 * @param code
	 * @return
	 */
	int verifySms(String phone, String email, String type, String code);
	/**
	 * 查询用户
	 * @param phone
	 * @return
	 */
	int findUser(String phone);
	/**
	 * 查询用户
	 * @param key
	 * @param type
	 * @return
	 */
	int findUser(String key, String type);

	/**
	 * 放款通知发送短信
	 * @return
	 */
	int loanInform(long userId, long borrowId);

	/**
	 * 扣款通知发送短信
	 * @return
	 */
	int repayInform(long userId, long borrowId);
	/**
	 * 逾期发送通知短信
	 * @param borrowId
	 */
	int overdue(long borrowId, long userId);

	/**
	 * 还款前通知
	 */
	int repayBefore(long userId, long borrowId);

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
	 * @return
	 */
	int activePayment(long userId, long borrowMainId, Date settleTime, Double repayAmount, String repayOrderNo);

	/**
	 * 老用户提额短信&消息推送
	 * @param userId
	 * @param credits 提额的额度
	 * @param validPeriod 有效期
	 * @return int
	 * */
	int creditsUpgrade(Long userId, String credits, String validPeriod);

	/**
	 * 查询短信推送记录
	 * @param paramMap
	 * @param current
	 * @param pageSize
	 * @return Page<Sms>
	 * */
	Page<Sms> getList(Map<String, Object> paramMap, int current, int pageSize);


	/**
	 * 创泰短信记录保存
	 * @param result
	 * @param type
	 * */
	int chuanglanSms(String result, String type);
	/**
	 * 创泰短信发送成功通知
     * @param msgid
     * @param status*/
    int updateByMsgid(String msgid, String status);
	/**
	 * 放款成功短信发送
	 * */
    int payment(Long userId, Long borrowMainId, Date date, Double amount, String orderNo);
	/**
	 * 获取时间段内的需重发短信列表
	 * */
	List<Sms> getResendSmsList(Map<String, Object> map);

    void updateByIds(Map<String, Object> smsInfo);

	int sendMsg(Sms sms);

    void updateByMsgids(Map<String, Object> tyhSmsInfo);
}
