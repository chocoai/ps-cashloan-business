package com.adpanshi.cashloan.business.cl.model;

/**
 * @author yecy
 * @date 2017/12/14 22:34
 */
public class RepayDetailShowModel {

    //还款时间
    private String repayTime;

    // 还款金额(本金+利息)
    private String amount;

    // 利息
    private String interest;

    // 手续费
    private String poundage;

    // 逾期罚金
    private String penaltyAmount;

    // 逾期天数
    private String penaltyDay;

    // 名称
    private String title;

    public String getRepayTime() {
        return repayTime;
    }

    public void setRepayTime(String repayTime) {
        this.repayTime = repayTime;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getPoundage() {
        return poundage;
    }

    public void setPoundage(String poundage) {
        this.poundage = poundage;
    }

    public String getPenaltyAmount() {
        return penaltyAmount;
    }

    public void setPenaltyAmount(String penaltyAmount) {
        this.penaltyAmount = penaltyAmount;
    }

    public String getPenaltyDay() {
        return penaltyDay;
    }

    public void setPenaltyDay(String penaltyDay) {
        this.penaltyDay = penaltyDay;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
