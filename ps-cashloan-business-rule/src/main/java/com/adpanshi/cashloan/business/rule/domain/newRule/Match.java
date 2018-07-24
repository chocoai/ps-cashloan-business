package com.adpanshi.cashloan.business.rule.domain.newRule;


import java.io.Serializable;
import java.util.Map;

/**
 * Created by cc on 2017-09-20 10:41.
 */
public class Match implements Serializable{

    private String rule_name; //规则描述
    private String match_value;//匹配字段
    private Rule bank;//null或""时候的处理
    private Rule other;//不在body条件里的
    private Map<String,Rule> matchs;//匹配的值对应规则
    private boolean used;//是否被用

    public String getRule_name() {
        return rule_name;
    }

    public void setRule_name(String rule_name) {
        this.rule_name = rule_name;
    }

    public String getMatch_value() {
        return match_value;
    }

    public void setMatch_value(String match_value) {
        this.match_value = match_value;
    }

    public Rule getBank() {
        return bank;
    }

    public void setBank(Rule bank) {
        this.bank = bank;
    }

    public Rule getOther() {
        return other;
    }

    public void setOther(Rule other) {
        this.other = other;
    }

    public Map<String, Rule> getMatchs() {
        return matchs;
    }

    public void setMatchs(Map<String, Rule> matchs) {
        this.matchs = matchs;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    @Override
    public String toString() {
        return "Match{" +
                "rule_name='" + rule_name + '\'' +
                ", match_value='" + match_value + '\'' +
                ", bank=" + bank +
                ", other=" + other +
                ", matchs=" + matchs +
                ", used=" + used +
                '}';
    }
}
