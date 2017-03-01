<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/pages/common/mytags.jsp" %>
<%@include file="/pages/common/commonjs_css.jsp" %>
<html>
  <head>
    <title>案例-法务助手</title>
   
    <link rel="stylesheet" type="text/css" href="<%=basePath %>static/css/homepage.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>static/css/user.css">
    <script src="<%=basePath %>static/js/case.js"></script>
  </head>
  <script type="text/javascript">
   $(function(){
  		$(".fwzs_sc").click(function(){
  			if(isLogin == false){
  				loginDialog();
  				return;
  			}
  			var soon = $(this);
  			var caseId = soon.attr("id");
  			var classaa= soon.html();
  			if(classaa.trim() == '添加关注'){
  				followCase(caseId,function(result,caseId){
  					if(result == 'true'){
		  				 soon.html("取消关注");
		  			}else{
		  				alert("操作失败!");
		  			}
  				}); 
  			}else{
  				disFollowCase(caseId,function(result,caseId){
  					if(result == 'true'){
		  				 soon.html("添加关注");
		  			}else{
		  				alert("操作失败!");
		  			}
  				}); 
  			} 
  		});
  		
  })
  </script>

  <body>
  	<%@include file="/pages/common/common_top.jsp" %>
  	<div class="fwzs_content">
  		<div class="fwzs_content_left">
  			<%@include file="user_center.jsp" %>	
  			<div class="user_question">
  		 		<ul class="user_question_ul">
  		 		 	<li class="title">
  		 		 		<span class="memtion">
			  				<c:if test="${user.userId==UserSession.userId }">您</c:if>
			  				<c:if test="${user.userId!=UserSession.userId }">${user.userName }</c:if>的案例
			  			</span>
			  			<div class="paixu">
			  			 <c:if test="${user.userId == UserSession.userId}">
			  				<a href="<%=basePath %>case/addCase.shtml?orderBy=agreeCount" class="active" >
				  			  上传案例
				  			</a>
				  		</c:if>	
			  			</div>
			  			
  		 		 	</li>
	  		 		<c:forEach items="${listCase}" var="cases">
	  		 			<li>
	  		 				<div class="user_question_title am-text-truncate"> 
	  		 					<a  href="<%=basePath %>case/detail${cases.caseId}.shtml">${cases.caseTitle}</a>
	  		 				</div>
	  		 				<div class="user_question_info">
	  		 					<div>
	  		 						<span class="user_question_time">${cases.publishTime}</span>
	  		 						<div class="user_question_name">
	  		 							${cases.userName} ${cases.oneWord}
		  		 					</div> 
	  		 						<div class="user_question_content"><%--
	  		 							${cases.caseContent}  
	  		 								<span class="user_question_showall">显示全部</span>
	  		 						--%></div>
	  		 						<div class="user_info">
	  		 							评论${cases.commentCount}  
		  		 						<span class="user_dot">●</span>
		  		 							<c:if test="${cases.followFlag == 1}">
						  					<span id="${cases.caseId}"  class="fwzs_sc">取消关注</span>
						  				</c:if>
						  				<c:if test="${cases.followFlag == 0}">
						  					<span id="${cases.caseId}" class="fwzs_sc">添加关注</span>
						  				</c:if>
	  		 						</div>
	  		 					</div>
	  		 				</div>
	  		 			</li>
	  		 		</c:forEach>	
  		 		</ul>
  		 	</div>
	  	 </div>	 
  		</div><div class="fwzs_content_right"><%@include file="/pages/common/common_right_user.jsp" %></div>
  	</div>
  	<%@include file="/pages/common/common_bottom.jsp" %>
  </body>
</html>
