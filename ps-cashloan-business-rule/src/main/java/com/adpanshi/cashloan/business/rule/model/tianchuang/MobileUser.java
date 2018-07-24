package com.adpanshi.cashloan.business.rule.model.tianchuang;

import java.util.Date;

public class MobileUser {
	
	private Long id;
	
	private String orderId;
	/**
	 * 手机号
	 */
	private String userName;
	/**
	 * 用户姓名
	 */
	private String personName;
	/**
	 * 性别
	 */
	private String gender;
	/**
	 * 注册号，一般是⾝份证号
	 */
	private String identifyNum;
	/**
	 * 注册地址
	 */
	private String identifyAddr;
	/**
	 * 帐单地址（用户自己预留地址⽤于运营商账单邮寄）
	 */
	private String billAddr;
	/**
	 * 证件类型
	 */
	private String identifyType;
	/**
	 * 当前星级，联通⽂字描述，移动数字描述（联通：三星，移动：3）
	 */
	private String starLevel;
	/**
	 * 归属城市
	 */
	private String userCity;
	/**
	 * 备注信息
	 */
	private String other;
	/**
	 * 用户输入的姓名
	 */
	private String inputName;
	/**
	 * 用户输入的身证号码
	 */
	private String inputNum;
	/**
	 * 用户输入的身证号 归属地
	 */
	private String inputNumAdd;

	/**
	 * 数据获取时间
	 */
	private Date createTime;

	/**
	 * 是否实名（-1:未知，0:未实名；1:已实名；）
	 */
	private String ifRealName;

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
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getIdentifyNum() {
		return identifyNum;
	}
	public void setIdentifyNum(String identifyNum) {
		this.identifyNum = identifyNum;
	}
	public String getIdentifyAddr() {
		return identifyAddr;
	}
	public void setIdentifyAddr(String identifyAddr) {
		this.identifyAddr = identifyAddr;
	}
	public String getBillAddr() {
		return billAddr;
	}
	public void setBillAddr(String billAddr) {
		this.billAddr = billAddr;
	}
	public String getIdentifyType() {
		return identifyType;
	}
	public void setIdentifyType(String identifyType) {
		this.identifyType = identifyType;
	}
	public String getStarLevel() {
		return starLevel;
	}
	public void setStarLevel(String starLevel) {
		this.starLevel = starLevel;
	}
	public String getUserCity() {
		return userCity;
	}
	public void setUserCity(String userCity) {
		this.userCity = userCity;
	}
	public String getOther() {
		return other;
	}
	public void setOther(String other) {
		this.other = other;
	}
	public String getInputName() {
		return inputName;
	}
	public void setInputName(String inputName) {
		this.inputName = inputName;
	}
	public String getInputNum() {
		return inputNum;
	}
	public void setInputNum(String inputNum) {
		this.inputNum = inputNum;
	}
	public String getInputNumAdd() {
		return inputNumAdd;
	}
	public void setInputNumAdd(String inputNumAdd) {
		this.inputNumAdd = inputNumAdd;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getIfRealName() {
		return ifRealName;
	}

	public void setIfRealName(String ifRealName) {
		this.ifRealName = ifRealName;
	}
}
