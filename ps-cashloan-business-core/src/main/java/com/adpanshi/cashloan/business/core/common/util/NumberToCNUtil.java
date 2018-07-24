package com.adpanshi.cashloan.business.core.common.util;

/**
 * @author yecy
 * @date 2017/12/15 20:55
 */
public class NumberToCNUtil {
    private NumberToCNUtil() {
    }

    private static final String[] hanArr = {"零", "一", "二", "三", "四", "五", "六", "七",
            "八", "九"};
    private static final String[] unitArr = {"十", "百", "千", "万", "十", "白", "千", "亿",
            "十", "百", "千"};

    public static String toHanStr(Integer number) {
        String numStr = String.valueOf(number);
        StringBuilder result = new StringBuilder();
        int numLen = numStr.length();
        for (int i = 0; i < numLen; i++) {
            int num = numStr.charAt(i) - 48;
            if (i != numLen - 1 && num != 0) {
                result.append(hanArr[num]).append(unitArr[numLen - 2 - i]);
                if (number >= 10 && number < 20) {
                    result = new StringBuilder(result.substring(1));
                }
            } else {
                if (!(number >= 10 && number % 10 == 0)) {
                    result.append(hanArr[num]);
                }
            }
        }
        return result.toString();
    }
}
