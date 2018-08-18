package com.adpanshi.cashloan.rule.service.impl;

import com.adpanshi.cashloan.core.common.context.Global;
import com.adpanshi.cashloan.core.domain.UserBaseInfo;
import com.adpanshi.cashloan.core.mapper.UserBaseInfoMapper;
import com.adpanshi.cashloan.rc.domain.TppBusiness;
import com.adpanshi.cashloan.rc.mapper.TppBusinessMapper;
import com.adpanshi.cashloan.rule.domain.TDBodyGuardLog;
import com.adpanshi.cashloan.rule.mapper.TDBodyGuardLogMapper;
import com.adpanshi.cashloan.rule.service.TDBodyGuardService;
import com.adpanshi.cashloan.rule.tongdun.BodyGuardApi;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.httpclient.URIException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 8470
 * @version 1.0.0
 * @date 2018/06/25 10:50:02
 * @desc 风控-同盾信贷保镖
 * Copyright 浙江盘石 All Rights Reserved
 */
@Service("TDBodyGuardService")
public class TDBodyGuardServiceImpl implements TDBodyGuardService {

    final Logger logger = LoggerFactory.getLogger(TDBodyGuardServiceImpl.class);

    @Resource
    private TppBusinessMapper tppBusinessMapper;

    @Resource
    private TDBodyGuardLogMapper tdBodyGuardLogMapper;

    @Resource
    private UserBaseInfoMapper userBaseInfoMapper;


    /**
     * 同盾信贷保镖认证
     * @param userId 用户id
     * @param mobileType 手机类型
     * @throws URIException
     */
    @Override
    public String tdBodyGuardLogin(String userId, String mobileType){
        String result = null;
        String finalResult = null;
        Boolean testState = Boolean.TRUE;
        BodyGuardApi bodyGuardApi = new BodyGuardApi();
        Map<String,String> params = new HashMap<String,String>();

        try{
            //根据用户userId获取用户信息
            UserBaseInfo userBaseInfo = userBaseInfoMapper.findByUserId(Long.valueOf(userId));
            if(userBaseInfo !=null){
                //拼装postData参数,后期可能要追加其他参数
                params.put("ina_account_mobile",userBaseInfo.getPhone());
                params.put("ina_id_number",userBaseInfo.getIdNo());
                params.put("account_name",userBaseInfo.getRealName());
            }else{
                logger.info("同盾信贷保镖调用失败==用户身份认证未完成:"+userId);
                return null;
            }

            TppBusiness tppBusiness = tppBusinessMapper.findByNid("TongdunBodyGuardRisk", 3L);
            //判断是否是测试环境
            if(!"dev".equals(Global.getValue("app_environment"))){
                testState = Boolean.FALSE;
            }

            result = bodyGuardApi.request(params,tppBusiness,testState,mobileType);
        }catch(Exception e){
            logger.error("调用信贷保镖接口失败------",e);
        }

        TDBodyGuardLog log = new TDBodyGuardLog();
        log.setUserId(Long.valueOf(userId));
        log.setParams(params.toString());
        log.setResult(result);
        //保存信贷保镖请求返回数据
        int num = tdBodyGuardLogMapper.save(log);
        if(num > 0){
            logger.info("同盾信贷保镖数据保存成功userId="+userId);
        }
        //简单解析同盾保镖返回值
        Map<String,Object> resultMap = JSONObject.parseObject(result,Map.class);
        if(Boolean.valueOf(resultMap.get("success").toString()) && resultMap.get("reason_code")==null){
            finalResult = "success";
        }

        return finalResult;
    }

    /**
     * 同盾信贷保镖拉取报告
     * @param userId 用户id
     * @param orderId 订单号
     * 拉取状态。200：成功，500：异常， other：同盾状态
     */
    @Override
    public String tryGetReport(String userId, String orderId){

        return "";
    }
}
