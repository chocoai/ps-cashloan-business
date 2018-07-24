package com.adpanshi.cashloan.business.cl.mapper;

import com.adpanshi.cashloan.business.cl.domain.YoudunLog;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;

import java.util.Map;

/**
 * 有盾请求记录表Dao

 */
@RDBatisDao
public interface YoudunLogMapper extends BaseMapper<YoudunLog, Long> {

    /**
     * 获取一条记录
     * @date: 20170728
     * @author: nmnl
     * @param paramMap
     * @return 查询结果
     */
    YoudunLog findSelectiveOne(Map<String, Object> paramMap);

}
