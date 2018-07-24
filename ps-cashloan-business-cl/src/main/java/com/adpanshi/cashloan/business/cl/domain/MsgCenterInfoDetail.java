package com.adpanshi.cashloan.business.cl.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 8470
 * @version 1.0.1
 * @date 2018/06/19 19:16:11
 * @desc 消息中心详情实体类
 * Copyright 浙江盘石 All Rights Reserved
 */
public class MsgCenterInfoDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键Id
     */
    private Long id;

    /**
     * 消息中心Id
     */
    private Long infoId;

    /**
     * 消息内容标题
     */
    private String title;

    /**
     * 消息内容文本
     */
    private String content;

    /**
     * 消息内容图片
     */
    private String contentImg;

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

    public Long getInfoId() {
        return infoId;
    }

    public void setInfoId(Long infoId) {
        this.infoId = infoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentImg() {
        return contentImg;
    }

    public void setContentImg(String contentImg) {
        this.contentImg = contentImg;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
