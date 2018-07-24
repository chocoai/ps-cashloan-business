package com.adpanshi.cashloan.business.cr.mapper;

import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;
import com.adpanshi.cashloan.business.cr.domain.Card;

import java.util.List;
import java.util.Map;

/**
 * 评分卡Dao
 * 
 * @author
 * @version 1.0.0
 * @date 2017-01-04 15:06:51
 * Copyright 粉团网路  creditrank All Rights Reserved
 *
 * 
 *
 */
@RDBatisDao
public interface CardMapper extends BaseMapper<Card,Long> {

	/**
	 * 根据cardName返回数据
	 * @param cardName
	 * @return
	 */
	Card findByCardName(String cardName);

	/**
	 * 禁用评分卡
	 * @param map
	 * @return
	 */
	int updateState(Map<String, Object> map);
	
	/**
	 * 标记评分卡是否已经被使用
	 * @param id
	 * @return
	 */
	int updateType(Long id);
	
	/**
	 * 查询所有评分卡
	 * @return
	 */
	List<Card> findAll();
}
