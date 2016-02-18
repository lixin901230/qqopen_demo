<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta property="qc:admins" content="45652176076410163263756367117056" />
	<title>腾讯开放平台接入测试</title>
</head>
<body>
	李鑫——腾讯开放平台接入测试
	<br/>
	<div>
		<a href="login.do">
			<img src="${pageContext.request.contextPath}/resource/images/qq_login_logo/qq_login_img4/Connect_logo_7.png" title="使用qq登录"/>
		</a>
		<!-- 请使用你的QQ账号登陆 -->
	</div>
	
	<script type="text/javascript">
	 	function toLogin() {
	   		//以下为按钮点击事件的逻辑。注意这里要重新打开窗口，否则后面跳转到QQ登录，授权页面时会直接缩小当前浏览器的窗口，而不是打开新窗口
	   		var A = window.open("login.do", "TencentLogin", 
	   			"width=450, height=320, menubar=0, scrollbars=1, resizable=1, status=1, titlebar=0, toolbar=0, location=1");
	 	}
	</script>
</body>
</html>