package com.adpanshi.cashloan.business.cl.model.yincheng;

import com.adpanshi.cashloan.business.cl.enums.YinChengRepayStatusEnum;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * 还款模型
 *
 * @author yecy
 */
public class RepayModel {
    private String uid;//    用户id是借款人记录接口返回的用户id
    @JSONField(name = "order_no")
    private String orderNo;//    借款订单号
    private Double money;//    本期预期还款本金金额
    private Double interest;//    本期预期还款利率金额
    private Double overdue;//    已还逾期（逾期）
    @JSONField(name = "actual_money")
    private Double actualMoney; // 已还金额（本金+利息）
    private YinChengRepayStatusEnum status;//     还款状态 2正常还款 4逾期还款
    private Integer periods; // 还第几期的

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public YinChengRepayStatusEnum getStatus() {
        return status;
    }

    public void setStatus(YinChengRepayStatusEnum status) {
        this.status = status;
    }

    public Integer getPeriods() {
        return periods;
    }

    public void setPeriods(Integer periods) {
        this.periods = periods;
    }

    public Double getInterest() {
        return interest;
    }

    public void setInterest(Double interest) {
        this.interest = interest;
    }

    public Double getOverdue() {
        return overdue;
    }

    public void setOverdue(Double overdue) {
        this.overdue = overdue;
    }

    public Double getActualMoney() {
        return actualMoney;
    }

    public void setActualMoney(Double actualMoney) {
        this.actualMoney = actualMoney;
    }
}
