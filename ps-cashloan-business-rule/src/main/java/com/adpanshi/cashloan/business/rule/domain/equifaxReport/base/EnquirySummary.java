package com.adpanshi.cashloan.business.rule.domain.equifaxReport.base;


import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;

public class EnquirySummary implements Serializable {
    @XStreamAlias("sch:Purpose")
    private String purpose;
    //总次数
    @XStreamAlias("sch:Total")
    private String total;
    //过去30天次数
    @XStreamAlias("sch:Past30Days")
    private String past30Days;
    //过去12个月次数
    @XStreamAlias("sch:Past12Months")
    private String past12Months;
    //过去24个月次数
    @XStreamAlias("sch:Past24Months")
    private String past24Months;
    //最近查询时间
    @XStreamAlias("sch:Recent")
    private String recent;

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getPast30Days() {
        return past30Days;
    }

    public void setPast30Days(String past30Days) {
        this.past30Days = past30Days;
    }

    public String getPast12Months() {
        return past12Months;
    }

    public void setPast12Months(String past12Months) {
        this.past12Months = past12Months;
    }

    public String getPast24Months() {
        return past24Months;
    }

    public void setPast24Months(String past24Months) {
        this.past24Months = past24Months;
    }

    public String getRecent() {
        return recent;
    }

    public void setRecent(String recent) {
        this.recent = recent;
    }
}
