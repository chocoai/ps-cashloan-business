package com.adpanshi.cashloan.business.cl.service.impl;

import com.adpanshi.cashloan.business.cl.mapper.TCCurrentPayingMapper;
import com.adpanshi.cashloan.business.cl.service.TCCurrentPayingService;
import com.adpanshi.cashloan.business.core.common.util.StringUtil;
import com.adpanshi.cashloan.business.rule.model.tianchuang.CurrentPaying;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tool.util.DateUtil;

import java.util.Date;
import java.util.Map;

@Service("tcCurrentPayingService")
public class TCCurrentPayingServiceImpl implements TCCurrentPayingService{
	
	@Autowired
	private TCCurrentPayingMapper tcCurrentPayingMapper;

	@Override
	public void save(Map<String, String> currentPayingMap, String orderId, Long userId, String phone) {
		CurrentPaying currentPaying = new CurrentPaying();
		currentPaying.setOrderId(orderId);
		currentPaying.setUserId(userId);
		currentPaying.setAccountRemaining(currentPayingMap.get("accountRemaining"));
		currentPaying.setAdmissionDate(currentPayingMap.get("admissionDate"));
		// 计算网龄
		if(!StringUtil.isEmpty(currentPayingMap.get("admissionDate")) && StringUtil.isValidDate(currentPayingMap.get("admissionDate"))){
			Date date=DateUtil.getNow();
			int age=0;
			age=DateUtil.daysBetween(DateUtil.valueOf(currentPayingMap.get("admissionDate")), date);
			currentPaying.setNetAge(age);
		} else {
			currentPaying.setNetAge(0);
		}
		currentPaying.setBelongBrad(currentPayingMap.get("belongBrad"));
		currentPaying.setCallingLevel(currentPayingMap.get("callingLevel"));
		currentPaying.setCreditAccount(currentPayingMap.get("creditAccount"));
		currentPaying.setCurrentStatus(currentPayingMap.get("currentStatus"));
		currentPaying.setFeeType(currentPayingMap.get("feeType"));
		currentPaying.setInternationalCallingLevel(currentPayingMap.get("internationalCallingLevel"));
		currentPaying.setPackageName(currentPayingMap.get("packageName"));
		currentPaying.setTimeFee(currentPayingMap.get("timeFee"));
		currentPaying.setUserName(phone);
		tcCurrentPayingMapper.insert(currentPaying);
	}
	

}
