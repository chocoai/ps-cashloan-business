package com.adpanshi.cashloan.business.rule.domain.equifaxReport.base;


import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.io.Serializable;
import java.util.List;

@XStreamAlias("sch:IdentityInfo")
public class IdentityInfo implements Serializable {
    //PAN卡信息
    @XStreamImplicit(itemFieldName="sch:PANId")
    private List<PANId> panIdInfo;
    //护照信息
    @XStreamImplicit(itemFieldName="sch:PassportID")
    private List<PassportID> passportIDInfo;
    //驾驶证信息
    @XStreamImplicit(itemFieldName="sch:DriverLicence")
    private List<DriverLicence> driverLicenceInfo;
    //选民证信息
    @XStreamImplicit(itemFieldName="sch:VoterID")
    private List<VoterID> voterIDInfo;
    //国民卡信息
    @XStreamImplicit(itemFieldName="sch:NationalIDCard")
    private List<NationalIDCard> nationalIDCardInfo;
    //配给卡信息
    @XStreamImplicit(itemFieldName="sch:RationCard")
    private List<RationCard> rationCardInfo;
    //证件信息
    @XStreamImplicit(itemFieldName="sch:IDCard")
    private List<IDCard> idCardInfo;
    //图片版权信息
    @XStreamImplicit(itemFieldName="sch:PhotoCreditCard")
    private List<PhotoCreditCard> photoCreditCardInfo;
    //其他证件信息
    @XStreamImplicit(itemFieldName="sch:IDOther")
    private List<IDOther> idOtherInfo;
    
    private String panIds;
    private String passportIDs;
    private String driverLicences;
    private String voterIDs;
    private String nationalIDCards;
    private String rationCards;
    private String idCards;
    private String photoCreditCards;
    private String idOthers;

    public List<PANId> getPanIdInfo() {
        return panIdInfo;
    }

    public void setPanIdInfo(List<PANId> panIdInfo) {
        this.panIdInfo = panIdInfo;
    }

    public List<PassportID> getPassportIDInfo() {
        return passportIDInfo;
    }

    public void setPassportIDInfo(List<PassportID> passportIDInfo) {
        this.passportIDInfo = passportIDInfo;
    }

    public List<DriverLicence> getDriverLicenceInfo() {
        return driverLicenceInfo;
    }

    public void setDriverLicenceInfo(List<DriverLicence> driverLicenceInfo) {
        this.driverLicenceInfo = driverLicenceInfo;
    }

    public List<VoterID> getVoterIDInfo() {
        return voterIDInfo;
    }

    public void setVoterIDInfo(List<VoterID> voterIDInfo) {
        this.voterIDInfo = voterIDInfo;
    }

    public List<NationalIDCard> getNationalIDCardInfo() {
        return nationalIDCardInfo;
    }

    public void setNationalIDCardInfo(List<NationalIDCard> nationalIDCardInfo) {
        this.nationalIDCardInfo = nationalIDCardInfo;
    }

    public List<RationCard> getRationCardInfo() {
        return rationCardInfo;
    }

    public void setRationCardInfo(List<RationCard> rationCardInfo) {
        this.rationCardInfo = rationCardInfo;
    }

    public List<IDCard> getIdCardInfo() {
        return idCardInfo;
    }

    public void setIdCardInfo(List<IDCard> idCardInfo) {
        this.idCardInfo = idCardInfo;
    }

    public List<PhotoCreditCard> getPhotoCreditCardInfo() {
        return photoCreditCardInfo;
    }

    public void setPhotoCreditCardInfo(List<PhotoCreditCard> photoCreditCardInfo) {
        this.photoCreditCardInfo = photoCreditCardInfo;
    }

    public List<IDOther> getIdOtherInfo() {
        return idOtherInfo;
    }

    public void setIdOtherInfo(List<IDOther> idOtherInfo) {
        this.idOtherInfo = idOtherInfo;
    }


    public String getPanIds() {
        String panIds ="";
        if(this.panIdInfo!=null&&this.panIdInfo.size()>0){
            for(int i=0;i<this.panIdInfo.size();i++){
                panIds += this.panIdInfo.get(i).getIdNumber()+",";
            }
            panIds = panIds.substring(0,panIds.length()-1);
        }
        return panIds;
    }

    public String getPassportIDs() {
        String passportIDs ="";
        if(this.passportIDInfo!=null&&this.passportIDInfo.size()>0){
            for(int i=0;i<this.passportIDInfo.size();i++){
                passportIDs += this.passportIDInfo.get(i).getIdNumber()+",";
            }
            passportIDs = passportIDs.substring(0,passportIDs.length()-1);
        }
        return passportIDs;
    }

    public String getDriverLicences() {
        String driverLicences ="";
        if(this.driverLicenceInfo!=null&&this.driverLicenceInfo.size()>0){
            for(int i=0;i<this.driverLicenceInfo.size();i++){
                driverLicences += this.driverLicenceInfo.get(i).getIdNumber()+",";
            }
            driverLicences = driverLicences.substring(0,driverLicences.length()-1);
        }
        return driverLicences;
    }

    public String getVoterIDs() {
        String voterIDs ="";
        if(this.voterIDInfo!=null&&this.voterIDInfo.size()>0){
            for(int i=0;i<this.voterIDInfo.size();i++){
                voterIDs += this.voterIDInfo.get(i).getIdNumber()+",";
            }
            voterIDs = voterIDs.substring(0,voterIDs.length()-1);
        }
        return voterIDs;
    }

    public String getNationalIDCards() {
        String nationalIDCards ="";
        if(this.nationalIDCardInfo!=null&&this.nationalIDCardInfo.size()>0){
            for(int i=0;i<this.nationalIDCardInfo.size();i++){
                nationalIDCards += this.nationalIDCardInfo.get(i).getIdNumber()+",";
            }
            nationalIDCards = nationalIDCards.substring(0,nationalIDCards.length()-1);
        }
        return nationalIDCards;
    }

    public String getRationCards() {
        String rationCards ="";
        if(this.rationCardInfo!=null&&this.rationCardInfo.size()>0){
            for(int i=0;i<this.rationCardInfo.size();i++){
                rationCards += this.rationCardInfo.get(i).getIdNumber()+",";
            }
            rationCards = rationCards.substring(0,rationCards.length()-1);
        }
        return rationCards;
    }

    public String getIdCards() {
        String idCards ="";
        if(this.idCardInfo!=null&&this.idCardInfo.size()>0){
            for(int i=0;i<this.idCardInfo.size();i++){
                idCards += this.idCardInfo.get(i).getIdNumber()+",";
            }
            idCards = idCards.substring(0,idCards.length()-1);
        }
        return idCards;
    }

    public String getPhotoCreditCards() {
        String photoCreditCards ="";
        if(this.passportIDInfo!=null&&this.passportIDInfo.size()>0){
            for(int i=0;i<this.passportIDInfo.size();i++){
                photoCreditCards += this.passportIDInfo.get(i).getIdNumber()+",";
            }
            photoCreditCards = photoCreditCards.substring(0,photoCreditCards.length()-1);
        }
        return photoCreditCards;
    }

    public String getIdOthers() {
        String idOthers ="";
        if(this.idOtherInfo!=null&&this.idOtherInfo.size()>0){
            for(int i=0;i<this.idOtherInfo.size();i++){
                idOthers += this.idOtherInfo.get(i).getIdNumber()+",";
            }
            idOthers = idOthers.substring(0,idOthers.length()-1);
        }
        return idOthers;
    }
}
