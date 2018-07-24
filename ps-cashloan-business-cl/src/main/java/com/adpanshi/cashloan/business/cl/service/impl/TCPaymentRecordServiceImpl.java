package com.adpanshi.cashloan.business.cl.service.impl;

import com.adpanshi.cashloan.business.cl.mapper.TCPaymentRecordMapper;
import com.adpanshi.cashloan.business.cl.service.TCPaymentRecordService;
import com.adpanshi.cashloan.business.rule.model.tianchuang.PaymentRecord;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("tcPaymentRecordService")
public class TCPaymentRecordServiceImpl implements TCPaymentRecordService{
	
	@Autowired
	private TCPaymentRecordMapper tcPaymentRecordMapper;

	@Override
	public void batchSave(JSONArray paymentRecordArray, String orderId) {
		List<PaymentRecord> paymentRecords = new ArrayList<PaymentRecord>(paymentRecordArray.size());
		for (int i = 0; i < paymentRecordArray.size(); i++) {
			JSONObject paymentRecordJson = paymentRecordArray.getJSONObject(i);
			PaymentRecord paymentRecord = new PaymentRecord();
			paymentRecord.setOrderId(orderId);
			paymentRecord.setPayChannel(paymentRecordJson.getString("payChannel"));
			paymentRecord.setPayDate(paymentRecordJson.getString("payDate"));
			paymentRecord.setPayFee(paymentRecordJson.getString("payFee"));
			paymentRecord.setPayType(paymentRecordJson.getString("payType"));
			paymentRecords.add(paymentRecord);
		}
		tcPaymentRecordMapper.batchInsert(paymentRecords);
	}
	
}
