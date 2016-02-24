package com.lx.qqopen.connect;

import java.net.URLEncoder;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;

import com.lx.qqopen.utils.CommonUtils;
import com.lx.qqopen.utils.JsonUtil;
import com.lx.qqopen.utils.LoadQQPropertiesConfig;
import com.lx.qqopen.utils.NumberUtil;
import com.lx.qqopen.utils.QQConnectURLConst;
import com.lx.qqopen.utils.cache.CacheUtils;

/**
 * QQ开放平台接口访问凭证（access_token）获取工具类
 * 
 * @author lixin
 *
 */
public class AccessTokenUtils {
	
	private static Logger logger = Logger.getLogger(AccessTokenUtils.class);
	
	/** QQ开放平台接口调用凭证access_token缓存key */
	private static final String ACCESS_TOKEN_CACHE_KEY = "qq_@_access_token_key";

	private static String APP_ID;
	private static String APP_KEY;
	private static String GRANT_TYPE = "authorization_code";
	
	/** 加载QQ开放平台账号配置 */
	static {
		Properties config = LoadQQPropertiesConfig.getInstance().getConfig();
		APP_ID = config.getProperty("app_ID");
		APP_KEY = config.getProperty("app_KEY");
	}
	
	/**
	 * 测试
	 * @param args
	 */
	public static void main(String[] args) {
		String code = "";
		String state = "";
		AccessToken accessToken = getAccessToken(code, state);
		System.out.println(accessToken.getToken());
	}
	
	/**
	 * 获取access_token	接口访问凭证
	 * @param code 授权code
	 * @return
	 */
	public static String getToken(String code, String state) {
		return getAccessToken(code, state).getToken();
	}
	
	/**
	 * 获取access_token 对象
	 * @param code 授权code
	 * @return
	 */
	public static AccessToken getAccessToken(String code, String state) {
		
		AccessToken accessToken = new AccessToken();
		
		// 从缓存中取AccessToken对象，若未获取到缓存的accessToken，则重新获取
		Object object = CacheUtils.get(ACCESS_TOKEN_CACHE_KEY);
		if(object != null) {
			logger.info("\n>>>>>>从缓存中获取AccessToken对象成功！\n");
			accessToken = (AccessToken) object;
		} else {
			
			logger.info("\n>>>>>>从缓存中获取AccessToken对象失败，开始重新调用QQ开放平台获取access_token...\n");
			try {
				//重新获取AccessToken对象
				String url = QQConnectURLConst.accessTokenURL();
				url += "?grant_type="+GRANT_TYPE
					+ "&client_id="+ APP_ID
					+ "&client_secret="+ APP_KEY
					+ "&code="+ code
					+ "&state="+ state
					+ "&redirect_uri="+ URLEncoder.encode(QQConnectURLConst.redirect_URI(), "UTF-8");
				
//				Map<String, String> parameter = new HashMap<String, String>();
//				parameter.put("grant_type", GRANT_TYPE);
//				parameter.put("appid", APP_ID);
//				parameter.put("secret", APP_KEY);
				
//				HttpClient httpClient = HttpClientUtil.initHttpClient();
//				GetMethod method = HttpClientUtil.getMethod(url, parameter);
				HttpClient httpClient = new HttpClient();
				GetMethod method = new GetMethod(url);
				int httpState = httpClient.executeMethod(method);
				if(httpState == 200) {
					String respBodyStr = method.getResponseBodyAsString();
					logger.info("\n>>>>>>获取到的access_token串："+respBodyStr);//access_token=B4D1D3B2039C8A03A83F44AE973061AA&expires_in=7776000&refresh_token=724D50630503A11D8106158F9B4DB48F
					
					String access_token = "";
					int expires_in = 0;
					String refresh_token = "";
					Matcher m = Pattern.compile("^access_token=(\\w+)&expires_in=(\\w+)&refresh_token=(\\w+)$").matcher(respBodyStr);
			   		if (m.find()) {
			   			access_token = m.group(1);
			   			expires_in = Integer.parseInt(m.group(2));
			   			refresh_token = m.group(3);
			   		} else {
			   			Matcher m2 = Pattern.compile("^access_token=(\\w+)&expires_in=(\\w+)$").matcher(respBodyStr);
				        if (m2.find()) {
				        	access_token = m2.group(1);
				        	expires_in = Integer.parseInt(m2.group(2));
				        }
			   		}
					if(StringUtils.isNotEmpty(access_token)) {
						
						accessToken = new AccessToken(access_token, expires_in, refresh_token);
						
						CacheUtils.put(ACCESS_TOKEN_CACHE_KEY, accessToken, 6480000);	//缓存AccessToken对象，由于access_token有效期为7776000秒（3个月），故此处缓存有效期为6480000秒（两个半月）
					} else {
						throw new Exception("获取access_token失败，原因："+respBodyStr);
					}
				}
				
				logger.info("\n>>>>>>QQ开放平台接口访问认证accessToken ["+accessToken.getToken()+"]\n");
			} catch (Exception e) {
				logger.error("\n>>>>>>获取access_token失败，原因："+e+"\n");
				e.printStackTrace();
			}
		}
		return accessToken;
	}
}
