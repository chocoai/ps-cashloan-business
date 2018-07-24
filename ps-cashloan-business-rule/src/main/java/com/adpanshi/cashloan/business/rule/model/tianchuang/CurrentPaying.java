package com.adpanshi.cashloan.business.rule.model.tianchuang;

/**
 * 实时话费
 * @author ppchen
 * 2017年8月18日 下午12:01:32
 * 
 */
public class CurrentPaying {
	
	private Long id;
	
	private String orderId;
	
	private Long userId;

	/**
	 * 手机号
	 */
	private String userName;
	/**
	 * 实时话费（本⽉截⽌到⽬前的花费），⼩数点后两位精确到分，如 10.00
	 */
	private String timeFee;
	/**
	 * 账号余额，⼩数点后两位精确到分，如 10.00 含义
	 */
	private String accountRemaining;
	/**
	 * 当前状态，例如：开通、在⽤
	 */
	private String currentStatus;
	/**
	 * 付费类型 ，例如：后付费
	 */
	private String feeType;
	/**
	 * 入网日期，yyyy-MM-dd HH:mm:ss
	 */
	private String admissionDate;
	/**
	 * 所属品牌 ：例如：沃4G后付费 、全球通、神州⾏、动感地带
	 */
	private String belongBrad;
	/**
	 * 通话级别，如：国内通话
	 */
	private String callingLevel;
	/**
	 * 漫游级别，如：国内漫游
	 */
	private String internationalCallingLevel;
	/**
	 * 套餐名称
	 */
	private String packageName;
	/**
	 * 信用额度
	 */
	private String creditAccount;
	
	/**
	 * 网龄
	 */
	private int netAge;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getTimeFee() {
		return timeFee;
	}
	public void setTimeFee(String timeFee) {
		this.timeFee = timeFee;
	}
	public String getAccountRemaining() {
		return accountRemaining;
	}
	public void setAccountRemaining(String accountRemaining) {
		this.accountRemaining = accountRemaining;
	}
	public String getCurrentStatus() {
		return currentStatus;
	}
	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}
	public String getFeeType() {
		return feeType;
	}
	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}
	public String getAdmissionDate() {
		return admissionDate;
	}
	public void setAdmissionDate(String admissionDate) {
		this.admissionDate = admissionDate;
	}
	public String getBelongBrad() {
		return belongBrad;
	}
	public void setBelongBrad(String belongBrad) {
		this.belongBrad = belongBrad;
	}
	public String getCallingLevel() {
		return callingLevel;
	}
	public void setCallingLevel(String callingLevel) {
		this.callingLevel = callingLevel;
	}
	public String getInternationalCallingLevel() {
		return internationalCallingLevel;
	}
	public void setInternationalCallingLevel(String internationalCallingLevel) {
		this.internationalCallingLevel = internationalCallingLevel;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getCreditAccount() {
		return creditAccount;
	}
	public void setCreditAccount(String creditAccount) {
		this.creditAccount = creditAccount;
	}
	public int getNetAge() {
		return netAge;
	}
	public void setNetAge(int netAge) {
		this.netAge = netAge;
	}
}
