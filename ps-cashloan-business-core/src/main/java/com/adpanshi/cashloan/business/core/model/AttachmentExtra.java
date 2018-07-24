package com.adpanshi.cashloan.business.core.model;
/***
 ** @category 附件信息...
 ** @author qing.yunhui
 ** @email: qingyunhui@smoney.cc
 ** @createTime: 2017年10月10日下午9:02:59
 **/
public class AttachmentExtra {
	
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
	
	public AttachmentExtra(){}
	
	public AttachmentExtra(String targetTable,String targetField){
		this.targetTable=targetTable;
		this.targetField=targetField;
	}
	
	public AttachmentExtra(String targetTable,String targetField,Long targetId){
		this(targetTable, targetField);
		this.targetId=targetId;
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
	
}
