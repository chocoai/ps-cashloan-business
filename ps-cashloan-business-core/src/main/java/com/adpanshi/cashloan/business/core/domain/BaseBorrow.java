package com.adpanshi.cashloan.business.core.domain;

import java.util.Date;

/**
 * @author yecy
 * @date 2017/12/8 15:42
 */
public abstract class BaseBorrow {

    /**
     * 主键Id
     */
    protected Long id;

    /**
     * 用户id
     */
    protected Long userId;

    /**
     * 订 单号
     */
    protected String orderNo;

    /**
     * 借款金额
     */
    protected Double amount;

    /**
     * 实际到账金额
     */
    protected Double realAmount;

    /**
     * 综合费用(借款利息)
     */
    protected Double fee;

    /**
     * 订单状态 10-审核中 12-临时状态_自动审核_初审审核成功，待活体（自动） 14-临时状态_自动审核未决待人工复审_待活体（人工） 20-自动审核成功 21自动审核不通过 22自动审核未决待人工复审 26人工复审通过
     * 27人工复审不通过 30-待还款 31放款失败 40-已还款 50逾期 90坏账
     */
    protected String state;


    /**
     * 订单生成时间
     */
    protected Date createTime;

    /**
     * 借款期限(天)
     */
    protected String timeLimit;

    /**
     * 收款银行卡关联id
     */
    protected Long cardId;

    /**
     * 客户端 默认10-移动app
     */
    protected String client;

    /**
     * 发起借款地址
     */
    protected String address;

    /**
     * 借款经纬度坐标
     */
    protected String coordinate;

    /**
     * 备注、审核说明
     */
    protected String remark;

    /**
     * ip地址
     */
    protected String ip;

    /**
     * 审核人姓名
     */
    protected String auditName;

    /**
     * 审批时间
     */
    protected Date auditTime;

    /**
     * 获取主键Id
     *
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置主键Id
     *
     * @param 要设置的主键Id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取用户id
     *
     * @return 用户id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置用户id
     *
     * @param userId 要设置的用户id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取订 单号
     *
     * @return 订 单号
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * 设置订 单号
     *
     * @param orderNo 要设置的订 单号
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * 获取借款金额
     *
     * @return 借款金额
     */
    public Double getAmount() {
        return amount;
    }

    /**
     * 设置借款金额
     *
     * @param amount 要设置的借款金额
     */
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    /**
     * 获取实际到账金额
     *
     * @return 实际到账金额
     */
    public Double getRealAmount() {
        return realAmount;
    }

    /**
     * 设置实际到账金额
     *
     * @param realAmount 要设置的实际到账金额
     */
    public void setRealAmount(Double realAmount) {
        this.realAmount = realAmount;
    }

    /**
     * 获取综合费用(借款利息)
     *
     * @return 综合费用(借款利息)
     */
    public Double getFee() {
        return fee;
    }

    /**
     * 设置综合费用(借款利息)
     *
     * @param fee 要设置的综合费用(借款利息)
     */
    public void setFee(Double fee) {
        this.fee = fee;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    /**
     * 获取订单生成时间
     *
     * @return 订单生成时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置订单生成时间
     *
     * @param createTime 要设置的订单生成时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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
     * 获取收款银行卡关联id
     *
     * @return 收款银行卡关联id
     */
    public Long getCardId() {
        return cardId;
    }

    /**
     * 设置收款银行卡关联id
     *
     * @param cardId 要设置的收款银行卡关联id
     */
    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    /**
     * 获取客户端 默认10-移动app
     *
     * @return 客户端 默认10-移动app
     */
    public String getClient() {
        return client;
    }

    /**
     * 设置客户端 默认10-移动app
     *
     * @param client 要设置的客户端 默认10-移动app
     */
    public void setClient(String client) {
        this.client = client;
    }

    /**
     * 获取发起借款地址
     *
     * @return 发起借款地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置发起借款地址
     *
     * @param address 要设置的发起借款地址
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取借款经纬度坐标
     *
     * @return 借款经纬度坐标
     */
    public String getCoordinate() {
        return coordinate;
    }

    /**
     * 设置借款经纬度坐标
     *
     * @param coordinate 要设置的借款经纬度坐标
     */
    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    /**
     * 获取备注、审核说明
     *
     * @return 备注、审核说明
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注、审核说明
     *
     * @param remark 要设置的备注、审核说明
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取ip地址
     *
     * @return ip地址
     */
    public String getIp() {
        return ip;
    }

    /**
     * 设置ip地址
     *
     * @param ip 要设置的ip地址
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * 获取审核人姓名
     *
     * @return 审核人姓名
     */
    public String getAuditName() {
        return auditName;
    }

    /**
     * 设置审核人姓名
     *
     * @param auditName 要设置的审核人姓名
     */
    public void setAuditName(String auditName) {
        this.auditName = auditName;
    }

    /**
     * 获取审批时间
     *
     * @return 审批时间
     */
    public Date getAuditTime() {
        return auditTime;
    }

    /**
     * 设置审批时间
     *
     * @param auditTime 要设置的审批时间
     */
    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }


}
