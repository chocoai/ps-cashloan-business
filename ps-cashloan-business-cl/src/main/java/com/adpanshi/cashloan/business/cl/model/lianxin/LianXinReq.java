package com.adpanshi.cashloan.business.cl.model.lianxin;

/**
 * 联信请求对象
 * Created by cc on 2017-08-30 14:25.
 */
public class LianXinReq {
    private UserInfo userinfo;

    public LianXinReq() {
    }

    public LianXinReq(UserInfo userinfo) {
        this.userinfo = userinfo;
    }

    public UserInfo getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(UserInfo userinfo) {
        this.userinfo = userinfo;
    }
}
