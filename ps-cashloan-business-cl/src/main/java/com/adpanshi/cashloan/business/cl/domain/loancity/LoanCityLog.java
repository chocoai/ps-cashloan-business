package com.adpanshi.cashloan.business.cl.domain.loancity;

import java.io.Serializable;
import java.util.Date;

/**
 * 消贷同城需求记录实体
 * 
 * @author yecy
 * @version 1.0.0
 * @date 2018-01-01 18:08:00

 *
 *
 */
 public class LoanCityLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 需求编号
    */
    private String requireNo;

    /**
    * 用户手机号
    */
    private String mobile;

    /**
    * 消贷同城端状态，10:已发布, 20:已关联,30:未支付,40:已支付,50:撤销中60:撤销,70:支付前已过期,80:支付后已过期,90:未通过,100:已完成
    */
    private Integer state;

    /**
    * 关联的借款订单id
    */
    private Long borrowMainId;

    /**
    * 验证码
    */
    private String identifyingCode;

    /**
    * 创建时间
    */
    private Date createTime;

    /**
    * 用户输错验证码次数
    */
    private Integer codeRetryCount = 0;


    /**
    * 获取需求编号
    *
    * @return 需求编号
    */
    public String getRequireNo(){
        return requireNo;
    }

    /**
    * 设置需求编号
    * 
    * @param requireNo 要设置的需求编号
    */
    public void setRequireNo(String requireNo){
        this.requireNo = requireNo;
    }

    /**
    * 获取用户手机号
    *
    * @return 用户手机号
    */
    public String getMobile(){
        return mobile;
    }

    /**
    * 设置用户手机号
    * 
    * @param mobile 要设置的用户手机号
    */
    public void setMobile(String mobile){
        this.mobile = mobile;
    }

    /**
    * 获取消贷同城端状态，10:已发布, 20:已关联,30:未支付,40:已支付,50:撤销中60:撤销,70:支付前已过期,80:支付后已过期,90:未通过,100:已完成
    *
    * @return 消贷同城端状态，10:已发布, 20:已关联,30:未支付,40:已支付,50:撤销中60:撤销,70:支付前已过期,80:支付后已过期,90:未通过,100:已完成
    */
    public Integer getState(){
        return state;
    }

    /**
    * 设置消贷同城端状态，10:已发布, 20:已关联,30:未支付,40:已支付,50:撤销中60:撤销,70:支付前已过期,80:支付后已过期,90:未通过,100:已完成
    * 
    * @param state 要设置的消贷同城端状态，10:已发布, 20:已关联,30:未支付,40:已支付,50:撤销中60:撤销,70:支付前已过期,80:支付后已过期,90:未通过,100:已完成
    */
    public void setState(Integer state){
        this.state = state;
    }

    /**
    * 获取关联的借款订单id
    *
    * @return 关联的借款订单id
    */
    public Long getBorrowMainId(){
        return borrowMainId;
    }

    /**
    * 设置关联的借款订单id
    * 
    * @param borrowMainId 要设置的关联的借款订单id
    */
    public void setBorrowMainId(Long borrowMainId){
        this.borrowMainId = borrowMainId;
    }

    /**
    * 获取验证码
    *
    * @return 验证码
    */
    public String getIdentifyingCode(){
        return identifyingCode;
    }

    /**
    * 设置验证码
    * 
    * @param identifyingCode 要设置的验证码
    */
    public void setIdentifyingCode(String identifyingCode){
        this.identifyingCode = identifyingCode;
    }

    /**
    * 获取创建时间
    *
    * @return 创建时间
    */
    public Date getCreateTime(){
        return createTime;
    }

    /**
    * 设置创建时间
    * 
    * @param createTime 要设置的创建时间
    */
    public void setCreateTime(Date createTime){
        this.createTime = createTime;
    }

    /**
    * 获取用户输错验证码次数
    *
    * @return 用户输错验证码次数
    */
    public Integer getCodeRetryCount(){
        return codeRetryCount;
    }

    /**
    * 设置用户输错验证码次数
    * 
    * @param codeRetryCount 要设置的用户输错验证码次数
    */
    public void setCodeRetryCount(Integer codeRetryCount){
        this.codeRetryCount = codeRetryCount;
    }

}
