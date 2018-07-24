package com.adpanshi.cashloan.business.core.model;

import com.adpanshi.cashloan.business.core.common.util.StringUtil;

/***
 ** @category 天创响应结构体...
 ** @author qing.yunhui
 ** @email: qingyunhui@fentuan360.com
 ** @createTime: 2018年3月16日下午18:34:50
 **/
public class TianChuangResponse {
	
	/**请求id*/
	private String reqId;
	
	/**响应id*/
	private String resId;
	
	/**任务id*/
	private String tid;

	/** 状态码(0:成功、非0:失败) */
	private String code;
	
	/**响应描述*/
    private String msg;
    
    /**结果集[JSON串]*/
    private String data;
    
    private String userId;
    
    /**接口是否响应、数据是否抓取成功*/
    public boolean isSuccess(){
    	return StringUtil.isNotEmptys(code,data)&&"0".equals(code);
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

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}
	
}
