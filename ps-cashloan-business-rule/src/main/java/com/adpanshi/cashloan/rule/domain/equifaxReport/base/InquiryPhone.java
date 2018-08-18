package com.adpanshi.cashloan.rule.domain.equifaxReport.base;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;

/**
 * Created by zsw on 2018/7/13 0013.
 */
@XStreamAlias("sch:InquiryPhone")
public class InquiryPhone implements Serializable {
    @XStreamAlias("sch:Number")
    private String Number;
    @XStreamAlias("sch:PhoneType")
    private String PhoneType;

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getPhoneType() {
        return PhoneType;
    }

    public void setPhoneType(String phoneType) {
        PhoneType = phoneType;
    }
}
