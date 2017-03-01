<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
<title>登录</title>

<meta name="keywords" content="keyword1,keyword2,keyword3" />
<meta name="description" content="this is my page" />
<meta  charset="UTF-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" />
<link rel="stylesheet" type="text/css" href="css/amazeui.css" />
<link rel="stylesheet" type="text/css" href="css/login.css" />
</head>

<body>

	<img class="login_logo" src="images/logo.png">
	
	<div class="login_tab">
		<a href="#" class="login_tab_checked_a">登录</a> <span class="dot">●</span>
		<a href="register.html" class="login_tab_unchecked_a">注册</a>
	</div>
	<div class="login_content">
		<form action="">
			<div class="am-form-group">
				<input type="text" class="am-form-field login_input" iscookie="true"
					name="mobile" id="mobile" placeholder="您的手机号">
			</div>
			<div class="am-form-group">
				<input type="password" class="am-form-field login_input"
					name="password" id="password" placeholder="密码">
			</div>

			<input type="button" class="default_btn am-disabled am-round"
				style="height:35px;width:240px;line-height: 10px;"
				onclick="login_it();" value="登录">

			<div id="msg" class="msg"></div>
		</form>
	</div>


</body>
  
</html>
