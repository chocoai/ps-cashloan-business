package com.adpanshi.cashloan.business.core.common.util;

import java.util.List;
import java.util.Map;

/**
 * json工具类
 * @author mujuezhike
 */
public interface FastJsonUtil {
	
	/**
	 * 转成string
	 * @param o
	 * @return
	 */
	String toString(Object o);
	
	/**
	 * 转成bject
	 * @param json
	 * @param clz 指定要转成的Object类型
	 * @return
	 */
	<T> T toObject(String json, Class<T> clz);
	
	/**
	 * 转成map
	 * @param json
	 * @return
	 */
	Map<?, ?> toMap(String json);

	/**
	 * 转成list
	 * @param json
	 * @param clz 指定要转成的Object类型
	 * @return
	 */
	<T> List<T> toObjectArray(String json, Class<T> clz);
	
}
