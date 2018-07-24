package com.adpanshi.cashloan.business.cl.service;


import com.alibaba.fastjson.JSONArray;

public interface TCBillDetailInfoService {
	
	int batchSave(JSONArray billDetailInfoArray, String orderId);
	

}
