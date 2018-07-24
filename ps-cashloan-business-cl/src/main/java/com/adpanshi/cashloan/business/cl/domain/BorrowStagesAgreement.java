package com.adpanshi.cashloan.business.cl.domain;

import java.io.Serializable;
import java.util.LinkedList;

/***
 ** @category 请用一句话来描述其用途...
 ** @author qing.yunhui
 ** @email: qingyunhui@fentuan360.com
 ** @createTime: 2018年2月9日下午1:59:09
 **/
public class BorrowStagesAgreement implements Serializable{

	private static final long serialVersionUID = 1L;
	
	//因为签章是在同一时间签署的，所以时间都一致
	private String signatureDate;//甲方签章日期(YYYY年MM月dd日)
	
	/*********** 甲方(借款方) ***********/
	private String borName;//借款人:
	private String borIDCard;//借款人身份证号:
	private String borPhone;//借款人手机号:
	private String borEmail;//借款人邮箱:
	private String borSignature;//甲签章(姓名):
	
	/*********** 乙方(出借方) ***********/
	private String lenderName;//出借人:
	private String lenderIDCard;//出借人身份证号:
	private String lenderPartySign;//乙方签名:
	
	/*********** 丙方(居间服务方) ***********/
	private String signOrderNo ;//协议编号(订单号):
    private String borMoney   ;//借款本金额:
    private String borDays  ;//借款期限(天):
    private String borStartDate  ;//借款开始日期(YYYY年MM月dd日)
	private String borEndDate ;//借款到期日期(YYYY年MM月dd日)
	private String borAnnualRate;//借款年利率
	private String borServiceFee;//借款人服务费 
	private String penaltyRate;//罚金利率
	private String borIntent;//借款意图
	private String totalStages;//总分期数
	private String stagesDays;//每期天数
	private String servicePartySignature ;//丙方签章(印章):
	
	private String contractUrl;

	private LinkedList<StagesDetail> stages;
	
	public String getSignatureDate() {
		return signatureDate;
	}
	public void setSignatureDate(String signatureDate) {
		this.signatureDate = signatureDate;
	}
	public String getBorName() {
		return borName;
	}
	public void setBorName(String borName) {
		this.borName = borName;
	}
	public String getBorIDCard() {
		return borIDCard;
	}
	public void setBorIDCard(String borIDCard) {
		this.borIDCard = borIDCard;
	}
	public String getBorPhone() {
		return borPhone;
	}
	public void setBorPhone(String borPhone) {
		this.borPhone = borPhone;
	}
	public String getBorEmail() {
		return borEmail;
	}
	public void setBorEmail(String borEmail) {
		this.borEmail = borEmail;
	}
	public String getBorSignature() {
		return borSignature;
	}
	public void setBorSignature(String borSignature) {
		this.borSignature = borSignature;
	}
	public String getLenderName() {
		return lenderName;
	}
	public void setLenderName(String lenderName) {
		this.lenderName = lenderName;
	}
	public String getLenderIDCard() {
		return lenderIDCard;
	}
	public void setLenderIDCard(String lenderIDCard) {
		this.lenderIDCard = lenderIDCard;
	}
	public String getLenderPartySign() {
		return lenderPartySign;
	}
	public void setLenderPartySign(String lenderPartySign) {
		this.lenderPartySign = lenderPartySign;
	}
	public String getSignOrderNo() {
		return signOrderNo;
	}
	public void setSignOrderNo(String signOrderNo) {
		this.signOrderNo = signOrderNo;
	}
	public String getBorMoney() {
		return borMoney;
	}
	public void setBorMoney(String borMoney) {
		this.borMoney = borMoney;
	}
	public String getBorDays() {
		return borDays;
	}
	public void setBorDays(String borDays) {
		this.borDays = borDays;
	}
	public String getBorStartDate() {
		return borStartDate;
	}
	public void setBorStartDate(String borStartDate) {
		this.borStartDate = borStartDate;
	}
	public String getBorEndDate() {
		return borEndDate;
	}
	public void setBorEndDate(String borEndDate) {
		this.borEndDate = borEndDate;
	}
	public String getBorAnnualRate() {
		return borAnnualRate;
	}
	public void setBorAnnualRate(String borAnnualRate) {
		this.borAnnualRate = borAnnualRate;
	}
	public String getBorServiceFee() {
		return borServiceFee;
	}
	public void setBorServiceFee(String borServiceFee) {
		this.borServiceFee = borServiceFee;
	}
	public String getPenaltyRate() {
		return penaltyRate;
	}
	public void setPenaltyRate(String penaltyRate) {
		this.penaltyRate = penaltyRate;
	}
	public String getBorIntent() {
		return borIntent;
	}
	public void setBorIntent(String borIntent) {
		this.borIntent = borIntent;
	}
	public String getTotalStages() {
		return totalStages;
	}
	public void setTotalStages(String totalStages) {
		this.totalStages = totalStages;
	}
	public String getStagesDays() {
		return stagesDays;
	}
	public void setStagesDays(String stagesDays) {
		this.stagesDays = stagesDays;
	}
	public String getServicePartySignature() {
		return servicePartySignature;
	}
	public void setServicePartySignature(String servicePartySignature) {
		this.servicePartySignature = servicePartySignature;
	}
	public LinkedList<StagesDetail> getStages() {
		return stages;
	}
	public void setStages(LinkedList<StagesDetail> stages) {
		this.stages = stages;
	}
	public String getContractUrl() {
		return contractUrl;
	}
	public void setContractUrl(String contractUrl) {
		this.contractUrl = contractUrl;
	}
	
}
