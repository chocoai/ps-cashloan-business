package com.adpanshi.cashloan.business.cl.model.loancity;

import com.adpanshi.cashloan.business.cl.constant.LoanCityConstant;
import com.adpanshi.cashloan.business.cl.util.loancity.PaymentKit;
import com.adpanshi.cashloan.business.core.common.context.Global;
import com.adpanshi.cashloan.business.core.common.exception.BaseException;
import com.adpanshi.cashloan.business.core.common.util.MapUtil;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author yecy
 * @date 2018/1/2 14:37
 */
public class BaseLoanCityModel {
    private String sign;//签名
    private String appid;//商户号
    private String mobile;//用户手机号
    private String requireNo;//需求编号(“req”+15位数字)
    private Integer state;//状态

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRequireNo() {
        return requireNo;
    }

    public void setRequireNo(String requireNo) {
        this.requireNo = requireNo;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }


    public Boolean checkSign() {
        String tempSign = createSign();
        return tempSign.equals(this.sign);
    }

    public void sign() {
        this.sign = createSign();
    }

    private String createSign() {
        Map<String, Object> tempMap;
        try {
            tempMap = MapUtil.convertBean(this);
        } catch (IntrospectionException | IllegalAccessException | InvocationTargetException e) {
            throw new BaseException("Errors in bean conversion to map.");
        }
        Map<String, String> map = new HashMap<>();
        for (Map.Entry<String, Object> param : tempMap.entrySet()) {
            Object value = param.getValue();
            map.put(param.getKey(), value.toString());
        }
        map.remove("sign");

        String secretKey = Global.getValue(LoanCityConstant.LOANCITY_PRIVATE_KEY);
        return PaymentKit.createSignWithSHA256(map, secretKey);
    }

    public enum periodType {
        MONTH("月", "M"),
        DAY("天", "d");

        private String name;
        private String code;

        periodType(String name, String code) {
            this.name = name;
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public String getCode() {
            return code;
        }
    }

    public enum LoanCityStateEnum {
        PUBLISH("已发布", 10),
        RELATED("已关联", 20), //表示消贷同城端需求推送成功
        UNPAID("未支付", 30),
        PAID("已支付", 40),
        CANCELING("撤销中", 50),
        CANCELED("撤销", 60),
        UNPAY_EXPIRED("支付前已过期", 70),
        PAYED_EXPIRED("支付后已过期", 80),
        NOTPASS("未通过", 90),
        COMPLETED("已完成", 100);

        private String name;
        private Integer code;

        LoanCityStateEnum(String name, Integer code) {
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


    /*消贷同城接口名称*/
    public enum LoanCityServiceEnum {
        SKIPREQUIRENOTIFY("跳过发布需求直接下单", "skipRequireNotify"),
        GENERATENOTIFY("推送验证码", "generateNotify"),
        NOTPASSNOTIFY("审核失败", "notPassNotify"),
        PASSNOTIFY("放款成功", "passNotify");

        private String name;
        private String code;

        LoanCityServiceEnum(String name, String code) {
            this.name = name;
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public String getCode() {
            return code;
        }

        public static Map<String, String> toMap() {
            Map<String, String> map = new TreeMap<>();
            LoanCityServiceEnum[] values = LoanCityServiceEnum.values();
            for (LoanCityServiceEnum value : values) {
                map.put(value.getCode(), value.getName());
            }
            return map;
        }
    }
}
