package com.adpanshi.cashloan.core.common.context;

import com.adpanshi.cashloan.core.common.util.RedisCli;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;


/**
 * 启动加载缓存类
 * 
 * @version 1.0.0
 * @date 2016年11月11日 下午3:37:13
 * Copyright 粉团网路  All Rights Reserved
 *
 */
public class Global {
	
	private final static String SYS_CONFIG_KEY = "sysConfig";

	public static int getInt(String key){
		int i = 0;
		if(StringUtils.isNotEmpty(get(key))){
			i = Integer.valueOf(get(key));
		}
		return i;
	}

	public static double getDouble(String key){
		double i = 0;
		if(StringUtils.isNotEmpty(get(key))){
			i = Double.valueOf(get(key));
		}
		return i;
	}

	public static String getValue(String key) {
		return get(key);
	}

	public static Object getObject(String key){
		return get(key);
	}
	
	public static void del(){
		RedisCli.delete(SYS_CONFIG_KEY);
	}
	
	public static String get(String key){
		return RedisCli.getHash(SYS_CONFIG_KEY,key);
	}
	
	public static void set(Map<String,String> map){
		RedisCli.putAll(SYS_CONFIG_KEY, map);
	}
}
