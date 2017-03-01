<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
 <head>
 <script type="text/javascript">
   	function register_it(){
		var formData = new Object();
		var data=$(":input").each(function() {
			formData[this.name] =$("#"+this.name ).val();
		});
		if(formData['mobile']==''||formData['password']==''){
			$('#msg').html('手机号和密码不能为空!');
			return;
		}
		if(formData['password'].length < 6 || formData['password'].length > 18){
			$('#msg').html('密码由6-18个字符组成，包括英文字母加数字或符号!');
			return; 
		}
		
		if(formData['userName']==''){
			$('#msg').html('昵称不能为空!');
			return;
		}
		
		if(formData['userName'].length < 3 || formData['userName'].length > 10){
			$('#msg').html('昵称长度在3-10个字符，包括小写字母、数字、下划线!');
			return; 
		}
		
		if(formData['validCode']==''){
			$('#msg').html('验证码不能为空!');
			return;
		}
		$.ajax({
			async : false,
			cache : false,
			type : 'POST',
			url : "<%=basePath%>register/saveUsers.shtml",// 请求的action路径
			data : formData,
			error : function() {// 请求失败处理函数
			},
			success : function(data) {
		  		if (data.success) {
		  			alert("注册成功!");
					window.location.href='<%=basePath%>'+"homepage/index.shtml";
				} else {
					$('#msg').html(data.msg);
				// 	$(".code_img").attr("src","${ctx}/ImageServlet?r="+new Date().getTime());
				}
			}
		});
   	}
   </script>
<title>注册</title>

<meta name="keywords" content="keyword1,keyword2,keyword3" />
<meta name="description" content="this is my page" />
<meta charset="UTF-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" />
<link rel="stylesheet" type="text/css" href="css/amazeui.css" />
<link rel="stylesheet" type="text/css" href="css/login.css" />
</head>

<body>

	<img class="login_logo" src="images/logo.png">

	<div class="login_tab">
		<a href="login.html" class="login_tab_unchecked_a">登录</a> <span
			class="dot">●</span> <a href="#" class="login_tab_checked_a">注册</a>
	</div>
	<div class="login_content">
		<form action="">
			<div class="am-form-group">
				<input type="text" class="am-form-field login_input" iscookie="true"
					name="mobile" id="mobile" placeholder="您的手机号">
			</div>
			<div class="am-form-group">
				<input value="" type="text" id="validCode" style="width: 140px"
					name="validCode" class="am-form-field login_input"
					placeholder="验证码"> <input type="button" id="sendSMS"
					value="获取验证码" />
			</div>
			<div class="am-form-group">
				<input type="text" id="userName" name="userName"
					class="am-form-field login_input" placeholder="请输入昵称">
			</div>

			<div class="am-form-group">
				<input type="password" id="password" name="password"
					class="am-form-field login_input" placeholder="密码">
			</div>
			<input type="button" class="default_btn am-disabled am-round"
				style="height:35px;width:240px;line-height: 10px;"
				onclick="register_it();" value="注册">
				<div class="am-form-group">
					<span id="msg"
						style="color: red;margin-left: 10px;text-align: left;"></span>
				</div>
		</form>
	</div>


</body>
</html>
