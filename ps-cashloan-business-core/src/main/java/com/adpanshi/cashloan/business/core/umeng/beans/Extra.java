package com.adpanshi.cashloan.business.core.umeng.beans;

import java.util.HashMap;

/***
 ** @category 可选 用户自定义key-value。
 *	<p>只对"通知"(display_type=notification)生效,可以配合通知到达后,打开App,打开URL,打开Activity使用。</p>
 ** @author qing.yunhui
 ** @email: qingyunhui@smoney.cc
 ** @createTime: 2017年8月31日上午9:18:05
 **/
@SuppressWarnings("serial")
public class Extra extends HashMap<String,Object>{
	
	public Extra(){}
	
	public Extra(String key,Object value){
		super.put(key, value);
	}
}
