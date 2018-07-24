package com.adpanshi.cashloan.business.rule.domain;

import com.adpanshi.cashloan.business.rule.enums.PayType;

import java.io.Serializable;
import java.util.Date;

/**
 * 借款模板信息表实体
 *
 * @author yecy
 * @version 1.0.0
 * @date 2017-11-30 15:14:56
 */
public class BorrowTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 借款规则（json格式，maxAmount:最大可借；minAmount:最少可借；interval:可借间隔）
     */
   private String borrowRule;
    

    /**
     * 借款期限(天)
     */
    private String timeLimit;

    /**
     * 分期周期(天)
     */
    private String cycle;

    /**
     * 费用详细(json格式，value为小数，占借款金额的比例)
     */
    private String feeDetail;

    /**
     * 砍头息适用用户（0 都不使用，1 新用户，2 老用户，3 新，老用户）
     * 转换为二进制：个位代表新用户，十位代表老用户。1代表开，0代表关。
     */
    private Integer cutType;

    /**
     * 逾期罚金（每天金额）
     */
    private Double penalty;

    /**
     * 最高逾期罚金（超过罚金不累加）
     */
    private Double maxPenalty;

    /**
     * 最高逾期罚金适用用户（0 都不使用，1 新用户，2 老用户，3 新，老用户）
     * 转换为二进制：个位代表新用户，十位代表老用户。1代表开，0代表关。
     */
    private Integer penaltyType;

    /**
     * 模板创建时间
     */
    private Date createTime;

    /**
     * 模板更新时间
     */
    private Date updateTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 还款方式（ACPI:等额本息，AC:等额本金）
     */
    private PayType payType;

    /**
     * 借款日息（年息按照360天计算）
     */
    private Double dayRate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBorrowRule() {
        return borrowRule;
    }

    public void setBorrowRule(String borrowRule) {
        this.borrowRule = borrowRule;
    }

    /**
     * 获取借款期限(天)
     *
     * @return 借款期限(天)
     */
    public String getTimeLimit() {
        return timeLimit;
    }

    /**
     * 设置借款期限(天)
     *
     * @param timeLimit 要设置的借款期限(天)
     */
    public void setTimeLimit(String timeLimit) {
        this.timeLimit = timeLimit;
    }

    /**
     * 获取分期周期(天)
     *
     * @return 分期周期(天)
     */
    public String getCycle() {
        return cycle;
    }

    /**
     * 设置分期周期(天)
     *
     * @param cycle 分期周期(天)
     */
    public void setCycle(String cycle) {
        this.cycle = cycle;
    }

    /**
     * 获取费用详细
     *
     * @return 费用详细
     */
    public String getFeeDetail() {
        return feeDetail;
    }

    /**
     * 设置费用详细
     *
     * @param feeDetail 要设置的费用详细
     */
    public void setFeeDetail(String feeDetail) {
        this.feeDetail = feeDetail;
    }

    /**
     * 获取砍头息适用用户（0 都不使用，1 新用户，2 老用户，3 新，老用户）
     *
     * @return 砍头息适用用户（0 都不使用，1 新用户，2 老用户，3 新，老用户）
     */
    public Integer getCutType() {
        return cutType;
    }

    /**
     * 设置砍头息适用用户（0 都不使用，1 新用户，2 老用户，3 新，老用户）
     *
     * @param cutType 要设置的砍头息适用用户（0 都不使用，1 新用户，2 老用户，3 新，老用户）
     */
    public void setCutType(Integer cutType) {
        this.cutType = cutType;
    }

    /**
     * 获取逾期罚金（每天金额）
     *
     * @return 逾期罚金（每天金额）
     */
    public Double getPenalty() {
        return penalty;
    }

    /**
     * 设置逾期罚金（每天金额）
     *
     * @param penalty 要设置的逾期罚金（每天金额）
     */
    public void setPenalty(Double penalty) {
        this.penalty = penalty;
    }

    /**
     * 获取最高逾期罚金（超过罚金不累加）
     *
     * @return 最高逾期罚金（超过罚金不累加）
     */
    public Double getMaxPenalty() {
        return maxPenalty;
    }

    /**
     * 设置最高逾期罚金（超过罚金不累加）
     *
     * @param maxPenalty 要设置的最高逾期罚金（超过罚金不累加）
     */
    public void setMaxPenalty(Double maxPenalty) {
        this.maxPenalty = maxPenalty;
    }

    /**
     * 获取最高逾期罚金适用用户（0 都不使用，1 新用户，2 老用户，3 新，老用户）
     *
     * @return 最高逾期罚金适用用户（0 都不使用，1 新用户，2 老用户，3 新，老用户）
     */
    public Integer getPenaltyType() {
        return penaltyType;
    }

    /**
     * 设置最高逾期罚金适用用户（0 都不使用，1 新用户，2 老用户，3 新，老用户）
     *
     * @param penaltyType 要设置的最高逾期罚金适用用户（0 都不使用，1 新用户，2 老用户，3 新，老用户）
     */
    public void setPenaltyType(Integer penaltyType) {
        this.penaltyType = penaltyType;
    }

    /**
     * 获取模板创建时间
     *
     * @return 模板创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置模板创建时间
     *
     * @param createTime 要设置的模板创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取模板更新时间
     *
     * @return 模板更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置模板更新时间
     *
     * @param updateTime 要设置的模板更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取备注
     *
     * @return 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注
     *
     * @param remark 要设置的备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public PayType getPayType() {
        return payType;
    }

    public void setPayType(PayType payType) {
        this.payType = payType;
    }

    public Double getDayRate() {
        return dayRate;
    }

    public void setDayRate(Double dayRate) {
        this.dayRate = dayRate;
    }
}
