package com.adpanshi.cashloan.business.cl.mapper;

import com.adpanshi.cashloan.business.cl.domain.loancity.LoanCityLog;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;

import java.util.Map;

/**
 * 消贷同城需求记录Dao
 * 
 * @author yecy
 * @version 1.0.0
 * @date 2018-01-01 18:08:00

 *
 *
 */
@RDBatisDao
public interface LoanCityLogMapper extends BaseMapper<LoanCityLog, String> {

    /**
     * 根据条件查询记录条数
     * @param paramMap
     * @return
     */
    int countSelective(Map<String, Object> paramMap);
    

}
