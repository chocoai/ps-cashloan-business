package com.adpanshi.cashloan.core.common.enums;

/**
 * Redis缓存前缀
 * @author mujuezhike
 * @date 20160929
 */
public enum RedisCacheExpireEnum {

	/** 1天 */
	AuthTokenExpireSeconds( 60*60*1 , "健默认持续时间" ),
	/** 1小时 */
	DefaultExpireSeconds( 60*60*24 , "健默认持续时间" ),

	;
	
	RedisCacheExpireEnum(Integer seconds, String describe){

		this.seconds  = seconds ;
		this.describe = describe;
	}
    
	private Integer seconds;
	private String describe;
	
	public Integer seconds() {
		return seconds;
	}
	
	public String describe() {
		return describe;
	}
}
