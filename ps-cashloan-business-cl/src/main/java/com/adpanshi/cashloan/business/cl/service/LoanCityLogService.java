package com.adpanshi.cashloan.business.cl.service;

import com.adpanshi.cashloan.business.cl.domain.loancity.LoanCityLog;
import com.adpanshi.cashloan.business.core.common.service.BaseService;

/**
 * 消贷同城需求记录Service
 * 
 * @author yecy
 * @version 1.0.0
 * @date 2018-01-01 18:08:00

 *
 *
 */
public interface LoanCityLogService extends BaseService<LoanCityLog, String>{


    /**
     * 查询该用户是否为消贷同城用户
     * @param mobile 用户手机号码
     * @return
     */
    boolean isLoanCityUser(String mobile);

    /**
     * 根据手机号码查询状态为‘发布’的需求记录条数
     * @param mobile 用户手机号码
     * @return
     */
    LoanCityLog countPublishLogByMobile(String mobile);

    /**
     * 根据borrowId查找关联的需求
     * @param borrowMainId
     * @return
     */
    LoanCityLog findLogByBorrowId(Long borrowMainId);
}
