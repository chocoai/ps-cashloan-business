package com.adpanshi.cashloan.business.core.model;
/***
 ** @category 认证项...
 ** @author qing.yunhui
 ** @email: 280672161@qq.com
 ** @createTime: 2017年12月15日上午11:53:00
 **/
public class UserAuthExtra {
	
	/**
	 * @1.认证项支持顺序校验
	   @2.认证项支持增加,减少校验
	*/
	
	/**
	 * 认证code{注意该code必需与数据库表中的字段保持一致性}
	 * */
	private String code;
	
	/**认证状态*/
	private String status;
	
	/**
	 * 名称(比如:身份证认证)
	 * */
	private String name;
	
	/**
	 * 优先级(以1为基础开始递增)-值越小优先级越高
	 * */
	private int order;
	
	public UserAuthExtra(String code,String name,int order){
		this.code=code;
		this.name=name;
		this.order=order;
	}
	
	public UserAuthExtra(){}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

}
