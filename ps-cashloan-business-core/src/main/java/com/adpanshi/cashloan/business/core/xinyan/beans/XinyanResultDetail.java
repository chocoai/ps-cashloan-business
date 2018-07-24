package com.adpanshi.cashloan.business.core.xinyan.beans;
/***
 ** @category 新颜响应-核查结果详情(只有当code=0时才有值、其它状态为空)...
 ** @author qing.yunhui
 ** @email: qingyunhui@smoney.cc
 ** @createTime: 2017年10月21日下午5:18:37
 **/
public class XinyanResultDetail {

	/**最大逾期金额*/
	private String max_overdue_amt;
	
	/**当前逾期*/
	private String currently_overdue;
	
	/**当前履约*/
	private String currently_performance;
	
	/**账户异常*/
	private String acc_exc;
	
	/**睡眠账户*/
	private String acc_sleep;
	
	/**最近逾期时 yyyy-MM*/
	private String latest_overdue_time;
	
	/**最长逾期天*/
	private String max_overdue_days;
	
	
	public String getMax_overdue_amt() {
		return max_overdue_amt;
	}
	public void setMax_overdue_amt(String max_overdue_amt) {
		this.max_overdue_amt = max_overdue_amt;
	}
	public String getCurrently_overdue() {
		return currently_overdue;
	}
	public void setCurrently_overdue(String currently_overdue) {
		this.currently_overdue = currently_overdue;
	}
	public String getCurrently_performance() {
		return currently_performance;
	}
	public void setCurrently_performance(String currently_performance) {
		this.currently_performance = currently_performance;
	}
	public String getAcc_exc() {
		return acc_exc;
	}
	public void setAcc_exc(String acc_exc) {
		this.acc_exc = acc_exc;
	}
	public String getAcc_sleep() {
		return acc_sleep;
	}
	public void setAcc_sleep(String acc_sleep) {
		this.acc_sleep = acc_sleep;
	}
	public String getLatest_overdue_time() {
		return latest_overdue_time;
	}
	public void setLatest_overdue_time(String latest_overdue_time) {
		this.latest_overdue_time = latest_overdue_time;
	}
	public String getMax_overdue_days() {
		return max_overdue_days;
	}
	public void setMax_overdue_days(String max_overdue_days) {
		this.max_overdue_days = max_overdue_days;
	}
	
	
}
