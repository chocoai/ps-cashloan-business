package com.adpanshi.cashloan.business.cl.service.impl;

import com.adpanshi.cashloan.business.cl.service.TCCallingRecordService;
import com.adpanshi.cashloan.business.rule.mapper.TCCallingRecordMapper;
import com.adpanshi.cashloan.business.rule.mapper.UserContactsMapper;
import com.adpanshi.cashloan.business.rule.model.tianchuang.CallingRecord;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * saas风控通话记录
 * @author ppchen
 * 2017年9月5日 上午11:11:00
 * 
 */
@Service("tcCallingRecordService")
public class TCCallingRecordServiceImpl implements TCCallingRecordService{
	
	@Autowired
	private TCCallingRecordMapper tcCallingRecordMapper;
	@Autowired
	private UserContactsMapper userContactsMapper;

	@Override
	public void batchSave(JSONArray callingRecordArray, String orderId, String phone) {
		List<CallingRecord> callingRecords = new ArrayList<CallingRecord>(callingRecordArray.size());
		for (int i = 0; i < callingRecordArray.size(); i++) {
			JSONObject callingRecordJson = callingRecordArray.getJSONObject(i);
			CallingRecord callingRecord = new CallingRecord();
			callingRecord.setOrderId(orderId);
			callingRecord.setCallingFee(callingRecordJson.getString("callingFee"));
			callingRecord.setCallingNumber(callingRecordJson.getString("callingNumber"));
			callingRecord.setCallingPosition(callingRecordJson.getString("callingPosition"));
			callingRecord.setCallingType(callingRecordJson.getString("callingType"));
			callingRecord.setHoldingPosition(callingRecordJson.getString("holdingPosition"));
			callingRecord.setHoldingTime(callingRecordJson.getString("holdingTime"));
			callingRecord.setHoldingType(callingRecordJson.getString("holdingType"));
			callingRecord.setMobileSign(callingRecordJson.getString("mobileSign"));
			callingRecord.setPackageDis(callingRecordJson.getString("packageDis"));
			callingRecord.setRomatType(callingRecordJson.getString("romatType"));
			callingRecord.setTalkTime(callingRecordJson.getString("talkTime"));
			callingRecord.setUserName(phone);
			callingRecords.add(callingRecord);
		}
		tcCallingRecordMapper.batchInsert(callingRecords);
	}


}
