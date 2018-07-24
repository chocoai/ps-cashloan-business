package com.adpanshi.cashloan.business.cl.enums;

import com.adpanshi.cashloan.business.core.common.enums.ICommonEnum;

/***
 ** @category 分期付(主动还款或银行卡扣款)-支付结果枚举...
 ** @author qing.yunhui
 ** @email: qingyunhui@smoney.cc
 ** @createTime: 2017年9月13日下午6:16:59
 **/
public enum StagesResultPayEnum implements ICommonEnum{
	
	NOT_FOUND("NOT_FOUND","订单号未找到"),//注意 CODE 是自定义的、区别自己返回还是由连连返回
	SUCCESS("SUCCESS","成功"),
	WAITING("WAITING","等待支付"),
	PROCESSING("PROCESSING","银行支付处理中"),
	REFUND("REFUND","退款"),
	FAILURE("FAILURE","失败");
	
	private String code;
	
	private String name;
	
	private StagesResultPayEnum(String code, String name){
		this.code = code;
		this.name = name;
	}
	
	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}
	
	
	/**
	 * <p>判断是否需要重新发起，连连接订单查询口请求</p>
	 * @param code
	 * @return boolean 当值为true时、表示会请求连连查询接口，值为false时表示这个订单已经交易结束
	 * */
	public static boolean isReRequest(String code){
		if(SUCCESS.code.equals(code) || REFUND.code.equals(code)|| FAILURE.code.equals(code)){
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
		if(REFUND.code.equals(code) || FAILURE.code.equals(code)){
			return true;
		}
		return false;
	}
	
	@Override
	public Integer getKey() {
		return null;
	}
}
