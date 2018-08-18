package com.adpanshi.cashloan.core.common.util;

import com.adpanshi.cashloan.core.common.enums.ICommonEnum;

import java.util.ArrayList;
import java.util.List;

/***
 ** @category 枚举工具类...
 ** @author qing.yunhui
 ** @email: qingyunhui@smoney.cc
 ** @createTime: 2017年9月15日下午2:53:53
 **/
public class EnumUtil {
	
	/**
	 * 根据给定code获取name
	 * @param clzs  枚举类(该枚举类必须实现ICommonEnum接口)
	 * @param code 枚举code
	 * @return String 枚举name
	 * */
	public static String getNameByCode(Class<?> clzs,Object code){
		ICommonEnum[] commonEnums=getICommonEnums(clzs);
		if(null==commonEnums) {return null;}
		for(ICommonEnum ienum:commonEnums){
			if(ienum.getCode().equals(String.valueOf(code))){
				return ienum.getName();
			}
		}
		return null;
	}
	
	/**
	 * 根据给定value获取name
	 * @param clzs  枚举类(该枚举类必须实现ICommonEnum接口)
	 * @param key 枚举key
	 * @return String 枚举name
	 * */
	public static String getNameByKey(Class<?> clzs,Integer key){
		ICommonEnum[] commonEnums=getICommonEnums(clzs);
		if(null==commonEnums) return null;
		for(ICommonEnum ienum:commonEnums){
			if(ienum.getKey().intValue()==key.intValue()){
				return ienum.getName();
			}
		}
		return null;
	}
	
	public static String getNameByKey(String clzs,Integer key){
		Class<?> claz=null;
		try {
			claz = Class.forName(clzs);
			ICommonEnum[] commonEnums=getICommonEnums(claz);
			if(null==commonEnums) return null;
			for(ICommonEnum ienum:commonEnums){
				if(ienum.getKey().intValue()==key.intValue()){
					return ienum.getName();
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * <p>根据给定条件查询</p>
	 * @param clzs
	 * @param key
	 * @return 
	 * */
	public static ICommonEnum getEnumByKey(Class<?> clzs,Integer key){
		ICommonEnum[] commonEnums=getICommonEnums(clzs);
		if(null==commonEnums) return null;
		for(ICommonEnum ienum:commonEnums){
			if(ienum.getKey().intValue()==key.intValue()){
				return ienum;
			}
		}
		return null;
	}
	
	/**
	 * 根据给定name获取code
	 * @param clzs  枚举类 (该枚举类必须实现ICommonEnum接口)
	 * @param name 枚举name
	 * @return String 枚举code
	 * */
	public static String getCodeByName(Class<?> clzs,String name){
		ICommonEnum[] commonEnums=getICommonEnums(clzs);
		if(null==commonEnums) return null;
		for(ICommonEnum ienum:commonEnums){
			if(ienum.getName().equals(name)){
				return ienum.getCode();
			}
		}
		return null;
	}
	
	/**
	 * 根据给定name获取code
	 * @param clzs  枚举类 (该枚举类必须实现ICommonEnum接口)
	 * @param key 枚举key
	 * @return String 枚举code
	 * */
	public static String getCodeByKey(Class<?> clzs,Integer key){
		ICommonEnum[] commonEnums=getICommonEnums(clzs);
		if(null==commonEnums) return null;
		for(ICommonEnum ienum:commonEnums){
			if(ienum.getKey().equals(key)){
				return ienum.getCode();
			}
		}
		return null;
	}
	
	public static Integer getKeyByCode(Class<?> clzs,String code){
		ICommonEnum[] commonEnums=getICommonEnums(clzs);
		if(null==commonEnums) return null;
		for(ICommonEnum ienum:commonEnums){
			if(ienum.getCode().equals(code)){
				return ienum.getKey();
			}
		}
		return null;
	}
	
	/**
	 * 根据给定name获取key
	 * @param clzs  枚举类 (该枚举类必须实现ICommonEnum接口)
	 * @param name 枚举name
	 * @return String 枚举key
	 * */
	public static Integer getKeyByName(Class<?> clzs,String name){
		ICommonEnum[] commonEnums=getICommonEnums(clzs);
		if(null==commonEnums) return null;
		for(ICommonEnum ienum:commonEnums){
			if(ienum.getName().equals(name)){
				return ienum.getKey();
			}
		}
		return null;
	}
	
	/**
	 * <p>根据给定枚举类得到该枚举类所有的keys集合</p>
	 * @param clzs
	 * @return 
	 * */
	public static List<String> getCodesByClz(Class<?> clzs){
		return getCodesByClz(clzs, null);
	}
	
	/**
	 * <p>根据给定枚举类得到该枚举类所有的keys集合</p>
	 * @param clzs
	 * @param suffix 待拼接的后缀
	 * @return 
	 * */
	public static List<String> getCodesByClz(Class<?> clzs,String suffix){
		ICommonEnum[] enums=getICommonEnums(clzs);
		List<String> result=new ArrayList<>();
		for(ICommonEnum em:enums){
			result.add(em.getCode()+suffix);
		}
		return result;
	}
	
	/**
	 * <p>根据给定枚举类得到该枚举类所有的code集合</p>
	 * @param clzs
	 * @return 
	 * */
	public static List<Integer> getKeysByClz(Class<?> clzs){
		ICommonEnum[] enums=getICommonEnums(clzs);
		List<Integer> result=new ArrayList<>();
		for(ICommonEnum em:enums){
			result.add(em.getKey());
		}
		return result;
	}
	
	//----------------------------->  private method <-----------------------------
	
	public static ICommonEnum[] getICommonEnums(Class<?> clzs){
		ICommonEnum[] commonEnums=null;
		try {
			commonEnums=(ICommonEnum[]) getEnumConstants(clzs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return commonEnums;
	}
	
	private static Object[] getEnumConstants(Class<?> clzs){
		Object[] objs=null;
		if(clzs.isEnum()){
			objs=clzs.getEnumConstants();
		}
		return objs;
	}
}
