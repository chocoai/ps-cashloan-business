package com.adpanshi.cashloan.business.rule.domain.newRule;

/**
 * 规则用的常量
 * Created by cc on 2017-09-19 15:28.
 */
public class RuleConstant {

    //type类型
    public static final String TYPE_RESULT= "result";
    public static final String TYPE_ON_OFF = "on_off";
    public static final String TYPE_IF_ELSE = "if_else";
    public static final String TYPE_MATCH = "match";


    //if_else 用的运算符
    public static final String HIGH = ">";
    public static final String HIGH_EQUAL = ">=";
    public static final String EQUAL = "=";
    public static final String LOW = "<";
    public static final String LOW_EQUAL = "<=";
    public static final String NOT_EQUAL = "!=";

    public static final String LOW_LOW = "<<";
    public static final String LOW_EQUAL_LOW = "<=<";
    public static final String LOW_EQUAL_LOW_EQUAL = "<=<=";
    public static final String LOW_LOW_EQUAL = "<<=";

    //redis key

    //rule_type key
    public static final String REDIS_RULE = "rule_tmp";
    public static final String REDIS_RESULT = "rule_type:result";
    public static final String REDIS_ON_OFF = ":rule_type:on_off";
    public static final String REDIS_IF_ELSE = ":rule_type:if_else";
    public static final String REDIS_MATCH = ":rule_type:match";






}
