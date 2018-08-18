package com.adpanshi.cashloan.core.common.util;

import com.adpanshi.cashloan.core.common.enums.OrderPrefixEnum;

import java.util.UUID;

/**
 * 工具类-订单号生成
 * JAVA版本的自动生成有规则的订单号(或编号) 生成的格式是: 200908010001 前面几位为当前的日期,后面五位为系统自增长类型的编号 原理:
 * 1.获取当前日期格式化值; 
 * 2.读取文件,上次编号的值+1最为当前此次编号的值 (新的一天会重新从1开始编号)

 * @version 1.0
 * @date 2015年11月17日 下午5:36:03
 * Copyright 粉团网路  All Rights Reserved
 *
 * 
 *
 */
public class OrderNoUtil {

	/**
	 * 产生唯一 的序列号
	 * @return
	 */
	public static String getSerialNumber() {
		return GenOrderNoUtil.nextId();
	}
	
	/**
	 * 生成付款业务订单号
	 * @return
	 */
	public static String genPayOrderNo(){
		return OrderPrefixEnum.PAY.getCode()+GenOrderNoUtil.nextId();
	}
	
	/**
	 * 生成还款业务订单号
	 * @return
	 */
	public static String genRepaymentOrderNo(){
		return OrderPrefixEnum.REPAYMENT.getCode()+GenOrderNoUtil.nextId();
	} 
	
	/**
	 * 生成退还(退给用户-给用户打钱)业务订单号
	 * @return
	 */
	public static String genGiveBackOrderNo(){
		return OrderPrefixEnum.GIVE_BACK.getCode()+GenOrderNoUtil.nextId();
	}
	
	/**
	 * <p>根据给定前缀生成新的序列号</p>
	 * @param prefix 前缀
	 * @return String 待添加的前缀
	 * @return 
	 * */
	public static String getSerialNumber(String prefix){
		if(StringUtil.isEmpty(prefix)) {return getSerialNumber();}
		return prefix+getSerialNumber();
	}

	/**
	 * 产生唯一的序列号
	 * @author: nmnl
	 * @date: 2017-10-23
	 * @return uuid
	 */
	public static String geUUIDOrderNo() {
		return UUID.randomUUID().toString().replace("-","");
	}

	public static void main(String[ ]args){
		System.out.println(genPayOrderNo());
		System.out.println(genRepaymentOrderNo());
		System.out.println(genGiveBackOrderNo());
	}
	
}
