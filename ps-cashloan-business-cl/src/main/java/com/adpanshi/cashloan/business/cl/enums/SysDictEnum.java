package com.adpanshi.cashloan.business.cl.enums;

import com.adpanshi.cashloan.business.core.common.enums.ICommonEnum;

/***
 ** @category 字典枚举...
 ** @author qing.yunhui
 ** @email: qingyunhui@fentuan360.com
 ** @createTime: 2018年2月7日上午11:37:36
 **/
public class SysDictEnum {

	//类型编码
	public enum TYPE_CODE implements ICommonEnum{
		
		//借款用途
		BORROW_TYPE("BORROW_TYPE","借款用途");
		
		private String code;
		private String name;
		
		private TYPE_CODE(String code, String name){
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
	
}
