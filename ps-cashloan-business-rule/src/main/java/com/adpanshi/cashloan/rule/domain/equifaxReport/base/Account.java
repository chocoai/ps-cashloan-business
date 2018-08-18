package com.adpanshi.cashloan.rule.domain.equifaxReport.base;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;

@XStreamAlias("sch:Account")
public class Account implements Serializable {
    @XStreamAlias("sch:AccountNumber")
    private String accountNumber;
    @XStreamAlias("sch:Institution")
    private String institution;
    @XStreamAlias("sch:AccountType")
    private String accountType;
    @XStreamAlias("sch:OwnershipType")
    private String ownershipType;
    @XStreamAlias("sch:Balance")
    private String balance;
    @XStreamAlias("sch:PastDueAmount")
    private String pastDueAmount;
    @XStreamAlias("sch:LastPayment")
    private String lastPayment;
    @XStreamAlias("sch:WriteOffAmount")
    private String writeOffAmount;
    @XStreamAlias("sch:Open")
    private String open;
    @XStreamAlias("sch:SanctionAmount")
    private String sanctionAmount;
    @XStreamAlias("sch:LastPaymentDate")
    private String lastPaymentDate;
    @XStreamAlias("sch:HighCredit")
    private String highCredit;
    @XStreamAlias("sch:DateReported")
    private String dateReported;
    @XStreamAlias("sch:DateOpened")
    private String dateOpened;
    @XStreamAlias("sch:DateClosed")
    private String dateClosed;
    @XStreamAlias("sch:Reason")
    private String reason;
    @XStreamAlias("sch:InterestRate")
    private String interestRate;
    @XStreamAlias("sch:RepaymentTenure")
    private String repaymentTenure;
    @XStreamAlias("sch:DisputeCode")
    private String disputeCode;
    @XStreamAlias("sch:InstallmentAmount")
    private String installmentAmount;
    @XStreamAlias("sch:TermFrequency")
    private String termFrequency;
    @XStreamAlias("sch:CreditLimit")
    private String creditLimit;
    @XStreamAlias("sch:CollateralValue")
    private String collateralValue;
    @XStreamAlias("sch:CollateralType")
    private String collateralType;
    @XStreamAlias("sch:AccountStatus")
    private String accountStatus;
    @XStreamAlias("sch:AssetClassification")
    private String assetClassification;
    @XStreamAlias("sch:SuitFiledStatus")
    private String suitFiledStatus;
    @XStreamAlias("sch:History48Months")
    private String history48Months;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getOwnershipType() {
        return ownershipType;
    }

    public void setOwnershipType(String ownershipType) {
        this.ownershipType = ownershipType;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getPastDueAmount() {
        return pastDueAmount;
    }

    public void setPastDueAmount(String pastDueAmount) {
        this.pastDueAmount = pastDueAmount;
    }

    public String getLastPayment() {
        return lastPayment;
    }

    public void setLastPayment(String lastPayment) {
        this.lastPayment = lastPayment;
    }

    public String getWriteOffAmount() {
        return writeOffAmount;
    }

    public void setWriteOffAmount(String writeOffAmount) {
        this.writeOffAmount = writeOffAmount;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getSanctionAmount() {
        return sanctionAmount;
    }

    public void setSanctionAmount(String sanctionAmount) {
        this.sanctionAmount = sanctionAmount;
    }

    public String getLastPaymentDate() {
        return lastPaymentDate;
    }

    public void setLastPaymentDate(String lastPaymentDate) {
        this.lastPaymentDate = lastPaymentDate;
    }

    public String getHighCredit() {
        return highCredit;
    }

    public void setHighCredit(String highCredit) {
        this.highCredit = highCredit;
    }

    public String getDateReported() {
        return dateReported;
    }

    public void setDateReported(String dateReported) {
        this.dateReported = dateReported;
    }

    public String getDateOpened() {
        return dateOpened;
    }

    public void setDateOpened(String dateOpened) {
        this.dateOpened = dateOpened;
    }

    public String getDateClosed() {
        return dateClosed;
    }

    public void setDateClosed(String dateClosed) {
        this.dateClosed = dateClosed;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(String interestRate) {
        this.interestRate = interestRate;
    }

    public String getRepaymentTenure() {
        return repaymentTenure;
    }

    public void setRepaymentTenure(String repaymentTenure) {
        this.repaymentTenure = repaymentTenure;
    }

    public String getDisputeCode() {
        return disputeCode;
    }

    public void setDisputeCode(String disputeCode) {
        this.disputeCode = disputeCode;
    }

    public String getInstallmentAmount() {
        return installmentAmount;
    }

    public void setInstallmentAmount(String installmentAmount) {
        this.installmentAmount = installmentAmount;
    }

    public String getTermFrequency() {
        return termFrequency;
    }

    public void setTermFrequency(String termFrequency) {
        this.termFrequency = termFrequency;
    }

    public String getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(String creditLimit) {
        this.creditLimit = creditLimit;
    }

    public String getCollateralValue() {
        return collateralValue;
    }

    public void setCollateralValue(String collateralValue) {
        this.collateralValue = collateralValue;
    }

    public String getCollateralType() {
        return collateralType;
    }

    public void setCollateralType(String collateralType) {
        this.collateralType = collateralType;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getAssetClassification() {
        return assetClassification;
    }

    public void setAssetClassification(String assetClassification) {
        this.assetClassification = assetClassification;
    }

    public String getSuitFiledStatus() {
        return suitFiledStatus;
    }

    public void setSuitFiledStatus(String suitFiledStatus) {
        this.suitFiledStatus = suitFiledStatus;
    }

    public String getHistory48Months() {
        return history48Months;
    }

    public void setHistory48Months(String history48Months) {
        this.history48Months = history48Months;
    }
}
