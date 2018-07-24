package com.adpanshi.cashloan.business.cl.model;

import com.adpanshi.cashloan.business.cl.domain.OperatorTdCallInfo;

/**
 * 通话记录详单实体
 * 

 * @version 1.0.0
 * @date 2017-05-24 09:32:41
 * Copyright 粉团网路  com.adpanshi.cashloan All Rights Reserved
 *
 *
 */
 public class OperatorTdCallInfoModel extends OperatorTdCallInfo {

    private static final long serialVersionUID = 1L;
    /**
    * 通话记录
    */
    private String callRecord;
    
	public String getCallRecord() {
		return callRecord;
	}
	public void setCallRecord(String callRecord) {
		this.callRecord = callRecord;
	}
 

}