package com.adpanshi.cashloan.business.rule.model.tianchuang;

/**
 * 
 * 风控结果详细信息
 * @author ppchen
 * 2017年9月1日 下午4:34:13
 * 
 */
public class RiskManageRespDetail {
	
	private String id;
	
	/**
	 * 外部单号
	 */
	private String borrowId;
	
	/**
	 * 风控结果 详细
	 */
	private String riskManageResult;
	
	/**
	 * 风控结果
	 */
	private String rsState;
	
	private Integer rsScore;
	
	private Long userId;
	
	private String createTime;

	private String resultCode;

	public RiskManageRespDetail(String borrowId, Long userId, String riskManageResult, String rsState, int score, String resultCode) {
		super();
		this.borrowId = borrowId;
		this.userId = userId;
		this.riskManageResult = riskManageResult;
		this.rsState = rsState;
		this.rsScore = score;
		this.resultCode=resultCode;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBorrowId() {
		return borrowId;
	}

	public void setBorrowId(String borrowId) {
		this.borrowId = borrowId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getRiskManageResult() {
		return riskManageResult;
	}

	public void setRiskManageResult(String riskManageResult) {
		this.riskManageResult = riskManageResult;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getRsState() {
		return rsState;
	}

	public void setRsState(String rsState) {
		this.rsState = rsState;
	}

	public Integer getRsScore() {
		return rsScore;
	}

	public void setRsScore(Integer rsScore) {
		this.rsScore = rsScore;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
}
