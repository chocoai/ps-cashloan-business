package com.adpanshi.cashloan.business.rc.service.impl;

import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.service.impl.BaseServiceImpl;
import com.adpanshi.cashloan.business.rc.domain.TppReqLog;
import com.adpanshi.cashloan.business.rc.mapper.TppReqLogMapper;
import com.adpanshi.cashloan.business.rc.service.TppReqLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;


/**
 * 第三方征信请求记录ServiceImpl
 * 

 * @version 1.0.0
 * @date 2017-03-20 13:50:34
 *
 *
 *
 *
 */
 
@Service("tppReqLogService")
public class TppReqLogServiceImpl extends BaseServiceImpl<TppReqLog, Long> implements TppReqLogService {
   
    @Resource
    private TppReqLogMapper tppReqLogMapper;

	@Override
	public BaseMapper<TppReqLog, Long> getMapper() {
		return tppReqLogMapper;
	}

	@Override
	public int modifyTppReqLog(TppReqLog log) {
		return tppReqLogMapper.modifyTppReqLog(log);
	}
	
	@Override
	public TppReqLog findSelective(Map<String, Object> params) {
		return tppReqLogMapper.findSelective(params);
	}
	
}