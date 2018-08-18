package com.adpanshi.cashloan.core.common.util.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component("fastJsonUtil")
public class FastJsonUtil implements com.adpanshi.cashloan.core.common.util.FastJsonUtil {

	@Override
	public String toString(Object o) {
		return JSON.toJSONString(o);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> toMap(String json) {
		return (Map<String, Object>) JSON.parse(json);
	}

    @Override
	public <T> T toObject(String json ,Class<T> clz) {
		return JSON.parseObject(json, clz);
	}
    
    @Override
   	public <T> List<T> toObjectArray(String json ,Class<T> clz) {
   		return JSONObject.parseArray(json,clz);
   	}
    
}
