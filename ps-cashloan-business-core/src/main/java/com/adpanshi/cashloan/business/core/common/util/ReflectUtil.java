package com.adpanshi.cashloan.business.core.common.util;

import com.adpanshi.cashloan.business.core.common.exception.BaseRuntimeException;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;

import java.lang.reflect.*;
import java.sql.Date;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 工具类-反射帮助类
 * 

 * @version 1.0
 * @date 2015年11月17日 下午5:39:54 Copyright 粉团网路  All Rights
 *       Reserved
 *
 */
public class ReflectUtil {

	private static final Logger logger = Logger.getLogger(ReflectUtil.class);

	public static List<?> PRIMITIVE_TYPES = Arrays.asList(new Class[] {
			char.class, short.class, byte.class, int.class, long.class,
			float.class, double.class, boolean.class, Short.class, Byte.class,
			Integer.class, Long.class, Float.class, Double.class,
			Boolean.class, String.class, Date.class });

	public static boolean isPrimitive(Class<?> type) {
		return PRIMITIVE_TYPES.contains(type);
	}

	public static Object invokeGetMethod(Class<?> claszz, Object o, String name) {
		Object ret = null;
		try {
			Method method = claszz.getMethod("get"
					+ StringUtil.firstCharUpperCase(name));
			ret = method.invoke(o);
		} catch (NoSuchMethodException | SecurityException
				| IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			logger.error("claszz:" + claszz + ",name:" + name,e);
		}
		return ret;
	}

	public static Object invokeSetMethod(Class<?> claszz, Object o,
			String name, Class<?>[] argTypes, Object[] args) {
		Object ret = null;
		// 非 常量 进行反射
		try {
			if (!checkModifiers(claszz, name)) {
				Method method = claszz.getMethod(
						"set" + StringUtil.firstCharUpperCase(name), argTypes);
				ret = method.invoke(o, args);
			}
		} catch (NoSuchMethodException | SecurityException
				| IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			logger.error("claszz:" + claszz + ",name:" + name + ",argType:"
					+ argTypes + ",args:" + args,e);
		}
		return ret;
	}

	public static Object invokeSetMethod(Class<?> claszz, Object o,
			String name, Class<?> argType, Object args) {
		Object ret = null;
		// 非 常量 进行反射
		try {
			if (!checkModifiers(claszz, name)) {
				Method method = claszz.getMethod(
						"set" + StringUtil.firstCharUpperCase(name),
						new Class[] { argType });
				ret = method.invoke(o, new Object[] { args });
			}
		} catch (NoSuchMethodException | SecurityException
				| IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			logger.error("claszz:" + claszz + ",name:" + name + ",argType:"
					+ argType + ",args:" + args);
		}
		return ret;
	}

	/**
	 * 校验参数类型 目前只校验是否为 常量
	 * 
	 * @param claszz
	 * @param name
	 * @return 常量返回true，非常量返回false
	 */
	private static boolean checkModifiers(Class<?> claszz, String name) {
		try {
			Field field = claszz.getField(name);
			if (isConstant(field.getModifiers())) {
				return true;
			}
		} catch (NoSuchFieldException e) {
			logger.error(e);
			return false;
		}
		return false;
	}

	/**
	 * 是否为常量
	 * 
	 * @param modifiers
	 * @return 常量返回true，非常量返回false
	 */
	private static boolean isConstant(int modifiers) {
		// static 和 final修饰
		if (Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers)) {
			return true;
		}
		return false;
	}

	@SuppressWarnings("rawtypes")
	public static Class<?> getSuperClassGenricType(Class clazz, int index) {
		Type genType = clazz.getGenericSuperclass();

		if (!(genType instanceof ParameterizedType)) {
			return Object.class;
		}

		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		if ((index >= params.length) || (index < 0)) {
			throw new BaseRuntimeException("你输入的索引"
					+ ((index < 0) ? "不能小于0" : "超出了参数的总数"));
		}
		if (!(params[index] instanceof Class)) {
			return Object.class;
		}
		return (Class<?>) params[index];
	}

	@SuppressWarnings("rawtypes")
	public static Class getSuperClassGenricType(Class clazz) {
		return getSuperClassGenricType(clazz, 0);
	}

	public static Method getDeclaredMethod(Object object, String methodName,
			Class<?>... parameterTypes) {
		Method method = null;
		Class<?> clazz = object.getClass();
		while (clazz != Object.class) {
			try {
				method = clazz.getDeclaredMethod(methodName, parameterTypes);
			} catch (Exception e) {
				// 这里异常不能抛出去。
				// 如果这里的异常打印或者往外抛，就不会执行clazz = clazz.getSuperclass(),
				// 最后就不会进入到父类中了
			}
			if (method != null)
				break;
			clazz = clazz.getSuperclass();
		}
		return method;
	}

	public static Map<String, Field> getClassField(Class<?> clazz) {
		Field[] declaredFields = clazz.getDeclaredFields();
		Map<String, Field> fieldMap = new HashMap<String, Field>();
		Map<String, Field> superFieldMap = new HashMap<String, Field>();
		for (Field field : declaredFields) {
			fieldMap.put(field.getName(), field);
		}
		if (clazz.getSuperclass() != null) {
			superFieldMap = getClassField(clazz.getSuperclass());
		}
		fieldMap.putAll(superFieldMap);
		return fieldMap;
	}

	/**
	 * object 属性名称及属性值组装为 Map，再用Map转Json字符串。 组装规则： 只组装String类型，且不为常量的字段，
	 * 组装时若属性值为空或为null，则不加入Json
	 * 
	 * @param object
	 * @return
	 */
	public static String fieldValueToJson(Object object) {
		Class<?> clazz = object.getClass();
		Field[] fss = new Field[0];
		for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
			try {
				Field[] fs = clazz.getDeclaredFields();
				fss = ArrayUtil.concat(fss, fs);
			} catch (Exception e) {
				// 这里异常不能抛出去。
				// 如果这里的异常打印或者往外抛，就不会执行clazz = clazz.getSuperclass(),
				// 最后就不会进入到父类中了
			}
		}

		Map<String, Object> map = new HashMap<String, Object>();
		for (Field f : fss) {
			// 反射对象中String类型，且不为常量的字段
			if (String.class.equals(f.getType())
					&& !isConstant(f.getModifiers())) {
				String fieldName = f.getName();
				Object o = ReflectUtil.invokeGetMethod(f.getDeclaringClass(),
						object, fieldName);
				String value = String.valueOf(o);
				if (value == "") {
					continue;
				}
				map.put(fieldName, value);
			}
		}
		String str = JSONObject.toJSONString(map);
		return str;
	}

	/**
	 * object 属性名称及属性值组装为 Map。 组装规则： 只组装String类型，且不为常量的字段，
	 * 组装时若属性值为空或为null，则不加入Json
	 * 
	 * @param object
	 * @return
	 */
	public static Map<String, Object> fieldValueToMap(Object object) {
		Class<?> clazz = object.getClass();
		Field[] fss = new Field[0];
		for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
			try {
				Field[] fs = clazz.getDeclaredFields();
				fss = ArrayUtil.concat(fss, fs);
			} catch (Exception e) {
				// 这里异常不能抛出去。
				// 如果这里的异常打印或者往外抛，就不会执行clazz = clazz.getSuperclass(),
				// 最后就不会进入到父类中了
			}
		}

		Map<String, Object> map = new HashMap<String, Object>();
		for (Field f : fss) {
			// 反射对象中String类型，且不为常量的字段
			if (String.class.equals(f.getType())
					&& !isConstant(f.getModifiers())) {
				String fieldName = f.getName();
				Object o = ReflectUtil.invokeGetMethod(f.getDeclaringClass(),
						object, fieldName);
				String value = String.valueOf(o);
				if (value == "") {
					continue;
				}
				map.put(fieldName, value);
			}
		}
		return map;
	}

	/**
	 * object 属性名称及属性值组装为String字符串。 组装规则：
	 * field.name1=field.value1&field.name2=field.value2 ...
	 * 
	 * @param object
	 * @return
	 */
	public static String fieldValueToString(Object object) {
		Class<?> clazz = object.getClass();
		Field[] fss = new Field[0];
		for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
			try {
				Field[] fs = clazz.getDeclaredFields();
				fss = ArrayUtil.concat(fss, fs);
			} catch (Exception e) {
				// 这里异常不能抛出去。
				// 如果这里的异常打印或者往外抛，就不会执行clazz = clazz.getSuperclass(),
				// 最后就不会进入到父类中了
			}
		}
		StringBuilder sb = new StringBuilder(50);
		for (Field f : fss) {
			// 反射对象中String类型，且不为常量的字段
			if (String.class.equals(f.getType())
					&& !isConstant(f.getModifiers())) {
				String fieldName = f.getName();
				Object o = ReflectUtil.invokeGetMethod(f.getDeclaringClass(),
						object, fieldName);
				String value = String.valueOf(o);
				if (value == "") {
					continue;
				}
				sb.append(fieldName + "=" + value + "&");
			}
		}
		logger.debug("请求TPP参数：" + sb.toString());
		return sb.toString();
	}

	/**
	 * object 属性名称及属性值组装为 Map，再用Map转Json字符串。 组装规则： 只组装String类型，且不为常量的字段，
	 * 组装时若属性值为空或为null，则不加入Json
	 * 
	 * @param object
	 * @return
	 */
	public static String fieldValueToJson(Object object, String[] paramNames) {
		Map<String, String> map = fieldValueToMap(object, paramNames);
		String str = JSONObject.toJSONString(map);
		return str;
	}

	/**
	 * object 属性名称及属性值组装为 Map，再用Map转Json字符串。 组装规则： 只组装String类型，且不为常量的字段，
	 * 组装时若属性值为空或为null，则不加入Map
	 * 
	 * @param object
	 * @return
	 */
	public static Map<String, String> fieldValueToMap(Object object,
			String[] paramNames) {
		return fieldValueToMap(object, paramNames, true);
	}

	/**
	 * object 属性名称及属性值组装为 Map，再用Map转Json字符串。 组装规则： 只组装String类型，且不为常量的字段，
	 * 组装时若isTrim为true 且 属性值为空或为null，则不加入Map
	 * 
	 * @param object
	 * @param paramNames
	 * @param is_trim
	 * @return
	 */
	public static Map<String, String> fieldValueToMap(Object object,
			String[] paramNames, boolean is_trim) {
		Map<String, String> map = new HashMap<String, String>();
		for (String name : paramNames) {
			Object o = ReflectUtil.invokeGetMethod(object.getClass(), object,
					name);
			String value = String.valueOf(o);
			// 是否去空
			if (is_trim && "".equals(value)) {
				continue;
			}
			map.put(name, value);
		}
		logger.debug("数组反射结果：" + map.toString());
		return map;
	}
	
	/**
	 * 对象到map转换、只有当obj对象中的值不为null或空的时候才会被放到map中。
	 * @param obj 待转换的对象
	 * @return 转换后的map对象。
	 * **/
	public static Map<String,Object> clzToMap(Object obj){
		Map<String,Object> map=new HashMap<String,Object>();
		if(null==obj){
			return map;
		}
		Field[] fields=obj.getClass().getDeclaredFields();	//得到所有已声明的的属性字段。
		for(Field field:fields){
			try {
				//这里过滤掉静态字段、通常entity都实现了序列化！
				int modifiers=field.getModifiers();
				if(Modifier.isStatic(modifiers))continue;
				Method method=obj.getClass().getMethod("get"+StringUtil.firstLetterConvert(field.getName(), true));
				Object object=method.invoke(obj);//method.invoke(obj,new Object[]{})也可;
				if(null!=object && !StringUtil.isEmpty(object)){
					map.put(field.getName(), object);
				}
			} catch (Exception e) {
				logger.error("异常信息请忽略!",e);
			} 
		}
		return map;
	}
	
	/**
	 * <p>根据给定value重置对象的key对应的value</p>
	 * @param obj 待处理的对象
	 * @param keys 待操作的对象
	 * @param suffix 后缀 (非必填)
	 * @param value
	 * @return Object
	 * */
	public static Object setObjectValue(Object obj,List<String>keys,String suffix,List<Object> values){
//		String[] tmpkeys=(String[])ConvertUtils.convert(keys, String.class);
//		Object[] tmpValues=(Object[])ConvertUtils.convert(values, Object.class);
		String[] tmpkeys = keys.toArray(new String[keys.size()]);
		Object[] tmpValues=values.toArray();
		return setObjectValue(obj, tmpkeys, suffix, tmpValues);
	}
	
	/**
	 * <p>根据给定value重置对象的key对应的value</p>
	 * @param obj 待处理的对象
	 * @param keys 待操作的对象
	 * @param suffix 后缀 (非必填)
	 * @param value
	 * @return Object
	 * */
	public static Object setObjectValue(Object obj,String keys[],String suffix,Object[] values){
		if(StringUtil.isEmpty(obj,keys,values)) return obj;
		if(keys.length!=values.length) return obj;
		Field[] fields=obj.getClass().getDeclaredFields();	//得到所有已声明的的属性字段。
		for(Field field:fields){
			try {
				String name=field.getName();
				for(int i=0;i<keys.length;i++){
					if(name.equals(keys[i]+(StringUtil.isBlank(suffix)?"":suffix))){
						field.setAccessible(true);
						Class<?> clz=field.getType();
						if(clz.equals(Date.class)){
							field.set(obj, DateUtil.valueOf(values[i].toString()));
						}else if(clz.equals(Integer.class) || clz.equals(int.class)){
							field.set(obj, Integer.parseInt(values[i].toString()));
						}else if(clz.equals(Long.class) || clz.equals(long.class)){
							field.set(obj, Long.parseLong(values[i].toString()));
						}else if(clz.equals(Double.class) || clz.equals(double.class)){
							field.set(obj, Double.parseDouble(values[i].toString()));
						}else{
							field.set(obj, values[i].toString());
						}
					}
				}
			} catch (Exception e) {
				continue;
			} 
		}
		return obj;
	}
		
	/**
	 * <p>根据给定value重置对象的key对应的value</p>
	 * @param obj 待处理的对象
	 * @param key 待操作的对象
	 * @param suffix 后缀 (非必填)
	 * @param values
	 * @return Object
	 * */
	public static Object setObjectValue(Object obj,String key,String suffix,String value){
		return setObjectValue(obj, key+(StringUtil.isBlank(suffix)?"":suffix), value);
	}
	
	/**
	 * <p>根据给定value重置对象的key对应的value</p>
	 * @param obj 待处理的对象
	 * @param key 待操作的对象
	 * @param value
	 * @return Object
	 * */
	public static Object setObjectValue(Object obj,String key,String value){
		if(StringUtil.isEmpty(obj,key,value)) return obj;
		Field[] fields=obj.getClass().getDeclaredFields();	//得到所有已声明的的属性字段。
		for(Field field:fields){
			try {
				String name=field.getName();
				if(StringUtil.isBlank(value)) continue;
				if(name.equals(key)){
					field.setAccessible(true);
					Class<?> clz=field.getType();
					if(clz.equals(Date.class)){
						field.set(obj, DateUtil.valueOf(value));
					}else if(clz.equals(Integer.class) || clz.equals(int.class)){
						field.set(obj, Integer.parseInt(value));
					}else if(clz.equals(Long.class) || clz.equals(long.class)){
						field.set(obj, Long.parseLong(value));
					}else if(clz.equals(Double.class) || clz.equals(double.class)){
						field.set(obj, Double.parseDouble(value));
					}else{
						field.set(obj, value);
					}
				}
			} catch (Exception e) {
				continue;
			} 
		}
		return obj;
	}
}
