package com.adpanshi.cashloan.business.rule.mapper;

import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;
import com.adpanshi.cashloan.business.rule.domain.UserContacts;
import com.adpanshi.cashloan.business.rule.domain.UserContactsMatch;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 用户资料--联系人Dao
 * 

 * @version 1.0.0
 * @date 2017-03-04 11:52:26
 *
 *
 * 
 *
 */
@RDBatisDao
public interface UserContactsMapper extends BaseMapper<UserContacts,Long> {
	
	/**
	 * 根据表名查询表数量
	 * @param tableName
	 * @return
	 */
	int countTable(String tableName);
	
	/**
	 * 根据表名创建表
	 * @param tableName
	 */
	void createTable(@Param("tableName") String tableName);
	
	/**
	 * (分表)新增
	 * @param tableName
	 * @param userContacts
	 * @return
	 */
	int saveShard(@Param("tableName") String tableName, @Param("item") UserContacts userContacts);

	/**
	 * 删除原有记录
	 * @param tableName
	 * @param userId
	 * @return
	 */
	int deleteShardByUserId(@Param("tableName") String tableName, @Param("userId") long userId);

	/**
	 * 根据参数(分表)查询
	 * @param tableName
	 * @param params
	 * @return
	 */
	List<UserContacts> listShardSelective(
            @Param("tableName") String tableName,
            @Param("params") Map<String, Object> params);


	/**
	 * 做了字典匹配的通讯录
	 * @param tableName
	 * @param params
	 * @return
	 */
    List<UserContactsMatch> listShardSelectiveNew(@Param("tableName") String tableName,
                                                  @Param("params") Map<String, Object> params);
	
	/**
	 * 通过userid删除数据
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	int deleteBatchByUserId(@Param("list") List<Long> list, @Param("tableName") String tableName);
}
