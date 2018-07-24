package com.adpanshi.cashloan.business.cl.service.impl;

import com.adpanshi.cashloan.business.cl.service.TCMessageBillService;
import com.adpanshi.cashloan.business.rule.mapper.TCMessageBillMapper;
import com.adpanshi.cashloan.business.rule.model.tianchuang.MessageBill;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("tcMessageBillService")
public class TCMessageBillServiceImpl implements TCMessageBillService{
	
	@Autowired
	private TCMessageBillMapper tcMessageBillMapper;

	@Override
	public void batchSave(JSONArray messageBillArray, String orderId) {
		List<MessageBill> messageBills = new ArrayList<MessageBill>(messageBillArray.size());
		for (int i = 0; i < messageBillArray.size(); i++) {
			JSONObject messageBillJson = messageBillArray.getJSONObject(i);
			MessageBill messageBill = new MessageBill();
			messageBill.setOrderId(orderId);
			messageBill.setAmount(messageBillJson.getString("amount"));
			messageBill.setBusiName(messageBillJson.getString("busiName"));
			messageBill.setBusType(messageBillJson.getString("busType"));
			messageBill.setOtherNum(messageBillJson.getString("otherNum"));
			messageBill.setOtherNumArea(messageBillJson.getString("otherNumArea"));
			messageBill.setPackageDis(messageBillJson.getString("packageDis"));
			messageBill.setSmsAddr(messageBillJson.getString("smsAddr"));
			messageBill.setSmsSaveType(messageBillJson.getString("smsSaveType"));
			messageBill.setSmsTime(messageBillJson.getString("smsTime"));
			messageBill.setSmsType(messageBillJson.getString("smsType"));
			messageBills.add(messageBill);
		}
		tcMessageBillMapper.batchInsert(messageBills);
	}


}
