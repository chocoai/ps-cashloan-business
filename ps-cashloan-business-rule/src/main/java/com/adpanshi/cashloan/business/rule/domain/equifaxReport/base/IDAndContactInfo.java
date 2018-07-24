package com.adpanshi.cashloan.business.rule.domain.equifaxReport.base;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.io.Serializable;
import java.util.List;

@XStreamAlias("sch:IDAndContactInfo")
public class IDAndContactInfo implements Serializable {
    //身份信息
    @XStreamAlias("sch:PersonalInfo")
    private PersonalInfo personalInfo;
    //证件信息
    @XStreamAlias("sch:IdentityInfo")
    private  IdentityInfo identityInfo;
//    @XStreamAlias("sch:AddressInfo")
    //地址信息
    @XStreamImplicit(itemFieldName="sch:AddressInfo")
    private List<AddressInfo> addressInfo;
//    @XStreamAlias("sch:PhoneInfo")
    //电话信息
    @XStreamImplicit(itemFieldName="sch:PhoneInfo")
    private List<PhoneInfo> phoneInfo;
    //邮箱信息
    @XStreamImplicit(itemFieldName="sch:EmailAddressInfo")
    private List<EmailAddressInfo> emailAddressInfo;
    private String phones;
    private String addresses;
    private String emailAddresses;

    public PersonalInfo getPersonalInfo() {
        return personalInfo;
    }

    public void setPersonalInfo(PersonalInfo personalInfo) {
        this.personalInfo = personalInfo;
    }

    public IdentityInfo getIdentityInfo() {
        return identityInfo;
    }

    public void setIdentityInfo(IdentityInfo identityInfo) {
        this.identityInfo = identityInfo;
    }

    public List<AddressInfo> getAddressInfo() {
        return addressInfo;
    }

    public void setAddressInfo(List<AddressInfo> addressInfo) {
        this.addressInfo = addressInfo;
    }

    public List<PhoneInfo> getPhoneInfo() {
        return phoneInfo;
    }

    public void setPhoneInfo(List<PhoneInfo> phoneInfo) {
        this.phoneInfo = phoneInfo;
    }

    public List<EmailAddressInfo> getEmailAddressInfo() {
        return emailAddressInfo;
    }

    public void setEmailAddressInfo(List<EmailAddressInfo> emailAddressInfo) {
        this.emailAddressInfo = emailAddressInfo;
    }
    public String getPhones(){
        String phones ="";
        if(this.phoneInfo!=null&&this.phoneInfo.size()>0){
            for(int i=0;i<this.phoneInfo.size();i++){
                phones += this.phoneInfo.get(i).getNumber()+",";
            }
            phones = phones.substring(0,phones.length()-1);
        }
        return phones;
    }
    public String getAddresses(){
        String addresses ="";
        if(this.addressInfo!=null&&this.addressInfo.size()>0){
            for(int i=0;i<this.addressInfo.size();i++){
                addresses += this.addressInfo.get(i).getAddress()+",";
            }
            addresses = addresses.substring(0,addresses.length()-1);
        }
        return addresses;
    }
    public String getEmailAddresses(){
        String emailAddresses ="";
        if(this.emailAddressInfo!=null&&this.emailAddressInfo.size()>0){
            for(int i=0;i<this.emailAddressInfo.size();i++){
                emailAddresses += this.emailAddressInfo.get(i).getEmailAddress()+",";
            }
            emailAddresses = emailAddresses.substring(0,emailAddresses.length()-1);
        }
        return emailAddresses;
    }
}
