package com.adpanshi.cashloan.rule.domain.equifaxReport.base;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;

@XStreamAlias("sch:Enquiries")
public class Enquiries implements Serializable {
    @XStreamAlias("sch:Institution")
    private String institution;
    @XStreamAlias("sch:Date")
    private String date;
    @XStreamAlias("sch:Time")
    private String time;
    @XStreamAlias("sch:RequestPurpose")
    private String requestPurpose;
    @XStreamAlias("sch:Amount")
    private String amount;

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRequestPurpose() {
        return requestPurpose;
    }

    public void setRequestPurpose(String requestPurpose) {
        this.requestPurpose = requestPurpose;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
