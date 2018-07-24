package com.adpanshi.cashloan.business.cl.model;

import tool.util.NumberUtil;
import tool.util.StringUtil;

/**
 * 每月/每日 还款分析

 * @version 1.0
 * @date 2017年3月21日下午3:27:53
 * Copyright 粉团网路 现金贷  All Rights Reserved
 *
 * 
 *
 */
public class RepayAnalisisModel {
	/**
	 * 日期、月份
	 */
	private String date;
	
	/**
	 * 还款笔数
	 */
	private String repayCount;
	
	/**
	 * 逾期笔数
	 */
	private String overdueCount;
	
	/**
	 * 还款金额
	 */
	private String repayAmt;
	
	/**
	 * 放款笔数
	 */
	private String loanCount;
	
	/**
	 * 逾期罚息
	 */
	private String penaltyAmout;
	
	/**
	 * 逾期占比
	 */
	private String apr;

	/** 
	 * 获取日期、月份
	 * @return date
	 */
	public String getDate() {
		return date;
	}

	/** 
	 * 设置日期、月份
	 * @param date
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/** 
	 * 获取还款笔数
	 * @return repayCount
	 */
	public String getRepayCount() {
		return repayCount;
	}

	/** 
	 * 设置还款笔数
	 * @param repayCount
	 */
	public void setRepayCount(String repayCount) {
		this.repayCount = repayCount;
	}

	/** 
	 * 获取逾期笔数
	 * @return overdueCount
	 */
	public String getOverdueCount() {
		return overdueCount;
	}

	/** 
	 * 设置逾期笔数
	 * @param overdueCount
	 */
	public void setOverdueCount(String overdueCount) {
		this.overdueCount = overdueCount;
	}

	/** 
	 * 获取还款金额
	 * @return repayAmt
	 */
	public String getRepayAmt() {
		return repayAmt;
	}

	/** 
	 * 设置还款金额
	 * @param repayAmt
	 */
	public void setRepayAmt(String repayAmt) {
		this.repayAmt = repayAmt;
	}

	/** 
	 * 获取放款笔数
	 * @return loanCount
	 */
	public String getLoanCount() {
		return loanCount;
	}

	/** 
	 * 设置放款笔数
	 * @param loanCount
	 */
	public void setLoanCount(String loanCount) {
		this.loanCount = loanCount;
	}

	/** 
	 * 获取逾期罚息
	 * @return penaltyAmout
	 */
	public String getPenaltyAmout() {
		return penaltyAmout;
	}

	/** 
	 * 设置逾期罚息
	 * @param penaltyAmout
	 */
	public void setPenaltyAmout(String penaltyAmout) {
		this.penaltyAmout = penaltyAmout;
	}

	/** 
	 * 获取逾期占比
	 * @return apr
	 */
	public String getApr() {
		String repayCount = this.getRepayCount();
		String overdueCount = this.getOverdueCount();
		if(StringUtil.isNotBlank(repayCount)&&StringUtil.isNotBlank(overdueCount) && Integer.valueOf(overdueCount)>0){
			Double repay = Double.valueOf(repayCount);
			Double overdue = Double.valueOf(overdueCount);
			Double aprValue = overdue/repay;
			apr = NumberUtil.format2Str(aprValue*100);
		}else{
			apr = "0.00";
		}
		
		
		return apr;
	}

	/** 
	 * 设置逾期占比
	 * @param apr
	 */
	public void setApr(String apr) {
		this.apr = apr;
	}
	
	
	
}
