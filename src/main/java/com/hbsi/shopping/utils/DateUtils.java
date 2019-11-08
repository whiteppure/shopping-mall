package com.hbsi.shopping.utils;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 时间工具类
 * @author admin
 *
 */
public class DateUtils {
	/**
	 * 把Date类型 转化为 LocalDatetime 类型
	 * @param date 日期对象 中间带有 T 
	 * @return
	 */
	public static String formatDate(Date date) {
		return date == null ? "" : DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").
									format(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());	
	}
	
}
