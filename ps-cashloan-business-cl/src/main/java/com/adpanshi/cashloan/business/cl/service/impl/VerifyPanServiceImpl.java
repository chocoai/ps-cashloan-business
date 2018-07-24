package com.adpanshi.cashloan.business.cl.service.impl;

import com.adpanshi.cashloan.business.cl.mapper.model.PanVerifyModelMapper;
import com.adpanshi.cashloan.business.cl.model.UserAuthModel;
import com.adpanshi.cashloan.business.cl.service.UserAuthService;
import com.adpanshi.cashloan.business.cl.service.VerifyPanService;
import com.adpanshi.cashloan.business.core.common.context.Global;
import com.adpanshi.cashloan.business.core.common.util.HttpUtils;
import com.adpanshi.cashloan.business.core.common.util.StringUtil;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: fenqidai_dev
 * @description: panNumber verify
 * @author: Mr.Wange
 * @create: 2018-07-05 16:00
 **/
@Service("verifyPanService")
public class VerifyPanServiceImpl implements VerifyPanService {
    /**
     * 日志记录
     */
    private static final Logger logger = LoggerFactory.getLogger(VerifyPanServiceImpl.class);

    @Resource
    private PanVerifyModelMapper panVerifyModelMapper;
    @Resource
    private UserAuthService userAuthService;

    @Override
    public int verifyPanNumber(Map<String, Object> paramMap) {
        int result = 0;
        Map request = panVerifyModelMapper.getPanInfo((String)paramMap.get("panNo"));
        Map<String,Object> map = new HashMap<>(16);
        map.putAll(paramMap);
        if( request == null){
            //API url
            String url = Global.getValue("pan_verify_url");
            //API授权的key
            String key = Global.getValue("pan_verify_key");
            Map sendMap = new HashMap<>(16);
            sendMap.put("key",key);
            sendMap.put("pan_no",paramMap.get("panNo"));
            String returnJson = HttpUtils.doPost(url,sendMap);
            logger.info(returnJson);
            request = JSONObject.parseObject(returnJson);
            map.putAll(request);
        }else{
            logger.info(request.toString());
            map.put("success",request.get("id"));
            map.put("first_name",request.get("first_name"));
            map.put("last_name",request.get("last_name"));
        }
        map.put("createTime",new Date());
        map.put("panAuth","20");

        String panStatusValid = "VALID_PAN";
        String panStatus = "pan_status";
        if(panStatusValid.equals(request.get(panStatus))){
            String firstName = (String)request.get("first_name");
            String lastName = (String)request.get("last_name");
            if(StringUtil.isNotBlank(firstName) && StringUtil.isNotBlank(lastName)){
                if(firstName.toLowerCase().equals(paramMap.get("firstName").toString().toLowerCase())
                        && lastName.toLowerCase().equals(paramMap.get("lastName").toString().toLowerCase())){
                    result = 1;
                    Map<String,Object> returnMap = new HashMap<>(16);
                    returnMap.put("panState", UserAuthModel.STATE_VERIFIED);
                    returnMap.put("userId", paramMap.get("userId"));
                    userAuthService.updateByUserId(returnMap);
                    map.put("panAuth","30");
                }else{
                    map.put("panAuth","10");
                }
            }
        }
        panVerifyModelMapper.save(map);
        return result;
    }

}
