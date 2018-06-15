package com.businessstore.util;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * SharedPreferences 工具类
 * 
 * @author admin
 * 
 */
public class SharedPreferencesUtil {
	public final static String CONFIG_FILES = "config_files";
	public final static String PHONENUM = "phone_number";
	public final static String SESSIONID = "sessionid";
	public final static String USERID = "userid";
	public final static String COMPLAINTTYPELIST = "complainttypelist";
	
	public final static String HEADURL = "headurl";
	public final static String PRICE = "price";
	public final static String ISREAD = "isRead";
	
	

	/**
	 * 保存用户手机号
	 */
	public static void savePhoneNum(Context con, String phoneNum,
			String sessionid, String userid, String headurl, String price) {
		SharedPreferences sp = con.getSharedPreferences(CONFIG_FILES,
				Context.MODE_PRIVATE);
		sp.edit().putString(PHONENUM, phoneNum).commit();
		sp.edit().putString(SESSIONID, sessionid).commit();
		sp.edit().putString(USERID, userid).commit();
		sp.edit().putString(HEADURL, headurl).commit();
		sp.edit().putString(PRICE, price).commit();
	}

	/**
	 * 保存头像地址
	 */
	public static void saveHeadurl(Context con,String headurl) {
		SharedPreferences sp = con.getSharedPreferences(CONFIG_FILES,
				Context.MODE_PRIVATE);
		sp.edit().putString(HEADURL, headurl).commit();
	}
	
	/**
	 * 读取头像
	 */
	public static String readHeadUrl(Context con) {
		SharedPreferences sp = con.getSharedPreferences(CONFIG_FILES,
				Context.MODE_PRIVATE);
		return sp.getString(HEADURL, "");
	}
	
	/**
	 * 读取最低价格
	 */
	public static String readPrice(Context con) {
		SharedPreferences sp = con.getSharedPreferences(CONFIG_FILES,
				Context.MODE_PRIVATE);
		return sp.getString(PRICE, "");
	}
	
	/**
	 * 读取用户手机号
	 */
	public static String readPhoneNum(Context con) {
		SharedPreferences sp = con.getSharedPreferences(CONFIG_FILES,
				Context.MODE_PRIVATE);
		return sp.getString(PHONENUM, "");
	}

	/**
	 * 读取sessionid
	 */
	public static String readSessionid(Context con) {
		SharedPreferences sp = con.getSharedPreferences(CONFIG_FILES,
				Context.MODE_PRIVATE);
		return sp.getString(SESSIONID, "");
	}
	
	/**
	 * 读取userid
	 */
	public static String readUserid(Context con) {
		SharedPreferences sp = con.getSharedPreferences(CONFIG_FILES,
				Context.MODE_PRIVATE);
		return sp.getString(USERID, "");
	}

/*
	*
	 * 刪除用户手机号/sessionid/userid
	public static void delPhoneNum(Context con) {
		SharedPreferences sp = con.getSharedPreferences(CONFIG_FILES,
				Context.MODE_PRIVATE);
//		sp.edit().remove(PHONENUM).commit();
		sp.edit().remove(SESSIONID).commit();
		sp.edit().remove(USERID).commit();
		sp.edit().remove(ISREAD).commit();

		JPushInterface.setAliasAndTags(con, "", null);
	}
*/


	public static void saveComplainttypeList(Context con,String placeList){
		SharedPreferences sp = con.getSharedPreferences(CONFIG_FILES,
				Context.MODE_PRIVATE);
		sp.edit().putString(COMPLAINTTYPELIST, placeList).commit();
	}
	
	public static String readComplainttypeList(Context con) {
		SharedPreferences sp = con.getSharedPreferences(CONFIG_FILES,
				Context.MODE_PRIVATE);
		return sp.getString(COMPLAINTTYPELIST, "");
	}
	
	/**
	 * 是否读过通知
	 */
	public static void saveIsRead(Context con,String isRead) {
		SharedPreferences sp = con.getSharedPreferences(CONFIG_FILES,
				Context.MODE_PRIVATE);
		sp.edit().putString(ISREAD, isRead).commit();
	}
	public static String readIsRead(Context con) {
		SharedPreferences sp = con.getSharedPreferences(CONFIG_FILES,
				Context.MODE_PRIVATE);
		return sp.getString(ISREAD, "0");
	}
	

}
