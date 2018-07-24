package com.adpanshi.cashloan.business.cl.service.impl;

import com.adpanshi.cashloan.business.cl.domain.PayCheck;
import com.adpanshi.cashloan.business.cl.mapper.PayCheckMapper;
import com.adpanshi.cashloan.business.cl.model.ManagePayCheckModel;
import com.adpanshi.cashloan.business.cl.service.PayCheckService;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.service.impl.BaseServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 资金对账记录ServiceImpl
 * 

 * @version 1.0.0
 * @date 2017-04-13 17:12:20
 * Copyright 粉团网路   All Rights Reserved
 *
 *
 */
@Service("payCheckService")
public class PayCheckServiceImpl extends BaseServiceImpl<PayCheck, Long> implements PayCheckService {
	
    private static final Logger logger = LoggerFactory.getLogger(PayCheckServiceImpl.class);
   
    @Resource
    private PayCheckMapper payCheckMapper;

	@Override
	public BaseMapper<PayCheck, Long> getMapper() {
		return payCheckMapper;
	}

	@Override
	public boolean save(PayCheck payCheck) {
		int result = payCheckMapper.save(payCheck);
		if (result > 0) {
			return true;
		}
		return false;
	}

	@Override
	public Page<ManagePayCheckModel> page(int current, int pageSize,
			Map<String, Object> searchMap) {
		PageHelper.startPage(current, pageSize);
		Page<ManagePayCheckModel> page = (Page<ManagePayCheckModel>) payCheckMapper.page(searchMap);
		return page;
	}

	@Override
	public PayCheck findSelective(Map<String, Object> searchMap) {
		PayCheck payCheck = null;
		try {
			payCheck = payCheckMapper.findSelective(searchMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return payCheck;
	}

}