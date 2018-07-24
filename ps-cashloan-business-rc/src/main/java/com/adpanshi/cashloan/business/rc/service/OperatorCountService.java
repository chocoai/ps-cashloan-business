package com.adpanshi.cashloan.business.rc.service;


import com.adpanshi.cashloan.business.core.common.service.BaseService;
import com.adpanshi.cashloan.business.rc.model.OperatorCountModel;

/**
 * 运营商信息统计

 * @version 1.0
 * @date 2017年4月18日上午10:34:26
 * Copyright 粉团网路 现金贷  All Rights Reserved
 *
 * 研发中心：rdc@fentuan123.com
 *
 */
public interface OperatorCountService extends BaseService<OperatorCountModel,String> {
	
	/**
	 * 通话信息
	 * @param phone
	 * @return
	 */
	int operatorCountVoice(Long userId);
	
	/**
	 * 联系人借款信息
	 * @param phone
	 * @return
	 */
	int operatorCountBorrow(Long userId);
	
}
