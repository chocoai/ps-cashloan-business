package com.adpanshi.cashloan.business.rule.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 大风策请求记录表实体
 * 
 * @author yecy
 * @version 1.0.0
 * @date 2018-02-05 18:01:30

 *
 *
 */
 public class TcwindReqLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 主键Id
    */
    private Long id;

    /**
    * 访问的接口类型
    */
    private String service;

    /**
    * 用户id
    */
    private Long userId;

    /**
    * 请求参数
    */
    private String params;

    /**
    * 创建时间
    */
    private Date createTime;

    /**
    * 同步响应结果
    */
    private String respParams;

    /**
    * 同步响应时间
    */
    private Date respTime;

    /**
    * 是否响应成功
    */
    private Boolean success;


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
    * 获取访问的接口类型
    *
    * @return 访问的接口类型
    */
    public String getService(){
        return service;
    }

    /**
    * 设置访问的接口类型
    * 
    * @param service 要设置的访问的接口类型
    */
    public void setService(String service){
        this.service = service;
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
    * 获取请求参数
    *
    * @return 请求参数
    */
    public String getParams(){
        return params;
    }

    /**
    * 设置请求参数
    * 
    * @param params 要设置的请求参数
    */
    public void setParams(String params){
        this.params = params;
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
    * 获取同步响应结果
    *
    * @return 同步响应结果
    */
    public String getRespParams(){
        return respParams;
    }

    /**
    * 设置同步响应结果
    * 
    * @param respParams 要设置的同步响应结果
    */
    public void setRespParams(String respParams){
        this.respParams = respParams;
    }

    /**
    * 获取同步响应时间
    *
    * @return 同步响应时间
    */
    public Date getRespTime(){
        return respTime;
    }

    /**
    * 设置同步响应时间
    * 
    * @param respTime 要设置的同步响应时间
    */
    public void setRespTime(Date respTime){
        this.respTime = respTime;
    }

    /**
    * 获取是否响应成功
    *
    * @return 是否响应成功
    */
    public Boolean getSuccess(){
        return success;
    }

    /**
    * 设置是否响应成功
    * 
    * @param success 要设置的是否响应成功
    */
    public void setSuccess(Boolean success){
        this.success = success;
    }

}
