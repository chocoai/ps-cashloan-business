package com.adpanshi.cashloan.business.cl.service.impl;

import com.adpanshi.cashloan.business.cl.domain.yincheng.YinChengRespLog;
import com.adpanshi.cashloan.business.cl.mapper.YinChengRespLogMapper;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.service.BaseService;
import com.adpanshi.cashloan.business.core.common.service.impl.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * 银程请求保存表ServiceImpl
 * 
 * @author yecy
 * @version 1.0.0
 * @date 2018-03-09 22:15:51

 *
 *
 */
 
@Service("yinChengRespLogService")
public class YinChengRespLogServiceImpl extends BaseServiceImpl<YinChengRespLog, Long> implements BaseService<YinChengRespLog, Long> {
	
    private static final Logger logger = LoggerFactory.getLogger(YinChengRespLogServiceImpl.class);
   
    @Resource
    private YinChengRespLogMapper yinChengRespLogMapper;

	@Override
	public BaseMapper<YinChengRespLog, Long> getMapper() {
		return yinChengRespLogMapper;
	}
	
}
