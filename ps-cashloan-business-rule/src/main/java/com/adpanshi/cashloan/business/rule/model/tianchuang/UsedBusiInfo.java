package com.adpanshi.cashloan.business.rule.model.tianchuang;

/**
 * 在用业务
 * @author ppchen
 * 2017年8月18日 下午6:20:37
 * 
 */
public class UsedBusiInfo {
	private Long id;
	
	private String orderId;
	/**
	 * 业务名称
	 */
	private String busiName;
	/**
	 * 资费标准
	 */
	private String feeStandard;
	/**
	 * 订购时间，格式yyyy-MM-dd HH:mm:ss
	 */
	private String dealTime;
	/**
	 * ⽣效时间，格式yyyy-MM-dd HH:mm:ss
	 */
	private String efftime;
	/**
	 * 失效时间，格式yyyy-MM-dd HH:mm:ss
	 */
	private String expTime;
	/**
	 * 费用类型
	 */
	private String feeType;
	/**
	 * 描述
	 */
	private String desc;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getBusiName() {
		return busiName;
	}
	public void setBusiName(String busiName) {
		this.busiName = busiName;
	}
	public String getFeeStandard() {
		return feeStandard;
	}
	public void setFeeStandard(String feeStandard) {
		this.feeStandard = feeStandard;
	}
	public String getDealTime() {
		return dealTime;
	}
	public void setDealTime(String dealTime) {
		this.dealTime = dealTime;
	}
	public String getEfftime() {
		return efftime;
	}
	public void setEfftime(String efftime) {
		this.efftime = efftime;
	}
	public String getExpTime() {
		return expTime;
	}
	public void setExpTime(String expTime) {
		this.expTime = expTime;
	}
	public String getFeeType() {
		return feeType;
	}
	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
