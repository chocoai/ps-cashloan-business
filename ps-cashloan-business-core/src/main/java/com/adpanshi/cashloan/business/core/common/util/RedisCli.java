package com.adpanshi.cashloan.business.core.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ScanParams;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class RedisCli {


	private static JedisPool jedisPool = null;
	private static FastJsonUtil jsonUtil;
	private static Logger log = LoggerFactory.getLogger(RedisCli.class);
	
	@Resource(name="jedisPool")
	void setJedisPool(JedisPool jedisPool){
		RedisCli.jedisPool = jedisPool;
	}
	@Resource(name="fastJsonUtil")
	void setJsonUtil(FastJsonUtil jsonUtil){
		RedisCli.jsonUtil = jsonUtil;
	}

	private static Jedis getJedis() {
		if (jedisPool != null) {
			return jedisPool.getResource();
		} else
			log.error("未找到连接池!");
		return null;
	}

	public static void set(String key, Object value) {
		//log.info("--------执行:RedisCli:set:key:"+key);
		Jedis jedis = getJedis();
		try {
			jedis.set(key, jsonUtil.toString(value));
		}catch(Exception e){
			log.error("-----------RedisCli:set Error!----------");
		} finally {
			jedisPool.returnResource(jedis);
		}
	}
	
	public static void expire(String key, int seconds){
		Jedis jedis = getJedis();
		try {
			jedis.expire(key, seconds);
		}catch(Exception e){
			log.error("-----------RedisCli:expire Error!----------");
		} finally {
			jedisPool.returnResource(jedis);
		}
	}
	
	/**
	 * @20161025 set + expire 
	 * @param key
	 * @param value
	 * @param seconds
	 */
	public static void setExpire(String key, Object value, int seconds){
		Jedis jedis = getJedis();
		try {
			jedis.setex(key, seconds, jsonUtil.toString(value));
		}catch(Exception e){
			log.error("-----------RedisCli:expire Error!----------");
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	public static void append(String key, Object value) {
		//log.info("--------执行:RedisCli:append:key:"+key);
		Jedis jedis = getJedis();
		try {
			jedis.append(key, jsonUtil.toString(value));
		}catch(Exception e){
			log.error("-----------RedisCli:append Error!----------");
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	public static void delete(String key) {
		//log.info("--------执行:RedisCli:delete:key:"+key);
		Jedis jedis = getJedis();
		try {
			jedis.del(key);
		}catch(Exception e){
			log.error("-----------RedisCli:delete Error!----------");
		} finally {
			jedisPool.returnResource(jedis);
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void deleteByPrefix(String prefix) {
		//log.info("--------执行:RedisCli:deleteByPrefix:prefix:"+prefix);
		Jedis jedis = getJedis();
		ScanParams params = new ScanParams();
		params.match(prefix);
		try {
			jedis.scan(0, params);
		}catch(Exception e){
			log.error("-----------RedisCli:deleteByPrefix Error!----------");
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	public static <T> T get(String key,Class<T> clz) {
		//log.info("--------执行:RedisCli:get:key:"+key);
		Jedis jedis = getJedis();
		String value = null;
		try {
			value = jedis.get(key);
		}catch(Exception e){
			log.error("-----------RedisCli:get Error!----------");
		} finally {
			jedisPool.returnResource(jedis);
		}
		if(value==null) return null;
		//log.info("--------执行:RedisCli:get:value:"+value);
		return jsonUtil.toObject(value, clz);
	}

	/*
	 * Hash操作
	 */
	
	public static void setHash(String key, String field, Object value){
		//log.info("--------执行:RedisCli:setHash:key:"+key+":field:"+field);
		Jedis jedis = getJedis();
		try {
			jedis.hset(key, field, (value instanceof String)?(String)value:jsonUtil.toString(value));
		}catch(Exception e){
			log.error("-----------RedisCli:setHash Error!----------");
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	public static void putAll(String key, Map<String,String> m) {
		if (m.isEmpty()) {
			return;
		}
		Jedis jedis = getJedis();
		try {
			jedis.hmset(key,m);
		}catch(Exception e){
			log.error("-----------RedisCli:setHash Error!----------");
		} finally {
			jedisPool.returnResource(jedis);
		}
	}
	
	public static void delHash(String key,String field){
		//log.info("--------执行:RedisCli:delHash:key:"+key);
		Jedis jedis = getJedis();
		try {
			jedis.hdel(key, field);
		}catch(Exception e){
			log.error("-----------RedisCli:delHash Error!----------");
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	public static String getHash(String key, String field) {
		//log.info("--------执行:RedisCli:getHsh:key:"+key+":field:"+field);
		Jedis jedis = getJedis();
		String value = null;
		try {
			value = jedis.hget(key, field);
		}catch(Exception e){
			log.error("-----------RedisCli:getHash Error!----------");
		} finally {
			jedisPool.returnResource(jedis);
		}
		return value;
	}
	
	public static <T> T getHash(String key, String field, Class<T> clz) {
		//log.info("--------执行:RedisCli:getHsh:key:"+key+":field:"+field);
		Jedis jedis = getJedis();
		String value = null;
		try {
			value = jedis.hget(key, field);
		}catch(Exception e){
			log.error("-----------RedisCli:getHash Error!----------");
		} finally {
			jedisPool.returnResource(jedis);
		}
		return jsonUtil.toObject(value, clz);
	}

	public static Map<String, String> getHash(String key) {
		//log.info("--------执行:RedisCli:getAllHash:key:"+key);
		Jedis jedis = getJedis();
		Map<String, String> value = null;
		try {
			value = jedis.hgetAll(key);
		}catch(Exception e){
			log.error("-----------RedisCli:getAllHash Error!----------");
		} finally {
			jedisPool.returnResource(jedis);
		}
		return value;
	}

	public static void flushDB() {
		Jedis jedis = getJedis();
		try {
			jedis.flushDB();
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	public static int size() {
		Jedis jedis = getJedis();
		int size = 0;
		try {
			size = jedis.dbSize().intValue();
		} finally {
			jedisPool.returnResource(jedis);
		}
		return size;
	}

	public static Set<String> keys() {
		Jedis jedis = getJedis();
		Set<String> keys = null;
		try {
			keys = jedis.keys("*");
		}catch(Exception e){
			log.error("-----------RedisCli:keys Error!----------");
		} finally {
			jedisPool.returnResource(jedis);
		}
		return keys;
	}

	/**
	 * @auther huangqin
	 * @description 获取对应的所有redis的key
	 * @param key
	 * @return Set<String>
	 * @data 2017-12-19
	 */
	public static Set<String> keys(String key) {
		Jedis jedis = getJedis();
		Set<String> keys = null;
		try {
			keys = jedis.keys(key);
		}catch(Exception e){
			log.error("-----------RedisCli:keys Error!----------");
		} finally {
			jedisPool.returnResource(jedis);
		}
		return keys;
	}
	
	public static Long incrBy(String key,long step){
		Jedis jedis = getJedis();
		try {
			return jedis.incrBy(key.getBytes(),step);
		}catch(Exception e){
			log.error("-----------RedisCli:keys Error!----------");
		} finally {
			jedisPool.returnResource(jedis);
		}
		return null;
	}
	
	public static Long decrBy(String key,long step){
		Jedis jedis = getJedis();
		try {
			return jedis.decrBy(key.getBytes(),step);
		}catch(Exception e){
			log.error("-----------RedisCli:keys Error!----------");
		} finally {
			jedisPool.returnResource(jedis);
		}
		return null;
	}
	
	public static boolean isExist(String key){
		Jedis jedis = getJedis();
		boolean value = false;
		try {
			value = jedis.exists(key);
		}catch(Exception e){
			log.error("-----------RedisCli:isExist Error!----------");
		} finally {
			jedisPool.returnResource(jedis);
		}
		return value;
	}

	public static <T> List<T> getList(String key, Class<T> clz) {
		//log.info("--------执行:RedisCli:get:key:"+key);
		Jedis jedis = getJedis();
		String value = null;
		try {
			value = jedis.get(key);
		}catch(Exception e){
			log.error("-----------RedisCli:get Error!----------");
		} finally {
			jedisPool.returnResource(jedis);
		}
		if(value==null) return null;
		//log.info("--------执行:RedisCli:get:value:"+value);
		return jsonUtil.toObjectArray(value, clz);
	}
}
