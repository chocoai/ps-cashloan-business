package com.adpanshi.cashloan.business.cl.model.lianxin;

import com.adpanshi.cashloan.business.core.common.context.Global;
import com.adpanshi.cashloan.business.core.common.util.code.MD5;

import java.io.Serializable;

/**
 * 联信语音消息发送参数
 * Created by cc on 2017-08-30 14:26.
 */
public class UserInfo implements Serializable{
    private String teltemp;//自定义模板ID
    private String contextparm;//变量内容
    private String company;//企业账户
    private long keytime;//时间戳
    private String key;//加密内容
    private String callingline;//被叫
    private String telno;//显示号码"
    private String sex;//性别
    private String requestid;//唯一请求ID
    private String ivr;//ivr参数

    public UserInfo() {
        this.company = Global.getValue("lx_company");
        this.telno = Global.getValue("lx_telno");
        this.ivr = Global.getValue("lx_ivr");
        this.sex = Global.getValue("lx_sex");
        this.keytime = System.currentTimeMillis();
    }

    /**
     *
     * @param teltemp  模板id
     * @param contextparm 自定义参数内容
     */
    public UserInfo(String teltemp, String contextparm) {
        this();
        this.teltemp = teltemp;
        this.contextparm = contextparm;
        this.key = sign();
    }

    private String sign() {
        String key = Global.getValue("lx_key");
        String password = Global.getValue("lx_password");
        long time = this.keytime;
        return MD5.getMD5ofStr(key + password + time).toLowerCase();
    }

    public String getTeltemp() {
        return teltemp;
    }

    public void setTeltemp(String teltemp) {
        this.teltemp = teltemp;
    }

    public String getContextparm() {
        return contextparm;
    }

    public void setContextparm(String contextparm) {
        this.contextparm = contextparm;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public long getKeytime() {
        return keytime;
    }

    public void setKeytime(long keytime) {
        this.keytime = keytime;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCallingline() {
        return callingline;
    }

    public void setCallingline(String callingline) {
        this.callingline = callingline;
    }

    public String getTelno() {
        return telno;
    }

    public void setTelno(String telno) {
        this.telno = telno;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getRequestid() {
        return requestid;
    }

    public void setRequestid(String requestid) {
        this.requestid = requestid;
    }

    public String getIvr() {
        return ivr;
    }

    public void setIvr(String ivr) {
        this.ivr = ivr;
    }




}
