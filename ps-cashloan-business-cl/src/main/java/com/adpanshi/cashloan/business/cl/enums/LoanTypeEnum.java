package com.adpanshi.cashloan.business.cl.enums;

/**
 * @author yecy
 * @date 2018/1/2 9:47
 */
public enum LoanTypeEnum {
    RENT("租房贷款", 10),
    CONSUME("消费分期", 20);

    private String name;
    private Integer code;

    LoanTypeEnum(String name, Integer code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public Integer getCode() {
        return code;
    }

}
