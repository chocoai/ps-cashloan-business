package com.adpanshi.cashloan.business.cl.enums;

import com.adpanshi.cashloan.business.core.common.enums.ICommonEnum;

/***
 ** @category 分期字段枚举...
 ** @author qing.yunhui
 ** @email: qingyunhui@fentuan360.com
 ** @createTime: 2018年2月7日下午3:12:44
 **/
public class StagesFieldEnum {
	
	
	/**因为合同模板控件中分期属性是按规律生成的，需要反射填充*/
	public enum STAGES_NUMBER implements ICommonEnum{
		ONE(1,"one","第一期"),
		TWO(2,"two","第二期"),
		THREE(3,"three","第三期"),
		FOUR(4,"four","第四期"),
		FIVE(5,"five","第五期"),
		SIX(6,"six","第六期"),
		SEVEN(7,"seven","第七期"),
		EIGHT(8,"eight","第八期"),
		NINE(9,"nine","第九期"),
		TEN(10,"ten","第十期"),
		ELEVEN(11,"eleven","第十一期"),
		TWELVE(12,"twelve","第十二期");
		
		private int key;
		private String code;
		private String name;
		
		private STAGES_NUMBER(int key,String code, String name){
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
			return key;
		}
	}
	
}
