package com.adpanshi.cashloan.rule.domain.equifaxReport.base;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zsw on 2018/7/13 0013.
 */
public class InquiryRequestInfo implements Serializable {
    @XStreamAlias("sch:InquiryPurpose")
    private String InquiryPurpose;
    @XStreamAlias("sch:FirstName")
    private String FirstName;
    @XStreamAlias("sch:LastName")
    private String LastName;
    @XStreamAlias("sch:AddrLine1")
    private String AddrLine1;
    @XStreamAlias("sch:State")
    private String State;
    @XStreamAlias("sch:Postal")
    private String Postal;
    @XStreamAlias("sch:InquiryAddresses")
    private List<InquiryAddress> inquiryAddresses;
    @XStreamAlias("sch:InquiryPhones")
    private List<InquiryPhone> inquiryPhones;
    @XStreamAlias("sch:DOB")
    private String DOB;
    @XStreamAlias("sch:PANId")
    private String PANId;
    @XStreamAlias("sch:MobilePhone")
    private String MobilePhone;

    public String getInquiryPurpose() {
        return InquiryPurpose;
    }

    public void setInquiryPurpose(String inquiryPurpose) {
        InquiryPurpose = inquiryPurpose;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getAddrLine1() {
        return AddrLine1;
    }

    public void setAddrLine1(String addrLine1) {
        AddrLine1 = addrLine1;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getPostal() {
        return Postal;
    }

    public void setPostal(String postal) {
        Postal = postal;
    }

    public List<InquiryAddress> getInquiryAddresses() {
        return inquiryAddresses;
    }

    public void setInquiryAddresses(List<InquiryAddress> inquiryAddresses) {
        this.inquiryAddresses = inquiryAddresses;
    }

    public List<InquiryPhone> getInquiryPhones() {
        return inquiryPhones;
    }

    public void setInquiryPhones(List<InquiryPhone> inquiryPhones) {
        this.inquiryPhones = inquiryPhones;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getPANId() {
        return PANId;
    }

    public void setPANId(String PANId) {
        this.PANId = PANId;
    }

    public String getMobilePhone() {
        return MobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        MobilePhone = mobilePhone;
    }
}
