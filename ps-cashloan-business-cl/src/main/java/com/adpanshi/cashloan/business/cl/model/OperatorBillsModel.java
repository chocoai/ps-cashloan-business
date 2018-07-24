package com.adpanshi.cashloan.business.cl.model;

import com.adpanshi.cashloan.business.cl.domain.OperatorBills;

/**
 * 运营商信息-账单信息

 * @version 1.0
 * @date 2017年3月13日下午7:26:31
 * Copyright 粉团网路 现金贷  All Rights Reserved
 *
 * 
 *
 */
public class OperatorBillsModel extends OperatorBills {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 账单起始日期2016-11-01-2016-11-30
	 */
	private String billMonthDate;

	/** 
	 * 获取账单起始日期2016-11-01-2016-11-30
	 * @return billMonthDate
	 */
	public String getBillMonthDate() {
		return billMonthDate;
	}

	/** 
	 * 设置账单起始日期2016-11-01-2016-11-30
	 * @param billMonthDate
	 */
	public void setBillMonthDate(String billMonthDate) {
		this.billMonthDate = billMonthDate;
	}
	
	
}
