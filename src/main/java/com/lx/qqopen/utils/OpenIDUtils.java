package com.lx.qqopen.utils;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang.StringUtils;


/**
 * 获取授权qq的openId
 * @author lixin
 *
 */
public class OpenIDUtils {

	/**
	 * 获取用户openID
	 * @param accessToken
	 * @return
	 * @throws Exception 
	 */
	public static String getUserOpenID(String accessToken) throws Exception {
		
		if(StringUtils.isEmpty(accessToken)) {
			throw new Exception("access_token不能为空！");
		}
		
		String openid = "";
		String url = QQConnectURLConst.getOpenIDURL() + "?access_token="+accessToken;
		HttpClient httpClient = new HttpClient();
		GetMethod getMethod = new GetMethod(url);
		int httpState = httpClient.executeMethod(getMethod);
		if(httpState == 200) {
			String bodyAsString = getMethod.getResponseBodyAsString();
			Matcher m = Pattern.compile("\"openid\"\\s*:\\s*\"(\\w+)\"").matcher(bodyAsString);
		    if (m.find()) {
		    	openid = m.group(1);
		    } else {
		    	throw new Exception(">>>>>>获取openId失败！");
		    }
		}
		return openid;
	}
}
