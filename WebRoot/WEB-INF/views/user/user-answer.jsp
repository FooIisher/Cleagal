<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/pages/common/mytags.jsp" %>
<%@include file="/pages/common/commonjs_css.jsp" %>
<html>
  <head>
    <title>回答-法务助手</title>
   
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
  		$(".fwzs_sc").click(function(){
  			var soon = $(this);
  			var answerId = soon.attr("id");
  			var classaa= soon.html();
  			if(classaa.trim() == '收藏'){
  				followAnswer(answerId,function(data){
  					if(data.codenum == 200){
      				    soon.html("取消收藏");
  					}else{
  						alert("操作失败!");
  					}
  				}); 
  			}else{
  				disFollowAnswer(answerId,function(data){
  					if(data.codenum == 200){
  						 soon.html("收藏");
  					}else{
  						alert("操作失败!");
  					}
  				}); 
  			} 
  		});
  		$("#fwzs_question_ul").on("click",".user_question_showall",function(){
  			var showId = $(this).attr("showId");
  			var flag = $(this).attr("flag");
  			if(flag == "show"){//显示全部
  				$("#show_"+showId).hide();
  				$("#hide_"+showId).show();
  			}else if(flag == "hide"){//收起
  				$("#show_"+showId).show();
  				$("#hide_"+showId).hide();
  			}
  		});
  });
  </script>
  <body>
  	<%@include file="/pages/common/common_top.jsp" %>
  	<div class="fwzs_content">
  		<div class="fwzs_content_left">
  			<%@include file="user_center.jsp" %>	
  			<div class="user_question">
  		 		<ul class="user_question_ul" id="fwzs_question_ul">
  		 		 	<li class="title">
  		 		 		<span class="memtion">
			  				<c:if test="${user.userId==UserSession.userId }">您</c:if>
			  				<c:if test="${user.userId!=UserSession.userId }">${user.userName }</c:if>的精彩回答
			  			</span>
			  			<div class="paixu">
			  				排序方式:
			  				<a href="<%=basePath %>member/${UserSession.userId}/answers.shtml"<c:if test="${orderBy=='new'}"> class="active"</c:if>>
				  			  回答时间
				  			</a>
			  				<a href="<%=basePath %>member/${UserSession.userId}/answers.shtml?orderBy=agreeCount"<c:if test="${orderBy=='agreeCount'}"> class="active"</c:if> >
				  			  赞成数量
				  			</a>
			  				
			  			</div>
			  			
  		 		 	</li>
	  		 		<c:forEach items="${listAnswer}" var="answer">
	  		 			<li>
	  		 				<div class="user_question_title am-text-truncate"> 
	  		 					<a  href="<%=basePath %>question/detail${answer.questionId}.shtml">${answer.questionTitle}</a>
	  		 				</div>
	  		 				<div class="user_question_info">
	  		 					<div class="user_question_left"> 
	  		 						 <span id="${answer.answerId}" class="fwzs_zan <c:if test="${answer.praiseFlag == 1}">fwzs_zan_active</c:if>"><font id ="zanCount${answer.answerId}">${answer.agreeCount}</font></span>
	  		 					</div>
	  		 					<div class="user_question_right">
	  		 						<span class="user_question_time">${answer.addTime}</span>
	  		 						<div class="user_question_name">
	  		 							${answer.userName}：${answer.oneWord}  
	  		 						</div>
	  		 						<div class="user_question_content" id="show_${answer.answerId}">
	  		 							<c:if test="${answer.answerContent.length() > 110 }">${answer.answerContent.substring(0,110) }...</c:if>
			  							<c:if test="${answer.answerContent.length() <= 110 }">${answer.answerContent }</c:if>
			  							<c:if test="${answer.answerContent.length() > 110 }"><span class="user_question_showall" flag="show" showId="${answer.answerId}">显示全部</span></c:if>
	  		 						</div>
	  		 						<div class="user_question_content" style="display: none" id="hide_${answer.answerId}">
	  		 							${answer.answerContent}<span class="user_question_showall" flag="hide" showId="${answer.answerId}">收起</span>
	  		 						</div>
	  		 						<div class="user_info" style="cursor: pointer;">
	  		 							评论${answer.commentCount}  
		  		 						<span class="user_dot">●</span>
		  		 							<c:if test="${answer.followFlag == 1}">
						  					<span id="${answer.answerId}" class="fwzs_sc">取消收藏</span>
						  				</c:if>
						  				<c:if test="${answer.followFlag == 0}">
						  					<span id="${answer.answerId}" class="fwzs_sc">收藏</span>
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
