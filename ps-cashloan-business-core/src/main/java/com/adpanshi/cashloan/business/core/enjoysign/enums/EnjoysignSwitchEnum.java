package com.adpanshi.cashloan.business.core.enjoysign.enums;

import com.adpanshi.cashloan.business.core.common.enums.ICommonEnum;

/***
 ** @category 1号签总开关...
 ** @author qing.yunhui
 ** @email: qingyunhui@smoney.cc
 ** @createTime: 2017年10月12日下午2:51:04
 **/
public enum EnjoysignSwitchEnum implements ICommonEnum{
	
	//1号签总开关(1:开启、2.关闭)
	
	OPEN(1,"打开"),
	CLOSE(2,"关闭");

	
	private Integer key;
	
	private String name;
	
	private EnjoysignSwitchEnum(Integer key,String name){
		this.key=key;
		this.name=name;
	}
	
	@Override
	public String getCode() {
		return key.toString();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Integer getKey() {
		return key;
	}

}
