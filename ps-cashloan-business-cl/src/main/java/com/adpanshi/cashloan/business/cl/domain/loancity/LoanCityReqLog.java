package com.adpanshi.cashloan.business.cl.domain.loancity;

import java.io.Serializable;
import java.util.Date;

/**
 * 请求消贷同城记录表实体
 *
 * @author yecy
 * @version 1.0.0
 * @date 2018-01-01 18:52:40
 */
public class LoanCityReqLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键Id
     */
    private Long id;

    /**
     * 请求接口名称
     */
    private String service;

    /**
     * 借款订单id
     */
    private Long borrowMainId;

    /**
     * 请求参数
     */
    private String params;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 同步响应结果
     */
    private String respParams;

    /**
     * 同步响应时间
     */
    private Date respTime;

    /**
     * 是否响应成功
     */
    private Boolean success;

    /**
     * 获取主键Id
     *
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置主键Id
     *
     * @param 要设置的主键Id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取请求接口名称
     *
     * @return 请求接口名称
     */
    public String getService() {
        return service;
    }

    /**
     * 设置请求接口名称
     *
     * @param service 要设置的请求接口名称
     */
    public void setService(String service) {
        this.service = service;
    }

    /**
     * 获取借款订单id
     *
     * @return 借款订单id
     */
    public Long getBorrowMainId() {
        return borrowMainId;
    }

    /**
     * 设置借款订单id
     *
     * @param borrowMainId 要设置的借款订单id
     */
    public void setBorrowMainId(Long borrowMainId) {
        this.borrowMainId = borrowMainId;
    }

    /**
     * 获取请求参数
     *
     * @return 请求参数
     */
    public String getParams() {
        return params;
    }

    /**
     * 设置请求参数
     *
     * @param params 要设置的请求参数
     */
    public void setParams(String params) {
        this.params = params;
    }

    /**
     * 获取创建时间
     *
     * @return 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 要设置的创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取同步响应结果
     *
     * @return 同步响应结果
     */
    public String getRespParams() {
        return respParams;
    }

    /**
     * 设置同步响应结果
     *
     * @param respParams 要设置的同步响应结果
     */
    public void setRespParams(String respParams) {
        this.respParams = respParams;
    }

    /**
     * 获取同步响应时间
     *
     * @return 同步响应时间
     */
    public Date getRespTime() {
        return respTime;
    }

    /**
     * 设置同步响应时间
     *
     * @param respTime 要设置的同步响应时间
     */
    public void setRespTime(Date respTime) {
        this.respTime = respTime;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
