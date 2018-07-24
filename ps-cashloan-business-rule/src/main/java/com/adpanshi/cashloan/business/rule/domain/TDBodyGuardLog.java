package com.adpanshi.cashloan.business.rule.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 同盾第三方请求记录实体
 * 

 * @version 1.0.0
 * @date 2017-04-26 15:26:56
 *
 *
 *
 *
 */
 public class TDBodyGuardLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 主键Id
    */
    private Long id;

    /**
    * 用户标识
    */
    private Long userId;

   /**
    * 信贷保镖返回结果
    */
   private String params;

   /**
    * 信贷保镖返回结果
    */
   private String result;

    /**
    * 添加时间
    */
    private Date createTime;


   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public Long getUserId() {
      return userId;
   }

   public void setUserId(Long userId) {
      this.userId = userId;
   }

   public String getParams() {
      return params;
   }

   public void setParams(String params) {
      this.params = params;
   }

   public String getResult() {
      return result;
   }

   public void setResult(String result) {
      this.result = result;
   }

   public Date getCreateTime() {
      return createTime;
   }

   public void setCreateTime(Date createTime) {
      this.createTime = createTime;
   }
}