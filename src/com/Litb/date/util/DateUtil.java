package com.Litb.date.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.zip.DataFormatException;

/**
 * 时间工具类
 * @author litongbin
 *
 */
public class DateUtil {
	
	/**
	 * 私有构造方法,工具类不需要实例化
	 */
	private DateUtil(){
		
	}
	
	/**
	 * 判断字符串是否可以被格式化
	 * @param dateStr 字符串
	 * @return	true or fale 是否可以被格式化
	 */
	public static boolean isDate(String dateStr){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			df.parse(dateStr);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}
	
	/**
	 * 将date类型格式化为string类型的是时间传
	 * @param date	日期
	 * @param formate	格式化的格式
	 * @return	string类型的时间串
	 */
	public static String dateFormate(Date date,String formate){
		DateFormat df = new SimpleDateFormat(formate);
		return df.format(date);
	}
	
	/**
	 * 获取当前日期或者时间
	 * @param formate 时间格式    yyy-MM-dd HH:mm:ss
	 * @return	返回String类型的格式化后的当前日期
	 */
	public static String getDate(String formate){
		DateFormat df = new SimpleDateFormat(formate);
		return df.format(new Date());
	}
	
	/**
	 * 获取毫秒,将时间字符串转换成毫秒
	 * @param dateStr 时间字符串
	 * @param formate 时间字符串的格式
	 * @return	
	 * @throws ParseException
	 */
	private static long getMills(String dateStr,String formate) throws ParseException {
		Calendar c =Calendar.getInstance();
		DateFormat df =new SimpleDateFormat(formate);
			c.setTime(df.parse(dateStr));
			return c.getTimeInMillis();	
	}
	
	/**
	 * 计算事假加法
	 * @param dateStr	时间字符串
	 * @param formate	时间字符串的格式
	 * @param day		多少天
	 * @return	返回Date类型,如果时间错误则返回null
	 */
	public static Date addDay(String dateStr,String formate ,int day){
		Calendar c = Calendar.getInstance();
		try {
			c.setTimeInMillis(getMills(dateStr,formate) +  day * 24 * 3600 * 1000);
			return c.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 计算两个日期相差的天数
	 * @param strDate1	第一个String类型的时间串
	 * @param strDate2	第二个String类型的时间串
	 * @param formate	时间字符串的格式
	 * @return	返回相差的天数
	 * @throws ParseException
	 */
	public static long getDifferDays(String strDate1,String strDate2,String formate) throws ParseException{
		long days = 0;
		long time1 = getMills(strDate1,formate);
		long time2 = getMills(strDate2,formate);
		long diff ;
		if(time1 > time2){
			diff = time1 - time2;
		}else{
			diff = time2 - time1;
		}
		days = diff / (1000 * 60 * 60 * 24);
		return days;
	}
	
	
}
