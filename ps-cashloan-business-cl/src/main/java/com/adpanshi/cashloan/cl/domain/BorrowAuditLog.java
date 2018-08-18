package com.adpanshi.cashloan.cl.domain;

import java.io.Serializable;

/**
 * 人工复审操作记录对象
 * Created by cc on 2017/8/2.
 * @author cc
 */
public class BorrowAuditLog implements Serializable{

    /** 主键Id */
    private Long id;
    private Long borrowId;
    /** 操作记录json数据 */
    private String auditLog;
    private String jsonData;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBorrowId() {
        return borrowId;
    }

    public void setBorrowId(Long borrowId) {
        this.borrowId = borrowId;
    }

    public String getAuditLog() {
        return auditLog;
    }

    public void setAuditLog(String auditLog) {
        this.auditLog = auditLog;
    }

    public String getJsonData() {
        return jsonData;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }
}
