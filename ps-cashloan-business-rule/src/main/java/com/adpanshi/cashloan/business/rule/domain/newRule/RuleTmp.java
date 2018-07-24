package com.adpanshi.cashloan.business.rule.domain.newRule;

import java.io.Serializable;

/**
 * 规则模板对象
 * Created by cc on 2017-09-21 15:10.
 */
public class RuleTmp implements Serializable {

    private String name; //规则名称
    private String auth;  //创建者
    private String updateTime; //更新时间
    private String createTime;  //创建时间
    private boolean state; //状态 false-不可用  true-可用
    private String rule_type;//调用规则类型
    private String tpm_num; //调用规则序号

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

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
        return "RuleTmp{" +
                "name='" + name + '\'' +
                ", auth='" + auth + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", createTime='" + createTime + '\'' +
                ", state=" + state +
                ", rule_type='" + rule_type + '\'' +
                ", tpm_num='" + tpm_num + '\'' +
                '}';
    }
}
