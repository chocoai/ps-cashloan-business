package com.adpanshi.cashloan.business.cl.service;

import com.adpanshi.cashloan.business.cl.domain.loancity.LoanCityCodeLog;
import com.adpanshi.cashloan.business.core.common.service.BaseService;

import java.util.Map;

/**
 * 用户输入验证码记录Service
 * 
 * @author yecy
 * @version 1.0.0
 * @date 2018-01-01 18:51:26

 *
 *
 */
public interface LoanCityCodeLogService extends BaseService<LoanCityCodeLog, Long>{

    /**
     *
     * @param borrowMainId
     * @return
     */
    boolean codeIsSuccess(Long borrowMainId);

    /**
     * 检查验证码输入是否正确
     * 返回参数中"success"为验证码是否正确；"remainCount"为剩余可尝试次数
     * @param borrowMainId
     * @param idCode
     * @return
     */
    Map<String,Object> checkCode(Long borrowMainId, String idCode);
}
