package com.adpanshi.cashloan.business.cl.domain;

import java.io.Serializable;
import java.util.Date;

public class ClExamineDictDetail implements Serializable{
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 主键Id
	 */
	private Long id;
	/**
	 * 参数编码
	 */
	private String code;
	/**
	 * 参数值
	 */
	private String value;
	/**
	 * 参数类型
	 */
	private String codeType;
	/**
	 * 状态(10:启用，20：禁用)
	 */
	private String state;
	/**
	 * 创建者
	 */
	private String creator;
	/**
	 * 修改者
	 */
	private String mender;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 修改时间
	 */
	private Date menderTime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getCodeType() {
		return codeType;
	}
	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getMender() {
		return mender;
	}
	public void setMender(String mender) {
		this.mender = mender;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getMenderTime() {
		return menderTime;
	}
	public void setMenderTime(Date menderTime) {
		this.menderTime = menderTime;
	}
}
