package com.adpanshi.cashloan.business.cr.service;

import com.adpanshi.cashloan.business.core.common.service.BaseService;
import com.adpanshi.cashloan.business.cr.domain.CrResultDetail;
import com.adpanshi.cashloan.business.cr.model.CrResultDetailModel;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/**
 * 评分结果Service
 * 

 * @version 1.0.0
 * @date 2017-01-05 16:46:34
 * Copyright 粉团网路  creditrank All Rights Reserved
 *
 * 
 *
 */
public interface CrResultDetailService extends BaseService<CrResultDetail, Long>{

	Page<CrResultDetail> page(Map<String, Object> secreditrankhMap, int current, int pageSize);

	List<CrResultDetailModel> listDetailTree(Long resultId);

	List<CrResultDetail> listInfo(Long cardId);
}
