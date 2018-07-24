package com.adpanshi.cashloan.business.cr.service;

import com.adpanshi.cashloan.business.core.common.exception.CreditException;
import com.adpanshi.cashloan.business.core.common.service.BaseService;
import com.adpanshi.cashloan.business.cr.domain.CrResult;

/**
 * 信用评级及结果操作

 * @version 1.0.0
 * @date 2017年1月6日 上午10:41:22
 * Copyright 粉团网路 creditrank  All Rights Reserved
 *
 * 
 *
 */
public interface CreditRatingService extends BaseService<CrResult, Long> {

	/**
	 * 信用自动评分并保存记录
	 */
	CrResult saveCreditRating(String consumerNo, Long borrowTypeId) throws CreditException;
}
