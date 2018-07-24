package com.adpanshi.cashloan.business.core.umeng.beans;
/***
 ** @category IOS 通知...
 ** @author qing.yunhui
 ** @email: qingyunhui@smoney.cc
 ** @createTime: 2017年8月31日下午6:00:38
 **/
public class IOSNotifyContent {
	
	 private String alert;          // 必填                    
	 private String badge;         // 可选        
	 private String sound;         // 可选         
	 private String category;      // 可选, 注意: ios8才支持该字段。
	 
	public String getAlert() {
		return alert;
	}
	public void setAlert(String alert) {
		this.alert = alert;
	}
	public String getBadge() {
		return badge;
	}
	public void setBadge(String badge) {
		this.badge = badge;
	}
	public String getSound() {
		return sound;
	}
	public void setSound(String sound) {
		this.sound = sound;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}

}
