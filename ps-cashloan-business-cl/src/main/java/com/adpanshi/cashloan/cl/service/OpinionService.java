package com.adpanshi.cashloan.cl.service;

import com.adpanshi.cashloan.cl.domain.Opinion;
import com.adpanshi.cashloan.cl.model.OpinionModel;
import com.adpanshi.cashloan.core.common.service.BaseService;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;


/**
 * 意见反馈表Service
 * 
 * @author
 * @version 1.0.0
 * @date 2017-02-14 11:30:57
 *
 */
public interface OpinionService extends BaseService<Opinion, Long> {

	/**
	 * 保存
	 * @param userId
	 * @param opinion
	 * @return
	 */
	int save(long userId, String opinion);

	/**
	 * 更新
	 * @param searchMap
	 * @return
	 */
	int updateSelective(Map<String, Object> searchMap);

	/**
	 * page
	 * @param searchMap
	 * @param current
	 * @param pageSize
	 * @return
	 */
	Page<OpinionModel> page(Map<String, Object> searchMap, int current, int pageSize);

}
