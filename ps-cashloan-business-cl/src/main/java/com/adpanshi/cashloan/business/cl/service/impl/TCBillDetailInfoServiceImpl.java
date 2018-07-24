package com.adpanshi.cashloan.business.cl.service.impl;

import com.adpanshi.cashloan.business.cl.mapper.TCBillDetailInfoMapper;
import com.adpanshi.cashloan.business.cl.mapper.TCBillListMapper;
import com.adpanshi.cashloan.business.cl.service.TCBillDetailInfoService;
import com.adpanshi.cashloan.business.core.common.util.JsonUtil;
import com.adpanshi.cashloan.business.rule.model.tianchuang.BillDetailInfo;
import com.adpanshi.cashloan.business.rule.model.tianchuang.BillList;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("tcBillDetailInfoService")
public class TCBillDetailInfoServiceImpl implements TCBillDetailInfoService{
	
	@Autowired
	private TCBillDetailInfoMapper tcBillDetailInfoMapper;
	
	@Autowired
	private TCBillListMapper tcBillListMapper;

	@Override
	public int batchSave(JSONArray billDetailInfoArray, String orderId) {
		List<BillDetailInfo> billDetailInfos = new ArrayList<BillDetailInfo>(billDetailInfoArray.size());
		for (int i = 0; i < billDetailInfoArray.size(); i++) {
			JSONObject billDetailInfoJson = billDetailInfoArray.getJSONObject(i);
			BillDetailInfo billDetailInfo = new BillDetailInfo();
			billDetailInfo.setOrderId(orderId);
			billDetailInfo.setBillComsume(billDetailInfoJson.getString("billComsume"));
			billDetailInfo.setBillCycle(billDetailInfoJson.getString("billCycle"));
			billDetailInfo.setPlanAmount(billDetailInfoJson.getString("planAmount"));
			billDetailInfos.add(billDetailInfo);
			Long id =  tcBillDetailInfoMapper.insert(billDetailInfo);
			JSONArray billListJSONArray = billDetailInfoJson.getJSONArray("billList");
			if (billListJSONArray != null && billListJSONArray.size() > 0) {
				List<BillList>  billLists = new ArrayList<BillList>();
				for (int j = 0; j < billListJSONArray.size(); j++) {
					JSONObject billListObject = billListJSONArray.getJSONObject(j);
					Map<String, Object> billListMap = JsonUtil.parse(billListObject.toString(), Map.class);
					BillList billList = new BillList();
					billList.setOrderId(orderId);
					//天创运营商字段变更,进行相应修改 @author yecy 20180227
					billList.setAccountCategory(String.valueOf(billListMap.get("account_category")));
					billList.setAccountFee(String.valueOf(billListMap.get("account_fee")));
					billList.setAccountItems(String.valueOf(billListMap.get("account_items")));
					billList.setBillDetailInfoId(id);
					billLists.add(billList);
				}
				tcBillListMapper.batchInsert(billLists);
			}
		}
		return billDetailInfoArray.size();
	}

}
