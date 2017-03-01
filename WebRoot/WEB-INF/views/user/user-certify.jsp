<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/pages/common/mytags.jsp" %>
<%@include file="/pages/common/commonjs_css.jsp" %>
<html>
  <head>
    <title>认证-法务助手</title>
     <link rel="stylesheet" type="text/css" href="<%=basePath %>static/css/user.css">
     <script type="text/javascript" src="<%=basePath %>static/js/jquery/ajaxfileupload.js"></script>
     <script type="text/javascript" src="<%=basePath %>static/js/jquery/jquery.validate.min.js"></script>
     <script type="text/javascript" src="<%=basePath %>static/js/jquery/message_cn.js"></script>
  	<script type="text/javascript">
  		$(function(){
  				$("#myform").validate({
					rules: {
						'certifyName':{
							required:true,maxlength: 10
						},
						'certifyCompany':{
							required:true,maxlength: 50
						},
						'certifyFile':{accept:"jpg,jpeg,png,gif"}
					},
					messages:{
						'certifyName':{required:"请输入昵称",maxlength:"认证姓名不能超过10个字符"},
						'certifyCompany':{required:"请输入昵称",maxlength:"认证公司不能超过50个字符"},
						'upload':{accept:"图片格式错误,支持jpg,jpeg,png格式图片"}
					},
					success : function(label) {
						label.html("&nbsp;").addClass("checked");
					},
					highlight: function(element, errorClass) {
						$(element).parent().find("." + errorClass).removeClass("checked");
					}
				});
				
				
				$("#save").click(function(){
					 if($("#myform").valid()){
					 		$("#myform").submit();
					 }
				});
				 
  		});
  		function ajaxFileUpload(uploadId) { 
			$("#save").attr("disabled","disabled");
			$("#loading").show();
			$.ajaxFileUpload({  
		        url : 'uploadHeadImg.shtml',// servlet请求路径  
		        secureuri : false,  
		        fileElementId : uploadId,// 上传控件的id  
		        dataType : 'text',  
		        data : "", // 其它请求参数  
		        success : function(data, status) { 
		        	var reg = /<pre.+?>(.+)<\/pre>/g;  
					var result = data.match(reg);  
					data = RegExp.$1;
					var obj = eval('(' + data + ')');;
					var msg = obj.data.up;
					if(msg.state == "SUCCESS"){
						if(uploadId == 'upload'){
							$("#avatarFile").val(msg.url);
			        		$("#img1").attr("src", msg.basePath+msg.url);
			        	}else if(uploadId == 'RenzhenUpload'){
			        		$("#certifyFile").val(msg.url);
			        		$("#img2").attr("src", msg.basePath+msg.url);
			        	}	
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
				alert("提交成功失败");
			}
		}
  	</script>
  </head>
  <body>
  	<%@include file="/pages/common/common_top.jsp" %>
  	<div class="fwzs_content">
  	
  	<div class="fwzs_content_left">
  		
  		
  	<div class="user_certify">
  	
  		<div class="user_certify_title">
  			<span class="certify_title_active">提交资料</span>
  			<c:if test="${user.certifyStatus == 1 || user.certifyStatus == 2}">
  				<img src="<%=basePath %>static/images/arrow_red.png" class="certify_jiantou"/>
  			</c:if>
  			<c:if test="${user.certifyStatus == 0 || user.certifyStatus == 3}">
  				<img src="<%=basePath %>static/images/arrow_white.png" class="certify_jiantou"/>
  			</c:if>		
  			<span <c:if test="${user.certifyStatus == 1 || user.certifyStatus == 2}">
  			 class="certify_title_active"
  			 </c:if>
  			 >资料审核</span>
  			 <c:if test="${user.certifyStatus == 2}">
  				<img src="<%=basePath %>static/images/arrow_red.png" class="certify_jiantou"/>
  			</c:if>	
  			 <c:if test="${user.certifyStatus != 2}">
  			 	<img src="<%=basePath %>static/images/arrow_white.png" class="certify_jiantou"/>
  			 </c:if>
  			<span <c:if test="${user.certifyStatus == 2}">class="certify_title_active"</c:if> >完成认证</span>
  		</div>
  		<c:if test="${user.certifyStatus == 3 }">
  			<center style="color: red;font-size: 14px;margin-top: 20px">审核未通过：${user.certifyVerify}</center> 
  		</c:if>
  	<div class="user_edit">
  		
  		<div class="title">
  			<span class="memtion">
  				提交认证资料
  			</span>
  			<a href="<%=basePath %>member/${UserSession.userId}.shtml">
  			 返回个人主页
  			</a>
  		</div>
  		<iframe id="s_myform" name="s_myform" height="5px" width="5px" style="display:none;"></iframe>     
   		<form class="am-form am-form-horizontal myForm" action="<%=basePath %>member/certify/edit.shtml" id="myform" name="myform" target="s_myform"  enctype="multipart/form-data" method="post" >
			 <input type="hidden" name = "certifyStatus" value="${user.certifyStatus}"  id="certifyStatus" >
			 
			<div id = "renzhengDiv"  >  
			   <div class="am-form-group">
			    <label for="doc-ipt-pwd-2" class="am-u-sm-2 am-form-label ">认证姓名<font color="red">*</font></label>
			    <div class="am-u-sm-10 content">
			    	<c:if test="${user.certifyStatus == 0 || user.certifyStatus == 3}">
			      		<input type="text" style="width: 70%" value="${user.certifyCompany}" id="certifyName"  name = "certifyName" placeholder="认证">
			      	</c:if>	
			      	<c:if test="${user.certifyStatus == 1 || user.certifyStatus == 2}">
			      		 ${user.certifyName}
			      	</c:if>	
			    </div>
			  </div>
			   <div class="am-form-group">
			    <label for="doc-ipt-pwd-2" class="am-u-sm-2 am-form-label">真实公司<font color="red">*</font></label>
			    <div class="am-u-sm-10 content">
			    	<c:if test="${user.certifyStatus == 0 || user.certifyStatus == 3}">
			     		 <input type="text" style="width: 70%" value="${user.certifyCompany}" id="certifyCompany"  name = "certifyCompany" placeholder="真实公司">
			      	</c:if>	
			      	<c:if test="${user.certifyStatus == 1 || user.certifyStatus == 2}">
			      		 ${user.certifyCompany}
			      	</c:if>	
			    </div>
			  </div>
			   <div class="am-form-group">
			    <label for="doc-ipt-pwd-2" class="am-u-sm-2 am-form-label">认证资料<font color="red">*</font></label>
			    <div class="am-u-sm-10 content">
			    <c:if test="${user.certifyStatus == 1 || user.certifyStatus == 2}">
			    		<img id="img2" src="<%=basePath %>${user.certifyFile}" class="am-img-thumbnail" width="120px" height="120px">
			    </c:if>	
			  	<c:if test="${user.certifyStatus == 0 || user.certifyStatus == 3}">
			  			 <c:if test="${empty user.avatarFile}">
							<img id="img2" src="<%=basePath %>/static/images/default.jpg" class="am-img-thumbnail" width="120px" height="120px">
						</c:if>
						<c:if test="${not empty user.avatarFile}">
								<img id="img2" src="<%=basePath %>${user.certifyFile}" class="am-img-thumbnail" width="120px" height="120px">
						</c:if>
						<input type="file" id="RenzhenUpload" onChange="ajaxFileUpload('RenzhenUpload')" name = "upload" >
					     <input type="hidden" id="certifyFile" value="${user.certifyFile}" name = "certifyFile" >
					     	 <div>
					        	上传您的律师职业资格证（支持jpg、png、jpeg、gif格式，不要超过2m，建议大小200px*200px） 暂时仅支持律师认证
					        </div>
			  	</c:if>
			       <lable id="loading" style="display:none"><i class="am-icon-spinner am-icon-spin"></i>上传中</lable>
			    </div>
			  </div>
			</div>	  
				 <div class="am-form-group">
				    <div class="am-u-sm-10 am-u-sm-offset-4" >
				    <c:if test="${user.certifyStatus == 0 || user.certifyStatus == 3}">
				    	<input type="button" id="save" class="default_btn am-disabled" style="height:35px;width:240px;line-height: 10px;" onclick="login_it();" value="提交保存">
				    </c:if>
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



