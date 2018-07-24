package com.adpanshi.cashloan.business.core.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 编号生成器

 *
 */
public class NidGenerator {
	public static final Logger logger = LoggerFactory.getLogger(NidGenerator.class);
    protected final  SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    private static int getHashCode() {
        int hashCode = UUID.randomUUID().toString().hashCode();
        if (hashCode < 0) {
            hashCode = -hashCode;
        }
        return hashCode;
    }

    /**
     * orderNo
     * @return
     */
    public synchronized static String getOrderNo(){
        int[] reult = randomCommon(100,999,2);
        String msecStr = DateUtil.dateStr(new Date(),DateUtil.YYMMDD)+System.currentTimeMillis();
        if(null != reult && reult.length >0)
            return msecStr+reult[0];
        return msecStr;
    }
    
    /**
     * 评分卡nid
     * @return
     */
    public static String getCardNid() {
        int hashCode = getHashCode();
        return "CC" + String.format("%011d", hashCode);
    }
    
    /**
     *评分项目nid 
     * @return
     */
    public static String getItemNid() {
        int hashCode = getHashCode();
        return "CI" + String.format("%011d", hashCode);
    }
    
    /**
     * 评分卡因子nid
     * @return
     */
    public static String getFactorNid() {
        int hashCode = getHashCode();
        return "CF" + String.format("%011d", hashCode);
    }
    
    /**
     * 评分参数nid
     * @return
     */
    public static String getParamNid() {
        int hashCode = getHashCode();
        return "CFP" + String.format("%010d", hashCode);
    }

    /**
     * 随机指定范围内N个不重复的数
     * 最简单最基本的方法
     * @param min 指定范围最小值
     * @param max 指定范围最大值
     * @param n 随机数个数
     */
    public static int[] randomCommon(int min, int max, int n){
        if (n > (max - min + 1) || max < min) {
            return null;
        }
        int[] result = new int[n];
        int count = 0;
        while(count < n) {
            int num = (int) (Math.random() * (max - min)) + min;
            boolean flag = true;
            for (int j = 0; j < n; j++) {
                if(num == result[j]){
                    flag = false;
                    break;
                }
            }
            if(flag){
                result[count] = num;
                count++;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(getOrderNo());
    }
    
}
