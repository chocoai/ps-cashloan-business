package com.adpanshi.cashloan.business.core.model;

public class AuthUserModel {
	/** 用户id */
	private String userId;
	/** 真实姓名 */
	private String realName;
	/** 手机号码 */
	private String phone;
	/** 银行卡状态 */
	private String bankCardState;
	/** 身份证状态 */
	private String idState;
	/** 紧急联系人状态 */
	private String contactState;
	/** 唯一标识符 */
	private String id;
	/** 手机运营商认证状态 */
	private String phoneState;
	/** 工作信息状态 */
	private String workInfoState;
	/** 活体识别认证状态 */
	private String livingIdentifyState;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getBankCardState() {
		return bankCardState;
	}

	public void setBankCardState(String bankCardState) {
		this.bankCardState = bankCardState;
	}

	public String getIdState() {
		return idState;
	}

	public void setIdState(String idState) {
		this.idState = idState;
	}

	public String getContactState() {
		return contactState;
	}

	public void setContactState(String contactState) {
		this.contactState = contactState;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPhoneState() {
		return phoneState;
	}

	public void setPhoneState(String phoneState) {
		this.phoneState = phoneState;
	}

	public String getWorkInfoState() {
		return workInfoState;
	}

	public void setWorkInfoState(String workInfoState) {
		this.workInfoState = workInfoState;
	}

	public String getLivingIdentifyState() {
		return livingIdentifyState;
	}

	public void setLivingIdentifyState(String livingIdentifyState) {
		this.livingIdentifyState = livingIdentifyState;
	}
}