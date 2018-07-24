package com.adpanshi.cashloan.business.core.service.impl;

import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.service.impl.BaseServiceImpl;
import com.adpanshi.cashloan.business.core.domain.ClLog;
import com.adpanshi.cashloan.business.core.mapper.ClLogMapper;
import com.adpanshi.cashloan.business.core.service.ClLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * APP用户操作日志表ServiceImpl
 * 
 * @author tq
 * @version 1.0.0
 * @date 2018-06-19 11:18:07

 *
 *
 */
 
@Service("clLogService")
public class ClLogServiceImpl extends BaseServiceImpl<ClLog, Long> implements ClLogService {
	
    private static final Logger logger = LoggerFactory.getLogger(ClLogServiceImpl.class);
   
    @Resource
    private ClLogMapper clLogMapper;

	@Override
	public BaseMapper<ClLog, Long> getMapper() {
		return clLogMapper;
	}
	
}
