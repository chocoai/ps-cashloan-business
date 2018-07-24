package com.adpanshi.cashloan.business.core.common.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/***
 ** @category 计算工具类...
 ** @author qing.yunhui
 ** @email: 280672161@qq.com
 ** @createTime: 2017年12月23日上午11:55:22
 **/
public class MathUtil {
	
	/**默认保留二位小数位*/
	public static final String defaultFormat="#0.00";
	
	/**默认保留2位小数*/
	public static final int scale=2;

	/**
	 * <p>根据给定数值进行除法运算并保留scale位小数且小数位后的数字直接舍去</p>
	 * @param arg1 除数
	 * @param arg2 被除数
	 * @param scale 保留小数位
	 * @return 
	 * */
	public static double div(Double arg1,Double arg2,int scale){
		BigDecimal decima1 = new BigDecimal(arg1);
		BigDecimal decima2 = new BigDecimal(arg2);
		BigDecimal result = decima1.divide(decima2,scale, RoundingMode.DOWN);
		return result.doubleValue();
	}
	
	/**
	 * <p>根据给定数值进行相加</p>
	 * @param doubles 
	 * @return double
	 * */
	public static double add(Double ...doubles){
		double total=0;
		for(Double arg:doubles){
			total+=arg;
		}
		return total;
	}
	
	/**
	 * <p>根据给定object、用指定format进行格式化</p>
	 * @param object 待格式化的对象
	 * @param format 用作格式化的模式
	 * @return 
	 * */
	public static String format(Double object,String format){
		DecimalFormat decimalFormat = new DecimalFormat(format);
		if(null==object) object=0d;
		return decimalFormat.format(object);
	}
	
	/**
	 * <p>根据给定object进行格式化默认保留二位小数位</p>
	 * @param object 待格式化的对象
	 * @return 
	 * */
	public static String format(Double object){
		return format(object, defaultFormat);
	}
	
	/**
	 * <p>根据给定数值进行乘法运算并保留scale位小数且小数位后的数字直接舍去</p>
	 * @param arg1 乘数
	 * @param arg2 被乘数
	 * @param scale 保留小数位
	 * @return 
	 * */
	public static double multiply(Double arg1,Double arg2,int scale){
		BigDecimal decima1 = new BigDecimal(arg1);
		BigDecimal decima2 = new BigDecimal(arg2);
		BigDecimal result = decima1.multiply(decima2).setScale(scale, RoundingMode.DOWN);
		return result.doubleValue();
	}
	
	/**
     * <p>判断给定array是否是递增数组</p>
     * @param array 待校验的数组
     * @return 
     * */
    public static boolean isIncreseArray(List<Integer> array){
        return isIncreseArray(array.toArray(new Integer[array.size()]), 0);
    }
	
	/**
     * <p>判断给定array是否是递增数组</p>
     * @param array 待校验的数组
     * @return 
     * */
    public static boolean isIncreseArray(Integer[] array){
        return isIncreseArray(array, 0);
    }
    
    /**
     * <p>逻辑推算-得到值少于curNumber递减的值</p>
     * <p>比如curNumber值为5、则推算后得到的将是:4、3、2、1</p>
     * @param curNumber 待推算的值
     * @return 推算后得到的数组(当返回值为null时、表示未推算到)
     * */
    public static List<Integer> decreasing(int curNumber){
    	int begin=1;
    	if(begin>=curNumber) return null;
    	List<Integer> numbers=new ArrayList<>();
    	decreasing(numbers,curNumber, begin);
    	return numbers;
    }
    
    private static void decreasing(List<Integer> numbers,int curNumber,int begin){
    	if(curNumber!=begin){
    		int number=curNumber-1;
    		numbers.add(number);
    		decreasing(numbers, number, begin);
    	}
    }
    
    private static boolean isIncreseArray(Integer[] array, int begin){
    	return (begin==array.length-1)? true:(array[begin]<array[begin+1] && array[begin]+1==array[begin+1] && isIncreseArray(array,begin+1));
    }
    
}
