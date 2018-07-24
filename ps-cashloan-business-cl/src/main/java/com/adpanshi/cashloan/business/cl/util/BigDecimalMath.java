package com.adpanshi.cashloan.business.cl.util;

import java.math.BigDecimal;

public class BigDecimalMath {
	// 进行加法运算
    public static double add(double d1, double d2){        
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return b1.add(b2).doubleValue();
    }
    public static double add(BigDecimal b1, BigDecimal b2){        
        return b1.add(b2).doubleValue();
    }
    // 进行减法运算
    public static double sub(double d1, double d2){        
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return b1.subtract(b2).doubleValue();
    }
    // 进行乘法运算
    public static double mul(double d1, double d2){        
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return b1.multiply(b2).doubleValue();
    }
    // 进行乘法运算
    public static double mul(Integer i, BigDecimal b){
        BigDecimal bi = new BigDecimal(i);
        return bi.multiply(b).doubleValue();
    }
    // 进行除法运算
    public static double div(double d1,double d2,int len) {
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return b1.divide(b2,len,BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    // 进行除法运算
    public static double div(Integer i, double b) {
        BigDecimal b1 = new BigDecimal(i);
        BigDecimal b2 = new BigDecimal(b);
        return b2.divide(b1,BigDecimal.ROUND_HALF_EVEN).doubleValue();
    }
    // 进行四舍五入操作
    public static BigDecimal round(double d,int len) {     
        BigDecimal b1 = new BigDecimal(d);
        BigDecimal b2 = new BigDecimal(1);
        // 任何一个数字除以1都是原数字
        // ROUND_HALF_UP是BigDecimal的一个常量，表示进行四舍五入的操作
        return b1.divide(b2,len,BigDecimal.ROUND_HALF_UP);
    }
    
  //测试
    public static void main(String[] args) {
         System.out.println("加法运算：" +
        		 BigDecimalMath.round(BigDecimalMath.add(10.345,3.333), 2));
         System.out.println("乘法运算：" +
        		 BigDecimalMath.round(BigDecimalMath.mul(10.345,3.333), 2));
         System.out.println("乘法运算：" +
        		 BigDecimalMath.round(BigDecimalMath.mul(103,3.333), 3));
         System.out.println("除法运算：" +
        		 BigDecimalMath.div(10.345, 3.333, 2));
         System.out.println("减法运算：" +
        		 BigDecimalMath.round(BigDecimalMath.sub(10.345,3.333), 2));
         System.out.println("除法运算：" +
        		 BigDecimalMath.div(35, 35.33));
         System.out.println("乘法运算：" +
        		 BigDecimalMath.round(BigDecimalMath.mul(0.35,35.33), 3));
         Integer a = 1;
         Integer b = 1;
         Integer c = 1;
         if(a != b || b != c )
        	 System.out.println("fuck");
     }
}
