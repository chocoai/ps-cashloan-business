package com.adpanshi.cashloan.business.rule.model.tianchuang;

/**
 * 月账单
 * @author ppchen
 * 2017年8月18日 下午5:37:12
 * 
 */
public class BillDetailInfo {
	
	private Long id;
	
	private String orderId;
	/**
	 * 总额
	 */
	private String billComsume;
	/**
	 * 账期
	 */
	private String billCycle;
	/**
	 * 月固定费
	 */
	private String planAmount;
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
	public String getBillComsume() {
		return billComsume;
	}
	public void setBillComsume(String billComsume) {
		this.billComsume = billComsume;
	}
	public String getBillCycle() {
		return billCycle;
	}
	public void setBillCycle(String billCycle) {
		this.billCycle = billCycle;
	}
	public String getPlanAmount() {
		return planAmount;
	}
	public void setPlanAmount(String planAmount) {
		this.planAmount = planAmount;
	}
}
