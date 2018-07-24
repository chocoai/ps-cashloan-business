package com.adpanshi.cashloan.business.cl.domain.yincheng;

import java.io.Serializable;

/**
 * 银程借款中其他相关字段存储实体
 * 
 * @author yecy
 * @version 1.0.0
 * @date 2018-03-29 22:58:10

 *
 *
 */
 public class YinChengBorrowOther implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * cl_borrow_main表中的id
    */
    private Long borrowMainId;

    /**
    * 其他字段以json格式存储
    */
    private String otherJson;


    /**
    * 获取cl_borrow_main表中的id
    *
    * @return cl_borrow_main表中的id
    */
    public Long getBorrowMainId(){
        return borrowMainId;
    }

    /**
    * 设置cl_borrow_main表中的id
    * 
    * @param borrowMainId 要设置的cl_borrow_main表中的id
    */
    public void setBorrowMainId(Long borrowMainId){
        this.borrowMainId = borrowMainId;
    }

    /**
    * 获取其他字段以json格式存储
    *
    * @return 其他字段以json格式存储
    */
    public String getOtherJson(){
        return otherJson;
    }

    /**
    * 设置其他字段以json格式存储
    * 
    * @param otherJson 要设置的其他字段以json格式存储
    */
    public void setOtherJson(String otherJson){
        this.otherJson = otherJson;
    }

}
