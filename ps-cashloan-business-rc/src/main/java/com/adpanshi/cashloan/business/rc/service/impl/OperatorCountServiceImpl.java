package com.adpanshi.cashloan.business.rc.service.impl;

import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.service.impl.BaseServiceImpl;
import com.adpanshi.cashloan.business.core.common.util.DateUtil;
import com.adpanshi.cashloan.business.core.domain.UserBaseInfo;
import com.adpanshi.cashloan.business.core.mapper.UserBaseInfoMapper;
import com.adpanshi.cashloan.business.rc.domain.PhoneCallBaseCount;
import com.adpanshi.cashloan.business.rc.domain.PhoneCallBorrowCount;
import com.adpanshi.cashloan.business.rc.mapper.OperatorCountMapper;
import com.adpanshi.cashloan.business.rc.mapper.PhoneCallBaseCountMapper;
import com.adpanshi.cashloan.business.rc.mapper.PhoneCallBorrowCountMapper;
import com.adpanshi.cashloan.business.rc.model.OperatorCountModel;
import com.adpanshi.cashloan.business.rc.service.OperatorCountService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

/**
 * 运营商信息

 * @version 1.0
 * @date 2017年4月18日上午10:40:07
 * Copyright 粉团网路 现金贷  All Rights Reserved
 *
 * 研发中心：rdc@fentuan123.com
 *
 */
@Service("operatorCountService")
public class OperatorCountServiceImpl extends BaseServiceImpl<OperatorCountModel, String> implements OperatorCountService {

	@Resource
	private OperatorCountMapper operatorCountMapper;
	@Resource
	private UserBaseInfoMapper userBaseInfoMapper;
	@Resource
	private PhoneCallBaseCountMapper phoneCallBaseCountMapper;
	@Resource
	private PhoneCallBorrowCountMapper phoneCallBorrowCountMapper;

	@Override
	public BaseMapper<OperatorCountModel, String> getMapper() {
		return operatorCountMapper;
	}
	
	@Override
	public int operatorCountVoice(Long userId) {
		int update = 0;
		UserBaseInfo baseInfo = userBaseInfoMapper.findByUserId(userId); 
		OperatorCountModel result = null;
		if(baseInfo!=null && baseInfo.getPhone()!=null){
			result = operatorCountMapper.operatorVoicesInfo(baseInfo.getPhone());
			result.setUserId(userId);
			result.setPhone(baseInfo.getPhone());
		}
		
		if(result!=null){
			Double monthAmt = operatorCountMapper.operatorMonthAmt(baseInfo.getPhone());
			result.setMonthAmt(monthAmt);
			Date joinDate = operatorCountMapper.operatorJoinDate(baseInfo.getPhone());
			if(joinDate!=null){
				Calendar cal1 = Calendar.getInstance();
				cal1.setTime(joinDate);
				Calendar cal2 = Calendar.getInstance();
				cal2.setTime(DateUtil.getNow());
				int month = (cal2.get(Calendar.YEAR) -cal1.get(Calendar.YEAR))*12 - cal1.get(Calendar.MONTH) + cal2.get(Calendar.MONTH);
				result.setJoinMonthCount(month);
			}else{
				result.setJoinMonthCount(0);
			}
			OperatorCountModel voicesPhone = operatorCountMapper.operatorVoicesPhone(baseInfo.getPhone());
			
			if(voicesPhone!=null){
				result.setGe3Times60SNumCount90(voicesPhone.getGe3Times60SNumCount90());
				result.setGe5TimesNumCount90(voicesPhone.getGe5TimesNumCount90());
			}
			
			Integer times = operatorCountMapper.emerConcatTimes(userId);
			result.setEmerConcatTimes6Month(times);
		}
		
		if(result!=null){
			PhoneCallBaseCount baseCount = new PhoneCallBaseCount(result);
			if(result.getCountVoices90()!=null && result.getLiveAddrVoice90N()!=null &&result.getCountVoices90()>0 && result.getLiveAddrVoice90N()>0 && result.getCountVoices90().intValue() == result.getLiveAddrVoice90N()){
				baseCount.setAddressMatching("20");
			}else{
				baseCount.setAddressMatching("10");
			}
			if(result.getMonthAmt()!=null && result.getMonthAmt()>0){
				BigDecimal monthAmt = new BigDecimal(result.getMonthAmt()).setScale(0, BigDecimal.ROUND_HALF_UP);
				baseCount.setMonthSource(monthAmt.intValue());
			}else{
				baseCount.setMonthSource(0);
			}
			update = phoneCallBaseCountMapper.save(baseCount);
		}
		
		return update;
	}

	@Override
	public int operatorCountBorrow(Long userId) {
		int update = 0;
		UserBaseInfo baseInfo = userBaseInfoMapper.findByUserId(userId); 
		OperatorCountModel result = null;
		if(baseInfo!=null && baseInfo.getPhone()!=null){
			result = operatorCountMapper.concatsBorrowInfo(baseInfo.getPhone());
			result.setUserId(userId);
			result.setPhone(baseInfo.getPhone());
		}
		
		if(result!=null){
			PhoneCallBorrowCount borrowCount = new PhoneCallBorrowCount(result);
			update = phoneCallBorrowCountMapper.save(borrowCount);
		}
		
		return update;
	}

}
