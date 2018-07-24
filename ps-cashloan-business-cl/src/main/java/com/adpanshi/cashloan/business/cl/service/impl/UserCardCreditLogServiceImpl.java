package com.adpanshi.cashloan.business.cl.service.impl;

import com.adpanshi.cashloan.business.cl.domain.UserCardCreditLog;
import com.adpanshi.cashloan.business.cl.mapper.UserCardCreditLogMapper;
import com.adpanshi.cashloan.business.cl.service.UserCardCreditLogService;
import com.adpanshi.cashloan.business.core.common.context.Global;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.service.impl.BaseServiceImpl;
import com.adpanshi.cashloan.business.core.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tool.util.NumberUtil;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


/**
 * 人脸识别请求记录ServiceImpl
 * 

 * @version 1.0.0
 * @date 2017-04-10 14:37:56
 *
 *
 * 
 *
 */
 
@Service("userCardCreditLogService")
public class UserCardCreditLogServiceImpl extends BaseServiceImpl<UserCardCreditLog, Long> implements UserCardCreditLogService {
	
    private static final Logger logger = LoggerFactory.getLogger(UserCardCreditLogServiceImpl.class);
   
    @Resource
    private UserCardCreditLogMapper userCardCreditLogMapper;

	@Override
	public BaseMapper<UserCardCreditLog, Long> getMapper() {
		return userCardCreditLogMapper;
	}

	@Override
	public boolean isCanCredit(Long userId) {
		boolean result=true; 
		String daysMostTimes = Global.getValue("idCardCredit_day_most_times");
		if(StringUtil.isNotBlank(daysMostTimes)){
			int times = NumberUtil.getInt(daysMostTimes);
			Map<String, Object> paramMap=new HashMap<String, Object>();
			paramMap.put("userId", userId);
		    int count=userCardCreditLogMapper.countByUserId(paramMap);
			if(count>=times){
				logger.error("用户"+userId+"今天请求人脸识别次数超过"+times+",请明日再来认证");
				result=false;
			}
		}
		return result;
	}
	
}