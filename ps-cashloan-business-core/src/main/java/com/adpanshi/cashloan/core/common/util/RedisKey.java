package com.adpanshi.cashloan.core.common.util;

import com.adpanshi.cashloan.core.common.enums.RedisCachePrefixEnum;

/**
 *
 * @author 8452
 */
public class RedisKey {
	public static String getTokenKey(String id){
		return RedisCachePrefixEnum.TokenCache.prefix() + id;
	}

	public static String getManagerMenuByRoleId(Long roleId){
		return RedisCachePrefixEnum.ManagerMenuByRoleCache.prefix() + roleId;
	}

	public static String getManagerHomeInfoByMethodName(String methodName){
		return RedisCachePrefixEnum.ManagerHomeInfoCache.prefix() + methodName;
	}

}
