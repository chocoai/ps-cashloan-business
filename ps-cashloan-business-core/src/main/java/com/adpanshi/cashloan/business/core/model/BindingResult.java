package com.adpanshi.cashloan.business.core.model;

import com.adpanshi.cashloan.business.core.common.context.Constant;
import com.adpanshi.cashloan.business.core.common.util.StringUtil;

import java.util.HashMap;
import java.util.Map;

/***
 ** @category 绑定aop参数校验不通过的返回code|value
 ** @author qing.yunhui
 ** @email: qingyunhui@smoney.cc
 ** @createTime: 2017年11月29日下午4:00:35
 **/
public class BindingResult {
	
	private int code;
	
	private String msg;
	
	/**
	 * false:标识校验通过、true:校验不通过
	 * */
	private Boolean fail;

	public BindingResult(){}
	
	public boolean isPass(){
		return (StringUtil.isNotEmptys(this,fail) && fail);
	}
	
	public BindingResult(int code,String msg,Boolean fail){
		this.code=code;
		this.msg=msg;
		this.fail=fail;
	}
	
	/**
	 * <p>处理api入参校验时所绑定的参数并重置到输出结果上</p>
	 * @param result 待输出的结果
	 * @param bindingResult 已绑定的结果
	 * */
	public Map<String,Object> resetResult(Map<String,Object> result){
		if(StringUtil.isNotEmptys(this,fail) && fail){
			if(null==result)result=new HashMap<>();
			result.put(Constant.RESPONSE_CODE,this.code);
			result.put(Constant.RESPONSE_CODE_MSG,this.msg);
		}
		return result;
	}
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Boolean getFail() {
		return fail;
	}

	public void setFail(Boolean fail) {
		this.fail = fail;
	}

}
