package com.adpanshi.cashloan.cl.model;



import com.adpanshi.cashloan.cl.domain.BorrowRepayLog;

public class ManageBRepayLogModel extends BorrowRepayLog {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 状态
	 */
	private String state;
	/**
	 * 订单号
	 */
	private String orderNo;
	/**
	 * 借款金额
	 */
	private Double borrowAmount;
	/**
	 * 还款金额（还款计划）
	 */
	private Double repayAmount;
	/**
	 * 已还款金额（还款记录）
	 */
	private Double repayLogAmount;
	/**
	 * 实际还款时间 zy

	 */
	private String repayTimeStr;
	/**
	 * 应还款时间
	 */
	private String repayPlanTimeStr;
	/**
	 * 应还逾期金额
	 */
	private Double repayPenalty;
	/**
	 * 姓名
	 */
	private String realName;
	/**
	 * 手机号
	 */
	private String phone;

	/**借款期限*/
	private String timeLimit;

	public Double getRepayPenalty() {
		return repayPenalty;
	}

	public void setRepayPenalty(Double repayPenalty) {
		this.repayPenalty = repayPenalty;
	}

	public String getRepayPlanTimeStr() {
		return repayPlanTimeStr;
	}

	public void setRepayPlanTimeStr(String repayPlanTimeStr) {
		this.repayPlanTimeStr = repayPlanTimeStr;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Double getBorrowAmount() {
		return borrowAmount;
	}

	public void setBorrowAmount(Double borrowAmount) {
		this.borrowAmount = borrowAmount;
	}

	public Double getRepayAmount() {
		return repayAmount;
	}

	public void setRepayAmount(Double repayAmount) {
		this.repayAmount = repayAmount;
	}

	public Double getRepayLogAmount() {
		return repayLogAmount;
	}

	public void setRepayLogAmount(Double repayLogAmount) {
		this.repayLogAmount = repayLogAmount;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	//zy 2017.7.31
	public String getRepayTimeStr() {
		return repayTimeStr;
	}
	public void setRepayTimeStr(String repayTimeStr) {
		this.repayTimeStr = repayTimeStr;;
	}

	public String getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(String timeLimit) {
		this.timeLimit = timeLimit;
	}

}
