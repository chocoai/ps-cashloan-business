package com.adpanshi.cashloan.business.core.model;

import com.adpanshi.cashloan.business.core.common.context.Constant;

import java.util.HashMap;
import java.util.Map;

/***
 ** @category 请用一句话来描述其用途...
 ** @author qing.yunhui
 ** @email: qingyunhui@fentuan360.com
 ** @createTime: 2018年4月8日上午10:07:02
 **/
public class ResponseData {
	
	private Map<String,Object> data=new HashMap<>();
	
	public ResponseData(){}
	
	public ResponseData(int code,String msg){
		data.put(Constant.RESPONSE_CODE, code);
		data.put(Constant.RESPONSE_CODE_MSG, msg);
	}
	
	public ResponseData add(int code,String msg){
		data.put(Constant.RESPONSE_CODE, code);
		data.put(Constant.RESPONSE_CODE_MSG, msg);
		return this;
	}

	public Map<String, Object> getData() {
		return data;
	}
	
}
