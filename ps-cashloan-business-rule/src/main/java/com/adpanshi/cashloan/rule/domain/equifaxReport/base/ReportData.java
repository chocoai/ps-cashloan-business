package com.adpanshi.cashloan.rule.domain.equifaxReport.base;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.io.Serializable;
import java.util.List;

@XStreamAlias("sch:ReportData")
public class ReportData implements Serializable {
    //错误信息
    @XStreamAlias("sch:Error")
    private Error error;
    //身份及联系信息
    @XStreamAlias("sch:IDAndContactInfo")
    private IDAndContactInfo idAndContactInfo;
    //信用分
    @XStreamAlias("sch:Score")
    private Score score;
    //调查总结
    @XStreamAlias("sch:EnquirySummary")
    private EnquirySummary enquirySummary;
//    @XStreamAlias("sch:Enquiries")
    //被调查信息列表
    @XStreamImplicit(itemFieldName="sch:Enquiries")
    private List<Enquiries> enquiriesList;
    //账户总结
    @XStreamAlias("sch:AccountSummary")
    private AccountSummary accountSummary;
    //最近活动
    @XStreamAlias("sch:RecentActivities")
    private RecentActivities recentActivities;
    //其他关键信息
    @XStreamAlias("sch:OtherKeyInd")
    private OtherKeyInd otherKeyInd;
    //账户详情
    @XStreamAlias("sch:AccountDetails")
    private AccountDetails accountDetails;

//    private Glossary glossary;
//
//    private Disclaimer disclaimer;
    //信用分元素
    @XStreamAlias("sch:ScoringElements")
    private ScoringElements scoringElements;

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }

    public EnquirySummary getEnquirySummary() {
        return enquirySummary;
    }

    public void setEnquirySummary(EnquirySummary enquirySummary) {
        this.enquirySummary = enquirySummary;
    }

    public List<Enquiries> getEnquiriesList() {
        return enquiriesList;
    }

    public void setEnquiriesList(List<Enquiries> enquiriesList) {
        this.enquiriesList = enquiriesList;
    }

    public IDAndContactInfo getIdAndContactInfo() {
        return idAndContactInfo;
    }

    public void setIdAndContactInfo(IDAndContactInfo idAndContactInfo) {
        this.idAndContactInfo = idAndContactInfo;
    }

    public AccountSummary getAccountSummary() {
        return accountSummary;
    }

    public void setAccountSummary(AccountSummary accountSummary) {
        this.accountSummary = accountSummary;
    }

    public RecentActivities getRecentActivities() {
        return recentActivities;
    }

    public void setRecentActivities(RecentActivities recentActivities) {
        this.recentActivities = recentActivities;
    }

    public OtherKeyInd getOtherKeyInd() {
        return otherKeyInd;
    }

    public void setOtherKeyInd(OtherKeyInd otherKeyInd) {
        this.otherKeyInd = otherKeyInd;
    }

    public AccountDetails getAccountDetails() {
        return accountDetails;
    }

    public void setAccountDetails(AccountDetails accountDetails) {
        this.accountDetails = accountDetails;
    }

    public ScoringElements getScoringElements() {
        return scoringElements;
    }

    public void setScoringElements(ScoringElements scoringElements) {
        this.scoringElements = scoringElements;
    }
}
