package com.adpanshi.cashloan.business.cl.util.youdun;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;


/**
 * 异步通知结果接收接口
 *

 * @date 2017-01-06
 */
public class NotifyResultProcessor {
    /**
     * TODO 获取商户开户的pub_key
     */
    static final String PUB_KEY = "3d0fcd26-bb37-49e8-84bd-e5ecdd8ba9cf";
    /**
     * TODO 获取商户开户的security_key
     */
    static final String SECURITY_KEY = "cab06b8b-cb25-4074-b90c-32ffd478cc70";

    static final String CHARSET_UTF_8 = "UTF-8";
    static final boolean IS_DEBUG = true;

    /**
     * 生成MD5签名
     *
     * @param pub_key          商户公钥
     * @param partner_order_id 商户订单号
     * @param sign_time        签名时间
     * @param security_key     商户私钥
     */
    public static String getMD5Sign(String pub_key, String partner_order_id, String sign_time, String security_key) throws UnsupportedEncodingException {
        String signStr = String.format("pub_key=%s|partner_order_id=%s|sign_time=%s|security_key=%s", pub_key, partner_order_id, sign_time, security_key);
        System.out.println("signField：" + signStr + SECURITY_KEY);
        return MD5Utils.MD5Encrpytion(signStr.getBytes("UTF-8"));
    }

    /**
     * 接收实名认证异步通知
     */
    public void process(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final JSONObject reqObject = getRequestJson(request);

        JSONObject respJson = new JSONObject();
        //验签
        String sign = reqObject.getString("sign");
        String sign_time = reqObject.getString("sign_time");
        String partner_order_id = reqObject.getString("partner_order_id");
        System.out.println("sign：" + sign);
        String signMD5 = getMD5Sign(PUB_KEY, partner_order_id, sign_time, SECURITY_KEY);
        System.out.println("signMD5：" + signMD5);
        if (!sign.equals(signMD5)) {
            System.err.println("异步通知签名错误");
            respJson.put("code", "0");
            respJson.put("message", "签名错误");
        } else {
            System.out.print("收到商户异步通知");
            respJson.put("code", "1");
            respJson.put("message", "收到通知");
            //TODO 异步执行商户自己的业务逻辑(以免阻塞返回导致通知多次)
            Thread asyncThread = new Thread("asyncDataHandlerThread") {
                public void run() {
                    System.out.println("异步执行商户自己的业务逻辑...");
                    try {
                        String id_name = reqObject.getString("id_name");
                        String id_number = reqObject.getString("id_number");
                        System.out.println(id_name + "：" + id_number);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            asyncThread.start();
        }

        System.out.println("返回结果：" + respJson.toJSONString());
        //返回结果
        response.setCharacterEncoding(CHARSET_UTF_8);
        response.setContentType("application/json; charset=utf-8");
        response.getOutputStream().write(respJson.toJSONString().getBytes(CHARSET_UTF_8));
    }

    /**
     * 获取请求json对象
     */
    private JSONObject getRequestJson(HttpServletRequest request) throws IOException {
        InputStream in = request.getInputStream();
        byte[] b = new byte[10240];
        int len;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        while ((len = in.read(b)) > 0) {
            baos.write(b, 0, len);
        }
        String bodyText = new String(baos.toByteArray(), CHARSET_UTF_8);
        JSONObject json = (JSONObject) JSONObject.parse(bodyText);
        if (IS_DEBUG) {
            System.out.println("received notify message:");
            System.out.println(JSON.toJSONString(json, true));
        }
        return json;
    }
}
