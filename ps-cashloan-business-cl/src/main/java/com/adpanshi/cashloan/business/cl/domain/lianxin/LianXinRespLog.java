package com.adpanshi.cashloan.business.cl.domain.lianxin;

/**
 * 联信异步回调参数对象
 * Created by cc on 2017-09-01 08:47.
 */
public class LianXinRespLog {

    private String caller;//主叫
    private String called;//被叫
    private int talktime;//通话时长
    private long connecttime;//开始时间
    private long hunguptime;//挂机时间
    private int hungupreason;//挂机原因
    private int calltype;//呼叫类型
    private String requestid;//请求id
    private String svpctmkey;//按键

    public String getCaller() {
        return caller;
    }

    public void setCaller(String caller) {
        this.caller = caller;
    }

    public String getCalled() {
        return called;
    }

    public void setCalled(String called) {
        this.called = called;
    }

    public int getTalktime() {
        return talktime;
    }

    public void setTalktime(int talktime) {
        this.talktime = talktime;
    }

    public long getConnecttime() {
        return connecttime;
    }

    public void setConnecttime(long connecttime) {
        this.connecttime = connecttime;
    }

    public long getHunguptime() {
        return hunguptime;
    }

    public void setHunguptime(long hunguptime) {
        this.hunguptime = hunguptime;
    }

    public int getHungupreason() {
        return hungupreason;
    }

    public void setHungupreason(int hungupreason) {
        this.hungupreason = hungupreason;
    }

    public int getCalltype() {
        return calltype;
    }

    public void setCalltype(int calltype) {
        this.calltype = calltype;
    }


    public String getRequestid() {
        return requestid;
    }

    public void setRequestid(String requestid) {
        this.requestid = requestid;
    }

    public String getSvpctmkey() {
        return svpctmkey;
    }

    public void setSvpctmkey(String svpctmkey) {
        this.svpctmkey = svpctmkey;
    }


    @Override
    public String toString() {
        return "LianXinRespLog{" +
                "caller='" + caller + '\'' +
                ", called='" + called + '\'' +
                ", talktime=" + talktime +
                ", connecttime=" + connecttime +
                ", hunguptime=" + hunguptime +
                ", hungupreason=" + hungupreason +
                ", calltype=" + calltype +
                ", requestid='" + requestid + '\'' +
                ", svpctmkey='" + svpctmkey + '\'' +
                '}';
    }
}
