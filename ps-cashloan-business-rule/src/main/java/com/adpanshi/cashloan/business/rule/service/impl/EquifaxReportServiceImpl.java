package com.adpanshi.cashloan.business.rule.service.impl;

import com.adpanshi.cashloan.business.core.common.context.Global;
import com.adpanshi.cashloan.business.core.common.util.HttpUtils;
import com.adpanshi.cashloan.business.rule.mapper.EquifaxReportMapper;
import com.adpanshi.cashloan.business.rule.service.EquifaxReportService;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service("equifaxReportService")
public class EquifaxReportServiceImpl implements EquifaxReportService {
    @Resource
            private EquifaxReportMapper equifaxReportMapper;
    Logger logger= LoggerFactory.getLogger(getClass());
    @Override
    public void getEquifaxReport(String userId,String panNo, String firstName, String lastName){
        Map<String,Object> sendMap = new HashMap<>();
        String url = Global.get("equifax_report_url");
        String key = Global.get("equifax_report_key");
        sendMap.put("key",key);
        sendMap.put("pan_no",panNo);
        sendMap.put("first_name",firstName);
        sendMap.put("last_name",lastName);
        try {
            String resultXml = HttpUtils.doPost(url, sendMap);
            Map<String,Object> map = new HashMap<>();
            map.put("userId",userId);
            map.put("createTime",new Date());
            map.put("reportContent",resultXml);
            if(resultXml.contains("message")){
                JSONObject jsonObject = JSONObject.parseObject(resultXml);
                map.put("message",jsonObject.get("message"));
                map.put("state","500");
            }else{
                map.put("message","Get credit report success");
                map.put("state","200");
            }
            equifaxReportMapper.save(map);
        }catch (Exception e){
            Map<String,Object> map = new HashMap<>();
            map.put("userId",userId);
            map.put("createTime",new Date());
            map.put("message","Get credit report failure ");
            map.put("state","500");
//            map.put("reportContent",resultXml);
            equifaxReportMapper.save(map);
            logger.error("获取信用报告异常"+e.getMessage());
        }
    }



}
