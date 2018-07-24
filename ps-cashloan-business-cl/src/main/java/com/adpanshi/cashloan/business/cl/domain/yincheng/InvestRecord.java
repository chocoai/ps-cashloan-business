package com.adpanshi.cashloan.business.cl.domain.yincheng;

import java.io.Serializable;
import java.util.Date;

/**
 * 投资人投资记录表
 * 
 * @author yecy
 * @version 1.0.0
 * @date 2017-11-02 20:57:14

 *
 *
 */
 public class InvestRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 主键Id
    */
    private Long id;

    /**
    * 投资标编号
    */
    private String fullScaleNo;

    /**
    * 投资人id
    */
    private Long investorId;

    /**
    * 投资时间
    */
    private Date investTime;

    /**
    * 投资金额
    */
    private Double money;

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
    * 获取投资人id
    *
    * @return 投资人id
    */
    public Long getInvestorId(){
        return investorId;
    }

    /**
    * 设置投资人id
    * 
    * @param investorId 要设置的投资人id
    */
    public void setInvestorId(Long investorId){
        this.investorId = investorId;
    }

    /**
    * 获取投资时间
    *
    * @return 投资时间
    */
    public Date getInvestTime(){
        return investTime;
    }

    /**
    * 设置投资时间
    * 
    * @param investTime 要设置的投资时间
    */
    public void setInvestTime(Date investTime){
        this.investTime = investTime;
    }

    /**
    * 获取投资金额
    *
    * @return 投资金额
    */
    public Double getMoney(){
        return money;
    }

    /**
    * 设置投资金额
    * 
    * @param money 要设置的投资金额
    */
    public void setMoney(Double money){
        this.money = money;
    }

    /**
     * 获取创建时间
     *
     * @return 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 要设置的创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
