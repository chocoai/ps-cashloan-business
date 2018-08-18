package com.adpanshi.cashloan.cl.model;

import java.util.List;

/***
 ** @category 请用一句话来描述其用途...
 ** @author qing.yunhui
 ** @email: 280672161@qq.com
 ** @createTime: 2018年1月3日下午12:26:06
 **/
public class StaginOrderModel {

	public StaginOrderModel(){}
	
	private boolean pass;
	
	private String orderNoPrefix;
	
	private List<Integer> arrays;

	public StaginOrderModel(boolean pass, List<Integer> arrays, String orderNoPrefix){
		this.pass=pass;
		this.arrays=arrays;
		this.orderNoPrefix=orderNoPrefix;
	}
	
	public boolean isPass() {
		return pass;
	}

	public void setPass(boolean pass) {
		this.pass = pass;
	}

	public List<Integer> getArrays() {
		return arrays;
	}

	public void setArrays(List<Integer> arrays) {
		this.arrays = arrays;
	}

	public String getOrderNoPrefix() {
		return orderNoPrefix;
	}

	public void setOrderNoPrefix(String orderNoPrefix) {
		this.orderNoPrefix = orderNoPrefix;
	}
	
}
