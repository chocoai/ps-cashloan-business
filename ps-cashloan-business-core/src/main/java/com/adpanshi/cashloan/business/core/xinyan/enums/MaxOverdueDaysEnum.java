package com.adpanshi.cashloan.business.core.xinyan.enums;

import com.adpanshi.cashloan.business.core.common.enums.ICommonEnum;

/***
 ** @category 最长逾期时间区间(后期会应用到规则中)...
 ** @author qing.yunhui
 ** @email: qingyunhui@smoney.cc
 ** @createTime: 2017年10月21日下午7:43:54
 **/
public enum MaxOverdueDaysEnum implements ICommonEnum{
	
	/**逾期 0天*/
	DAYS_0("0","逾期天数0天"),
	
	/**逾期 1至15天*/
	DAYS_1_15("1-15","逾期天数1-15天"),
	
	/**逾期16至30天*/
	DAYS_16_30("16-30","逾期天数16-30天"),
	
	/**逾期31至60天*/
	DAYS_31_60("31-60","逾期天数31-60天"),
	
	/**逾期61至90天*/
	DAYS_61_90("61-90","逾期天数61-90天"),
	
	/**逾期91至120天*/
	DAYS_91_120("91-120","逾期天数91-120天"),
	
	/**逾期121至150天*/
	DAYS_121_150("121-150","逾期天数121-150天"),
	
	/**逾期151至180天*/
	DAYS_151_180("151-180","逾期天数151-180天"),
	
	/**逾期>180天*/
	DAYS_180_PLUS(">180","逾期天数大于180天");
	
	private String code;
	
	private String name;
	
	private MaxOverdueDaysEnum(String code, String name){
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
