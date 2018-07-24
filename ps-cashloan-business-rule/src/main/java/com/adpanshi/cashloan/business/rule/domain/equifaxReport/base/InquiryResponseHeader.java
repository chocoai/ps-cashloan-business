package com.adpanshi.cashloan.business.rule.domain.equifaxReport.base;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;

/**
 * Created by zsw on 2018/7/13 0013.
 */
@XStreamAlias("sch:InquiryResponseHeader")
public class InquiryResponseHeader implements Serializable {

    @XStreamAlias("sch:CustomerCode")
    private String CustomerCode;
    @XStreamAlias("sch:ClientID")
    private String ClientID;
    @XStreamAlias("sch:CustRefField")
    private String CustRefField;
    @XStreamAlias("sch:ReportOrderNO")
    private String ReportOrderNO;
    @XStreamAlias("sch:ProductCode")
    private String ProductCode;
    @XStreamAlias("sch:SuccessCode")
    private String SuccessCode;
    @XStreamAlias("sch:Date")
    private String Date;
    @XStreamAlias("sch:Time")
    private String Time;
    @XStreamAlias("sch:HitCode")
    private String HitCode;


    public String getCustomerCode() {
        return CustomerCode;
    }

    public void setCustomerCode(String customerCode) {
        CustomerCode = customerCode;
    }

    public String getClientID() {
        return ClientID;
    }

    public void setClientID(String clientID) {
        ClientID = clientID;
    }

    public String getCustRefField() {
        return CustRefField;
    }

    public void setCustRefField(String custRefField) {
        CustRefField = custRefField;
    }

    public String getReportOrderNO() {
        return ReportOrderNO;
    }

    public void setReportOrderNO(String reportOrderNO) {
        ReportOrderNO = reportOrderNO;
    }

    public String getProductCode() {
        return ProductCode;
    }

    public void setProductCode(String productCode) {
        ProductCode = productCode;
    }

    public String getSuccessCode() {
        return SuccessCode;
    }

    public void setSuccessCode(String successCode) {
        SuccessCode = successCode;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getHitCode() {
        return HitCode;
    }

    public void setHitCode(String hitCode) {
        HitCode = hitCode;
    }
}
