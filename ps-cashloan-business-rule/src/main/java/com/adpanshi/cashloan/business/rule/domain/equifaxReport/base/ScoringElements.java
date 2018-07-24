package com.adpanshi.cashloan.business.rule.domain.equifaxReport.base;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.io.Serializable;
import java.util.List;

@XStreamAlias("sch:ScoringElements")
public class ScoringElements implements Serializable {
    @XStreamImplicit(itemFieldName="sch:ScoringElement")
    private List<ScoringElement> scoringElementList;

    public List<ScoringElement> getScoringElementList() {
        return scoringElementList;
    }

    public void setScoringElementList(List<ScoringElement> scoringElementList) {
        this.scoringElementList = scoringElementList;
    }
}
