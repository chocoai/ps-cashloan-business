package com.adpanshi.cashloan.business.cl.mapper;

import com.adpanshi.cashloan.business.cl.domain.UserCardCreditLog;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;

import java.util.Map;

/**
 * 人脸识别请求记录Dao
 * 

 * @version 1.0.0
 * @date 2017-04-10 14:37:56
 *
 *
 * 
 *
 */
@RDBatisDao
public interface UserCardCreditLogMapper extends BaseMapper<UserCardCreditLog,Long> {
	/**
	 * 获取用户当天请求次数
	 * @param paramMap
	 * @return
	 */
	int countByUserId(Map<String, Object> paramMap);

    

}
