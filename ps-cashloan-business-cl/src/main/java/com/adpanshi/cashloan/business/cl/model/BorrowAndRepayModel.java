package com.adpanshi.cashloan.business.cl.model;

import com.adpanshi.cashloan.business.core.domain.BorrowMain;

import java.util.Date;

/**
 * @author yecy
 * @date 2017/12/20 10:35
 */
public class BorrowAndRepayModel {

    /**
     * 借款id
     */
    private Long borrowId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 借款金额
     */
    private Double amount;

    /**
     * 实际到账金额
     */
    private Double realAmount;

    /**
     * 借款期限(天)
     */
    private String timeLimit;

    /**
     * 借款订单状态
     */
    private String borrowState;

    /**
     * 还款id
     */
    private Long repayId;

    /**
     * 还款金额
     */
    private Double repayAmount;

    /**
     * 还款时间
     */
    private Date repayTime;

    /**
     * 还款状态 10-已还款 20-未还款
     */
    private String repayState;

    /**
     * 逾期罚金
     */
    private Double penaltyAmout;

    /**
     * 逾期天数
     */
    private String penaltyDay;

    private BorrowMain borrowMain;

    public Long getBorrowId() {
        return borrowId;
    }

    public void setBorrowId(Long borrowId) {
        this.borrowId = borrowId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getRealAmount() {
        return realAmount;
    }

    public void setRealAmount(Double realAmount) {
        this.realAmount = realAmount;
    }

    public String getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(String timeLimit) {
        this.timeLimit = timeLimit;
    }

    public String getBorrowState() {
        return borrowState;
    }

    public void setBorrowState(String borrowState) {
        this.borrowState = borrowState;
    }

    public Long getRepayId() {
        return repayId;
    }

    public void setRepayId(Long repayId) {
        this.repayId = repayId;
    }

    public Double getRepayAmount() {
        return repayAmount;
    }

    public void setRepayAmount(Double repayAmount) {
        this.repayAmount = repayAmount;
    }

    public Date getRepayTime() {
        return repayTime;
    }

    public void setRepayTime(Date repayTime) {
        this.repayTime = repayTime;
    }

    public String getRepayState() {
        return repayState;
    }

    public void setRepayState(String repayState) {
        this.repayState = repayState;
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

    public BorrowMain getBorrowMain() {
        return borrowMain;
    }

    public void setBorrowMain(BorrowMain borrowMain) {
        this.borrowMain = borrowMain;
    }
}
