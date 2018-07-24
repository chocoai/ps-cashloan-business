package com.adpanshi.cashloan.business.rule.tongdun;

/**
 * @author 8470
 * @version 1.0.1
 * @date 2018/06/23 14:26:26
 * @desc 同盾信贷保镖API
 * Copyright 浙江盘石 All Rights Reserved
 */

import com.adpanshi.cashloan.business.core.common.util.HttpsUtil;
import com.adpanshi.cashloan.business.rc.domain.TppBusiness;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Map;

public class BodyGuardApi {

    private static final Log logger = LogFactory.getLog(BodyGuardApi.class);

    public String request(Map<String, String> requestParams, TppBusiness business, boolean testState, String mobileType){
        JSONObject extend = JSONObject.parseObject(business.getExtend());
        if(extend == null){
            logger.error("BodyGuardApi-request()-获取参数错误：business.getExtend()为空!");
            return null;
        }
        if(!"TongdunBodyGuardRisk".equals(business.getNid())){
            logger.error("BodyGuardApi-request()-获取参数TppBusiness不正确-TppBusiness："+JSONObject.toJSONString(business));
            return null;
        }
        String reqUrl = "";
        String result = null;
        String partnerKey = extend.getString("partner_key");
        String partnerCode = extend.getString("partner_code");
        String appName = extend.getString("app_name");
        JSONObject names = JSONObject.parseObject(appName);
        switch (mobileType){
            case "2":
                appName=names.getString("and");
                break;
            case "1":
                appName=names.getString("ios");
                break;
            default:
                appName=names.getString("web");
                break;
        }

        //测试环境和生产环境URL区分
        if(testState){
            reqUrl = new StringBuilder().append(business.getTestUrl()).append("?partner_code=").append(partnerCode).append("&partner_key=").append(partnerKey).append("&app_name=").append(appName).toString();
            logger.info("测试环境:"+reqUrl);
        }else{
            reqUrl = new StringBuilder().append(business.getUrl()).append("?partner_code=").append(partnerCode).append("&partner_key=").append(partnerKey).append("&app_name=").append(appName).toString();
            logger.info("生产环境:"+reqUrl);
        }

        try{
            result = HttpsUtil.postClient(reqUrl,requestParams);
        }catch(Exception e){
            logger.error("BodyGuardApi.request()=>detailException："+e);
        }

        return result;
    }
}
