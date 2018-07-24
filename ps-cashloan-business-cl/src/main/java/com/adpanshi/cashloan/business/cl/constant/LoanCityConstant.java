package com.adpanshi.cashloan.business.cl.constant;

/**
 * @author yecy
 * @date 2018/1/3 14:12
 */
public final class LoanCityConstant {

    private LoanCityConstant() {
    }

    /*消贷同城系统配置参数名称*/
    public static final String LOANCITY_HOST = "loancity_host";
    public static final String LOANCITY_APPID = "loancity_appid";
    public static final String LOANCITY_PUBLIC_KEY = "loancity_public_key";
    public static final String LOANCITY_PRIVATE_KEY = "loancity_private_key";
    public static final String LOAN_CITY_CODE_LENGTH = "loan_city_code_length";
    public static final String LOANCITY_OPEN = "loancity_open";
    public static final String LOANCITY_MAX_RETRIES = "loancity_max_retries";
    public static final String LOANCITY_RETRY_INTERVAL = "loancity_retry_interval";
    public static final String LOANCITY_MAX_CODE_RETRIES = "loancity_max_code_retries";

    /*消贷同城响应code和msg*/
    public static final String RES_CODE = "resCode";
    public static final String RES_MSG = "resMsg";

    public static final String RES_CODE_SUCCESS = "100";
    public static final String RES_MSG_SUCCESS = "success";

    public static final String RES_CODE_FAIL_EXIST = "3001";
    public static final String RES_MSG_FAIL_EXIST = "需求已存在";

    public static final String RES_CODE_FAIL_NOT_EXIST = "3002";
    public static final String RES_MSG_FAIL_NOT_EXIST = "需求不存在";

    public static final String RES_CODE_FAIL_FINISH = "3003";
    public static final String RES_MSG_FAIL_FINISH = "借款订单已放款，不允许撤销或过期";

    public static final String RES_CODE_FAIL_STATE = "3004";
    public static final String RES_MSG_FAIL_STATE = "当前需求订单不允许支付";

    public static final String RES_CODE_FAIL_SIGN = "2001";
    public static final String RES_MSG_FAIL_SIGN = "验签失败";

}
