package com.adpanshi.cashloan.business.rule.domain.equifaxReport.base;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;

public class AccountSummary implements Serializable {
    //账户总数
    @XStreamAlias("sch:NoOfAccounts")
    private String noOfAccount;
    //过去一年开户数
    @XStreamAlias("sch:NoOfActiveAccounts")
    private String noOfActiveAccounts;
    //注销帐户数
    @XStreamAlias("sch:NoOfWriteOffs")
    private String noOfWriteOffs;
    //逾期总金额
    @XStreamAlias("sch:TotalPastDue")
    private String totalPastDue;
    //2年内最严重行为
    @XStreamAlias("sch:MostSevereStatusWithIn24Months")
    private String mostSevereStatusWithIn24Months;
    //单次最高信用额度
    @XStreamAlias("sch:SingleHighestCredit")
    private String singleHighestCredit;
    //单次最高贷款金额
    @XStreamAlias("sch:SingleHighestSanctionAmount")
    private String singleHighestSanctionAmount;
    //最高额度总和
    @XStreamAlias("sch:TotalHighCredit")
    private String totalHighCredit;
    //平均账户余额
    @XStreamAlias("sch:AverageOpenBalance")
    private String averageOpenBalance;
    //最高账户余额
    @XStreamAlias("sch:SingleHighestBalance")
    private String singleHighestBalance;
    //逾期次数
    @XStreamAlias("sch:NoOfPastDueAccounts")
    private String noOfPastDueAccounts;
    //空账户数
    @XStreamAlias("sch:NoOfZeroBalanceAccounts")
    private String noOfZeroBalanceAccounts;
    //最新开户情况
    @XStreamAlias("sch:RecentAccount")
    private String recentAccount;
    //最早开户情况
    @XStreamAlias("sch:OldestAccount")
    private String oldestAccount;
    //总账户余额
    @XStreamAlias("sch:TotalBalanceAmount")
    private String totalBalanceAmount;
    //总贷款金额
    @XStreamAlias("sch:TotalSanctionAmount")
    private String totalSanctionAmount;
    //信用卡限额总和
    @XStreamAlias("sch:TotalCreditLimit")
    private String totalCreditLimit;
    //每月支付金额
    @XStreamAlias("sch:TotalMonthlyPaymentAmount")
    private String totalMonthlyPaymentAmount;

    public String getNoOfAccount() {
        return noOfAccount;
    }

    public void setNoOfAccount(String noOfAccount) {
        this.noOfAccount = noOfAccount;
    }

    public String getNoOfActiveAccounts() {
        return noOfActiveAccounts;
    }

    public void setNoOfActiveAccounts(String noOfActiveAccounts) {
        this.noOfActiveAccounts = noOfActiveAccounts;
    }

    public String getNoOfWriteOffs() {
        return noOfWriteOffs;
    }

    public void setNoOfWriteOffs(String noOfWriteOffs) {
        this.noOfWriteOffs = noOfWriteOffs;
    }

    public String getTotalPastDue() {
        return totalPastDue;
    }

    public void setTotalPastDue(String totalPastDue) {
        this.totalPastDue = totalPastDue;
    }

    public String getMostSevereStatusWithIn24Months() {
        return mostSevereStatusWithIn24Months;
    }

    public void setMostSevereStatusWithIn24Months(String mostSevereStatusWithIn24Months) {
        this.mostSevereStatusWithIn24Months = mostSevereStatusWithIn24Months;
    }

    public String getSingleHighestCredit() {
        return singleHighestCredit;
    }

    public void setSingleHighestCredit(String singleHighestCredit) {
        this.singleHighestCredit = singleHighestCredit;
    }

    public String getSingleHighestSanctionAmount() {
        return singleHighestSanctionAmount;
    }

    public void setSingleHighestSanctionAmount(String singleHighestSanctionAmount) {
        this.singleHighestSanctionAmount = singleHighestSanctionAmount;
    }

    public String getTotalHighCredit() {
        return totalHighCredit;
    }

    public void setTotalHighCredit(String totalHighCredit) {
        this.totalHighCredit = totalHighCredit;
    }

    public String getAverageOpenBalance() {
        return averageOpenBalance;
    }

    public void setAverageOpenBalance(String averageOpenBalance) {
        this.averageOpenBalance = averageOpenBalance;
    }

    public String getSingleHighestBalance() {
        return singleHighestBalance;
    }

    public void setSingleHighestBalance(String singleHighestBalance) {
        this.singleHighestBalance = singleHighestBalance;
    }

    public String getNoOfPastDueAccounts() {
        return noOfPastDueAccounts;
    }

    public void setNoOfPastDueAccounts(String noOfPastDueAccounts) {
        this.noOfPastDueAccounts = noOfPastDueAccounts;
    }

    public String getNoOfZeroBalanceAccounts() {
        return noOfZeroBalanceAccounts;
    }

    public void setNoOfZeroBalanceAccounts(String noOfZeroBalanceAccounts) {
        this.noOfZeroBalanceAccounts = noOfZeroBalanceAccounts;
    }

    public String getRecentAccount() {
        return recentAccount;
    }

    public void setRecentAccount(String recentAccount) {
        this.recentAccount = recentAccount;
    }

    public String getOldestAccount() {
        return oldestAccount;
    }

    public void setOldestAccount(String oldestAccount) {
        this.oldestAccount = oldestAccount;
    }

    public String getTotalBalanceAmount() {
        return totalBalanceAmount;
    }

    public void setTotalBalanceAmount(String totalBalanceAmount) {
        this.totalBalanceAmount = totalBalanceAmount;
    }

    public String getTotalSanctionAmount() {
        return totalSanctionAmount;
    }

    public void setTotalSanctionAmount(String totalSanctionAmount) {
        this.totalSanctionAmount = totalSanctionAmount;
    }

    public String getTotalCreditLimit() {
        return totalCreditLimit;
    }

    public void setTotalCreditLimit(String totalCreditLimit) {
        this.totalCreditLimit = totalCreditLimit;
    }

    public String getTotalMonthlyPaymentAmount() {
        return totalMonthlyPaymentAmount;
    }

    public void setTotalMonthlyPaymentAmount(String totalMonthlyPaymentAmount) {
        this.totalMonthlyPaymentAmount = totalMonthlyPaymentAmount;
    }
}
