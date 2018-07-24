package com.adpanshi.cashloan.business.core.umeng.beans;

/***
 ** @category 请用一句话来描述其用途 @2...
 ** @author qing.yunhui
 ** @email: qingyunhui@smoney.cc
 ** @createTime: 2017年8月30日下午6:02:11
 **/
public class Payload {
	
	/**
	 * <p>参考UnEnum.DisplayType</p>
	 * 消息类型值可以为:
	 * 		通知:notification
	 * 		消息:message
	 * */
	private String display_type;  //必填 (点击"通知"的后续行为，默认为打开app)
	 
	 /**
	  * 必填 消息体。
              display_type=message时,body的内容只需填写custom字段。
              display_type=notification时, body包含如下参数:
	  * */
	private AndroidNotifyContent body;
	
	/**
	 * IOS 必填字段
	 * */
	private IOSNotifyContent aps;
	
	/**
	 * 可选 用户自定义key-value。
	 * 只对"通知"(display_type=notification)生效。
     * 可以配合通知到达后,打开App,打开URL,打开Activity使用
	 * */
	private Extra extra;

	public String getDisplay_type() {
		return display_type;
	}

	public void setDisplay_type(String display_type) {
		this.display_type = display_type;
	}

	public AndroidNotifyContent getBody() {
		return body;
	}

	public void setBody(AndroidNotifyContent body) {
		this.body = body;
	}

	public Extra getExtra() {
		return extra;
	}

	public void setExtra(Extra extra) {
		this.extra = extra;
	}

	public IOSNotifyContent getAps() {
		return aps;
	}

	public void setAps(IOSNotifyContent aps) {
		this.aps = aps;
	}
	
}
