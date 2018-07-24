package com.adpanshi.cashloan.business.rule.util;


import com.adpanshi.cashloan.business.rule.domain.newRule.Param;
import com.adpanshi.cashloan.business.rule.domain.newRule.RuleConstant;

/**
 * Created by cc on 2017-09-19 10:53.
 */
public class RuleUtil {

    /**
     * 规则if_else匹配
     *
     * @param operator
     * @param param
     * @param value
     * @return
     */
    public static boolean compare(String operator, Param param, int value) {

        int head = param.getHead();
        int tail = 0;
        if (param.getTail()!=null){
            tail = param.getTail();
        }
        switch (operator) {
            case RuleConstant.HIGH:
                return value - head > 0;
            case RuleConstant.HIGH_EQUAL:
                return value - head >= 0;
            case RuleConstant.EQUAL:
                return value - head == 0;
            case RuleConstant.LOW:
                return value - head < 0;
            case RuleConstant.LOW_EQUAL:
                return value - head <= 0;
            case RuleConstant.NOT_EQUAL:
                return value - head != 0;
            case RuleConstant.LOW_LOW:
                return value - head > 0 && value - tail < 0;
            case RuleConstant.LOW_EQUAL_LOW:
                return value - head >= 0 && value - tail < 0;
            case RuleConstant.LOW_LOW_EQUAL:
                return value - head > 0 && value - tail <= 0;
            case RuleConstant.LOW_EQUAL_LOW_EQUAL:
                return value - head >= 0 && value - tail <= 0;
            default:
                return false;
        }
    }


}
