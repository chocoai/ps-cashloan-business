package com.adpanshi.cashloan.cl.model.stagin;

import com.adpanshi.cashloan.cl.domain.PayOrder;
import com.adpanshi.cashloan.cl.model.StaginCostInfoModel;

/***
 ** @category 分期付款数据集(用户主动发起付款)...
 ** @author qing.yunhui
 ** @email: 280672161@qq.com
 ** @createTime: 2018年1月2日下午3:26:55
 **/
public class StaginPaymentData {
	
	private String idNo;//付款人身份证号
    private String phone;//付款人手机号
    private double serviceAmount;//服务费 
    private double realAmount;//实还金额
    private String sign;//签名 
    private String cardNo;//银行卡号
    private String orderNo;//付款订单号 
    private String businessNo;// "201608101001022519", 
    private double interest;//利息
    private double totalAmount;//实还总金额
    private double penaltyAmout;//逾期费 
    private String realName;//还款人姓名
    private String notifyUrl;//异步通知地址 "http://192.168.0.253:6097/api/active/payment/asynNotify.htm", 
    private String registTime;//"20170907163945"
    private double earlyFee;//提前还款的手续费
    
    /**费用详情*/
    private StaginCostInfoModel costInfo;
    
    public StaginPaymentData(){}
    
    public StaginPaymentData(String idNo, String phone, double serviceAmount, double realAmount,
                             String cardNo, String orderNo, String businessNo, double interest,
                             double totalAmount, double penaltyAmout, String realName, String notifyUrl){
    		this.idNo=idNo;
    		this.phone=phone;
    		this.serviceAmount=serviceAmount;
    		this.realAmount=realAmount;
    		this.cardNo=cardNo;
    		this.orderNo=orderNo;
    		this.businessNo=businessNo;
    		this.interest=interest;
    		this.totalAmount=totalAmount;
    		this.penaltyAmout=penaltyAmout;
    		this.realName=realName;
    		this.notifyUrl=notifyUrl;
    }
    
    public StaginPaymentData(String idNo, String phone, double serviceAmount, double realAmount,
                             String sign, String cardNo, String orderNo, String businessNo, double interest,
                             double totalAmount, double penaltyAmout, String realName, String notifyUrl, String registTime){
    		this(idNo, phone, serviceAmount, realAmount, cardNo, orderNo, businessNo, interest, totalAmount, penaltyAmout, realName, notifyUrl);
    		this.sign=sign;
    		this.registTime=registTime;
    }
    
    /**支付订单*/
    private PayOrder payOrder;

    //-------------------------------> column end ------------------------------->
    
	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public double getServiceAmount() {
		return serviceAmount;
	}

	public void setServiceAmount(double serviceAmount) {
		this.serviceAmount = serviceAmount;
	}

	public double getRealAmount() {
		return realAmount;
	}

	public void setRealAmount(double realAmount) {
		this.realAmount = realAmount;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getBusinessNo() {
		return businessNo;
	}

	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}

	public double getInterest() {
		return interest;
	}

	public void setInterest(double interest) {
		this.interest = interest;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public double getPenaltyAmout() {
		return penaltyAmout;
	}

	public void setPenaltyAmout(double penaltyAmout) {
		this.penaltyAmout = penaltyAmout;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getRegistTime() {
		return registTime;
	}

	public void setRegistTime(String registTime) {
		this.registTime = registTime;
	}

	public PayOrder getPayOrder() {
		return payOrder;
	}

	public void setPayOrder(PayOrder payOrder) {
		this.payOrder = payOrder;
	}

	public double getEarlyFee() {
		return earlyFee;
	}

	public void setEarlyFee(double earlyFee) {
		this.earlyFee = earlyFee;
	}

	public StaginCostInfoModel getCostInfo() {
		return costInfo;
	}

	public void setCostInfo(StaginCostInfoModel costInfo) {
		this.costInfo = costInfo;
	}
	
}
