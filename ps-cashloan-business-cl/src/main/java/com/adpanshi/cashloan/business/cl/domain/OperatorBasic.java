package com.adpanshi.cashloan.business.cl.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 运营商信息-基础信息实体
 * 

 * @version 1.0.0
 * @date 2017-03-13 16:35:43
 *
 *
 * 
 *
 */
 public class OperatorBasic implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 主键Id
    */
    private Long id;
    
    /**
     * 用户Id
     */
    private Long userId;

    /**
    * 修改时间
    */
    private Date gmtModified;

    /**
    * 当月消费(单位为分)
    */
    private Double basicExpenditure;

    /**
    * 创建时间
    */
    private Date gmtCreate;

    /**
    * 入网时间
    */
    private Date extendJoinDt;

    /**
    * 累计积分（可以为0）
    */
    private Integer basicAllBonus;

    /**
    * 实名认证状态
    */
    private String extendCertifedStatus;

    /**
    * 余额（单位为分）
    */
    private Double basicBalance;

    /**
    * 号码
    */
    private String basicPhoneNum;

    /**
    * 归属地
    */
    private String extendBelongto;

    /**
    * 联系地址
    */
    private String extendContactAddr;

    /**
    * 网龄
    */
    private Integer extendPhoneAge;

    /**
    * 业务编号
    */
    private String bizNo;

    /**
    * 姓名
    */
    private String basicUserName;
    
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
    * 获取修改时间
    *
    * @return 修改时间
    */
    public Date getGmtModified(){
    return gmtModified;
    }

    /**
    * 设置修改时间
    * 
    * @param gmtModified 要设置的修改时间
    */
    public void setGmtModified(Date gmtModified){
    this.gmtModified = gmtModified;
    }

    /**
    * 获取当月消费(单位为分)
    *
    * @return 当月消费(单位为分)
    */
    public Double getBasicExpenditure(){
    return basicExpenditure;
    }

    /**
    * 设置当月消费(单位为分)
    * 
    * @param basicExpenditure 要设置的当月消费(单位为分)
    */
    public void setBasicExpenditure(Double basicExpenditure){
    this.basicExpenditure = basicExpenditure;
    }

    /**
    * 获取创建时间
    *
    * @return 创建时间
    */
    public Date getGmtCreate(){
    return gmtCreate;
    }

    /**
    * 设置创建时间
    * 
    * @param gmtCreate 要设置的创建时间
    */
    public void setGmtCreate(Date gmtCreate){
    this.gmtCreate = gmtCreate;
    }

    /**
    * 获取入网时间
    *
    * @return 入网时间
    */
    public Date getExtendJoinDt(){
    return extendJoinDt;
    }

    /**
    * 设置入网时间
    * 
    * @param extendJoinDt 要设置的入网时间
    */
    public void setExtendJoinDt(Date extendJoinDt){
    this.extendJoinDt = extendJoinDt;
    }

    /**
    * 获取累计积分（可以为0）
    *
    * @return 累计积分（可以为0）
    */
    public Integer getBasicAllBonus(){
    return basicAllBonus;
    }

    /**
    * 设置累计积分（可以为0）
    * 
    * @param basicAllBonus 要设置的累计积分（可以为0）
    */
    public void setBasicAllBonus(Integer basicAllBonus){
    this.basicAllBonus = basicAllBonus;
    }

    /**
    * 获取实名认证状态
    *
    * @return 实名认证状态
    */
    public String getExtendCertifedStatus(){
    return extendCertifedStatus;
    }

    /**
    * 设置实名认证状态
    * 
    * @param extendCertifedStatus 要设置的实名认证状态
    */
    public void setExtendCertifedStatus(String extendCertifedStatus){
    this.extendCertifedStatus = extendCertifedStatus;
    }

    /**
    * 获取余额（单位为分）
    *
    * @return 余额（单位为分）
    */
    public Double getBasicBalance(){
    return basicBalance;
    }

    /**
    * 设置余额（单位为分）
    * 
    * @param basicBalance 要设置的余额（单位为分）
    */
    public void setBasicBalance(Double basicBalance){
    this.basicBalance = basicBalance;
    }

    /**
    * 获取号码
    *
    * @return 号码
    */
    public String getBasicPhoneNum(){
    return basicPhoneNum;
    }

    /**
    * 设置号码
    * 
    * @param basicPhoneNum 要设置的号码
    */
    public void setBasicPhoneNum(String basicPhoneNum){
    this.basicPhoneNum = basicPhoneNum;
    }

    /**
    * 获取归属地
    *
    * @return 归属地
    */
    public String getExtendBelongto(){
    return extendBelongto;
    }

    /**
    * 设置归属地
    * 
    * @param extendBelongto 要设置的归属地
    */
    public void setExtendBelongto(String extendBelongto){
    this.extendBelongto = extendBelongto;
    }

    /**
    * 获取联系地址
    *
    * @return 联系地址
    */
    public String getExtendContactAddr(){
    return extendContactAddr;
    }

    /**
    * 设置联系地址
    * 
    * @param extendContactAddr 要设置的联系地址
    */
    public void setExtendContactAddr(String extendContactAddr){
    this.extendContactAddr = extendContactAddr;
    }

    /**
    * 获取网龄
    *
    * @return 网龄
    */
    public Integer getExtendPhoneAge() {
		return extendPhoneAge;
	}

    /**
    * 设置网龄
    * 
    * @param extendPhoneAge 要设置的网龄
    */
    public void setExtendPhoneAge(Integer extendPhoneAge) {
		this.extendPhoneAge = extendPhoneAge;
	}

	/**
    * 获取业务编号
    *
    * @return 业务编号
    */
    public String getBizNo(){
    return bizNo;
    }

    /**
    * 设置业务编号
    * 
    * @param bizNo 要设置的业务编号
    */
    public void setBizNo(String bizNo){
    this.bizNo = bizNo;
    }

    /**
    * 获取姓名
    *
    * @return 姓名
    */
    public String getBasicUserName(){
    return basicUserName;
    }

    /**
    * 设置姓名
    * 
    * @param basicUserName 要设置的姓名
    */
    public void setBasicUserName(String basicUserName){
    this.basicUserName = basicUserName;
    }

	/** 
	 * 获取添加时间
	 * @return createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/** 
	 * 设置添加时间
	 * @param createTime
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}