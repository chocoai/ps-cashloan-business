package com.adpanshi.cashloan.business.cl.service;

import com.adpanshi.cashloan.business.cl.domain.UrgeRepayOrder;
import com.adpanshi.cashloan.business.cl.model.UrgeRepayOrderModel;
import com.adpanshi.cashloan.business.core.common.service.BaseService;
import com.github.pagehelper.Page;

import java.util.Map;

/**
 * 催款计划表Service
 *
 * @version 1.0.0
 * @date 2017-03-07 14:21:58
 */
public interface UrgeRepayOrderService extends BaseService<UrgeRepayOrder, Long> {
    /**
     * 催款记录信息
     *
     * @param params
     * @param current
     * @param pageSize
     * @return
     */
    Page<UrgeRepayOrderModel> list(Map<String, Object> params, int current,
                                   int pageSize);

    /**
     * 催款记录详细信息
     *
     * @param params
     * @param current
     * @param pageSize
     * @return
     */
    Page<UrgeRepayOrderModel> listModel(Map<String, Object> params, int current,
                                        int pageSize);

    /**
     * 催款订单是否为催收管理员订单
     *
     * @param
     * @return
     */
    boolean isCollector(Long id);

    UrgeRepayOrder findOrderByMap(Map<String, Object> orderMap);

    /**
     * 删除催收订单
     *
     * @param borrowId
     * @return
     */
    boolean deleteByBorrowId(Long borrowId);


}
