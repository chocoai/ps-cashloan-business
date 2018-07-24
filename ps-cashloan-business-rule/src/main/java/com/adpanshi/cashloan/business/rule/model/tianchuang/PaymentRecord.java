package com.adpanshi.cashloan.business.rule.model.tianchuang;

public class PaymentRecord {

	private Long id;
	
	private String orderId;
	
	/**
	 * 缴费金额，小数点后两位精确到分，如：10.00
	 */
	private String payFee;
	/**
	 * 缴费日期，格式：yyyy-MM-dd HH:mm:ss
	 */
	private String payDate;
	/**
	 * 缴费渠道，例如：帐务后台 移动：现⾦交费
	 */
	private String payChannel;
	/**
	 * 缴费类别，例如：本期存⼊、本期调整
	 */
	private String payType;
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
	public String getPayFee() {
		return payFee;
	}
	public void setPayFee(String payFee) {
		this.payFee = payFee;
	}
	public String getPayDate() {
		return payDate;
	}
	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}
	public String getPayChannel() {
		return payChannel;
	}
	public void setPayChannel(String payChannel) {
		this.payChannel = payChannel;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	
}
