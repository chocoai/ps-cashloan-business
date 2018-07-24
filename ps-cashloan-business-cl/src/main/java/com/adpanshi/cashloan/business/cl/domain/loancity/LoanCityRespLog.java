package com.adpanshi.cashloan.business.cl.domain.loancity;

import java.io.Serializable;
import java.util.Date;

/**
 * 消贷同城响应消息记录表实体
 * 
 * @author yecy
 * @version 1.0.0
 * @date 2018-01-01 18:53:18

 *
 *
 */
 public class LoanCityRespLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 主键Id
    */
    private Long id;

    /**
    * 需求编码
    */
    private String requireNo;

    /**
    * 消贷同城端状态，10:已发布, 20:已关联,30:未支付,40:已支付,50:撤销中60:撤销,70:支付前已过期,80:支付后已过期,90:未通过,100:已完成
    */
    private Integer state;

    /**
    * 返回参数
    */
    private String params;

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
    * 获取需求编码
    *
    * @return 
    */
    public String getRequireNo(){
        return requireNo;
    }

    /**
    * 设置需求编码
    * 
    * @param requireNo 要设置的需求编码
    */
    public void setRequireNo(String requireNo){
        this.requireNo = requireNo;
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
    * 获取返回参数
    *
    * @return 返回参数
    */
    public String getParams(){
        return params;
    }

    /**
    * 设置返回参数
    * 
    * @param params 要设置的返回参数
    */
    public void setParams(String params){
        this.params = params;
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
