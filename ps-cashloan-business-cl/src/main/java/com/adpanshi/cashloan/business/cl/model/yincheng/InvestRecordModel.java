package com.adpanshi.cashloan.business.cl.model.yincheng;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * 投资记录Model
 *
 * @author yecy
 * @date 2017/11/3 9:17
 */
public class InvestRecordModel {
    /**
     * 投资人登录名
     */
    @JSONField(name = "login_name")
    private String loginName;

    /**
     * 投资人姓名
     */
    private String name;

    /**
     * 投资人联系方式
     */
    private String phone;

    /**
     * 投资时间
     */
    @JSONField(name = "invest_time")
    private Date investTime;

    /**
     * 投资金额
     */
    private Double money;

    /**
     * 投资人身份证号码
     */
    @JSONField(name = "id_no")
    private String idNo;

    /**
     * 投资人邮箱地址
     */
    private String email;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getInvestTime() {
        return investTime;
    }

    public void setInvestTime(Date investTime) {
        this.investTime = investTime;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
