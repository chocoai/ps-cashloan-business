package com.adpanshi.cashloan.business.rule.domain.equifaxReport.base;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;

@XStreamAlias("sch:OtherKeyInd")
public class OtherKeyInd implements Serializable {
    @XStreamAlias("sch:AgeOfOldestTrade")
    private String ageOfOldestTrade;
    @XStreamAlias("sch:NumberOfOpenTrades")
    private String numberOfOpenTrades;
    @XStreamAlias("sch:AllLinesEVERWritten")
    private String allLinesEVERWritten;
    @XStreamAlias("sch:AllLinesEVERWrittenIn9Months")
    private String allLinesEVERWrittenIn9Months;
    @XStreamAlias("sch:AllLinesEVERWrittenIn6Months")
    private String allLinesEVERWrittenIn6Months;

    public String getAgeOfOldestTrade() {
        return ageOfOldestTrade;
    }

    public void setAgeOfOldestTrade(String ageOfOldestTrade) {
        this.ageOfOldestTrade = ageOfOldestTrade;
    }

    public String getNumberOfOpenTrades() {
        return numberOfOpenTrades;
    }

    public void setNumberOfOpenTrades(String numberOfOpenTrades) {
        this.numberOfOpenTrades = numberOfOpenTrades;
    }

    public String getAllLinesEVERWritten() {
        return allLinesEVERWritten;
    }

    public void setAllLinesEVERWritten(String allLinesEVERWritten) {
        this.allLinesEVERWritten = allLinesEVERWritten;
    }

    public String getAllLinesEVERWrittenIn9Months() {
        return allLinesEVERWrittenIn9Months;
    }

    public void setAllLinesEVERWrittenIn9Months(String allLinesEVERWrittenIn9Months) {
        this.allLinesEVERWrittenIn9Months = allLinesEVERWrittenIn9Months;
    }

    public String getAllLinesEVERWrittenIn6Months() {
        return allLinesEVERWrittenIn6Months;
    }

    public void setAllLinesEVERWrittenIn6Months(String allLinesEVERWrittenIn6Months) {
        this.allLinesEVERWrittenIn6Months = allLinesEVERWrittenIn6Months;
    }
}
