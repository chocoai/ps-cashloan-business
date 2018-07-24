package com.adpanshi.cashloan.business.cl.service.impl;

import com.adpanshi.cashloan.business.cl.domain.Sms;
import com.adpanshi.cashloan.business.cl.domain.SmsTpl;
import com.adpanshi.cashloan.business.cl.mapper.SmsMapper;
import com.adpanshi.cashloan.business.cl.mapper.SmsTplMapper;
import com.adpanshi.cashloan.business.core.common.context.Global;
import com.adpanshi.cashloan.business.core.common.context.MessageConstant;
import com.adpanshi.cashloan.business.core.common.exception.BaseRuntimeException;
import com.adpanshi.cashloan.business.core.common.util.StringUtil;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Service;
import tool.util.DateUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lsk on 2017/2/15.
 */
@Service
public class SmsService {
    
    @Resource
    private SmsTplMapper smsTplMapper;
    
    @Resource
    private SmsMapper smsMapper;


    public String validateSmsCode(String phone, String email, String type, String vcode) {
    	if("dev".equals(Global.getValue("app_environment")) && "0000".equals(vcode)){
            return null;
        }
    	Map<String,Object> data=new HashMap<>();
    	if(StringUtil.isNotBlank(phone)){
            data.put("phone", phone);
        }else{
    	    data.put("email",email);
        }
    	data.put("smsType", type);
    	Sms sms=smsMapper.findTimeMsg(data);
        if (null==sms) {
            return MessageConstant.GET_VERIFICATION_CODE;
        }
        String verifyTime = sms.getVerifyTime()+"";
        if (Integer.parseInt(verifyTime)>10) {
        	return MessageConstant.VERIFICATION_CODE_EXPIRED;
		}
        long timeLimit = Long.parseLong(Global.getValue("sms_time_limit"));
		Date d1 = sms.getSendTime();
		Date d2 = DateUtil.getNow();
		long diff = d2.getTime() - d1.getTime();
		if (diff > timeLimit * 60 * 1000) {
            return MessageConstant.VERIFICATION_CODE_EXPIRED;
		}
        if (!vcode.equals(sms.getCode())) {
            return MessageConstant.VERIFICATION_CODE_ERROR;
        }
        return null;
    }

    public String sendSmsByTpl(HttpServletRequest request, String phone, String smsType, Object... params) {
    	SmsTpl smsTpl=smsTplMapper.findByType(smsType);
        if (null==smsTpl) {
            throw new BaseRuntimeException("没有找到短信模板:" + smsType);
        }
        String templ = smsTpl.getTpl();
        for (int i = 0; i < params.length; i++) {
            Object value = params[i];
            if (value == null) {
                value = "";
            } else {
                value = value.toString();
            }
            templ = templ.replaceFirst("\\{}", (String) value);
        }
        sendSms(phone, templ, smsType);
        return templ;
    }

    @SuppressWarnings("unchecked")
	public void sendSms(String phone, String content, String smsType) {
		Map<String, Object> rec = new HashedMap();
        rec.put("send_time", new Date());
        //String result = SmsUtil.send(phone, msg);
        String result = "短信已发送";
        Sms sms=new Sms(phone,content,result,new Date(),smsType);
        smsMapper.save(sms);
    }
}
