package com.adpanshi.cashloan.business.core.umeng.beans;

import com.adpanshi.cashloan.business.core.common.util.DateUtil;

import java.util.Date;
import java.util.HashMap;

/***
 ** @category 发送策略...
 ** @author qing.yunhui
 ** @email: qingyunhui@smoney.cc
 ** @createTime: 2017年9月1日上午9:54:46
 **/
@SuppressWarnings("serial")
public class Policy extends HashMap<String,Object>{
	
	/**
	 * @param expire_time 消息过期时间
	 * */
	public Policy(String expire_time){
		super.put("expire_time", expire_time);
	}
	
	/**
	 * @param expire_time 消息过期时间
	 * */
	public Policy(Date expire_time){
		super.put("expire_time",DateUtil.dateToString(expire_time, DateUtil.YYYY_MM_DD_HH_MM_SS) );
	}
	
	/**
	 * @param start_time 消息发送时间
	 * @param expire_time 消息过期时间(值不可小于消息发送时间)
	 * */
	public Policy(String start_time,String expire_time){
		super.put("start_time", start_time);
		super.put("expire_time", expire_time);
	}
	
	/**
	 * @param start_time 消息发送时间
	 * @param expire_time 消息过期时间(值不可小于消息发送时间)
	 * */
	public Policy(Date start_time,Date expire_time){
		super.put("start_time",DateUtil.dateToString(start_time, DateUtil.YYYY_MM_DD_HH_MM_SS) );
		super.put("expire_time",DateUtil.dateToString(expire_time, DateUtil.YYYY_MM_DD_HH_MM_SS) );
	}
	
	/**
	 * "start_time":"xx",   // 可选 定时发送时间，默认为立即发送。发送时间不能小于当前时间。
                                   格式: "YYYY-MM-DD HH:mm:ss"。 
                                   注意, start_time只对任务生效。
      "expire_time":"xx",  // 可选 消息过期时间,其值不可小于发送时间,
                                   默认为3天后过期。格式同start_time
	 * */
	
}
