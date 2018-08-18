package com.adpanshi.cashloan.core.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: fenqidai
 * @description: 消息实体
 * @author: Mr.Wange
 * @create: 2018-07-23 09:43
 **/
public class Notices implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 消息主键
     */
    private Long id;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 标题
     */
    private String title;
    /**
     * 链接地址
     */
    private String url;
    /**
     * 内容
     */
    private String content;
    /**
     * 消息类型
     */
    private String type;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 开始时间
     */
    private Date startDate;
    /**
     * 结束时间
     */
    private Date stopDate;
    /**
     * 状态
     */
    private String state;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getStopDate() {
        return stopDate;
    }

    public void setStopDate(Date stopDate) {
        this.stopDate = stopDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
