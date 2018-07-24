package com.adpanshi.cashloan.business.cl.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author wangquantao
 * 将下划线的属性转换成驼峰式的属性
 *
 */
public class SwitchMapkeyUtils {
	 private static Pattern linePattern = Pattern.compile("_(\\w)");  
	 private static Pattern humpPattern = Pattern.compile("[A-Z]");
     /**下划线转驼峰*/  
     public static String lineToHump(String str){  
         str = str.toLowerCase();  
         Matcher matcher = linePattern.matcher(str);  
         StringBuffer sb = new StringBuffer();  
         while(matcher.find()){  
             matcher.appendReplacement(sb, matcher.group(1).toUpperCase());  
         }  
         matcher.appendTail(sb);  
         return sb.toString();  
     }  
     /**驼峰转下划线(简单写法，效率低于{@link #humpToLine2(String)})*/  
     public static String humpToLine(String str){  
         return str.replaceAll("[A-Z]", "_$0").toLowerCase();  
     }    
     /**驼峰转下划线,效率比上面高*/  
     public static String humpToLine2(String str){  
         Matcher matcher = humpPattern.matcher(str);  
         StringBuffer sb = new StringBuffer();  
         while(matcher.find()){  
             matcher.appendReplacement(sb, "_"+matcher.group(0).toLowerCase());  
         }  
         matcher.appendTail(sb);  
         return sb.toString();  
     }  
	/**
	 * 反射效率比较低
	 */
	public static void setEntityValue(Map<String,Object> map,Object oo) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		for (String str : map.keySet()) {
		   String[] tempArray = str.split("_");
		   String functionName = "set";
		   String temp = "";
		   for(int i=0;i<tempArray.length;i++){
			   tempArray[i]=Character.toUpperCase(tempArray[i].charAt(0))+tempArray[i].substring(1, tempArray[i].length());
			   temp+=tempArray[i];
			   
		   }
		   functionName +=temp;
		   Method method =  oo.getClass().getMethod(functionName, String.class);
		   method.invoke(oo, map.get(str));
		   System.out.println(functionName);
		}
	}
	
	/**
	 * Map中将下划线转成驼峰key格式转换
	 *
	 */
	
/*	public static Map<String,Object> setEntityValue(Map<String,Object> map) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		for (String str : map.keySet()) {
		   String[] tempArray = str.split("_");
		   String functionName = "";
		   String temp = "";
		   for(int i=0;i<tempArray.length;i++){
			   if(i!=0)
				   tempArray[i]=Character.toUpperCase(tempArray[i].charAt(0))+tempArray[i].substring(1, tempArray[i].length());
			   temp+=tempArray[i];
			   
		   }
		   functionName +=temp;
		   map.put(functionName, map.get(str));
		   map.remove(str);
		}
		return map;
	}*/
	
	/**
	 * Map中将驼峰转成下划线key格式转换
	 *
	 */
	public static Map<String,Object> changeMapKey(Map<String,String> map) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Map<String,Object> result = new HashMap<String,Object>();
		for (String str : map.keySet()) {
			String mapKey = humpToLine2(str);
			result.put(mapKey, map.get(str));
		}
		return result;
	}
	
	
	class Test{
		private String aaaTest;

		public String getAaaTest() {
			return aaaTest;
		}

		public void setAaaTest(String aaaTest) {
			this.aaaTest = aaaTest;
		}

	}
	public static void main(String[] args) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Map<String,String> map = new HashMap<String,String>();
		map.put("aaaTestBbb", "123");
		map.put("aaTestBbb", "123");
		/*Test test = new SetEntityValueUtils().new Test();
		long start1 = System.currentTimeMillis();
		for(int i=0;i<10000;i++){
			test.setAaaTest("123");
		}
		long end1 = System.currentTimeMillis();
		System.out.println(end1-start1+"----normalSet-----");
		long start2 = System.currentTimeMillis();
		for(int j=0;j<10000;j++)
			setEntityValue(map,test);
		long end2 = System.currentTimeMillis();
		System.out.println(end2-start2+"-----invoke------");
		System.out.println(test.getAaaTest());*/
		Map<String,Object>  test = changeMapKey(map);
		System.out.println(test);
		/*String lineToHump = lineToHump("f_parent_no_leader");  
        System.out.println(lineToHump);//fParentNoLeader  
        System.out.println(humpToLine(lineToHump));//f_parent_no_leader  
        System.out.println(humpToLine2(lineToHump));//f_parent_no_leader  
*/		
	}
}
