package com.sjzc.kt.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期处理
 * 
 * @author asiainfo
 * 
 * @date 
 */
public class DateUtils {
	/** 时间格式(yyyy-MM-dd) */
	public final static String DATE_PATTERN = "yyyy-MM-dd";
	/** 时间格式(yyyy-MM-dd HH:mm:ss) */
	public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

	public static String format(Date date) {
		return format(date, DATE_PATTERN);
	}

	public static String format(Date date, String pattern) {
		if (date != null) {
			SimpleDateFormat df = new SimpleDateFormat(pattern);
			return df.format(date);
		}
		return null;
	}
	public static Date StringToDate(String timeStamp) {
		// TODO Auto-generated method stub
		long eTime = Long.parseLong(timeStamp);
		System.err.println(eTime);
		Timestamp ts = new Timestamp(eTime);
		Date expireTime = new Date();
		try{
			expireTime = ts;
			
		}catch(
		Exception e){
			e.printStackTrace();
		}
		System.err.println(expireTime);
		return expireTime;
	}
	
	public static void main(String[] args) {
		
		String timeStamp ="1580659200000";
		long eTime = Long.parseLong(timeStamp);
		Timestamp ts = new Timestamp(eTime);
		Date expireTime = new Date();
		try{
			expireTime = ts;
			
		}catch(
		Exception e){
			e.printStackTrace();
		}
	}
}
