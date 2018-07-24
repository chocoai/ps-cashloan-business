package com.adpanshi.cashloan.business.rule.domain.equifaxReport.base;


import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;

public class PersonalInfo implements Serializable {
    @XStreamAlias("sch:Name")
    private Name name;
//    @XStreamAlias("sch:PreviousName")
//    private Name preName;
//    @XStreamAlias("sch:AliasNameInfo")
//    private Name aliasName;
    @XStreamAlias("sch:DateOfBirth")
    private String dateOfBirth;
    @XStreamAlias("sch:Gender")
    private String gender;
    @XStreamAlias("sch:Age")
    private AgeInfo ageInfo;
    @XStreamAlias("sch:TotalIncome")
    private String totalIncome;
    private String ages;

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public AgeInfo getAgeInfo() {
        return ageInfo;
    }

    public void setAgeInfo(AgeInfo ageInfo) {
        this.ageInfo = ageInfo;
    }

    public String getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(String totalIncome) {
        this.totalIncome = totalIncome;
    }

    public String getAges(){
        String age = "";
        if(this.ageInfo!=null&&this.ageInfo.getAges().length>0){
            for(int i=0;i<this.ageInfo.getAges().length;i++){
                age += this.ageInfo.getAges()[i]+",";
            }
            age = age.substring(0, age.length()-1);
        }
        return age;
    }
}
