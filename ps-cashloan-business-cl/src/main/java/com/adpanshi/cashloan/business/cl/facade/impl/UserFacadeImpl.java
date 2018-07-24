package com.adpanshi.cashloan.business.cl.facade.impl;

import com.adpanshi.cashloan.business.cl.domain.Sms;
import com.adpanshi.cashloan.business.cl.facade.UserFacade;
import com.adpanshi.cashloan.business.cl.mapper.AppSessionMapper;
import com.adpanshi.cashloan.business.cl.mapper.SmsMapper;
import com.adpanshi.cashloan.business.cl.mapper.UserInviteMapper;
import com.adpanshi.cashloan.business.cl.service.AppSessionService;
import com.adpanshi.cashloan.business.core.common.exception.BaseRuntimeException;
import com.adpanshi.cashloan.business.core.common.util.StringUtil;
import com.adpanshi.cashloan.business.core.domain.User;
import com.adpanshi.cashloan.business.core.mapper.UserMapper;
import com.adpanshi.cashloan.business.cr.mapper.CreditMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/***
 ** @category 请用一句话来描述其用途...
 ** @author qing.yunhui
 ** @email: 280672161@qq.com
 ** @createTime: 2017年12月18日下午5:10:31
 **/
@Service("userFacade")
public class UserFacadeImpl implements UserFacade{

	@Resource
	private UserMapper userMapper;
	
	@Resource
	private AppSessionMapper appSessionMapper;
	
	@Resource
	private AppSessionService appSessionService;
	
	@Resource
	private SmsMapper smsMapper;
	
	@Resource
	private CreditMapper creditMapper;
	
	@Resource
	private UserInviteMapper userInviteMapper;
	
	@Override
	public Object autoLogin(String refreshToken,Integer loginType) {
		if(StringUtil.isBlank(refreshToken)){
			throw new BaseRuntimeException("refreshToken不能为空或null!");
		}
		Map<String,Object> paramMap=new HashMap<>();
		paramMap.put("uuid", refreshToken);
		User user=userMapper.findSelective(paramMap);
		if(null==user){
			return new Object[][] {{"success", false },{ "msg", "refreshToken不存在"}};
		}
		return appSessionService.create(user.getLoginName(), loginType);
	}

	@Override
	public boolean isPhoneExists(String phone) {
		return userMapper.getUserByPhone(phone)!=null;
	}

	@Override
	public Sms findTimeMsg(String phone, String smsType) {
		Map<String,Object> data=new HashMap<>();
		data.put("phone", phone);
		data.put("smsType", smsType);
		return smsMapper.findTimeMsg(data);
	}

	@Override
	public User queryUserById(Long id) {
		return userMapper.findByPrimary(id);
	}

	@Override
	public int updateSelective(User user) {
		return userMapper.update(user);
	}

	@Override
	public Map<String, Object> info(String userId) {
		return creditMapper.info(userId);
	}

	@Override
	public Map<String, Object> getTradeState(Long userId) {
		return userMapper.getTradeState(userId);
	}

	@Override
	public Map<String, Object> validateTradePwd(Long userId, String tradePwd) {
		return userMapper.validateTradePwd(userId, tradePwd);
	}

	@Override
	public Map<String, Object> inviteListCnt(Long userId) {
		return userInviteMapper.inviteListCnt(userId);
	}

	@Override
	public Map<String, Object> inviteList(Long userId, int start, int length) {
		return userInviteMapper.inviteList(userId, start, length);
	}
	
}
