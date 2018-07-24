package com.adpanshi.cashloan.business.cl.util;


import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 日期工具类
 * @author M
 */
public class DateFormatUtil {

	/**
	 * 获取日期(Date类型)
	 * @param	format
	 * @return 	format || yyyy-MM-dd
	 */
	public static Date getDate(String format){
		if(StringUtils.isBlank(format))format = "yyyy-MM-dd";
		SimpleDateFormat f = new SimpleDateFormat(format);
		try {
			return f.parse(f.format(new Date()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取日期(Date类型)
	 * @return	yyyy-MM-dd HH:mm:ss
	 */
	public static Date getDateYMDHMS(){
		return getDate("yyyy-MM-dd HH:mm:ss");
	}
	/**
	 * 获取日期(Date类型)
	 * @return	yyyy-MM-dd
	 */
	public static Date getDateYMD(){
		return getDate("yyyy-MM-dd");
	}
	/**
	 * 获取日期(Date类型)
	 * @return	yyyy-MM
	 */
	public static Date getDateYM(){
		return getDate("yyyy-MM");
	}
	/**
	 * 获取日期(Date类型)
	 * @return	yyyy
	 */
	public static Date getDateY(){
		return getDate("yyyy");
	}
	
	/**
	 * 获取日期(String类型)
	 * @return format || yyyy-MM-dd HH:mm:ss
	 */
	public static String getStringDate(String format){
		if(StringUtils.isBlank(format))
			format = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat f = new SimpleDateFormat(format);
		try {
			return f.format(new Date());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取日期(String类型)
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String getStringDateYMDHMS(){
		return getStringDate(null);
	}
	/**
	 * 获取日期(String类型)
	 * @return yyyy-MM-dd
	 */
	public static String getStringDateYMD(){
		return getStringDate("yyyy-MM-dd");
	}
	/**
	 * 获取日期(String类型)
	 * @return yyyy-MM
	 */
	public static String getStringDateYM(){
		return getStringDate("yyyy-MM");
	}	
	
	/**
	 * 将Date型转为String型
	 * @param 	date
	 * @param 	format
	 * @return 	format || null
	 */
	public static String formatDate(Date date, String format){
		if(date==null) return null;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	/**
	 * 将Date型转为String型
	 * @param	date
	 * @return	yyyyMMddHHmmss
	 */
	public static String formatDateTYMDHMS(Date date) {
		return formatDate(date, "yyyyMMddHHmmss");
	}
	/**
	 * 将Date型转为String型
	 * @param	date
	 * @return	yyyy-MM-dd HH:mm:ss
	 */
	public static String formatDateYMDHMS(Date date) {
		return formatDate(date, "yyyy-MM-dd HH:mm:ss");
	}
	/**
	 * 将Date型转为String型
	 * @param	date
	 * @return	yyyy-MM-dd
	 */
	public static String formatDateYMD(Date date) {
		return formatDate(date, "yyyy-MM-dd");
	}
	/**
	 * 将Date型转为String型
	 * @param	date
	 * @return	yyyy-MM
	 */
	public static String formatDateYM(Date date) {
		return formatDate(date, "yyyy-MM");
	}
	/**
	 * 将Date型转为String型
	 * @param	date
	 * @return	yyyy
	 */
	public static String formatDateY(Date date) {
		return formatDate(date, "yyyy");
	}
	
	/**
	 * 将String型转为String型
	 * @param	date
	 * @param	format
	 * @return 	format || null
	 */
	public static String formatStringDate(String date, String format){
		if(StringUtils.isNotBlank(date)){
			try {
				String dateformat = format.replace("年", "-").replace("月", "-").replace("日", "");
				SimpleDateFormat sdf = new SimpleDateFormat(dateformat);
				Date d = sdf.parse(date);
				sdf = new SimpleDateFormat(format);
				return sdf.format(d);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	/**
	 * 将String型转为String型
	 * @param	date
	 * @return 	yyyy-MM-dd HH:mm:ss
	 */
	public static String formatStringDateYMDHMS(String date) {
		return formatStringDate(date, "yyyy-MM-dd HH:mm:ss");
	}
	/**
	 * 将String型转为String型
	 * @param	date
	 * @return 	yyyy-MM-dd HH:mm
	 */
	public static String formatStringDateYMDHM(String date) {
		return formatStringDate(date, "yyyy-MM-dd HH:mm");
	}
	/**
	 * 将String型转为String型
	 * @param	date
	 * @return 	yyyy-MM-dd
	 */
	public static String formatStringDateYMD(String date) {
		return formatStringDate(date, "yyyy-MM-dd");
	}
	/**
	 * 将String型转为String型
	 * @param	date
	 * @return 	yyyy-MM
	 */
	public static String formatStringDateYM(String date) {
		return formatStringDate(date, "yyyy-MM");
	}
	/**
	 * 将String型转为String型
	 * @param	date
	 * @return 	yyyy
	 */
	public static String formatStringDateY(String date) {
		return formatStringDate(date, "yyyy");
	}

	/**
	 * 将String型转为Date型
	 * @param	value
	 * @param	format
	 * @return 	format || yyyy-MM-dd
	 */
	public static Date convertDate(String value, String format){
		if(StringUtils.isBlank(format))format = "yyyy-MM-dd";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		if(StringUtils.isNotBlank(value)){
			try {
				return sdf.parse(value.toString());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} 
		return null;
	}
	
	/**
	 * 将String型转为Date型
	 * @param	value
	 * @return 	yyyy-MM-dd HH:mm:ss
	 */
	public static Date convertDateYMDHMS(String value){
		return convertDate(value, "yyyy-MM-dd HH:mm:ss");
	}
	/**
	 * 将String型转为Date型
	 * @param	value
	 * @return 	yyyy-MM-dd HH:mm
	 */
	public static Date convertDateYMDHM(String value){
		return convertDate(value, "yyyy-MM-dd HH:mm");
	}
	/**
	 * 将String型转为Date型
	 * @param	value
	 * @return 	yyyy-MM-dd
	 */
	public static Date convertDateYMD(String value){
		return convertDate(value, "yyyy-MM-dd");
	}
	/**
	 * 将String型转为Date型
	 * @param	value
	 * @return 	yyyy-MM
	 */
	public static Date convertDateYM(String value){
		return convertDate(value, "yyyy-MM");
	}
	/**
	 * 将String型转为Date型
	 * @param	value
	 * @return 	yyyy
	 */
	public static Date convertDateY(String value){
		return convertDate(value, "yyyy");
	}
	
	/**
	 * 将Date型转为Date型
	 * @param	date
	 * @param	format
	 * @return 	format || yyyy-MM-dd
	 */
	public static Date convertDateToDate(Date date, String format) {
		if(StringUtils.isBlank(format))format = "yyyy-MM-dd";
		if(date != null){
			try {
				SimpleDateFormat sdf = new SimpleDateFormat(format);
				return sdf.parse(sdf.format(date));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} 
		return null;
	}
	/**
	 * 将Date型转为Date型
	 * @param	date
	 * @return 	yyyy-MM-dd HH:mm:ss
	 */
	public static Date convertDateToDateYMDHMS(Date date) {
		return convertDateToDate(date, "yyyy-MM-dd HH:mm:ss");
	}
	/**
	 * 将Date型转为Date型
	 * @param	date
	 * @return 	yyyy-MM-dd
	 */
	public static Date convertDateToDateYMD(Date date) {
		return convertDateToDate(date, "yyyy-MM-dd");
	}
	/**
	 * 将Date型转为Date型
	 * @param	date
	 * @return 	yyyy-MM
	 */
	public static Date convertDateToDateYM(Date date) {
		return convertDateToDate(date, "yyyy-MM");
	}
	/**
	 * 将Date型转为Date型
	 * @param	date
	 * @return 	yyyy
	 */
	public static Date convertDateToDateY(Date date) {
		return convertDateToDate(date, "yyyy");
	}
	
	/**
	 * 获取两个日期相差的天数
	 * @param beginDate
	 * @param endDate
	 */
	public static long getDifferDay(Date beginDate,Date endDate){
		long day = (beginDate.getTime()-endDate.getTime())/(24*60*60*1000)>0 ? (beginDate.getTime()-endDate.getTime())/(24*60*60*1000): 
		   (endDate.getTime()-beginDate.getTime())/(24*60*60*1000); 
		return day;
	}

	/**
	 * 获取两个日期相差的月份
	 * @param beginDate
	 * @param endDate
	 */
	public static int getMonthSpace(Date beginDate,Date endDate){
		int result = 0;
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(beginDate);
		c2.setTime(endDate);
		if(c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR) > 0){
			result = 12*(c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR) - 1);
			result += (12 - c1.get(Calendar.MONTH));
			result += (c2.get(Calendar.MONTH) + 1);
		}else{
			result = c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);
		}
		return result;
	}	
	
	/**
	 * 获取年份
	 * @return	String型
	 */
	public static String getYear(){
		return getIntYear() + "";
	}
	/**
	 * 获取年份
	 */
	public static Integer getIntYear(){
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.YEAR);
	}
	
	/**
	 * 从date型时间中获取年份
	 * @param 	date
	 */
	public static String getYear(Date date){
		if(date!=null){
			return getIntYear() + "";
		}
		return null;
	}
	/**
	 * 从date型时间中获取年份
	 * @param 	date
	 */
	public static Integer getIntYear(Date date){
		if(date!=null){
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			return cal.get(Calendar.YEAR);
		}
		return null;
	}
	/**
	 * 从string型时间中获取年份
	 * @param 	date
	 */
	public static Integer getIntYear(String date){
		if(date!=null){
			Calendar cal = Calendar.getInstance();
			cal.setTime(convertDateYMD(date));
			return cal.get(Calendar.YEAR);
		}
		return null;
	}
	
	/**
	 * 获取date指定的日期是一个月的第几天
	 * @param 	date
	 */
	public static Integer getDayOfMonth(Date date) {
		return getTime(date, Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获取当前日期 一天的时刻
	 * @param date
	 */
	public static Integer getHourOfDay(Date date) {
		return getTime(date, Calendar.HOUR_OF_DAY);
	}

	/**
	 * 获取指定日期的月份
	 * @param date
	 */
	public static Integer getMonth(Date date) {
		return getTime(date, Calendar.MONTH) + 1;
	}

	/**
	 * 获取时间类别
	 * @param date
	 * @param timeType
	 */
	public static Integer getTime(Date date, int timeType) {
		int rst = -1;
		if (date != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			rst = calendar.get(timeType);
		}
		return rst;
	}

	/**
	 * 减去当前日期小时
	 * @param date
	 * @param hour 要减去的小时数
	 */
	public static Date decreaseDateByHour(Date date, int hour) {
		int dayHour = getHourOfDay(date) - hour;
		return formatDateSetHour(date, dayHour);
	}

	/**
	 * 减去当前日期天数
	 * @param date
	 * @param day	要减去的天数
	 */
	public static Date decreaseDateByDay(Date date, int day) {
		int dayOfMonth = getDayOfMonth(date) - day;
		return formatDateSetDay(date, dayOfMonth);
	}

	/**
	 * 减去当前日期月数
	 * @param date
	 * @param month	要减去的月数
	 */
	public static Date decreaseDateByMonth(Date date, int month) {
		int gMonth = getMonth(date) - month;
		return formatDateSetMonth(date, gMonth);
	}

	/**
	 * 设置日期月份
	 * @param date
	 * @param month
	 */
	public static Date formatDateSetMonth(Date date, int month) {
		if (date != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(Calendar.MONTH, month - 1);
			return calendar.getTime();
		}
		return null;
	}

	/**
	 * 设置日期年份
	 * @param date
	 * @param year
	 */
	public static Date formatDateSetYear(Date date, int year) {
		if (date != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(Calendar.YEAR, year);
			return calendar.getTime();
		}
		return null;
	}

	/**
	 * 设置日期天
	 * @param date
	 * @param day
	 */
	public static Date formatDateSetDay(Date date, int day) {
		if (date != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(Calendar.DAY_OF_MONTH, day);
			return calendar.getTime();
		}
		return null;
	}

	/**
	 * 设置日期小时
	 * @param date
	 * @param hour
	 */
	public static Date formatDateSetHour(Date date, int hour) {
		if (date != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(Calendar.HOUR_OF_DAY, hour);
			return calendar.getTime();
		}
		return null;
	}

	/**
	 * 将一个日期增加(amount为正数)或减少(amount为负数)指定的量
	 * @param date	日期
	 * @param field	要改变的领域
	 * @param amount 数量
	 */
	public static Date add(Date date, int field, int amount){
		if(date != null){
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.add(field, amount);
			return c.getTime();
		}
		return null;
	}

	/**
	 * 将一个日期增加n年
	 * @param date
	 * @param amount	增加的年数
	 */
	public static Date addYear(Date date, int amount) {
		return add(date, Calendar.YEAR, amount);
	}
	/**
	 * 将一个日期增加n月
	 * @param date
	 * @param amount	增加的月数
	 */
	public static Date addMonth(Date date, int amount) {
		return add(date, Calendar.MONTH, amount);
	}
	/**
	 * 将一个日期增加n天
	 * @param date
	 * @param amount	增加的天数
	 */
	public static Date addDay(Date date, int amount) {
		return add(date, Calendar.DATE, amount);
	}
	/**
	 * 将一个日期增加n小时
	 * @param date 格式：yyyy-MM-dd HH:mm:ss
	 * @param amount	增加的小时数
	 */
	public static Date addHour(Date date, int amount){
		return add(date, Calendar.HOUR_OF_DAY, amount);
	}
	/**
	 * 将一个日期增加n分钟
	 * @param date 格式：yyyy-MM-dd HH:mm:ss
	 * @param amount	增加的分钟数
	 */
	public static Date addMinute(Date date, int amount){
		return add(date, Calendar.MINUTE, amount);
	}
	/**
	 * 将一个日期增加n秒
	 * @param date 格式：yyyy-MM-dd HH:mm:ss
	 * @param amount	增加的秒数
	 */
	public static Date addSecond(Date date, int amount){
		return add(date, Calendar.SECOND, amount);
	}
	
	/**
	 * 将一个日期增加n天
	 * @param date
	 * @param n	增加的天数
	 */
	public static String addDay(String date, int n){
		try {
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			c.setTime(format.parse(date));
			c.add(Calendar.DATE, n);
			return format.format(c.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取一周的周一和周日两天
	 */
    public static String[] getMondayAndSunday(String str) {
        String[] result = new String[2];
        Calendar calendar = Calendar.getInstance(); //创建日历实例，默认当前日期           
        SimpleDateFormat sDateFormat =  new SimpleDateFormat("yyyy-MM-dd"); //设置日期格式       
        try {
            Date date = sDateFormat.parse(str);
            calendar.setTime(date);
            int flag = calendar.get(Calendar.DAY_OF_WEEK); //获得礼拜几信息 
            //获得当前礼拜   
            calendar.add(Calendar.DAY_OF_YEAR,  -flag + 2);
            date = calendar.getTime();
            result[0] = sDateFormat.format(date); //礼拜一
            calendar.add(Calendar.DAY_OF_YEAR, 6);
            date = calendar.getTime();
            result[1] = sDateFormat.format(date); //礼拜天
        } catch (ParseException e) {
        	e.printStackTrace();
        }

        return result;
    }	
	
    /**
	 * 获取一周的日期
	 */
    public String[] getWeeks(String str) {
        String[] result = new String[7];
        Calendar calendar = Calendar.getInstance(); //创建日历实例，默认当前日期           
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd"); //设置日期格式       
        try {
            Date date = sDateFormat.parse(str);
            calendar.setTime(date);
            int flag = calendar.get(Calendar.DAY_OF_WEEK); //获得礼拜几信息 
            //获得当前礼拜   
            calendar.add(Calendar.DAY_OF_YEAR, -flag + 2);
            date = calendar.getTime();
            result[0] = sDateFormat.format(date); //礼拜一
            for(int i=0;i<6;i++){
	            calendar.add(Calendar.DAY_OF_YEAR, 1);
	            date = calendar.getTime();
	            result[i+1] = sDateFormat.format(date); //礼拜2...
            }
        } catch (ParseException e) {
        	e.printStackTrace();
        }
        return result;
    }
    
	/**
	 * 根据datestr指定的日期获取星期，星期天用7表示
	 * @param datestr	日期
	 */	
    public static Integer getDayOfWeek(String datestr) {
    	Integer result = 1;
        Calendar calendar = Calendar.getInstance(); //创建日历实例，默认当前日期           
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd"); //设置日期格式       
        try {
            Date date = sDateFormat.parse(datestr);

            calendar.setTime(date);
            int flag = calendar.get(Calendar.DAY_OF_WEEK); //获得礼拜几信息 
            if(flag==1) flag=8;
            result = flag - 1;
        } catch (ParseException e) {
        	e.printStackTrace();
        }       
        return result;
    }
    
    /**
	 * 根据datestr指定的日期获取星期
	 * @param datestr	日期
	 */	
    public static String getDayOfWeekStr(String datestr) {
    	String weeks[] = new String[]{" ", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"};
    	Integer week = getDayOfWeek(datestr);   
        return weeks[week];
    }
    
    /**
	 * 根据datestr指定的日期获取属于当年第几周
	 * @param datestr	日期
	 */	
    public static Integer getWeekOfYear(String datestr){
    	try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date date = format.parse(datestr);
			Calendar calendar = Calendar.getInstance();
			calendar.setFirstDayOfWeek(Calendar.MONDAY);
			calendar.setTime(date);
			return calendar.get(Calendar.WEEK_OF_YEAR);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
    }
    
    /**
     * 以字符串形式返回下周一和下周日的日期
     */
    @SuppressWarnings("deprecation")
	public static String[] nextMonday() throws ParseException {
        String[] str=new String[2];
        Date ks = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String riqi = "";
        if (ks.getDay() != 0) {
            riqi = 
        formatter.format(new Date(ks.getTime() + 1000 * 3600 * 24 * 7));
        } else {
            riqi = formatter.format(ks);
        }
        Calendar as = Calendar.getInstance();
        as.setTime(formatter.parse(riqi));
        as.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);        
        str[0]= formatter.format(as.getTime());
        str[1]=formatter.format(new Date(as.getTime().getTime()+1000*3600*24*6));
        return str;
    }
    /**
     * 获取两个日子之间的月份
     * @param begin	开始日期
     * @param end	结束日期
     * @return Map<yyyy-MM, yyyy年MM月>
     */
    public static Map<String, String>  getBetweenMonth(Date begin, Date end){      
        Map<String, String> dayMap = new LinkedHashMap<String, String>();
        GregorianCalendar gc1 = new GregorianCalendar();
        GregorianCalendar gc2 = new GregorianCalendar();      
        gc1.setTime(begin);
        gc2.setTime(end);
        do{
            GregorianCalendar gc3=(GregorianCalendar)gc1.clone();
            String key = gc3.get(Calendar.YEAR) + "-";
            String value = gc3.get(Calendar.YEAR) + "年";
            Integer month = gc3.get(Calendar.MONTH)+1 ;
            if(month<10){
            	key +="0"+month;
            	value +="0" + month + "月";
            }else{
            	key += month;
            	value += month + "月";
            }
            dayMap.put(key, value);
            gc1.add(Calendar.MONTH, 1);
        }
        while(!gc1.after(gc2));
        return dayMap;
    }
    
    /**
     * 获取两个日子之间的所有日期
     * @param begin	开始日期
     * @param end	结束日期
     * @return 返回以逗号分隔的yyyy-MM-dd格式的日期字符串
     */
    public static String getBetweenDate(Date begin, Date end){
    	StringBuffer dates = new StringBuffer();;
    	GregorianCalendar gc1 = new GregorianCalendar();
        GregorianCalendar gc2 = new GregorianCalendar();      
        gc1.setTime(begin);
        gc2.setTime(end);
        do{
            GregorianCalendar gc3 = (GregorianCalendar)gc1.clone();
            if(dates.length() > 0)dates.append(",");
            dates.append(formatDateYMD(gc3.getTime()));
            gc1.add(Calendar.DATE, 1);
        }
        while(!gc1.after(gc2));
        return dates.toString();
    }
    /**
     * 获取两个日子之间的所有日期
     * @param begin	开始日期
     * @param end	结束日期
     * @return 返回以逗号分隔的yyyy-MM-dd格式的日期字符串
     */
    public static String getBetweenDateEx(String begin, String end){
    	Date beginEx = convertDateYMD(begin), endEx = convertDateYMD(end);
    	StringBuffer dates = new StringBuffer();;
    	GregorianCalendar gc1 = new GregorianCalendar();
        GregorianCalendar gc2 = new GregorianCalendar();      
        gc1.setTime(beginEx);
        gc2.setTime(endEx);
        do{
            GregorianCalendar gc3 = (GregorianCalendar)gc1.clone();
            if(dates.length() > 0)dates.append(",");
            dates.append(formatDateYMD(gc3.getTime()));
            gc1.add(Calendar.DATE, 1);
        }
        while(!gc1.after(gc2));
        return dates.toString();
    }
    
    /**
     * 获取两个日期之间的星期几
     * @param begin	开始日期
     * @param end	结束日期
     * @return 返回以逗号分隔的yyyy-MM-dd格式的日期字符串
     */
    public static List<String> getBeginEndWeekDate(String begin, String end,String week){
    	Date beginEx = convertDateYMD(begin), endEx = convertDateYMD(end);
    	List<String> weekList = new ArrayList<String>();
    	GregorianCalendar gc1 = new GregorianCalendar();
        GregorianCalendar gc2 = new GregorianCalendar();      
        gc1.setTime(beginEx);
        gc2.setTime(endEx);
        do{
            GregorianCalendar gc3 = (GregorianCalendar)gc1.clone();
            Calendar calendar = Calendar.getInstance(); //创建日历实例，默认当前日期 
            calendar.setTime(gc3.getTime());
            int flag = calendar.get(Calendar.DAY_OF_WEEK); //获得礼拜几信息 
            if(flag==1) 
            	flag=8;
            if(flag-1 == Integer.parseInt(week))
            	weekList.add(formatDateYMD(gc3.getTime()));
            gc1.add(Calendar.DATE, 1);
        }
        while(!gc1.after(gc2));
        return weekList;
    }
    
    public static void main(String [] args){
    	//System.out.println(addDay(new Date(),15));
    	System.out.println(getBeginEndWeekDate("2017-03-01","2017-03-30","1"));
    }
}
