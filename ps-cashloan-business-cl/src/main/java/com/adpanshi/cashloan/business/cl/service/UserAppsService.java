package com.adpanshi.cashloan.business.cl.service;

import com.adpanshi.cashloan.business.rule.domain.UserApps;

import java.util.List;
import java.util.Map;

/**
 * @author qing.yunhui 
 * @Since 2011-2017
 * @create 2017-09-11 11:03:41
 * @history
 */
public interface UserAppsService{
	
	/**
	 * <p>根据给定数据新增(userId必填,先查询待新增的数据是否存在，如果存在则跳过,否则新增)</p>
	 * @param userApps
	 * @return count 受影响的行数
	 * */
	int save(UserApps userApps);
	
	/**
	 * <p>批量新增处理</p>
	 * @param userAppsList 待新增的对象
	 * @param return int 受影响的行数
	 * */
	int saveBatchHandle(List<UserApps> userAppsList);

	/**
	 * <p>更新</p>
	 * @param userApps 待更新的对象(userId 必填)
	 * @param return int 受影响的行数
	 * */
	int updateSelective(UserApps userApps);

	/**
	 * <p>根据给定的主键查找</p>
	 * @param userId 用户id(根据userId构造表名)
	 * @param id
	 * @param return int 受影响的行数
	 * */
	UserApps findByPrimary(Long userId, Long id);

	/**
	 * <p>根据给定的条件查询</p>
	 * @param userId 用户id(根据userId构造表名)
	 * @param userApps
	 * @return List<UserApps>
	 * */
	List<UserApps> listSelective(Long userId, Map<String, Object> userApps);

}