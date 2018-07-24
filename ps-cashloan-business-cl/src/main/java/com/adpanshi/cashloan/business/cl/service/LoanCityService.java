package com.adpanshi.cashloan.business.cl.service;

/**
 * @author yecy
 * @date 2018/1/3 20:57
 */
public interface LoanCityService {

    /**
     * 将未发布直接借款的用户，推送给消贷同城
     *
     * @param borrowMainId 借款订单id
     * @param mobile       用户手机号码
     */
    void unPublish(Long borrowMainId, String mobile);

    /**
     * 推送验证码，给消贷同城
     *
     * @param borrowMainId 借款订单id
     */
    void pushIdentifyCode(Long borrowMainId);

    /**
     * 推送审核失败，给消贷同城
     *
     * @param borrowMainId 借款订单id
     */
    void auditFail(Long borrowMainId);

}
