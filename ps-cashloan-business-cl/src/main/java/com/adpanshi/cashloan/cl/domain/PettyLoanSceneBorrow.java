package com.adpanshi.cashloan.cl.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @author qing.yunhui 
 * @Since 2011-2017
 * @create 2017-12-06 09:52:47
 * @history
 */
public class PettyLoanSceneBorrow implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public PettyLoanSceneBorrow(){}
	
	public PettyLoanSceneBorrow(Long pettyLoanSceneId, Long borrowMainId, Long userId){
		this.pettyLoanSceneId=pettyLoanSceneId;
		this.borrowId=borrowMainId;
		this.userId=userId;
	}

	//columns START
	/**
	 * @Fields id:主键id
	 */
	private Long id;
	
	/**
	 * @Fields pettyLoanSceneId:小额贷场景id
	 */
	private Long pettyLoanSceneId;
	
	/**
	 * @Fields borrowId:订单id
	 */
	private Long borrowId;
	
	/**
	 * @Fields userId:用户id
	 */
	private Long userId;
	
	/**
	 * @Fields gmtCreateTime:创建时间
	 */
	private Date gmtCreateTime;
	
	/**
	 * @Fields gmtUpdateTimes:更新时间
	 */
	private Date gmtUpdateTimes;
	
	/**
	 * @Fields remarks:备注
	 */
	private String remarks;
	
	//columns END

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPettyLoanSceneId() {
		return pettyLoanSceneId;
	}

	public void setPettyLoanSceneId(Long pettyLoanSceneId) {
		this.pettyLoanSceneId = pettyLoanSceneId;
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

	public Date getGmtCreateTime() {
		return gmtCreateTime;
	}

	public void setGmtCreateTime(Date gmtCreateTime) {
		this.gmtCreateTime = gmtCreateTime;
	}

	public Date getGmtUpdateTimes() {
		return gmtUpdateTimes;
	}

	public void setGmtUpdateTimes(Date gmtUpdateTimes) {
		this.gmtUpdateTimes = gmtUpdateTimes;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}