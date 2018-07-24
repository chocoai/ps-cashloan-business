package com.adpanshi.cashloan.business.cr.service;

import com.adpanshi.cashloan.business.core.common.exception.CreditException;
import com.adpanshi.cashloan.business.core.common.service.BaseService;
import com.adpanshi.cashloan.business.cr.domain.FactorParam;
import com.adpanshi.cashloan.business.cr.model.FactorParamModel;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/**
 * 评分因子参数Service
 * 
 * @author
 * @version 1.0.0
 * @date 2017-01-05 11:13:30
 * Copyright 粉团网路  creditrank All Rights Reserved
 *
 * 
 *
 */
public interface FactorParamService extends BaseService<FactorParam, Long>{


	/**
	 * 分页查看
	 * @param secreditrankhMap
	 * @param current
	 * @param pageSize
	 * @return
	 */
	Page<FactorParam> page(Map<String, Object> secreditrankhMap, int current,
                           int pageSize);

	/**
	 * 修改参数
	 * @param updateMap
	 * @return
	 */
	int updateSelective(Map<String, Object> updateMap);

	/**
	 * 查询所有参数
	 * @param param
	 * @return
	 */
	List<FactorParamModel> listSelect(Map<String, Object> param);

	/**
	 * 保存参数
	 * @param fp
	 * @return
	 */
	int save(FactorParam fp);

	/**
	 * 根据主键删除
	 * @param factorId 
	 * @param parseLong
	 * @return
	 * @throws CreditException 
	 */
	Map<String, Object> deleteSelective(long id) throws CreditException;

	/**
	 * 主键查询
	 * @param id
	 * @return
	 */
	FactorParam findByPrimary(long id);

	/**
	 * 引用删除
	 * @param id
	 * @return
	 */
	int deleteSelective(Long id);

	/**
	 * 计算最高因子参数分值
	 * @param factorId 
	 * @return
	 */
	int findMaxScore(long factorId);

}
