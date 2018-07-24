package com.adpanshi.cashloan.business.cl.enums;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author nmnl
 * @version 1.1
 * @Description : ManageZhiRongEnum
 * @Date 20180310
 */
public class ZhiRongEnum {

    /**
     * 直融状态
     */
    public enum ZHIRONG_STATE {
        ZHIRONG_SUCCESS(0, "成功"),
        ZHIRONG_FAIL(1, "失败"),
        ZHIRONG_REPART(2, "重复");
        private Integer EnumKey;
        private String EnumValue;

        ZHIRONG_STATE(Integer EnumKey, String EnumValue) {
            this.EnumKey = EnumKey;
            this.EnumValue = EnumValue;
        }

        public Integer EnumKey() {
            return EnumKey;
        }

        public String EnumValue() {
            return EnumValue;
        }

        public static Map<String, Object> EnumValueS() {
            Map<String, Object> outMap = new TreeMap<>();
            ZHIRONG_STATE[] enums = values();
            for (ZHIRONG_STATE e : enums) {
                outMap.put(String.valueOf(e.EnumKey), e.EnumValue);
            }
            return outMap;
        }

        public static ZHIRONG_STATE getByEnumKey(Integer EnumKey) {
            ZHIRONG_STATE[] enums = values();
            for (ZHIRONG_STATE e : enums) {
                if (e.EnumKey.equals(EnumKey)) {
                    return e;
                }
            }
            return null;
        }
    }

    /**
     * 直融接口
     */
    public enum ZHIRONG_INTERFACE {
        ZHIRONG_INTERFACE_REGISTER("register", "注册"),
        ZHIRONG_INTERFACE_LOAN("loan", "借款订单"),
        ZHIRONG_INTERFACE_REPAYMENT("repayment", "还款订单");
        private String EnumKey;
        private String EnumValue;

        ZHIRONG_INTERFACE(String EnumKey, String EnumValue) {
            this.EnumKey = EnumKey;
            this.EnumValue = EnumValue;
        }

        public String EnumKey() {
            return EnumKey;
        }

        public String EnumValue() {
            return EnumValue;
        }

        public static Map<String, Object> EnumValueS() {
            Map<String, Object> outMap = new TreeMap<>();
            ZHIRONG_INTERFACE[] enums = values();
            for (ZHIRONG_INTERFACE e : enums) {
                outMap.put(e.EnumKey, e.EnumValue);
            }
            return outMap;
        }

        public static ZHIRONG_INTERFACE getByEnumKey(String EnumKey) {
            ZHIRONG_INTERFACE[] enums = values();
            for (ZHIRONG_INTERFACE e : enums) {
                if (e.EnumKey.equals(EnumKey)) {
                    return e;
                }
            }
            return null;
        }
    }

}

