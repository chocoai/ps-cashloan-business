package com.adpanshi.cashloan.business.cl.mapper;

import com.adpanshi.cashloan.business.cl.domain.UserMessages;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户资料--联系人Dao
 * 

 * @version 1.0.0
 * @date 2017-03-04 11:54:57
 *
 *
 * 
 *
 */
@RDBatisDao
public interface UserMessagesMapper extends BaseMapper<UserMessages,Long> {

	/**
	 * 通过userid删除数据
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	int deleteBatchByUserId(@Param("list") List<Long> list);
	
}
