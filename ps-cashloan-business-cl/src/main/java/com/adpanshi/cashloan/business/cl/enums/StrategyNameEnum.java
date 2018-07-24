package com.adpanshi.cashloan.business.cl.enums;

/**
 * @author yecy
 * @date 2017/11/24 16:41
 */
public enum StrategyNameEnum {

    TIANCHUANG("tianchuang"),
    TONGDUN("tongdun"),
    TONGDUN_NEW("tongdun_new");

    private String name;

    StrategyNameEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
