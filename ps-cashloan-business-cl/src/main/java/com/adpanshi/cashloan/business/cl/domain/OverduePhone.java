package com.adpanshi.cashloan.business.cl.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 小贷信用卡催收电话号码库实体
 *
 * @author yecy
 * @version 1.0.0
 * @date 2017-10-24 19:58:22
 */
public class OverduePhone implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键Id
     */
    private Long id;

    /**
     * 客户名称
     */
    private String customerName;

    /**
     * 电话号码
     */
    private String phone;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人的id
     */
    private Long createUserId;


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
     * @param id 要设置的主键Id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取客户名称
     *
     * @return customerName
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * 设置客户名称
     *
     * @param customerName 要设置的客户名称
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * 获取电话号码
     *
     * @return 电话号码
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置电话号码
     *
     * @param phone 电话号码
     */
    public void setPhone(String phone) {
        this.phone = phone;
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
     * 获取创建人的id
     *
     * @return 创建人的id
     */
    public Long getCreateUserId() {
        return createUserId;
    }

    /**
     * 设置创建人的id
     *
     * @param createUserId 要设置的创建人的id
     */
    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

}
