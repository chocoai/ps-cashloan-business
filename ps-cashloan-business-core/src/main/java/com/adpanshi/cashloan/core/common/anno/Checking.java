package com.adpanshi.cashloan.core.common.anno;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/***
 ** @category API统一校验(入参格式、长度、非空、剪切等...)
 ** @author qing.yunhui
 ** @email: qingyunhui@smoney.cc
 ** @createTime: 2017年11月13日下午8:21:06
 **/
@Target({ElementType.PARAMETER})
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface Checking {
	
	/**[可以搭配truncate()进行栽剪]最大长度校验(当值为-1时则跳过校验，当值>0时进行长度校验)**/
	int maxSize() default -1;
	
	/**[只有当设置了maxSize()时才会生效]true:剪切不超过maxSize个长度,false:不剪切 (必须是在指定maxSize时才有效)*/
	boolean truncate() default false;
	
	/**非空(null或空值)校验(当值为false时则跳过校验,当值为true时进行非空校验)*/
	boolean required() default false;

	/**要校验的字段名称(必填)*/
	String value();
	
	/**校验的字段是否是json串*/
	boolean json() default false;
	
	
}
