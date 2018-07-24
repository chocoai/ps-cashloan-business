package com.adpanshi.cashloan.business.cl.enums;

import com.adpanshi.cashloan.business.core.common.enums.ICommonEnum;

/***
 ** @category 实时付-支付结果枚举...
 ** @author qing.yunhui
 ** @email: qingyunhui@smoney.cc
 ** @createTime: 2017年9月15日下午3:37:52
 **/
public enum InstantResultPayEnum implements ICommonEnum{

	
	//APPLY 付款申请 CHECK 复核申请 SUCCESS 付款成功 PROCESSING 付款处理中 CANCEL 付款退款（付款成 功后，有可能发生退款） FAILURE  付款失败 CLOSED  付款关闭
	
	NOT_FOUND("NOT_FOUND","订单号未找到"),//注意 CODE 是自定义的、区别自己返回还是由连连返回
	
	APPLY("APPLY","付款申请"),
	CHECK("CHECK","复核申请"),
	SUCCESS("SUCCESS","付款成功"),
	PROCESSING("PROCESSING","付款处理中"),
	CANCEL("CANCEL","付款退款(付款成 功后，有可能发生退款)"),
	FAILURE("FAILURE","付款失败"),
	CLOSED("CLOSED","付款关闭");
	
	private String code;
	
	private String name;
	
	private InstantResultPayEnum(String code, String name){
		this.code = code;
		this.name = name;
	}
	
	/**
	 * <p>判断是否需要重新发起，连连接订单查询口请求</p>
	 * @param code
	 * @return boolean 当值为true时、表示会请求连连查询接口，值为false时表示这个订单已经交易结束
	 * */
	public static boolean isReRequest(String code){
		if(SUCCESS.code.equals(code) || CANCEL.code.equals(code)||FAILURE.code.equals(code)|| CLOSED.getCode().equals(code)){
			return false;
		}
		return true;
	}
	
	/***
	 * <p>根据给定cods判断是否需要发起重新支付请求</p>
	 * @param codes
	 * @return boolean(true:重新发起请求、false:等待银行处理..)
	 * */
	public static boolean isPayAgainForRequest(String code){
		if(FAILURE.code.equals(code) || CLOSED.code.equals(code) || CANCEL.code.equals(code) || CHECK.code.equals(code)){
			return true;
		}
		return false;
	}
	
	@Override
	public String getCode() {
		return code;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Integer getKey() {
		return null;
	}

}
