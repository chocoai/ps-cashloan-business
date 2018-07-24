package com.adpanshi.cashloan.business.rule.domain.equifaxReport.base;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;

/**
 * Created by zsw on 2018/7/13 0013.
 */
public class Body implements Serializable {
    @XStreamAlias("sch:InquiryResponse")
    private InquiryResponse inquiryResponse;

    public InquiryResponse getInquiryResponse() {
        return inquiryResponse;
    }

    public void setInquiryResponse(InquiryResponse inquiryResponse) {
        this.inquiryResponse = inquiryResponse;
    }
}
