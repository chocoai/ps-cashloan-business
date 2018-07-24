package com.adpanshi.cashloan.business.cl.model.yincheng;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;
import java.util.List;

/**
 * 投资model
 *
 * @author yecy
 * @date 2017/11/3 9:05
 */
public class InvestModel {
    /**
     * 标编号(唯一标识)
     */
    @JSONField(name = "full_scale_no")
    private String fullScaleNo;

    /**
     * 标名称
     */
    @JSONField(name = "full_scale_name")
    private String fullScaleName;

    /**
     * 标总金额
     */
    private Double money;

    /**
     * 标创建时间(对应Invest中的scaleCreateTime字段)
     */
    @JSONField(name = "create_time")
    private Date createTime;

    /**
     * 投资记录
     */
    private List<InvestRecordModel> investors;

    /**
     * 允许放款的借款订单号
     */
    @JSONField(name = "order_no")
    private List<String> orderNo;

    public String getFullScaleNo() {
        return fullScaleNo;
    }

    public void setFullScaleNo(String fullScaleNo) {
        this.fullScaleNo = fullScaleNo;
    }

    public String getFullScaleName() {
        return fullScaleName;
    }

    public void setFullScaleName(String fullScaleName) {
        this.fullScaleName = fullScaleName;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<InvestRecordModel> getInvestors() {
        return investors;
    }

    public void setInvestors(List<InvestRecordModel> investors) {
        this.investors = investors;
    }

    public List<String> getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(List<String> orderNo) {
        this.orderNo = orderNo;
    }
}
