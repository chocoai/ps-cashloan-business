package com.adpanshi.cashloan.business.cr.service;

import com.adpanshi.cashloan.business.core.common.service.BaseService;
import com.adpanshi.cashloan.business.cr.domain.Info;
import com.adpanshi.cashloan.business.cr.model.InfoModel;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/**
 * 评分关联表Service
 * 
 * @author
 * @version 1.0.0
 * @date 2017-01-04 15:05:09
 * Copyright 粉团网路  creditrank All Rights Reserved
 *
 * 
 *
 */
public interface InfoService extends BaseService<Info, Long>{

	/**
	 * 根据tbNid查询
	 * @param tbNid
	 * @return
	 */
	Info findByTbNid(String tbNid);
	
	/**
	 * 保存数据
	 * @param tbNid
	 * @param tbName
	 * @param detail
	 * @return
	 */
	int save(String tbNid, String tbName, String detail);

	/**
	 * 分页查询
	 * @param secreditrankhMap
	 * @param current
	 * @param pageSize
	 * @return
	 */
	Page<InfoModel> page(Map<String, Object> secreditrankhMap, int current, int pageSize);

	/**
	 * 修改数据
	 * @param updateMap
	 * @return
	 */
	int updateSelective(Map<String, Object> updateMap);

	/**
	 * 查询所有
	 * @param map
	 * @return
	 */
	List<Info> listSelective(Map<String, Object> map);

	List<Map<String, Object>> findTable();

	List<Map<String, Object>> findColumnByName(Map<String, Object> map);

	/**
	 * 主键查询
	 * @param id
	 * @return
	 */
	Info findByPrimary(long id);

}
