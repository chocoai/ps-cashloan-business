package com.adpanshi.cashloan.core.common.service;

import java.io.Serializable;

/**
 * 基类接口定义

 * @version 1.0
 * @date 2016年10月24日 上午10:23:20
 * Copyright 粉团网路  All Rights Reserved
 * @author 8452
 *
 */
public interface BaseService<T, ID extends Serializable> {

	/**
	 * 插入数据库操作
	 * @param record
	 * @return
	 */
	int insert(T record);

	/**
	 * 通过id更新数据库数据
	 * @param record
	 * @return
	 */
	int updateById(T record);

	/**
	 * 通过id查询数据库信息
	 * @param id
	 * @return
	 */
	T getById(ID id);
	

}
