package com.adpanshi.cashloan.core.chuanglan;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.adpanshi.cashloan.core.common.context.Global;
import com.adpanshi.cashloan.core.common.util.HttpsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLEncoder;
import java.util.Map;

public class SmsApi {
    protected static final Logger logger = LoggerFactory.getLogger(SmsApi.class);

    public String sendMsg(Map<String, Object> payload){
        String smsPlatform = payload.get("smsPlatform")!=null? (String) payload.get("smsPlatform") :Global.getValue("sms_platform");
        String areaCode = Global.getValue("sms_area_code");
        String title = Global.getValue("title");
        String message = (String) payload.get("message");
        message = message.replace("{$product}",title);
        String result= null;
        if(smsPlatform==null||smsPlatform.equals("chuanglan")){
            String url =  Global.getValue("sms_chuanglan_url");
            String account = Global.getValue("sms_chuanglan_account");
            String password = Global.getValue("sms_chuanglan_password");
            //组装请求参数
            JSONObject map=new JSONObject();
            map.put("account", account);
            map.put("password", password);
            map.put("msg", message);
            map.put("mobile", areaCode+payload.get("mobile"));
            String params=map.toString();
            logger.info("创蓝请求参数为:" + params);
            try {
                result= HttpsUtil.postStrClient(url,params);
                logger.info("返回参数为:" + result);
                JSONObject jsonObject =  JSON.parseObject(result);
                jsonObject.put("phone",payload.get("mobile"));
                jsonObject.put("orderNo",payload.get("orderNo"));
//            if (jsonObject.getInteger("code")==0){
                jsonObject.put("content",message);
                jsonObject.put("vcode",payload.get("vcode"));
                jsonObject.put("smsPlatform",smsPlatform);
//			}
                result = jsonObject.toJSONString();
                String code = jsonObject.get("code").toString();
                String msgid = jsonObject.get("msgid").toString();
                String error = jsonObject.get("error").toString();
                logger.info("创蓝状态码:" + code + ",状态码说明:" + error + ",消息id:" + msgid);
            } catch (Exception e) {
                // TODO: handle exception
                logger.error("创蓝请求异常：" + e.getMessage());
            }
        }else if(smsPlatform.equals("tianyihong")){
            String account = Global.getValue("sms_tianyihong_account");
            String password = Global.getValue("sms_tianyihong_password");
            String numbers = areaCode+payload.get("mobile");
//            String content = "[Oloan]Your applied loan has been made successfully with the loan amount of 10000.0 rupees and repayment date on Jul 13 2018,please repay on time.";
            //请求url
            String url = Global.getValue("sms_tianyihong_url")+"/sendsms?account="+account+"&password="+password+"&numbers="+numbers+"&content="+ URLEncoder.encode(message);
            logger.info("天一泓请求参数为:" + url);
            result= HttpsUtil.getClient(url).replaceAll("\\[\\[","\"").replaceAll("]]","\"");
            try {
                JSONObject json = JSONObject.parseObject(result);
                if(json.get("status")!=null&&json.getInteger("status")==0){
                    String array = (String) json.get("array");
                    String msgid =array.split(",")[1];
                    logger.info(msgid);
                    json.put("phone",payload.get("mobile"));
                    json.put("orderNo",payload.get("orderNo"));
                    json.put("content",message);
                    json.put("vcode",payload.get("vcode"));
                    json.put("msgid",msgid);
                    json.put("smsPlatform",smsPlatform);
                    result = json.toJSONString();
//                url = "http://sms.skylinelabs.cc:20003/getreport?account="+account+"&password="+password+"&ids="+msgid;
//                String msgResult = HttpsUtil.getClient(url);
//                logger.info("天一泓发送短信结果为:"+msgResult);
                }
                logger.info("天一泓发送短信情况:"+result);
            }catch (Exception e) {
                logger.error("天一泓请求异常：" + e.getMessage());
            }
        }

        return result;
    }
    public static void main(String[] args){
        String account = "18672659901";
        String password = "An5LV5Ea";
        String numbers = "91"+"9560876195";
        String content = "[Oloan]The verification of your card bound is 8289 and 3 minutes valid,Please complete the verification as soon as possible.";
        //组装请求参数
//        JSONObject map=new JSONObject();
//        map.put("account",account);
//        map.put("password",password);
//        map.put("numbers",numbers);
//        map.put("content",content);
//        String params=map.toString();
//        logger.info("请求参数为:" + params);
        //请求url
//        String url = "http://sms.skylinelabs.cc:20003/sendsms?account="+account+"&password="+password+"&numbers="+numbers+"&content="+ URLEncoder.encode(content);
//        String result= null;
//        result= HttpsUtil.getClient(url).replaceAll("\\[\\[","\"").replaceAll("]]","\"");
//        JSONObject json = JSONObject.parseObject(result);
//        if(json.get("status")==0){
//            String array = (String) json.get("array");
//            String msgid =array.split(",")[1];
//            logger.info(msgid);
            String url = "http://sms.skylinelabs.cc:20003/getreport?account="+account+"&password="+password+"&ids=171431";
            String msgResult = HttpsUtil.getClient(url);
            logger.info("发送短信结果为:"+msgResult);
        JSONArray array = (JSONArray) JSONObject.parseObject(msgResult).get("array");
//        }
//        logger.info("发送短信情况:"+result);
    }
}
