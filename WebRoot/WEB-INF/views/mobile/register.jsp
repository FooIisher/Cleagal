<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/pages/common/mytags.jsp" %>
<%@include file="/pages/common/commonjs_css.jsp" %>
<link rel="stylesheet" type="text/css" href="<%=basePath %>static/css/login.css">
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
    <title>注册-fish</title>
  </head>
  <body>
  	<img class="login_logo" src="<%=basePath %>static/images/logo.png" > 
 	<img class="login_title" src="<%=basePath %>static/images/login_title.png"  > 
  	<div class="login_tab">
		<span class="line left"></span>
		<a href="<%=basePath %>login2.shtml" class="login_tab_unchecked_a">登录</a>
		<span class="dot">●</span>
		<a href="#" class="login_tab_checked_a">注册</a>
		<span class="line right"></span>
  	</div>
  	 <div class="login_content" >
  		<form action="register.html"  method="post" id="register_form" >
  			<div class="am-form-group">
				<input type="text" name="mobile" id = "mobile" class="am-form-field login_input" placeholder="您的手机号">  		
			</div> 
  			<div class="am-form-group">
  				<input value="" type="text" id = "validCode" style="width: 140px" name="validCode" class="am-form-field login_input" placeholder="验证码">
  				<input type="button" id="sendSMS" value="点击获取验证码" />
  			 </div>
  			 <div class="am-form-group">
  			 	 <input type="text" id="userName" name="userName"  class="am-form-field login_input" placeholder="请输入昵称">
  			 </div>
  			
  			  <div class="am-form-group">
  			 	 <input type="password" id = "password" name="password" class="am-form-field login_input" placeholder="密码">
  			 </div>
  			<input type="button" class="default_btn" style="height:35px;width:240px;line-height: 10px;" onclick="register_it();" value="注册">
  			<div class="am-form-group">
  				 <span id="msg" style="color: red;margin-left: 10px;text-align: left;"></span>
  			 </div>
  		</form>	   
  	</div>
<script type="text/javascript">
$(function(){
		var interval_;
		$("#telphone").focus(function(){
			$("#telphoneMsg").remove();
		});
		$("#sendSMS").click(function(){
			var mobile = $("#mobile").val();
			$("#sendSMS").attr("disabled",true);
			$.ajax({
	            type: "post",
	            url: "<%=basePath%>register/getSmsCode.shtml",
	            data: "telphone="+mobile,
	            dataType: "json",
	            success: function(msg){
	            	if(msg.codenum!=200){
	            		$("#sendSMS").attr("disabled",false);
	            		$("#msg").html(msg.msg);
	            	}else{
	            		$("#msg").html(msg.msg);
	           			interval_ = window.setInterval(_timeout_,1000);
	           			
	            	}
	            }
	        });
		});
		
		var timeout = 60;
		function _timeout_(){
			if (timeout > 0){
				timeout--;
				$("#sendSMS").attr("value",timeout+"秒后重新获取");
			}
			if(timeout == 0){
				$("#sendSMS").attr("value","重新获取验证码");
				$("#sendSMS").attr("disabled",false);
				 window.clearInterval(interval_);
				 timeout = 60;
			}
		}
  });
  
 

</script>   
   
  <%@include file="/pages/common/common_bottom.jsp" %>
  </body>
</html>
