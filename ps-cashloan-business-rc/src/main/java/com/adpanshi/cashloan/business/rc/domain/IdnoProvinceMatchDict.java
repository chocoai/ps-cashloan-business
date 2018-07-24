package com.adpanshi.cashloan.business.rc.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 通讯录匹配字典
 * Created by cc on 2017-09-05 11:37.
 */
public class IdnoProvinceMatchDict implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * @Fields id:主键id
     * */
    private Long id;

    /**
     * @Fields id:主键id
     * */
    private String provinceName;

    /**
     * @Fields id:主键id
     * */
    private int provinceStartNo;

    /**
     * @Fields id:主键id
     * */
    private Date createTime;

    /**
     * @Fields id:主键id
     * */
    private int state;//类型 0-禁用  1-启用

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public int getProvinceStartNo() {
        return provinceStartNo;
    }

    public void setProvinceStartNo(int provinceStartNo) {
        this.provinceStartNo = provinceStartNo;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
