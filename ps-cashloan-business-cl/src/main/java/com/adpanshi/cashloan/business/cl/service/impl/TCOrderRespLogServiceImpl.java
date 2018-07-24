package com.adpanshi.cashloan.business.cl.service.impl;

import com.adpanshi.cashloan.business.cl.domain.TCOrderRespLog;
import com.adpanshi.cashloan.business.cl.mapper.TCOrderRespLogMapper;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.service.BaseService;
import com.adpanshi.cashloan.business.core.common.service.impl.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * 天创运营商异步响应表ServiceImpl
 * 
 * @author yecy
 * @version 1.0.0
 * @date 2018-02-27 11:07:10

 *
 *
 */
 
@Service("tCOrderRespLogService")
public class TCOrderRespLogServiceImpl extends BaseServiceImpl<TCOrderRespLog, Long> implements BaseService<TCOrderRespLog, Long> {
	
    private static final Logger logger = LoggerFactory.getLogger(TCOrderRespLogServiceImpl.class);
   
    @Resource
    private TCOrderRespLogMapper tCOrderRespLogMapper;

	@Override
	public BaseMapper<TCOrderRespLog, Long> getMapper() {
		return tCOrderRespLogMapper;
	}
	
}
