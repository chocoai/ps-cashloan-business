package com.adpanshi.cashloan.business.rule.model;

import com.adpanshi.cashloan.business.rule.domain.BorrowTemplate;

/**
 * @author yecy
 * @date 2017/12/1 15:52
 */
public class BorrowTemplateModel extends BorrowTemplate {

    /**
     * 综合费用(费用详细信息累加)
     */
    private Double fee;
    private Boolean isOldUser;//是否为复借用户
    private Boolean isCutOpen;//是否需要使用砍头息
    private Boolean isMaxPenaltyOpen;//是否需要执行最高逾期

    /**
     * 获取综合费用(费用详细信息累加)
     *
     * @return 综合费用(费用详细信息累加)
     */
    public Double getFee() {
        return fee;
    }

    /**
     * 设置综合费用(费用详细信息累加)
     *
     * @param fee 要设置的综合费用(费用详细信息累加)
     */
    public void setFee(Double fee) {
        this.fee = fee;
    }

    public Boolean getOldUser() {
        return isOldUser;
    }

    public void setOldUser(Boolean oldUser) {
        isOldUser = oldUser;
    }

    public Boolean getCutOpen() {
        return isCutOpen;
    }

    public void setCutOpen(Boolean cutOpen) {
        isCutOpen = cutOpen;
    }

    public Boolean getMaxPenaltyOpen() {
        return isMaxPenaltyOpen;
    }

    public void setMaxPenaltyOpen(Boolean maxPenaltyOpen) {
        isMaxPenaltyOpen = maxPenaltyOpen;
    }

    /** 分期期数*/
    public Long getStage(){
        Long cycle = Long.parseLong(this.getCycle());
        Long timeLimit = Long.parseLong(this.getTimeLimit());
        return timeLimit / cycle;
    }
}
