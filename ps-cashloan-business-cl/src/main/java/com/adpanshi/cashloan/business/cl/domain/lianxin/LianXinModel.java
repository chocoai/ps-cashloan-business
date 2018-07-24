package com.adpanshi.cashloan.business.cl.domain.lianxin;

import com.adpanshi.cashloan.business.cl.model.ManageBRepayModel;

/**
 * 列表显示对象
 * Created by cc on 2017-09-04 15:19.
 */
public class LianXinModel extends ManageBRepayModel {

    private String voiceState;

    private String hungupreasons;

    public String getVoiceState() {
        return voiceState;
    }

    public void setVoiceState(String voiceState) {
        this.voiceState = voiceState;
    }

    public String getHungupreasons() {
        return hungupreasons;
    }

    public void setHungupreasons(String hungupreasons) {
        this.hungupreasons = hungupreasons;
    }
}
