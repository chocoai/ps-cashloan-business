package com.adpanshi.cashloan.business.cl.domain.yincheng;

import java.io.Serializable;

/**
 * 投资标与借款单对应表实体
 * 
 * @author yecy
 * @version 1.0.0
 * @date 2017-11-02 21:10:24

 *
 *
 */
 public class InvestBorrowRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 投资标编号
    */
    private String fullScaleNo;

    /**
    * 借款单号
    */
    private String orderNo;

    public InvestBorrowRelation(String fullScaleNo, String orderNo){
        this.fullScaleNo = fullScaleNo;
        this.orderNo = orderNo;
    }

    /**
    * 获取投资标编号
    *
    * @return 投资标编号
    */
    public String getFullScaleNo(){
        return fullScaleNo;
    }

    /**
    * 设置投资标编号
    * 
    * @param fullScaleNo 要设置的投资标编号
    */
    public void setFullScaleNo(String fullScaleNo){
        this.fullScaleNo = fullScaleNo;
    }

    /**
    * 获取借款单号
    *
    * @return 借款单号
    */
    public String getOrderNo(){
        return orderNo;
    }

    /**
    * 设置借款单号
    * 
    * @param orderNo 要设置的借款单号
    */
    public void setOrderNo(String orderNo){
        this.orderNo = orderNo;
    }

}
