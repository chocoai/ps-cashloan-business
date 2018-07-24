package com.adpanshi.cashloan.business.core.common.db;


/**
 * @ClassName: DataSource
 * @Description: 注解@DataSource既可以加在方法上，也可以加在接口或者接口的实现类上，优先级别：方法>实现类>接口。
 *               如果接口、接口实现类以及方法上分别加了@DataSource注解来指定数据源，则优先以方法上指定的为准。
 * @author zhubingbing
 * @date 2017年9月14日
 */ 
public @interface DataSource {
	
	 /**
     * 
     * 
     * @Title: name
     * 
     * @Description: 如果仅标注@DataSource 默认为DatabaseContextHolder.WRITE_DB数据库实例
     * 
     * @return
     * 
     * @return: String
     */
    String name() default DatabaseContextHolder.WRITE_DB;
}
