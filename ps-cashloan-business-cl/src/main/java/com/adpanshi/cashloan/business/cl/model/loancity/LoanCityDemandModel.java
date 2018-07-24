package com.adpanshi.cashloan.business.cl.model.loancity;

import java.util.Date;

/**
 * 消贷同城需求model
 *
 * @author yecy
 * @date 2018/1/1 23:07
 */
public class LoanCityDemandModel extends BaseLoanCityModel {

    private Long amount;//借款金额
    private String periodType;//周期类型(M月、d天）
    private Integer borrowType;//借款类型(租房贷款10/消费分期20)
    private Integer borrowPeriod;//借款周期
    private Double yearRate;//年化利率

    private Date expiredAt;//过期时间（消贷同城推送需求过来时有值）

    private String identifyingCode;//验证码（借款订单推送给消贷同城时有值）

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getPeriodType() {
        return periodType;
    }

    public void setPeriodType(String periodType) {
        this.periodType = periodType;
    }

    public Integer getBorrowType() {
        return borrowType;
    }

    public void setBorrowType(Integer borrowType) {
        this.borrowType = borrowType;
    }

    public Integer getBorrowPeriod() {
        return borrowPeriod;
    }

    public void setBorrowPeriod(Integer borrowPeriod) {
        this.borrowPeriod = borrowPeriod;
    }

    public Double getYearRate() {
        return yearRate;
    }

    public void setYearRate(Double yearRate) {
        this.yearRate = yearRate;
    }

    public Date getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(Date expiredAt) {
        this.expiredAt = expiredAt;
    }

    public String getIdentifyingCode() {
        return identifyingCode;
    }

    public void setIdentifyingCode(String identifyingCode) {
        this.identifyingCode = identifyingCode;
    }
}
