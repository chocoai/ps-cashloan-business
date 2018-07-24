package com.adpanshi.cashloan.business.core.enjoysign.enums;

import com.adpanshi.cashloan.business.core.common.enums.ICommonEnum;

/***
 ** @category 1号签接口类型...
 ** @author qing.yunhui
 ** @email: qingyunhui@smoney.cc
 ** @createTime: 2017年9月28日下午3:20:12
 **/
public enum InterfaceTypeEnum implements ICommonEnum{
	
	
	/**模板签署人签署控件列表接口**/
	EXPORTWIDGETS(1,"模板签署人签署控件列表"),
	
	/**发起签署后台自动签章*/
	STARTSIGN(2,"发起签署后台自动签章"),
	
	/**下载合同接口*/
	DOWNLOADPDF(3,"下载合同"),
	
	/**在线查看合同接口*/
	VIEW(4,"在线查看合同");
	
	private Integer key;
	
	private String name;
	
	private InterfaceTypeEnum(Integer key,String name){
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
