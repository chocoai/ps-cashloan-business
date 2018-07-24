package com.adpanshi.cashloan.business.core.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * APP用户操作日志表实体
 * 
 * @author tq
 * @version 1.0.0
 * @date 2018-06-19 11:18:07

 *
 *
 */
 public class ClLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 主键Id
    */
    private Long id;

    /**
    * 登录名
    */
    private String loginName;

    /**
    * 操作方法
    */
    private String optionMethod;

    /**
    * 输入参数
    */
    private String inputParam;

    /**
    * 输出参数
    */
    private String outputParam;

    /**
    * 操作IP
    */
    private String optionIp;

    /**
    * 操作时间
    */
    private Date createTime;

    /**
    * app操作用户id
    */
    private Long userId;


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
    * 获取登录名
    *
    * @return 登录名
    */
    public String getLoginName(){
        return loginName;
    }

    /**
    * 设置登录名
    * 
    * @param loginName 要设置的登录名
    */
    public void setLoginName(String loginName){
        this.loginName = loginName;
    }

    /**
    * 获取操作方法
    *
    * @return 操作方法
    */
    public String getOptionMethod(){
        return optionMethod;
    }

    /**
    * 设置操作方法
    * 
    * @param optionMethod 要设置的操作方法
    */
    public void setOptionMethod(String optionMethod){
        this.optionMethod = optionMethod;
    }

    /**
    * 获取输入参数
    *
    * @return 输入参数
    */
    public String getInputParam(){
        return inputParam;
    }

    /**
    * 设置输入参数
    * 
    * @param inputParam 要设置的输入参数
    */
    public void setInputParam(String inputParam){
        this.inputParam = inputParam;
    }

    /**
    * 获取输出参数
    *
    * @return 输出参数
    */
    public String getOutputParam(){
        return outputParam;
    }

    /**
    * 设置输出参数
    * 
    * @param outputParam 要设置的输出参数
    */
    public void setOutputParam(String outputParam){
        this.outputParam = outputParam;
    }

    /**
    * 获取操作IP
    *
    * @return 操作IP
    */
    public String getOptionIp(){
        return optionIp;
    }

    /**
    * 设置操作IP
    * 
    * @param optionIp 要设置的操作IP
    */
    public void setOptionIp(String optionIp){
        this.optionIp = optionIp;
    }

    /**
    * 获取操作时间
    *
    * @return 操作时间
    */
    public Date getCreateTime(){
        return createTime;
    }

    /**
    * 设置操作时间
    * 
    * @param createTime 要设置的操作时间
    */
    public void setCreateTime(Date createTime){
        this.createTime = createTime;
    }

    /**
    * 获取app操作用户id
    *
    * @return app操作用户id
    */
    public Long getUserId(){
        return userId;
    }

    /**
    * 设置app操作用户id
    * 
    * @param userId 要设置的app操作用户id
    */
    public void setUserId(Long userId){
        this.userId = userId;
    }

}
