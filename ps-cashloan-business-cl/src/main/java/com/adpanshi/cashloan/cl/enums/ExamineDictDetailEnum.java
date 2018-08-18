package com.adpanshi.cashloan.cl.enums;

import com.adpanshi.cashloan.core.common.enums.ICommonEnum;

/***
 ** @category 请用一句话来描述其用途...
 ** @author qing.yunhui
 ** @email: 280672161@qq.com
 ** @createTime: 2017年12月6日下午4:52:55
 **/
public class ExamineDictDetailEnum {

	/**参数类型[25.订单挂起 26.订单通过 27.订单拒绝 28.租房收入证明不合法]*/
	public enum CODE_TYPE implements ICommonEnum{
		
		HANGUP(25,"订单挂起"),
		PASS(26,"订单通过"),
		REFUSED(27,"订单拒绝"),
		TENEMENT_INCOME_WRONGFUL(28,"租房收入证明不合法");
		private Integer key;
		private String name;
		
		private CODE_TYPE(Integer key, String name){
			this.key = key;
			this.name = name;
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
	
	/**参数编码[88888888的租房收入证明code]*/
	public enum CODE implements ICommonEnum{
		
		TENEMENT_INCOME_CODE(88888888,"租房收入证明编码");
		private Integer key;
		private String name;
		
		private CODE(Integer key, String name){
			this.key = key;
			this.name = name;
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
	
}
