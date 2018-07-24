package com.adpanshi.cashloan.business.cl.constant;

/**
 * @author yecy
 * @date 2018/1/24 14:29
 */
public class YinChengConstant {
    private YinChengConstant() {
    }


    public static final String PACKAGE_ID = "package_id";
    public static final String RESULT = "result";
    public static final String MSG = "msg";
    public static final String DATA = "data";

    public static final String CODE_SUCCESS = "0000"; //成功
    public static final String CODE_FAIL_JSON = "0001"; //json解析错误
    public static final String CODE_FAIL_PARAM = "0002"; //参数异常
    public static final String CODE_FAIL_ORDERNO_REPEAT = "0003"; //订单重复或已放款
    public static final String CODE_FAIL_ORDERNO_MISS = "0004";//订单状态不为待放款，或不存在

    public static final String CODE_FAIL_OTHER = "1000";//其他异常

    /**
     * 银程中相应的附件对应的字段名称
     */
    public static final String AGREEMENT = "agreement";//租房合同 (租房详情)
    public static final String PROOF_OF_EARNINGS = "proof_of_earnings";//收入证明
    public static final String HOUSE_PROPERTY_PROOF = "house_property_proof";//房产证明(自有房产)
    public static final String CONSUMPTION_BILL = "consumption_bill";//消费账单
    public static final String BUSINESS = "business";//营业执照
    public static final String TAX_REGISTRATION = "tax_registration";//税务登记证
    public static final String HYGIENE = "hygiene";//卫生许可证
    public static final String TOBACCO = "tobacco";//烟草专卖许可证
    public static final String PURCHASE_HOUSE = "purchase_house";//购房合同或购房证明(住房家装)
    public static final String RECEIPT = "receipt";//水电开户单据或门牌号或装修合同
    public static final String OTHER = "other";//其他场景附件

}
