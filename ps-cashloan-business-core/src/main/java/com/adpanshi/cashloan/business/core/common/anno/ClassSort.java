package com.adpanshi.cashloan.business.core.common.anno;

import java.lang.annotation.*;

/***
 ** @category 用于对使用该注解的类进行排序
 ** @author HuangQin
 ** @email: huangqin@fentuan360.com
 ** @createTime: 2018年3月1日 10:35:06
 **/
@Target({ElementType.TYPE})
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface ClassSort {
	
	/**[对需要排序的类设置相同的类别)**/
	String category() default "default";
	
	/**[排序顺序)*/
	int sortNum() default 0;
	
}
