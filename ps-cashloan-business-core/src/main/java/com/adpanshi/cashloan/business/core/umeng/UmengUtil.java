package com.adpanshi.cashloan.business.core.umeng;/*package com.adpanshi.com.adpanshi.cashloan.com.adpanshi.cashloan.api.core.umeng;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.adpanshi.cashloan.com.adpanshi.cashloan.api.core.common.util.DateUtil;
import com.adpanshi.com.adpanshi.cashloan.com.adpanshi.cashloan.api.core.umeng.beans.AndroidNotifyContent;
import com.adpanshi.com.adpanshi.cashloan.com.adpanshi.cashloan.api.core.umeng.beans.IOSNotifyContent;
import com.adpanshi.com.adpanshi.cashloan.com.adpanshi.cashloan.api.core.umeng.enums.UmEnum;
import com.adpanshi.com.adpanshi.cashloan.com.adpanshi.cashloan.api.core.umeng.enums.UmEnum.AfterOpen;
import com.adpanshi.com.adpanshi.cashloan.com.adpanshi.cashloan.api.core.umeng.enums.UmEnum.DisplayType;

*//***
 ** @category 请用一句话来描述其用途...
 ** @author qing.yunhui
 ** @email: qingyunhui@smoney.cc
 ** @createTime: 2017年8月30日下午4:24:21
 **//*
public class UmengUtil {
	
	
	//android 广播消息
	public static void sendAndroidByBroadcast(){
		PushClient client=new PushClient();
		AndroidNotifyContent body=new AndroidNotifyContent();
		body.setAfter_open(AfterOpen.GO_APP.getValue());
		String datestr=DateUtil.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss");
		body.setText("Android广播-通知文字描述"+datestr);
		body.setTicker("Android广播-通知栏提示文字"+datestr);
		body.setTitle("Android广播-通知标题"+datestr);
		client.sendByAndroid(DisplayType.NOTIFICATION, AfterOpen.GO_APP, UmEnum.Type.BROADCAST, body,null,"这是消息描述");
	}
	
	//android 单播消息
	public static void sendAndroidByUnicast(){
		PushClient client=new PushClient();
		List<String> list=new ArrayList<>();
		list.add("AnIr_F9y18cSauRQ0s0EvYPFFN7St5mhqF8PJtuQf2U5");//ME
		list.add("Amef01jHi4Fj9bKRuMzHKP-gPI6gdK_1CBqnuSqaHlQw");//HD
		String datestr=DateUtil.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss");
		for(int i=0;i<list.size();i++){
			AndroidNotifyContent body=new AndroidNotifyContent();
			body.setAfter_open(AfterOpen.GO_APP.getValue());
			body.setText("Android单播-通知文字描述"+datestr);
			body.setTicker("Android单播-通知栏提示文字"+datestr);
			body.setTitle("Android单播-通知标题"+datestr);
			client.sendByAndroid(DisplayType.NOTIFICATION, AfterOpen.GO_APP, UmEnum.Type.UNICAST, body,list.get(i),"这是消息描述");
		}
	}
	
	//android 列播消息
	public static void sendAndroidByListcast(){
		PushClient client=new PushClient();
		String devicetoken="AnIr_F9y18cSauRQ0s0EvYPFFN7St5mhqF8PJtuQf2U5,Amef01jHi4Fj9bKRuMzHKP-gPI6gdK_1CBqnuSqaHlQw";
		AndroidNotifyContent body=new AndroidNotifyContent();
		body.setAfter_open(AfterOpen.GO_APP.getValue());
		String datestr=DateUtil.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss");
		body.setText("Android列播-通知文字描述"+datestr);
		body.setTicker("Android列播-通知栏提示文字"+datestr);
		body.setTitle("Android列播-通知标题"+datestr);
		client.sendByAndroid(DisplayType.NOTIFICATION, AfterOpen.GO_APP, UmEnum.Type.LISTCAST, body,devicetoken,"这是消息描述");
	}
	
	//IOS 单播
	public static void sendIOSByUnicast(){
		PushClient client=new PushClient();
		String datestr=DateUtil.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss");
		List<String> list=new ArrayList<>();
		list.add("204af5f71a82935b3bd250fbe3ad513f0c42e01a189880c5ee2c1bc76dfac0d3");//ME
		list.add("59cd429fcf10a5ca6640ac77caf64d7d6e36fcd95992bc2139ca68528763e1c4");//YZF
		for(int i=0;i<list.size();i++){
			IOSNotifyContent body=new IOSNotifyContent();
			body.setAlert("IOS单播-测试"+datestr);
			body.setBadge("0");
			body.setSound( "default");
			client.sendByIOS(UmEnum.Type.UNICAST, body,list.get(i),false,"这是消息描述");
		}
	}
	
	//IOS 列播
	public static void sendIOSByListcast(){
		PushClient client=new PushClient();
		String devicetoken ="204af5f71a82935b3bd250fbe3ad513f0c42e01a189880c5ee2c1bc76dfac0d3,59cd429fcf10a5ca6640ac77caf64d7d6e36fcd95992bc2139ca68528763e1c4";
		IOSNotifyContent body=new IOSNotifyContent();
		String datestr=DateUtil.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss");
		body.setAlert("IOS列播-测试"+datestr);
		body.setBadge("0");
		body.setSound( "default");
		client.sendByIOS(UmEnum.Type.LISTCAST, body,devicetoken,false,"这是消息描述");
	}
	
	//IOS 广播
	public static void sendIOSByBroadcast(){
		PushClient client=new PushClient();
		IOSNotifyContent body=new IOSNotifyContent();
		String datestr=DateUtil.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss");
		body.setAlert("IOS广播-测试"+datestr);
		body.setBadge("0");
		body.setSound( "default");
		client.sendByIOS(UmEnum.Type.BROADCAST, body,null,false,"这是消息描述");
	}
	
	

	public static void main(String[] args){
		
		sendIOSByBroadcast();
		sendIOSByUnicast();
		
		sendAndroidByBroadcast();;
		sendAndroidByUnicast();
		
		sendAndroidByListcast();
		sendIOSByListcast();
		
		
		
		//IOS Me toke:204af5f71a82935b3bd250fbe3ad513f0c42e01a189880c5ee2c1bc76dfac0d3
		//IOS YBF toke:59cd429fcf10a5ca6640ac77caf64d7d6e36fcd95992bc2139ca68528763e1c4
		
		
		
	}
	
}
*/