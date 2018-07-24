package com.adpanshi.cashloan.business.cl.model;

import com.adpanshi.cashloan.business.cl.domain.EnjoysignRecord;

import java.io.Serializable;

/**
 * @author qing.yunhui 
 * @Since 2011-2017
 * @create 2017-09-28 14:55:17
 * @history
 */
public class EnjoysignRecordModel extends EnjoysignRecord implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/**
	 * 文件保存路径
	 */
	private String filePath;

	/**
	 * 文件名称
	 */
	private String originName;

	/**
	 * 文件后缀
	 */
	private String suffix;

	/**
	 * 目标id(EnjoysignRecord : ID)
	 */
	private Long targetId;

	/**
	 * 分类
	 */
	private String classify;


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

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public Long getTargetId() {
		return targetId;
	}

	public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}

	public String getClassify() {
		return classify;
	}

	public void setClassify(String classify) {
		this.classify = classify;
	}
}