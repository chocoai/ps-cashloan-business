package com.adpanshi.cashloan.business.cl.service;

import com.adpanshi.cashloan.business.core.common.service.BaseService;
import com.adpanshi.cashloan.business.rule.domain.BorrowTemplate;

import java.util.Map;

/**
 * 借款模板信息表Service
 *
 * @author yecy
 * @version 1.0.0
 * @date 2017-11-30 15:14:56
 */
public interface BorrowTemplateService extends BaseService<BorrowTemplate, Long> {

    /**
     * FIXME 兼容老版本，暂时保留，稳定后删除 @author yecy 20171219
     *
     * @param amount
     * @param timeLimit
     * @param userId
     * @return
     */
    Map<String, Object> choice(double amount, String timeLimit, String userId);

}
