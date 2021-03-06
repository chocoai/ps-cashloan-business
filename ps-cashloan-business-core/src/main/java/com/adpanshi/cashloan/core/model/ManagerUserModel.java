package com.adpanshi.cashloan.core.model;

import com.adpanshi.cashloan.core.domain.UserBaseInfo;

import java.util.Date;

/**
 * @author 8452
 */
public class ManagerUserModel extends UserBaseInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * 登录名
	 */
	private String loginName;

	/**
	 * 登录密码
	 */
	private String loginPwd;

	/**
	 * 上次登录密码修改时间
	 */
	private Date loginpwdModifyTime;

	/**
	 * 注册时间
	 */
	private Date registTime;

	/**
	 * 注册客户端
	 */
	private String registerClient;

	/**
	 * 交易密码
	 */
	private String tradePwd;

	/**
	 * 上次交易密码修改时间
	 */
	private Date tradepwdModifyTime;

	/**
	* 
	*/
	private String uuid;

	/**
	 * 邀请码
	 */
	private String invitationCode;

	/**
	 * 代理等级
	 */
	private Integer level;

	/**
	 * 银行卡号
	 */
	private String cardNo;

	/**
	 * 开户行
	 */
	private String bank;

	/**
	  * 预留手机号
	 */
	 private String bankPhone;
	 
	 /**
	  * 注册渠道
	  * @return
	  */
	 private String channelName;
	 
	 /**
	  * 芝麻分
	  * @return
	  */
	 private String score;

	/**
	 * 用户状态: cl_user
	 * @return
	 */
	private int userState;

	/**
	 * 获取支行code
	 * @return
	 */
	public String getIfscCode() {
		return ifscCode;
	}

	/**
	 * 设置支行code
	 * @param ifscCode
	 */
	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

	/**
	 * 支行code
	 */
	private String ifscCode;

	/**
	 * 登录用户邮箱
	 */
	private String loginNameEmail;

	public String getLoginNameEmail() {
		return loginNameEmail;
	}

	public void setLoginNameEmail(String loginNameEmail) {
		this.loginNameEmail = loginNameEmail;
	}

	public String getBankPhone() {
		return bankPhone;
	}

	public void setBankPhone(String bankPhone) {
		this.bankPhone = bankPhone;
	}

	/**
	 * 获取登录名
	 * 
	 * @return 登录名
	 */
	public String getLoginName() {
		return loginName;
	}

	/**
	 * 设置登录名
	 * 
	 * @param loginName
	 *            要设置的登录名
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/**
	 * 获取登录密码
	 * 
	 * @return 登录密码
	 */
	public String getLoginPwd() {
		return loginPwd;
	}

	/**
	 * 设置登录密码
	 * 
	 * @param loginPwd
	 *            要设置的登录密码
	 */
	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}

	/**
	 * 获取上次登录密码修改时间
	 * 
	 * @return 上次登录密码修改时间
	 */
	public Date getLoginpwdModifyTime() {
		return loginpwdModifyTime;
	}

	/**
	 * 设置上次登录密码修改时间
	 * 
	 * @param loginpwdModifyTime
	 *            要设置的上次登录密码修改时间
	 */
	public void setLoginpwdModifyTime(Date loginpwdModifyTime) {
		this.loginpwdModifyTime = loginpwdModifyTime;
	}

	/**
	 * 获取注册时间
	 * 
	 * @return 注册时间
	 */
	public Date getRegistTime() {
		return registTime;
	}

	/**
	 * 设置注册时间
	 * 
	 * @param registTime
	 *            要设置的注册时间
	 */
	public void setRegistTime(Date registTime) {
		this.registTime = registTime;
	}

	/**
	 * 获取注册客户端
	 * 
	 * @return 注册客户端
	 */
	public String getRegisterClient() {
		return registerClient;
	}

	/**
	 * 设置注册客户端
	 * 
	 * @param registerClient
	 *            要设置的注册客户端
	 */
	public void setRegisterClient(String registerClient) {
		this.registerClient = registerClient;
	}

	/**
	 * 获取交易密码
	 * 
	 * @return 交易密码
	 */
	public String getTradePwd() {
		return tradePwd;
	}

	/**
	 * 设置交易密码
	 * 
	 * @param tradePwd
	 *            要设置的交易密码
	 */
	public void setTradePwd(String tradePwd) {
		this.tradePwd = tradePwd;
	}

	/**
	 * 获取上次交易密码修改时间
	 * 
	 * @return 上次交易密码修改时间
	 */
	public Date getTradepwdModifyTime() {
		return tradepwdModifyTime;
	}

	/**
	 * 设置上次交易密码修改时间
	 * 
	 * @param tradepwdModifyTime
	 *            要设置的上次交易密码修改时间
	 */
	public void setTradepwdModifyTime(Date tradepwdModifyTime) {
		this.tradepwdModifyTime = tradepwdModifyTime;
	}

	/**
	 * 获取
	 * 
	 * @return
	 */
	public String getUuid() {
		return uuid;
	}

	/**
	 * 设置
	 * 
	 * @param uuid
	 *            要设置的
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	/**
	 * 获取邀请码
	 * 
	 * @return 邀请码
	 */
	public String getInvitationCode() {
		return invitationCode;
	}

	/**
	 * 设置邀请码
	 * 
	 * @param invitationCode
	 *            要设置的邀请码
	 */
	public void setInvitationCode(String invitationCode) {
		this.invitationCode = invitationCode;
	}

	/**
	 * 获取
	 * 
	 * @return
	 */
	public Integer getLevel() {
		return level;
	}

	/**
	 * 设置
	 * 
	 * @param level
	 *            要设置的
	 */
	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	/**
	 * @return the channelName
	 */
	public String getChannelName() {
		return channelName;
	}

	/**
	 * @param channelName the channelName to set
	 */
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	/**
	 * @return the score
	 */
	public String getScore() {
		return score;
	}

	/**
	 * @param score the score to set
	 */
	public void setScore(String score) {
		this.score = score;
	}

	public int getUserState() {
		return userState;
	}

	public void setUserState(int userState) {
		this.userState = userState;
	}

}
