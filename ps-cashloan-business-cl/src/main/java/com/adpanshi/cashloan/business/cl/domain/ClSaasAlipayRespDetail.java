package com.adpanshi.cashloan.business.cl.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * saas alipay response 数据实体
 * 
 * @author nmnl
 * @version 1.0.0
 * @date 2018-02-28 09:28:48

 *
 *
 */
 public class ClSaasAlipayRespDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 主键Id
    */
    private Long id;

    /**
    * cl_saas_alipay_req_log id
    */
    private Long reqLogId;

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
    * 返回数据状态
    */
    private String resCode;

    /**
    * 报告结果
    */
    private String resParams;

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
    * 获取cl_saas_alipay_req_log id
    *
    * @return cl_saas_alipay_req_log id
    */
    public Long getReqLogId(){
        return reqLogId;
    }

    /**
    * 设置cl_saas_alipay_req_log id
    * 
    * @param reqLogId 要设置的cl_saas_alipay_req_log id
    */
    public void setReqLogId(Long reqLogId){
        this.reqLogId = reqLogId;
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
    * 获取返回数据状态
    *
    * @return 返回数据状态
    */
    public String getResCode(){
        return resCode;
    }

    /**
    * 设置返回数据状态
    * 
    * @param resCode 要设置的返回数据状态
    */
    public void setResCode(String resCode){
        this.resCode = resCode;
    }

    /**
    * 获取报告结果
    *
    * @return 报告结果
    */
    public String getResParams(){
        return resParams;
    }

    /**
    * 设置报告结果
    * 
    * @param resParams 要设置的报告结果
    */
    public void setResParams(String resParams){
        this.resParams = resParams;
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

}
