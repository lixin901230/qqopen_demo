package com.lx.qqopen.connect;

import java.net.URLEncoder;
import java.util.Properties;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
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
		AccessToken accessToken = getAccessToken(code);
		System.out.println(accessToken.getToken());
	}
	
	/**
	 * 获取access_token	接口访问凭证
	 * @param code 授权code
	 * @return
	 */
	public static String getToken(String code) {
		return getAccessToken(code).getToken();
	}
	
	/**
	 * 获取access_token 对象
	 * @param code 授权code
	 * @return
	 */
	public static AccessToken getAccessToken(String code) {
		
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
					+ "&state="+ CommonUtils.uuid()
					+ "&redirect_uri="+ URLEncoder.encode(QQConnectURLConst.redirect_URI(), "UTF-8");
				
//				Map<String, String> parameter = new HashMap<String, String>();
//				parameter.put("grant_type", GRANT_TYPE);
//				parameter.put("appid", APP_ID);
//				parameter.put("secret", APP_KEY);
				
//				HttpClient httpClient = HttpClientUtil.initHttpClient();
//				GetMethod method = HttpClientUtil.getMethod(url, parameter);
				HttpClient httpClient = new HttpClient();
				GetMethod method = new GetMethod(url);
				int state = httpClient.executeMethod(method);
				if(state == 200) {
					String respBodyStr = method.getResponseBodyAsString();
					JSONObject jsonObject = JsonUtil.strToJson(respBodyStr);
					if(jsonObject.has("access_token")) {
						
						String token = jsonObject.getString("access_token");
						String expiresInStr = jsonObject.getString("expires_in");
						accessToken = new AccessToken(token, NumberUtil.strToInteger(expiresInStr));
						
						CacheUtils.put(ACCESS_TOKEN_CACHE_KEY, accessToken, 6480000);	//缓存AccessToken对象，由于access_token有效期为7776000秒（3个月），故此处缓存有效期为6480000秒（两个半月）
					} else {
						throw new Exception("获取access_token失败，原因："+jsonObject.toString());
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
