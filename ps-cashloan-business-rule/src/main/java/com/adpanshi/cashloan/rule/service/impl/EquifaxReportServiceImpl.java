package com.adpanshi.cashloan.rule.service.impl;

import com.adpanshi.cashloan.core.mapper.UserBaseInfoMapper;
import com.adpanshi.cashloan.core.model.ManagerUserModel;
import com.adpanshi.cashloan.data.thirdparty.equifax.domain.EquifaxCreditReportDomain;
import com.adpanshi.cashloan.dispatch.run.bo.DispatchRunResponseBo;
import com.adpanshi.cashloan.dispatch.run.domain.DispatchRunDomain;
import com.adpanshi.cashloan.rule.domain.UserEquipmentInfo;
import com.adpanshi.cashloan.rule.mapper.UserEquipmentInfoMapper;
import com.alibaba.fastjson.JSONObject;
import com.adpanshi.cashloan.core.common.context.Global;
import com.adpanshi.cashloan.rule.domain.equifaxReport.EquifaxReport;
import com.adpanshi.cashloan.rule.domain.equifaxReport.base.Envelope;
import com.adpanshi.cashloan.rule.mapper.EquifaxReportMapper;
import com.adpanshi.cashloan.rule.service.EquifaxReportService;
import com.thoughtworks.xstream.XStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service("equifaxReportService")
public class EquifaxReportServiceImpl implements EquifaxReportService {
    Logger logger= LoggerFactory.getLogger(getClass());
    @Resource
    private EquifaxReportMapper equifaxReportMapper;
    @Resource
    private DispatchRunDomain dispatchRunDomain;
    @Resource
    private UserBaseInfoMapper userBaseInfoMapper;
    @Resource
    private UserEquipmentInfoMapper userEquipmentInfoMapper;
    /** 印度OLOAN Equifax信用报告 */
    private String nodeNumber = "india_oloan_equifax";

    @Override
    public void getEquifaxReport(Long userId,String panNo, String firstName, String lastName){
        Map<String,String> sendMap = new HashMap<>();
        sendMap.put("panNo",panNo);
        sendMap.put("firstName",firstName);
        sendMap.put("lastName",lastName);
        logger.info("信用报告信息："+sendMap);
        try {
            //获取用户的全名/邮箱号
            ManagerUserModel managerUserModel = userBaseInfoMapper.getBaseModelByUserId(userId);
            //获取设备指纹
            Map<String,Object> userIdMap = new HashMap<>();
            userIdMap.put("userId", userId);
            UserEquipmentInfo userEquipmentInfo = userEquipmentInfoMapper.findSelective(userIdMap);
            String blackBox = "null";
            if (userEquipmentInfo!=null){
                blackBox = userEquipmentInfo.getDeviceFinger();
            }
            Map params = new HashMap();
            params.put("THIRD_PARTY_REQUEST_PARAMJSON",sendMap);
            //调起节点
            dispatchRunDomain.startNode(
                    managerUserModel.getUserDataId(),nodeNumber,managerUserModel.getPhone(),
                    managerUserModel.getLoginNameEmail(),null,managerUserModel.getRealName(),
                    blackBox,params);
        }catch (Exception e){
            logger.error("获取信用报告异常"+e.getMessage());
        }
    }

    @Override
    public Envelope getEquifaxReportDetail(Long userId) {
        EquifaxReport report = equifaxReportMapper.getReport(userId);
        if(report!=null&&report.getState()!=null&&report.getState().equals("200")){
            StringBuffer sb = new StringBuffer();
            sb.append("<soapenv:Envelope>");
            sb.append("<soapenv:Body>");
            sb.append("<sch:InquiryResponse>");
            try {
                String blobString = new String((byte[])report.getReportContent(),"UTF-8");
                int i = blobString.indexOf("<sch:InquiryResponseHeader>");
                blobString = blobString.substring(i,blobString.length());
                XStream xs = new XStream();
                //xml节点对应实体类
                xs.alias("Envelope", Envelope.class);
                xs.processAnnotations(new Class[] { Envelope.class });
                xs.ignoreUnknownElements();
                sb.append(blobString);
                //通过这种方式把xml转成对象
                Object obj = xs.fromXML(sb.toString());
                //强转成Data 对象
                Envelope data = (Envelope) obj;
                return data;
            }catch (Exception e){
                logger.error(e.getMessage());
                return null;
            }
        }
        return null;
    }


}
