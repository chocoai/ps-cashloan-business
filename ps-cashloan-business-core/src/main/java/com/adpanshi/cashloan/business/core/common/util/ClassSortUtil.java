package com.adpanshi.cashloan.business.core.common.util;

import com.adpanshi.cashloan.business.core.common.anno.ClassSort;

public class ClassSortUtil {

	/**
	 * 对相同类别的类进行排序
	 * @param clazzs 类数据
	 * @return Class[]
	 */
	public static Class[] sortClass(Class[] clazzs) {
		if(null == clazzs || clazzs.length<=0){
			return clazzs;
		}
		Class temporaryClazzs;
		for(int i=0;i<clazzs.length-1;i++){
			for(int j=0;j<clazzs.length-i-1;j++){
				if(((ClassSort)clazzs[j+1].getAnnotation(ClassSort.class)).sortNum()<((ClassSort)clazzs[j].getAnnotation(ClassSort.class)).sortNum()){
					temporaryClazzs = clazzs[j];
					clazzs[j] = clazzs[j+1];
					clazzs[j+1] = temporaryClazzs;
				}
			}
		}
		return clazzs;
	}
}
