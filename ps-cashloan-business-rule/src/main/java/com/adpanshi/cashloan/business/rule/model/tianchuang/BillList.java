package com.adpanshi.cashloan.business.rule.model.tianchuang;

/**
 * 月账单 详细
 * @author ppchen
 * 2017年8月18日 下午12:01:32
 * 
 */
public class BillList {
	
	private Long id;
	
	private String orderId;
	/**
	 * 月账单id
	 */
	private Long billDetailInfoId;
	/**
	 * 费用类型
	 * 固定费用、语⾳通信费、上⽹费、短彩信、增值业务费、代收费、其它费用、优惠等
	 */
	private String accountCategory;
	/**
	 * 费用金额
	 */
	private String accountFee;
	/**
	 * 费用名称
	 */
	private String accountItems;
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
	public Long getBillDetailInfoId() {
		return billDetailInfoId;
	}
	public void setBillDetailInfoId(Long billDetailInfoId) {
		this.billDetailInfoId = billDetailInfoId;
	}
	public String getAccountCategory() {
		return accountCategory;
	}
	public void setAccountCategory(String accountCategory) {
		this.accountCategory = accountCategory;
	}
	public String getAccountFee() {
		return accountFee;
	}
	public void setAccountFee(String accountFee) {
		this.accountFee = accountFee;
	}
	public String getAccountItems() {
		return accountItems;
	}
	public void setAccountItems(String accountItems) {
		this.accountItems = accountItems;
	}
	
}
