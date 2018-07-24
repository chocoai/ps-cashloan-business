package com.adpanshi.cashloan.business.cl.mapper;

import com.adpanshi.cashloan.business.cl.domain.UserSdkLog;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;

import java.util.Map;


/**
 * sdk识别记录表Dao
 * 
 * @author
 * @version 1.0.0
 * @date 2017-04-20 09:52:08
 *
 *
 *
 *
 */
@RDBatisDao
public interface UserSdkLogMapper extends BaseMapper<UserSdkLog,Long> {

	/**
	 * 查询当日可识别次数
	 * @param searchMap
	 * @return
	 */
	int countDayTime(Map<String, Object> searchMap);

    

}
