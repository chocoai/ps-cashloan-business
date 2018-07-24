package com.adpanshi.cashloan.business.rule.constant;

/**
 * @author yecy
 * @date 2018/2/6 10:09
 */
public class TCWindConstant {
    private TCWindConstant() {
    }

    /*社保流水号*/
    public static final String SOCIAL_SECURITY_TID = "social_security_tid";
    /*公积金流水号*/
    public static final String HOUSING_FUND_TID = "housing_fund_tid";
    /*网银账单流水号*/
    public static final String ONLINE_BANK_TID = "online_bank_tid";
    /*信用卡邮箱流水号*/
    public static final String CREDITCARD_BILL_TID = "creditcard_bill_tid";
    /*运营商流水号*/
    public static final String OPERATOR_TID = "operator_tid";

    /*天创大风策请求开关*/
    public static final String TCREDIT_OPEN = "tcredit_open";
    /*天创大风策预授信请求间隔时间（天）*/
    public static final String TCWIND_REQ_AUTH_INTERVAL = "tcwind_req_auth_interval";
    /*天创大风策审批请求间隔时间（天）*/
    public static final String TCWIND_REQ_REVIEW_INTERVAL = "tcwind_req_review_interval";

    /*预授信接口名称*/
    public static final String SERVICE_PREAUTH = "preAuth";
    /*审批授信接口名称*/
    public static final String SERVICE_REVIEW = "review";

    public static final String SUCCESS_CODE = "0";


}
