package com.adpanshi.cashloan.system.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * <P>附件</p>
 * @author qing.yunhui 
 * @Since 2011-2017
 * @create 2017-09-30 11:52:05
 * @history
 */
public class SysAttachment implements Serializable{
	
	private static final long serialVersionUID = 1L;

	//columns START
	/**
	 * @Fields id:主键id
	 */
	private Long id;
	
	/**
	 * @Fields targetTable:目标表
	 */
	private String targetTable;
	
	/**
	 * @Fields targetField:目标字段
	 */
	private String targetField;
	
	/**
	 * @Fields targetId:目标主键id
	 */
	private Long targetId;
	
	/**
	 * @Fields attachmentType:附件类型(如:pdf、png、jpg、jpng、)
	 */
	private String attachmentType;
	
	/**
	 * @Fields classify:分类(1:1号签,2.)
	 */
	private Integer classify;
	
	/**
	 * @Fields subClassify:子分类(开头以classify开头)
	 */
	private Integer subClassify;
	
	/**
	 * @Fields filePath:文件路径
	 */
	private String filePath;
	
	/**
	 * @Fields originName:文件名
	 */
	private String originName;
	
	/**
	 * @Fields size:文件大小
	 */
	private Long size;
	
	/**
	 * @Fields status:状态(0:非正常、1:正常)
	 */
	private Integer status;
	
	/**
	 * @Fields suffix:文件后缀
	 */
	private String suffix;
	
	/**
	 * @Fields gmtCreaterTime:创建时间
	 */
	private Date gmtCreaterTime;
	
	/**
	 * @Fields gmtUpdateTime:修改时间
	 */
	private Date gmtUpdateTime;
	
	/**
	 * @Fields remarks:备注
	 */
	private String remarks;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTargetTable() {
		return targetTable;
	}

	public void setTargetTable(String targetTable) {
		this.targetTable = targetTable;
	}

	public String getTargetField() {
		return targetField;
	}

	public void setTargetField(String targetField) {
		this.targetField = targetField;
	}

	public Long getTargetId() {
		return targetId;
	}

	public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}

	public String getAttachmentType() {
		return attachmentType;
	}

	public void setAttachmentType(String attachmentType) {
		this.attachmentType = attachmentType;
	}

	public Integer getClassify() {
		return classify;
	}

	public void setClassify(Integer classify) {
		this.classify = classify;
	}
	
	public Integer getSubClassify() {
		return subClassify;
	}

	public void setSubClassify(Integer subClassify) {
		this.subClassify = subClassify;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getOriginName() {
		return originName;
	}

	public void setOriginName(String originName) {
		this.originName = originName;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
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
	
	//columns END

}