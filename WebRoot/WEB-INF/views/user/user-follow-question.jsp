<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/pages/common/mytags.jsp" %>
<%@include file="/pages/common/commonjs_css.jsp" %>
<html>
  <head>
    <title>关注的问题-法务助手</title>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>static/css/search.css">
<link rel="stylesheet" type="text/css" href="<%=basePath %>static/css/user.css">
   <script src="<%=basePath %>static/js/question.js"></script>
  </head>
  <script type="text/javascript">
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
  		<div class="fwzs_content_left" >
  		 	<div class="user_follow_questiton">
  		 		<c:if test="${listQuestion.size() == 0}"><center style="font-size: 16px">请先去关注问题</center></c:if>
  		 		<ul class="user_follow_questiton_ul">
  		 		<c:forEach items="${listQuestion }" var="question">
  		 			<li>
  		 				<div class="user_follow_questiton_info">
  		 					<div class="user_follow_questiton_left">
  		 							<div   class="search_question_zan" >
  		 							<div>
  		 								${question.aviewCount}
  		 							</div>
  		 							<div>
  		 								浏览
  		 							</div>
  		 						</div>
  		 					</div>
  		 					<div class="user_follow_questiton_right" style="width: 560px">
  		 						<a class="user_follow_questiton_name" href="<%=basePath %>question/detail${question.questionId}.shtml">
  		 						 	 ${question.questionTitle} 
  		 						</a>
  		 						<div class="user_follow_questiton_data">
  		 						<span id="gzHtml${question.questionId}">
  		 							<c:if test="${question.followFlag == 1}">
  		 						 		<a  href="javascript:userFollowQuestion('${question.questionId}');">取消关注</a>
  		 						 	</c:if>	
  		 						 	<c:if test="${question.followFlag == 0}">
  		 						 		<a  href="javascript:unfollowQuestion('${question.questionId}');">添加关注</a>
  		 						 	</c:if>	
  		 						</span> 	
  		 							<span class="search_dot">●</span>
  		 							${question.commentCount}个评论
  		 						</div>
  		 					</div>
  		 				</div>
  		 			</li>
  		 		</c:forEach>	 
  		 		</ul>
  		 	</div>
  		</div><div class="fwzs_content_right"><%@include file="/pages/common/common_right.jsp" %></div>
  	</div>
  	<%@include file="/pages/common/common_bottom.jsp" %>
  </body>
</html>
