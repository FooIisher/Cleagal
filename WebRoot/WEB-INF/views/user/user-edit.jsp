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
						'userName':{
							required:true,maxlength: 10
						},
						'individualResume':{maxlength: 200},
						'oneWord':{maxlength: 50},
						'company':{maxlength: 50},
						'school':{maxlength: 50},
						'profess':{maxlength: 50},
						'upload':{accept:"jpg,jpeg,png,gif"}
					},
					messages:{
						'userName':{required:"请输入昵称",maxlength:"不能超过10个字符"},
						'individualResume':{maxlength:"不能超过200个字符"},
						'oneWord':{maxlength: "不能超过50个字符"},
						'company':{maxlength: "不能超过50个字符"},
						'school':{maxlength: "不能超过50个字符"},
						'profess':{maxlength: "不能超过50个字符"},
						'upload':{accept:"图片格式错误,支持jpg,jpeg,png格式图片"}
					},
					success : function(label) {
						label.html("&nbsp;").addClass("checked");
					},
					highlight: function(element, errorClass) {
						$(element).parent().find("." + errorClass).removeClass("checked");
					}
				});
				
				$("#ifRenzhen").click(function(){
					if($(this).is(":checked")){
						$("#renzhengDiv").show();
						$("#renzhenFlag").val(1);
					}else{
						$("#renzhengDiv").hide();
						$("#renzhenFlag").val(0);
					}
				});
				
				$("#save").click(function(){
					 if($("#myform").valid()){
					 		$("#myform").submit();
					 }
				});
				$("#jobId").change(function(){
					 var cVal = $(this).val();
					 if(cVal == 'lawyer'){
					 	$("#ifRenzhenSpan").show();
					 	if($("#ifRenzhen").is(":checked")){
							$("#renzhengDiv").show();
						}else{
							$("#renzhengDiv").hide();
						}
					 }else{ 
					 	$("#ifRenzhenSpan").hide();
					 	$("#renzhengDiv").hide();
					 }
				});
				var province = $("#province").val();
				$("#city > option").hide();
				$("option[parentId='"+province+"']").show();
				$("#province").change(function(){
					var province = $("#province").val();
					$("#city > option").hide();
					$("#city").val("");
					$("option[parentId='"+province+"']").show();
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
		  				编辑个人资料
		  			</span>
		  			<a href="<%=basePath %>member/${UserSession.userId}.shtml">
		  			 返回个人主页
		  			</a>
		  		</div>
  			<iframe id="s_myform" name="s_myform" height="5px" width="5px" style="display:none;"></iframe>     
  			
   			<form class="am-form am-form-horizontal myForm" action="<%=basePath %>member/edit/users.shtml" id="myform" name="myform" target="s_myform"  enctype="multipart/form-data" method="post" >
			 
			 	<input type="hidden" name = "certifyStatus" value="${user.certifyStatus}"  id="certifyStatus" >
			  <div class="am-form-group">
			    <label for="doc-ipt-3" class="am-u-sm-2 am-form-label"  style="width: 20%">头像</label>
					<div class="am-u-sm-8 am-u-md-10 content"  style="width: 80%">
						<c:if test="${empty user.avatarFile}">
							<img id="img1" src="<%=basePath %>/static/images/default.jpg" class="am-img-thumbnail" width="120px" height="120px">
						</c:if>
						<c:if test="${not empty user.avatarFile}">
							<img id="img1" src="<%=basePath %>${user.avatarFile}" class="am-img-thumbnail" width="120px" height="120px">
						</c:if>
					    <input name="upload" id="upload" accept="image/*" value="选择图片" onChange="ajaxFileUpload('upload')" type="file" class="am-input-sm">
					   	<input type="hidden" name="avatarFile" id="avatarFile" value="${user.avatarFile}">
					    <lable id="loading" style="display:none"><i class="am-icon-spinner am-icon-spin"></i>上传中</lable>
					  </div>   
			  </div>
			
			  <div class="am-form-group">
			    <label for="doc-ipt-pwd-2" class="am-u-sm-2 am-form-label" style="width: 20%">昵称<font color="red">*</font></label>
			    <div class="am-u-sm-10 am-u-md-10 content" style="width: 80%">
			      <input type="text" name = "userName" value="${user.userName}"  id="userName" style="width: 70%" placeholder="请填写你的昵称">
			    </div>
			  </div>
			  
			  <div class="am-form-group">
			    <label for="doc-ipt-pwd-2" class="am-u-sm-2 am-form-label" style="width: 20%">一句话介绍</label>
			    <div class="am-u-sm-10 am-u-md-10 content" style="width: 80%">
			      <input type="text" id="oneWord" value="${user.oneWord}" name="oneWord" style="width: 70%" placeholder="一句话介绍">
			    </div>
			  </div>
			  
			  <div class="am-form-group">
			    <label for="doc-ipt-pwd-2" class="am-u-sm-2 am-form-label" style="width: 20%">个人简介</label>
			    <div class="am-u-sm-10 am-u-md-10 content" style="width: 80%">
			      	<textarea class="" id="individualResume"  name="individualResume" rows="3" style="width: 70%;resize:none" placeholder="个人简介" id="doc-ta-1">${user.individualResume}</textarea>
			    </div>
			  </div>
			  
			  
			  <div class="am-form-group ">
			    <label for="doc-ipt-pwd-2" class="am-u-sm-2 am-form-label" style="width: 20%">行业</label>
			    <div class="am-u-sm-10 content" style="width: 80%">
			    	<c:if test="${user.certifyStatus == 0 || user.certifyStatus == 3}">
				     	 <select name = "jobId" id="jobId" style="width: 70%">
					        <option value="" >请选择</option>
					        <c:forEach items="${listJob}" var="job">
					       		 <option value="${job.jobId}" <c:if test="${job.jobId == user.jobId}">selected</c:if> >${job.jobName}</option>
					         </c:forEach>
					      </select>
					  </c:if>
					  <c:if test="${user.certifyStatus == 1 || user.certifyStatus == 2}">
					  		<c:forEach items="${listJob}" var="job">
					       		 <c:if test="${job.jobId == user.jobId}">${job.jobName}</c:if>
					         </c:forEach>
					  </c:if> 
					 
				     <input type="hidden"  value="0" name="renzhenFlag"  id="renzhenFlag">
				     <span id="ifRenzhenSpan"  <c:if test="${user.jobId != 'lawyer'}"> style="display:none" </c:if> > 
					     <c:if test="${user.certifyStatus == 0 || user.certifyStatus == 3}">
					     	<input type="checkbox"  value="1" name="ifRenzhen"  id="ifRenzhen">是否认证律师
					     </c:if>
				     </span>
				     <c:if test="${user.certifyStatus == 1}">
				       		&nbsp;&nbsp;&nbsp;<font color="blue">正在审核</font>
				       </c:if>
				       <c:if test="${user.certifyStatus == 2}">
				       		&nbsp;&nbsp;&nbsp;<font color="green">已认证</font>
				       </c:if> 
				       <c:if test="${user.certifyStatus == 3}">
				       		&nbsp;&nbsp;&nbsp;<font color="red">未通过</font>
				       </c:if>    
				    	
			    </div>
			  </div>
			<div id = "renzhengDiv" <c:if test="${user.certifyStatus != 2 && user.certifyStatus != 1}">style="display:none"</c:if> >  
			   <div class="am-form-group">
			    <label for="doc-ipt-pwd-2" class="am-u-sm-2 am-form-label" style="width: 20%">认证姓名<font color="red">*</font></label>
			    <div class="am-u-sm-10 content" style="width: 80%">
			    	<c:if test="${user.certifyStatus == 0 || user.certifyStatus == 3}">
			      		<input type="text" value="${user.certifyCompany}" id="certifyName"  name = "certifyName" placeholder="认证">
			      	</c:if>	
			      	<c:if test="${user.certifyStatus == 1 || user.certifyStatus == 2}">
			      		 ${user.certifyName}111
			      	</c:if>	
			    </div>
			  </div>
			   <div class="am-form-group">
			    <label for="doc-ipt-pwd-2" class="am-u-sm-2 am-form-label" style="width: 20%">真实公司<font color="red">*</font></label>
			    <div class="am-u-sm-10 content">
			    	<c:if test="${user.certifyStatus == 0 || user.certifyStatus == 3}">
			     		 <input type="text" value="${user.certifyCompany}" id="certifyCompany"  name = "certifyCompany" placeholder="真实公司">
			      	</c:if>	
			      	<c:if test="${user.certifyStatus == 1 || user.certifyStatus == 2}">
			      		 ${user.certifyCompany}111
			      	</c:if>	
			    </div>
			  </div>
			   <div class="am-form-group">
			    <label for="doc-ipt-pwd-2" class="am-u-sm-2 am-form-label" style="width: 20%">认证资料<font color="red">*</font></label>
			    <div class="am-u-sm-10 content" style="width: 80%">
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
					        	上传您的律师职业资格证（支持jpg、png、jpeg、gif格式，不要超过2m） 暂时仅支持律师认证
					        </div>
			  	</c:if>
			      
			    </div>
			  </div>
			</div>	  
			<div class="am-form-group">
			    <label for="doc-ipt-pwd-2" class="am-u-sm-2 am-form-label" style="width: 20%">居住地</label>
			    <div class="am-u-sm-10 content" style="width: 80%">
			      <select id="province" name="province">
					<c:forEach items="${provinceList }" var="province">
						<option value="${province.id }"<c:if test="${user.province==province.id}">selected</c:if>>${province.name }</option>
					</c:forEach>
				  </select>
				  <select id="city" name="city">
				    <option value="">请选择</option>
					<c:forEach items="${cityList }" var="city">
						<option parentId="${city.parent_id }" value="${city.id }"<c:if test="${user.city==city.id}">selected</c:if>>${city.name }</option>
					</c:forEach>
				  </select>
			    </div>
			  </div>
			  <div class="am-form-group">
			    <label for="doc-ipt-pwd-2" class="am-u-sm-2 am-form-label" style="width: 20%">所属公司</label>
			    <div class="am-u-sm-10 content" style="width: 80%">
			      <input type="text" id="companyName" value="${user.companyName}" style="width: 70%" name = "companyName" placeholder="填写所属公司">
			    </div>
			  </div>
			  
			  <div class="am-form-group">
			    <label for="doc-ipt-pwd-2" class="am-u-sm-2 am-form-label" style="width: 20%">教育经历</label>
			    <div class="am-u-sm-10 content" style="width: 80%">
			      <input type="text" value="${user.school}"   id="school" name="school" placeholder="学校名称">
			      <input type="text" value="${user.profess}"   id="profess" name="profess" placeholder="专业(选填)">
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



