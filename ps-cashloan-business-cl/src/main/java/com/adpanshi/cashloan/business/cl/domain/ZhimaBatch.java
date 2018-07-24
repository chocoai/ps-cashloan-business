package com.adpanshi.cashloan.business.cl.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 批量生产芝麻授权数据实体
 * 
 * @author nmnl
 * @version 1.0.0
 * @date 2017-08-15 17:54:27

 *
 *
 */
 public class ZhimaBatch implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 主键Id
    */
    private Long id;

    /**
    * 是否反馈，1反馈,0未反馈,-1反馈失败
    */
    private Integer state;

    /**
    * 文件名
    */
    private String fileName;

    /**
    * 
    */
    private Date createTime;

    /**
    * 文件路径
    */
    private String filePath;


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
    * @param id
    */
    public void setId(Long id){
        this.id = id;
    }

    /**
    * 获取是否反馈，1反馈,0未反馈,-1反馈失败
    *
    * @return 是否反馈，1反馈,0未反馈,-1反馈失败
    */
    public Integer getState(){
        return state;
    }

    /**
    * 设置是否反馈，1反馈,0未反馈,-1反馈失败
    * 
    * @param state 要设置的是否反馈，1反馈,0未反馈,-1反馈失败
    */
    public void setState(Integer state){
        this.state = state;
    }

    /**
    * 获取文件名
    *
    * @return 文件名
    */
    public String getFileName(){
        return fileName;
    }

    /**
    * 设置文件名
    * 
    * @param fileName 要设置的文件名
    */
    public void setFileName(String fileName){
        this.fileName = fileName;
    }

    /**
    * 获取
    *
    * @return 
    */
    public Date getCreateTime(){
        return createTime;
    }

    /**
    * 设置
    * 
    * @param createTime 要设置的
    */
    public void setCreateTime(Date createTime){
        this.createTime = createTime;
    }

    /**
    * 获取文件路径
    *
    * @return 文件路径
    */
    public String getFilePath(){
        return filePath;
    }

    /**
    * 设置文件路径
    * 
    * @param filePath 要设置的文件路径
    */
    public void setFilePath(String filePath){
        this.filePath = filePath;
    }

}