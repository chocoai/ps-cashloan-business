package com.adpanshi.cashloan.business.rule.domain.equifaxReport.base;


import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;

public class Error implements Serializable {
    @XStreamAlias("sch:ErrorCode")
    private String errorCode;
    @XStreamAlias("sch:ErrorMsg")
    private String errorMsg;
    @XStreamAlias("sch:Details")
    private String details;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
