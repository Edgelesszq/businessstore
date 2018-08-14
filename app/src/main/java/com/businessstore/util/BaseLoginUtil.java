package com.businessstore.util;

import android.content.Context;

public class BaseLoginUtil {
	/**
	 * 检查用户是否登录
	 * 
	 * @return
	 *//*
	public static boolean checkLogin(Context context) {
		try {
			if (!StringUtil.isBlank(SharedPreferencesUtil.readPhoneNum(context)) && !StringUtil.isBlank(SharedPreferencesUtil.readSessionid(context))) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	*//**
	 * 检查用户是否第一次登陆
	 * 
	 * @return
	 *//*
	public static boolean checkIsFirstLogin(Context context) {
		try {
			if (!StringUtil.isBlank(SharedPreferencesUtil.readPhoneNum(context))) {
				return true;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}*/
}
