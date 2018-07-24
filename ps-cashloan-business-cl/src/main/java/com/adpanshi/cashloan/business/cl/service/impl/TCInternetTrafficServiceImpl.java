package com.adpanshi.cashloan.business.cl.service.impl;

import com.adpanshi.cashloan.business.cl.mapper.TCInternetTrafficMapper;
import com.adpanshi.cashloan.business.cl.service.TCInternetTrafficService;
import com.adpanshi.cashloan.business.rule.model.tianchuang.InternetTraffic;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("tcInternetTrafficService")
public class TCInternetTrafficServiceImpl implements TCInternetTrafficService{

	@Autowired
	private TCInternetTrafficMapper tcInternetTrafficMapper;

	@Override
	public void batchSave(JSONArray internetTrafficArray, String orderId) {
		List<InternetTraffic> internetTraffics = new ArrayList<InternetTraffic>(internetTrafficArray.size());
		for (int i = 0; i < internetTrafficArray.size(); i++) {
			JSONObject internetTrafficJson = internetTrafficArray.getJSONObject(i);
			InternetTraffic internetTraffic = new InternetTraffic();
			internetTraffic.setBeginTime(internetTrafficJson.getString("beginTime"));
			internetTraffic.setExtParaName(internetTrafficJson.getString("extParaName"));
			internetTraffic.setFlowDic(internetTrafficJson.getString("flowDic"));
			internetTraffic.setHomeAreaName(internetTrafficJson.getString("homeAreaName"));
			internetTraffic.setInternetTrafficWithinPackage(internetTrafficJson.getString("internetTrafficWithinPackage"));
			internetTraffic.setNetGoType(internetTrafficJson.getString("netGoType"));
			internetTraffic.setNetLongTime(internetTrafficJson.getString("netLongTime"));
			internetTraffic.setNetTypeName(internetTrafficJson.getString("netTypeName"));
			internetTraffic.setOrderId(orderId);
			internetTraffic.setPackageDic(internetTrafficJson.getString("packageDic"));
			internetTraffic.setSvcName(internetTrafficJson.getString("svcName"));
			internetTraffic.setTotalFee(internetTrafficJson.getString("totalFee"));
			internetTraffics.add(internetTraffic);
		}
		tcInternetTrafficMapper.batchInsert(internetTraffics);
	}

}
