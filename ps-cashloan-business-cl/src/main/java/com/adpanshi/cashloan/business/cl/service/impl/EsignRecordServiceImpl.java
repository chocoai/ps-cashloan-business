package com.adpanshi.cashloan.business.cl.service.impl;

import com.adpanshi.cashloan.business.cl.domain.EnjoysignDownloadLog;
import com.adpanshi.cashloan.business.cl.domain.EnjoysignRecord;
import com.adpanshi.cashloan.business.cl.mapper.EnjoysignDownloadLogMapper;
import com.adpanshi.cashloan.business.cl.mapper.EnjoysignRecordMapper;
import com.adpanshi.cashloan.business.cl.model.EnjoysignRecordModel;
import com.adpanshi.cashloan.business.cl.service.EsignRecordService;
import com.adpanshi.cashloan.business.cl.util.DateFormatUtil;
import com.adpanshi.cashloan.business.core.common.context.Global;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.service.impl.BaseServiceImpl;
import com.adpanshi.cashloan.business.core.common.util.DateUtil;
import com.adpanshi.cashloan.business.core.common.util.HttpsUtil;
import com.adpanshi.cashloan.business.core.common.util.StringUtil;
import com.adpanshi.cashloan.business.core.common.util.base64.Base64Util;
import com.adpanshi.cashloan.business.core.enjoysign.EnjoysignUtil;
import com.adpanshi.cashloan.business.core.enjoysign.enums.StatusEnum;
import com.adpanshi.cashloan.business.core.mapper.BorrowMainMapper;
import com.adpanshi.cashloan.business.core.mapper.UserBaseInfoMapper;
import com.adpanshi.cashloan.business.core.model.ManagerUserModel;
import com.adpanshi.cashloan.business.rule.mapper.ClBorrowMapper;
import com.adpanshi.cashloan.business.system.domain.SysAttachment;
import com.adpanshi.cashloan.business.system.mapper.SysAttachmentMapper;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 8470
 * @version 1.0.1
 * @date 2018/07/13 19:11:15
 * @desc e-sign电子签实现类
 * Copyright 浙江盘石 All Rights Reserved
 */
@Service("EsignRecordService")
public class EsignRecordServiceImpl extends BaseServiceImpl<EnjoysignRecord,Long> implements EsignRecordService {

    Logger logger=LoggerFactory.getLogger(getClass());

    @Resource
    BorrowMainMapper borrowMainMapper;

    @Resource
    ClBorrowMapper clBorrowMapper;

    @Resource
    UserBaseInfoMapper userBaseInfoMapper;

    @Resource
    EnjoysignRecordMapper enjoysignRecordMapper;

    @Resource
    SysAttachmentMapper sysAttachmentMapper;

    @Resource
    EnjoysignDownloadLogMapper enjoysignDownloadLogMapper;

    @Override
    public BaseMapper<EnjoysignRecord, Long> getMapper() {
        return enjoysignRecordMapper;
    }

    /**
     * 发送电子签章请求
     * @param userId
     * @return
     */
    @Override
    public Map<String,Object> startSignWithAutoSilentSign(Long userId) {
        Map<String,Object> resultMap = new HashMap<>();
        //发起签章之前确认用户已经借款且通过审核
        boolean swithOpen=EnjoysignUtil.isSwithOpen();
        if(!swithOpen){
            return null;
        }
        //获取签章所需的参数值
        String esign_key = Global.getValue("esign_key");
        String eSign_txd_id = Global.getValue("esign_txd_id");
        String esign_url = Global.getValue("esign_url");
        int esign_times = Global.getInt("esign_times");
        //获取用户的全名/邮箱号
        ManagerUserModel managerUserModel = userBaseInfoMapper.getBaseModelByUserId(userId);
        if(StringUtil.isEmpty(esign_key,eSign_txd_id,esign_url)) {
            logger.info("--------------->签章前传入的第三方参数或者URL不能为空");
            return null;
        }
        try {
            //查询这个借款用户是否已经签署过
            EnjoysignRecordModel iExist= enjoysignRecordMapper.findSignAttachment(userId);
            if(null==iExist){
                //查看用户当天认证次数
                String date = DateFormatUtil.formatDate(DateUtil.getNow(),DateUtil.DATEFORMAT_STR_002);
                Map<String,Object> signCountMap = enjoysignRecordMapper.findSignCount(date,userId);
                if(signCountMap!=null && signCountMap.size()>0){
                    //获取认证次数
                    if(Integer.valueOf(signCountMap.get("num").toString()) > esign_times){
                        logger.info("--------------->当天发起电子签次数已超过规定次数");
                        resultMap.put("num",signCountMap.get("num"));
                        return resultMap;
                    }
                }
                //拼装参数
                Map<String,String> param = new HashMap<>();
                //获取服务器上的文件
                byte[] fileByte = EnjoysignUtil.getFileByType("/data/htdocs/pdf/sample.pdf");
                if(StringUtil.isNotEmptys(fileByte)){
                    //对文件进行加密处理
                    String b64Doc = Base64Util.base64Encode(fileByte);
                    param.put("b64Doc",b64Doc);
                }else{
                    logger.info("-------->读取签章合同异常或没有找到签章合同");
                    return null;
                }
                param.put("key",esign_key);
                param.put("fullname",managerUserModel.getRealName());
                param.put("email",managerUserModel.getLoginNameEmail());
                param.put("txd_id",eSign_txd_id);
                //发送请求
                String result = HttpsUtil.postClient(esign_url,param);
                //解析返回数据
                if(result!=null){
                    EnjoysignRecord  record = new EnjoysignRecord();
                    record.setBorId(userId);
                    record.setResParameter(result);
                    record.setStatus(10);
                    record.setBorName(managerUserModel.getRealName());
                    Map<String,Object> reMap = JSONObject.parseObject(result,Map.class);
                    String status = reMap.get("status").toString();
                    //请求成功的场合
                    if(!status.equals("success")){
                        //状态10：发起签章成功/20：发起签章失败
                        record.setStatus(20);
                        //保存请求记录
                        enjoysignRecordMapper.save(record);
                        return null;
                    }
                    String signInfo = reMap.get("signer_info").toString();
                    Map<String,Object> signInfoMap = JSONObject.parseObject(signInfo,Map.class);
                    //解析数据并返回该数据
                    resultMap.put("docket_id",reMap.get("docket_id"));
                    resultMap.put("txd_id",eSign_txd_id);
                    resultMap.put("document_id",signInfoMap.get("document_id"));
                    resultMap.put("signer_id",signInfoMap.get("signer_id"));
                    //保存请求记录
                    enjoysignRecordMapper.save(record);
                }
            }else {
                if (StatusEnum.SUCCESS.getKey().equals(iExist.getStatus())
                        || StatusEnum.DOWNLOAD_FAIL.getKey().equals(iExist.getStatus())
                        || StatusEnum.DOWNLOAD_SUCCESS.getKey().equals(iExist.getStatus())
                        ) {
                    logger.info("------>用户已存在电子签请勿重复签署...userId="+userId);
                    return null;
                }
            }
        } catch (Exception e) {
            logger.error("------>调用自动签章接口出错，方法名:startSignWithAutoSilentSign出错原因:",e);
        }
        return resultMap;
    }

    /***
     * 电子签章：请求第三方服务，保存签章文件
     * 用户申请借款之后且订单
     * @param userId
     * @return String SignatureStatus
     * */
    @Override
    public Map<String,Object> requestGetEsignFile(Long userId){
        Map<String,Object> resultMap = new HashMap<>();
        String documentId = null;
        //获取URL地址
        String url = Global.getValue("esign_url_doc");
        String key = Global.getValue("esign_key_doc");
        String txd_id = Global.getValue("esign_txd_id");
        //根据用户id/订单id查询签章记录表
        Map<String,Object> params = new HashMap<>();
        params.put("userId",userId);
        EnjoysignRecord enjoysignRecord = enjoysignRecordMapper.findSelective(params);

        if(enjoysignRecord!=null && enjoysignRecord.getStatus()==10){
            //判断用户是本地下载还是远程下载
            params.put("targetId",enjoysignRecord.getId());
            SysAttachment sysAttachment = sysAttachmentMapper.findSelective(params);
            if(sysAttachment!=null){
                resultMap.put("file_path",sysAttachment.getFilePath());
                resultMap.put("origin_name",sysAttachment.getOriginName());
                //如果存在则直接返回结果
                return resultMap;
            }
            try{
                Map<String,String> param = new HashMap<>();
                //如果存在签章记录且状态为成功(3)，第二步第三步的数据稍后看
                Map<String,Object> jsonMap = JSONObject.parseObject(enjoysignRecord.getResParameter(),Map.class);
                if(jsonMap.get("status").equals("200")){
                    List<Object> signerInfoList = (List)jsonMap.get("signer_info");
                    Map<String,Object> signerInfoMap = JSONObject.parseObject(signerInfoList.get(0).toString(),Map.class);
                    //获取文档id,拼装参数
                    documentId = signerInfoMap.get("document_id").toString();
                }
                if(StringUtil.isEmpty(documentId,enjoysignRecord.getEmail())){
                    logger.info("用户下载电子签章传入参数不正确，邮箱或document_id为空==userId："+userId);
                    return null;
                }
                param.put("key",key);
                param.put("txd_id",txd_id);
                param.put("document_id",documentId);
                param.put("email",enjoysignRecord.getEmail());

                //根据参数发起请求
                String result = HttpsUtil.postClient(url,param);
                //解析返回值并保存文件
                Map<String,Object> resMap = JSONObject.parseObject(result,Map.class);
                //初始电子签保存记录对象,并设置初始值
                EnjoysignDownloadLog enjoysignDownloadLog = new EnjoysignDownloadLog();
                enjoysignDownloadLog.setBorId(userId);
                enjoysignDownloadLog.setEmail(enjoysignRecord.getEmail());
                enjoysignDownloadLog.setResParameter(result);
                enjoysignDownloadLog.setDownloadType(1);
                enjoysignDownloadLog.setStatus(50);

                if(resMap!=null && resMap.get("status").equals("200")){
                    //获取加密文件
                    String bDoc = (String)resMap.get("base64Doc");
                    byte[] byteFile = Base64Util.base64DecodeToArray(bDoc);
                    //拼装文件名
                    StringBuilder fileName = new StringBuilder(String.valueOf(userId));
                    fileName.append("_");
                    fileName.append(documentId);
                    fileName.append(".pdf");
                    String filePath = EnjoysignUtil.saveTypeToFile(fileName.toString(),byteFile);
                    if(!StringUtil.isNotEmptys(filePath)){
                        enjoysignDownloadLog.setStatus(40);
                        enjoysignDownloadLog.setRemark("电子签文件保存异常");
                        enjoysignDownloadLogMapper.save(enjoysignDownloadLog);
                        return null;
                    }
                    sysAttachment = new SysAttachment();
                    sysAttachment.setFilePath(filePath);
                    sysAttachment.setClassify(1);
                    sysAttachment.setAttachmentType("pdf");
                    sysAttachment.setOriginName(fileName.toString());
                    sysAttachment.setSize(0L);
                    sysAttachment.setTargetId(enjoysignRecord.getId());
                    sysAttachment.setTargetField("");
                    sysAttachmentMapper.save(sysAttachment);
                    enjoysignDownloadLogMapper.save(enjoysignDownloadLog);
                    resultMap.put("file_path",filePath);
                    resultMap.put("origin_name",fileName.toString());
                }else{
                    enjoysignDownloadLog.setStatus(40);
                    enjoysignDownloadLog.setRemark("电子签返回数据异常");
                    enjoysignDownloadLogMapper.save(enjoysignDownloadLog);
                    return null;
                }
            }catch (Exception e){
                logger.info("------>调用获取电子签文件接口出错，方法名:requestGetEsignFile出错原因:"+e);
                return null;
            }
        }else{
            logger.info("------>调用获取电子签文件接口出错，无法查询到用户的电子签章==userId："+userId);
            return null;
        }
        return resultMap;
    }
}
