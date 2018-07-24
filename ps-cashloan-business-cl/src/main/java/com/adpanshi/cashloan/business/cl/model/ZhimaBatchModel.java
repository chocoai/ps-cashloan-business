package com.adpanshi.cashloan.business.cl.model;

import com.adpanshi.cashloan.business.cl.domain.ZhimaBatch;

import java.util.Date;

/**
 * 批量生产芝麻授权数据实体
 * 
 * @author nmnl
 * @version 1.0.0
 * @date 2017-08-15 17:54:27

 *
 *
 */
 public class ZhimaBatchModel extends ZhimaBatch {

    private static final long serialVersionUID = 1L;

    private Long userId;
    private String userName;
    private String idNo;
    private String idType;
    private String orderNo;
    private Long borrowId;
    private Long borrowProgressId;
    private String borrowProgressState;
    private Date borrowProgressTime;
    private Date cbrRepayTime;
    private String cbrState;
    private String cbrPenaltyAmout;
    private String cbrPenaltyDay;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Long getBorrowId() {
        return borrowId;
    }

    public void setBorrowId(Long borrowId) {
        this.borrowId = borrowId;
    }

    public Long getBorrowProgressId() {
        return borrowProgressId;
    }

    public void setBorrowProgressId(Long borrowProgressId) {
        this.borrowProgressId = borrowProgressId;
    }

    public String getBorrowProgressState() {
        return borrowProgressState;
    }

    public void setBorrowProgressState(String borrowProgressState) {
        this.borrowProgressState = borrowProgressState;
    }

    public Date getBorrowProgressTime() {
        return borrowProgressTime;
    }

    public void setBorrowProgressTime(Date borrowProgressTime) {
        this.borrowProgressTime = borrowProgressTime;
    }

    public Date getCbrRepayTime() {
        return cbrRepayTime;
    }

    public void setCbrRepayTime(Date cbrRepayTime) {
        this.cbrRepayTime = cbrRepayTime;
    }

    public String getCbrState() {
        return cbrState;
    }

    public void setCbrState(String cbrState) {
        this.cbrState = cbrState;
    }

    public String getCbrPenaltyAmout() {
        return cbrPenaltyAmout;
    }

    public void setCbrPenaltyAmout(String cbrPenaltyAmout) {
        this.cbrPenaltyAmout = cbrPenaltyAmout;
    }

    public String getCbrPenaltyDay() {
        return cbrPenaltyDay;
    }

    public void setCbrPenaltyDay(String cbrPenaltyDay) {
        this.cbrPenaltyDay = cbrPenaltyDay;
    }
}