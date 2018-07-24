package com.adpanshi.cashloan.business.core.common.util;


import com.adpanshi.cashloan.business.core.common.enums.RedisCachePrefixEnum;

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
	
	/*public static <T> String getEntityKey(String entityname){
		return RedisCachePrefixEnum.EntityCache.prefix() + entityname +"_";
	}
	
	public static <T> String getEntityKeyByUserId(String entityname){
		return RedisCachePrefixEnum.EntityCacheByUser.prefix() + entityname +"_";
	}
	
	public static <T> String getEntityKey(Class<T> clz, Integer id){
		return RedisCachePrefixEnum.EntityCache.prefix() + clz.getSimpleName() +"_" + id;
	}
	
	public static <T> String getEntityKey(Class<T> clz, String msg){
		return RedisCachePrefixEnum.EntityCache.prefix() + clz.getSimpleName() +"_" + msg;
	}*/
}
