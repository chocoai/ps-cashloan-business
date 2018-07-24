package com.adpanshi.cashloan.business.cl.service;


import com.alibaba.fastjson.JSONArray;

public interface TCInternetTrafficService {
	
	public void batchSave(JSONArray internetTrafficArray, String orderId);

}
