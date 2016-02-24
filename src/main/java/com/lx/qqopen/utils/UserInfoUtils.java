package com.lx.qqopen.utils;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONObject;

/**
 * 获取授权qq的用户信息
 * @author lixin
 *
 */
public class UserInfoUtils {

	/**
	 * 获取qq授权后的用户信息
	 * @param accessToken
	 * @param appId
	 * @param openId
	 * @return
	 * @throws Exception
	 */
	public static JSONObject getUserInfo(String accessToken, String appId, String openId) throws Exception {
		
		if(StringUtils.isEmpty(accessToken) || StringUtils.isEmpty(appId) || StringUtils.isEmpty(openId)) {
			throw new Exception("获取授权qq用户信息失败，原因：参数错误！");
		}
		
		//https://graph.qq.com/user/get_user_info?access_token=YOUR_ACCESS_TOKEN&oauth_consumer_key=YOUR_APP_ID&openid=YOUR_OPENID
		String url = QQConnectURLConst.getUserInfoURL()+"?access_token="+accessToken+"&oauth_consumer_key="+appId+"&openid="+openId;
		HttpClient httpClient = new HttpClient();
		GetMethod getMethod = new GetMethod(url);
		int httpState = httpClient.executeMethod(getMethod);
		if(httpState == 200) {
			String bodyAsString = getMethod.getResponseBodyAsString();
			JSONObject jsonObject = JsonUtil.strToJson(bodyAsString);
			return jsonObject;
		}
		return null;
	}
}
