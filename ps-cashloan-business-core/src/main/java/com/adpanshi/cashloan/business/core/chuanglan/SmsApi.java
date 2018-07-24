package com.adpanshi.cashloan.business.core.chuanglan;

import com.adpanshi.cashloan.business.core.common.context.Global;
import com.adpanshi.cashloan.business.core.common.util.HttpsUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class SmsApi {
    protected static final Logger logger = LoggerFactory.getLogger(SmsApi.class);

    public String sendMsg(Map<String, Object> payload){
        String url =  Global.getValue("sms_chuanglan_url");
        String account = Global.getValue("sms_chuanglan_account");
        String password = Global.getValue("sms_chuanglan_password");
        String areaCode = Global.getValue("sms_area_code");
        String title = Global.getValue("title");
        //组装请求参数
        JSONObject map=new JSONObject();
        map.put("account", account);
        map.put("password", password);
        String message = (String) payload.get("message");
        message = message.replace("{$product}",title);
        map.put("msg", message);
        map.put("mobile", areaCode+payload.get("mobile"));
//        map.put("mobile", "918130547663");
//        map.put("mobile", "8618672659901");
        String params=map.toString();
        logger.info("请求参数为:" + params);
        String result= null;
        try {
            result= HttpsUtil.postStrClient(url,params);
            logger.info("返回参数为:" + result);
            JSONObject jsonObject =  JSON.parseObject(result);
            jsonObject.put("phone",payload.get("mobile"));
            jsonObject.put("orderNo",payload.get("orderNo"));
//            if (jsonObject.getInteger("code")==0){
				jsonObject.put("content",message);
				jsonObject.put("vcode",payload.get("vcode"));
//			}
            result = jsonObject.toJSONString();
            String code = jsonObject.get("code").toString();
            String msgid = jsonObject.get("msgid").toString();
            String error = jsonObject.get("error").toString();
            logger.info("状态码:" + code + ",状态码说明:" + error + ",消息id:" + msgid);
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("请求异常：" + e);
        }
        return result;
    }
}
