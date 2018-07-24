package com.adpanshi.cashloan.business.core.common.enums;
/***
 ** @category 权限对应的枚举类...
 ** @author qing.yunhui
 ** @email: qingyunhui@smoney.cc
 ** @createTime: 2017年8月18日下午6:20:59
 **/
public class SysPermEnum {
	
	//'权限级别 1-系统级别 2-普通级别'
	public enum PERM_LEVEL implements ICommonEnum{

		/**系统级别*/
		LEVEL_SYSTEM("1","系统级别"),
		/**普通级别*/
		LEVEL_NORMAL("2","普通级别");
		
	    private final String code;
	    private final String name;
	    
	    private PERM_LEVEL( String code,String name) {
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
			return Integer.parseInt(code);
		}
	}

}
