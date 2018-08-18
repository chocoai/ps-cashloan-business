package com.adpanshi.cashloan.cl.enums;

/**
 * 设备状态
 * @author 8452
 */
public enum UserDeviceTokensStateEnum {

	/**
	 *
	 */
	DISABLE(0,"禁用"),
	ENABLE(1,"启用");
	
	/**
	 * cookie key
	 */
	private Integer code;
	
	/**
	 * 描述信息
	 */
	private String name;
	
	private UserDeviceTokensStateEnum(Integer code, String name){
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
