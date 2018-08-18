package com.adpanshi.cashloan.cl.service;

import com.adpanshi.cashloan.core.common.service.BaseService;
import com.adpanshi.cashloan.core.domain.Notices;

import java.util.Date;

/**
 * @program: fenqidai
 * @description: 个人消息服务
 * @author: Mr.Wange
 * @create: 2018-07-25 14:45
 **/
public interface ClNoticesService extends BaseService<Notices,Long> {
    /**
     * 主动还款消息
     * @method: activePayment
     * @param userId
     * @param borrowMainId
     * @param settleTime
     * @param repayAmount
     * @return: void
     * @throws
     * @Author: Mr.Wange
     * @Date: 2018/7/25 14:44
     */
    void activePayment(long userId, long borrowMainId, Date settleTime, Double repayAmount);
    /**
     * 审核未通过
     * @method: refuse
     * @param userId
     * @param day
     * @return: void
     * @throws
     * @Author: Mr.Wange
     * @Date: 2018/7/25 15:59
     */
    void refuse(long userId, int day);
    /**
     * 放款
     * @method: payment
     * @param userId
     * @param borrowMainId
     * @param amount
     * @return: void
     * @throws
     * @Author: Mr.Wange
     * @Date: 2018/7/25 16:13
     */
    void payment(Long userId, Long borrowMainId, Double amount);
    /**
     * 逾期
     * @method: overdue
     * @param borrowId
     * @param userId
     * @return: void
     * @throws
     * @Author: Mr.Wange
     * @Date: 2018/7/25 16:25
     */
    void overdue(long borrowId, long userId);
    /**
     * 还款提醒
     * @method: repayBefore
     * @param userId
     * @param borrowId
     * @return: void
     * @throws
     * @Author: Mr.Wange
     * @Date: 2018/7/25 16:29
     */
    void repayBefore(long userId, long borrowId);
}
