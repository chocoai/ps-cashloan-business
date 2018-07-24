package com.adpanshi.cashloan.business.cl.domain.lianxin;

import java.io.Serializable;
import java.util.Date;

/**
 * 请求日志类
 * Created by cc on 2017-08-31 20:29.
 */
public class LianXinReqLog implements Serializable{

    private String phone;
    private String reqId;
    private String msg;
    private String teltemp;
    private Date reqTime;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTeltemp() {
        return teltemp;
    }

    public void setTeltemp(String teltemp) {
        this.teltemp = teltemp;
    }

    public Date getReqTime() {
        return reqTime;
    }

    public void setReqTime(Date reqTime) {
        this.reqTime = reqTime;
    }
}
