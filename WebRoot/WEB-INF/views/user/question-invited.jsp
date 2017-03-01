<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/pages/common/mytags.jsp" %>
<%@include file="/pages/common/commonjs_css.jsp" %>
<html>
  <head>
    <title>邀请回答的问题-法务助手</title>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>static/css/search.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>static/css/user.css">
  </head>
  <script type="text/javascript">
  
  </script>
  <body>
  	<%@include file="/pages/common/common_top.jsp" %>
  	<div class="fwzs_content">
  		<div class="fwzs_content_left" >
  		 		<div class="user_follow_questiton">
  		 		<c:if test="${listQuestionInvite.size() == 0}"><center style="font-size: 16px">没有邀请你回答问题</center></c:if>
  		 		<ul class="user_follow_questiton_ul">
  		 		<c:forEach items="${listQuestionInvite}" var="question">
  		 			<li>
  		 				<div class="user_follow_questiton_info">
   		 					<div>
  		 						<div class="user_follow_questiton_name" onclick="javascript:window.open('<%=basePath %>question/detail${question.questionId}.shtml')">
  		 						 	 ${question.questionTitle} 
  		 						</div>
  		 						<div class="user_follow_questiton_data">
  		 							<a href="<%=basePath %>/member/${question.senderUid}.shtml">${question.senderUser} </a>
  		 							 邀请你回答问题
  		 							 &nbsp;${question.answerCount}个答案
  		 							<span class="search_dot">●</span>
  		 							${question.followCount}人关注
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
