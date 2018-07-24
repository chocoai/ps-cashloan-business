package com.adpanshi.cashloan.business.rule.domain.equifaxReport.base;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;

public class Name implements Serializable {
    @XStreamAlias("sch:FullName")
    private String fullName;
    @XStreamAlias("sch:FirstName")
    private String firstName;
    @XStreamAlias("sch:MiddleName")
    private String middleName;
    @XStreamAlias("sch:LastName")
    private String lastName;
    @XStreamAlias("sch:AdditionalMiddleName")
    private String additionalMiddleName;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAdditionalMiddleName() {
        return additionalMiddleName;
    }

    public void setAdditionalMiddleName(String additionalMiddleName) {
        this.additionalMiddleName = additionalMiddleName;
    }
}
