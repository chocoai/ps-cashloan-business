package com.adpanshi.cashloan.business.cr.mapper;

import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;
import com.adpanshi.cashloan.business.cr.domain.CrResultDetail;
import com.adpanshi.cashloan.business.cr.model.CrResultDetailModel;
import com.adpanshi.cashloan.business.cr.model.CrResultFactorDetail;
import com.adpanshi.cashloan.business.cr.model.CrResultItemDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 评分结果Dao
 * 

 * @version 1.0.0
 * @date 2017-01-05 16:46:34
 * Copyright 粉团网路  creditrank All Rights Reserved
 *
 * 
 *
 */
@RDBatisDao
public interface CrResultDetailMapper extends BaseMapper<CrResultDetail,Long> {

	List<CrResultDetail> listResultDetail(Map<String, Object> secreditrankhMap);

	/**
	 * 统计因子得分
	 * @param resultId
	 * @return
	 */
	List<CrResultDetail> countFactorScore(@Param("resultId") Long resultId);

	/**
	 * 统计项目得分
	 * @param resultId
	 * @return
	 */
	List<CrResultDetail> countItemScore(@Param("resultId") Long resultId);

	/**
	 * 统计评分卡得分
	 * @param resultId
	 * @return
	 */
	List<CrResultDetail> countCardScore(@Param("resultId") Long resultId);

	/**
	 * 保存统计结果
	 * @param list
	 */
	void saveCountScore(List<CrResultDetail> list);

	/**
	 * 根据级别查询评分结果
	 * @param resultId
	 * @param level
	 * @return
	 */
	List<CrResultDetailModel> findDetail(@Param("resultId") Long resultId, @Param("level") String level);
	List<CrResultFactorDetail> findFactorDetail(@Param("resultId") Long resultId, @Param("level") String level);
	List<CrResultItemDetail> findItemDetail(@Param("resultId") Long resultId, @Param("level") String level);
}
