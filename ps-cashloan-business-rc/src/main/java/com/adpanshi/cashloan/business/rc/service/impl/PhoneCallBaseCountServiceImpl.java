package com.adpanshi.cashloan.business.rc.service.impl;

import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.service.impl.BaseServiceImpl;
import com.adpanshi.cashloan.business.rc.domain.PhoneCallBaseCount;
import com.adpanshi.cashloan.business.rc.mapper.PhoneCallBaseCountMapper;
import com.adpanshi.cashloan.business.rc.service.PhoneCallBaseCountService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * 运营商通话信息统计ServiceImpl
 * 

 * @version 1.0.0
 * @date 2017-04-18 14:34:09
 *
 *
 *
 *
 */
 
@Service("phoneCallBaseCountService")
public class PhoneCallBaseCountServiceImpl extends BaseServiceImpl<PhoneCallBaseCount, Long> implements PhoneCallBaseCountService {
   
    @Resource
    private PhoneCallBaseCountMapper phoneCallBaseCountMapper;

	@Override
	public BaseMapper<PhoneCallBaseCount, Long> getMapper() {
		return phoneCallBaseCountMapper;
	}
	
}