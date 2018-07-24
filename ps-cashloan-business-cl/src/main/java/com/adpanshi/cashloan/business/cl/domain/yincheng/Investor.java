package com.adpanshi.cashloan.business.cl.domain.yincheng;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * 投资标中投资人信息表
 *
 * @author yecy
 * @version 1.0.0
 * @date 2017-11-02 20:55:33
 */
public class Investor implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键Id
     */
    private Long id;

    /**
     * 登录名
     */
    private String loginName;

    /**
     * 真实姓名
     */
    private String name;

    /**
     * 联系方式
     */
    private String phone;

    /**
     * 证件号码
     */
    private String idNo;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 创建时间
     */
    private Date createTime;


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
     * 获取	登录名
     *
     * @return 登录名
     */
    public String getLoginName() {
        return loginName;
    }

    /**
     * 设置	登录名
     *
     * @param loginName 要设置的	登录名
     */
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    /**
     * 获取真实姓名
     *
     * @return 真实姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置真实姓名
     *
     * @param name 要设置的真实姓名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取联系方式
     *
     * @return 联系方式
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置联系方式
     *
     * @param phone 要设置的联系方式
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取证件号码
     *
     * @return 证件号码
     */
    public String getIdNo() {
        return idNo;
    }

    /**
     * 设置证件号码
     *
     * @param idNo 要设置的证件号码
     */
    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    /**
     * 获取邮箱
     *
     * @return 邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置邮箱
     *
     * @param email 要设置的邮箱
     */
    public void setEmail(String email) {
        this.email = email;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Investor investor = (Investor) o;
        return Objects.equals(id, investor.id) &&
                Objects.equals(loginName, investor.loginName) &&
                Objects.equals(name, investor.name) &&
                Objects.equals(phone, investor.phone) &&
                Objects.equals(idNo, investor.idNo) &&
                Objects.equals(email, investor.email) &&
                Objects.equals(createTime, investor.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, loginName, name, phone, idNo, email, createTime);
    }
}
