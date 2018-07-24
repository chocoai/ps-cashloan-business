package com.adpanshi.cashloan.business.rule.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @category 新颜黑名单
 * @author qing.yunhui 
 * @Since 2011-2017
 * @create 2017-10-21 16:33:33
 * @history
 */
public class XinyanBlacklist implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/**
	 * @Fields id:自增id
	 */
	private Long id;
	
	/**
	 * @Fields id_no:身份证号
	 */
	private String idNo;
	
	/**
	 * @Fields idName:姓名
	 */
	private String idName;
	
	/**
	 * @Fields type:类型: 1.身份证
	 */
	private Integer type;
	
	/**
	 * @Fields transId:请求订单号
	 */
	private String transId;
	
	/**
	 * @Fields trade_no:新颜响应订单号
	 */
	private String tradeNo;
	
	/**
	 * @Fields desc:结果描述
	 */
	private String descs;
	
	/**
	 * @Fields maxOverdueDays:最长逾期天数(区间值)
	 */
	private String maxOverdueDays;
	
	/**
	 * @Fields latestOverdueTime:最近逾期时间格式：yyyy-MM
	 */
	private String latestOverdueTime;
	
	/**
	 * @Fields maxOverdueAmt:最大逾期金额(区间值)
	 */
	private String maxOverdueAmt;
	
	/**
	 * @Fields currentlyOverdue:当前逾期
	 */
	private String currentlyOverdue;
	
	/**
	 * @Fields currentlyPerformance:当前履约
	 */
	private String currentlyPerformance;
	
	/**
	 * @Fields accExc:账户异常
	 */
	private String accExc;
	
	/**
	 * @Fields accSleep:睡眠账户
	 */
	private String accSleep;
	
	/**
	 * @Fields updateTime:修改时间
	 */
	private Date updateTime;
	
	/**
	 * @Fields createTime:创建时间
	 */
	private Date createTime;
	
	/**
	 * @Fields state:黑名单状态: 1正常,0非正常
	 */
	private Integer state;
	
	//columns END
	
	/**是否本地数据(true:数据来自本地服务器，false:数据来自新颜服务器)*/
	private boolean isLocalData;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getId_no() {
		return idNo;
	}

	public void setId_no(String idNo) {
		this.idNo = idNo;
	}

	public String getId_name() {
		return idName;
	}

	public void setId_name(String idName) {
		this.idName = idName;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTrade_no() {
		return tradeNo;
	}

	public void setTrade_no(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getTrans_id() {
		return transId;
	}

	public void setTrans_id(String transId) {
		this.transId = transId;
	}

	public String getDesc() {
		return descs;
	}

	public void setDesc(String descs) {
		this.descs = descs;
	}

	public String getMax_overdue_days() {
		return maxOverdueDays;
	}

	public void setMax_overdue_days(String maxOverdueDays) {
		this.maxOverdueDays = maxOverdueDays;
	}

	public String getLatest_overdue_time() {
		return latestOverdueTime;
	}

	public void setLatest_overdue_time(String latestOverdueTime) {
		this.latestOverdueTime = latestOverdueTime;
	}

	public String getMax_overdue_amt() {
		return maxOverdueAmt;
	}

	public void setMax_overdue_amt(String maxOverdueAmt) {
		this.maxOverdueAmt = maxOverdueAmt;
	}

	public String getCurrently_overdue() {
		return currentlyOverdue;
	}

	public void setCurrently_overdue(String currentlyOverdue) {
		this.currentlyOverdue = currentlyOverdue;
	}

	public String getCurrently_performance() {
		return currentlyPerformance;
	}

	public void setCurrently_performance(String currentlyPerformance) {
		this.currentlyPerformance = currentlyPerformance;
	}

	public String getAcc_exc() {
		return accExc;
	}

	public void setAcc_exc(String accExc) {
		this.accExc = accExc;
	}

	public String getAcc_sleep() {
		return accSleep;
	}

	public void setAcc_sleep(String accSleep) {
		this.accSleep = accSleep;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public boolean getIsLocalData() {
		return isLocalData;
	}

	public void setIsLocalData(boolean isLocalData) {
		this.isLocalData = isLocalData;
	}

}