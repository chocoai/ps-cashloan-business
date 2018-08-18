package com.adpanshi.cashloan.rule.model;

import java.util.Date;

/**
 * 首页轮播参数
 *
 */
public class IndexModel {


	private String cardNo;

	private Date creditTime;

	private Date loanTime;

	private Double amount;

	private Date createTime;

	private String phone;

	/**
	 * @return the cardNo
	 */
	public String getCardNo() {
		return cardNo;
	}

	/**
	 * @param cardNo the cardNo to set
	 */
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	/**
	 * @return the creditTime
	 */
	public Date getCreditTime() {
		return creditTime;
	}

	/**
	 * @param creditTime the creditTime to set
	 */
	public void setCreditTime(Date creditTime) {
		this.creditTime = creditTime;
	}

	/**
	 * @return the loanTime
	 */
	public Date getLoanTime() {
		return loanTime;
	}

	/**
	 * @param loanTime the loanTime to set
	 */
	public void setLoanTime(Date loanTime) {
		this.loanTime = loanTime;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
