package com.adpanshi.cashloan.business.rule.enums;

/**
 * @author yecy
 * @date 2017/12/16 11:25
 */
public enum PayType {


    AC("等额本金"), ACPI("等额本息");

    private String name;

    PayType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
