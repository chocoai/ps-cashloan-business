package com.adpanshi.cashloan.business.cl.service;

import com.adpanshi.cashloan.business.cl.domain.Opinion;
import com.adpanshi.cashloan.business.cl.model.OpinionModel;
import com.adpanshi.cashloan.business.core.common.service.BaseService;
import com.github.pagehelper.Page;

import java.util.Map;


/**
 * 意见反馈表Service
 * 
 * @author
 * @version 1.0.0
 * @date 2017-02-14 11:30:57
 *
 *
 * 
 *
 */
public interface OpinionService extends BaseService<Opinion, Long> {
	
	int save(long userId, String opinion);
	
	int updateSelective(Map<String, Object> searchMap);

	Page<OpinionModel> page(Map<String, Object> searchMap, int current, int pageSize);

}
