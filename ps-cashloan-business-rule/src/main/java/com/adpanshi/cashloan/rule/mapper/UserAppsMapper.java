
package com.adpanshi.cashloan.rule.mapper;

import com.adpanshi.cashloan.core.common.mapper.RDBatisDao;
import com.adpanshi.cashloan.rule.domain.UserApps;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author qing.yunhui 
 * @Since 2011-2017
 * @create 2017-09-11 11:03:41
 * @history
 */
@RDBatisDao
public interface UserAppsMapper {
	
	/**
	 * <p>新增</p>
	 * @param tableName 待操作的表
	 * @param userApps 待新增的对象
	 * @return int 受影响的行数
	 * */
	int save(@Param("tableName") String tableName, @Param("item") UserApps userApps);

	/**
	 * <p>批量新增</p>
	 * @param userAppsList 待操作的表
	 * @param tableName 待新增的对象
	 * @return int 受影响的行数
	 * */
	int insertBatch(@Param("tableName") String tableName, @Param("userAppsList") List<UserApps> userAppsList);

	/**
	 * <p>删除</p>
	 * @param tableName 待操作的表
	 * @param id 待删除的id
	 * @return int 受影响的行数
	 * */
	int deleteById(@Param("tableName") String tableName, @Param("id") Long id);

	/**
	 * <p>更新</p>
	 * @param tableName 待操作的表
	 * @param userApps 待更新的对象
	 * @return int 受影响的行数
	 * */
	int updateSelective(@Param("tableName") String tableName, @Param("item") UserApps userApps);

	/**
	 * <p>根据给定的主键查找</p>
	 * @param tableName 待操作的表
	 * @param id
	 * @return int 受影响的行数
	 * */
	UserApps findByPrimary(@Param("tableName") String tableName, @Param("id") Long id);

	/**
	 * <p>根据给定的条件查询</p>
	 * @param tableName 待操作的表
	 * @param userApps
	 * @return List<UserApps>
	 * */
	List<UserApps> listSelective(@Param("tableName") String tableName, @Param("item") Map<String, Object> userApps);
	
}
