package com.adpanshi.cashloan.business.cl.service.impl;

import com.adpanshi.cashloan.business.cl.domain.UserSdkLog;
import com.adpanshi.cashloan.business.cl.mapper.UserSdkLogMapper;
import com.adpanshi.cashloan.business.cl.service.UserAuthService;
import com.adpanshi.cashloan.business.cl.service.UserSdkLogService;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.service.impl.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * sdk识别记录表ServiceImpl
 * 
 * @author
 * @version 1.0.0
 * @date 2017-04-20 09:47:04
 *
 *
 *
 *
 */
 
@Service("userSdkLogService")
public class UserSdkLogServiceImpl extends BaseServiceImpl<UserSdkLog, Long> implements UserSdkLogService {
	
    @SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(UserSdkLogServiceImpl.class);
   
    @Resource
    private UserSdkLogMapper userSdkLogMapper;

	@Resource
	private UserAuthService userAuthService;

	@Override
	public BaseMapper<UserSdkLog, Long> getMapper() {
		return userSdkLogMapper;
	}

	@Override
	public int save(UserSdkLog usl) {
		return userSdkLogMapper.save(usl);
	}
	
}