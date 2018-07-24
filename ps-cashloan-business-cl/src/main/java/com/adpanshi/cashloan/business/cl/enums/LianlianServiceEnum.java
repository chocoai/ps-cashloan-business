package com.adpanshi.cashloan.business.cl.enums;

import com.adpanshi.cashloan.business.core.common.enums.ICommonEnum;

/***
 ** @category 连连服务接口类型...
 ** @author qing.yunhui
 ** @email: qingyunhui@fentuan360.com
 ** @createTime: 2018年4月12日下午8:41:52
 **/
public class LianlianServiceEnum {

	public enum SERVICE implements ICommonEnum{
		/** 绑卡*/
		BANK_CARD_BIND(1,"BANK_CARD_BIND"),
		/** 放款*/
		PAY(2,"PAY"),
		/**还款 */
		REPAY(3,"REPAY");
		
		private Integer key;
		
		private String name;
		
		private SERVICE(Integer key, String name){
			this.key = key;
			this.name = name;
		}
		
		public String getName() {
			return name;
		}

		@Override
		public String getCode() {
			return String.valueOf(key);
		}

		@Override
		public Integer getKey() {
			return key;
		}
		
	}
	
	
}
