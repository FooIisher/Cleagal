<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/pages/common/mytags.jsp" %>
<%@include file="/pages/common/commonjs_css.jsp" %>
<link rel="stylesheet" type="text/css" href="<%=basePath %>static/css/login.css">
<html>
  <head>
   <script type="text/javascript">
   	function register_it(){
   		$('#msg').html('');
		var formData = new Object();
		var data=$(":input").each(function() {
			formData[this.name] =$("#"+this.name ).val();
		});
		if(formData['mobile']==''){
			$('#msg').html('手机号不能为空!');
			return;
		}
	 	if(formData['newPassword']=='' || formData['newPassword1']==''){
			$('#msg').html('密码不能为空!');
			return;
		}
		
	 	if(formData['newPassword'].length < 6 || formData['newPassword'].length > 18){
			$('#msg').html('密码由6-18个字符组成，包括英文字母加数字或符号!');
			return; 
		}
	 	
		if(formData['newPassword']!= formData['newPassword1']){
			$('#msg').html('两次密码不一致!');
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
			url : "<%=basePath%>login/resetpwd.shtml",// 请求的action路径
			data : formData,
			error : function() {// 请求失败处理函数
			},
			success : function(data) {
		  		if (data.success) {
		  			alert("修改成功,请重新登陆");
					window.location.href='<%=basePath%>'+"login.shtml";
				} else {
					$('#msg').html(data.msg);
				}
			}
		});
   	}
   </script>
  <title>注册-法务助手</title>
  </head>
  <body>
  	<img class="login_logo" src="<%=basePath %>static/images/logo.png" > 
 	<img class="login_title" src="<%=basePath %>static/images/login_title.png"  > 
 
  	 <div class="login_content" >
  		<form action="register.html"  method="post" id="register_form" >
  			<div class="am-form-group">
				<input type="text" name="mobile" id = "mobile" class="am-form-field login_input" placeholder="您的手机号">  		
			</div> 
  			<div class="am-form-group">
  				<input value="" type="text" id = "validCode" style="width: 140px " name="validCode" class="am-form-field login_input" placeholder="验证码">
  				<input type="button" id="sendSMS" value="点击获取验证码" />
  			 </div>
  		 
  			  <div class="am-form-group">
  			 	 <input type="password" id = "newPassword" name="newPassword" class="am-form-field login_input" placeholder="新密码">
  			 </div>
  			  <div class="am-form-group">
  			 	 <input type="password" id = "newPassword1" name="newPassword1" class="am-form-field login_input" placeholder="确认密码">
  			 </div>
  			<input type="button" class="default_btn" style="height:35px;width:240px;line-height: 10px;" onclick="register_it();" value="确定">
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
	            data: "telphone="+mobile+"&sendType=2",
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
