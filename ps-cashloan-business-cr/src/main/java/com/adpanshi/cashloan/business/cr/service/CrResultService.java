package com.adpanshi.cashloan.business.cr.service;


import com.adpanshi.cashloan.business.core.common.service.BaseService;
import com.adpanshi.cashloan.business.cr.domain.CrResult;

import java.util.List;
import java.util.Map;

/**
 * 评分结果Service
 * 

 * @version 1.0.0
 * @date 2017-01-05 16:22:54
 * Copyright 粉团网路  creditrank All Rights Reserved
 *
 * 
 *
 */
public interface CrResultService extends BaseService<CrResult, Long>{

	/**
	 * 统计用户的总评分和总额度
	 * @param userId
	 * @return
	 */
	public Map<String,Object> findUserResult(Long userId);
	
	/**
	 * 查询用户各借款类型的总额度
	 * @param userId
	 * @return
	 */
	public List<CrResult> findAllBorrowTypeResult(Long userId);
	
}
