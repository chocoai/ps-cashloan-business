package com.adpanshi.cashloan.business.rule.domain.newRule;


/**
 * 开关类型对象
 * Created by cc on 2017-09-19 11:54.
 */
public class On_Off {

    private String rule_name; //规则描述
    private String switch_state;//开关状态
    private Rule on;//开的时候走的规则
    private Rule off;//关的时候走的规则
    private boolean used;//是否被用

    public String getRule_name() {
        return rule_name;
    }

    public void setRule_name(String rule_name) {
        this.rule_name = rule_name;
    }

    public String getSwitch_state() {
        return switch_state;
    }

    public void setSwitch_state(String switch_state) {
        this.switch_state = switch_state;
    }

    public Rule getOn() {
        return on;
    }

    public void setOn(Rule on) {
        this.on = on;
    }

    public Rule getOff() {
        return off;
    }

    public void setOff(Rule off) {
        this.off = off;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    @Override
    public String toString() {
        return "On_Off{" +
                "rule_name='" + rule_name + '\'' +
                ", switch_state='" + switch_state + '\'' +
                ", on=" + on +
                ", off=" + off +
                ", used=" + used +
                '}';
    }
}
