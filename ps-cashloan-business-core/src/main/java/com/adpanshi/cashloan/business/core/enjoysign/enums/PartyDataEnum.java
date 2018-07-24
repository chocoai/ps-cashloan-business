package com.adpanshi.cashloan.business.core.enjoysign.enums;

import com.adpanshi.cashloan.business.core.common.enums.ICommonEnum;

/***
 ** @category 请用一句话来描述其用途...
 ** @author qing.yunhui
 ** @email: qingyunhui@smoney.cc
 ** @createTime: 2017年9月30日下午5:25:08
 **/
public enum PartyDataEnum implements ICommonEnum{

	/**首次请求(新的订单号三方都需要进行签章)*/
	FIRST_AGAIN(1,"首次请求"),
	
	/**签章失败(部分签章失败)*/
	FAIL(2,"签章失败");
	
	private Integer key;
	
	private String name;
	
	private PartyDataEnum(Integer key,String name){
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
