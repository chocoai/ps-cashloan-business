package com.adpanshi.cashloan.business.rule.model.tianchuang;

import java.util.Date;

/**
 * saas风控订单记录
 * @author ppchen
 * 2017年8月21日 下午7:49:00
 * 
 */
public class OrderLog {
	
	private Long id;
	
	private String orderId;
	
	/**
	 * 手机号
	 */
	private String mobile;
	/**
	 * 服务密码
	 */
	private String password;
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 身份证号
	 */
	private String code;
	/**
	 * 订单创建时间
	 */
	private Date createTime;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
