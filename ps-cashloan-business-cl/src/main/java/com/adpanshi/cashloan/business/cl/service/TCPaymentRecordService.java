package com.adpanshi.cashloan.business.cl.service;


import com.alibaba.fastjson.JSONArray;

public interface TCPaymentRecordService {
	
	public void batchSave(JSONArray paymentRecordArray, String orderId);

}
