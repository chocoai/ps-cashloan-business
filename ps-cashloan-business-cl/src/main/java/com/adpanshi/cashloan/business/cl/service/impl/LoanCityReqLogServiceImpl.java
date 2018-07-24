package com.adpanshi.cashloan.business.cl.service.impl;

import com.adpanshi.cashloan.business.cl.domain.loancity.LoanCityReqLog;
import com.adpanshi.cashloan.business.cl.mapper.LoanCityReqLogMapper;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.service.BaseService;
import com.adpanshi.cashloan.business.core.common.service.impl.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * 请求消贷同城记录表ServiceImpl
 * 
 * @author yecy
 * @version 1.0.0
 * @date 2018-01-01 18:52:40

 *
 *
 */
 
@Service("loanCityReqLogService")
public class LoanCityReqLogServiceImpl extends BaseServiceImpl<LoanCityReqLog, Long> implements BaseService<LoanCityReqLog, Long> {
	
    private static final Logger logger = LoggerFactory.getLogger(LoanCityReqLogServiceImpl.class);
   
    @Resource
    private LoanCityReqLogMapper loanCityReqLogMapper;

	@Override
	public BaseMapper<LoanCityReqLog, Long> getMapper() {
		return loanCityReqLogMapper;
	}
	
}
