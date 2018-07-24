package com.adpanshi.cashloan.business.api.controller;

import com.adpanshi.cashloan.business.cl.service.EsignRecordService;
import com.adpanshi.cashloan.business.core.common.context.Constant;
import com.adpanshi.cashloan.business.core.common.util.ServletUtils;
import com.adpanshi.cashloan.business.core.common.util.StringUtil;
import com.adpanshi.cashloan.business.core.common.web.controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 8470
 * @version 1.0.1
 * @date 2018/07/14 15:44:11
 * @desc 第三方电子签Controller
 * Copyright 浙江盘石 All Rights Reserved
 */

@Controller
@Scope("prototype")
public class EnjoysignController extends BaseController {

    public static final Logger logger = LoggerFactory.getLogger(EnjoysignController.class);

    @Resource
    EsignRecordService esignRecordService;

    /**
     * 用户发起签章请求
     * @param userId
     * @throws Exception
     */
    @RequestMapping(value = "/com/adpanshi/cashloan/api/act/esign/getSign.htm", method = RequestMethod.POST)
    public void getSign(@RequestParam(value = "userId") Long userId) throws Exception {
        logger.info(" 用户发起电子签章请求: userId " + userId);
        Map<String,Object> resultMap = esignRecordService.startSignWithAutoSilentSign(userId);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put(Constant.RESPONSE_DATA, resultMap);
        if(resultMap != null && resultMap.size()>0 && StringUtil.isEmpty(resultMap.get("num"))){
            result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
        }else{
            result.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "查询失败");
        }
        ServletUtils.writeToResponse(response, result);
    }

    /**
     * 用户发起请求获取签章文件
     * @param userId
     * @throws Exception
     */
    @RequestMapping(value = "/com/adpanshi/cashloan/api/act/esign/getSignFile.htm", method = RequestMethod.POST)
    public void getSignFile(@RequestParam(value = "userId") String userId) throws Exception {
        logger.info(" 用户发起获取签章文件请求: userId " + userId);
        Map<String,Object> resultMap = esignRecordService.requestGetEsignFile(Long.valueOf(userId));
        Map<String, Object> result = new HashMap<String, Object>();
        result.put(Constant.RESPONSE_DATA, resultMap);
        if(resultMap!=null && resultMap.size() > 0){
            result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "下载成功");
        }else{
            result.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "下载失败");
        }
        ServletUtils.writeToResponse(response, result);
    }
}
