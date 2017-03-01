<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/pages/common/mytags.jsp" %>
<%@include file="/pages/common/commonjs_css.jsp" %>
<html>
  <head>
    <title>动态-法务助手</title>
   
    <link rel="stylesheet" type="text/css" href="<%=basePath %>static/css/homepage.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>static/css/user.css">
    <script src="<%=basePath %>static/js/answer.js"></script>
    <script src="<%=basePath %>static/js/question.js"></script>
    <style type="text/css">
    	p{margin: 0}
    </style>
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
  			if(isLogin == false){
  				loginDialog();
  				return;
  			}
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
  		
  });
  </script>
  <body>
  	<%@include file="/pages/common/common_top.jsp" %>
  	<div class="fwzs_content">
  		<div class="fwzs_content_left">
  			<%@include file="user_center.jsp" %>
  			<div class="user_dynamic">
  		 		<ul class="user_dynamic_ul">
	  		 		<c:forEach items="${dynamicList}" var="dynamic">
	  		 		<li>
		  		 		<div class="user_dynamic_time">${fn:substring(dynamic.add_time,0,10) }</div>
		  		 		<c:choose>  
					    <c:when test="${dynamic.associate_action == 1 }">关注了
					    </c:when>
					    <c:when test="${dynamic.associate_action == 2 }">评论了
					    </c:when>
					    <c:when test="${dynamic.associate_action == 3 }">回答了
					    </c:when>
					    <c:when test="${dynamic.associate_action == 4 }">点赞了
					    </c:when>
					    <c:when test="${dynamic.associate_action == 5 }">访问了
					    </c:when>
					    <c:when test="${dynamic.associate_action == 6 }">发布了
					    </c:when>
						</c:choose>
						<c:choose>  
					    <c:when test="${dynamic.associate_type == 1 }">问题：
					    	<a href="<%=basePath %>question/detail${dynamic.associate_id}.shtml">${dynamic.question_title}</a>
					    </c:when>
					    <c:when test="${dynamic.associate_type == 2 }">
					    	<c:if test="${dynamic.associate_action == 3 }">问题：
					    		<a href="<%=basePath %>question/detail${dynamic.question_id}.shtml">${dynamic.answer_question}</a><br>
					    		${dynamic.answer_content}
					    	</c:if>
					    	<c:if test="${dynamic.associate_action != 3 }">回答：<span style="color:blue">${dynamic.answer_content}</span></c:if>
					    </c:when>
					    <c:when test="${dynamic.associate_type == 3 }">案例：
					    	<span style="color:blue">${dynamic.case_title}</span>
					    </c:when>
					    <c:when test="${dynamic.associate_type == 4 }">用户：
					    	<span style="color:blue">${dynamic.user_name}</span>
					    </c:when>
						</c:choose>
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
