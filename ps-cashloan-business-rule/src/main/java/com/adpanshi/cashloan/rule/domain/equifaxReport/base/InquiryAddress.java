package com.adpanshi.cashloan.rule.domain.equifaxReport.base;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;

/**
 * Created by zsw on 2018/7/13 0013.
 */
@XStreamAlias("sch:InquiryAddress")
public class InquiryAddress implements Serializable {

    @XStreamAlias("sch:AddressLine")
    private String AddressLine;
    @XStreamAlias("sch:State")
    private String State;
    @XStreamAlias("sch:Postal")
    private String Postal;

    public String getAddressLine() {
        return AddressLine;
    }

    public void setAddressLine(String addressLine) {
        AddressLine = addressLine;
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
}
