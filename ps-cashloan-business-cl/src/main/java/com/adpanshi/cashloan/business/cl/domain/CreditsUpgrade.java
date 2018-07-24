package com.adpanshi.cashloan.business.cl.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @category 用户额度提升表
 * @author qing.yunhui 
 * @Since 2011-2017
 * @create 2017-10-30 20:46:14
 * @history
 */
@SuppressWarnings("serial")
public class CreditsUpgrade implements Serializable{
	
	//columns START
	/**
	 * @Fields id:主键id
	 */
	private Long id;
	
	/**
	 * @Fields userId:用户id
	 */
	private Long userId;
	
	/**
	 * @Fields creditsLeve:额度等级(1.1级、2.2级、3.3级)
	 */
	private Integer creditsLeve;
	
	/**
	 * @Fields credits:额度(等级为1:1200、等级为2:1400、等级为3:1500)
	 */
	private Double credits;
	
	/**
	 * @Fields used:已使用额度
	 */
	private Double used;
	
	/**
	 * @Fields sendStatus:发送状态(1:待发送、2.发送失败、3.发送成功)
	 */
	private Integer sendStatus;
	
	/**
	 * @Fields status:状态(-1:非正常、1.正常)
	 */
	private Integer status;
	
	/**
	 * @Fields expiredTime:过期时间
	 */
	private Date expiredTime;
	
	/**
	 * @Fields gmtCreaterTime:创建时间
	 */
	private Date gmtCreaterTime;
	
	/**
	 * @Fields gmtUpdateTime:修改时间
	 */
	private Date gmtUpdateTime;
	
	/**
	 * @Fields remarks:备注(存储json串,记录每次变更且上一次记录也要保存)
	 */
	private String remarks;
	
	
	//columns END

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getCreditsLeve() {
		return creditsLeve;
	}

	public void setCreditsLeve(Integer creditsLeve) {
		this.creditsLeve = creditsLeve;
	}

	public Double getCredits() {
		return credits;
	}

	public void setCredits(Double credits) {
		this.credits = credits;
	}
	
	public Double getUsed() {
		return used;
	}

	public void setUsed(Double used) {
		this.used = used;
	}

	public Integer getSendStatus() {
		return sendStatus;
	}

	public void setSendStatus(Integer sendStatus) {
		this.sendStatus = sendStatus;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Date getExpiredTime() {
		return expiredTime;
	}

	public void setExpiredTime(Date expiredTime) {
		this.expiredTime = expiredTime;
	}

	public Date getGmtCreaterTime() {
		return gmtCreaterTime;
	}

	public void setGmtCreaterTime(Date gmtCreaterTime) {
		this.gmtCreaterTime = gmtCreaterTime;
	}

	public Date getGmtUpdateTime() {
		return gmtUpdateTime;
	}

	public void setGmtUpdateTime(Date gmtUpdateTime) {
		this.gmtUpdateTime = gmtUpdateTime;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}