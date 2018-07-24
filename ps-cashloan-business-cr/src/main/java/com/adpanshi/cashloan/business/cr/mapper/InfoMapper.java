package com.adpanshi.cashloan.business.cr.mapper;

import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;
import com.adpanshi.cashloan.business.cr.domain.Info;
import com.adpanshi.cashloan.business.cr.model.InfoModel;

import java.util.List;
import java.util.Map;

/**
 * 评分关联表Dao
 * 
 * @author
 * @version 1.0.0
 * @date 2017-01-04 15:05:09
 * Copyright 粉团网路  creditrank All Rights Reserved
 *
 * 
 *
 */
@RDBatisDao
public interface InfoMapper extends BaseMapper<Info,Long> {

	/**
	 * 根据tbNid返回数据
	 * @param tbNid
	 * @return
	 */
    Info findByTbNid(String tbNid);

    /**
     * 列表查询返回InfoModel
     * @param secreditrankhMap
     * @return
     */
	List<InfoModel> listSelect(Map<String, Object> secreditrankhMap);
	
	/**
     * 查询数据表信息
     */
	List<Map<String, Object>> findTable();

	/**
	 * 查询数据库表字段信息
	 */
	List<Map<String, Object>> findColumnByName(Map<String, Object> map);

}
