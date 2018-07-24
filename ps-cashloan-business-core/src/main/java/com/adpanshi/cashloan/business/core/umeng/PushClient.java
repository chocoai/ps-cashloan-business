package com.adpanshi.cashloan.business.core.umeng;

import com.adpanshi.cashloan.business.core.common.context.Global;
import com.adpanshi.cashloan.business.core.common.enums.SwitchEnum;
import com.adpanshi.cashloan.business.core.common.util.DateUtil;
import com.adpanshi.cashloan.business.core.umeng.beans.*;
import com.adpanshi.cashloan.business.core.umeng.constants.UmConstant;
import com.adpanshi.cashloan.business.core.umeng.enums.UmEnum;
import com.adpanshi.cashloan.business.core.umeng.enums.UmEnum.*;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/***
 ** @category 请用一句话来描述其用途...
 ** @author qing.yunhui
 ** @email: qingyunhui@smoney.cc
 ** @createTime: 2017年8月31日上午10:40:34
 **/
@SuppressWarnings("deprecation")
public class PushClient {
	
	Logger logger =LoggerFactory.getLogger(getClass());
	
	// The user agent
	protected final String USER_AGENT = "Mozilla/5.0";

	// This object is used for sending the post request to Umeng
	protected HttpClient client = new DefaultHttpClient();
	
	
	/**
	 * <p>向IOS设备推送消息(默认以单播方式推送，默认发送正式环境)</p>
     * @param body 消息体
	 * @param devicetoken 用户手机唯一标识,可选项(当Type为unicast(单播)、listcast(列播)时,其值必填)
	 * @param description 消息描述
	 * @return boolean 发送成功标志
	 * */
	public boolean sendByIOS(IOSNotifyContent body, String devicetoken, String description){
		return (Boolean)sendByIOS(Type.UNICAST, body, devicetoken,description).get("success");
	}
	
	/**
	 * <p>向IOS设备推送消息</p>
	 * @param msgSendType 消息发送类型 其值可以为:
          <p>unicast-单播</p>
          <p>listcast-列播(要求不超过500个device_token)</p>
          <p>filecast-文件播 (多个device_token可通过文件形式批量发送）</p>
          <p>broadcast-广播</p>
          <p>groupcast-组播(按照filter条件筛选特定用户群, 具体请参照filter参数)</p>
          <p>customizedcast(通过开发者自有的alias进行推送)包括两种case(alias:对单个或者多个alias进行推送,file_id:将alias存放到文件后,根据file_id来推送)</p>
     * @param body 消息体
	 * @param devicetoken 用户手机唯一标识,可选项(当Type为unicast(单播)、listcast(列播)时,其值必填)
	 * @param description 消息描述
	 * @return boolean 发送成功标志
	 * */
	public Map<String,Object> sendByIOS(Type msgSendType,IOSNotifyContent body,String devicetoken,String description){
		boolean flag=false;
		Map<String,Object> resultMap=new HashMap<String,Object>();
		resultMap.put("success",false);
		if(!isIOSPerm()){
			logger.error("---------->开关未启用(请检查相应的开关是否都已开启),消息停止推送.");
			return resultMap;
		}
		boolean productionMode=Boolean.FALSE;
		String iosSwitch=Global.getValue(UmConstant.IOS_SWITCH);
		if(StringUtils.isNotEmpty(iosSwitch) && iosSwitch.equals(SwitchEnum.ON.getCode())){
			productionMode=Boolean.TRUE;
		}
		DataStruct data=new DataStruct();
		data.setDescription(description);
		data.setPolicy(new Policy(DateUtil.getDate(1)));// TODO 失效时间 默认1天
		data.setType(msgSendType.getValue());
		data.setTimestamp(System.currentTimeMillis()+"");
		data.setAppkey(Global.getValue(UmEnum.IOSAppKey.KEY_CODE.getCode()));
		data.setProduction_mode(productionMode);
		Payload payload=new Payload();
//		payload.setExtra(new Extra("testZS", "张三"));
		payload.setAps(body);
		data.setPayload(payload);
		if(Type.UNICAST.getValue().equals(msgSendType.getValue()) || Type.LISTCAST.getValue().equals(msgSendType.getValue())){
			if(StringUtils.isBlank(devicetoken)){
				logger.info("--------------->消息类型为单播或列播时，用户手机唯一标识必填.");
				return resultMap;
			}
			data.setDevice_tokens(devicetoken);//单播
		}else {}
		String host=Global.getValue(UmEnum.UM_HOST_CODE);
		String sendPath=Global.getValue(UmEnum.SEND_PATH_CODE);
		PushClient client=new PushClient();
		try {
			resultMap=client.send(data, host+sendPath,Global.getValue(UmEnum.IOSAppKey.MASTER_SECRET_CODE.getCode()));
		} catch (Exception e) {
			logger.error("",e);
			resultMap.put("success",false);
		}
		return resultMap;
	}
	
	/**
	 * <p>向Android设备推送消息(默认消息类型为通知,默认打开通知后打开app,默认以列播的形式推送消息)</p>
     * @param body 消息体
	 * @param devicetoken 用户手机唯一标识,可选项(当Type为unicast(单播)、listcast(列播)时列播时(列播,多个devicetoken以逗号隔开),其值必填)
	 * @param description 消息描述
	 * @return boolean 发送成功标志
	 * */
	public boolean sendByAndroid(AndroidNotifyContent body,String devicetoken,String description){
		return (Boolean) sendByAndroid(DisplayType.NOTIFICATION, AfterOpen.GO_APP, Type.LISTCAST, body, devicetoken,description).get("success");
	}
	
	/**
	 * <p>向Android设备推送消息</p>
	 * @param msgSendType 消息发送类型 其值可以为:
          <p>unicast-单播</p>
          <p>listcast-列播(要求不超过500个device_token)</p>
          <p>filecast-文件播 (多个device_token可通过文件形式批量发送）</p>
          <p>broadcast-广播</p>
          <p>groupcast-组播(按照filter条件筛选特定用户群, 具体请参照filter参数)</p>
          <p>customizedcast(通过开发者自有的alias进行推送)包括两种case(alias:对单个或者多个alias进行推送,file_id:将alias存放到文件后,根据file_id来推送)</p>
     * @param body 消息体
	 * @param devicetoken 用户手机唯一标识,可选项(当Type为unicast(单播)、listcast(列播)时列播时(列播,多个devicetoken以逗号隔开),其值必填)
	 * @param description 消息描述
	 * @return boolean 发送成功标志
	 * */
	public boolean sendByAndroid(Type msgSendType,AndroidNotifyContent body,String devicetoken,String description){
		return (Boolean) sendByAndroid(DisplayType.NOTIFICATION, AfterOpen.GO_APP, msgSendType, body, devicetoken,description).get("success");
	}
	
	/**
	 * <p>向Android设备推送消息</p>
	 * @param displayType 消息类型(notification:通知，message:消息)
	 * @param afterOpen 点击"通知"的后续行为，默认为打开app，其值可以为(go_app:打开应用,go_url:跳转到URL,go_activity:打开特定的activity,go_custom:用户自定义内容)
	 * @param msgSendType 消息发送类型 其值可以为:
          <p>unicast-单播</p>
          <p>listcast-列播(要求不超过500个device_token)</p>
          <p>filecast-文件播 (多个device_token可通过文件形式批量发送）</p>
          <p>broadcast-广播</p>
          <p>groupcast-组播(按照filter条件筛选特定用户群, 具体请参照filter参数)</p>
          <p>customizedcast(通过开发者自有的alias进行推送)包括两种case(alias:对单个或者多个alias进行推送,file_id:将alias存放到文件后,根据file_id来推送)</p>
     * @param body 消息体
	 * @param devicetoken 用户手机唯一标识,可选项(当Type为unicast(单播)、listcast(列播)时列播时(列播,多个devicetoken以逗号隔开),其值必填)
	 * @param description 消息描述
	 * @return boolean 发送成功标志
	 * */
	public Map<String,Object> sendByAndroid(DisplayType displayType, AfterOpen afterOpen, Type msgSendType, AndroidNotifyContent body, String devicetoken, String description){
		boolean flag=false;
		Map<String,Object> resultMap=new HashMap<String,Object>();
		resultMap.put("success",false);
		if(!isAndroidPerm()){
			logger.error("---------->开关未启用(请检查相应的开关是否都已开启),消息停止推送.");
			return resultMap;
		}
		DataStruct data=new DataStruct();
		data.setType(msgSendType.getValue());
		data.setTimestamp(System.currentTimeMillis()+"");
		data.setAppkey(Global.getValue(UmEnum.AndroidAppKey.KEY_CODE.getCode()));
		data.setDescription(description);
		data.setPolicy(new Policy(DateUtil.getDate(1)));//消息过期时间默认为1天
		Payload payload=new Payload();
		payload.setDisplay_type(displayType.getValue());
		body.setAfter_open(afterOpen.getValue());
//		payload.setExtra(new Extra("testZS", "张三"));
		payload.setBody(body);
		data.setPayload(payload);
		if(Type.UNICAST.getValue().equals(msgSendType.getValue()) || Type.LISTCAST.getValue().equals(msgSendType.getValue())){
			if(StringUtils.isBlank(devicetoken)){
				logger.info("------------->消息类型为单播或列播时，用户手机唯一标识必填.");
				return resultMap;
			}
			data.setDevice_tokens(devicetoken);
		}else {}
		if(displayType.getValue().equals(DisplayType.MESSAGE.getValue())){
			if(StringUtils.isBlank(body.getCustom())){
				logger.info("------------->display_type为message时，请求参数custom必填.");
				return resultMap;
			}
		}
		PushClient client=new PushClient();
		String host=Global.getValue(UmEnum.UM_HOST_CODE);
		String sendPath=Global.getValue(UmEnum.SEND_PATH_CODE);
		try {
			resultMap=client.send(data, host+sendPath, Global.getValue(UmEnum.AndroidAppKey.MASTER_SECRET_CODE.getCode()));
		} catch (Exception e) {
			logger.info("",e);
			resultMap.put("success",false);
		}
		return resultMap;
	}
	
	/**
	 * @param data 消息体
	 * @param url 请求的url
	 * @param appMasterSecret 
	 * @return boolean
	 * */
	private  Map<String,Object> send(DataStruct data,String url,String appMasterSecret) throws Exception {
		Map<String,Object> resultMap=new HashMap<String,Object>();
		resultMap.put("success",false);
        String postBody = JSONObject.toJSONString(data);
        String sign = DigestUtils.md5Hex(("POST" + url + postBody + appMasterSecret).getBytes("utf8"));
        url = url + "?sign=" + sign;
        HttpPost post = new HttpPost(url);
        post.setHeader("User-Agent", USER_AGENT);
        StringEntity entity = new StringEntity(postBody, "UTF-8");
        post.setEntity(entity);
        // Send the post request and get the response
        HttpResponse response = client.execute(post);
        int status = response.getStatusLine().getStatusCode();
        logger.info("Response Code : " + status);
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        logger.info(result.toString());
        if (status == 200) {
            logger.info("Notification sent successfully.");
        } else { 
            logger.info("Failed to send the notification!");
        }
		resultMap.put("code",status);
		resultMap.put("data",result);
		resultMap.put("success",true);
        return resultMap;
    }
	
	private boolean isIOSPerm(){
		IOSAppKey[] iosList=UmEnum.IOSAppKey.values();
		for(IOSAppKey ios:iosList){
			String value=Global.getValue(ios.getCode());
			if(StringUtils.isBlank(value)){
				logger.error("---------->>> code={}的配置,不存在或未启用。", new Object[]{ios.getCode()});
				return Boolean.FALSE;
			}
		}
		return isPerm();
	}
	
	/**
	 * @return Boolean
	 * */
	private boolean isAndroidPerm(){
		AndroidAppKey[] androids=UmEnum.AndroidAppKey.values();
		for(AndroidAppKey android:androids){
			String value=Global.getValue(android.getCode());
			if(StringUtils.isBlank(value)){
				logger.error("---------->>> code={}的配置,不存在或未启用。", new Object[]{android.getCode()});
				return Boolean.FALSE;
			}
		}
		return isPerm();
	}
	
	private boolean isPerm(){
		Boolean isPerm=Boolean.TRUE;
		String host=Global.getValue(UmEnum.UM_HOST_CODE);
		String sendPath=Global.getValue(UmEnum.SEND_PATH_CODE);
		if(StringUtils.isBlank(host)){
			logger.error("---------->>> code={}的配置,不存在或未启用。", new Object[]{UmEnum.UM_HOST_CODE});
			return Boolean.FALSE;
		}
		if(StringUtils.isBlank(sendPath)){
			logger.error("---------->>> code={}的配置,不存在或未启用。", new Object[]{UmEnum.SEND_PATH_CODE});
			return Boolean.FALSE;
		}
		return isPerm;
	}

}
