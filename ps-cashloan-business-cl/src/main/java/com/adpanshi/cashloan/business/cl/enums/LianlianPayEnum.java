package com.adpanshi.cashloan.business.cl.enums;

import com.adpanshi.cashloan.business.core.common.enums.ICommonEnum;

/***
 ** @category 连连查询订单枚举（业务逻辑处理时用到）
 ** @author qing.yunhui
 ** @email: qingyunhui@smoney.cc
 ** @createTime: 2017年9月14日下午4:02:31
 **/
public enum LianlianPayEnum implements ICommonEnum{

	REQUEST_AGAIN("REQUEST_AGAIN","再次发起请求"),
	INTERVAL_MINUTE("30","间隔时间30分钟");
	
	
	private String code;
	
	private String name;
	
	private LianlianPayEnum(String code, String name){
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	@Override
	public Integer getKey() {
		return null;
	}
	
}
