package com.adpanshi.cashloan.business.cl.service.impl;

import com.adpanshi.cashloan.business.cl.mapper.TCMobileUserMapper;
import com.adpanshi.cashloan.business.cl.service.TCMobileUserService;
import com.adpanshi.cashloan.business.rule.model.tianchuang.MobileUser;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service("tcMobileUserService")
public class TCMobileUserServiceImpl implements TCMobileUserService{
	
	@Autowired
	private TCMobileUserMapper mobileUserDao;

	@Override
	public void save(Map<String, Object> mobileUserMap, String orderId) {
		MobileUser mobileUser = new MobileUser();
		mobileUser.setBillAddr(MapUtils.getString(mobileUserMap,"billAddr"));
		mobileUser.setGender(MapUtils.getString(mobileUserMap,"gender"));
		mobileUser.setIdentifyAddr(MapUtils.getString(mobileUserMap,"identifyAddr"));
		mobileUser.setIdentifyNum(MapUtils.getString(mobileUserMap,"identifyNum"));
		mobileUser.setIdentifyType(MapUtils.getString(mobileUserMap,"identifyType"));
		mobileUser.setInputName(MapUtils.getString(mobileUserMap,"inputName"));
		mobileUser.setInputNum(MapUtils.getString(mobileUserMap,"inputNum"));
		mobileUser.setOrderId(orderId);
		mobileUser.setOther(MapUtils.getString(mobileUserMap,"other"));
		mobileUser.setPersonName(MapUtils.getString(mobileUserMap,"personName"));
		mobileUser.setStarLevel(MapUtils.getString(mobileUserMap,"starLevel"));
		mobileUser.setUserCity(MapUtils.getString(mobileUserMap,"userCity"));
		mobileUser.setUserName(MapUtils.getString(mobileUserMap,"userName"));

		mobileUser.setIfRealName(MapUtils.getString(mobileUserMap,"ifRealName"));
		mobileUser.setCreateTime(new Date((Long)mobileUserMap.get("createTime")));
		mobileUserDao.insert(mobileUser);
	}

	
}
