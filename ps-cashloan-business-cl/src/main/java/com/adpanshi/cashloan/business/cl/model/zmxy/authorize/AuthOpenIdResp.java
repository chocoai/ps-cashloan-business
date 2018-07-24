package com.adpanshi.cashloan.business.cl.model.zmxy.authorize;

import com.adpanshi.cashloan.business.cl.model.zmxy.base.BaseResp;
import com.antgroup.zmxy.openplatform.api.response.ZhimaAuthInfoAuthqueryResponse;


/**
 * Created by syq on 2016/9/13.
 */
public class AuthOpenIdResp extends BaseResp {

    /**
     * 最终授权状态
     */
    private boolean authorized;

    /**
     * 用户授权后的openId
     */
    private String openId;


    public AuthOpenIdResp(ZhimaAuthInfoAuthqueryResponse response) {
        super(response);
        this.authorized = response.getAuthorized();
        this.openId = response.getOpenId();
    }

    public boolean isAuthorized() {
        return authorized;
    }

    public String getOpenId() {
        return openId;
    }
}
