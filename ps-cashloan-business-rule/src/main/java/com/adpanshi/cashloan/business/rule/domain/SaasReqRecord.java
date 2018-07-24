package com.adpanshi.cashloan.business.rule.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @category saas 请求记录
 * @author qing.yunhui 
 * @Since 2011-2018
 * @create 2018-04-03 12:03:55
 * @history
 */
public class SaasReqRecord implements Serializable{
	
	private static final long serialVersionUID = 1L;

	//alias
	protected static final String TABLE_ALIAS = "ClSaasReqRecord";
	
	
	public SaasReqRecord(){
		super();
	}
	
	/**
	 * @param channel
	 * @param type
	 * @param state
	 * @param userId
	 * @param reqId
	 * */
	public SaasReqRecord(String channel,Integer type,String state,Long userId,String reqId){
		this.channel=channel;
		this.type=type;
		this.state=state;
		this.userId=userId;
		this.reqId=reqId;
	}
	
	/**
	 * @param channel
	 * @param type
	 * @param state
	 * @param userId
	 * @param data
	 * @param msg
	 * @param reqCode
	 * @param reqId
	 * @param resId
	 * */
	public SaasReqRecord(String channel,Integer type,String state,Long userId,String reqId,String data,String msg,String reqCode,String resId){
		this(channel, type, state, userId, reqId);
		this.data=data;
		this.msg=msg;
		this.reqCode=reqCode;
		this.resId=resId;
	}
	
	//columns START
	/**
	 * @Fields id:主键
	 */
	private Long id;
	
	/**
	 * @Fields reqId:requestid:uuid
	 */
	private String reqId;
	
	/**
	 * @Fields resId:responseid
	 */
	private String resId;
	
	/**
	 * @Fields userId:用户id
	 */
	private Long userId;
	
	/**
	 * @Fields type:接口类型(10 公积金认证、20 社保认证)
	 */
	private Integer type;
	
	/**
	 * @Fields state:爬取状态  10 已提交申请 20 爬取成功  30 爬取失败
	 */
	private String state;
	
	/**
	 * @Fields channel:渠道  10:天创
	 */
	private String channel;
	
	/**
	 * @Fields reqCode:报告状态码
	 */
	private String reqCode;
	
	/**
	 * @Fields msg:报告描述
	 */
	private String msg;
	
	/**
	 * @Fields data:报告结果
	 */
	private String data;
	
	/**
	 * @Fields createTime:创建时间
	 */
	private Date createTime;
	
	/**
	 * @Fields updateTime:更新时间
	 */
	private Date updateTime;
	
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

	public String getReqId() {
		return reqId;
	}

	public void setReqId(String reqId) {
		this.reqId = reqId;
	}

	public String getResId() {
		return resId;
	}

	public void setResId(String resId) {
		this.resId = resId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getReqCode() {
		return reqCode;
	}

	public void setReqCode(String reqCode) {
		this.reqCode = reqCode;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}
