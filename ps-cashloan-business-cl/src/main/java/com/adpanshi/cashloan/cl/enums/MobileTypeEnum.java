package com.adpanshi.cashloan.cl.enums;

/**
 * 客户端系统类型
 * @author 8452
 */
public enum MobileTypeEnum {

	IOS(1,"IOS系统"),
	ANDROID(2,"安卓系统"),
	WINPHONE(3,"winPhone系统");
	
	/**
	 * cookie key
	 */
	private Integer code;
	
	/**
	 * 描述信息
	 */
	private String name;
	
	private MobileTypeEnum(Integer code, String name){
		this.code = code;
		this.name = name;
	}

	public Integer getCode() {
		return code;
	}

	public String getName() {
		return name;
	}
	
}
