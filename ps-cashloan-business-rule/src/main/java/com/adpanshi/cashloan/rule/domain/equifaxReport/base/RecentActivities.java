package com.adpanshi.cashloan.rule.domain.equifaxReport.base;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;

@XStreamAlias("sch:RecentActivities")
public class RecentActivities implements Serializable {
    @XStreamAlias("sch:AccountsDeliquent")
    private String accountsDeliquent;
    @XStreamAlias("sch:AccountsOpened")
    private String accountsOpened;
    @XStreamAlias("sch:TotalInquiries")
    private String totalInquiries;
    @XStreamAlias("sch:AccountsUpdated")
    private String accountsUpdated;

    public String getAccountsDeliquent() {
        return accountsDeliquent;
    }

    public void setAccountsDeliquent(String accountsDeliquent) {
        this.accountsDeliquent = accountsDeliquent;
    }

    public String getAccountsOpened() {
        return accountsOpened;
    }

    public void setAccountsOpened(String accountsOpened) {
        this.accountsOpened = accountsOpened;
    }

    public String getTotalInquiries() {
        return totalInquiries;
    }

    public void setTotalInquiries(String totalInquiries) {
        this.totalInquiries = totalInquiries;
    }

    public String getAccountsUpdated() {
        return accountsUpdated;
    }

    public void setAccountsUpdated(String accountsUpdated) {
        this.accountsUpdated = accountsUpdated;
    }
}
