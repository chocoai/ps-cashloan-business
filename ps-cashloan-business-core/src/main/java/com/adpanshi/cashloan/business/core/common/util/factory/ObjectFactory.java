package com.adpanshi.cashloan.business.core.common.util.factory;

import com.adpanshi.cashloan.business.core.common.exception.ManageException;

/**
 * 对象工厂

 *
 * @param <T>
 */
public interface ObjectFactory<T> {
	/**
	 * 从工厂中获取对象
	 * @param qualifier 限定符
	 * @return
	 * @throws ManageException
	 */
	T getObject(Object qualifier) throws ManageException;
}
