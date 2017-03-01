<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/pages/common/mytags.jsp" %>
<%@include file="/pages/common/commonjs_css.jsp" %>
<link rel="stylesheet" type="text/css" href="<%=basePath %>static/css/login.css">
<html>
  <head>
    <title>登录-法务助手</title>
  </head>
 <script type="text/javascript">
 	
 	function login_it(){
 		$('#msg').html("");
 		setCookie();
 		var actionurl=$('form').attr('action');//提交路径
		var checkurl=$('form').attr('check');//验证路径
		var formData = new Object();
		var data=$(":input").each(function() {
			formData[this.name] =$("#"+this.name ).val();
		});
		if(formData['mobile']==''|| formData['password']==''){
			$('#msg').html('手机号和密码不能为空!');
			return;
		}
	//	if(formData['validCode']==''){
	//		$('#msg').html('验证码不能为空!');
	//		return;
	//	}
		$.ajax({
			async : false,
			cache : false,
			type : 'POST',
			url : "<%=basePath%>login/checkUsers.shtml",// 请求的action路径
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
						$("#validCodeInput").show();
						$('#msg').html("用户名或密码错误!");
						$(".code_img").attr("src",basePath+"ImageServlet?r="+new Date().getTime()); 
					}else if(data.msg == '201'){
						$('#msg').html("验证码不能为空!");
					}else if(data.msg == '202'){
						$('#msg').html("验证码输入错误!");
						$(".code_img").attr("src",basePath+"ImageServlet?r="+new Date().getTime()); 
					}else{
						$('#msg').html("用户名或密码错误!"); 
						$(".code_img").attr("src",basePath+"ImageServlet?r="+new Date().getTime()); 
					}
					
				// 	$(".code_img").attr("src","${ctx}/ImageServlet?r="+new Date().getTime());
				}
			}
		});
   	}
 
 //设置cookie
function setCookie(){
 
	if ($("#on_off").prop("checked")) {
		$("input[iscookie='true']").each(function() {
			$.cookie(this.name, $("#"+this.name).val(), "/",24);
			$.cookie("COOKIE_NAME","true", "/",24);
		});
	} else {
		$("input[iscookie='true']").each(function() {
			$.cookie(this.name,null);
			$.cookie("COOKIE_NAME",null);
		});
	}
}
 
//读取cookie
function getCookie(){
	var COOKIE_NAME=$.cookie("COOKIE_NAME");
	if (COOKIE_NAME != null) {
		$("input[iscookie='true']").each(function() {
			$($("#"+this.name).val($.cookie(this.name)));
		});
		$("#on_off").attr("checked", true);
		$("#on_off").val("1");
	}else{
		$("#on_off").attr("checked", false);
		$("#on_off").val("0");
	}

} 
  
 </script>
  <body onload="getCookie();">
 	<img class="login_logo" src="<%=basePath %>static/images/logo.png" > 
 	<img class="login_title" src="<%=basePath %>static/images/login_title.png"  > 
  	<div class="login_tab">
		<span class="line left"></span>
		<a href="<%=basePath %>login.shtml" class="login_tab_checked_a">登录</a>
		<span class="dot">●</span>
		<a href="<%=basePath %>register.shtml" class="login_tab_unchecked_a">注册</a>
		<span class="line right"></span>
  	</div>
  	<div class="login_content" >
  		<form action="homepage/index.shtml"  >
  			<div class="am-form-group">
  				 <input type="text" class="am-form-field login_input" iscookie="true"  name="mobile" id="mobile" placeholder="您的手机号">
  			</div> 
  			<div class="am-form-group">
  				 <input type="password" class="am-form-field login_input" name="password" id="password" placeholder="密码">
  			 </div>
  			 <div class="am-form-group" 
  			 <c:if test="${validCodeKey==null || validCodeKey<3}">
  			 	 style="display:none" 
  			 </c:if>
  			  id="validCodeInput">
  				 <img src="<%=basePath %>ImageServlet" class="code_img"  alt="会员登录验证码" style="width:80px;height:35px;cursor: pointer;">	
  				 <input value="" type="text" style="width: 140px" class="am-form-field login_input" name="validCode" id="validCode" placeholder="验证码">
  			 </div>
  			<input type="button" class="default_btn am-disabled" style="height:35px;width:240px;line-height: 10px;" onclick="login_it();" value="登录">
			<div class="action"><input type="checkbox" id="on_off" name="on_off" style="float: left"><span>记住我</span><a href="<%=basePath %>login/findpwd.shtml">忘记密码</a></div>
			<div id="msg" class="msg"></div>
  		</form>	   
  	</div>
    <%@include file="/pages/common/common_bottom.jsp" %>
  </body>
  <script>
	$(".code_img").click(function(){
		$(".code_img").attr("src",basePath+"ImageServlet?r="+new Date().getTime());
	});
 </script>
</html>
