package com.adpanshi.cashloan.core.model;

/***
 ** @category 分期记录...
 ** @author qing.yunhui
 ** @email: 280672161@qq.com
 ** @createTime: 2017年12月23日上午10:11:55
 **/
public class StagingRecordModel {

	/**主键id*/
	private Long borMainId;
	
	/**
	 * 订单号
	 * */
	private String borMainOrderNo;
	
	/**
	 * 分N期-(需要转换成明文)
	 * */
	private String byStages ;
	
	/**
	 * 申请时间
	 * */
	private String applyDate;
	
	/**
	 * 借款金额
	 * */
	private Double amount;
	
	
	/**
	 * 总金额(分期本金+利息+逾期+手续费)
	 * */
	private Double totalAmount;
	
	/**
     * 订单状态 10-审核中 12-临时状态_自动审核_初审审核成功，待活体（自动） 14-临时状态_自动审核未决待人工复审_待活体（人工） 20-自动审核成功 21自动审核不通过 22自动审核未决待人工复审 26人工复审通过
     * 27人工复审不通过 30-待还款 31放款失败 40-已还款 50逾期 90坏账
     */
    private String state;
    
    /**
     * 每期还款金款(每期本金+利息:需要根据模板或者查询子订单)
     * */
    private Double stagesAmount;
    
    /**
     * 总利息(每期利息总和-borrowMain取即可)
     * */
    private Double interest;
    
    /**
     * 总逾期(每期逾期总和)
     * */
    private Double penaltyAmout;
    
    /**
     * 手续费(提前还款)
     * */
    private Double fee;
    
    /**
     * 模板
     * */
    private String templateInfo;
    
    public StagingRecordModel(){}
    
    public StagingRecordModel(String byStages, String applyDate, Double amount, String state, Double stagesAmount, Double interest){
    	this.byStages=byStages;
    	this.applyDate=applyDate;
    	this.amount=amount;
    	this.state=state;
    	this.stagesAmount=stagesAmount;
    	this.interest=interest;
    }
    
    public StagingRecordModel(String byStages, String applyDate, Double amount, String state, Double stagesAmount, Double interest, Double penaltyAmout, Double fee){
    	this(byStages, applyDate, amount, state, stagesAmount, interest);
    	this.penaltyAmout=penaltyAmout;
    	this.fee=fee;
    }
    
    // column end
    

	public String getByStages() {
		return byStages;
	}

	public Long getBorMainId() {
		return borMainId;
	}

	public void setBorMainId(Long borMainId) {
		this.borMainId = borMainId;
	}

	public String getBorMainOrderNo() {
		return borMainOrderNo;
	}

	public void setBorMainOrderNo(String borMainOrderNo) {
		this.borMainOrderNo = borMainOrderNo;
	}

	public void setByStages(String byStages) {
		this.byStages = byStages;
	}

	public String getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Double getStagesAmount() {
		return stagesAmount;
	}

	public void setStagesAmount(Double stagesAmount) {
		this.stagesAmount = stagesAmount;
	}

	public Double getInterest() {
		return interest;
	}

	public void setInterest(Double interest) {
		this.interest = interest;
	}

	public Double getPenaltyAmout() {
		return penaltyAmout;
	}

	public void setPenaltyAmout(Double penaltyAmout) {
		this.penaltyAmout = penaltyAmout;
	}

	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}

	public String getTemplateInfo() {
		return templateInfo;
	}

	public void setTemplateInfo(String templateInfo) {
		this.templateInfo = templateInfo;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
    
}
