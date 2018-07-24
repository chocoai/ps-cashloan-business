package com.adpanshi.cashloan.business.core.model;

import org.springframework.web.multipart.MultipartFile;

/***
 ** @category 请用一句话来描述其用途...
 ** @author qing.yunhui
 ** @email: 280672161@qq.com
 ** @createTime: 2017年12月4日下午7:19:43
 **/
public class AttachmentMoreExtra extends AttachmentExtra{
	
	/**待上传的文件*/
	private MultipartFile[] files;
	
	/**文件落地目录*/
	private String downloadDir;
	
	/**分类*/
	private Integer classify;
	
	public AttachmentMoreExtra(){}
	
	public AttachmentMoreExtra(String targetTable,String targetField,MultipartFile[] files){
		super(targetTable, targetField);
		this.files=files;
	}
	
	public AttachmentMoreExtra(String targetTable,String targetField,MultipartFile[] files,String downloadDir){
		this(targetTable, targetField,files);
		this.downloadDir=downloadDir;
	}
	
	public AttachmentMoreExtra(String targetTable,String targetField,MultipartFile[] files,String downloadDir,Integer classify){
		this(targetTable, targetField,files,downloadDir);
		this.classify=classify;
	}
	
	public MultipartFile[] getFiles() {
		return files;
	}

	public void setFiles(MultipartFile[] files) {
		this.files = files;
	}

	public String getDownloadDir() {
		return downloadDir;
	}
	public void setDownloadDir(String downloadDir) {
		this.downloadDir = downloadDir;
	}

	public Integer getClassify() {
		return classify;
	}

	public void setClassify(Integer classify) {
		this.classify = classify;
	}
	
}
