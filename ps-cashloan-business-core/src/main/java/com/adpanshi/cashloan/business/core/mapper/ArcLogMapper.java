package com.adpanshi.cashloan.business.core.mapper;

import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;
import com.adpanshi.cashloan.business.core.domain.ArcLog;

/**
 * APP用户操作日志表Dao
 * 
 * @author tq
 * @version 1.0.0
 * @date 2018-06-19 11:17:57

 *
 *
 */
@RDBatisDao
public interface ArcLogMapper extends BaseMapper<ArcLog, Long> {

    

}
