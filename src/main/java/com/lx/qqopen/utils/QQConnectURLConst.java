package com.lx.qqopen.utils;

import java.util.Properties;

/**
 * QQ互联平台接口URL常量类
 * @author lx
 *
 */
public class QQConnectURLConst {
	
	private static Properties config = null;
	static {
		config = LoadQQPropertiesConfig.getInstance().getConfig();
	}
	
	public static String redirect_URI() {
		return config.getProperty("redirect_URI");
	}
	public static String redirect_URI_bak() {
		return config.getProperty("redirect_URI_bak");
	}
	public static String scope() {
		return config.getProperty("scope");
	}
	public static String baseURL() {
		return config.getProperty("baseURL");
	}
	public static String getUserInfoURL() {
		return config.getProperty("getUserInfoURL");
	}
	public static String accessTokenURL() {
		return config.getProperty("accessTokenURL");
	}
	public static String authorizeURL() {
		return config.getProperty("authorizeURL");
	}
	public static String getOpenIDURL() {
		return config.getProperty("getOpenIDURL");
	}
	public static String addTopicURL() {
		return config.getProperty("addTopicURL");
	}
	public static String addBlogURL() {
		return config.getProperty("addBlogURL");
	}
	public static String addAlbumURL() {
		return config.getProperty("addAlbumURL");
	}
	public static String uploadPicURL() {
		return config.getProperty("uploadPicURL");
	}
	public static String listAlbumURL() {
		return config.getProperty("listAlbumURL");
	}
	public static String addShareURL() {
		return config.getProperty("addShareURL");
	}
	public static String checkPageFansURL() {
		return config.getProperty("checkPageFansURL");
	}
	public static String addTURL() {
		return config.getProperty("addTURL");
	}
	public static String addPicTURL() {
		return config.getProperty("addPicTURL");
	}
	public static String delTURL() {
		return config.getProperty("delTURL");
	}
	public static String getWeiboUserInfoURL() {
		return config.getProperty("getWeiboUserInfoURL");
	}
	public static String getWeiboOtherUserInfoURL() {
		return config.getProperty("getWeiboOtherUserInfoURL");
	}
	public static String getFansListURL() {
		return config.getProperty("getFansListURL");
	}
	public static String getIdolsListURL() {
		return config.getProperty("getIdolsListURL");
	}
	public static String addIdolURL() {
		return config.getProperty("addIdolURL");
	}
	public static String delIdolURL() {
		return config.getProperty("delIdolURL");
	}
	public static String getTenpayAddrURL() {
		return config.getProperty("getTenpayAddrURL");
	}
	public static String getRepostListURL() {
		return config.getProperty("getRepostListURL");
	}
	
}
