package com.adpanshi.cashloan.business.rule.domain.newRule;

import java.io.Serializable;

/**
 * 结果集对象
 * Created by cc on 2017-09-19 11:55.
 */
public class Result implements Serializable {

    private String result_name;//规则名称
    private String result_type;//规则类型
    private String result_value;//具体值

    public String getResult_name() {
        return result_name;
    }

    public void setResult_name(String result_name) {
        this.result_name = result_name;
    }

    public String getResult_type() {
        return result_type;
    }

    public void setResult_type(String result_type) {
        this.result_type = result_type;
    }

    public String getResult_value() {
        return result_value;
    }

    public void setResult_value(String result_value) {
        this.result_value = result_value;
    }

    @Override
    public String toString() {
        return "Result{" +
                "result_name='" + result_name + '\'' +
                ", result_type='" + result_type + '\'' +
                ", result_value='" + result_value + '\'' +
                '}';
    }
}
