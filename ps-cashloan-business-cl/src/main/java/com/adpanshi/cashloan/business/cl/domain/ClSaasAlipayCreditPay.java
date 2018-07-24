package com.adpanshi.cashloan.business.cl.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * saas alipay 借呗数据实体
 * 
 * @author nmnl
 * @version 1.0.0
 * @date 2018-02-28 13:44:39

 *
 *
 */
 public class ClSaasAlipayCreditPay implements Serializable {

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
    * 借呗额度
    */
    private Double amount;

    /**
    * 是否逾期
    */
    private Integer isOverdue;

    /**
    * 是否新账号
    */
    private Integer isNewable;

    /**
    * 是否关闭
    */
    private Integer isClosed;

    /**
    * 是否可用
    */
    private Integer isAvailable;

    /**
    * 未还期数
    */
    private String unClearLoanCount;

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
    * 获取借呗额度
    *
    * @return 借呗额度
    */
    public Double getAmount(){
        return amount;
    }

    /**
    * 设置借呗额度
    * 
    * @param amount 要设置的借呗额度
    */
    public void setAmount(Double amount){
        this.amount = amount;
    }

    /**
    * 获取是否逾期
    *
    * @return 是否逾期
    */
    public Integer getIsOverdue(){
        return isOverdue;
    }

    /**
    * 设置是否逾期
    * 
    * @param isOverdue 要设置的是否逾期
    */
    public void setIsOverdue(Integer isOverdue){
        this.isOverdue = isOverdue;
    }

    /**
    * 获取是否新账号
    *
    * @return 是否新账号
    */
    public Integer getIsNewable(){
        return isNewable;
    }

    /**
    * 设置是否新账号
    * 
    * @param isNewable 要设置的是否新账号
    */
    public void setIsNewable(Integer isNewable){
        this.isNewable = isNewable;
    }

    /**
    * 获取是否关闭
    *
    * @return 是否关闭
    */
    public Integer getIsClosed(){
        return isClosed;
    }

    /**
    * 设置是否关闭
    * 
    * @param isClosed 要设置的是否关闭
    */
    public void setIsClosed(Integer isClosed){
        this.isClosed = isClosed;
    }

    /**
    * 获取是否可用
    *
    * @return 是否可用
    */
    public Integer getIsAvailable(){
        return isAvailable;
    }

    /**
    * 设置是否可用
    * 
    * @param isAvailable 要设置的是否可用
    */
    public void setIsAvailable(Integer isAvailable){
        this.isAvailable = isAvailable;
    }

    /**
    * 获取未还期数
    *
    * @return 未还期数
    */
    public String getUnClearLoanCount(){
        return unClearLoanCount;
    }

    /**
    * 设置未还期数
    * 
    * @param unClearLoanCount 要设置的未还期数
    */
    public void setUnClearLoanCount(String unClearLoanCount){
        this.unClearLoanCount = unClearLoanCount;
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
