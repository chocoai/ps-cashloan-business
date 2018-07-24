package com.adpanshi.cashloan.business.core.enjoysign.enums;

import com.adpanshi.cashloan.business.core.common.enums.ICommonEnum;

/***
 ** @category 自定义:1号签订单请求状态(EnjoysignRecord.status)...
 ** @author qing.yunhui
 ** @email: qingyunhui@smoney.cc
 ** @createTime: 2017年9月28日下午3:29:10
 **/
public enum StatusEnum implements ICommonEnum{
	
	
	/**待响应*/
	WAIT_RESPONSE(1,"待响应"),
	
	/**签章失败*/
	FAIL(2,"签章失败"),
	
	/**签章成功*/
	SUCCESS(3,"签章成功"),
	
	/**下载失败*/
	DOWNLOAD_FAIL(4,"下载失败"),
	
	/**下载成功*/
	DOWNLOAD_SUCCESS(5,"下载成功");
	
	private Integer key;
	
	private String name;
	
	private StatusEnum(Integer key,String name){
		this.key=key;
		this.name=name;
	}

	@Override
	public String getCode() {
		return String.valueOf(key);
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
