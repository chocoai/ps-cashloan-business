package com.adpanshi.cashloan.business.rule.domain.newRule;


import java.io.Serializable;

/**
 * if_else的条件值对象
 * Created by cc on 2017-09-19 10:54.
 */
public class Param implements Serializable{

    public Param() {
    }

    public Param(Integer head, Integer tail) {
        this.head = head;
        this.tail = tail;
    }

    private Integer head;
    private Integer tail;
    public Param(Integer head) {
        this.head = head;
    }

    public Integer getHead() {
        return head;
    }

    public void setHead(Integer head) {
        this.head = head;
    }

    public Integer getTail() {
        return tail;
    }

    public void setTail(Integer tail) {
        this.tail = tail;
    }

    @Override
    public String toString() {
        return "Param{" +
                "head=" + head +
                ", tail=" + tail +
                '}';
    }
}
