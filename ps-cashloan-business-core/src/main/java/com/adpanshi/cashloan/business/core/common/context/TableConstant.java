package com.adpanshi.cashloan.business.core.common.context;


/**
 * 表名/表字段枚举定义
 *
 * @version 1.0
 * @created 2014年9月23日 下午2:17:20
 */
public class TableConstant {

    public enum ClSaasALipayCashNow{
        /**表名*/
        TABLE_NAME("cl_saas_alipay_cash_now"),
        /**amount*/
        CLOUMN_AMOUNT("amount");

        private String code;

        ClSaasALipayCashNow(String code){
            this.code=code;
        }
        public String getCode() {
            return code;
        }

    }
}
