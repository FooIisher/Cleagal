<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">
<%@include file="/pages/common/mytags.jsp" %>
<%@include file="/pages/common/commonjs_css.jsp" %>
<link rel="stylesheet" type="text/css" href="<%=basePath %>static/css/login.css">
<html>
<head>
<title>登录-fish</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" />
</head>
<script type="text/javascript">
	function login_it() {
		$('#msg').html("");
		var actionurl=$('form').attr('action');//提交路径
		var formData = new Object();
		var data=$(":input").each(function() {
			formData[this.name] =$("#"+this.name ).val();
		});
		if(formData['mobile']==''|| formData['password']==''){
			$('#msg').html('手机号和密码不能为空!');
			return;
		}
		$.ajax({
			async : false,
			cache : false,
			type : 'POST',
			url : "<%=basePath%>mobile/checkUsers.shtml",// 请求的action路径
			data : formData,
			error : function() {// 请求失败处理函数
				alert("error");
			},
			success : function(data) {
		  		if (data.success) {
					var rurl = $.trim('${redirectURL}');
					if(rurl.length>0){
						window.location.href='${redirectURL}';
					}else{
						window.location.href='<%=basePath%>'+actionurl;
					}
				} else {
					if(data.msg == '3'){
						$('#msg').html("用户名或密码错误!");						
					}								
				}
			}
		});
 
	}

</script>

<body>

	<img class="login_logo" src="<%=basePath %>static/images/logo.png">
	
	<div class="login_tab">
		<a href="#" class="login_tab_checked_a">登录</a>
		 <span class="dot">●</span>
		<a href="<%=basePath %>register2.shtml" class="login_tab_checked_a">注册</a>
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
