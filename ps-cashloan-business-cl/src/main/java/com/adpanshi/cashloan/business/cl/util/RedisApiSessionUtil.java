package com.adpanshi.cashloan.business.cl.util;

import com.adpanshi.cashloan.business.core.common.cache.RedisClient;

import java.util.Map;

/**
 * com.adpanshi.com.adpanshi.cashloan.api session 缓存工具类
 * @author zhubingbing
 *
 */
public class RedisApiSessionUtil {

	private final static String SESSION_KEY = "apiSession:";
	
	public static void remove(String key){
		 RedisClient.getInstance().del(getKey(key));
	}
	
	public static void put(String key, Map map, long expireSeconds){
		 RedisClient.getInstance().setSerialize(getKey(key), map, expireSeconds);
	}
	
	public static Map get(String key){
		return RedisClient.getInstance().getSerialize(getKey(key));
	}
	
	private static String getKey(String key){
		return SESSION_KEY+key;
	}
	
}
