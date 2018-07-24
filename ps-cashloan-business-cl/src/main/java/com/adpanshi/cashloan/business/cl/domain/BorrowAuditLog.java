package com.adpanshi.cashloan.business.cl.domain;

import java.io.Serializable;

/**
 * 人工复审操作记录对象
 * Created by cc on 2017/8/2.
 */
public class BorrowAuditLog implements Serializable{


    private Long id;//主键Id
    private Long borrowId;
    private String auditLog;//操作记录json数据
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
