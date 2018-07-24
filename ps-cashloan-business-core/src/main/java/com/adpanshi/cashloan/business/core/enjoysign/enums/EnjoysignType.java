package com.adpanshi.cashloan.business.core.enjoysign.enums;

/***
 ** @category 1号签-控件类型...
 ** @author qing.yunhui
 ** @email: qingyunhui@smoney.cc
 ** @createTime: 2017年9月28日下午8:54:02
 **/
public enum EnjoysignType{

	/**文本*/
	TEXT("text"),
	
	/**签名*/
	SIGNET("signet"),
	
	/***印章*/
	IMG("img");
	
	
	private String code;
	
	private EnjoysignType(String code){
		this.code=code;
	}
	
	public String getCode() {
		return code;
	}

}
