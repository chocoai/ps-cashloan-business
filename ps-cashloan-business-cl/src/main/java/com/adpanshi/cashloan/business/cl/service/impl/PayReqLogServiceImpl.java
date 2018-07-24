package com.adpanshi.cashloan.business.cl.service.impl;

import com.adpanshi.cashloan.business.cl.domain.PayReqLog;
import com.adpanshi.cashloan.business.cl.mapper.PayReqLogMapper;
import com.adpanshi.cashloan.business.cl.model.ManagePayReqLogModel;
import com.adpanshi.cashloan.business.cl.service.PayReqLogService;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.service.impl.BaseServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import tool.util.DateUtil;

import javax.annotation.Resource;
import java.util.Map;


/**
 * 支付请求记录ServiceImpl
 * 

 * @version 1.0.0
 * @date 2017-03-07 16:18:30
 *
 *
 * 
 *
 */
 
@Service("payReqLogService")
public class PayReqLogServiceImpl extends BaseServiceImpl<PayReqLog, Long> implements PayReqLogService {
	
    @Resource
    private PayReqLogMapper payReqLogMapper;

	@Override
	public boolean save(PayReqLog log) {
		log.setCreateTime(DateUtil.getNow());
		int result = payReqLogMapper.save(log);
		if (result > 0L) {
			return true;
		}
		return false;
	}


	@Override
	public Page<ManagePayReqLogModel> page(int current, int pageSize,Map<String, Object> searchMap) {
		PageHelper.startPage(current,pageSize);
		Page<ManagePayReqLogModel> page = (Page<ManagePayReqLogModel>) payReqLogMapper.page(searchMap);
		return page;
	}
	
	@Override
	public BaseMapper<PayReqLog, Long> getMapper() {
		return payReqLogMapper;
	}

	@Override
	public PayReqLog findPayReqLogByLastOrderNo(String orderNo) {
		return payReqLogMapper.findPayReqLogByLastOrderNo(orderNo);
	}
	
}