package com.adpanshi.cashloan.business.rule.domain.newRule;


import java.io.Serializable;

/**
 * 区间条件配置对象
 * Created by cc on 2017-09-19 14:02.
 */
public class If_Else_body implements Serializable{
    private String operator;
    private Param param;
    private Rule rule;

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Param getParam() {
        return param;
    }

    public void setParam(Param param) {
        this.param = param;
    }

    public Rule getRule() {
        return rule;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }

    @Override
    public String toString() {
        return "If_Else_body{" +
                "operator='" + operator + '\'' +
                ", param=" + param +
                ", com.adpanshi.cashloan.api.com.adpanshi.cashloan.api.rule=" + rule +
                '}';
    }
}
