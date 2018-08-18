package com.adpanshi.cashloan.core.common.util;

import java.util.Arrays;

/**
 * 数组工具
 * @author 8452
 */
public class ArrayUtil {

	/**
	 * 数组合并
	 * 
	 * @param first 第一个数组
	 * @param second 第二个数组
	 * @return
	 */
	public static <T> T[] concat(T[] first, T[] second) {
		T[] result = Arrays.copyOf(first, first.length + second.length);
		System.arraycopy(second, 0, result, first.length, second.length);
		return result;
	}
}
