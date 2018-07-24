package com.adpanshi.cashloan.business.core.enjoysign.enums;

import com.adpanshi.cashloan.business.core.common.enums.ICommonEnum;

/***
 ** @category CA证书类型...
 ** @author qing.yunhui
 ** @email: qingyunhui@smoney.cc
 ** @createTime: 2017年9月29日下午5:05:07
 **/
public enum CaTypeEnum implements ICommonEnum{

	/**
	 * 该签署人在签署时使用的CA证书类型
	 * 0是1号签平台证书，1是企业长期证书(有效期1年)，2是企业临时证书(有效期1个月,会自动更换)
	 * 3是个人长期证书，4是个人临时证书。 默认是0.
	 * */
	PLATFORM_CA(0,"1号签平台证书"),
	COMPANY_LONG_CA(1,"企业长期证书"),
	COMPANY_TEMPORARY_CA(2,"企业临时证书"),
	INDIVIDUAL_LONG_CA(3,"个人长期证书"),
	INDIVIDUAL_TEMPORARY_CA(4,"个人临时证书");
	
	private Integer key;
	
	private String name;
	
	private CaTypeEnum(Integer key,String name){
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
