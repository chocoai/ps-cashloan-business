package com.adpanshi.cashloan.business.cl.service;


import com.alibaba.fastjson.JSONArray;

public interface TCUsedBusiInfoService {
	
	public void batchSave(JSONArray usedBusiInfoArray, String orderId);

}
