package com.adpanshi.cashloan.business.cl.domain.yincheng;

import java.io.Serializable;
import java.util.Date;

/**
 * 银程投资标存储表实体
 * 
 * @author yecy
 * @version 1.0.0
 * @date 2017-11-02 20:51:50

 *
 *
 */
 public class Invest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 标编号(唯一标识)
    */
    private String fullScaleNo;

    /**
    * 标名称
    */
    private String fullScaleName;

    /**
    * 标总金额
    */
    private Double money;

    /**
    * 标创建时间
    */
    private Date scaleCreateTime;

    /**
    * 记录创建时间
    */
    private Date createTime;


    /**
    * 获取标编号(唯一标识)
    *
    * @return 标编号(唯一标识)
    */
    public String getFullScaleNo(){
        return fullScaleNo;
    }

    /**
    * 设置标编号(唯一标识)
    * 
    * @param fullScaleNo 要设置的标编号(唯一标识)
    */
    public void setFullScaleNo(String fullScaleNo){
        this.fullScaleNo = fullScaleNo;
    }

    /**
    * 获取标名称
    *
    * @return 标名称
    */
    public String getFullScaleName(){
        return fullScaleName;
    }

    /**
    * 设置标名称
    * 
    * @param fullScaleName 要设置的标名称
    */
    public void setFullScaleName(String fullScaleName){
        this.fullScaleName = fullScaleName;
    }

    /**
    * 获取标总金额
    *
    * @return 标总金额
    */
    public Double getMoney(){
        return money;
    }

    /**
    * 设置标总金额
    * 
    * @param money 要设置的标总金额
    */
    public void setMoney(Double money){
        this.money = money;
    }

    /**
    * 获取标创建时间
    *
    * @return 标创建时间
    */
    public Date getScaleCreateTime(){
        return scaleCreateTime;
    }

    /**
    * 设置标创建时间
    * 
    * @param scaleCreateTime 要设置的标创建时间
    */
    public void setScaleCreateTime(Date scaleCreateTime){
        this.scaleCreateTime = scaleCreateTime;
    }

    /**
    * 获取记录创建时间
    *
    * @return 记录创建时间
    */
    public Date getCreateTime(){
        return createTime;
    }

    /**
    * 设置记录创建时间
    * 
    * @param createTime 要设置的记录创建时间
    */
    public void setCreateTime(Date createTime){
        this.createTime = createTime;
    }

}
