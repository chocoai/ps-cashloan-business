package com.adpanshi.cashloan.business.cl.model;

import com.adpanshi.cashloan.business.cl.domain.PayRespLog;


public class ManagePayRespLogModel extends PayRespLog {

	private static final long serialVersionUID = 1L;

	public ManagePayRespLogModel(String orderNo, Integer type, String params) {
		super(orderNo, type, params);
	}
	
}
