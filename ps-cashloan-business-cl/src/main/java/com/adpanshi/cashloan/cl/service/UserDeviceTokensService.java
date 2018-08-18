package com.adpanshi.cashloan.cl.service;

import com.adpanshi.cashloan.cl.domain.UserDeviceTokens;
import com.adpanshi.cashloan.core.common.service.BaseService;

/**
 * @author qing.yunhui 
 * @Since 2011-2017
 * @create 2017-09-01 16:47:11
 * @history
 */
public interface UserDeviceTokensService extends BaseService<UserDeviceTokens,Long>{
    
	/**
	 * <p>根据给定userId、deviceTokens、state查询记录</p>
	 * @param userId 必填项
	 * @param deviceTokens  可选
	 * @param state  可选
	 * @return UserDeviceTokens
	 * */
	UserDeviceTokens getByUserIdWithTokens(Long userId, String deviceTokens, Integer state);

	/**
	 * <p>新增or更新</p>
	 * <p>
	 * 	 考虑到用户会更换手机，而每个手机的tokens不一样，tokens以用户登陆成功后的tokens 为准、
	 *   查询user_id 判断已入库的tokens跟当前的tokens是否一致，如果不一至，则更新之。
	 * </p>
	 * @param userId 用户id
	 * @param deviceTokens 设备id
	 * @param mobileType 系统类型
	 * */
	int saveOrUpdateUserDeviceTokens(Long userId, String deviceTokens, Integer mobileType);

	/**
	 * <p>新增</p>
	 * <p>用户每次切换手机时，都记录入库(一个用户对应多个设备) </p>
	 * @param userId 用户id
	 * @param deviceTokens 设备id
	 * @param mobileType 系统类型
	 * */
	int saveUserDeviceTokens(Long userId, String deviceTokens, Integer mobileType);

	/**
	 * <p>根据给定的userId、state 查询最近一次记录</p>
	 * @param userId 必填项
	 * @param state  可选项
	 * @return UserDeviceTokens
	 * */
	UserDeviceTokens getLastTimeByUserIdWithState(Long userId, Integer state);
	
	
	
}