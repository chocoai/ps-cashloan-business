package com.adpanshi.cashloan.core.common.util;

import com.adpanshi.cashloan.core.common.context.Global;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 工具类-字符串处理
 * 

 * @version 2.0
 * @since 2014年1月28日
 */
public final class StringUtil extends tool.util.StringUtil{
	  
    /** 每位加权因子 */
    private static int[] power = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };
	/** 邮件正则表达式 */
	private static Pattern EMAIL_PATTERN = Pattern.compile("^([a-z0-9A-Z]+[-_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
	/** 数字字符串正则表达式 */
	private static Pattern NUMBER_PATTERN = Pattern.compile("^\\d*$");
	/** 身份证正则表达式(15位) */
	private static Pattern ID_CARD_15_PATTERN = Pattern.compile("^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$");
	/** 身份证正则表达式(18位) */
	private static Pattern ID_CARD_18_PATTERN = Pattern.compile("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$");
	/** 印度手机号长度 */
	private static int IN_PHONE_LENGTH = 10;
	/** 中国手机号长度 */
	private static int CN_PHONE_LENGTH = 11;
	/** 印度Aadhaar号码长度 */
	private static int AADHAAR_ID_LENGTH = 12;
  
	/**
	 * 构造函数
	 */
	private StringUtil() {

	}

	/**
	 * 判断输入的手机号码是否有效
	 *
	 * @param str 手机号码
	 * @return 检验结果（true：有效 false：无效）
	 */
	public static boolean isPhone(String str) {
		if(str == null){
			return false;
		}
		Pattern pattern = Pattern.compile(Global.getValue("phone_regex"));
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}

	/**
	 * 判断邮箱是否有效
	 * @param str 邮箱
	 * @return 检验结果（true：有效 false：无效）
	 */
	public static boolean isMail(String str) {
		if(str == null){
			return false;
		}
		Matcher matcher = EMAIL_PATTERN.matcher(str);
		boolean isMatched = matcher.matches();
		return isMatched;
	}

	/**
	 * 判断输入的身份证号码是否有效
	 *
	 * @param str 身份证号码
	 * @return 检验结果（true：有效 false：无效）
	 */
	public static boolean isAadhaarId(String str) {
		if(str == null || str.length() != AADHAAR_ID_LENGTH){
			return false;
		}
		Matcher matcher = NUMBER_PATTERN.matcher(str);
		return matcher.matches();
	}

	/**
	 * 判断输入的身份证号码是否有效
	 * 
	 * @param str 身份证号码
	 * @return 检验结果（true：有效 false：无效）
	 */
	public static boolean isCard(String str) {
		Matcher matcher1 = ID_CARD_15_PATTERN.matcher(str);
		Matcher matcher2 = ID_CARD_18_PATTERN.matcher(str);
		boolean isMatched = matcher1.matches() || matcher2.matches();
		return isMatched;
	}
	
	/**
	 * 根据身份证Id获取性别
	 * 
	 * @param cardId
	 * @return 性别: '男' / '女'
	 */
	public static String getSex(String cardId) {
		int sexNum;
		// 15位的最后一位代表性别，18位的第17位代表性别，奇数为男，偶数为女
		if (cardId.length() == 15) {
			sexNum = cardId.charAt(cardId.length() - 1);
		} else {
			sexNum = cardId.charAt(cardId.length() - 2);
		}

		if (sexNum % 2 == 1) {
			return "男";
		} else {
			return "女";
		}
	}
	
    public static String toString(String separate,int...objs){
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < objs.length; i++) {
			if (i > 0) {
				sb.append(separate);
			}
			sb.append(objs[i]);
        }
        return sb.toString();
    }


	public static String toStringArray(Object... list){
		StringBuilder sb=new StringBuilder();
		int index=0;

		for (Object o : list) {
			if(index>0){sb.append(",");}
			sb.append(o.toString());
			index++;
		}
		return sb.toString();
	}

	@SuppressWarnings("rawtypes")
	public static String toString(Collection list){
		return toString(list,",");
	}
	
    @SuppressWarnings("rawtypes")
	public static String toString(Collection list,String delim){
        StringBuilder sb=new StringBuilder();
		int index=0;

		for (Object o : list) {
			if(index>0){sb.append(delim);}
			sb.append(o.toString());
			index++;
		}
        return sb.toString();
    }

    public static String getRelativePath(File file,File parentFile){
        return file.getAbsolutePath().replaceFirst("^\\Q"+parentFile.getAbsolutePath()+"\\E","").replace("\\","/");
    }
    
    @SuppressWarnings("deprecation")
	public static String getFileUri(HttpServletRequest request, File file){
        String pre=request.getRealPath("/");
        String fullpath=file.getAbsolutePath();
        return fullpath.replace(pre.replaceFirst("[\\\\/]$",""),"").replace("\\","/");
    };
    
    public static String getRepairedFileUri(String fullpath){
        return fullpath.replaceFirst("[\\\\/]$","").replace("\\","/").replace("//","/");
    };
	
    /**
     * 格式化金额数字为千分位显示； 
     * @param str
     * @return
     */
	public static String fmtMicrometer(String str) {
		DecimalFormat df;
		if (str.indexOf(".") > -1) {
			if (str.length() - str.indexOf(".") - 1 == 0) {
				df = new DecimalFormat("###,##0.");
			} else if (str.length() - str.indexOf(".") - 1 == 1) {
				df = new DecimalFormat("###,##0.0");
			} else {
				df = new DecimalFormat("###,##0.00");
			}
		} else {
			df = new DecimalFormat("###,##0");
		}
		double number = Double.parseDouble(str);
		return df.format(number);
	}

	/**
	 * 根据身份证获取星座
	 * @param idNo
	 * @return
	 * @throws ParseException
	 */
	public static String getConstellation(String idNo) throws ParseException {
		String constellation="";
		if(idNo != null && idNo.length()==15){
			idNo=convertIdcarBy15bit(idNo);
		}
		if (idNo != null && idNo.length() == 18) {
			int month=Integer.valueOf(idNo.substring(10,12));
			int day=Integer.valueOf(idNo.substring(12,14));
			constellation = DateUtil.getConstellation(month,day);
		}
		return constellation;
	}

	/**
	 * 根据出生日日期获取年龄
	 * @param dateBirthday
	 * @return
	 * @throws ParseException
	 */
	public static Integer getAgeByDOB(String dateBirthday) throws ParseException {
		int year = DateUtil.daysBetween(DateUtil.valueOf(dateBirthday,DateUtil.DATEFORMAT_STR_012),DateUtil.getNow())/365;
		return year;
	}

	/**
	 * 根据身份证获取年龄
	 * @param idNo
	 * @return
	 * @throws ParseException
	 */
	public static Integer getAge(String idNo) throws ParseException {
        String dates;
        if(idNo != null && idNo.length()==15){
        	idNo=convertIdcarBy15bit(idNo);
        }
        if (idNo != null && idNo.length() == 18) {
            dates = idNo.substring(6, 14);
            int year = DateUtil.daysBetween(DateUtil.valueOf(dates,DateUtil.DATEFORMAT_STR_012),DateUtil.getNow())/365;
            return year;
        }
        return 0;
	}
	 /** 
     * 将15位的身份证转成18位身份证 
     *  
     * @param idcard 
     * @return 
	 * @throws ParseException 
     */  
    public static String convertIdcarBy15bit(String idcard) throws ParseException {  
        String idcard17;  
        // 非15位身份证  
        if (idcard.length() != 15) {  
            return null;  
        }  
  
        if (isDigital(idcard)) {  
            // 获取出生年月日  
            String birthday = idcard.substring(6, 12);  
            Date birthdate = new SimpleDateFormat("yyMMdd").parse(birthday);  
            Calendar cday = Calendar.getInstance();  
            cday.setTime(birthdate);  
            String year = String.valueOf(cday.get(Calendar.YEAR));  
  
            idcard17 = idcard.substring(0, 6) + year + idcard.substring(8);  
  
            char c[] = idcard17.toCharArray();
            String checkCode;  
  
            if (null != c) {
//                int bit[] = new int[idcard17.length()];
            	int bit[];
                // 将字符数组转为整型数组  
                bit = converCharToInt(c);  
                int sum17 = getPowerSum(bit);  
  
                // 获取和值与11取模得到余数进行校验码  
                checkCode = getCheckCodeBySum(sum17);  
                // 获取不到校验位  
                if (null == checkCode) {  
                    return null;  
                }  
  
                // 将前17位与第18位校验码拼接  
                idcard17 += checkCode;  
            }  
        } else { // 身份证包含数字  
            return null;  
        }  
        return idcard17;  
    } 
    /** 
     * 将和值与11取模得到余数进行校验码判断 
     *
     * @param sum17 
     * @return 校验位 
     */  
    public static String getCheckCodeBySum(int sum17) {  
        String checkCode = null;  
        switch (sum17 % 11) {  
        case 10:  
            checkCode = "2";  
            break;  
        case 9:  
            checkCode = "3";  
            break;  
        case 8:  
            checkCode = "4";  
            break;  
        case 7:  
            checkCode = "5";  
            break;  
        case 6:  
            checkCode = "6";  
            break;  
        case 5:  
            checkCode = "7";  
            break;  
        case 4:  
            checkCode = "8";  
            break;  
        case 3:  
            checkCode = "9";  
            break;  
        case 2:  
            checkCode = "x";  
            break;  
        case 1:  
            checkCode = "0";  
            break;  
        case 0:  
            checkCode = "1";  
            break;  
        }  
        return checkCode;  
    }  
  
    /** 
     * 将身份证的每位和对应位的加权因子相乘之后，再得到和值 
     *  
     * @param bit 
     * @return 
     */  
    public static int getPowerSum(int[] bit) {  
  
        int sum = 0;  
  
        if (power.length != bit.length) {  
            return sum;  
        }  
  
        for (int i = 0; i < bit.length; i++) {  
            for (int j = 0; j < power.length; j++) {  
                if (i == j) {  
                    sum = sum + bit[i] * power[j];  
                }  
            }  
        }  
        return sum;  
    } 
    /** 
     * 数字验证 
     *  
     * @param str 
     * @return 
     */  
    public static boolean isDigital(String str) {  
        return str == null || "".equals(str) ? false : str.matches("^[0-9]*$");  
    }  
    /** 
     * 将字符数组转为整型数组 
     *  
     * @param c 
     * @return 
     * @throws NumberFormatException 
     */  
    public static int[] converCharToInt(char[] c) throws NumberFormatException {  
        int[] a = new int[c.length];  
        int k = 0;  
        for (char temp : c) {  
            a[k++] = Integer.parseInt(String.valueOf(temp));  
        }  
        return a;  
    }  
   
    /**
     * 字符转成map类型的
     * 字符串："current=1&mobileType=1&pageSize=10"
     */
    public static Map<String,Object> convertStringToMap(String s){
    	Map<String,Object> m=new HashMap<String,Object>();
    	String[] couple=s.split("&");
    	for (int i = 0; i < couple.length; i++) {
			String[] single=couple[i].split("=");
			if(single.length<2){
				m.put(single[0], "");
				continue;
			}
			m.put(single[0], single[1]);
		}
		return m;
    }
    
    /** 
	 * 比较版本号的大小,前者大则返回一个正数,后者大返回一个负数,相等则返回0 
	 * @param version1 
	 * @param version2 
	 * @return 
	 */  
	public static int compareVersion(String version1, String version2) throws Exception {  
	    if (version1 == null || version2 == null) {  
	        throw new Exception("compareVersion error:illegal params.");  
	    }
		//注意此处为正则匹配，不能用"."；
		String[] versionArray1 = version1.split("\\.");
	    String[] versionArray2 = version2.split("\\.");
	    int idx = 0;
		//取最小长度值
		int minLength = Math.min(versionArray1.length, versionArray2.length);
	    int diff = 0;
		while (idx < minLength
				//先比较长度
				&& (diff = versionArray1[idx].length() - versionArray2[idx].length()) == 0
				//再比较字符
	            && (diff = versionArray1[idx].compareTo(versionArray2[idx])) == 0) {
	        ++idx;
	    }
	    //如果已经分出大小，则直接返回，如果未分出大小，则再比较位数，有子版本的为大；  
	    diff = (diff != 0) ? diff : versionArray1.length - versionArray2.length;  
	    return diff;  
	}
	
	
	
	static int[] DAYS = { 0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };  
	  
	/** 
	 * @param date yyyy-MM-dd HH:mm:ss  / yyyy-MM-dd
	 * @return 
	 */  
	public static boolean isValidDate(String date) {  
	    try {  
	    	int year=0;
	    	int month=0;
	    	int day=0;
	    	if(date.length()>5){
		         year = Integer.parseInt(date.substring(0, 4));  
		        if (year <= 0){  return false;  }
	    	}else{
	    		return false;
	    	} 
	    	if(date.length()>8){
		         month = Integer.parseInt(date.substring(5, 7));  
		        if (month <= 0 || month > 12) {
					return false;
				}
	    	}else{
	    		return false;
	    	} 
	    	if(date.length()>11){   
		         day = Integer.parseInt(date.substring(8, 10));  
		        if (day <= 0 || day > DAYS[month]) {
					return false;
				}
	    	}else{
	    		return false;
	    	}
	        if (month == 2 && day == 29 && !isGregorianLeapYear(year)) {  
		            return false;  
		        } 
	        if(date.length()>20){
		        int hour = Integer.parseInt(date.substring(11, 13));  
		        if (hour < 0 || hour > 23) {
					return false;
				}
		        int minute = Integer.parseInt(date.substring(14, 16));  
		        if (minute < 0 || minute > 59) {
					return false;
				}
		        int second = Integer.parseInt(date.substring(17, 19));  
		        if (second < 0 || second > 59) {
					return false;
				}
	        }
	    } catch (Exception e) {  
	        e.printStackTrace();  
	        return false;  
	    }  
	    return true;  
	}  
	public static final boolean isGregorianLeapYear(int year) {  
	    return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0);  
	}  
	
	/**
	 * <p>判断给定的字符是否为null或空</p>
	 * @param obj 
	 * @return boolean
	 * */
	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(Object obj){
		if(null==obj){
			return true;
		}
		if(obj instanceof Map){
			if(((Map)obj).isEmpty()){
				return true;
			}
		}else if(obj instanceof Collection){
			if(((Collection)obj).isEmpty()){
				return true;
			}
		}
		else if(obj instanceof String){
			if(((String)obj).trim().length()==0){
				return true;
			}
			if("[]".equals(obj)){
				return true;
			}
		}else if(obj.getClass().isArray()){
			if(Array.getLength(obj)==0){
				return true;
			}
		}else{
			return false;
		}
		return false;
	}
    
	/**
	 * <p>判断给定的字符判断是否为null或空</p>
	 * @param objects
	 * @return  boolean
	 * */
	public static boolean isEmpty(Object...objects){
		if(null==objects || objects.length<1){
			return true;
		}
		for(Object obj:objects){
			boolean isEmpty=isEmpty(obj);
			if(isEmpty){ return true;}
		}
		return false;
	}
	
	/**
	 * <p>判断给定的字符判断是否为null或空</p>
	 * @param objects
	 * @return  boolean
	 * */
	public static boolean isNotEmptys(Object ...objects){
		return !isEmpty(objects);
	}
	
	 /**
	  * <p>把str剪切不超过count个长度;</p>
	  * @param str 待剪切的字符
	  * @param count 剪切字符的最大长度;
	  * @param positive 截取的顺序(true:从头往后截取,false:从末尾往前截取)
	  * @return 剪切后的字符串
	  * ***/
	 public static String truncate(String str,int count,boolean positive){
		 if(null==str){ return null;}
		 char[] charArray=null;
		 StringBuffer sbufer=new StringBuffer(str);
		 charArray=positive?sbufer.toString().toCharArray():sbufer.reverse().toString().toCharArray();
		 int len=charArray.length;
		 for(int i=0;i<count && i<len;i++){
			 if(charArray[i]>0XFF){
				 count--;
			 }
		 }
		 count=count>0 ?count:0;
		 int currCount=count<len?count:len;
		 String strData=sbufer.toString();
		 String tmp=strData.substring(0,currCount);
		 if(positive){ return tmp;}
		 sbufer.setLength(0);
		 return sbufer.append(tmp).reverse().toString();
	 }
	 
	 /***
	 * 首字母转换
	 * @param str 待转换的字符
	 * @param isUpper true:首字母转换大写 , false:首字母转换小写
	 * @return 转换后的字符
	 * **/
	public static String firstLetterConvert(String str,boolean isUpper) {
		StringBuffer buff = new StringBuffer(str);
		buff.replace(0, 1, String.valueOf(isUpper?Character.toUpperCase(str.charAt(0)):Character.toLowerCase(str.charAt(0))));
		return buff.toString();
	}
	
	/**
	 * <p>处理字符串(str)中非数字的字符并以指定符号(sysmbol)替换之<p>
	 * <p>比如字符串:1-15～30A31、处理后就变成1,15,30,31</p>
	 * @param str 待处理的字符串
	 * @param symbol 替换符,如果替换符为null或空则默认以逗号替换.
	 * @return 处理后的字符串
	 * */
	public static String handleNonNumericWithReplace(String str,String symbol){
		if(StringUtils.isEmpty(str)){return str;}
		//如果 symbol 为null或空、则符号默认以逗号隔开...
		if(StringUtils.isEmpty(symbol)){ symbol=",";}
		boolean isNumber=isDigital(str);
		if(isNumber){ return str;}
		char[] charsStr=str.toCharArray();
		// 0-9 Acll码:48-57
		for(char chars:charsStr){
			if(chars>=48 && chars<=57){
				//不做处理
			}else{
				str=str.replace(String.valueOf(chars),symbol);
			}
		}
		return str;
	}
	
	/**
	 * <p>数组转String并以指定分割符进行分割</p>
	 * @param objects 待转换的数组对象
	 * @param separator 分割符
	 * @return 转换后的字符串
	 * */
	public static String arrayToString(Object[] objects,String separator){
		if(null==objects||objects.length==0){ return "";}
		return StringUtils.join(objects,separator);
	}
	
	/***
	 * <p>根据给定field进行转换成数据库表字段</p>
	 * <p>java实体字段->数据表字段的转换(比如userName转换后user_name)</p>
	 * @param field 待转换的字段
	 * @return String 转换后的字段
	 * **/
	public static String getFieldName(String field){
		if(StringUtils.isEmpty(field)){return null;}
		/*小写字母a-z 对应数字97-122 , 大写字母A-Z对应数字65-90*/
		char[] chars=field.toCharArray();
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<chars.length;i++){
			if(i==0){
				sb.append(chars[i]);
				continue;
			}
			if((int)chars[i]>=65 && chars[i]<=90){
				sb.append("_").append(chars[i]);
			}else{
				sb.append(chars[i]);
			}
		}
		return sb.toString().toLowerCase();
	}
	
	/***
	 * <p>根据给定field进行转换成数据库表字段</p>
	 * <p>java实体字段->数据表字段的转换(比如userName转换后user_name)</p>
	 * @param entity 待转换的字段
	 * @return String 转换后的字段
	 * **/
	public static String getFieldName(Class<?> entity){
		return getFieldName(entity.getSimpleName());
	}

	public static String jointName(String firstName, String middleName, String lastName){
		String realName;
		if(StringUtil.isNotBlank(lastName)){
			lastName = lastName.trim();
		}
		if(StringUtil.isNotBlank(firstName)){
			firstName = firstName.trim();
		}
		if(StringUtil.isNotBlank(middleName)){
			middleName = middleName.trim();
			realName = firstName+" "+middleName+" "+lastName;
		}else{
			realName = firstName+" "+lastName;
		}
		return realName;
	}

	public static void main(String[]args){
		String str="as2412XD";
		String tmp=handleNonNumericWithReplace(str, "");
		System.out.println("tmp:"+tmp);
	}

}
