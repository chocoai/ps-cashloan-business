package com.adpanshi.cashloan.business.cl.util;

import com.alibaba.fastjson.JSON;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * 联信工具类
 * Created by cc on 2017-08-31 10:12.
 */
public class LianXinUtil {

    final static String CRLF = "\r\n";

    public static final String LIANXIN_CODE_0 ="0";
    public static final String LIANXIN_CODE_0_MSG ="未规定";

    public static final String LIANXIN_CODE_16 ="16";
    public static final String LIANXIN_CODE_16_MSG ="正常挂断";

    public static final String LIANXIN_CODE_17 ="17";
    public static final String LIANXIN_CODE_17_MSG ="用户忙";

    public static final String LIANXIN_CODE_18 ="18";
    public static final String LIANXIN_CODE_18_MSG ="无用户响应";

    public static final String LIANXIN_CODE_19 ="19";
    public static final String LIANXIN_CODE_19_MSG ="无用户应答";

    public static final String LIANXIN_CODE_20 ="20";
    public static final String LIANXIN_CODE_20_MSG ="用户缺席";

    public static final String LIANXIN_CODE_21 ="21";
    public static final String LIANXIN_CODE_21_MSG ="呼叫拒绝";

    public static final String LIANXIN_CODE_28 ="28";
    public static final String LIANXIN_CODE_28_MSG ="无效的号码格式";

    public static final String LIANXIN_CODE_34 ="34";
    public static final String LIANXIN_CODE_34_MSG ="无可用电路";

    public static final String LIANXIN_CODE_38 ="38";
    public static final String LIANXIN_CODE_38_MSG ="网络失序";
    public static final String LIANXIN_CODE_OHTER_MSG ="其他";





    public static String doApi(Object obj,String host) throws IOException {

        String param = JSON.toJSONString(obj);
        param = URLEncoder.encode(param,"GBK");
        String url = host.replace("USERINFO",param);
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        try {
            HttpURLConnection conn = getHttpConnection(url);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("content-type", "text/xml; charset=GBK");
            conn.connect();

            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "GBK"));
            String line = null;
            while ((line = in.readLine()) != null) {
                result.append(line).append(CRLF);
            }
            conn.disconnect();
        } finally {
            if (in != null) {
                in.close();
            }
        }
        return result.toString();
    }

    /**
     * get http connection
     *
     * @param url
     * @return HttpURLConnection
     * @throws IOException
     */
    private static HttpURLConnection getHttpConnection(String url) throws IOException {
        URL realUrl = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setUseCaches(false);

        return conn;
    }


    /**
     * 转化语音状态
     * @param value
     * @return
     */
    public static String changeValue(String value) {
        switch (value){
            case LIANXIN_CODE_0 :
                return LIANXIN_CODE_0_MSG;
            case LIANXIN_CODE_16 :
                return LIANXIN_CODE_16_MSG;
            case LIANXIN_CODE_17 :
                return LIANXIN_CODE_17_MSG;
            case LIANXIN_CODE_18 :
                return LIANXIN_CODE_18_MSG;
            case LIANXIN_CODE_19 :
                return LIANXIN_CODE_19_MSG;
            case LIANXIN_CODE_20 :
                return LIANXIN_CODE_20_MSG;
            case LIANXIN_CODE_21 :
                return LIANXIN_CODE_21_MSG;
            case LIANXIN_CODE_28 :
                return LIANXIN_CODE_28_MSG;
            case LIANXIN_CODE_34 :
                return LIANXIN_CODE_34_MSG;
            case LIANXIN_CODE_38 :
                return LIANXIN_CODE_38_MSG;
            default :
                return LIANXIN_CODE_OHTER_MSG;
        }
    }
}
