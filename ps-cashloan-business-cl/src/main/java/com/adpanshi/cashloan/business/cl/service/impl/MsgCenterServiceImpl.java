package com.adpanshi.cashloan.business.cl.service.impl;

import com.adpanshi.cashloan.business.cl.domain.MsgCenter;
import com.adpanshi.cashloan.business.cl.domain.MsgCenterInfo;
import com.adpanshi.cashloan.business.cl.domain.MsgCenterInfoDetail;
import com.adpanshi.cashloan.business.cl.mapper.MsgCenterInfoDetailMapper;
import com.adpanshi.cashloan.business.cl.mapper.MsgCenterInfoMapper;
import com.adpanshi.cashloan.business.cl.service.MsgCenterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 8470
 * @version 1.0.1
 * @date 2018/06/19 19:54:08
 * @desc 消息中心实现类
 * Copyright 浙江盘石 All Rights Reserved
 */

@Service("msgCenterService")
public class MsgCenterServiceImpl implements MsgCenterService {

    private static final Logger logger = LoggerFactory.getLogger(MsgCenterServiceImpl.class);

    @Resource
    private MsgCenterInfoMapper msgCenterInfoMapper;

    @Resource
    private MsgCenterInfoDetailMapper msgCenterInfoDetailMapper;

    @Override
    public int getMsgNum(Long userId){
        //根据参数获得系统消息和APP消息
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("userId",userId);
        params.put("msgType","30");
        int num = msgCenterInfoMapper.getMsgCount(params);
        return num;
    }

    @Override
    public List<MsgCenter> getMsgLst(Long userId){
        logger.info("获取用户消息中心userId:"+userId);
        //根据参数获得系统消息和APP消息
        List<MsgCenter> msgCenterList = new ArrayList<MsgCenter>();
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("userId",userId);
        params.put("msgType","30");
        List<MsgCenterInfo> msgCenterInfoLst = msgCenterInfoMapper.getMsgCenterInfoLst(params);
        if(msgCenterInfoLst.size()>0){
            List<Long> infoIdLst = new ArrayList<Long>();
            for(MsgCenterInfo msgCenterInfo:msgCenterInfoLst){
                infoIdLst.add(msgCenterInfo.getId());
            }
            //根据Id获得消息详细
            params.clear();
            params.put("infoIdLst",infoIdLst);
            msgCenterList = msgCenterInfoDetailMapper.getDetailByInfoId(params);
        }
        return msgCenterList;
    }

    @Override
    public List<MsgCenterInfoDetail> getMsgInfo (Long infoId){
        //根据infoId获取消息的详情
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("infoId",infoId);
        List<MsgCenterInfoDetail> msgCenterInfoDetailList = msgCenterInfoDetailMapper.listSelective(params);
        return msgCenterInfoDetailList;
    }
}
