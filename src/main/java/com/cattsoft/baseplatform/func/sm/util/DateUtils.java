package com.cattsoft.baseplatform.func.sm.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;

/**
 * 日期转换工具类
 * 
 * @author zhangweiqiang
 * 
 */
public class DateUtils {
	public final static String MONTH_STRING_FORMAT = "yyyyMM";

	public final static String DATE_FORMAT = "yyyy-MM-dd";

	public final static String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public final static String DATE_FORMAT_CON = "yyyyMMddHHmmss";

	public final static String DATE_FORMAT_HM = "yyyy-MM-dd HH:mm";

	private final static String[] PARSE_PATTERNS = { DATE_TIME_FORMAT, DATE_FORMAT,
			MONTH_STRING_FORMAT, DATE_FORMAT_HM };

	public static Timestamp parseDataDb(final String str) {
		try {
			Date d = parseDate(str);
			if (d != null) {
				return new Timestamp(d.getTime());
			}
		} catch (ParseException e) {
			return null;
		}
		return null;
	}

	public static Date parseDate(final String str) throws ParseException {

		if ((null == str) || "".equals(str)) {
			return null;
		}
		return parseDate(str, PARSE_PATTERNS);
	}

	public static Date parseDate(final String str, final String[] format) throws ParseException {

		return org.apache.commons.lang.time.DateUtils.parseDate(str, format);
	}
}
