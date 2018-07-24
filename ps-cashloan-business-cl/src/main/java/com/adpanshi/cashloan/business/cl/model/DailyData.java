package com.adpanshi.cashloan.business.cl.model;

import java.io.Serializable;

/**
 * 新平台数据日报
 */
public class DailyData implements Serializable{

	private Long id;

	private String date;//日期

	//老数据统计字段

	private String userCount;//用户数

	private String borrowCount; //借款笔数

	private String loanCount;//放款笔数

	private String overdueCount; //逾期笔数

	private String urgeRepayCount; //催收次数

	private String badAmtCount;  //坏账笔数

	private String badAmt;  //坏账金额

	private String loanAmt;  //放款金额

	private String repayAmt;  //还款金额

	private String serveFeeAmt; //服务费金额

	private String overdueAmt;  //逾期金额

	private String overdueInterest;//逾期罚息


//	新增统计字段

	private String newUserLoanCount;//每日新用户放款笔数  = 总数-老用户数人审核-老用户机审
	private String oldUserLoanCount;//老用户人审放款笔数
	private String oldUserAutoLoanCount;//老用户机审放款笔数
	private String repayAmtCount;//当日应还款笔数
	private String oldUserOverdueCount;//老用户逾期笔数
	private String overdueRate;//逾期率
	private String oldOverdueRate;//老用户逾期率

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getUserCount() {
		return userCount;
	}

	public void setUserCount(String userCount) {
		this.userCount = userCount;
	}

	public String getBorrowCount() {
		return borrowCount;
	}

	public void setBorrowCount(String borrowCount) {
		this.borrowCount = borrowCount;
	}

	public String getLoanCount() {
		return loanCount;
	}

	public void setLoanCount(String loanCount) {
		this.loanCount = loanCount;
	}

	public String getOverdueCount() {
		return overdueCount;
	}

	public void setOverdueCount(String overdueCount) {
		this.overdueCount = overdueCount;
	}

	public String getUrgeRepayCount() {
		return urgeRepayCount;
	}

	public void setUrgeRepayCount(String urgeRepayCount) {
		this.urgeRepayCount = urgeRepayCount;
	}

	public String getBadAmtCount() {
		return badAmtCount;
	}

	public void setBadAmtCount(String badAmtCount) {
		this.badAmtCount = badAmtCount;
	}

	public String getBadAmt() {
		return badAmt;
	}

	public void setBadAmt(String badAmt) {
		this.badAmt = badAmt;
	}

	public String getLoanAmt() {
		return loanAmt;
	}

	public void setLoanAmt(String loanAmt) {
		this.loanAmt = loanAmt;
	}

	public String getRepayAmt() {
		return repayAmt;
	}

	public void setRepayAmt(String repayAmt) {
		this.repayAmt = repayAmt;
	}

	public String getServeFeeAmt() {
		return serveFeeAmt;
	}

	public void setServeFeeAmt(String serveFeeAmt) {
		this.serveFeeAmt = serveFeeAmt;
	}

	public String getOverdueAmt() {
		return overdueAmt;
	}

	public void setOverdueAmt(String overdueAmt) {
		this.overdueAmt = overdueAmt;
	}

	public String getOverdueInterest() {
		return overdueInterest;
	}

	public void setOverdueInterest(String overdueInterest) {
		this.overdueInterest = overdueInterest;
	}

	public String getNewUserLoanCount() {
		return newUserLoanCount;
	}

	public void setNewUserLoanCount(String newUserLoanCount) {
		this.newUserLoanCount = newUserLoanCount;
	}

	public String getOldUserLoanCount() {
		return oldUserLoanCount;
	}

	public void setOldUserLoanCount(String oldUserLoanCount) {
		this.oldUserLoanCount = oldUserLoanCount;
	}

	public String getOldUserAutoLoanCount() {
		return oldUserAutoLoanCount;
	}

	public void setOldUserAutoLoanCount(String oldUserAutoLoanCount) {
		this.oldUserAutoLoanCount = oldUserAutoLoanCount;
	}

	public String getRepayAmtCount() {
		return repayAmtCount;
	}

	public void setRepayAmtCount(String repayAmtCount) {
		this.repayAmtCount = repayAmtCount;
	}

	public String getOldUserOverdueCount() {
		return oldUserOverdueCount;
	}

	public void setOldUserOverdueCount(String oldUserOverdueCount) {
		this.oldUserOverdueCount = oldUserOverdueCount;
	}

	public String getOverdueRate() {
		return overdueRate;
	}

	public void setOverdueRate(String overdueRate) {
		this.overdueRate = overdueRate;
	}

	public String getOldOverdueRate() {
		return oldOverdueRate;
	}

	public void setOldOverdueRate(String oldOverdueRate) {
		this.oldOverdueRate = oldOverdueRate;
	}
}
