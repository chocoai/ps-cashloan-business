package com.adpanshi.cashloan.business.api.controller;

import com.adpanshi.cashloan.business.cl.domain.MsgCenter;
import com.adpanshi.cashloan.business.cl.domain.MsgCenterInfoDetail;
import com.adpanshi.cashloan.business.cl.service.MsgCenterService;
import com.adpanshi.cashloan.business.core.common.context.Constant;
import com.adpanshi.cashloan.business.core.common.util.ServletUtils;
import com.adpanshi.cashloan.business.core.common.web.controller.BaseController;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 8470
 * @version 1.0.1
 * @date 2018/06/19 19:32:26
 * @desc 消息中心Controller
 * Copyright 浙江盘石 All Rights Reserved
 */
@Controller
@Scope("prototype")
public class MsgCenterController extends BaseController {

    @Resource
    private MsgCenterService msgCenterService;


    /**
     * 消息信息数量
     */
    @RequestMapping(value = "/com/adpanshi/cashloan/api/act/mine/msgcenter/getMsgNum.htm", method = RequestMethod.POST)
    public void getMsgNum(
            @RequestParam(value="userId") Long userId) throws Exception {
        //根据用户userId获取通知和系统公告数
        int num = msgCenterService.getMsgNum(userId);
        Map<String,Object> result = new HashMap<String,Object>();
        result.put(Constant.RESPONSE_DATA, num);
        result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        result.put(Constant.RESPONSE_CODE_MSG, "获取成功");
        ServletUtils.writeToResponse(response,result);
    }

    /**
     * 消息信息一览
     */
    @RequestMapping(value = "/com/adpanshi/cashloan/api/act/mine/msgcenter/getMsgList.htm", method = RequestMethod.POST)
    public void getMsgList(
            @RequestParam(value="userId") Long userId) throws Exception {
        //根据用户userId获取通知和系统公告一览
        List<MsgCenter> msgCenterList = msgCenterService.getMsgLst(userId);
        Map<String,Object> result = new HashMap<>();
        result.put(Constant.RESPONSE_DATA, msgCenterList);
        result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        result.put(Constant.RESPONSE_CODE_MSG, "获取成功");
        ServletUtils.writeToResponse(response,result);
    }


    /**
     * 消息信息详情
     */
    @RequestMapping(value = "/com/adpanshi/cashloan/api/act/mine/msgcenter/getMsgInfo.htm", method = RequestMethod.POST)
    public void getMsgInfo(
            @RequestParam(value="infoId") Long infoId) throws Exception {
        //根据infoId获取该消息的详细
        List<MsgCenterInfoDetail> msgCenterInfoDetailList = msgCenterService.getMsgInfo(infoId);
        Map<String,Object> result = new HashMap<String,Object>();
        result.put(Constant.RESPONSE_DATA, msgCenterInfoDetailList);
        result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        result.put(Constant.RESPONSE_CODE_MSG, "获取成功");
        ServletUtils.writeToResponse(response,result);
    }
}
