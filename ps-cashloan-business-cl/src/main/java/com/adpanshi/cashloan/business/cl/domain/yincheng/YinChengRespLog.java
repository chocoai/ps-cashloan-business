package com.adpanshi.cashloan.business.cl.domain.yincheng;

import java.io.Serializable;
import java.util.Date;

/**
 * 银程请求保存表实体
 * 
 * @author yecy
 * @version 1.0.0
 * @date 2018-03-09 22:15:51

 *
 *
 */
 public class YinChengRespLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 主键Id
    */
    private Long id;

    /**
    * 唯一标识
    */
    private String packageId;

    /**
    * 接口名称
    */
    private String service;

    /**
    * 请求参数
    */
    private String params;

    /**
    * 响应返回code
    */
    private String code;

    /**
    * 响应返回msg
    */
    private String msg;

    private Date createTime;


    /**
    * 获取主键Id
    *
    * @return id
    */
    public Long getId(){
        return id;
    }

    /**
    * 设置主键Id
    * 
    * @param id 要设置的主键Id
    */
    public void setId(Long id){
        this.id = id;
    }

    /**
    * 获取唯一标识
    *
    * @return 唯一标识
    */
    public String getPackageId(){
        return packageId;
    }

    /**
    * 设置唯一标识
    * 
    * @param packageId 要设置的唯一标识
    */
    public void setPackageId(String packageId){
        this.packageId = packageId;
    }

    /**
    * 获取接口名称
    *
    * @return 接口名称
    */
    public String getService(){
        return service;
    }

    /**
    * 设置接口名称
    * 
    * @param service 要设置的接口名称
    */
    public void setService(String service){
        this.service = service;
    }

    /**
    * 获取请求参数
    *
    * @return 请求参数
    */
    public String getParams(){
        return params;
    }

    /**
    * 设置请求参数
    * 
    * @param params 要设置的请求参数
    */
    public void setParams(String params){
        this.params = params;
    }

    /**
    * 获取响应返回code
    *
    * @return 响应返回code
    */
    public String getCode(){
        return code;
    }

    /**
    * 设置响应返回code
    * 
    * @param code 要设置的响应返回code
    */
    public void setCode(String code){
        this.code = code;
    }

    /**
    * 获取响应返回msg
    *
    * @return 响应返回msg
    */
    public String getMsg(){
        return msg;
    }

    /**
    * 设置响应返回msg
    * 
    * @param msg 要设置的响应返回msg
    */
    public void setMsg(String msg){
        this.msg = msg;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
