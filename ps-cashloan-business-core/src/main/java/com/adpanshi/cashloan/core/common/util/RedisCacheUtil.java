package com.adpanshi.cashloan.core.common.util;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 缓存工具类
 * @author mujuezhike
 */
public interface RedisCacheUtil {
	
	/**
	 * 取得值 --键值形式
	 * @param key 键
	 * @param clz 要转成的类型
	 * @return T
	 */
	<T> T get(String key, Class<T> clz);

	/**
	 * 取得值 --键值形式
	 * @param key 键
	 * @param clz 要转成的类型
	 * @return T
	 */
	<T> List<T> getList(String key, Class<T> clz);

	/**
	 * 取得值 --Hash形式
	 * @param key 键
	 * @param field 属性
	 * @param clz 要转成的类型
	 * @return T
	 */
	<T> T getHash(String key, String field, Class<T> clz);

	Map<String, String> getHash(String key);


	/**
	 * 设定值 --键值方式
	 * @param key
	 * @param value
	 */
	void set(String key, Object value);

	/**
	 * @20161025
	 * 设定值 --键值方式    持续时间
	 * @param key
	 * @param value
	 */
	void setExpire(String key, Object value, Integer seconds);

	/**
	 * @20161025
	 * 设定值 --设置健的   持续时间
	 * @param key
	 * @param seconds
	 */
	void expire(String key, Integer seconds);

	void setHash(String key, String field, Object value);

	/**
	 * 删除键
	 * @param key
	 */
	void del(String key);

	/**
	 * 按后缀删除
	 * @param prefix
	 */
	public void delByPrefix(String prefix);

	void delHash(String key, String field);

	/**
	 * 原子性增加
	 * @param key
	 * @param l
	 */
	Long increment(String key, long l);

	/**
	 * 原子性减少
	 * @param key
	 * @param l
	 */
	Long decrement(String key, long l);
	
	/**
	 * 设置失效时间
	 * @param seconds 秒
	 */
	void expire(String key, int seconds);
	
	/**
	 * 是否存在key
	 * @param key
	 * @return
	 */
	boolean isExist(String key);

	/**
	 * @auther huangqin
	 * @description 获取对应的所有redis的key
	 * @param key
	 * @return Set<String>
	 * @data 2017-12-19
	 */
	Set<String> keys(String key);

}
