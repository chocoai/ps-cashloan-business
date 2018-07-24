package com.adpanshi.cashloan.business.core.umeng.beans;

/***
 ** @category 通知展现内容 @3...
 ** @author qing.yunhui
 ** @email: qingyunhui@smoney.cc
 ** @createTime: 2017年8月30日下午5:13:05
 **/
public class AndroidNotifyContent {
	
	 private String ticker;     // 必填 通知栏提示文字
     private String title;      // 必填 通知标题
     private String text;       // 必填 通知文字描述 
     
     /**
      * <p>参考 UnEnum.AfterOpen</p>
      * 必填 值可以为:
           "go_app": 打开应用
           "go_url": 跳转到URL
           "go_activity": 打开特定的activity
           "go_custom": 用户自定义内容。
      * */
     private String after_open;
     
     /**
      * 可选 display_type=message, 或者display_type=notification且"after_open"为"go_custom"时，
      * 该字段必填。用户自定义内容, 可以为字符串或者JSON格式。
      * */
     private String custom;   
     
     /**
      * 可选 用户自定义内容, "d","p"为友盟保留字段，key不可以是"d","p"
      * */
     private Extra extra;
     
     
	public String getTicker() {
		return ticker;
	}
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getCustom() {
		return custom;
	}
	public void setCustom(String custom) {
		this.custom = custom;
	}
	public String getAfter_open() {
		return after_open;
	}
	public void setAfter_open(String after_open) {
		this.after_open = after_open;
	}
	public Extra getExtra() {
		return extra;
	}
	public void setExtra(Extra extra) {
		this.extra = extra;
	}
	
}
