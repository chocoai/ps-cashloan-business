package com.adpanshi.cashloan.business.cl.domain;

import java.util.Date;

/**
 * @author yecy
 * @date 2017/12/8 16:10
 */
public abstract class BaseBorrowProgress {
    /**
     * 主键Id
     */
    private Long id;

    /**
     * 关联用户id
     */
    private Long userId;

    /**
     * 借款信息id
     */
    private Long borrowId;

    /**
     * 借款进度状态 10申请成功待审核 20自动审核通过 21自动审核不通过 22自动审核未决待人工复审 25人工复审挂起 26人工复审通过 27人工复审不通过 30放款成功  31放款失败 40还款成功
     */
    private String state;

    /**
     * 进度描述
     */
    private String remark;

    /**
     * 进度生成时间
     */
    private Date createTime;


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
     * 获取关联用户id
     *
     * @return 关联用户id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置关联用户id
     *
     * @param userId 要设置的关联用户id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取借款信息id
     *
     * @return 借款信息id
     */
    public Long getBorrowId() {
        return borrowId;
    }

    /**
     * 设置借款信息id
     *
     * @param borrowId 要设置的借款信息id
     */
    public void setBorrowId(Long borrowId) {
        this.borrowId = borrowId;
    }

    /**
     * 获取借款进度状态 10申请成功待审核 20自动审核通过 21自动审核不通过 22自动审核未决待人工复审 25人工复审挂起 26人工复审通过 27人工复审不通过 30放款成功  31放款失败 40还款成功
     *
     * @return 借款进度状态 10申请成功待审核 20自动审核通过 21自动审核不通过 22自动审核未决待人工复审 25人工复审挂起 26人工复审通过 27人工复审不通过 30放款成功  31放款失败 40还款成功
     */
    public String getState() {
        return state;
    }

    /**
     * 设置借款进度状态 10申请成功待审核 20自动审核通过 21自动审核不通过 22自动审核未决待人工复审 25人工复审挂起 26人工复审通过 27人工复审不通过 30放款成功  31放款失败 40还款成功
     *
     * @param state 要设置的借款进度状态 10申请成功待审核 20自动审核通过 21自动审核不通过 22自动审核未决待人工复审 25人工复审挂起 26人工复审通过 27人工复审不通过 30放款成功  31放款失败 40还款成功
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * 获取进度描述
     *
     * @return 进度描述
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置进度描述
     *
     * @param remark 要设置的进度描述
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取进度生成时间
     *
     * @return 进度生成时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置进度生成时间
     *
     * @param createTime 要设置的进度生成时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
