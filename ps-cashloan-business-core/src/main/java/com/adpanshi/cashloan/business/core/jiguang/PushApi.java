package com.adpanshi.cashloan.business.core.jiguang;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.DeviceType;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import com.adpanshi.cashloan.business.core.common.context.Global;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 极光推送API
 * @author hjuangqin
 * @since 2018-01-09 21:15:33
 * */
public class PushApi {

    protected static final Logger LOG = LoggerFactory.getLogger(PushApi.class);

    protected static final String APP_KEY = Global.getValue("jpush_app_key");
    protected static final String MASTER_SECRET = Global.getValue("jpush_master_secret");

    /**
     * 极光推送
     *
     * @param title    String 推送的标题
     * @param text     String 推送的内容
     * @param tokens   List<String>推送的设备标识集合
     * @param platform Platform 推送的平台：
     *                 Platform.all()：所有平台，包括android、ios、winphone
     *                 Platform.android()：仅android平台
     *                 Platform.ios()：仅ios平台
     *                 Platform.winphone()：仅winphone平台
     *                 Platform.android_ios()：android和ios平台
     *                 Platform.android_winphone()：android和winphone平台
     *                 Platform.ios_winphone()：ios和winphone平台
     * @return Map<String, Object>
     *      success：true 无异常，false 异常
     *      code：	 http返回码，200代表成功
     *      data：	 推送返回的消息
     */
    public Map<String, Object> sendPushByRegistrationId(String title, String text, List<String> tokens, Platform platform) {
        //返回map
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("success", false);
        JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY, null, ClientConfig.getInstance());
        try {
            //构建推送消息体text
            PushPayload payload = buildPushObjectByRegistrationId(text, title, tokens, platform);
            //推送消息
            PushResult result = jpushClient.sendPush(payload);
            //请求结束后，调用 NettyHttpClient 中的 close 方法，否则进程不会退出。
            jpushClient.close();
            resultMap.put("success", true);
            resultMap.put("code", result.getResponseCode());
            resultMap.put("data", result.getOriginalContent());
        } catch (APIConnectionException e) {
            // Connection error, should retry later
            LOG.error("Connection error, should retry later", e);
            resultMap.put("code", HttpStatus.SC_EXPECTATION_FAILED);
            resultMap.put("data", "doneRetriedTimes :" + e.getDoneRetriedTimes() + "/" + e.getMessage());
        } catch (APIRequestException e) {
            // Should review the error, and fix the request
            LOG.error("Should review the error, and fix the request", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
            resultMap.put("code", e.getStatus());
            resultMap.put("data", e.getErrorCode() + ": " + e.getErrorMessage());
        }
        return resultMap;
    }


    /**
     * 构建极光推送对象
     *
     * @param title    String 推送的标题
     * @param text     String 推送的内容
     * @param tokens   List<String>推送的设备标识集合
     * @param platform Platform 推送的平台
     * @return PushPayload
     */
    private static PushPayload buildPushObjectByRegistrationId(String text,String title,List<String> tokens, Platform platform) {
        Notification notification=null;
        String deviceType=getPlatformDeviceType(platform);
        if(DeviceType.Android.value().equals(deviceType)){
            notification=buildAndroidNotification(text, title);
        }else if(DeviceType.IOS.value().equals(deviceType)){
            notification=buildIOSNotification(text);
        }
        PushPayload pushPayload = PushPayload.newBuilder()
                .setPlatform(platform)
                .setAudience(Audience.registrationId(tokens))
                .setOptions(Options.newBuilder().setApnsProduction(
                        "dev".equals(Global.getValue("app_environment")) ? false : true).build())//false为开发环境，true为生产环境
                .setNotification(notification)
                .build();
        return pushPayload;
    }

    /**
     * 构建安卓通知Notification
     * @param text
     * @param title
     * @return Notification
     * */
    private static Notification buildAndroidNotification(String text,String title){
        return Notification.newBuilder()
                .addPlatformNotification(AndroidNotification.newBuilder()
                        .setAlert(text)
                        .setBigPicPath("path to big picture")
                        .setBigText(text)
                        .setBuilderId(1)
                        .setCategory("CATEGORY_SOCIAL")
                        .setStyle(1)
                        .setTitle(title)
                        .setPriority(1)
                        .build())
                .build();
    }

    /**
     * 构建IOS通知Notification
     * @param text
     * @return Notification
     * */
    private static Notification buildIOSNotification(String text){
        return Notification.newBuilder()
                .addPlatformNotification(IosNotification.newBuilder()
                .setAlert(text)
                .setSound("")
                .setContentAvailable(true)
                .build()
                ).build();
    }

    /**
     * 根据Platform获取设备类型
     * @param platform
     * @return String
     * */
    private static String getPlatformDeviceType(Platform platform){
        JsonElement jsonElement=platform.toJSON();
        JsonArray JsonArray=jsonElement.getAsJsonArray();
        Iterator<JsonElement> iterator=JsonArray.iterator();
        String value=null;
        while (iterator.hasNext()){
            JsonElement element=iterator.next();
            value=element.getAsString();
        }
        return value;
    }

}
