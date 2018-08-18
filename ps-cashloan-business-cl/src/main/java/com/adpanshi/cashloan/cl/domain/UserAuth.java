package com.adpanshi.cashloan.cl.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户认证实体
 *
 * @version 1.0.0
 * @date 2017-02-21 13:42:44
 * @author 8452
 */
 public class UserAuth implements Serializable {

    private static final long serialVersionUID = 1L;
    
    public UserAuth(){}
    
    /**
     * @param idState 身份证认证状态
     * @param zhimaState 芝麻授信状态
     * @param phoneState 手机运营商认证状态
     * @param contactState 紧急联系人认证状态
     * @param bankCardState 银行卡认证状态
     * @param bankCardBillState 银行卡账单认证状态
     * @param workInfoState 工作信息认证状态
     * @param otherInfoState 更多信息认证状态
     * @param livingIdentifyState 活体识别状态
     * @param tongdunState 同盾认证状态
     * @param tenementIncomeState 租房合同与收入证明状态
     * */
    public UserAuth(String idState, String zhimaState, String phoneState, String contactState, String bankCardState, String bankCardBillState, String workInfoState,
                    String otherInfoState, String livingIdentifyState, String tongdunState, String tenementIncomeState, String gongJiJinState, String sheBaoState){
    	this(idState, zhimaState, phoneState, contactState, bankCardState, workInfoState, otherInfoState, livingIdentifyState, tongdunState);
    	this.bankCardBillState=bankCardBillState;
    	this.tenementIncomeState=tenementIncomeState;
    	this.gongJiJinState=gongJiJinState;
    	this.sheBaoState=sheBaoState;
    }
    
    /**
     * @param idState 身份证认证状态
     * @param zhimaState 芝麻授信状态
     * @param phoneState 手机运营商认证状态
     * @param contactState 紧急联系人认证状态
     * @param bankCardState 银行卡认证状态
     * @param workInfoState 工作信息认证状态
     * @param otherInfoState 更多信息认证状态
     * @param livingIdentifyState 活体识别状态
     * @param tongdunState 同盾认证状态
     * */
    public UserAuth(String idState, String zhimaState, String phoneState, String contactState, String bankCardState, String workInfoState,
                    String otherInfoState, String livingIdentifyState, String tongdunState){
    	this.idState=idState;
    	this.zhimaState=zhimaState;
    	this.phoneState=phoneState;
    	this.contactState=contactState;
    	this.bankCardState=bankCardState;
    	this.workInfoState=workInfoState;
    	this.otherInfoState=otherInfoState;
    	this.livingIdentifyState=livingIdentifyState;
    	this.tongdunState=tongdunState;
    }

	/**
	 * 主键Id
	 */

	private Long id;

	/**
	 * 客户表 外键
	 */

	private Long userId;

	/**
	 * 身份认证状态
	 */

	private String idState;

	/**
	 * 紧急联系人状态
	 */

	private String contactState;

	/**
	 * 银行卡状态
	 */

	private String bankCardState;
	
	/**
	 * 银行卡账单认证(10未认证/未完善,20认证中/完善中,25认证失败,30已认证/已完善,40已过期)
	 * */
	private String bankCardBillState;

	/**
	 * 银行卡账单认证时间
	 * */
	private Date bankCardBillTime;
	
	/**
	 * 芝麻授信状态
	 */

	private String zhimaState;

	/**
	 * 手机运营商认证状态
	 */

	private String phoneState;

	/**
	 * 工作信息状态
	 */

	private String workInfoState;

	/**
	 * 更多信息状态
	 */
	private String otherInfoState;
	
	/**
	 * 活体识别认证状态
	 */
	private String livingIdentifyState;

	/**
	 * 同盾认证状态

	 */
	private String tongdunState;

	/**
	 * 同盾认证时间
	 */
	private Date tongdunStateTime;
	
	/**
	 * 租房收入状态(10未认证/未完善,20认证中/完善中,25认证失败,26已过期,30已认证/已完善)
	 * */
	private String tenementIncomeState;

	/**
	 * 租房收入认证最后时间
	 * */
	private String tenementIncomeTime;
	
	/**公积金认证状态*/
	private String gongJiJinState;
	
	/**公积金认证时间*/
	private Date gongJiJinTime; 

	/**社保认证状态*/
	private String sheBaoState;
	
	/**社保认证时间*/
	private Date sheBaoTime;
	
	/**
	 * 小额钱袋新流程标记，不会影响以前流程

	 */
	private Integer newVersion;
	/**
	 * pan卡认证状态
	 */
	private String panState;
	/**
	 * pan卡认证时间
	 */
	private Date panStateTime;

	/**
	 * 获取主键Id
	 * 
	 * @return id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 设置主键Id
	 * 
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 获取客户表外键
	 * 
	 * @return userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * 设置客户表外键
	 * 
	 * @param userId
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * 获取身份认证状态
	 * 
	 * @return idState
	 */
	public String getIdState() {
		return idState;
	}

	/**
	 * 设置身份认证状态
	 * 
	 * @param idState
	 */
	public void setIdState(String idState) {
		this.idState = idState;
	}

	/**
	 * 获取紧急联系人状态
	 * 
	 * @return contactState
	 */
	public String getContactState() {
		return contactState;
	}

	/**
	 * 设置紧急联系人状态
	 * 
	 * @param contactState
	 */
	public void setContactState(String contactState) {
		this.contactState = contactState;
	}

	/**
	 * 获取银行卡状态
	 * 
	 * @return bankCardState
	 */
	public String getBankCardState() {
		return bankCardState;
	}

	/**
	 * 设置银行卡状态
	 * 
	 * @param bankCardState
	 */
	public void setBankCardState(String bankCardState) {
		this.bankCardState = bankCardState;
	}

	/**
	 * 获取芝麻授信状态
	 * 
	 * @return zhimaState
	 */
	public String getZhimaState() {
		return zhimaState;
	}

	/**
	 * 设置芝麻授信状态
	 * 
	 * @param zhimaState
	 */
	public void setZhimaState(String zhimaState) {
		this.zhimaState = zhimaState;
	}

	/**
	 * 获取手机运营商认证状态
	 * 
	 * @return phoneState
	 */
	public String getPhoneState() {
		return phoneState;
	}

	/**
	 * 设置手机运营商认证状态
	 * 
	 * @param phoneState
	 */
	public void setPhoneState(String phoneState) {
		this.phoneState = phoneState;
	}

	/**
	 * 获取工作信息状态
	 * 
	 * @return workInfoState
	 */
	public String getWorkInfoState() {
		return workInfoState;
	}

	/**
	 * 设置工作信息状态
	 * 
	 * @param workInfoState
	 */
	public void setWorkInfoState(String workInfoState) {
		this.workInfoState = workInfoState;
	}

	/**
	 * 获取更多信息状态
	 * 
	 * @return otherInfoState
	 */
	public String getOtherInfoState() {
		return otherInfoState;
	}

	/**
	 * 设置更多信息状态
	 * 
	 * @param otherInfoState
	 */
	public void setOtherInfoState(String otherInfoState) {
		this.otherInfoState = otherInfoState;
	}
	
	/**
	 *  获得活体识别认证状态
	 * @return
	 */
	public String getLivingIdentifyState() {
		return livingIdentifyState;
	}

	/**
	 * 设置活体识别认证状态
	 * @param livingIdentifyState
	 */
	public void setLivingIdentifyState(String livingIdentifyState) {
		this.livingIdentifyState = livingIdentifyState;
	}

	public Integer getNewVersion() {
		return newVersion;
	}

	public void setNewVersion(Integer newVersion) {
		this.newVersion = newVersion;
	}

	public String getTongdunState() {
		return tongdunState;
	}

	public void setTongdunState(String tongdunState) {
		this.tongdunState = tongdunState;
	}

	public Date getTongdunStateTime() {
		return tongdunStateTime;
	}

	public void setTongdunStateTime(Date tongdunStateTime) {
		this.tongdunStateTime = tongdunStateTime;
	}

	public String getTenementIncomeState() {
		return tenementIncomeState;
	}

	public void setTenementIncomeState(String tenementIncomeState) {
		this.tenementIncomeState = tenementIncomeState;
	}

	public String getTenementIncomeTime() {
		return tenementIncomeTime;
	}

	public void setTenementIncomeTime(String tenementIncomeTime) {
		this.tenementIncomeTime = tenementIncomeTime;
	}

	public String getBankCardBillState() {
		return bankCardBillState;
	}

	public void setBankCardBillState(String bankCardBillState) {
		this.bankCardBillState = bankCardBillState;
	}
	
	public String getGongJiJinState() {
		return gongJiJinState;
	}

	public void setGongJiJinState(String gongJiJinState) {
		this.gongJiJinState = gongJiJinState;
	}

	public Date getGongJiJinTime() {
		return gongJiJinTime;
	}

	public void setGongJiJinTime(Date gongJiJinTime) {
		this.gongJiJinTime = gongJiJinTime;
	}

	public String getSheBaoState() {
		return sheBaoState;
	}

	public void setSheBaoState(String sheBaoState) {
		this.sheBaoState = sheBaoState;
	}

	public Date getSheBaoTime() {
		return sheBaoTime;
	}

	public void setSheBaoTime(Date sheBaoTime) {
		this.sheBaoTime = sheBaoTime;
	}

	public Date getBankCardBillTime() {
		return bankCardBillTime;
	}

	public void setBankCardBillTime(Date bankCardBillTime) {
		this.bankCardBillTime = bankCardBillTime;
	}

	public String getPanState() {
		return panState;
	}

	public void setPanState(String panState) {
		this.panState = panState;
	}

	public Date getPanStateTime() {
		return panStateTime;
	}

	public void setPanStateTime(Date panStateTime) {
		this.panStateTime = panStateTime;
	}
}