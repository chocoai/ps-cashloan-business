package com.adpanshi.cashloan.business.cl.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 信审用户管理表
 * @author chengyinghao
 *
 */
public class UserExamine implements Serializable{
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 主键Id
	 */
	private Long id;
	/**
	 * 信审人Id
	 */
	private Long userId;
	/**
	 * 信审人姓名
	 */
	private String userName;
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 管理者ID
	 */
	private Long manageId;
	/**
	 * 管理者姓名
	 */
	private String manageName;
	/**
	 * 审批记录
	 */
	private String examineRecord;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;
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
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getManageId() {
		return manageId;
	}
	public void setManageId(Long manageId) {
		this.manageId = manageId;
	}
	public String getManageName() {
		return manageName;
	}
	public void setManageName(String manageName) {
		this.manageName = manageName;
	}
	public String getExamineRecord() {
		return examineRecord;
	}
	public void setExamineRecord(String examineRecord) {
		this.examineRecord = examineRecord;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
