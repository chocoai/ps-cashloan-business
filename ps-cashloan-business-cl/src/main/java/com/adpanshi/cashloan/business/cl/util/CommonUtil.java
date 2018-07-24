package com.adpanshi.cashloan.business.cl.util;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtil {

	public static String blankShrink(String first) {
		Pattern PATTERN_BLANK = Pattern.compile("\\s+");
		Matcher matcher = PATTERN_BLANK.matcher(first);
		return matcher.replaceAll(" ");
	}

	public static String getTransText(String text, String[] key, String[] value) {
		int len = key.length;
		if (len == value.length) {
			for (int i = 0; i < len; i++) {
				text = text.replace(key[i], value[i]);
			}
		}else{
			try {
				throw new Exception("键值数目不匹配");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return text;
	}

	/**
	 * 生成随机盐
	 * 
	 * @param num
	 *            生成位数(num<= 8)
	 * @return
	 */
	public static String getRandom(int num) {
		Random rdm = new Random();
		return Integer.toHexString(rdm.nextInt()).substring(0, num);
	}

	public static void main(String[] args) {
		System.out.println(getRandom(7));
		String test = "www.baodu.com?dfdf=123.txt";
		System.out.println(test.substring(0, test.indexOf("?")));
		String[] key = {"ds","cc"};
		String[] value = {"1","99","dd"};
		String text = "select×from t where ds=@ds and cc = @cc";
		System.out.println(getTransText(text,key,value));
	}
}
