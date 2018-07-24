package com.adpanshi.cashloan.business.rule.domain.newRule;


import java.io.Serializable;
import java.util.List;

/**
 * Created by cc on 2017-09-19 11:40.
 */
public class If_Else implements Serializable{

    private String rule_name; //规则描述
    private String compare_value;//比较的字段
    private Rule bank;//null或""时候的处理
    private Rule other;//不在body条件里的
    private List<If_Else_body> body;//条件列表
    private boolean used;//是否被用

    public String getRule_name() {
        return rule_name;
    }

    public void setRule_name(String rule_name) {
        this.rule_name = rule_name;
    }

    public String getCompare_value() {
        return compare_value;
    }

    public void setCompare_value(String compare_value) {
        this.compare_value = compare_value;
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

    public List<If_Else_body> getBody() {
        return body;
    }

    public void setBody(List<If_Else_body> body) {
        this.body = body;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    @Override
    public String toString() {
        return "If_Else{" +
                "rule_name='" + rule_name + '\'' +
                ", compare_value='" + compare_value + '\'' +
                ", bank=" + bank +
                ", other=" + other +
                ", body=" + body +
                ", used=" + used +
                '}';
    }
}
