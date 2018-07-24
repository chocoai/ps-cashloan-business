package com.adpanshi.cashloan.business.cl.model;

import java.io.Serializable;

/***
 ** @category 分期进度...
 ** @author qing.yunhui
 ** @email: 280672161@qq.com
 ** @createTime: 2017年12月24日下午3:50:58
 **/
public class ProgressModel implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public ProgressModel(){}
	
	/**用户id*/
	private Long userId;
	/**订单id*/
	private Long borrowId;
	/**状态*/
	private String state; 
	/*** 阶段名称* */
	private String stageName;
	/**描述*/
	private String remark;
	/**生成时间*/
	private String createTime;
	
	/**
	 * @param stageName 阶段名称
	 * @param createTime 生成时间
	 * */
	public ProgressModel(String stageName,String createTime){
		this.remark=stageName;
		this.createTime=createTime;
	}
	
	public ProgressModel(Long userId,Long borrowId,String state,String remark,String createTime){
		this(state, remark, createTime);
		this.userId=userId;
		this.borrowId=borrowId;
	}
	
	public ProgressModel(String state,String remark,String createTime){
		this.state=state;
		this.remark=remark;
		this.createTime=createTime;
	}
	
	public ProgressModel(String state,String remark,String createTime,String stageName){
		this(state, remark, createTime);
		this.stageName=stageName;
	}
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getBorrowId() {
		return borrowId;
	}
	public void setBorrowId(Long borrowId) {
		this.borrowId = borrowId;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getStageName() {
		return stageName;
	}

	public void setStageName(String stageName) {
		this.stageName = stageName;
	}
}
