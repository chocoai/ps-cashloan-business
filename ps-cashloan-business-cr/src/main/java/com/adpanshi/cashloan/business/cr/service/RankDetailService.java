package com.adpanshi.cashloan.business.cr.service;

import com.adpanshi.cashloan.business.core.common.service.BaseService;
import com.adpanshi.cashloan.business.cr.domain.RankDetail;
import com.github.pagehelper.Page;

import java.util.Map;

/**
 * 评分卡等级详情表Service
 * 
 * @author
 * @version 1.0.0
 * @date 2017-02-06 11:27:25
 *
 *
 * 
 *
 */
public interface RankDetailService extends BaseService<RankDetail, Long>{

	/**
	 * 保存数据
	 * @param rankDetail
	 * @return
	 */
	int save(RankDetail rankDetail);
	
	/**
	 * 修改数据
	 * @param rankDetailMap
	 * @return
	 */
	int updateSelective(Map<String, Object> rankDetailMap);

	/**
	 * 查询
	 * @param searchMap
	 * @param current
	 * @param pageSize
	 * @return
	 */
	Page<RankDetail> page(Map<String, Object> searchMap, int current, int pageSize);

}
