package com.adpanshi.cashloan.business.core.common.service;

import java.io.Serializable;

/**
 * 基类接口定义

 * @version 1.0
 * @date 2016年10月24日 上午10:23:20
 * Copyright 粉团网路  All Rights Reserved
 *
 * 
 *
 */
public interface BaseService<T, ID extends Serializable> {

	int insert(T record);


	int updateById(T record);
	
	
	T getById(ID id);
	

}
