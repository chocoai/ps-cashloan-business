package com.adpanshi.cashloan.business.cl.service.impl;

import com.adpanshi.cashloan.business.cl.domain.UserDeviceTokens;
import com.adpanshi.cashloan.business.cl.enums.UserDeviceTokensStateEnum;
import com.adpanshi.cashloan.business.cl.mapper.UserDeviceTokensMapper;
import com.adpanshi.cashloan.business.cl.service.UserDeviceTokensService;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.service.impl.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author qing.yunhui 
 * @Since 2011-2017
 * @create 2017-09-01 16:47:11
 * @history
 */
@Service("userDeviceTokensService")
public class UserDeviceTokensServiceImpl extends BaseServiceImpl<UserDeviceTokens,Long> implements UserDeviceTokensService{

	Logger logger=LoggerFactory.getLogger(getClass());
	
	@Resource
	UserDeviceTokensMapper userDeviceTokensMapper;
	
	@Override
	public UserDeviceTokens getByUserIdWithTokens(Long userId, String deviceTokens,Integer state) {
		if(null==userId){
			logger.error(" Parameter cannot be null, userId={},deviceTokens={}",new Object[]{userId,deviceTokens});
			return null;
		}
		Map<String,Object> map=new HashMap<>();
		map.put("userId", userId);
		if(null!=deviceTokens)map.put("deviceTokens", deviceTokens);
		if(null!=state)map.put("state", state);
		return userDeviceTokensMapper.getByUserIdWithTokens(map);
	}

	@Override
	public BaseMapper<UserDeviceTokens, Long> getMapper() {
		return userDeviceTokensMapper;
	}

	@Override
	public int saveUserDeviceTokens(Long userId, String deviceTokens,Integer mobileType) {
		if(null==userId || null==deviceTokens){
			logger.info("------------->Parameter cannot be null.userId={},deviceTokens={}",new Object[]{userId,deviceTokens});
			return 0;
		}
		UserDeviceTokens udt= getByUserIdWithTokens(userId, deviceTokens, UserDeviceTokensStateEnum.ENABLE.getCode());
		if(null!=udt){
			logger.info("------------->设备id已入库，开始修改更新时间.");
			Map<String,Object> map=new HashMap<>();
			map.put("id", udt.getId());
			map.put("userId", udt.getUserId());
			map.put("etime", new Date());
			userDeviceTokensMapper.updateSelective(map);
			logger.info("------------->更新完毕...");
			return 0;
		}
		logger.info("-------------------->开始新增deviceTokens.");
		//新增之
		UserDeviceTokens data=new UserDeviceTokens();
		data.setDeviceTokens(deviceTokens);
		data.setUserId(userId);
		data.setState(UserDeviceTokensStateEnum.ENABLE.getCode());
		data.setMobileType(mobileType);
		return userDeviceTokensMapper.save(data);
	}

	@Override
	public UserDeviceTokens getLastTimeByUserIdWithState(Long userId, Integer state) {
		if(null==userId){
			logger.info("------------->Parameter cannot be null.userId={}",new Object[]{userId});
			return null;
		}
		Map<String,Object> map=new HashMap<>();
		map.put("userId", userId);
		if(null!=state)map.put("state", state);
		return userDeviceTokensMapper.getLastTimeByUserIdWithState(map);
	}
      
}