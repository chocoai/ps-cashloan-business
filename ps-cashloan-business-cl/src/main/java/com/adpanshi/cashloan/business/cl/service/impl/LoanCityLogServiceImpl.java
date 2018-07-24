package com.adpanshi.cashloan.business.cl.service.impl;

import com.adpanshi.cashloan.business.cl.domain.loancity.LoanCityLog;
import com.adpanshi.cashloan.business.cl.mapper.LoanCityLogMapper;
import com.adpanshi.cashloan.business.cl.model.loancity.BaseLoanCityModel;
import com.adpanshi.cashloan.business.cl.service.LoanCityLogService;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.service.impl.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


/**
 * 消贷同城需求记录ServiceImpl
 * 
 * @author yecy
 * @version 1.0.0
 * @date 2018-01-01 18:08:00

 *
 *
 */
 
@Service("loanCityLogService")
public class LoanCityLogServiceImpl extends BaseServiceImpl<LoanCityLog, String> implements LoanCityLogService {
	
    private static final Logger logger = LoggerFactory.getLogger(LoanCityLogServiceImpl.class);
   
    @Resource
    private LoanCityLogMapper loanCityLogMapper;

	@Override
	public BaseMapper<LoanCityLog, String> getMapper() {
		return loanCityLogMapper;
	}

	@Override
	public boolean isLoanCityUser(String mobile) {
		Map<String, Object> map = new HashMap<>();
		map.put("mobile", mobile);
		int count = loanCityLogMapper.countSelective(map);
		return count > 0;
	}

	@Override
	public LoanCityLog countPublishLogByMobile(String mobile) {
		//同一个用户需求订单只允许有一条未完结状态
		Map<String, Object> map = new HashMap<>();
		map.put("mobile", mobile);
		map.put("state", BaseLoanCityModel.LoanCityStateEnum.RELATED.getCode());
		return loanCityLogMapper.findSelective(map);
	}

	@Override
	public LoanCityLog findLogByBorrowId(Long borrowMainId) {
		Map<String, Object> map = new HashMap<>();
		map.put("borrowMainId", borrowMainId);
		return loanCityLogMapper.findSelective(map);
	}
}
