package com.lx.qqopen.connect;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import org.apache.log4j.Logger;

import com.lx.qqopen.connect.core.DispatchServletSupport;

/**
 * QQ授权成功后的回调处理
 * 
 * @author lx
 */
public class QQAuthCallbackServlet extends DispatchServletSupport {
	
	private static final long serialVersionUID = 3703905337462319833L;
	
	private static Logger logger = Logger.getLogger(QQAuthCallbackServlet.class);
	
	private static String code;		//授权code
	private static String state;
	
	/**
	 * qq授权回调地址访问的方法（即：qq登录授权成功后，回调网站的地址）
	 * @param request
	 * @param response
	 */
	public static void qqAuthCallback(HttpServletRequest request, HttpServletResponse response) {
		
		code = request.getParameter("code");
		state = request.getParameter("state");
		
		logger.info("\n>>>>>>QQ 获取Authorization Code="+code+"\n");
		
	}
	
}
