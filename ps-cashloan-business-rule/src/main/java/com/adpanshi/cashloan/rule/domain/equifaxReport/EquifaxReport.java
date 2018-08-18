package com.adpanshi.cashloan.rule.domain.equifaxReport;


import java.io.Serializable;
import java.util.Date;

public class EquifaxReport implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主键Id
     */
    private Long id;
    /**
     * 报告正文
     */
    private Object reportContent;
    /**
     * 用户Id
     */
    private Long userId;
    /**
     * 获取时间
     */
    private Date createTime;
    /**
     * 响应消息
     */
    private String message;
    /**
     * 响应状态
     */
    private String state;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Object getReportContent() {
        return reportContent;
    }

    public void setReportContent(Object reportContent) {
        this.reportContent = reportContent;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
