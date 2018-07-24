package com.adpanshi.cashloan.business.cl.model.yincheng;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 借款模型
 *
 * @author yecy
 */
public class BorrowModel {
    private String uid;   // 用户id是借款人记录接口返回的用户id
    @JSONField(name = "order_no")
    private String orderNo; //   借款订单号
    private String money; //   借款金额
    private String interest;   // 借款人利息
    private Integer flag;   // 期限类型 0月 1天 默认1
    private Integer day;  //  当flag=1 就是天数 当flag=0就是月数
    @JSONField(name = "by_stages")
    private Integer byStages; //分几期
    @JSONField(name = "purpose_type")
    private Integer purposeType;//   借款用途类型 (与我们这边的类型保持一致)
    @JSONField(name = "purpose_json")
    private String purposeJson;//   借款用户详细借款数据

    @JSONField(name = "other_json")
    private String otherJson;//   其他字段

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getByStages() {
        return byStages;
    }

    public void setByStages(Integer byStages) {
        this.byStages = byStages;
    }

    public Integer getPurposeType() {
        return purposeType;
    }

    public void setPurposeType(Integer purposeType) {
        this.purposeType = purposeType;
    }

    public String getPurposeJson() {
        return purposeJson;
    }

    public void setPurposeJson(String purposeJson) {
        this.purposeJson = purposeJson;
    }

    public String getOtherJson() {
        return otherJson;
    }

    public void setOtherJson(String otherJson) {
        this.otherJson = otherJson;
    }

    public enum timeLimitType {
        DAY(1), MONTH(0);
        private Integer flag;

        timeLimitType(Integer flag) {
            this.flag = flag;
        }

        public Integer getFlag() {
            return flag;
        }
    }

}
