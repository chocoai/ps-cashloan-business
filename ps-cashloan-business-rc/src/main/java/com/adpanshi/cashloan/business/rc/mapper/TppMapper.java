package com.adpanshi.cashloan.business.rc.mapper;

import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;
import com.adpanshi.cashloan.business.rc.domain.Tpp;
import com.adpanshi.cashloan.business.rc.model.ManageTppModel;
import com.adpanshi.cashloan.business.rc.model.TppModel;

import java.util.List;
import java.util.Map;

/**
 * 第三方征信信息Dao
 * 

 * @version 1.0.0
 * @date 2017-03-14 13:41:05
 *
 *
 *
 *
 */
@RDBatisDao
public interface TppMapper extends BaseMapper<Tpp,Long> {

	/**
	 * 查询所有
	 * 
	 * @return
	 */
	List<TppModel> listAll();

	/**
	 * 条件查询List
	 * 
	 * @param paramMap
	 * @return
	 */
	List<ManageTppModel> list(Map<String, Object> paramMap);

	/**
	 * 遍历所有第三方信息
	 * 
	 * @return
	 */
	List<TppModel> listAllWithBusiness();
}
