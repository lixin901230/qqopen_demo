package com.lx.qqopen.connect;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lx.qqopen.connect.core.DispatchServletSupport;
import com.lx.qqopen.utils.LoadQQPropertiesConfig;
import com.lx.qqopen.utils.QQConnectURLConst;

public class QQAuthServlet extends DispatchServletSupport {
	
	private static final long serialVersionUID = 3703905337462319833L;
	
	private static String APP_ID;
	
	/** 加载QQ开放平台账号配置 */
	static {
		Properties config = LoadQQPropertiesConfig.getInstance().getConfig();
		APP_ID = config.getProperty("app_ID");
	}
	
	/**
	 * qq授权登录
	 * @param request
	 * @param response
	 */
	public void qqAuthorize(HttpServletRequest request, HttpServletResponse response) {
		try {
			String redirect_URI = URLEncoder.encode(QQConnectURLConst.redirect_URI_bak(), "UTF-8");
			String authorizeUrl = QQConnectURLConst.authorizeURL();
			String url = authorizeUrl+ "?response_type=code" 
					+ "&client_id="+ APP_ID 
					+ "&state=state" 
					+ "&redirect_uri="+ redirect_URI 
					+ "&scope="+ QQConnectURLConst.scope();
			response.sendRedirect(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
