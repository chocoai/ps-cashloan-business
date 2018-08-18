package com.adpanshi.cashloan.core.common.util;

import org.apache.commons.lang3.StringUtils;
import tool.exception.UtilException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 时间工具
 * Created by lsk on 2017/2/14.
 * @author 8452
 */
public class DateUtil {

	public static final String DATEFORMAT_STR_001 = "yyyy-MM-dd HH:mm:ss";
	public static final String DATEFORMAT_STR_002 = "yyyy-MM-dd";
	public static final String DATEFORMAT_STR_003 = "MM-dd";
	public static final String DATEFORMAT_STR_004 = "HH:mm:ss";
	public static final String DATEFORMAT_STR_011 = "yyyyMMddHHmmss";
	public static final String DATEFORMAT_STR_012 = "yyyyMMdd";
	public static final String DATEFORMAT_STR_013 = "yyyyMM";
	public static final String DATEFORMAT_STR_014 = "HHmmss";
	public static final String DATEFORMAT_STR_015 = "yyyyMMdd HH:mm:ss";
	public static final String DATEFORMAT_STR_016 = "yyyyMMddHHmmssSSS";
	public static final String DATEFORMAT_STR_017 = "yyyyMMddHHss";
	public static final String DATEFORMAT_STR_021 = "yyyy年MM月dd日 HH时mm分ss秒";
	public static final String DATEFORMAT_STR_022 = "yyyy年MM月dd日";
	public static final String DATEFORMAT_STR_023 = "MM月dd日 hh:mm";
	public static final String DATEFORMAT_STR_032 = "yyyy/MM/dd";

	public static final String DATEFORMAT_STR_033 = "yyyy/MM/dd HH:mm:ss";
	
	public static final String YYYY_MM_DD = "yyyy-MM-dd";
	
	public static final String YYYY_MM_DD_HH_MM_SS="yyyy-MM-dd HH:mm:ss";

	public static final String YYMMDD = "yyMMdd";
	
	public static final String MMDD = "MMdd";
	
	public static final String yyyyMMddHHmmss="yyyyMMddHHmmss";
	
	/**年月日*/
	public static final String YYYYMMDD="yyyyMMdd";

	/**yyyy年MM月dd日*/
	public static final String YYYY_MM_DD_CN = "yyyy年MM月dd日";

	private final static int[] dayArr = new int[] { 20, 19, 21, 20, 21, 22, 23,
			23, 23, 24, 23, 22 };
	private final static String[] constellationArr = new String[] { "摩羯座",
			"水瓶座", "双鱼座", "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座",
			"天蝎座", "射手座", "摩羯座" };


    @SuppressWarnings("deprecation")
	public static Date dateAddMins(Date date, int minCnt) {
        Date d = new Date(date.getTime());
        d.setMinutes(d.getMinutes() + minCnt);
        return d;
    }
    
    /**
     * 计算时间差,单位分
     * @param date1
     * @param date2
     * @return
     */
    public static int minuteBetween(Date date1, Date date2){
		DateFormat sdf=new SimpleDateFormat(DATEFORMAT_STR_001);
		Calendar cal = Calendar.getInstance();
		try {
			Date d1 = sdf.parse(DateUtil.dateStr4(date1));
			Date d2 = sdf.parse(DateUtil.dateStr4(date2));
			cal.setTime(d1);
			long time1 = cal.getTimeInMillis();
			cal.setTime(d2);
			long time2 = cal.getTimeInMillis();
			return Integer.parseInt(String.valueOf((time2 - time1) / 60000));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}
    
    /**
     * 计算时间差,单位秒
     * @param date1
     * @param date2
     * @return
     */
    public static int secondsBetween(Date date1, Date date2){
		DateFormat sdf=new SimpleDateFormat(DATEFORMAT_STR_001);
		Calendar cal = Calendar.getInstance();
		try {
			Date d1 = sdf.parse(DateUtil.dateStr4(date1));
			Date d2 = sdf.parse(DateUtil.dateStr4(date2));
			cal.setTime(d1);
			long time1 = cal.getTimeInMillis();
			cal.setTime(d2);
			long time2 = cal.getTimeInMillis();
			return Integer.parseInt(String.valueOf((time2 - time1) / 1000));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}
    
	/**
	 * 获取指定时间天的开始时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDayStartTime(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(date.getTime());
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
				cal.get(Calendar.DATE), 0, 0, 0);
		return cal.getTime();
	}

	/**
	 * 获取指定时间天的结束时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDayEndTime(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(date.getTime());
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
				cal.get(Calendar.DATE), 23, 59, 59);
		return cal.getTime();
	}

	/**
	 * String转化Date格式
	 * @param date
	 * @param type
	 * @return
	 */
	public static Date parse(String date,String type){
		SimpleDateFormat formatter = new SimpleDateFormat(type);
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(date, pos);
		return strtodate;
		
	}

	/**
	 * date + 天数 = 新日期
	 * @param date
	 * @param dayNum
	 * @return
	 */
	public static String plusHourForDate(Date date,int dayNum){
		Calendar Cal = Calendar.getInstance();
		Cal.setTime(date);
		Cal.add(Calendar.HOUR_OF_DAY,24 * dayNum);
		SimpleDateFormat formatter = new SimpleDateFormat(DATEFORMAT_STR_002);
		return formatter.format(Cal.getTime());
	}

	/**
	 * 获得当前日期的前一天
	 * @return
	 * @throws Exception
	 */
	public static String getSpecifiedDayBefore(){
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE,day-1);
		SimpleDateFormat formatter = new SimpleDateFormat(DATEFORMAT_STR_002);
		return formatter.format(c.getTime());
	}
	
	/**
	 * <p>根据给条件、格式化时间</p>
	 * @param dateDate
	 * @param formatString
	 * @return 
	 * */
	public static String dateToString(Date dateDate, String formatString) {
		String dateString = "";
		if (dateDate != null && formatString != "" && formatString != null) {
			SimpleDateFormat formatter = new SimpleDateFormat(formatString);
			dateString = formatter.format(dateDate);
		}
		return dateString;
	}
	
	/**
	 * 获取当前时间前后beforeOrAfterDay天时间
	 * @param beforeOrAfterDay
	 * @return Date
	 */
	public static Date getDate(int beforeOrAfterDay) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.add(GregorianCalendar.DATE, beforeOrAfterDay);
		return calendar.getTime();
	}
	
	/**
	 * 获取Date前后afterOrAgo天时间
	 * @param date
	 * @param beforeOrAfterDay
	 * @return Date
	 */
	public static Date getDate(Date date, int beforeOrAfterDay) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(GregorianCalendar.DATE, beforeOrAfterDay);
		return calendar.getTime();
	}
	
	
	/**
	 * 增加日期天数  
	 * 2017-05-06 to 2017-05-07
	 * @param dateString 日期字符串
	 * 					例： '2017-05-06'
	 * @param day 天数
	 * @return 增加后的日期  '2017-05-07'
	 */
	public static String addDay(String dateString, int day) {
		Date date = parse(dateString, YYYY_MM_DD);
		date = getDate(date, day);
		return dateToString(date, YYYY_MM_DD);
	}
	
	/**
	 * <p>根据给定时间获取当前年</p>
	 * @param date
	 * @return int
	 * */
	public static int getYear(Date date){
		return getCalendarFieldValue(date, Calendar.YEAR);
	}
	
	/**
	 * <p>根据给定时间获取当前月份</p>
	 * @param date
	 * @return int
	 * */
	public static int getMonth(Date date){
		return getCalendarFieldValue(date, Calendar.MONTH)+1;
	}
	
	/**
	 * <p>根据给定时间获取当前第几天</p>
	 * @param date
	 * @return int
	 * */
	public static int getDayOfMohth(Date date){
		return getCalendarFieldValue(date, Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * 根据给定条件获取日历字段的值
	 * @param date 待处理的时间
	 * @param field (Calendar.YEAR,Calendar.MONTH....)
	 * @return 
	 * */
	private static int getCalendarFieldValue(Date date,int field){
		try {
			GregorianCalendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			return calendar.get(field);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 通过生日计算星座
	 *
	 * @param month
	 * @param day
	 * @return String
	 */
	public static String getConstellation(int month, int day) {
		return day < dayArr[month - 1] ? constellationArr[month - 1]
				: constellationArr[month];
	}


	public static Date getNow() {
		Calendar cal = Calendar.getInstance();
		Date currDate = cal.getTime();
		return currDate;
	}

	public static String getNowDate() {
		Calendar cal = Calendar.getInstance();
		String date = cal.get(1) + "-" + (cal.get(2) + 1) + "-" + cal.get(5);
		return date;
	}

	public static String dateStr(Date date, String f) {
		if (date == null) {
			return "";
		} else {
			SimpleDateFormat format = new SimpleDateFormat(f);
			String str = format.format(date);
			return str;
		}
	}

	public static String dateStr(Date date) {
		return dateStr(date, "MM月dd日 hh:mm");
	}

	public static String dateStr2(Date date) {
		return dateStr(date, "yyyy-MM-dd");
	}

	public static String dateStr5(Date date) {
		return dateStr(date, "yyyy年MM月dd日 HH时mm分ss秒");
	}

	public static String dateStr3(Date date) {
		return dateStr(date, "yyyyMMddHHmmss");
	}

	public static String dateStr4(Date date) {
		return dateStr(date, "yyyy-MM-dd HH:mm:ss");
	}

	public static String dateStr6(Date date) {
		return dateStr(date, "yyyy年MM月dd日");
	}

	public static String dateStr7(Date date) {
		return dateStr(date, "yyyyMMdd");
	}

	public static String dateStr8(Date date) {
		return dateStr(date, "MM-dd");
	}

	public static String dateStr9(Date date) {
		return dateStr(date, "yyyyMM");
	}

	public static String dateStr10(Date date) {
		return dateStr(date, "yyyy/MM/dd");
	}

	public static String dateStr11(Date date) {
		return dateStr(date, "HHmmss");
	}

	public static Date getDate(String times) {
		long time = Long.parseLong(times);
		return new Date(time * 1000L);
	}

	public static String dateStr(String times) {
		return dateStr(getDate(times));
	}

	public static String dateStr2(String times) {
		return dateStr2(getDate(times));
	}

	public static String dateStr3(String times) {
		return dateStr3(getDate(times));
	}

	public static String dateStr4(String times) {
		return dateStr4(getDate(times));
	}

	public static String dateStr5(String times) {
		return dateStr5(getDate(times));
	}

	public static long getTime(Date date) {
		return date.getTime() / 1000L;
	}

	public static int getDay(Date d) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		return cal.get(5);
	}

	public static Date valueOf(String str) {
		return valueOf(str, "yyyy-MM-dd");
	}

	public static Date valueOf(String str, String dateFormatStr) {
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormatStr);
		ParsePosition pos = new ParsePosition(0);
		Date strtoDate = formatter.parse(str, pos);
		return strtoDate;
	}

	public static Date rollMinute(Date d, int minute) {
		return new Date(d.getTime() + (long)(minute * 60 * 1000));
	}

	public static Date rollMinuteBefore(Date d, int minute) {
		return new Date(d.getTime() - (long)(minute * 60 * 1000));
	}

	public static Date rollDay(Date d, int day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(5, day);
		return cal.getTime();
	}

	public static Date rollMon(Date d, int mon) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(2, mon);
		return cal.getTime();
	}

	public static Date rollYear(Date d, int year) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(1, year);
		return cal.getTime();
	}

	public static Date rollDate(Date d, int year, int mon, int day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(1, year);
		cal.add(2, mon);
		cal.add(5, day);
		return cal.getTime();
	}

	public static String getNowTimeStr() {
		String str = Long.toString(System.currentTimeMillis() / 1000L);
		return str;
	}

	public static String getTimeStr(Date time) {
		long date = time.getTime();
		String str = Long.toString(date / 1000L);
		return str;
	}

	public static String getTimeStr(String dateStr, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);

		Date date;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException var5) {
			var5.printStackTrace();
			return "";
		}

		String str = getTimeStr(date);
		return str;
	}


	public static Date getIntegralTime() {
		Calendar cal = Calendar.getInstance();
		cal.set(11, 0);
		cal.set(13, 0);
		cal.set(12, 0);
		cal.set(14, 0);
		return cal.getTime();
	}

	public static Date getLastIntegralTime() {
		Calendar cal = Calendar.getInstance();
		cal.set(11, 23);
		cal.set(13, 59);
		cal.set(12, 59);
		cal.set(14, 0);
		return cal.getTime();
	}

	public static Date getLastSecIntegralTime(Date d) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(d.getTime());
		cal.set(11, 23);
		cal.set(13, 59);
		cal.set(12, 59);
		cal.set(14, 0);
		return cal.getTime();
	}

	public static long getTime(String format) {
		long t = 0L;
		if (StringUtils.isEmpty(format)) {
			return t;
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			try {
				Date date = sdf.parse(format);
				t = date.getTime() / 1000L;
			} catch (ParseException var6) {
				var6.printStackTrace();
			}

			return t;
		}
	}

	public static String getCurrentWeekday() {
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(5, mondayPlus + 6);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	private static int getMondayPlus() {
		Calendar cd = Calendar.getInstance();
		int dayOfWeek = cd.get(7) - 1;
		return dayOfWeek == 1 ? 0 : 1 - dayOfWeek;
	}

	public static String getMondayOFWeek() {
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(5, mondayPlus);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	public static String getFirstDayOfMonth() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.add(2, 0);
		c.set(5, 1);
		return format.format(c.getTime());
	}

	public static String getLastDayOfMonth() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar ca = Calendar.getInstance();
		ca.set(5, ca.getActualMaximum(5));
		return format.format(ca.getTime());
	}

	public static Date timeMonthManage(Date d, int month) {
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(d);
		rightNow.add(2, month);
		return rightNow.getTime();
	}

	public static Date monthLastDay(int year_time, int month_time) {
		Calendar cal = Calendar.getInstance();
		cal.set(year_time, month_time, 0, 23, 59, 59);
		return cal.getTime();
	}

	public static Date monthFirstDay(int year_time, int month_time) {
		Calendar cal = Calendar.getInstance();
		cal.set(year_time, month_time - 1, 1, 0, 0, 0);
		return cal.getTime();
	}

	public static Date currMonthFirstDay(Date d) {
		Calendar cal = Calendar.getInstance();
		if (d != null) {
			cal.setTime(d);
		}

		cal.set(5, cal.getActualMinimum(5));
		cal.set(cal.get(1), cal.get(2), cal.get(5), 0, 0, 0);
		return cal.getTime();
	}

	public static Date currMonthLastDay(Date d) {
		Calendar cal = Calendar.getInstance();
		if (d != null) {
			cal.setTime(d);
		}

		cal.set(5, cal.getActualMaximum(5));
		cal.set(cal.get(1), cal.get(2), cal.get(5), 23, 59, 59);
		return cal.getTime();
	}

	public static int getTimeYear(Date date) {
		if (date == null) {
			date = new Date();
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(1);
	}

	public static int getTimeMonth(Date date) {
		if (date == null) {
			date = new Date();
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(2) + 1;
	}

	public static int getTimeDay(Date date) {
		if (date == null) {
			date = new Date();
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(5);
	}

	public static Date getFirstSecIntegralTime(Date d) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(d.getTime());
		cal.set(11, 0);
		cal.set(13, 0);
		cal.set(12, 0);
		cal.set(14, 0);
		cal.set(5, 0);
		return cal.getTime();
	}

	public static Date getDayEndTime(long d) {
		Date day;
		if (d <= 0L) {
			day = new Date();
		} else {
			day = new Date(d * 1000L);
		}

		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(day.getTime());
		cal.set(cal.get(1), cal.get(2), cal.get(5), 23, 59, 59);
		return cal.getTime();
	}

	public static Date getDayStartTime(long d) {
		Date day;
		if (d <= 0L) {
			day = new Date();
		} else {
			day = new Date(d * 1000L);
		}

		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(day.getTime());
		cal.set(cal.get(1), cal.get(2), cal.get(5), 0, 0, 0);
		return cal.getTime();
	}

	public static int daysBetween(Date date1, Date date2) {
		DateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();

		try {
			Date d1 = sdf.parse(dateStr7(date1));
			Date d2 = sdf.parse(dateStr7(date2));
			cal.setTime(d1);
			long time1 = cal.getTimeInMillis();
			cal.setTime(d2);
			long time2 = cal.getTimeInMillis();
			return Integer.parseInt(String.valueOf((time2 - time1) / 86400000L));
		} catch (ParseException var10) {
			throw new UtilException("计算时间差时出现ParseException，date1:" + date1 + "，date2:" + date2, var10);
		}
	}

	public static long getLeftSeconds() {
		Calendar cal = Calendar.getInstance();
		cal.set(5, cal.get(5) + 1);
		cal.set(11, 0);
		cal.set(12, 0);
		cal.set(13, 0);
		cal.set(14, 0);
		long diff = cal.getTimeInMillis() - System.currentTimeMillis();
		return diff / 1000L;
	}
	
}
