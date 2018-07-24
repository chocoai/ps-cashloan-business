package com.adpanshi.cashloan.business.cl.domain.lianxin;

import java.io.Serializable;
import java.util.Date;

/**
 * 逾期用户
 * Created by cc on 2017-08-31 19:43.
 */
public class OverDueUser implements Serializable{

    private long userId;
    private long borrowId;
    private Double amount;
    private Date repayTime;
    private Double penaltyAmout;
    private String penaltyDay;
    private String realName;
    private String phone;
    private String sex;


    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getBorrowId() {
        return borrowId;
    }

    public void setBorrowId(long borrowId) {
        this.borrowId = borrowId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getRepayTime() {
        return repayTime;
    }

    public void setRepayTime(Date repayTime) {
        this.repayTime = repayTime;
    }

    public Double getPenaltyAmout() {
        return penaltyAmout;
    }

    public void setPenaltyAmout(Double penaltyAmout) {
        this.penaltyAmout = penaltyAmout;
    }

    public String getPenaltyDay() {
        return penaltyDay;
    }

    public void setPenaltyDay(String penaltyDay) {
        this.penaltyDay = penaltyDay;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
