package com.adpanshi.cashloan.business.cl.model.yincheng;

/**
 * 紧急联系人模型
 * @author yecy
 */
public class UserEmerContactModel {
    private String name;//    紧急联系人姓名
    private String contact;//    紧急联系人关系
    private String mobile;//   紧急联系人电话

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
