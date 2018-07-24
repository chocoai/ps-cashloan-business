package com.adpanshi.cashloan.business.cl.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * saas alipay request 数据实体
 * 
 * @author nmnl
 * @version 1.0.0
 * @date 2018-02-27 11:59:36

 *
 *
 */
 public class ClSaasAlipayReqLog implements Serializable {

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
    * 报告状态
    */
    private String reqCode;

    /**
    * 报告结果
    */
    private String reqParams;

    /**
    * 更新时间
    */
    private Date updateTime;

    /**
     * 请求渠道
     */
    private String reqChannel;


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
    * 获取报告状态
    *
    * @return 报告状态
    */
    public String getReqCode(){
        return reqCode;
    }

    /**
    * 设置报告状态
    * 
    * @param reqCode 要设置的报告状态
    */
    public void setReqCode(String reqCode){
        this.reqCode = reqCode;
    }

    /**
    * 获取报告结果
    *
    * @return 报告结果
    */
    public String getReqParams(){
        return reqParams;
    }

    /**
    * 设置报告结果
    * 
    * @param reqParams 要设置的报告结果
    */
    public void setReqParams(String reqParams){
        this.reqParams = reqParams;
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
     * 获取请求渠道
     *
     * @return 请求渠道
     */
    public String getReqChannel() {
        return reqChannel;
    }

    /**
     * 设置请求渠道
     *
     * @param reqChannel 要设置的请求渠道
     */
    public void setReqChannel(String reqChannel) {
        this.reqChannel = reqChannel;
    }
}
