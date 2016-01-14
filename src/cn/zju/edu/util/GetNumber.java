package cn.zju.edu.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 获取字符串中的数�?
 * 
 * @author handahe
 * 
 */
public class GetNumber {
	public static String getNumbers(String str) {
		String regEx = "[^0-9]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}
}
