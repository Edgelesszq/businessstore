package com.businessstore.util;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;

public class StringUtil {

	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 * @return true 空，false非空
	 */
	public static boolean isBlank(String str) {
		boolean b = false;
		if (str == null || "".equals(str) || "null".equals(str) || "NULL".equals(str)) {
			b = true;
		} else {
			b = false;
		}
		return b;
	}

	/**
	 * 验证是否是邮箱
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {
		String str = "^([a-zA-Z0-9]*[-_.]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);
		return m.matches();
	}

	/**
	 * 获取关键帧图片
	 * 
	 * @param video_url
	 * @param count
	 * @return
	 */
	public static String getCIFImageUrl(String video_url, int count) {
		if (video_url == null)
			return null;
		// getbasepath
		int index = video_url.lastIndexOf("/");
		String basepath = video_url.substring(0, index + 1);
		// getbasefilename
		String tmp = video_url.substring(index + 1);
		tmp = tmp.substring(0, tmp.lastIndexOf("."));

		if (count > 3)
			count = 0;
		return basepath + tmp + "_cif_" + count + ".jpg";
	}

	/**
	 * 替换字符
	 * 
	 * @param text
	 * @param value
	 *            要替换成的字符
	 * @return
	 */
	public static String replace(String text, String value) {
		String[] lines = text.split("&&P&&");
		Pattern pattern = Pattern.compile("\\*\\d+\\*");

		Map<String, String> maps = new HashMap<String, String>();
		for (String line : lines) {
			Matcher matcher = pattern.matcher(line);
			while (matcher.find()) {
				if (!maps.containsKey(matcher.group())) {
					String key = matcher.group();
					int len = Integer.parseInt(key.replace("*", ""));
					StringBuffer sb = new StringBuffer();
					for (int i = 0; i < len; i++) {
						sb.append(value);
					}
					maps.put(key, sb.toString());
				}
			}
		}
		for (String key : maps.keySet()) {
			text = text.replace(key, maps.get(key));
		}
		return text;
	}

	/**
	 * 处理空字符串
	 * 
	 * @param str
	 * @return String
	 */
	public static String doEmpty(String str) {
		return doEmpty(str, "");
	}

	/**
	 * 处理空字符串
	 * 
	 * @param str
	 * @param defaultValue
	 * @return String
	 */
	public static String doEmpty(String str, String defaultValue) {
		if (str == null || str.equalsIgnoreCase("null") || str.trim().equals("") || str.trim().equals("－请选择－")) {
			str = defaultValue;
		} else if (str.startsWith("null")) {
			str = str.substring(4, str.length());
		}
		return str.trim();
	}

	public static boolean isPhoneNumberValid(String number) {
		boolean isValid = false;
		if (number == null || number.length() <= 0) {
			return false;
		}
		Pattern PHONE = Pattern.compile( // sdd = space, dot, or dash
				"(\\+[0-9]+[\\- \\.]*)?" // +<digits><sdd>*
						+ "(\\([0-9]+\\)[\\- \\.]*)?" // (<digits>)<sdd>*
						+ "([0-9][0-9\\-  0-9\\+ \\.][0-9\\- 0-9\\+ \\.]+[0-9])");
		Matcher matcher = PHONE.matcher(number);
		isValid = matcher.matches();
		return isValid;
	}

	public static boolean isMobileNum(String mobiles) {
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	public static boolean num(String str) {
		try {
			new BigDecimal(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@SuppressLint("DefaultLocale")
	public static String toLowerCase(String str) {
		return str.toLowerCase();
	}

	/**
	 * 求一个字符串的md5值
	 * 
	 * @param str
	 *            字符串
	 * @return md5 value
	 */
	public static String md5(String str) {
		return MD5.getMD5Str(str);
	}

}
