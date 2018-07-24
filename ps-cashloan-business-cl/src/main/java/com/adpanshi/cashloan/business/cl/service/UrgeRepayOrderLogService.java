package com.adpanshi.cashloan.business.cl.service;

import com.adpanshi.cashloan.business.cl.domain.UrgeRepayOrder;
import com.adpanshi.cashloan.business.cl.domain.UrgeRepayOrderLog;
import com.adpanshi.cashloan.business.core.common.service.BaseService;
import com.github.pagehelper.Page;

import java.util.Map;

/**
 * 催款记录表Service
 *
 * @version 1.0.0
 * @date 2017-03-07 14:28:22
 */
public interface UrgeRepayOrderLogService extends BaseService<UrgeRepayOrderLog, Long> {
    /**
     * 催款记录信息
     *
     * @param params
     * @param current
     * @param pageSize
     * @return
     */
    Page<UrgeRepayOrderLog> list(Map<String, Object> params, int current,
                                 int pageSize);


    /**
     * 保存催款记录信息
     *
     * @param orderLog
     * @return
     */
    void saveOrderInfo(UrgeRepayOrderLog orderLog, UrgeRepayOrder order);

    /**
     * 删除催收记录
     *
     * @param dueId
     * @return
     */
    boolean deleteByOrderId(Long dueId);
}
