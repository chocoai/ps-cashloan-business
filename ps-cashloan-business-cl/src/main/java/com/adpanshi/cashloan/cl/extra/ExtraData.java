package com.adpanshi.cashloan.cl.extra;

import java.util.LinkedHashMap;

/***
 ** @category 请用一句话来描述其用途...
 ** @author qing.yunhui
 ** @email: qingyunhui@smoney.cc
 ** @createTime: 2017年11月3日下午18:21:58
 **/
@SuppressWarnings("serial")
public class ExtraData extends LinkedHashMap<String, Object>{

	public ExtraData(){}
	
	public void addData(String key,Object value){
		this.put(key, value);
	}
}
