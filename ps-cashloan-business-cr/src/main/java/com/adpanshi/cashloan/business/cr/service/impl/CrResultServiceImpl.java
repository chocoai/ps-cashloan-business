package com.adpanshi.cashloan.business.cr.service.impl;


import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.service.impl.BaseServiceImpl;
import com.adpanshi.cashloan.business.cr.domain.CrResult;
import com.adpanshi.cashloan.business.cr.mapper.CrResultMapper;
import com.adpanshi.cashloan.business.cr.service.CrResultService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * 评分结果ServiceImpl
 * 

 * @version 1.0.0
 * @date 2017-01-05 16:22:54
 * Copyright 粉团网路  creditrank All Rights Reserved
 *
 * 
 *
 */
 
@Service("crResultService")
public class CrResultServiceImpl extends BaseServiceImpl<CrResult, Long> implements CrResultService {
	
    @Resource
    private CrResultMapper crResultMapper;

	@Override
	public BaseMapper<CrResult, Long> getMapper() {
		return crResultMapper;
	}

	@Override
	public Map<String, Object> findUserResult(Long userId) {
		return crResultMapper.findUserResult(userId);
	}

	@Override
	public List<CrResult> findAllBorrowTypeResult(Long userId) {
		return crResultMapper.findAllBorrowTypeResult(userId);
	}
}