package com.adpanshi.cashloan.business.rule.model.tianchuang;

/**
 * 流量记录
 * @author ppchen
 * 2017年8月18日 下午4:33:04
 * 
 */
public class InternetTraffic {

	
	private Long id;
	
	private String orderId;
	/**
	 * 总流量（KB）
	 */
	private String internetTrafficWithinPackage;
	/**
	 * 流量描述
	 */
	private String flowDic;
	/**
	 * 起始时间
	 */
	private String beginTime;
	/**
	 * 套餐优惠
	 */
	private String packageDic;
	/**
	 * 上⽹时长（s）
	 */
	private String netLongTime;
	/**
	 * 计费类型
	 */
	private String svcName;
	/**
	 * 通信费
	 */
	private String totalFee;
	/**
	 * 上⽹⽅式
	 */
	private String netGoType;
	/**
	 * 网络类型
	 */
	private String netTypeName;
	/**
	 * 通信地点
	 */
	private String homeAreaName;
	/**
	 * 是否定向
	 */
	private String extParaName;
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
	public String getInternetTrafficWithinPackage() {
		return internetTrafficWithinPackage;
	}
	public void setInternetTrafficWithinPackage(String internetTrafficWithinPackage) {
		this.internetTrafficWithinPackage = internetTrafficWithinPackage;
	}
	public String getFlowDic() {
		return flowDic;
	}
	public void setFlowDic(String flowDic) {
		this.flowDic = flowDic;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getPackageDic() {
		return packageDic;
	}
	public void setPackageDic(String packageDic) {
		this.packageDic = packageDic;
	}
	public String getNetLongTime() {
		return netLongTime;
	}
	public void setNetLongTime(String netLongTime) {
		this.netLongTime = netLongTime;
	}
	public String getSvcName() {
		return svcName;
	}
	public void setSvcName(String svcName) {
		this.svcName = svcName;
	}
	public String getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}
	public String getNetGoType() {
		return netGoType;
	}
	public void setNetGoType(String netGoType) {
		this.netGoType = netGoType;
	}
	public String getNetTypeName() {
		return netTypeName;
	}
	public void setNetTypeName(String netTypeName) {
		this.netTypeName = netTypeName;
	}
	public String getHomeAreaName() {
		return homeAreaName;
	}
	public void setHomeAreaName(String homeAreaName) {
		this.homeAreaName = homeAreaName;
	}
	public String getExtParaName() {
		return extParaName;
	}
	public void setExtParaName(String extParaName) {
		this.extParaName = extParaName;
	}
}
