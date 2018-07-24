package com.adpanshi.cashloan.business.rule.model;

import com.adpanshi.cashloan.business.core.common.util.NumberToCNUtil;

/**
 * @author yecy
 * @date 2017/12/22 11:06
 */
public class ScaleShowModel {

    private Long timeLimit = 0L;
    private Long cycle = 7L;


    public Long getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(Long timeLimit) {
        this.timeLimit = timeLimit;
    }


    public void setCycle(Long cycle) {
        this.cycle = cycle;
    }

    public String getTitle() {
        StringBuilder title = new StringBuilder();
        Long num = timeLimit / cycle;
        title.append(NumberToCNUtil.toHanStr(num.intValue())).append("期(").append(timeLimit).append("天)");

        return title.toString();
    }
}
