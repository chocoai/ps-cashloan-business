package com.adpanshi.cashloan.business.rc.mapper;

import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;
import com.adpanshi.cashloan.business.rc.domain.UserAuthCopy;

import java.util.Map;

/**
 * 用户Dao
 * 无法用cl系统的UserAuth,只能先复制一份出来单独写
 *

 * @date 2017-06-14
 */
@RDBatisDao
public interface UserAuthCopyMapper extends BaseMapper<UserAuthCopy,Long> {


	/**
	 * 更新字段
	 * @param paramMap
	 * @return
	 */
	int updateByUserId(Map<String, Object> paramMap);

	/**
	 * 获取条带版本的数据
	 *
	 * @param paramMap
	 *            查询条件
	 * @return 查询结果
	 */
	UserAuthCopy findSelectiveWithVersion(Map<String, Object> paramMap);



}
