package com.adpanshi.cashloan.business.cl.model;

import com.adpanshi.cashloan.business.cl.domain.OperatorTdSmsInfo;

/**
 * 短信详单实体
 * 

 * @version 1.0.0
 * @date 2017-05-24 09:29:45
 * Copyright 粉团网路  com.adpanshi.cashloan All Rights Reserved
 *
 *
 */
 public class OperatorTdSmsInfoModel extends OperatorTdSmsInfo {

    private static final long serialVersionUID = 1L;
 
    /**
    * 短信记录
    */
    private String smsRecord;

	public String getSmsRecord() {
		return smsRecord;
	}

	public void setSmsRecord(String smsRecord) {
		this.smsRecord = smsRecord;
	}

 

}