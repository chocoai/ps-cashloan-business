package com.adpanshi.cashloan.business.rule.domain.equifaxReport.base;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;

public class Score implements Serializable {
    @XStreamAlias("sch:Name")
    private String name;
    //描述
    @XStreamAlias("sch:Description")
    private String description;
    //分值
    @XStreamAlias("sch:Value")
    private String value;
    @XStreamAlias("sch:ReasonCode")
    private String reasonCode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }
}
