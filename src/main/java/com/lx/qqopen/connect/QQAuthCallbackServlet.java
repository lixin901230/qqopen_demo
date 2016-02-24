package com.lx.qqopen.connect;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;















import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.lx.qqopen.connect.core.DispatchServletSupport;
import com.lx.qqopen.utils.LoadQQPropertiesConfig;
import com.lx.qqopen.utils.OpenIDUtils;
import com.lx.qqopen.utils.QQConnectURLConst;
import com.lx.qqopen.utils.UserInfoUtils;

/**
 * QQ授权成功后的回调处理
 * 
 * @author lx
 */
public class QQAuthCallbackServlet extends DispatchServletSupport {
	
	private static final long serialVersionUID = 3703905337462319833L;
	
	private static Logger logger = Logger.getLogger(QQAuthCallbackServlet.class);
	
	private static String returnAuthCode;		//qq开放平台返回的授权code
	private static String returnState;
	
	private static String APP_ID;
	
	/** 加载QQ开放平台账号配置 */
	static {
		Properties config = LoadQQPropertiesConfig.getInstance().getConfig();
		APP_ID = config.getProperty("app_ID");
	}
	
	/**
	 * qq授权回调地址访问的方法（即：qq登录授权成功后，回调网站的地址）
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public static void qqAuthCallback(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        PrintWriter out = response.getWriter();
		
		returnAuthCode = request.getParameter("code");
		returnState = request.getParameter("state");
		String state = (String) request.getSession().getAttribute("qq_connect_state");	//用于第三方应用防止CSRF攻击
		
		logger.info("\n>>>>>>QQ 获取Authorization Code="+returnAuthCode+"；state="+state+"\n");

		
		try {
			if(StringUtils.isEmpty(returnAuthCode)) {
				throw new Exception(">>>>>>获取QQ授权回调后的授权Code为空；");
			}
			if(StringUtils.isEmpty(returnState)) {
				throw new Exception(">>>>>>获取QQ授权回调后的授权state为空；");
			}
			
			String access_token = null;
			if(returnState.equals(state)) {	//防止CSRF攻击
				access_token = AccessTokenUtils.getToken(returnAuthCode, state);
				
				logger.info("\n>>>>>>QQ 开放平台接口调用凭据access_token："+access_token+"\n");
			} else {
				throw new Exception(">>>>>>QQ授权前传入state的与授权后返回的state不一致，可能受到CSRF攻击；");
			}
			
			String openId = OpenIDUtils.getUserOpenID(access_token);
			logger.info("/n>>>>>>当前授权qq用户的openID："+openId+"\n");
			
			JSONObject userInfoJson = UserInfoUtils.getUserInfo(access_token, APP_ID, openId);
			logger.info("/n>>>>>>当前授权qq的用户信息："+userInfoJson.toString()+"\n");
			
			out.print("<h3>恭喜，qq授权登录成功！，当前授权qq的用户信息："+userInfoJson.toString()+"</h3>");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
