package com.adpanshi.cashloan.business.rule.service.impl;

import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.service.BaseService;
import com.adpanshi.cashloan.business.core.common.service.impl.BaseServiceImpl;
import com.adpanshi.cashloan.business.rule.domain.SaasReqRecord;
import com.adpanshi.cashloan.business.rule.mapper.SaasReqRecordMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author qing.yunhui 
 * @Since 2011-2018
 * @create 2018-04-03 12:03:55
 * @history
 */
@Service("saasReqRecordService")
public class SaasReqRecordServiceImpl extends BaseServiceImpl<SaasReqRecord,Long> implements BaseService<SaasReqRecord,Long> {

	Logger logger=LoggerFactory.getLogger(getClass());
	
	@Resource
    private SaasReqRecordMapper saasReqRecordMapper;
	
	@Override
	public BaseMapper<SaasReqRecord, Long> getMapper() {
		return saasReqRecordMapper;
	}
      
}
