package com.adpanshi.cashloan.business.core.xinyan.enums;

import com.adpanshi.cashloan.business.core.common.enums.ICommonEnum;

/***
 ** @category 新颜查询结果编码(后期会应用到规则中)...
 ** @author qing.yunhui
 ** @email: qingyunhui@smoney.cc
 ** @createTime: 2017年10月21日下午3:15:06
 **/
public enum XinyanRetunCodeEnum implements ICommonEnum{

	/**建议拉黑*/
	SUGGEST_PULL_BLACK("0","建议拉黑"),
	
	/**无法确认*/
	UNABLE_TO_CONFIRM("1","无法确认"),
	
	/**空值未知*/
	NULL_VALUE_IS_UNKNOWN("2","空值未知"),
	
	/**其他异常*/
	OTHER_EXCEPTION("9","其他异常");
	
	private String code;
	
	private String name;
	
	private XinyanRetunCodeEnum(String code, String name){
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
