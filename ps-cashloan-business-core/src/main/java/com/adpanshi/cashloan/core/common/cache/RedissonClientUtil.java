package com.adpanshi.cashloan.core.common.cache;

import org.redisson.Config;
import org.redisson.Redisson;
import org.redisson.RedissonClient;
import org.redisson.SingleServerConfig;
import org.redisson.core.RLock;

/**
 * @author zhubingbing
 * redis分布式工具
 */
public class RedissonClientUtil {

    private static final String KEY_PREFIX = "LOCK:";
	
	private static RedissonClient redissonClient;
	
	/**
	 * @return
	 */
	public static RedissonClient getInstance(){
		if(redissonClient == null){
			synchronized (RedissonClient.class) {
				if(redissonClient == null){
					redissonClient = create();
				}
			}
		}
		return redissonClient;
	}
	
	/**
	 * 创建redis连接池
	 * @return
	 */
	private static RedissonClient create(){
		Config config = new Config();
        SingleServerConfig singleServerConfig = config.useSingleServer();
        singleServerConfig.setAddress(RedisConfig.getContextProperty("redis.ip") + ":" + RedisConfig.getContextProperty("redis.port"));
        singleServerConfig.setDatabase(1);
        String password = (String) RedisConfig.getContextProperty("redis.password");
        if (password != null && !"".equals(password)) {
            singleServerConfig.setPassword(password);
        }
        return Redisson.create(config);
	}
	
	/**
	 * 获取锁
	 * @param key
	 * @return
	 */
	public static RLock getRedisLock(String key){
		return getInstance().getLock(KEY_PREFIX+key);
	}

	/**
	 * 获取锁
	 * @param object
	 * @param method
	 * @param paramKey
	 * @return
	 */
	public static RLock getRedisLock(Object object, String method, Object... paramKey){
	        StringBuilder stringBuilder = new StringBuilder(object.getClass().getName() + ":" + method);
	        for (Object _paramKey : paramKey) {
	            stringBuilder.append("_");
	            stringBuilder.append(_paramKey);
	        }
	        String key = KEY_PREFIX+stringBuilder.toString();
	        
	        return getInstance().getLock(KEY_PREFIX+key);
	 }
	
	/**
	 * 获取锁
	 * @param cla
	 * @param method
	 * @param paramKey
	 * @return
	 */
	public static RLock getRedisLock(Class cla, String method, Object... paramKey){
	        StringBuilder stringBuilder = new StringBuilder(cla.getName() + ":" + method);
	        for (Object _paramKey : paramKey) {
	            stringBuilder.append("_");
	            stringBuilder.append(_paramKey);
	        }
	        String key = KEY_PREFIX+stringBuilder.toString();
	        
	        return getInstance().getLock(KEY_PREFIX+key);
	  }
	
}
