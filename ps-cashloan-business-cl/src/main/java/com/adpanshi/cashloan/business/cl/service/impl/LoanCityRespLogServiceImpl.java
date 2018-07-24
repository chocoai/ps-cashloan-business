package com.adpanshi.cashloan.business.cl.service.impl;

import com.adpanshi.cashloan.business.cl.domain.loancity.LoanCityRespLog;
import com.adpanshi.cashloan.business.cl.mapper.LoanCityRespLogMapper;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.service.BaseService;
import com.adpanshi.cashloan.business.core.common.service.impl.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * 消贷同城响应消息记录表ServiceImpl
 * 
 * @author yecy
 * @version 1.0.0
 * @date 2018-01-01 18:53:18

 *
 *
 */
 
@Service("loanCityRespLogService")
public class LoanCityRespLogServiceImpl extends BaseServiceImpl<LoanCityRespLog, Long> implements BaseService<LoanCityRespLog, Long> {
	
    private static final Logger logger = LoggerFactory.getLogger(LoanCityRespLogServiceImpl.class);
   
    @Resource
    private LoanCityRespLogMapper loanCityRespLogMapper;

	@Override
	public BaseMapper<LoanCityRespLog, Long> getMapper() {
		return loanCityRespLogMapper;
	}
	
}
