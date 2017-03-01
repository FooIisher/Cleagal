<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/pages/common/mytags.jsp" %>
<%@include file="/pages/common/commonjs_css.jsp" %>
<html>
  <head>
    <title>首页-法务助手</title>
     <link rel="stylesheet" type="text/css" href="<%=basePath %>static/css/user.css">
     <script type="text/javascript" src="<%=basePath %>static/js/jquery/ajaxfileupload.js"></script>
     <script type="text/javascript" src="<%=basePath %>static/js/jquery/jquery.validate.min.js"></script>
     <script type="text/javascript" src="<%=basePath %>static/js/jquery/message_cn.js"></script>
  	<script type="text/javascript">
  		$(function(){
  				$("#myform").validate({
					rules: {
						'oldpassword':{
							required:true,maxlength: 18,minlength: 6
						},
						'newpassword1':{
							required:true,maxlength: 18,minlength: 6
						},
						'newpassword2':{
							required:true,maxlength: 18,minlength: 6,equalTo:"#newpassword1" 
						}
					},
					messages:{
						'oldpassword':{required:"请输入原密码",maxlength:"不能超过18个字符",minlength:"大于等于6个字符"},
						'newpassword1':{required:"请输入新密码",maxlength:"不能超过18个字符",minlength:"大于等于6个字符"},
						'newpassword2':{required:"请输入确认密码",maxlength:"不能超过18个字符",minlength:"大于等于6个字符",equalTo:"两次密码必须一致"},
					},
					success : function(label) {
						label.html("&nbsp;").addClass("checked");
					},
					highlight: function(element, errorClass) {
						$(element).parent().find("." + errorClass).removeClass("checked");
					}
				});
				
				
				$("#save").click(function(){
					 var formData = new Object();
					 formData["oldpassword"] =$("#oldpassword").val();
					 formData["newpassword"] =$("#newpassword1").val();
					 if($("#myform").valid()){
					 	$.ajax({
							async : false,
							cache : false,
							type : 'POST',
							url : "<%=basePath%>member/updatePassword.shtml",// 请求的action路径
							data : formData,
							error : function() {// 请求失败处理函数
								alert("error");
							},
							success : function(data) {
						  		if (data.success) {
						  			alert("修改成功!");
						  			window.location.href=basePath +"member/${UserSession.userId}.shtml"
								} else {
									alert(data.msg);
								// 	$(".code_img").attr("src","${ctx}/ImageServlet?r="+new Date().getTime());
								}
							}
						});
					 }
				});
				 
  		});
		var callback = function(msg){
			if(msg == "success"){
				window.location.href=basePath +"member/${UserSession.userId}.shtml"
			}else{
				alert("提交成功失败");
			}
		}
  	</script>
  </head>
  <body>
  	<%@include file="/pages/common/common_top.jsp" %>
  	<div class="fwzs_content">
  	<div class="fwzs_content_left">
  	<div class="user_sz">
  	<div class="user_edit">
  		<div class="title">
  			<span class="memtion">
  				修改密码
  			</span>
  			<a href="<%=basePath %>member/${UserSession.userId}.shtml">
  			 返回个人主页
  			</a>
  		</div>
  		<iframe id="s_myform" name="s_myform" height="5px" width="5px" style="display:none;"></iframe>     
   		<form class="am-form am-form-horizontal myForm"  id="myform" name="myform" target="s_myform"  enctype="multipart/form-data" method="post" >
			 
			<div id = "renzhengDiv"  >  
			   <div class="am-form-group">
			    <label for="doc-ipt-pwd-2" class="am-u-sm-2 am-form-label ">当前密码</label>
			    <div class="am-u-sm-10 content">
			      		<input style="width: 50%" type="password"  id="oldpassword"  name = "oldpassword" placeholder="请输入当前密码">
			    </div>
			  </div>
			   <div class="am-form-group">
			    <label for="doc-ipt-pwd-2" class="am-u-sm-2 am-form-label">新密码</label>
			    <div class="am-u-sm-10 content">
			     		 <input style="width: 50%" type="password"  id="newpassword1"  name = "newpassword1" placeholder="请输入6-18位密码">
			    </div>
			  </div>
			   <div class="am-form-group">
			    <label for="doc-ipt-pwd-2" class="am-u-sm-2 am-form-label">确认密码</label>
			    <div class="am-u-sm-10 content">
				 	<input style="width: 50%" type="password"   id="newpassword2"  name = "newpassword2" placeholder="请输入6-18位密码">
			    </div>
			  </div>
			</div>	  
				 <div class="am-form-group">
				    <div class="am-u-sm-10 am-u-sm-offset-4" >
				    	<input type="button" id="save" class="default_btn am-disabled" style="height:35px;width:240px;line-height: 10px;" onclick="login_it();" value="提交保存">
				    </div>
				  </div>
			</form>
		</div>	
   	</div>
  		</div><div class="fwzs_content_right"><%@include file="/pages/common/common_right_user.jsp" %></div>
  	</div>
  	<%@include file="/pages/common/common_bottom.jsp" %>
  </body>
</html>



