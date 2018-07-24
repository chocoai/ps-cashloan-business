package com.adpanshi.cashloan.business.cl.service.impl;

import com.adpanshi.cashloan.business.cl.domain.ProfitCashLog;
import com.adpanshi.cashloan.business.cl.mapper.ProfitCashLogMapper;
import com.adpanshi.cashloan.business.cl.service.ProfitCashLogService;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.service.impl.BaseServiceImpl;
import com.adpanshi.cashloan.business.core.mapper.UserMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 分润提现记录ServiceImpl
 * 
 * @author
 * @version 1.0.0
 * @date 2017-02-18 16:51:48
 *
 *
 * 
 *
 */
 
@Service("profitCashLogService")
public class ProfitCashLogServiceImpl extends BaseServiceImpl<ProfitCashLog, Long> implements ProfitCashLogService {
	
    @SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(ProfitCashLogServiceImpl.class);
   
    @Resource
    private ProfitCashLogMapper profitCashLogMapper;
    @Resource
    private UserMapper userMapper;
    
    

	@Override
	public BaseMapper<ProfitCashLog, Long> getMapper() {
		return profitCashLogMapper;
	}

	@Override
	public Page<ProfitCashLog> page(Map<String, Object> searchMap, int current,
			int pageSize) {
		PageHelper.startPage(current, pageSize);
		List<ProfitCashLog> list = profitCashLogMapper.listSelective(searchMap);
		return (Page<ProfitCashLog>) list;
	}

	
}