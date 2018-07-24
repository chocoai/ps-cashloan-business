package com.adpanshi.cashloan.business.cl.model;

import tool.util.NumberUtil;

/**
 * 每日通过率

 * @version 1.0
 * @date 2017年3月20日下午5:26:51
 * Copyright 粉团网路 现金贷  All Rights Reserved
 *
 * 
 *
 */
public class DayPassApr {

	/**
	 * 日期
	 */
	private String date;
	
	/**
	 * 借款人数
	 */
	private String borrowUserCount;
	
	/**
	 * 放款人数
	 */
	private String loanUserCount;
	
	/**
	 * 借款人数通过率
	 */
	private String borrowPassApr;

	private String newLoanUserCount;
	private String newLoanCount;

	private String newBorrowPassApr;//新用户通过率
	
	/**
	 * 借款笔数
	 */
	private String brrowCount;
	
	/**
	 * 放款笔数
	 */
	private String loanCount;
	
	/**
	 * 借款笔数通过率
	 */
	private String borrowApr;

	private String newBorrowApr;//新用户笔数通过率

	/** 
	 * 获取日期
	 * @return date
	 */
	public String getDate() {
		return date;
	}

	/** 
	 * 设置日期
	 * @param date
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/** 
	 * 获取借款人数
	 * @return borrowUserCount
	 */
	public String getBorrowUserCount() {
		return borrowUserCount;
	}

	/** 
	 * 设置借款人数
	 * @param borrowUserCount
	 */
	public void setBorrowUserCount(String borrowUserCount) {
		this.borrowUserCount = borrowUserCount;
	}

	/** 
	 * 获取放款人数
	 * @return loanUserCount
	 */
	public String getLoanUserCount() {
		return loanUserCount;
	}

	/** 
	 * 设置放款人数
	 * @param loanUserCount
	 */
	public void setLoanUserCount(String loanUserCount) {
		this.loanUserCount = loanUserCount;
	}

	/** 
	 * 获取借款人数通过率
	 * @return borrowPassApr
	 */
	public String getBorrowPassApr() {
		borrowPassApr = NumberUtil.format2Str(Double.valueOf(borrowPassApr));
		return borrowPassApr;
	}

	/** 
	 * 设置借款人数通过率
	 * @param borrowPassApr
	 */
	public void setBorrowPassApr(String borrowPassApr) {
		this.borrowPassApr = borrowPassApr;
	}

	/** 
	 * 获取借款笔数
	 * @return brrowCount
	 */
	public String getBrrowCount() {
		return brrowCount;
	}

	/** 
	 * 设置借款笔数
	 * @param brrowCount
	 */
	public void setBrrowCount(String brrowCount) {
		this.brrowCount = brrowCount;
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
	 * 获取借款笔数通过率
	 * @return borrowApr
	 */
	public String getBorrowApr() {
		borrowApr = NumberUtil.format2Str(Double.valueOf(borrowApr));
		return borrowApr;
	}

	/** 
	 * 设置借款笔数通过率
	 * @param borrowApr
	 */
	public void setBorrowApr(String borrowApr) {
		this.borrowApr = borrowApr;
	}

	public String getNewBorrowPassApr() {
		return newBorrowPassApr;
	}

	public void setNewBorrowPassApr(String newBorrowPassApr) {
		this.newBorrowPassApr = newBorrowPassApr;
	}

	public String getNewBorrowApr() {
		return newBorrowApr;
	}

	public void setNewBorrowApr(String newBorrowApr) {
		this.newBorrowApr = newBorrowApr;
	}

	public String getNewLoanUserCount() {
		return newLoanUserCount;
	}

	public void setNewLoanUserCount(String newLoanUserCount) {
		this.newLoanUserCount = newLoanUserCount;
	}

	public String getNewLoanCount() {
		return newLoanCount;
	}

	public void setNewLoanCount(String newLoanCount) {
		this.newLoanCount = newLoanCount;
	}
}
