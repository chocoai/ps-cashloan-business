package com.adpanshi.cashloan.business.rule.domain.newRule;


import java.io.Serializable;

/**
 * 规则对象
 * Created by cc on 2017-09-19 10:52.
 */
public class Rule implements Serializable {


    public Rule() {
    }

    public Rule(String rule_type, String tpm_num) {
        this.rule_type = rule_type;
        this.tpm_num = tpm_num;
    }

    //    private String rule_name; //规则描述
    private String rule_type;//on_off-开关类型  result-结果类型 if_else类型
    private String tpm_num;//规则编号

    public String getRule_type() {
        return rule_type;
    }

    public void setRule_type(String rule_type) {
        this.rule_type = rule_type;
    }

    public String getTpm_num() {
        return tpm_num;
    }

    public void setTpm_num(String tpm_num) {
        this.tpm_num = tpm_num;
    }

    @Override
    public String toString() {
        return "Rule{" +
                "rule_type='" + rule_type + '\'' +
                ", tpm_num='" + tpm_num + '\'' +
                '}';
    }
}
