package com.edu.roy.wx.tools;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Maps;

/**
 * 
 * @author luoyh
 * @date Feb 6, 2017
 */
public abstract class DateTools {
	
	public static void main(String[] args) {
		File file = new File("e:/datas/a/b/c");
		if (!file.exists()) {
			file.mkdirs();
		}
	}

	public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	public static final String YYYY_MM_DD = "yyyy-MM-dd";
	public static final String YYYYMMDD = "yyyyMMdd";
	public static final String YYYY_MM = "yyyy-MM";

	private static final ConcurrentMap<String, SimpleDateFormat> CACHE = Maps.newConcurrentMap();
	private static final SimpleDateFormat BASE = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);

	static {
		// 获取东八区时间
		TimeZone tz = TimeZone.getTimeZone("GMT+8");
		TimeZone.setDefault(tz);

		CACHE.put(YYYY_MM_DD_HH_MM_SS, BASE);
	}

	public static Date now() {
		return new Date();
	}

	public static long getTime() {
		return now().getTime();
	}

	public static String getNow(String format) {
		return getNowFormat(format);
	}

	public static String getNow() {
		return getNowFormat(null);
	}

	public static String getNowFormat(String format) {
		return date2String(now(), format);
	}

	public static String date2String(Date date, String format) {
		return getSdf(format).format(date);
	}

	public static String date2String(Date date) {
		return getSdf(null).format(date);
	}

	private static SimpleDateFormat getSdf(String format) {
		if (StringUtils.isBlank(format)) {
			return BASE;
		}
		SimpleDateFormat ret = null;
		if (null == (ret = CACHE.get(format))) {
			ret = new SimpleDateFormat(format);
			CACHE.putIfAbsent(format, ret);
		}
		return ret;
	}

}
