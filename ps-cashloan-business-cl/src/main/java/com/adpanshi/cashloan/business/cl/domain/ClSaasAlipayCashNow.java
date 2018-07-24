package com.adpanshi.cashloan.business.cl.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * saas alipay 花呗数据实体
 * 
 * @author nmnl
 * @version 1.0.0
 * @date 2018-02-28 13:42:44

 *
 *
 */
 public class ClSaasAlipayCashNow implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 主键Id
    */
    private Long id;

    /**
    * requestid:uuid
    */
    private String reqId;

    /**
    * responseid
    */
    private String resId;

    /**
    * 用户id
    */
    private Long userId;

    /**
    * 爬去状态  10 已提交申请 20 爬去成功  30 爬去失败
    */
    private String state;

    /**
    * 创建时间
    */
    private Date createTime;

    /**
    * 更新时间
    */
    private Date updateTime;

    /**
    * 花呗额度
    */
    private Double amount;

    /**
    * 花呗余额
    */
    private Double balance;

    /**
    * 花呗原始额度
    */
    private Double originalAmount;

    /**
    * 花呗是否被冻结
    */
    private String freeze;

    /**
    * 花呗的罚息
    */
    private Double penaltyAmount;

    /**
    * 返回数据存储
    */
    private String params;


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
    * 获取requestid:uuid
    *
    * @return requestid:uuid
    */
    public String getReqId(){
        return reqId;
    }

    /**
    * 设置requestid:uuid
    * 
    * @param reqId 要设置的requestid:uuid
    */
    public void setReqId(String reqId){
        this.reqId = reqId;
    }

    /**
    * 获取responseid
    *
    * @return responseid
    */
    public String getResId(){
        return resId;
    }

    /**
    * 设置responseid
    * 
    * @param resId 要设置的responseid
    */
    public void setResId(String resId){
        this.resId = resId;
    }

    /**
    * 获取用户id
    *
    * @return 用户id
    */
    public Long getUserId(){
        return userId;
    }

    /**
    * 设置用户id
    * 
    * @param userId 要设置的用户id
    */
    public void setUserId(Long userId){
        this.userId = userId;
    }

    /**
    * 获取爬去状态  10 已提交申请 20 爬去成功  30 爬去失败
    *
    * @return 爬去状态  10 已提交申请 20 爬去成功  30 爬去失败
    */
    public String getState(){
        return state;
    }

    /**
    * 设置爬去状态  10 已提交申请 20 爬去成功  30 爬去失败
    * 
    * @param state 要设置的爬去状态  10 已提交申请 20 爬去成功  30 爬去失败
    */
    public void setState(String state){
        this.state = state;
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
    * 获取更新时间
    *
    * @return 更新时间
    */
    public Date getUpdateTime(){
        return updateTime;
    }

    /**
    * 设置更新时间
    * 
    * @param updateTime 要设置的更新时间
    */
    public void setUpdateTime(Date updateTime){
        this.updateTime = updateTime;
    }

    /**
    * 获取花呗额度
    *
    * @return 花呗额度
    */
    public Double getAmount(){
        return amount;
    }

    /**
    * 设置花呗额度
    * 
    * @param amount 要设置的花呗额度
    */
    public void setAmount(Double amount){
        this.amount = amount;
    }

    /**
    * 获取花呗余额
    *
    * @return 花呗余额
    */
    public Double getBalance(){
        return balance;
    }

    /**
    * 设置花呗余额
    * 
    * @param balance 要设置的花呗余额
    */
    public void setBalance(Double balance){
        this.balance = balance;
    }

    /**
    * 获取花呗原始额度
    *
    * @return 花呗原始额度
    */
    public Double getOriginalAmount(){
        return originalAmount;
    }

    /**
    * 设置花呗原始额度
    * 
    * @param originalAmount 要设置的花呗原始额度
    */
    public void setOriginalAmount(Double originalAmount){
        this.originalAmount = originalAmount;
    }

    /**
    * 获取花呗是否被冻结
    *
    * @return 花呗是否被冻结
    */
    public String getFreeze(){
        return freeze;
    }

    /**
    * 设置花呗是否被冻结
    * 
    * @param freeze 要设置的花呗是否被冻结
    */
    public void setFreeze(String freeze){
        this.freeze = freeze;
    }

    /**
    * 获取花呗的罚息
    *
    * @return 花呗的罚息
    */
    public Double getPenaltyAmount(){
        return penaltyAmount;
    }

    /**
    * 设置花呗的罚息
    * 
    * @param penaltyAmount 要设置的花呗的罚息
    */
    public void setPenaltyAmount(Double penaltyAmount){
        this.penaltyAmount = penaltyAmount;
    }

    /**
    * 获取返回数据存储
    *
    * @return 返回数据存储
    */
    public String getParams(){
        return params;
    }

    /**
    * 设置返回数据存储
    * 
    * @param params 要设置的返回数据存储
    */
    public void setParams(String params){
        this.params = params;
    }

}
