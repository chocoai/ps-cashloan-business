package com.adpanshi.cashloan.business.cl.service;

import com.adpanshi.cashloan.business.cl.domain.yincheng.ClYinchengReqLog;
import com.adpanshi.cashloan.business.core.common.service.BaseService;
import com.github.pagehelper.Page;

import java.util.Map;

/**
 * 请求银程保存同步表Service
 * 
 * @author nmnl
 * @version 1.0.0
 * @date 2018-03-10 09:44:05

 *
 *
 */
public interface ClYinchengReqLogService extends BaseService<ClYinchengReqLog, Long> {

    ClYinchengReqLog findSelective(Map<String, Object> paramMap);
    /**
     * 分页查询
     * @date 20180310
     * @author nmnl
     * @param paramMap
     * @param current
     * @param pageSize
     * @return
     */
    Page<ClYinchengReqLog> page(Map<String, Object> paramMap, int current, int pageSize);

}
