package com.adpanshi.cashloan.business.core.common.enums;
/***
 ** @category 请用一句话来描述其用途...
 ** @author qing.yunhui
 ** @email: qingyunhui@smoney.cc
 ** @createTime: 2017年8月22日下午2:15:39
 **/
public class SysMenuEnum {
	
	//is_menu` '是否菜单 0否，1是',
	public enum IS_MENU implements ICommonEnum{
		NO("0","否"),
		YES("1","是");
	    private final String code;
	    private final String name;
	    
	    private IS_MENU( String code,String name) {
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
