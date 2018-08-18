package com.adpanshi.cashloan.rule.domain.equifaxReport.base;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;

@XStreamAlias("sch:IDCard")
public class IDCard  implements Serializable {
    @XStreamAlias("sch:IdNumber")
    private String idNumber;

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }
}
