package com.adpanshi.cashloan.business.rule.model.tianchuang;

/**
 * 通话记录
 * @author ppchen
 * 2017年8月18日 下午12:01:09
 * 
 */
public class CallingRecord {
	
	private Long id;

	/**
	 * 订单ID
	 */
	private String orderId; 
	/**
	 * 对方地区
	 */
	private String callingPosition;
	/**
	 * 对方手机号
	 */
	private String callingNumber;
	/**
	 * 通话时间
	 */
	private String holdingTime;
	/**
	 * 通话时长
	 */
	private String talkTime;
	/**
	 * ⾮通话呼叫类型，如：VPN呼叫
	 */
	private String romatType;
	/**
	 * 电话标记，例如疑似诈骗电话、推销电话、客服电话
	 */
	private String mobileSign;
	/**
	 * 手机号
	 */
	private String userName;
	/**
	 * 优惠套餐
	 */
	private String packageDis;
	/**
	 * 通话类型，如：国内漫游
	 */
	private String holdingType;
	/**
	 * 通话费⽤，⼩数点后两位精确到分，如 10.00
	 */
	private String callingFee;
	/**
	 * 通话地点
	 */
	private String holdingPosition;
	/**
	 * 呼叫类型：主叫，被叫
	 */
	private String callingType;
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
	public String getCallingPosition() {
		return callingPosition;
	}
	public void setCallingPosition(String callingPosition) {
		this.callingPosition = callingPosition;
	}
	public String getCallingNumber() {
		return callingNumber;
	}
	public void setCallingNumber(String callingNumber) {
		this.callingNumber = callingNumber;
	}
	public String getHoldingTime() {
		return holdingTime;
	}
	public void setHoldingTime(String holdingTime) {
		this.holdingTime = holdingTime;
	}
	public String getTalkTime() {
		return talkTime;
	}
	public void setTalkTime(String talkTime) {
		this.talkTime = talkTime;
	}
	public String getRomatType() {
		return romatType;
	}
	public void setRomatType(String romatType) {
		this.romatType = romatType;
	}
	public String getMobileSign() {
		return mobileSign;
	}
	public void setMobileSign(String mobileSign) {
		this.mobileSign = mobileSign;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPackageDis() {
		return packageDis;
	}
	public void setPackageDis(String packageDis) {
		this.packageDis = packageDis;
	}
	public String getHoldingType() {
		return holdingType;
	}
	public void setHoldingType(String holdingType) {
		this.holdingType = holdingType;
	}
	public String getCallingFee() {
		return callingFee;
	}
	public void setCallingFee(String callingFee) {
		this.callingFee = callingFee;
	}
	public String getHoldingPosition() {
		return holdingPosition;
	}
	public void setHoldingPosition(String holdingPosition) {
		this.holdingPosition = holdingPosition;
	}
	public String getCallingType() {
		return callingType;
	}
	public void setCallingType(String callingType) {
		this.callingType = callingType;
	}

}
