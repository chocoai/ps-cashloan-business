package com.adpanshi.cashloan.business.cl.domain;

import java.io.Serializable;

/**
 * 分润资金实体
 * 
 * @author
 * @version 1.0.0
 * @date 2017-02-18 16:29:51
 *
 *
 * 
 *
 */
 public class ProfitAmount implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 主键Id
    */
    private Long id;

    /**
    * 用户id
    */
    private Long userId;

    /**
    * 总金额
    */
    private double total;

    /**
    * 未提现
    */
    private double noCashed;

    /**
    * 已提现
    */
    private double cashed;

    /**
    * 账户状态 10-启用 20-冻结
    */
    private String state;


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
    * 获取总金额
    *
    * @return 总金额
    */
    public double getTotal(){
    return total;
    }

    /**
    * 设置总金额
    * 
    * @param total 要设置的总金额
    */
    public void setTotal(double total){
    this.total = total;
    }

    /**
    * 获取未提现
    *
    * @return 未提现
    */
    public double getNoCashed(){
    return noCashed;
    }

    /**
    * 设置未提现
    * 
    * @param noCashed 要设置的未提现
    */
    public void setNoCashed(double noCashed){
    this.noCashed = noCashed;
    }

    /**
    * 获取已提现
    *
    * @return 已提现
    */
    public double getCashed(){
    return cashed;
    }

    /**
    * 设置已提现
    * 
    * @param cashed 要设置的已提现
    */
    public void setCashed(double cashed){
    this.cashed = cashed;
    }

    /**
    * 获取账户状态 10-启用 20-冻结
    *
    * @return 账户状态 10-启用 20-冻结
    */
    public String getState(){
    return state;
    }

    /**
    * 设置账户状态 10-启用 20-冻结
    * 
    * @param state 要设置的账户状态 10-启用 20-冻结
    */
    public void setState(String state){
    this.state = state;
    }

}