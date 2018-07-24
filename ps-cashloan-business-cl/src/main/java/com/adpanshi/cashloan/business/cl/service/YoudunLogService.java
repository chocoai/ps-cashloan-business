package com.adpanshi.cashloan.business.cl.service;

import com.adpanshi.cashloan.business.cl.domain.YoudunLog;
import com.adpanshi.cashloan.business.core.common.service.BaseService;

import java.util.Map;

/**
 * 有盾请求记录表Service

 */
public interface YoudunLogService extends BaseService<YoudunLog, Long>{

    /**
     * 保存有盾请求日志
     * @param youdunLog
     * @return
     */
    int save(YoudunLog youdunLog);

    /**
     * 条件更新还款计划数据
     * @param paramMap
     * @return
     */
    int updateSelective(Map<String, Object> paramMap);

    /**
     * 获取一条记录
     * @date: 20170728
     * @author: nmnl
     * @param paramMap
     * @return 查询结果
     */
    YoudunLog findSelectiveOne(Map<String, Object> paramMap);

}
