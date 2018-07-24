package com.adpanshi.cashloan.business.core.mapper;


import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;
import com.adpanshi.cashloan.business.core.domain.ClLog;

/**
 * APP用户操作日志表Dao
 * 
 * @author tq
 * @version 1.0.0
 * @date 2018-06-19 11:18:07

 *
 *
 */
@RDBatisDao
public interface ClLogMapper extends BaseMapper<ClLog, Long> {

    

}
