package com.adpanshi.cashloan.business.core.enjoysign.beans;
/***
 *@category 丙方（居间服务人-杭州闪银网络技术有限公司-小额钱袋）
 *@author qing.yunhui
 *@email: qingyunhui@smoney.cc
 *@createTime: 2017年9月26日下午8:48:14
 **/
public class PartyC {

	 	private String signOrderNo ;//协议编号(订单号):
	    private String borMoney   ;//借款金额:
	    private String borMoneyCapital ;//借款金额(大写):
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
		private String servicePartySignatureDate ;//丙方签章日期(YYYY年MM月dd日)
		
		private String oneStagesDate;//第一期还款日
		private String oneStagesInterest;//第一期还款本息
		private String twoStagesDate;//第二期还款日
		private String twoStagesInterest;//第二期还款本息
		private String threeStagesDate;//第三期还款日
		private String threeStagesInterest;//三期还款本息
		private String fourStagesDate;//第四期还款日
		private String fourStagesInterest;//四期还款本息
		private String fiveStagesDate;//第五期还款日
		private String fiveStagesInterest;//第五期还款本息
		private String sixStagesDate;//第六期还款日
		private String sixStagesInterest;//六期还款本息
		private String sevenStagesDate;//第七期还款日
		private String sevenStagesInterest;//七期还款本息
		private String eightStagesDate;//第八期还款日
		private String eightStagesInterest;//第八期还款本息
		private String nineStagesDate;//第九期还款日
		private String nineStagesInterest;//九期还款本息
		private String tenStagesDate;//第十期还款日
		private String tenStagesInterest;//十期还款本息
		private String elevenStagesDate;//第十一期还款日
		private String elevenStagesInterest;//十一期还款本息
		private String twelveStagesDate;//第十二期还款日
		private String twelveStagesInterest;//十二期还款本息
		
		
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
		public String getBorMoneyCapital() {
			return borMoneyCapital;
		}
		public void setBorMoneyCapital(String borMoneyCapital) {
			this.borMoneyCapital = borMoneyCapital;
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
		public String getOneStagesDate() {
			return oneStagesDate;
		}
		public void setOneStagesDate(String oneStagesDate) {
			this.oneStagesDate = oneStagesDate;
		}
		public String getOneStagesInterest() {
			return oneStagesInterest;
		}
		public void setOneStagesInterest(String oneStagesInterest) {
			this.oneStagesInterest = oneStagesInterest;
		}
		public String getTwoStagesDate() {
			return twoStagesDate;
		}
		public void setTwoStagesDate(String twoStagesDate) {
			this.twoStagesDate = twoStagesDate;
		}
		public String getTwoStagesInterest() {
			return twoStagesInterest;
		}
		public void setTwoStagesInterest(String twoStagesInterest) {
			this.twoStagesInterest = twoStagesInterest;
		}
		public String getThreeStagesDate() {
			return threeStagesDate;
		}
		public void setThreeStagesDate(String threeStagesDate) {
			this.threeStagesDate = threeStagesDate;
		}
		public String getThreeStagesInterest() {
			return threeStagesInterest;
		}
		public void setThreeStagesInterest(String threeStagesInterest) {
			this.threeStagesInterest = threeStagesInterest;
		}
		public String getFourStagesDate() {
			return fourStagesDate;
		}
		public void setFourStagesDate(String fourStagesDate) {
			this.fourStagesDate = fourStagesDate;
		}
		public String getFourStagesInterest() {
			return fourStagesInterest;
		}
		public void setFourStagesInterest(String fourStagesInterest) {
			this.fourStagesInterest = fourStagesInterest;
		}
		public String getFiveStagesDate() {
			return fiveStagesDate;
		}
		public void setFiveStagesDate(String fiveStagesDate) {
			this.fiveStagesDate = fiveStagesDate;
		}
		public String getFiveStagesInterest() {
			return fiveStagesInterest;
		}
		public void setFiveStagesInterest(String fiveStagesInterest) {
			this.fiveStagesInterest = fiveStagesInterest;
		}
		public String getSixStagesDate() {
			return sixStagesDate;
		}
		public void setSixStagesDate(String sixStagesDate) {
			this.sixStagesDate = sixStagesDate;
		}
		public String getSixStagesInterest() {
			return sixStagesInterest;
		}
		public void setSixStagesInterest(String sixStagesInterest) {
			this.sixStagesInterest = sixStagesInterest;
		}
		public String getSevenStagesDate() {
			return sevenStagesDate;
		}
		public void setSevenStagesDate(String sevenStagesDate) {
			this.sevenStagesDate = sevenStagesDate;
		}
		public String getSevenStagesInterest() {
			return sevenStagesInterest;
		}
		public void setSevenStagesInterest(String sevenStagesInterest) {
			this.sevenStagesInterest = sevenStagesInterest;
		}
		public String getEightStagesDate() {
			return eightStagesDate;
		}
		public void setEightStagesDate(String eightStagesDate) {
			this.eightStagesDate = eightStagesDate;
		}
		public String getEightStagesInterest() {
			return eightStagesInterest;
		}
		public void setEightStagesInterest(String eightStagesInterest) {
			this.eightStagesInterest = eightStagesInterest;
		}
		public String getNineStagesDate() {
			return nineStagesDate;
		}
		public void setNineStagesDate(String nineStagesDate) {
			this.nineStagesDate = nineStagesDate;
		}
		public String getNineStagesInterest() {
			return nineStagesInterest;
		}
		public void setNineStagesInterest(String nineStagesInterest) {
			this.nineStagesInterest = nineStagesInterest;
		}
		public String getTenStagesDate() {
			return tenStagesDate;
		}
		public void setTenStagesDate(String tenStagesDate) {
			this.tenStagesDate = tenStagesDate;
		}
		public String getTenStagesInterest() {
			return tenStagesInterest;
		}
		public void setTenStagesInterest(String tenStagesInterest) {
			this.tenStagesInterest = tenStagesInterest;
		}
		public String getElevenStagesDate() {
			return elevenStagesDate;
		}
		public void setElevenStagesDate(String elevenStagesDate) {
			this.elevenStagesDate = elevenStagesDate;
		}
		public String getElevenStagesInterest() {
			return elevenStagesInterest;
		}
		public void setElevenStagesInterest(String elevenStagesInterest) {
			this.elevenStagesInterest = elevenStagesInterest;
		}
		public String getTwelveStagesDate() {
			return twelveStagesDate;
		}
		public void setTwelveStagesDate(String twelveStagesDate) {
			this.twelveStagesDate = twelveStagesDate;
		}
		public String getTwelveStagesInterest() {
			return twelveStagesInterest;
		}
		public void setTwelveStagesInterest(String twelveStagesInterest) {
			this.twelveStagesInterest = twelveStagesInterest;
		}
		public String getPenaltyRate() {
			return penaltyRate;
		}
		public void setPenaltyRate(String penaltyRate) {
			this.penaltyRate = penaltyRate;
		}
		public String getServicePartySignature() {
			return servicePartySignature;
		}
		public void setServicePartySignature(String servicePartySignature) {
			this.servicePartySignature = servicePartySignature;
		}
		public String getServicePartySignatureDate() {
			return servicePartySignatureDate;
		}
		public void setServicePartySignatureDate(String servicePartySignatureDate) {
			this.servicePartySignatureDate = servicePartySignatureDate;
		}

}
