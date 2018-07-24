package com.adpanshi.cashloan.business.rc.service.impl;

import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.service.impl.BaseServiceImpl;
import com.adpanshi.cashloan.business.rc.domain.PhoneCallBorrowCount;
import com.adpanshi.cashloan.business.rc.mapper.PhoneCallBorrowCountMapper;
import com.adpanshi.cashloan.business.rc.service.PhoneCallBorrowCountService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * 运营商联系人借款信息统计ServiceImpl
 * 

 * @version 1.0.0
 * @date 2017-04-18 14:49:11
 *
 *
 *
 *
 */
 
@Service("phoneCallBorrowCountService")
public class PhoneCallBorrowCountServiceImpl extends BaseServiceImpl<PhoneCallBorrowCount, Long> implements PhoneCallBorrowCountService {
	
    @Resource
    private PhoneCallBorrowCountMapper phoneCallBorrowCountMapper;

	@Override
	public BaseMapper<PhoneCallBorrowCount, Long> getMapper() {
		return phoneCallBorrowCountMapper;
	}
	
}