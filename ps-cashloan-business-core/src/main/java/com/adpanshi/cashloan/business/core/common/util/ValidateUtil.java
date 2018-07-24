package com.adpanshi.cashloan.business.core.common.util;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 数据校验工具类
 * 

 * @version 2.0
 * @since 2014年1月8日
 */
public class ValidateUtil {
	
	private static Map<String, Object> map;

	/**
	 * 校验用户名 规则：数字与字母组合，字母，汉字，4-16位(?![a-zA-Z]+$)
	 * 
	 * @param user_name
	 * @return
	 */
	public static boolean isUser_name(String user_name) {
		Pattern p = Pattern.compile("^(?![0-9]+$)[0-9A-Za-z\u0391-\uFFE5]{2,15}$");
		Matcher m = p.matcher(user_name);
		return m.matches();
	}
	
	/**
	 * 校验是否中文
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isChinese(String str) {
		Pattern regex = Pattern.compile("[\\u4e00-\\u9fa5]{2,25}");
		Matcher matcher = regex.matcher(str);
		return matcher.matches();
	}

	/**
	 * 校验Email格式
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {
		Pattern regex = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
		Matcher matcher = regex.matcher(email);
		return matcher.matches();
	}

	/**
	 * 校验手机号格式
	 * 
	 * @param phone
	 * @return
	 */
	public static boolean isPhone(String phone) {
		Pattern regex = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
		Matcher matcher = regex.matcher(phone);
		boolean isMatched = matcher.matches();
		return isMatched;
	}

	/**
	 * 校验手机号
	 * 
	 * @param phone
	 * @return
	 */
	public static Map<String, Object> checkPhone(String phone) {
		map = new HashMap<String, Object>();
		map.put("result", false);
		if (StringUtils.isBlank(phone)) {
			map.put("message", "请输入手机号！");
			return map;
		}
		if (!isPhone(phone)) {
			map.put("message", "请输入正确的11位手机号！");
		} else {
			map.put("result", true);
		}
		return map;
	}
}
