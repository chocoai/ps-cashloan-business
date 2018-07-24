package com.adpanshi.cashloan.business.cl.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @author qing.yunhui 
 * @Since 2011-2017
 * @create 2017-09-28 14:55:17
 * @history
 */
public class EnjoysignDownloadLog implements Serializable{
	
	private static final long serialVersionUID = 1L;

	//columns START
	/**
	 * @Fields id:主键id
	 */
	private Long id;
	
	/**
	 * @Fields borId:借款人id
	 */
	private Long borId;

	/**
	 * @Field email
	 */
	private String email;
	
	/**
	 * @Fields resParameter:响应参数
	 */
	private String resParameter;
	
	/**
	 * @Fields downloadType:下载类型 1.远程服务下载、2.本地服务下载
	 */
	private Integer downloadType;
	
	/**
	 * @Fields status:状态 40:下载失败、50:下载成功'
	 */
	private Integer status;
	
	/**
	 * @Fields gmtCreateTime:创建时间
	 */
	private Date gmtCreateTime;
	
	/**
	 * @Fields gmtUpdateTime:修改时间
	 */
	private Date gmtUpdateTime;

	/**
	 * @Fields remark:说明
	 */
	private String remark;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBorId() {
		return borId;
	}

	public void setBorId(Long borId) {
		this.borId = borId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getResParameter() {
		return resParameter;
	}

	public void setResParameter(String resParameter) {
		this.resParameter = resParameter;
	}

	public Integer getDownloadType() {
		return downloadType;
	}

	public void setDownloadType(Integer downloadType) {
		this.downloadType = downloadType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getGmtCreateTime() {
		return gmtCreateTime;
	}

	public void setGmtCreateTime(Date gmtCreateTime) {
		this.gmtCreateTime = gmtCreateTime;
	}

	public Date getGmtUpdateTime() {
		return gmtUpdateTime;
	}

	public void setGmtUpdateTime(Date gmtUpdateTime) {
		this.gmtUpdateTime = gmtUpdateTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}