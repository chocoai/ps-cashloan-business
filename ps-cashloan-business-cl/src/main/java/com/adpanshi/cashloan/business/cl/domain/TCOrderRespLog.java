package com.adpanshi.cashloan.business.cl.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 天创运营商异步响应表实体
 * 
 * @author yecy
 * @version 1.0.0
 * @date 2018-02-27 11:07:10

 *
 *
 */
 public class TCOrderRespLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 主键Id
    */
    private Long id;

    /**
    * 
    */
    private String orderId;

    /**
    * 
    */
    private Long userId;

    /**
    * 异步响应结果
    */
    private String respParams;

    /**
    * 
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
    * 获取
    *
    * @return 
    */
    public String getOrderId(){
        return orderId;
    }

    /**
    * 设置
    * 
    * @param orderId 要设置的
    */
    public void setOrderId(String orderId){
        this.orderId = orderId;
    }

    /**
    * 获取
    *
    * @return 
    */
    public Long getUserId(){
        return userId;
    }

    /**
    * 设置
    * 
    * @param userId 要设置的
    */
    public void setUserId(Long userId){
        this.userId = userId;
    }

    /**
    * 获取异步响应结果
    *
    * @return 异步响应结果
    */
    public String getRespParams(){
        return respParams;
    }

    /**
    * 设置异步响应结果
    * 
    * @param respParams 要设置的异步响应结果
    */
    public void setRespParams(String respParams){
        this.respParams = respParams;
    }

    /**
    * 获取
    *
    * @return 
    */
    public Date getCreateTime(){
        return createTime;
    }

    /**
    * 设置
    * 
    * @param createTime 要设置的
    */
    public void setCreateTime(Date createTime){
        this.createTime = createTime;
    }

}
