package com.adpanshi.cashloan.business.core.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 借款信息表实体
 *
 * @author
 * @version 1.0.0
 * @date 2017-02-14 10:13:31
 *
 * @change 提取和borrowMain相同属性到baseBorrow中，@author yecy 20171208
 */
public class Borrow extends BaseBorrow implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 审批状态（0 未审批，1 正在审批，2 已审批）
     */
    private Integer auditProcess;

    /**
     * 审批锁定时间
     */
    private Date auditProcessTime;

    /**
     * 服务费
     */
    private Double serviceFee;

    /**
     * 信息认证费
     */
    private Double infoAuthFee;

    /**
     * 借款利息
     */
    private Double interest;

    /**
     * 主借款订单id
     */
    private Long borrowMainId;


    //TODO 构造函数需修改
    public Borrow() {
        super();
    }

    public Borrow(Long userId, Double amount, String timeLimit, Long cardId, String client, String address,
                  String coordinate, String ip) {
        super();
        super.userId = userId;
        super.amount = amount;
        super.timeLimit = timeLimit;
        super.cardId = cardId;
        super.client = client;
        super.address = address;
        super.coordinate = coordinate;
        super.ip = ip;
    }

    /**
     * 获取审批状态（0 未审批，1 正在审批，2 已审批）
     *
     * @return 审批状态（0 未审批，1 正在审批，2 已审批）
     */
    public Integer getAuditProcess() {
        return auditProcess;
    }

    /**
     * 设置审批状态（0 未审批，1 正在审批，2 已审批）
     *
     * @param auditProcess 要设置的审批状态（0 未审批，1 正在审批，2 已审批）
     */
    public void setAuditProcess(Integer auditProcess) {
        this.auditProcess = auditProcess;
    }

    /**
     * 获取审批锁定时间
     *
     * @return 审批锁定时间
     */
    public Date getAuditProcessTime() {
        return auditProcessTime;
    }

    /**
     * 设置审批锁定时间
     *
     * @param auditProcessTime 要设置的审批锁定时间
     */
    public void setAuditProcessTime(Date auditProcessTime) {
        this.auditProcessTime = auditProcessTime;
    }

    /**
     * 获取服务费
     *
     * @return 服务费
     */
    public Double getServiceFee() {
        return serviceFee;
    }

    /**
     * 设置服务费
     *
     * @param serviceFee 要设置的服务费
     */
    public void setServiceFee(Double serviceFee) {
        this.serviceFee = serviceFee;
    }

    /**
     * 获取信息认证费
     *
     * @return 信息认证费
     */
    public Double getInfoAuthFee() {
        return infoAuthFee;
    }

    /**
     * 设置信息认证费
     *
     * @param infoAuthFee 要设置的信息认证费
     */
    public void setInfoAuthFee(Double infoAuthFee) {
        this.infoAuthFee = infoAuthFee;
    }

    /**
     * 获取借款利息
     *
     * @return 借款利息
     */
    public Double getInterest() {
        return interest;
    }

    /**
     * 设置借款利息
     *
     * @param interest 要设置的借款利息
     */
    public void setInterest(Double interest) {
        this.interest = interest;
    }

    public Long getBorrowMainId() {
        return borrowMainId;
    }

    public void setBorrowMainId(Long borrowMainId) {
        this.borrowMainId = borrowMainId;
    }
}
