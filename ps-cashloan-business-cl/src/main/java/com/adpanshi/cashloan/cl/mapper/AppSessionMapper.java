package com.adpanshi.cashloan.cl.mapper;

import com.adpanshi.cashloan.cl.domain.AppSession;
import com.adpanshi.cashloan.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.core.common.mapper.RDBatisDao;

/**
 * @author qing.yunhui 
 * @Since 2011-2017
 * @create 2017-12-18 16:42:01
 * @history
 */
@RDBatisDao
public interface AppSessionMapper extends BaseMapper<AppSession,Long>{
	
	/**
	 * <p>根据userId查询最新的一条记录</p>
	 * @param userId
	 * @return AppSession
	 * */
	AppSession queryLateAppSessionByUserId(Long userId);
	
	/**
	 * <p>根据token查询最新的一条记录</p>
	 * @param token
	 * @return AppSession
	 * */
	AppSession queryLateAppSessionByToken(String token);
	
	/**
	 * <p>根据给定token删除</p>
	 * @param token
	 * @return int
	 * */
	int deleteByToken(String token);
	
	/**
	 * <p>根据给定id删除</p>
	 * @return id
	 * @return 受影响的行数
	 * */
	int deleteById(Long id);
	
}
