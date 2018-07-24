package com.adpanshi.cashloan.business.cl.model;

import java.util.List;

/**
 * @author yecy
 * @date 2017/12/16 10:42
 */
public class RepayShowModel {
    //实际到账金额
    private String realAmount;
    //还款方式
    private String payType;
    //总利息
    private String allFee;
    //还款详情
    private List<RepayDetailShowModel> repayDetail;

    public String getRealAmount() {
        return realAmount;
    }

    public void setRealAmount(String realAmount) {
        this.realAmount = realAmount;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getAllFee() {
        return allFee;
    }

    public void setAllFee(String allFee) {
        this.allFee = allFee;
    }

    public List<RepayDetailShowModel> getRepayDetail() {
        return repayDetail;
    }

    public void setRepayDetail(List<RepayDetailShowModel> repayDetail) {
        this.repayDetail = repayDetail;
    }
}

