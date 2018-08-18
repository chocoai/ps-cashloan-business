package com.adpanshi.cashloan.rule.domain;

import java.io.Serializable;

/**
 * 字典匹配的用户通讯录
 * Created by cc on 2017-09-05 15:11.
 */
public class UserContactsMatch extends UserContacts implements Serializable{

    private Integer state ;

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
