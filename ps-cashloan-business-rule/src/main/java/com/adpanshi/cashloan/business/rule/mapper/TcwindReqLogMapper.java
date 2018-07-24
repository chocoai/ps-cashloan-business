package com.adpanshi.cashloan.business.rule.mapper;

import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;
import com.adpanshi.cashloan.business.rule.domain.TcwindReqLog;

import java.util.Map;

/**
 * 大风策请求记录表Dao
 * 
 * @author yecy
 * @version 1.0.0
 * @date 2018-02-05 18:01:30

 *
 *
 */
@RDBatisDao
public interface TcwindReqLogMapper extends BaseMapper<TcwindReqLog, Long> {

    /**
     * 根据条件查找最后响应成功的大风策数据
     * @param paramMap
     * @return
     */
    TcwindReqLog findLastSuccessOne(Map<String, Object> paramMap);

    /**
     * 根据条件查找最后响应的大风策数据
     * @param paramMap
     * @return
     */
    TcwindReqLog findLastOneByUserId(Map<String, Object> paramMap);

}
