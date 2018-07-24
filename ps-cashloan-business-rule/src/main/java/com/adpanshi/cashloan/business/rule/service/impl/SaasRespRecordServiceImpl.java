package com.adpanshi.cashloan.business.rule.service.impl;

import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.service.BaseService;
import com.adpanshi.cashloan.business.core.common.service.impl.BaseServiceImpl;
import com.adpanshi.cashloan.business.rule.domain.SaasRespRecord;
import com.adpanshi.cashloan.business.rule.mapper.SaasRespRecordMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author qing.yunhui 
 * @Since 2011-2018
 * @create 2018-04-03 12:04:06
 * @history
 */
@Service("saasRespRecordService")
public class SaasRespRecordServiceImpl extends BaseServiceImpl<SaasRespRecord,Long> implements BaseService<SaasRespRecord,Long> {

	Logger logger=LoggerFactory.getLogger(getClass());
	
	@Resource
    private SaasRespRecordMapper saasRespRecordMapper;
	
	@Override
	public BaseMapper<SaasRespRecord, Long> getMapper() {
		return saasRespRecordMapper;
	}
      
}
