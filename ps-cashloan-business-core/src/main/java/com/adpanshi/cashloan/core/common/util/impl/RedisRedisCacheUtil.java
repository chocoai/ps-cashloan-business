package com.adpanshi.cashloan.core.common.util.impl;

import com.adpanshi.cashloan.core.common.util.RedisCacheUtil;
import com.adpanshi.cashloan.core.common.util.RedisCli;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author 8452
 */
@Component("redisCacheUtil")
public class RedisRedisCacheUtil implements RedisCacheUtil {
	
	private final String _key = "AOI_";

	@Override
	public <T> T get(String key,Class<T> clz) {
		return RedisCli.get(_key+key,clz);
	}

	@Override
	public <T> List<T> getList(String key, Class<T> clz) {
		return RedisCli.getList(_key+key,clz);
	}
	
	@Override
	public void set(String key, Object value) {
		RedisCli.set(_key+key, value);
	}

	@Override
	public Long increment(String key, long l) {
		return RedisCli.incrBy(_key+key,l);
	}

	@Override
	public Long decrement(String key, long l) {
		return RedisCli.decrBy(_key+key,l);
	}

	@Override
	public void del(String key) {
		RedisCli.delete(_key+key);
	}
	
	@Override
	public void delByPrefix(String prefix){
		RedisCli.deleteByPrefix(_key+prefix);
	}
	
	@Override
	public void expire(String key, int seconds) {
		RedisCli.expire(_key+key,seconds);
	}

	@Override
	public boolean isExist(String key) {
		return RedisCli.isExist(_key+key);
	}

	@Override
	public <T> T getHash(String key, String field, Class<T> clz) {
		return RedisCli.getHash(_key+key, field, clz);
	}

	@Override
	public void setHash(String key, String field, Object value) {
		RedisCli.setHash(_key+key, field, value);
	}

	@Override
	public void delHash(String key, String field) {
		RedisCli.delHash(_key+key, field);
	}

	@Override
	public Map<String, String> getHash(String key) {
		return RedisCli.getHash(_key+key);
	}

	@Override
	public void setExpire(String key, Object value, Integer seconds) {
		
		RedisCli.setExpire(_key+key, value, seconds);		
	}

	@Override
	public void expire(String key, Integer seconds) {
		
		RedisCli.expire(_key+key, seconds);
		
	}

	@Override
	public Set<String> keys(String key) {
		return RedisCli.keys(_key+key);
	}

}
