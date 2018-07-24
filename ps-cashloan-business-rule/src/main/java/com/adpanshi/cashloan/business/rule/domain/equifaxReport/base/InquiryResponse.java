package com.adpanshi.cashloan.business.rule.domain.equifaxReport.base;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;

/**
 * Created by zsw on 2018/7/13 0013.
 */
@XStreamAlias("sch:InquiryResponse")
public class InquiryResponse implements Serializable {
    @XStreamAlias("sch:InquiryResponseHeader")
    private InquiryResponseHeader inquiryResponseHeader;
    @XStreamAlias("sch:InquiryRequestInfo")
    private InquiryRequestInfo inquiryRequestInfo;
    @XStreamAlias("sch:ReportData")
    private ReportData reportData;

    public InquiryResponseHeader getInquiryResponseHeader() {
        return inquiryResponseHeader;
    }

    public void setInquiryResponseHeader(InquiryResponseHeader inquiryResponseHeader) {
        this.inquiryResponseHeader = inquiryResponseHeader;
    }

    public InquiryRequestInfo getInquiryRequestInfo() {
        return inquiryRequestInfo;
    }

    public void setInquiryRequestInfo(InquiryRequestInfo inquiryRequestInfo) {
        this.inquiryRequestInfo = inquiryRequestInfo;
    }

    public ReportData getReportData() {
        return reportData;
    }

    public void setReportData(ReportData reportData) {
        this.reportData = reportData;
    }
}
