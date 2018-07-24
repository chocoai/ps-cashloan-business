package com.adpanshi.cashloan.business.cl.service;


import com.alibaba.fastjson.JSONArray;

public interface TCCallingRecordService {
	
	void batchSave(JSONArray callingRecordArray, String orderId, String phone);
	
}
