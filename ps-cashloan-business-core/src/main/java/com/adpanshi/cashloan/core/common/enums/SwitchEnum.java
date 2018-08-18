package com.adpanshi.cashloan.core.common.enums;
/***
 ** @category 开关...
 ** @author qing.yunhui
 ** @email: qingyunhui@smoney.cc
 ** @createTime: 2017年10月21日上午11:59:30
 **/
public enum SwitchEnum implements ICommonEnum{
	
	ON("on","开"),
	OFF("off","关");
	
    private final String code;
    
    private final String name;
    
    private SwitchEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Integer getKey() {
		return null;
	}

}
