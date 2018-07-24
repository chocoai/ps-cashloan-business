package com.adpanshi.cashloan.business.cr.mapper;

import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;
import com.adpanshi.cashloan.business.cr.domain.FactorParam;
import com.adpanshi.cashloan.business.cr.model.FactorParamModel;

import java.util.List;
import java.util.Map;

/**
 * 评分因子参数Dao
 * 
 * @author
 * @version 1.0.0
 * @date 2017-01-05 11:13:30
 * Copyright 粉团网路  creditrank All Rights Reserved
 *
 * 
 *
 */
@RDBatisDao
public interface FactorParamMapper extends BaseMapper<FactorParam,Long> {

	/**
	 * 查询列表返回FactorParamModel
	 * @param param
	 * @return
	 */
	List<FactorParamModel> listSelect(Map<String, Object> param);

	/**
	 * 根据主键删除
	 * @param parseLong
	 * @return
	 */
	int deleteSelective(long id);

	/**
	 * 计算最高因子参数分值
	 * @param factorId 
	 * @return
	 */
	int findMaxScore(long factorId);

}
