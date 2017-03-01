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
     
         <link href="<%=basePath %>umeditor1_2_2/themes/default/css/umeditor.css" type="text/css" rel="stylesheet">
    <script type="text/javascript" charset="utf-8" src="<%=basePath %>umeditor1_2_2/umeditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath %>umeditor1_2_2/umeditor.min.js"></script>
    <script type="text/javascript" src="<%=basePath %>umeditor1_2_2/lang/zh-cn/zh-cn.js"></script>
  	<script type="text/javascript">
  		$(function(){
  				var um = UM.getEditor('myEditor');
  				$("#myform").validate({
					rules: {
						'caseTitle':{
							required:true,maxlength: 50
						},
						'upload':{accept:"jpg,jpeg,png,gif"}
					},
					messages:{
						'caseTitle':{required:"请输入昵称"}
					},
					success : function(label) {
						label.html("&nbsp;").addClass("checked");
					},
					highlight: function(element, errorClass) {
						$(element).parent().find("." + errorClass).removeClass("checked");
					}
				});
			 
				$("#save").click(function(){
					$("#myEditor").val(UM.getEditor('myEditor').getContent());
			    	if(!UM.getEditor('myEditor').hasContents()){
			    		alert("请输入您的内容");
			    		return;
			    	}
					 if($("#myform").valid()){
					 		$("#myform").submit();
					 }
				});
				 
  		});
  		function ajaxFileUpload() { 
			$("#save").attr("disabled","disabled");
			$("#loading").show();
			$.ajaxFileUpload({  
		        url : 'uploadHeadImg.shtml',// servlet请求路径  
		        secureuri : false,  
		        fileElementId : "upload",// 上传控件的id  
		        dataType : 'text',  
		        data : "", // 其它请求参数  
		        success : function(data, status) { 
		        	var reg = /<pre.+?>(.+)<\/pre>/g;  
					var result = data.match(reg);  
					data = RegExp.$1;
					var obj = eval('(' + data + ')');;
					var msg = obj.data.up;
					if(msg.state == "SUCCESS"){
							$("#imgUrl").val(msg.url);
			        		$("#img1").attr("src", msg.basePath+msg.url);
			        	 
					}else{
						alert("上传失败,请稍后再试！");
					}
					$("#save").removeAttr("disabled");
		    		$("#loading").hide();
		        },  
		        error : function(data, status, e) {  
		        	$("#save").removeAttr("disabled");
		    		$("#loading").hide();
		            alert('上传出错');  
		        }  
		    });
		}
		var callback = function(msg){
			if(msg == "success"){
				window.location.href=basePath +"member/${UserSession.userId}.shtml"
			}else{
				alert("修改失败");
			}
		}
  	</script>
  </head>
  <body>
  	<%@include file="/pages/common/common_top.jsp" %>
  	<div class="fwzs_content">
  		<div class="fwzs_content_left">
  		<div class="user_edit">	
  				<div class="title">
		  			<span class="memtion">
		  				添加案例
		  			</span>
		  			<a href="<%=basePath %>member/${UserSession.userId}.shtml">
		  			 返回个人主页
		  			</a>
		  		</div>
  	 
   			<form class="am-form am-form-horizontal myForm" action="<%=basePath %>case/saveCase.shtml" id="myform" name="myform"  enctype="multipart/form-data" method="post" >
			  <div class="am-form-group" >
			    <label for="doc-ipt-pwd-2" class="am-u-sm-2 am-form-label"  style="width: 20%">案例标题</label>
			    <div class="am-u-sm-10 am-u-md-10 content"  style="width: 80%">
			      <input type="text" name = "caseTitle" value="" style="width:300px"  id="caseTitle" placeholder="请填写案例标题">
			    </div>
			  </div>
			 
			  <div class="am-form-group">
			    <label for="doc-ipt-3" class="am-u-sm-2 am-form-label"  style="width: 20%">图片</label>
					<div class="am-u-sm-8 am-u-md-10 content"  style="width: 80%"> 
						<img id="img1" src="/static/images/default.jpg" class="am-img-thumbnail" width="120px" height="120px">
					    <input name="upload" id="upload" accept="image/*" value="选择图片" onChange="ajaxFileUpload()" type="file" class="am-input-sm">
					   	<input type="hidden" name="imgUrl" id="imgUrl" >
					    <lable id="loading" style="display:none"><i class="am-icon-spinner am-icon-spin"></i>上传中</lable>
					</div>   
			  </div>
			  
			  
			   <div class="am-form-group" >
			    <label for="doc-ipt-pwd-2" class="am-u-sm-2 am-form-label"  style="width: 20%">案例内容</label>
			    <div class="am-u-sm-10 am-u-md-10 content"  style="width: 80%">
			       <textarea name="caseContent" id="myEditor" style="width:100%;height:240px;"></textarea>
			    </div>
			  </div>
			 
			
			
			
			<div class="am-form-group">
			 <label for="doc-ipt-pwd-2" class="am-u-sm-2 am-form-label" style="width: 20%">标签</label>
			  <div class="am-u-sm-8 am-u-md-10 content" style="width: 80%">
			   		<c:forEach items = "${topicList}"  var="soon">
			   		<div class="am-g am-margin-top">
					  <div class="am-u-sm-2 am-u-md-1.5 am-text-right">${soon.topicTitle}:</div>
					  <div class="am-u-sm-8 am-u-md-10">
					   		<div class="am-form-group">
					   		<c:forEach items="${soon.listTopics}"   var="soon2"  >
						      &nbsp;&nbsp;<input type="checkbox" width="10px" value="${soon2.topicId}" name="topicIds">${soon2.topicTitle}
						    </c:forEach>
						    </div>
					  </div>
					</div>  
					</c:forEach> 
			  </div>
			</div>	
				
			
			  <div class="am-form-group">
			    <div class="am-u-sm-10 am-u-sm-offset-4 content">
			    	<input type="button" id="save" class="default_btn am-disabled" style="height:35px;width:240px;line-height: 10px;" onclick="login_it();" value="提交保存">
			    </div>
			  </div>
			</form>
   		 	</div>
  		</div><div class="fwzs_content_right"><%@include file="/pages/common/common_right_user.jsp" %></div>
  	</div>
  	<%@include file="/pages/common/common_bottom.jsp" %>
  </body>
</html>



