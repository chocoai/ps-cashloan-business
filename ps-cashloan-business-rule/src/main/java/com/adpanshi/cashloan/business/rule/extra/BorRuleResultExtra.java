package com.adpanshi.cashloan.business.rule.extra;
/***
 ** @category 请用一句话来描述其用途...
 ** @author qing.yunhui
 ** @email: qingyunhui@fentuan360.com
 ** @createTime: 2018年3月17日下午12:45:27
 **/
public class BorRuleResultExtra {

	private String tbNid;
	private String tbName;
	private String colNid;
	private String colName;
	private String rule;
	
	public BorRuleResultExtra(){
		super();
	}
	
	/**
	 * @param tbNid 表英文名称
	 * @param tbName 表英中名称
	 * @param colNid 列名英文名称
	 * @param colName 列名中文名称
	 * @param rule 匹配规则表达式
	 * */
	public BorRuleResultExtra(String tbNid,String tbName,String colNid,String colName,String rule){
    	this.tbNid=tbNid;
    	this.tbName=tbName;
    	this.colNid=colNid;
    	this.colName=colName;
    	this.rule=rule;
    }

	public String getTbNid() {
		return tbNid;
	}

	public void setTbNid(String tbNid) {
		this.tbNid = tbNid;
	}

	public String getTbName() {
		return tbName;
	}

	public void setTbName(String tbName) {
		this.tbName = tbName;
	}

	public String getColNid() {
		return colNid;
	}

	public void setColNid(String colNid) {
		this.colNid = colNid;
	}

	public String getColName() {
		return colName;
	}

	public void setColName(String colName) {
		this.colName = colName;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}
	
}
