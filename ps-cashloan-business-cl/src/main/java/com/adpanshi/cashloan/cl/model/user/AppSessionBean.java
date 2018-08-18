package com.adpanshi.cashloan.cl.model.user;

import java.util.Map;

/**
 * Created by lsk on 2016/9/21.
 */
@SuppressWarnings({ "rawtypes" })
public class AppSessionBean {

    private Map session;

    public AppSessionBean(Map session) {
        this.session = session;
    }

    public Map getFront(){
        return (Map) session.get("front");
    }


    public String getUserId() {
        return getFront().get("userId").toString();
    }

    public Map getSession() {
        return session;
    }

    public Map getUserData(){
        return (Map) session.get("userData");
    }
}
