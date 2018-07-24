package com.adpanshi.cashloan.business.cl.domain;

import java.io.Serializable;
import java.util.Date;

public class BorrowUserExamineLog implements Serializable{
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键
	 */
	private Long id ;
	/**
	 * 系统管理人员
	 */
	private Long sysUserId;
	/**
	 * 订单ID
	 */
	private Long borrowId;
	/**
	 * 订单号
	 */
	private String orderNo;
	/**
	 * 订单标识 0:新用户订单 1：老用户订单
	 */
	private String orderSign;
	/**
	 * 借款用户ID
	 */
	private Long userId;
	/**
	 * 状态:0:待处理，1:已处理',系统错误，订单分配失败
	 */
	private String status;
	/**
	 * 审核人ID
	 */
	private Long auditId;
	/**
	 * 审核人姓名
	 */
	private String auditName;
	/**
	 * 操作时间
	 */
	private Date  operationDate;
	/**
	 * 订单生成时间
	 */
	private Date createDate;
	/**
	 * 订单审核信息
	 */
	private String orderView;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 订单辅助状态
	 */
	private String orderStatus;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getSysUserId() {
		return sysUserId;
	}
	public void setSysUserId(Long sysUserId) {
		this.sysUserId = sysUserId;
	}
	public Long getBorrowId() {
		return borrowId;
	}
	public void setBorrowId(Long borrowId) {
		this.borrowId = borrowId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Date getOperationDate() {
		return operationDate;
	}
	public void setOperationDate(Date operationDate) {
		this.operationDate = operationDate;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getOrderSign() {
		return orderSign;
	}
	public void setOrderSign(String orderSign) {
		this.orderSign = orderSign;
	}
	public Long getAuditId() {
		return auditId;
	}
	public void setAuditId(Long auditId) {
		this.auditId = auditId;
	}
	public String getAuditName() {
		return auditName;
	}
	public void setAuditName(String auditName) {
		this.auditName = auditName;
	}
	public String getOrderView() {
		return orderView;
	}
	public void setOrderView(String orderView) {
		this.orderView = orderView;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
}
