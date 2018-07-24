package com.adpanshi.cashloan.business.rc.domain;

import java.io.Serializable;

/**
 * 通讯录匹配字典
 * Created by cc on 2017-09-05 11:37.
 */
public class ContactMatchDict implements Serializable {
    private Long id;
    private String name;
    private int type;//类型 0-亲属类  1-专线号码类

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
