package com.adpanshi.cashloan.business.core.common.db;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @ClassName: MultipleDataSource
 * @Description: 多数据源路由	
 * @author zhubingbing
 * @date 2017年9月14日
 */ 
public class MultipleDataSource extends AbstractRoutingDataSource{
	
	@Override
	protected Object determineCurrentLookupKey() {
		
		return DatabaseContextHolder.getDbName();
	}

}
