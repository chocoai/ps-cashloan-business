package com.adpanshi.cashloan.business.rule.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @author qing.yunhui 
 * @Since 2011-2018
 * @create 2018-03-16 17:29:30
 * @history
 */
public class TanzhiRecord implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public TanzhiRecord(){}
	
	/**
	 * @param userId(saas会把userId作为resId返回)
	 * @param borrowMainId
	 * @param resId
	 * @param status
	 * */
	public TanzhiRecord(Long userId,Long borrowMainId,String resId,Integer status){
		this.userId=userId;
		this.borrowMainId=borrowMainId;
		this.resId=resId;
		this.status=status;
	}

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
	 * @Fields borrowMainId:订单id
	 */
	private Long borrowMainId;
	
	/**
	 * @Fields resId:流水id
	 */
	private String resId;
	
	/**
	 * @Fields code:响应码
	 */
	private String code;
	
	/**
	 * @Fields phoneNumber:账号手机号码
	 */
	private String phoneNumber;
	
	/**
	 * @Fields msg:响应描述
	 */
	private String msg;
	
	/**
	 * @Fields status:状态(0:作废、1:正常)
	 */
	private Integer status;
	
	/**
	 * @Fields reportInfo:报告基本信息
	 */
	private String reportInfo;
	
	/**
	 * @Fields basicInfo:运营商基本信息
	 */
	private String basicInfo;
	
	/**
	 * @Fields relationPhoneInfos:身份证匹配的其它手机号码
	 */
	private String relationPhoneInfos;
	
	/**
	 * @Fields section:近半年借贷历史
	 */
	private String section;
	
	/**
	 * @Fields eveSums:借贷事件数统计
	 */
	private String eveSums;
	
	/**
	 * @Fields platformInfos:借贷中涉及的平台数统计
	 */
	private String platformInfos;
	
	/**
	 * @Fields refInfos:参考统计变量
	 */
	private String refInfos;
	
	/**
	 * @Fields gmtCreateTime:创建时间
	 */
	private Date gmtCreateTime;
	
	/**
	 * @Fields gmtUpdateTime:修改时间
	 */
	private Date gmtUpdateTime;
	
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getBorrowMainId() {
		return borrowMainId;
	}

	public void setBorrowMainId(Long borrowMainId) {
		this.borrowMainId = borrowMainId;
	}

	public String getResId() {
		return resId;
	}

	public void setResId(String resId) {
		this.resId = resId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getReportInfo() {
		return reportInfo;
	}

	public void setReportInfo(String reportInfo) {
		this.reportInfo = reportInfo;
	}

	public String getBasicInfo() {
		return basicInfo;
	}

	public void setBasicInfo(String basicInfo) {
		this.basicInfo = basicInfo;
	}

	public String getRelationPhoneInfos() {
		return relationPhoneInfos;
	}

	public void setRelationPhoneInfos(String relationPhoneInfos) {
		this.relationPhoneInfos = relationPhoneInfos;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getEveSums() {
		return eveSums;
	}

	public void setEveSums(String eveSums) {
		this.eveSums = eveSums;
	}

	public String getPlatformInfos() {
		return platformInfos;
	}

	public void setPlatformInfos(String platformInfos) {
		this.platformInfos = platformInfos;
	}

	public String getRefInfos() {
		return refInfos;
	}

	public void setRefInfos(String refInfos) {
		this.refInfos = refInfos;
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

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}