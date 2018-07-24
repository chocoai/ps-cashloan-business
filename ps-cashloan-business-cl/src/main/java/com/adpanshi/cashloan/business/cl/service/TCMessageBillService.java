package com.adpanshi.cashloan.business.cl.service;


import com.alibaba.fastjson.JSONArray;

public interface TCMessageBillService {
	
	public void batchSave(JSONArray messageBillArray, String orderId);
	

}
