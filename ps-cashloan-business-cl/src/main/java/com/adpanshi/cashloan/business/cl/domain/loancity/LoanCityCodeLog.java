package com.adpanshi.cashloan.business.cl.domain.loancity;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户输入验证码记录实体
 * 
 * @author yecy
 * @version 1.0.0
 * @date 2018-01-01 18:51:26

 *
 *
 */
 public class LoanCityCodeLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 主键Id
    */
    private Long id;

    /**
    * 关联的借款订单id
    */
    private Long borrowMainId;

    /**
    * 输入的验证码
    */
    private String idCode;

    /**
    * 验证码是否正确
    */
    private Boolean success;

    /**
    * 创建时间
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
    * 获取关联的借款订单id
    *
    * @return 关联的借款订单id
    */
    public Long getBorrowMainId(){
        return borrowMainId;
    }

    /**
    * 设置关联的借款订单id
    * 
    * @param borrowMainId 要设置的关联的借款订单id
    */
    public void setBorrowMainId(Long borrowMainId){
        this.borrowMainId = borrowMainId;
    }

    /**
    * 获取输入的验证码
    *
    * @return 输入的验证码
    */
    public String getIdCode(){
        return idCode;
    }

    /**
    * 设置输入的验证码
    * 
    * @param idCode 要设置的输入的验证码
    */
    public void setIdCode(String idCode){
        this.idCode = idCode;
    }

    /**
    * 获取验证码是否正确
    *
    * @return 验证码是否正确
    */
    public Boolean getSuccess(){
        return success;
    }

    /**
    * 设置验证码是否正确
    * 
    * @param success 要设置的验证码是否正确
    */
    public void setSuccess(Boolean success){
        this.success = success;
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

}
