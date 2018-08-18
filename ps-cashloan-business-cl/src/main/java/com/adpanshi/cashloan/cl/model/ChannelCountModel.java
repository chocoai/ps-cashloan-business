package com.adpanshi.cashloan.cl.model;

/**
 * 渠道统计model
 *
 * @version 1.0.0
 * @date 2017-5-8 下午2:19:02 Copyright 粉团网路 金融创新事业部 cashloan All Rights
 * @author 8452
 */
public class ChannelCountModel {
	/**
	 * 统计时间---注册时间
	 */
	private String time;
	/**
	 * 渠道id
	 */
	private Long channelId;
	/**
	 * 渠道编码
	 */
	private String code;
	/**
	 * 渠道名称
	 */
	private String name;
	/**
	 * 注册人数
	 */
	private String registerCount;
	/**
	 * 借款人数
	 */
	private String borrowMember;
	/**
	 * 实名认证人数
	 */
	private String idMember;
	/**
	 * 银行卡绑定数
	 */
	private String bankCardMember;
	/**
	 * 电话运营商认证数
	 */
	private String phoneMember;
	/**
	 * 紧急联系人认证数
	 */
	private String contactMember;
	/**
	 * 芝麻授信数
	 */
	private String zhiMaMember;
	/**
	 * 放款人数
	 */
	private String loanMember;
	/**
	 * 老用户人数
	 */
	private String oldMember;
	/**
	 * 放款成功累计金额
	 */
	private String payAccount;

	public String getTime() { return time; }

	public void setTime(String time) { this.time = time; }

	public Long getChannelId() { return channelId; }

	public void setChannelId(Long channelId) { this.channelId = channelId; }

	public String getCode() { return code; }

	public void setCode(String code) { this.code = code; }

	public String getName() { return name; }

	public void setName(String name) { this.name = name; }

	public String getRegisterCount() { return registerCount; }

	public void setRegisterCount(String registerCount) { this.registerCount = registerCount; }

	public String getBorrowMember() { return borrowMember; }

	public void setBorrowMember(String borrowMember) { this.borrowMember = borrowMember; }

	public String getIdMember() { return idMember; }

	public void setIdMember(String idMember) { this.idMember = idMember; }

	public String getBankCardMember() { return bankCardMember; }

	public void setBankCardMember(String bankCardMember) { this.bankCardMember = bankCardMember; }

	public String getPhoneMember() { return phoneMember; }

	public void setPhoneMember(String phoneMember) { this.phoneMember = phoneMember; }

	public String getContactMember() { return contactMember; }

	public void setContactMember(String contactMember) { this.contactMember = contactMember; }

	public String getZhiMaMember() { return zhiMaMember; }

	public void setZhiMaMember(String zhiMaMember) { this.zhiMaMember = zhiMaMember; }

	public String getLoanMember() { return loanMember; }

	public void setLoanMember(String loanMember) { this.loanMember = loanMember; }

	public String getOldMember() { return oldMember; }

	public void setOldMember(String oldMember) { this.oldMember = oldMember; }

	public String getPayAccount() { return payAccount; }

	public void setPayAccount(String payAccount) { this.payAccount = payAccount; }
}
