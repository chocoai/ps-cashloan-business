package com.adpanshi.cashloan.cl.util;

import com.adpanshi.cashloan.core.common.cache.RedisClient;

import java.util.Map;

/**
 * api session 缓存工具类
 * @author zhubingbing
 *
 */
public class RedisApiSessionUtil {

	private final static String SESSION_KEY = "apiSession:";
	
	/**
	 * 通过key移除缓存信息
	 * @method: remove
	 * @param key 
	 * @return: void 
	 * @throws
	 * @Author: Mr.Wange
	 * @Date: 2018/7/31 16:07
	 */ 
	public static void remove(String key){
		 RedisClient.getInstance().del(getKey(key));
	}
	
	/** 
	 * 存入缓存信息
	 * @method: put
	 * @param key
	 * @param map
	 * @param expireSeconds 
	 * @return: void 
	 * @throws
	 * @Author: Mr.Wange
	 * @Date: 2018/7/31 16:06
	 */ 
	public static void put(String key, Map map, long expireSeconds){
		 RedisClient.getInstance().setSerialize(getKey(key), map, expireSeconds);
	}
	
	/** 
	 * 获取缓存内容 
	 * @method: get
	 * @param key 
	 * @return: java.util.Map 
	 * @throws
	 * @Author: Mr.Wange
	 * @Date: 2018/7/31 16:06
	 */ 
	public static Map get(String key){
		return RedisClient.getInstance().getSerialize(getKey(key));
	}
	
	/** 
	 * 获取缓存Key 
	 * @method: getKey
	 * @param key 
	 * @return: java.lang.String 
	 * @throws
	 * @Author: Mr.Wange
	 * @Date: 2018/7/31 16:06
	 */ 
	private static String getKey(String key){
		return SESSION_KEY+key;
	}
	
}
