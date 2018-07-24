package com.adpanshi.cashloan.business.cl.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 8470
 * @version 1.0.1
 * @date 2018/06/19 19:14:24
 * @desc 消息中心实体类
 * Copyright 浙江盘石 All Rights Reserved
 */
public class MsgCenterInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键Id
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 消息类型
     */
    private String msgType;

    /**
     * 消息状态
     */
    private String state;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建时间
     */
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
