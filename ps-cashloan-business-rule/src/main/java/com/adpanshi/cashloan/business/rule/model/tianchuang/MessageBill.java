package com.adpanshi.cashloan.business.rule.model.tianchuang;

/**
 * 短信记录
 * @author ppchen
 * 2017年8月18日 下午3:17:43
 * 
 */
public class MessageBill {
	
	private Long id;
	
	private String orderId;
	
	/**
	 * 收发短信时间
	 */
	private String smsTime;
	/**
	 * 机主收发短信地点
	 */
	private String smsAddr;
	/**
	 * 对方号码
	 */
	private String otherNum;
	/**
	 * 费⽤类型
	 * 联通：
	 * 01:国内短信
	 * 02:国际短信
	 * 03:国内彩信
	 * 04:国际漫游短信
	 * 05:集团短信
	 * 06:国际彩信
	 */
	private String busType;
	/**
	 * 业务名称
	 */
	private String busiName;
	/**
	 * 本条短信的费用
	 */
	private String amount;
	/**
	 * 通信⽅式，如：接收
	 */
	private String smsSaveType;
	/**
	 * 信息类型，如：短信
	 */
	private String smsType;
	/**
	 * 套餐优惠
	 */
	private String packageDis;
	/**
	 * 对⽅⼿机号码的归属地
	 */
	private String otherNumArea;
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
	public String getSmsTime() {
		return smsTime;
	}
	public void setSmsTime(String smsTime) {
		this.smsTime = smsTime;
	}
	public String getSmsAddr() {
		return smsAddr;
	}
	public void setSmsAddr(String smsAddr) {
		this.smsAddr = smsAddr;
	}
	public String getOtherNum() {
		return otherNum;
	}
	public void setOtherNum(String otherNum) {
		this.otherNum = otherNum;
	}
	public String getBusType() {
		return busType;
	}
	public void setBusType(String busType) {
		this.busType = busType;
	}
	public String getBusiName() {
		return busiName;
	}
	public void setBusiName(String busiName) {
		this.busiName = busiName;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getSmsSaveType() {
		return smsSaveType;
	}
	public void setSmsSaveType(String smsSaveType) {
		this.smsSaveType = smsSaveType;
	}
	public String getSmsType() {
		return smsType;
	}
	public void setSmsType(String smsType) {
		this.smsType = smsType;
	}
	public String getPackageDis() {
		return packageDis;
	}
	public void setPackageDis(String packageDis) {
		this.packageDis = packageDis;
	}
	public String getOtherNumArea() {
		return otherNumArea;
	}
	public void setOtherNumArea(String otherNumArea) {
		this.otherNumArea = otherNumArea;
	}
}
