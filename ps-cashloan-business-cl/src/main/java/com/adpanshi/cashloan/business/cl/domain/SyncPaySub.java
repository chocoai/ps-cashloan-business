package com.adpanshi.cashloan.business.cl.domain;

/**
 * Created by yhd on 2017/7/17.
 * 虚拟实体.接受app传入数据
 */

public class SyncPaySub {

    private String orderNo; //商户订单编号

    private String params  ; //请求参数（加密参数）

    private String reqDetailParams; // 请求参数（非加密）

    private String service; //请求第三方方法

    private String   returnParams ; //支付同步回调参数

    private String   returnTime ; // 支付同步回调时间

    private String   result  ; //支付结果（1成功 -1失败）

    private String  createTime ; //

    private String ip;//请求ip
    
    public SyncPaySub(){}
    
    /**
     * @param returnParams 支付同步回调参数
     * @param returnTime   支付同步回调时间
     * @param reqDetailParams 请求参数（非加密）
     * @param service  请求第三方方法
     * @param orderNo  商户订单编号
     * @param result   支付结果（1成功 -1失败）
     * @param params   请求参数（加密参数）
     * @param createTime
     * */
    public SyncPaySub(String returnParams,String returnTime,String reqDetailParams,String service,String orderNo,String result,String params,String createTime){
    	this.returnParams=returnParams;
    	this.returnTime=returnTime;
    	this.reqDetailParams=reqDetailParams;
    	this.service=service;
    	this.orderNo=orderNo;
    	this.result=result;
    	this.params=params;
    	this.createTime=createTime;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getReqDetailParams() {
        return reqDetailParams;
    }

    public void setReqDetailParams(String reqDetailParams) {
        this.reqDetailParams = reqDetailParams;
    }

    public String getReturnParams() {
        return returnParams;
    }

    public void setReturnParams(String returnParams) {
        this.returnParams = returnParams;
    }

    public String getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(String returnTime) {
        this.returnTime = returnTime;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        return "SyncPaySub{" +
                "createTime='" + createTime + '\'' +
                ", orderNo='" + orderNo + '\'' +
                ", params='" + params + '\'' +
                ", reqDetailParams='" + reqDetailParams + '\'' +
                ", com.adpanshi.cashloan.api.com.adpanshi.cashloan.api.cr.service='" + service + '\'' +
                ", returnParams='" + returnParams + '\'' +
                ", returnTime='" + returnTime + '\'' +
                ", result='" + result + '\'' +
                ", ip='" + ip + '\'' +
                '}';
    }
}
