package com.adpanshi.cashloan.business.core.common.anno;
/***
 ** @category 校验对象扩展(校验值、校验参数所在的下标)
 ** @author qing.yunhui
 ** @email: qingyunhui@smoney.cc
 ** @createTime: 2017年11月17日下午4:21:55
 **/
public class ValueExtra {

	/**待校验的value*/
	private Object value;
	
	/**待校验的value所在的下标位置*/
	private int point;
	
	/**标识值是否需要重置：true:是、false:否*/
	private boolean reset;

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}
	
	public ValueExtra(Object value,int point){
		this.value=value;
		this.point=point;
	}

	public boolean isReset() {
		return reset;
	}

	public void setReset(boolean reset) {
		this.reset = reset;
	}

}
