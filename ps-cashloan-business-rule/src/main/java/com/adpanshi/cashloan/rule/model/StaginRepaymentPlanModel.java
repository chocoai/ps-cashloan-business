package com.adpanshi.cashloan.rule.model;

import java.io.Serializable;

/***
 ** @category 分期还款计划...
 ** @author qing.yunhui
 ** @email: 280672161@qq.com
 ** @createTime: 2017年12月26日上午10:52:17
 **/
public class StaginRepaymentPlanModel implements Serializable{

	private static final long serialVersionUID = 1L;

	private String repayTime;//每期的还款日
	private String orderNo;//每期的子订单-订单号
	private Double interests;//每期的利息
	private Double amount;//每期的本金
	private String state;//每期的订单号
	private String borrowId;//子订单-主键id
	private Double totalAmount;//各分期总本金
	private Double penaltyAmout;//每期的逾期金额
	private Double fee;//每期的手续费
	private String bank;//银行名称
	private String cardNo;//银行卡号 
	private String byStages;//第N期
	private long repaymentDay;//距离还款日N天
	
	public String getRepayTime() {
		return repayTime;
	}
	public void setRepayTime(String repayTime) {
		this.repayTime = repayTime;
	}
	public Double getInterests() {
		return interests;
	}
	
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public void setInterests(Double interests) {
		this.interests = interests;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getBorrowId() {
		return borrowId;
	}
	public void setBorrowId(String borrowId) {
		this.borrowId = borrowId;
	}
	public Double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public Double getPenaltyAmout() {
		return penaltyAmout;
	}
	public void setPenaltyAmout(Double penaltyAmout) {
		this.penaltyAmout = penaltyAmout;
	}
	public Double getFee() {
		return fee;
	}
	public void setFee(Double fee) {
		this.fee = fee;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getByStages() {
		return byStages;
	}
	public void setByStages(String byStages) {
		this.byStages = byStages;
	}
	public long getRepaymentDay() {
		return repaymentDay;
	}
	public void setRepaymentDay(long repaymentDay) {
		this.repaymentDay = repaymentDay;
	}
}
