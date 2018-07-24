package com.adpanshi.cashloan.business.rule.domain;

import com.adpanshi.cashloan.business.core.common.util.StringUtil;
import com.adpanshi.cashloan.business.core.model.TianChuangResponse;
import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.util.Date;


/**
 * @category saas 响应记录
 * @author qing.yunhui 
 * @Since 2011-2018
 * @create 2018-04-03 12:04:06
 * @history
 */
public class SaasRespRecord implements Serializable{
	
	private static final long serialVersionUID = 1L;

	//alias
	protected static final String TABLE_ALIAS = "ClSaasRespRecord";
	
	public SaasRespRecord(){
		super();
	}
	
	/**
	 * @param reqId
	 * @param resId
	 * @param state
	 * @param resCode
	 * @param msg
	 * @param data
	 * */
	public SaasRespRecord(String reqId,String resId,String taskId,String state,String resCode,String msg,String data,Integer type){
		this.reqId=reqId;
		this.resId=resId;
		this.taskId=taskId;
		this.state=state;
		this.resCode=resCode;
		this.msg=msg;
		this.data=data;
		this.type=type;
	}
	
	public SaasRespRecord(Long userId,String reqId,String resId,String taskId,String state,String resCode,String msg,String data,Integer type){
		this(reqId, resId, taskId, state, resCode, msg, data, type);
		this.userId=userId;
	}
	
	public SaasRespRecord(String params,String state,Integer type){
		if(StringUtil.isNotBlank(params)){
			TianChuangResponse response=JSONObject.parseObject(params, TianChuangResponse.class);
			this.reqId=response.getReqId();
			this.resId=response.getResId();
			this.state=state;
			this.resCode=response.getCode();
			this.msg=response.getMsg();
			this.data=response.getData();
			this.taskId=response.getTid();
			if(StringUtil.isNotBlank(response.getUserId())){
				this.userId=Long.parseLong(response.getUserId());
			}
			this.type = type;
		}
	}
	
	//columns START
	/**
	 * @Fields id:主键Id
	 */
	private Long id;
	
	/**
	 * @Fields reqId:reqRecordId:cl_saas_req_record
	 */
	private String reqId;
	
	/**
	 * @Fields resId:responseid
	 */
	private String resId;
	
	/**
	 * @Filelds 任务id
	 * */
	private String taskId;

	/**
	 * @Fields userId:用户id
	 */
	private Long userId;
	
	/**
	 * @Fields state:爬取状态  10 已提交申请 20 爬取成功  30 爬取失败
	 */
	private String state;
	
	/**
	 * @Fields resCode:返回数据状态码
	 */
	private String resCode;
	
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

	/**
	 * @Fields type:账户类型
	 */
	private Integer type;
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getResCode() {
		return resCode;
	}

	public void setResCode(String resCode) {
		this.resCode = resCode;
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

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}
