package com.adpanshi.cashloan.business.cl.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 有盾请求记录表实体

 * @since 20170629
 */
 public class YoudunLog implements Serializable {

    private static final long serialVersionUID = 1L;

    public final  static String TYPE_IDCARD = "10";//身份证OCR
    public final  static String TYPE_LIVING = "20";//

    public final  static String STATE_IDCARD = "200";//身份证OCR 成功

    /**
    * 主键Id
    */
    private Long id;

    /**
    * 请求订单标识(身份证OCR和活体订单号都不一样)
    */
    private String orderNo;

    /**
    * 用户标识
    */
    private Long userId;

    /**
    * 借款订单id
    */
    private Long borrowId;

    /**
    * 支付方式 10:身份证OCR  20:活体
    */
    private String type;

    /**
    * 请求状态  200 成功 其他失败
    */
    private String state;

    /**
    * 备注
    */
    private String remark;

    /**
    * 添加时间
    */
    private Date createTime;


    /**
    * 获取主键Id
    *
    * @return id
    */
    public Long getId(){
        return id;
    }

    /**
    * 设置主键Id
    * 
    * @param 要设置的主键Id
    */
    public void setId(Long id){
        this.id = id;
    }

    /**
    * 获取请求订单标识(身份证OCR和活体订单号都不一样)
    *
    * @return 请求订单标识(身份证OCR和活体订单号都不一样)
    */
    public String getOrderNo(){
        return orderNo;
    }

    /**
    * 设置请求订单标识(身份证OCR和活体订单号都不一样)
    * 
    * @param orderNo 要设置的请求订单标识(身份证OCR和活体订单号都不一样)
    */
    public void setOrderNo(String orderNo){
        this.orderNo = orderNo;
    }

    /**
    * 获取用户标识
    *
    * @return 用户标识
    */
    public Long getUserId(){
        return userId;
    }

    /**
    * 设置用户标识
    * 
    * @param userId 要设置的用户标识
    */
    public void setUserId(Long userId){
        this.userId = userId;
    }

    /**
    * 获取借款订单id
    *
    * @return 借款订单id
    */
    public Long getBorrowId(){
        return borrowId;
    }

    /**
    * 设置借款订单id
    * 
    * @param borrowId 要设置的借款订单id
    */
    public void setBorrowId(Long borrowId){
        this.borrowId = borrowId;
    }

    /**
    * 获取支付方式 10:身份证OCR  20:活体
    *
    * @return 支付方式 10:身份证OCR  20:活体
    */
    public String getType(){
        return type;
    }

    /**
    * 设置支付方式 10:身份证OCR  20:活体
    * 
    * @param type 要设置的支付方式 10:身份证OCR  20:活体
    */
    public void setType(String type){
        this.type = type;
    }

    /**
    * 获取请求状态  200 成功 其他失败
    *
    * @return 请求状态  200 成功 其他失败
    */
    public String getState(){
        return state;
    }

    /**
    * 设置请求状态  200 成功 其他失败
    * 
    * @param state 要设置的请求状态  200 成功 其他失败
    */
    public void setState(String state){
        this.state = state;
    }

    /**
    * 获取备注
    *
    * @return 备注
    */
    public String getRemark(){
        return remark;
    }

    /**
    * 设置备注
    * 
    * @param remark 要设置的备注
    */
    public void setRemark(String remark){
        this.remark = remark;
    }

    /**
    * 获取添加时间
    *
    * @return 添加时间
    */
    public Date getCreateTime(){
        return createTime;
    }

    /**
    * 设置添加时间
    * 
    * @param createTime 要设置的添加时间
    */
    public void setCreateTime(Date createTime){
        this.createTime = createTime;
    }

}