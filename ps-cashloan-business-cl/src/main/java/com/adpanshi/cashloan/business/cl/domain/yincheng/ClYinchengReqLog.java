package com.adpanshi.cashloan.business.cl.domain.yincheng;

import java.io.Serializable;
import java.util.Date;

/**
 * 请求银程保存同步表实体
 * 
 * @author nmnl
 * @version 1.0.0
 * @date 2018-03-10 10:52:13

 *
 *
 */
 public class ClYinchengReqLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 主键Id
    */
    private Long id;

    /**
    * 控制器名称
    */
    private String m;

    /**
    * 方法名
    */
    private String s;

    /**
    * 请求参数
    */
    private String params;

    /**
    * 返回code,递增
    */
    private String code;

    /**
    * 返回消息,递增
    */
    private String msg;

    /**
    * 标记接口请求状态:0成功,1失败[最终结果]
    */
    private Integer state;

    /**
    * 首次创建时间
    */
    private Date createTime;

    /**
    * 更新时间,每次请求都会更新
    */
    private Date updateTime;

    /**
    * 请求次数，递增
    */
    private Integer num;

    /**
    * 请求参数标记
    */
    private String p;


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
    * @param 要设置的主键Id
    */
    public void setId(Long id){
        this.id = id;
    }

    /**
    * 获取控制器名称
    *
    * @return 控制器名称
    */
    public String getM(){
        return m;
    }

    /**
    * 设置控制器名称
    * 
    * @param m 要设置的控制器名称
    */
    public void setM(String m){
        this.m = m;
    }

    /**
    * 获取方法名
    *
    * @return 方法名
    */
    public String getS(){
        return s;
    }

    /**
    * 设置方法名
    * 
    * @param s 要设置的方法名
    */
    public void setS(String s){
        this.s = s;
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
    * 获取返回code,递增
    *
    * @return 返回code,递增
    */
    public String getCode(){
        return code;
    }

    /**
    * 设置返回code,递增
    * 
    * @param code 要设置的返回code,递增
    */
    public void setCode(String code){
        this.code = code;
    }

    /**
    * 获取返回消息,递增
    *
    * @return 返回消息,递增
    */
    public String getMsg(){
        return msg;
    }

    /**
    * 设置返回消息,递增
    * 
    * @param msg 要设置的返回消息,递增
    */
    public void setMsg(String msg){
        this.msg = msg;
    }

    /**
    * 获取标记接口请求状态:0成功,1失败[最终结果]
    *
    * @return 标记接口请求状态:0成功,1失败[最终结果]
    */
    public Integer getState(){
        return state;
    }

    /**
    * 设置标记接口请求状态:0成功,1失败[最终结果]
    * 
    * @param state 要设置的标记接口请求状态:0成功,1失败[最终结果]
    */
    public void setState(Integer state){
        this.state = state;
    }

    /**
    * 获取首次创建时间
    *
    * @return 首次创建时间
    */
    public Date getCreateTime(){
        return createTime;
    }

    /**
    * 设置首次创建时间
    * 
    * @param createTime 要设置的首次创建时间
    */
    public void setCreateTime(Date createTime){
        this.createTime = createTime;
    }

    /**
    * 获取更新时间,每次请求都会更新
    *
    * @return 更新时间,每次请求都会更新
    */
    public Date getUpdateTime(){
        return updateTime;
    }

    /**
    * 设置更新时间,每次请求都会更新
    * 
    * @param updateTime 要设置的更新时间,每次请求都会更新
    */
    public void setUpdateTime(Date updateTime){
        this.updateTime = updateTime;
    }

    /**
    * 获取请求次数，递增
    *
    * @return 请求次数，递增
    */
    public Integer getNum(){
        return num;
    }

    /**
    * 设置请求次数，递增
    * 
    * @param num 要设置的请求次数，递增
    */
    public void setNum(Integer num){
        this.num = num;
    }

    /**
    * 获取请求参数标记
    *
    * @return 请求参数标记
    */
    public String getP(){
        return p;
    }

    /**
    * 设置请求参数标记
    * 
    * @param p 要设置的请求参数标记
    */
    public void setP(String p){
        this.p = p;
    }

}
