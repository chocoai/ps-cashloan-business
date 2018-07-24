package com.adpanshi.cashloan.business.core.common.db;


/**
 * @ClassName: DatabaseContextHolder
 * @Description: 多数据源选择器
 * @author zhubingbing
 * @date 2017年9月14日
 */ 
public class DatabaseContextHolder {
	
	private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

	/** 写数据库 */
	public static final String WRITE_DB = "writeDataSource";
	
	/** 读数据库 */
	public static final String READ_DB = "readDataSource";

	public static void setDbName(String dbName) {
		contextHolder.set(dbName);
	}

	public static String getDbName() {
		return contextHolder.get();
	}

	public static void clearDbName() {
		contextHolder.remove();
	}
}
