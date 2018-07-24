package com.adpanshi.cashloan.business.cl.service.impl;

import com.adpanshi.cashloan.business.cl.mapper.TCUsedBusiInfoMapper;
import com.adpanshi.cashloan.business.cl.service.TCUsedBusiInfoService;
import com.adpanshi.cashloan.business.rule.model.tianchuang.UsedBusiInfo;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("tcUsedBusiInfoService")
public class TCUsedBusiInfoServiceImpl implements TCUsedBusiInfoService{
	
	@Autowired
	private TCUsedBusiInfoMapper tcUsedBusiInfoMapper;

	@Override
	public void batchSave(JSONArray usedBusiInfoArray, String orderId) {
		List<UsedBusiInfo> usedBusiInfos = new ArrayList<UsedBusiInfo>(usedBusiInfoArray.size());
		for (int i = 0; i < usedBusiInfoArray.size(); i++) {
			JSONObject usedBusiInfoJson = usedBusiInfoArray.getJSONObject(i);
			UsedBusiInfo usedBusiInfo = new UsedBusiInfo();
			usedBusiInfo.setBusiName(usedBusiInfoJson.getString("busiName"));
			usedBusiInfo.setDealTime(usedBusiInfoJson.getString("dealTime"));
			usedBusiInfo.setDesc(usedBusiInfoJson.getString("desc"));
			usedBusiInfo.setEfftime(usedBusiInfoJson.getString("efftime"));
			usedBusiInfo.setExpTime(usedBusiInfoJson.getString("expTime"));
			usedBusiInfo.setFeeStandard(usedBusiInfoJson.getString("feeStandard"));
			usedBusiInfo.setFeeType(usedBusiInfoJson.getString("feeType"));
			usedBusiInfo.setOrderId(orderId);
			usedBusiInfos.add(usedBusiInfo);
		}
		tcUsedBusiInfoMapper.batchInsert(usedBusiInfos);
	}
	
}
