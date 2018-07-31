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

	private static final String FILE_NAME = "share_date";

	public static final String INDEX="index";
	public static final String LOGIN_DATA="loginData";
	public static final String IS_LOGIN="isLogin";

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

	/**
	 * save data into FILE_NAME ,this path is data/data/POCKET_NAME/shared_prefs
	 * @param context
	 * @param key
	 * @param object
	 */
	public static void setParam(Context context , String key, Object object){

		String type = object.getClass().getSimpleName();
		SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();

		if("String".equals(type)){
			editor.putString(key, (String)object);
		}
		else if("Integer".equals(type)){
			editor.putInt(key, (Integer)object);
		}
		else if("Boolean".equals(type)){
			editor.putBoolean(key, (Boolean)object);
		}
		else if("Float".equals(type)){
			editor.putFloat(key, (Float)object);
		}
		else if("Long".equals(type)){
			editor.putLong(key, (Long)object);
		}

		editor.apply();
	}


	/**
	 * get value via enter key
	 * @param context
	 * @param key
	 * @param defaultObject
	 * @return
	 */
	public static Object getParam(Context context , String key, Object defaultObject){
		String type = defaultObject.getClass().getSimpleName();
		SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);

		if("String".equals(type)){
			return sp.getString(key, (String)defaultObject);
		}
		else if("Integer".equals(type)){
			return sp.getInt(key, (Integer)defaultObject);
		}
		else if("Boolean".equals(type)){
			return sp.getBoolean(key, (Boolean)defaultObject);
		}
		else if("Float".equals(type)){
			return sp.getFloat(key, (Float)defaultObject);
		}
		else if("Long".equals(type)){
			return sp.getLong(key, (Long)defaultObject);
		}

		return null;
	}

	/**
	 * delete key
	 * @param context
	 * @param key
	 */
	public static void removeParam(Context context,String key){
		SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.remove(key);
		editor.apply();
	}
	

}
