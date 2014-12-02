package com.cattsoft.baseplatform.func.sm.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class CommonUtil {
	private static final String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
	private static final String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
	private static final String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式

	public static String removeHtmlTag(String source) {

		Pattern p_script = Pattern.compile(regEx_script,
				Pattern.CASE_INSENSITIVE);
		Matcher m_script = p_script.matcher(source);
		source = m_script.replaceAll(""); // 过滤script标签

		Pattern p_style = Pattern
				.compile(regEx_style, Pattern.CASE_INSENSITIVE);
		Matcher m_style = p_style.matcher(source);
		source = m_style.replaceAll(""); // 过滤style标签

		Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
		Matcher m_html = p_html.matcher(source);
		source = m_html.replaceAll(""); // 过滤html标签
		return source.replace("\r\n", " ").trim(); // 返回文本字符串
	}

	public static String cutString(String source, Integer count) {
		if (StringUtils.isNotBlank(source) && count > 0) {
			return source.length() > count ? source.substring(0, count) + "..."
					: source;
		} else {
			return "";
		}
	}

	/**
	 * 格式化时间 rex "yyyy-MM-dd HH:mm:ss"
	 * 
	 * @param rex
	 * @param date
	 * @return
	 */
	public static String formatDate(String rex, Date date) {
		if (null != date && StringUtils.isNotBlank(rex)) {
			SimpleDateFormat formatter = new SimpleDateFormat(rex);
			return formatter.format(date);
		}
		return null;
	}

}
