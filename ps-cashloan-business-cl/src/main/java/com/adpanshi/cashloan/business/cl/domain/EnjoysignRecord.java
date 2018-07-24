package com.adpanshi.cashloan.business.cl.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @author qing.yunhui 
 * @Since 2011-2017
 * @create 2017-09-28 14:55:17
 * @history
 */
public class EnjoysignRecord implements Serializable{
	
	private static final long serialVersionUID = 1L;

	//columns START
	/**
	 * @Fields id:主键id
	 */
	private Long id;
	
	/**
	 * @Fields borId:借款人id
	 */
	private Long borId;
	
	/**
	 * @Fields borName:借款人名称
	 */
	private String borName;
	
	/**
	 * @Fields orderId:订单号
	 */
	private String orderId;

	/**
	 * @Field email
	 */
	private String email;
	
	/**
	 * @Fields resParameter:响应参数
	 */
	private String resParameter;
	
	/**
	 * @Fields interfaceType:接口类型(用来描述调用了哪一个接口)
	 */
	private Integer interfaceType;
	
	/**
	 * @Fields status:状态(1:待响应、2:签章失败、3:签章成功、4:下载失败、5:下载成功)
	 */
	private Integer status;
	
	/**
	 * @Fields gmtCreateTime:创建时间
	 */
	private Date gmtCreateTime;
	
	/**
	 * @Fields gmtUpdateTime:修改时间
	 */
	private Date gmtUpdateTime;
	
	/**
	 * @Fields remarks:备注
	 */
	private String remarks;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBorId() {
		return borId;
	}

	public void setBorId(Long borId) {
		this.borId = borId;
	}

	public String getBorName() {
		return borName;
	}

	public void setBorName(String borName) {
		this.borName = borName;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getResParameter() {
		return resParameter;
	}

	public void setResParameter(String resParameter) {
		this.resParameter = resParameter;
	}

	public Integer getInterfaceType() {
		return interfaceType;
	}

	public void setInterfaceType(Integer interfaceType) {
		this.interfaceType = interfaceType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getGmtCreateTime() {
		return gmtCreateTime;
	}

	public void setGmtCreateTime(Date gmtCreateTime) {
		this.gmtCreateTime = gmtCreateTime;
	}

	public Date getGmtUpdateTime() {
		return gmtUpdateTime;
	}

	public void setGmtUpdateTime(Date gmtUpdateTime) {
		this.gmtUpdateTime = gmtUpdateTime;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}