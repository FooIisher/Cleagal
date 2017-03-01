<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/pages/common/mytags.jsp" %>
<%@include file="/pages/common/commonjs_css.jsp" %>
<html>
  <head>
    <title>提问-法务助手</title>
   
    <link rel="stylesheet" type="text/css" href="<%=basePath %>static/css/homepage.css">
     <link rel="stylesheet" type="text/css" href="<%=basePath %>static/css/user.css">
       <script src="<%=basePath %>static/js/answer.js"></script>
       <script src="<%=basePath %>static/js/question.js"></script>
  </head>
  <script type="text/javascript">
  $(function(){
  		$(".fwzs_zan").click(function(){
  			if(isLogin == false){
  				loginDialog();
  				return;
  			}
  			var soon = $(this);
  			var answerId = soon.attr("id");
  			var classaa= soon.attr("class");
  			if(classaa.trim() != 'fwzs_zan fwzs_zan_active'){
  				praiseAnswer(answerId,function(result,answerId){
  					if(result == 'true'){
      				   soon.attr("class","fwzs_zan fwzs_zan_active");
      				   var zanCount = parseInt($("#zanCount"+answerId).html())+1;
      				   $("#zanCount"+answerId).html(zanCount);
  					}else{
  						alert("操作失败!");
  					}
  				}); 
  			}else{
  				disPraiseAnswer(answerId,function(result,answerId){
  					if(result == 'true'){
  						 soon.attr("class","fwzs_zan ");
  						 var zanCount = parseInt($("#zanCount"+answerId).html())-1;
      				  	 $("#zanCount"+answerId).html(zanCount);
  					}else{
  						alert("操作失败!");
  					}
  				}); 
  			} 
  		});
  		
  });
  function userFollowQuestion(questionId){
  	if(isLogin == false){
  				loginDialog();
  				return;
  			}
  	followQuestion(questionId,function(result,questionId){
  			if(result == 'true'){
  				$("#gzHtml"+questionId).html("<a  href=\"javascript:unfollowQuestion('"+questionId+"');\">取消关注</a>");
  			}else{
  				alert("操作失败!");
  			}
  	});
  }
  function unfollowQuestion(questionId){
  if(isLogin == false){
  				loginDialog();
  				return;
  			}
  	disFollowQuestion(questionId,function(result,questionId){
  			if(result == 'true'){
  				$("#gzHtml"+questionId).html("<a  href=\"javascript:userFollowQuestion('"+questionId+"');\">添加关注</a>");
  			}else{
  				alert("操作失败!");
  			}
  	});
  }
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
			  				<c:if test="${user.userId!=UserSession.userId }">${user.userName }</c:if>的精彩提问
			  			</span>
			  			<div class="paixu">
			  				排序方式:
			  				<a href="<%=basePath %>member/${UserSession.userId}/asks.shtml"<c:if test="${orderBy=='time'}"> class="active"</c:if>>
				  			  提问时间
				  			</a>
			  				<a href="<%=basePath %>member/${UserSession.userId}/asks.shtml?orderBy=answerCount"<c:if test="${orderBy=='answerCount'}"> class="active"</c:if> >
				  			  回答数量
				  			</a>
			  				
			  			</div>
			  			
  		 		 	</li>
	  		 		<c:forEach items="${listQuestion}" var="question">
	  		 			<li>
	  		 				<div class="user_question_title am-text-truncate"> 
	  		 					<a  href="<%=basePath %>question/detail${question.questionId}.shtml">${question.questionTitle}</a>
	  		 				</div>
	  		 				<div class="user_question_info">
	  		 				<c:if test="${question.answerId != null}">
	  		 					<div class="user_question_left"> 
	  		 						 <span id="${question.answerId}" class="fwzs_zan <c:if test="${question.praiseFlag == 1}">fwzs_zan_active</c:if>"><font id ="zanCount${question.answerId}">${question.agreeCount}</font></span>
	  		 					</div>
	  		 				</c:if>	
	  		 					<div 
	  		 						<c:if test="${question.answerId != null}">
	  		 							class="user_question_right"
	  		 						</c:if>
	  		 					>
	  		 						<span class="user_question_time">${question.addTime}</span>
	  		 						<div class="user_question_name">
	  		 							${question.userName}  ${question.oneWord}  
	  		 						</div>
	  		 						<div class="user_question_content">
	  		 							<c:if test="${question.verifyStatus == 0}">
	  		 								<span style="color: gray" class="user_question_showall">审核中</span>
	  		 							</c:if>
	  		 							<c:if test="${question.verifyStatus == 1}">
	  		 								<span style="color: green" class="user_question_showall">已通过</span>
	  		 							</c:if>
	  		 							<c:if test="${question.verifyStatus == 2}">
	  		 								<span class="user_question_showall">未通过</span>
	  		 							</c:if>
	  		 						</div>
	  		 						<div class="user_info">
	  		 							评论${question.commentCount}  
	  		 						<span class="user_dot">●</span>
	  		 							<span id = "gzHtml${question.questionId}">
	  		 							
	  		 						 		<c:if test="${question.followFlag == 1}">
		  		 								<a  href="javascript:unfollowQuestion('${question.questionId}');">取消关注</a>
		  		 						 	</c:if>	
		  		 						 	<c:if test="${question.followFlag == 0}">
		  		 						 		<a  href="javascript:userFollowQuestion('${question.questionId}');">添加关注</a>
		  		 						 	</c:if>	
	  		 						 		
	  		 							</span>	
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
