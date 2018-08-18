package com.adpanshi.cashloan.cl.service.impl;

import com.adpanshi.cashloan.cl.domain.OperatorReqLog;
import com.adpanshi.cashloan.cl.mapper.OperatorReqLogMapper;
import com.adpanshi.cashloan.cl.service.OperatorReqLogService;
import com.adpanshi.cashloan.core.common.context.Global;
import com.adpanshi.cashloan.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.core.common.service.impl.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tool.util.NumberUtil;
import tool.util.StringUtil;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 运营商认证中间表ServiceImpl
 * 
 * @author
 * @version 1.0.0
 * @date 2017-03-01 16:27:59
 */
 
@Service("operatorReqLogService")
public class OperatorReqLogServiceImpl extends BaseServiceImpl<OperatorReqLog, Long> implements OperatorReqLogService {
   
	public static final Logger logger = LoggerFactory.getLogger(OperatorReqLogServiceImpl.class);
    @Resource
    private OperatorReqLogMapper operatorReqLogMapper;

	@Override
	public BaseMapper<OperatorReqLog, Long> getMapper() {
		return operatorReqLogMapper;
	}

	@Override
	public OperatorReqLog findSelective(Map<String, Object> paramMap) {
		return operatorReqLogMapper.findSelective(paramMap);
	}

	@Override
	public String findOrderByUserId(Long userId) {
		return operatorReqLogMapper.findOrderByUserId(userId);
	}

	@Override
	public boolean checkUserOperator(long userId) {
		boolean result=true; 
		String daysMostTimes = Global.getValue("day_most_times");
		if (StringUtil.isNotBlank(daysMostTimes)) {
			int times = NumberUtil.getInt(daysMostTimes);
			Map<String, Object> paramMap=new HashMap<String, Object>();
			paramMap.put("userId", userId);
			List<OperatorReqLog> logs=operatorReqLogMapper.listByUserId(paramMap);
			if(!logs.isEmpty() && logs.size()>=times){
				logger.error("用户"+userId+"今天请求认证次数超过"+times+",请明日再来认证");
				result=false;
			}
		}
		return result;
	}

	@Override
	public OperatorReqLog findLastRecord(Map<String, Object> paramMap) {
		return operatorReqLogMapper.findLastRecord(paramMap);
	}

	@Override
	public int updateSelectRecord(Map<String, Object> paramMap) {
		return operatorReqLogMapper.updateSelective(paramMap);
	}
}