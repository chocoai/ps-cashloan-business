package com.adpanshi.cashloan.business.core.common.enums;
/***
 ** @category 应用环境枚举...
 ** @author qing.yunhui
 ** @email: qingyunhui@fentuan360.com
 ** @createTime: 2018年3月16日下午18:33:10
 **/
public enum AppEnvironmentEnum implements ICommonEnum{
	
	DEV("dev","开发环境"),
	PROD("prod","生产环境");
	
    private final String code;
    
    private final String name;
    
    private AppEnvironmentEnum( String code,String name) {
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
