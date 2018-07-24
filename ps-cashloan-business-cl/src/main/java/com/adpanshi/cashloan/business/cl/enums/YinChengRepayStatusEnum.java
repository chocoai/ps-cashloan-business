package com.adpanshi.cashloan.business.cl.enums;


public enum YinChengRepayStatusEnum {
    //     还款状态 2正常还款 4逾期还款
    NORMAL(2), OVERDUE(4);
    private int status;

    YinChengRepayStatusEnum(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.valueOf(this.status);
    }
}
