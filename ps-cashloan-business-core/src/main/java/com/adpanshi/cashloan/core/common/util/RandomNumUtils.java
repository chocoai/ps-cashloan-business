package com.adpanshi.cashloan.core.common.util;

import java.util.Random;

/**
 * @author 8452
 */
public class RandomNumUtils {

	/**
	 * 生成任意位数的随机数
	 * @param digCount
	 * @return
	 */
	public static String createRandomNum(int digCount) {
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder(digCount);
		for (int i = 0; i < digCount; i++) {
			sb.append((char) ('0' + rnd.nextInt(10)));
		}
		return sb.toString();
	}

	/**
	 * 生成任意位数包含字母或数字的随机数
	 * @param digCount
	 * @return
	 */
	public static String createRandomNumAndChar(int digCount){
		 String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";   
		    Random random = new Random();   
		    StringBuffer sb = new StringBuffer();   
		    for (int i = 0; i < digCount; i++) {   
		        int number = random.nextInt(base.length());   
		        sb.append(base.charAt(number));   
		    }   
		    return sb.toString();  
	}
	
	/**
	 * num 的概率返回true  num = 0.1 10%概率
	 * @param num
	 */
	public static Boolean getBoolByChance(double num){
		Double db = Math.random();
		if(db <  num){
			return true;
		}
		return false;
		
	}
}
