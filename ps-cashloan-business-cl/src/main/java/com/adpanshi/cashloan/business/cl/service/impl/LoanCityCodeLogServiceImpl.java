package com.adpanshi.cashloan.business.cl.service.impl;

import com.adpanshi.cashloan.business.cl.constant.LoanCityConstant;
import com.adpanshi.cashloan.business.cl.domain.loancity.LoanCityCodeLog;
import com.adpanshi.cashloan.business.cl.domain.loancity.LoanCityLog;
import com.adpanshi.cashloan.business.cl.mapper.LoanCityCodeLogMapper;
import com.adpanshi.cashloan.business.cl.mapper.LoanCityLogMapper;
import com.adpanshi.cashloan.business.cl.model.loancity.BaseLoanCityModel;
import com.adpanshi.cashloan.business.cl.service.LoanCityCodeLogService;
import com.adpanshi.cashloan.business.core.common.context.Global;
import com.adpanshi.cashloan.business.core.common.exception.BussinessException;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.service.impl.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * 用户输入验证码记录ServiceImpl
 * 
 * @author yecy
 * @version 1.0.0
 * @date 2018-01-01 18:51:26

 *
 *
 */
 
@Service("loanCityCodeLogService")
public class LoanCityCodeLogServiceImpl extends BaseServiceImpl<LoanCityCodeLog, Long> implements LoanCityCodeLogService {
	
    private static final Logger logger = LoggerFactory.getLogger(LoanCityCodeLogServiceImpl.class);
   
    @Resource
    private LoanCityCodeLogMapper loanCityCodeLogMapper;
    @Resource
	private LoanCityLogMapper loanCityLogMapper;

	@Override
	public BaseMapper<LoanCityCodeLog, Long> getMapper() {
		return loanCityCodeLogMapper;
	}

	@Override
	public boolean codeIsSuccess(Long borrowMainId) {
		Map<String,Object> map = new HashMap<>();
		map.put("borrowMainId",borrowMainId);
		map.put("success",true);
		LoanCityCodeLog codeLog = loanCityCodeLogMapper.findSelective(map);
		return codeLog != null;
	}

	@Override
	public Map<String,Object> checkCode(Long borrowMainId, String idCode) {
		Map<String, Object> map = new HashMap<>();
		map.put("borrowMainId", borrowMainId);
		LoanCityLog loanCityLog = loanCityLogMapper.findSelective(map);
		if (loanCityLog == null){
			throw new BussinessException("该订单不需要输入验证码。");
		}
		if (!BaseLoanCityModel.LoanCityStateEnum.PAID.getCode().equals(loanCityLog.getState())){
			throw new BussinessException("该订单目前不允许输入验证码。");
		}

		Map<String,Object> codeMap = new HashMap<>();
		codeMap.put("borrowMainId",borrowMainId);
		codeMap.put("success",true);
		LoanCityCodeLog codeLog = loanCityCodeLogMapper.findSelective(codeMap);

		if (codeLog != null){
			throw new BussinessException("该订单已经输入正确的验证码，不需要重新输入。");
		}

		int maxCount = Global.getInt(LoanCityConstant.LOANCITY_MAX_CODE_RETRIES);
		int retryCount = loanCityLog.getCodeRetryCount();
		if (retryCount >= maxCount){
			throw new BussinessException("输入错误已达上限，请联系客服。");
		}

		Boolean success = idCode.equalsIgnoreCase(loanCityLog.getIdentifyingCode());
		LoanCityCodeLog newCodeLog = new LoanCityCodeLog();
		newCodeLog.setBorrowMainId(borrowMainId);
		newCodeLog.setIdCode(idCode);
		newCodeLog.setCreateTime(new Date());
		newCodeLog.setSuccess(success);
		loanCityCodeLogMapper.save(newCodeLog);
		if (!success){
			retryCount++;
			loanCityLog.setCodeRetryCount(retryCount);
			loanCityLogMapper.update(loanCityLog);
		}
		Map<String,Object> result = new HashMap<>();
		result.put("success",success);
		result.put("remainCount",maxCount- retryCount);

		return result;
	}
}
