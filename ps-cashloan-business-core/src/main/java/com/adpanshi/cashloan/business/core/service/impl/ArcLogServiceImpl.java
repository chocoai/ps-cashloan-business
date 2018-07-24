package com.adpanshi.cashloan.business.core.service.impl;

import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.service.impl.BaseServiceImpl;
import com.adpanshi.cashloan.business.core.domain.ArcLog;
import com.adpanshi.cashloan.business.core.mapper.ArcLogMapper;
import com.adpanshi.cashloan.business.core.service.ArcLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * APP用户操作日志表ServiceImpl
 * 
 * @author tq
 * @version 1.0.0
 * @date 2018-06-19 11:17:57

 *
 *
 */
 
@Service("arcLogService")
public class ArcLogServiceImpl extends BaseServiceImpl<ArcLog, Long> implements ArcLogService {
	
    private static final Logger logger = LoggerFactory.getLogger(ArcLogServiceImpl.class);
   
    @Resource
    private ArcLogMapper arcLogMapper;

	@Override
	public BaseMapper<ArcLog, Long> getMapper() {
		return arcLogMapper;
	}
	
}
