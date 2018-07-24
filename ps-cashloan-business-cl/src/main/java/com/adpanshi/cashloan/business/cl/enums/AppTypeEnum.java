package com.adpanshi.cashloan.business.cl.enums;
/***
 ** @category 应用类型...
 ** @author qing.yunhui
 ** @email: qingyunhui@smoney.cc
 ** @createTime: 2017年9月13日上午9:35:03
 **/
public enum AppTypeEnum {
	
	SHAN_YIN(1,"闪银app"),
	YIN_CHEN(2,"银程金服app");
	
	/**
	 * cookie key
	 */
	private Integer code;
	
	/**
	 * 描述信息
	 */
	private String name;
	
	private AppTypeEnum(Integer code, String name){
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
