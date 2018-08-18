package com.adpanshi.cashloan.core.common.service.impl;

import com.adpanshi.cashloan.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.core.common.service.BaseService;

import javax.annotation.Resource;
import java.io.Serializable;

/**
 * @author 8452
 * @param <T>
 * @param <ID>
 */
public abstract class BaseServiceImpl<T, ID extends Serializable> implements BaseService<T, ID> {
	
	@Resource
	private BaseMapper<T, ID> baseMapper;

	@Override
	public int insert(T record) {
		return getMapper().save(record);
	}

	@Override
	public T getById(ID id) {
		return getMapper().findByPrimary(id);
	}

	@Override
	public int updateById(T record) {
		return getMapper().update(record);
	}

	public abstract BaseMapper<T, ID> getMapper();

}
