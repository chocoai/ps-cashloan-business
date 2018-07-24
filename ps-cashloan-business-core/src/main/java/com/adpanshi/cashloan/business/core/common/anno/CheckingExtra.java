package com.adpanshi.cashloan.business.core.common.anno;
/***
 ** @category 校验对象(待校验对象与参数所在的下标)与校验规则器...
 ** @author qing.yunhui
 ** @email: qingyunhui@smoney.cc
 ** @createTime: 2017年11月17日上午10:10:00
 **/
public class CheckingExtra {
	
	/**校验器*/
	private Checking checking;
	
	/**待校验的值*/
	private ValueExtra valueExtra;

	public CheckingExtra(){}
	
	public CheckingExtra(Checking checking){
		this.checking=checking;
	}
	
	public Checking getChecking() {
		return checking;
	}

	public void setChecking(Checking checking) {
		this.checking = checking;
	}

	public ValueExtra getValueExtra() {
		return valueExtra;
	}

	public void setValueExtra(ValueExtra valueExtra) {
		this.valueExtra = valueExtra;
	}
	
}
