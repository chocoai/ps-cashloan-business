package com.adpanshi.cashloan.business.core.enjoysign.beans;

import com.adpanshi.cashloan.business.core.enjoysign.constants.EnjoysignConstant;

/***
 ** @category 请用一句话来描述其用途...
 ** @author qing.yunhui
 ** @email: qingyunhui@smoney.cc
 ** @createTime: 2017年9月25日下午9:00:39
 **/
public class SignatoryResult {
	
	/**
	 * 执行结果码。"0":成功、-1：失败;  ( 40004 :重复签章 )
	 */
	private String resultCode;

	/**
	 * 详细的消息内容。
	 */
	private String resultMsg;
	
	/**
	 * 要返回的对象
	 */
	private Object obj;
	
	/**
	 * 类型
	 * */
	private Class<?> clz;
	
	public SignatoryResult(){}
	
	public SignatoryResult(String resultCode,String resultMsg,Object obj){
		this.resultCode=resultCode;
		this.resultMsg=resultMsg;
		this.obj=obj;
	}
	
	public SignatoryResult(String resultCode,String resultMsg,Object obj,Class<?> clz){
		this.resultCode=resultCode;
		this.resultMsg=resultMsg;
		this.obj=obj;
		this.clz=clz;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	public Boolean getIsSuccess() {
		return EnjoysignConstant.SUCCESS.equals(getResultCode());
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public Class<?> getClz() {
		return clz;
	}

	public void setClz(Class<?> clz) {
		this.clz = clz;
	}
	
}
