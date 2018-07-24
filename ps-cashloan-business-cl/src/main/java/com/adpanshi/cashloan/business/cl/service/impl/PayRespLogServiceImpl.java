package com.adpanshi.cashloan.business.cl.service.impl;

import com.adpanshi.cashloan.business.cl.domain.PayRespLog;
import com.adpanshi.cashloan.business.cl.mapper.PayRespLogMapper;
import com.adpanshi.cashloan.business.cl.model.ManagePayRespLogModel;
import com.adpanshi.cashloan.business.cl.service.PayRespLogService;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.service.impl.BaseServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import tool.util.DateUtil;

import javax.annotation.Resource;
import java.util.Map;


/**
 * 支付响应记录ServiceImpl
 * 

 * @version 1.0.0
 * @date 2017-03-07 16:18:10
 *
 *
 * 
 *
 */
 
@Service("payRespLogService")
public class PayRespLogServiceImpl extends BaseServiceImpl<PayRespLog, Long> implements PayRespLogService {
	
    @Resource
    private PayRespLogMapper payRespLogMapper;

    
	@Override
	public boolean save(PayRespLog log) {
		log.setCreateTime(DateUtil.getNow());
		int result = payRespLogMapper.save(log);
		if (result > 0L) {
			return true;
		}
		return false;
	}

	
	@Override
	public Page<ManagePayRespLogModel> page(int current, int pageSize,
			Map<String, Object> searchMap) {
		PageHelper.startPage(current,pageSize);
		Page<ManagePayRespLogModel>  page = (Page<ManagePayRespLogModel>) payRespLogMapper.page(searchMap);
		return page;
	}

	@Override
	public BaseMapper<PayRespLog, Long> getMapper() {
		return payRespLogMapper;
	}
}