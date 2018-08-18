package com.adpanshi.cashloan.cl.facade;

import com.adpanshi.cashloan.cl.domain.Sms;
import com.adpanshi.cashloan.core.domain.User;

import java.util.Map;

/***
 ** @category 请用一句话来描述其用途...
 ** @author qing.yunhui
 ** @email: 280672161@qq.com
 ** @createTime: 2017年12月18日下午5:08:48
 **/
public interface UserFacade {
	
	/**
	 * <p>自动登陆</p>
	 * @param refreshToken
	 * @param loginType 登陆类型
	 * @return Object
	 * */
	Object autoLogin(String refreshToken, Integer loginType);

	/**
	 * <p>根据给定phone查询用户是否存在</p>
	 * @param phone
	 * @return boolean
	 * */
	boolean isPhoneExists(String phone);

	/**
	 * 查询最新一条短信记录
	 * @param phone
	 * @param smsType
	 * @return Sms
	 */
	Sms findTimeMsg(String phone, String smsType);

	/**
	 * <p>根据给定id查询user</p>
	 * @param id
	 * @return User
	 * */
	User queryUserById(Long id);

	int updateSelective(User user);

	Map<String,Object> info(String userId);

	Map<String,Object> getTradeState(Long userId);

	Map<String,Object> validateTradePwd(Long userId, String tradePwd);

}
