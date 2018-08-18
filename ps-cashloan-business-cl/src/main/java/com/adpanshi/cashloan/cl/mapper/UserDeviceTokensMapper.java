
package com.adpanshi.cashloan.cl.mapper;

import com.adpanshi.cashloan.cl.domain.UserDeviceTokens;
import com.adpanshi.cashloan.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.core.common.mapper.RDBatisDao;

import java.util.Map;

/**
 * @author qing.yunhui 
 * @Since 2011-2017
 * @create 2017-09-01 16:47:11
 * @history
 */
@RDBatisDao
public interface UserDeviceTokensMapper extends BaseMapper<UserDeviceTokens,Long>{
	
	/**
	 * <p>根据给定userId、deviceTokens查询记录</p>
	 * @param map
	 * @return UserDeviceTokens
	 */
	UserDeviceTokens getByUserIdWithTokens(Map<String, Object> map);

	/**
	 * <p>根据给定的userId、state 查询最近一次记录</p>
	 * @param map 待查询的数据
	 * @return UserDeviceTokens
	 */
	UserDeviceTokens getLastTimeByUserIdWithState(Map<String, Object> map);
}
