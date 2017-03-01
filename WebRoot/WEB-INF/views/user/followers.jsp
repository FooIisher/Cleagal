<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/pages/common/mytags.jsp" %>
<%@include file="/pages/common/commonjs_css.jsp" %>
<html>
  <head>
    <title>搜索结果-法务助手</title>
      <link rel="stylesheet" type="text/css" href="<%=basePath %>static/css/user.css">
  </head>
  <script type="text/javascript">
  	 $(function(){
  		$("span[name='gzSpan']").click(function(){
  			if(isLogin == false){
  				loginDialog();
  				return;
  			}
  			var soon = $(this);
  			var userId = $(this).attr("title");
  			var classaa= soon.attr("class");
  			if(classaa == 'user_follow_ungz'){
  				followUser(userId,function(result,userId){
  					if(result == 'true'){
      				   soon.attr("class","user_follow_gz");
      				   soon.html("取消关注");
      				//   var zanCount = parseInt($("#zanCount"+answerId).html())+1;
      				//   $("#zanCount"+answerId).html(zanCount);
  					}else{
  						alert("操作失败!");
  					}
  				}); 
  			}else{
  				disFollowUser(userId,function(result,userId){
  					if(result == 'true'){
  						 soon.attr("class","user_follow_ungz");
  						 soon.html("关注");
  					//	 var zanCount = parseInt($("#zanCount"+answerId).html())-1;
      				//   $("#zanCount"+answerId).html(zanCount);
  					}else{
  						alert("操作失败!");
  					}
  				}); 
  			} 
  			
  		
  		});
  	 
  });
  
  </script>
    <script src="<%=basePath %>static/js/users.js"></script>
  <body>
  	<%@include file="/pages/common/common_top.jsp" %>
  	<div class="fwzs_content">
  		<div class="fwzs_content_left" >
  		 	 <%@include file="user_center.jsp" %>
  		 	<div class="user_follow">
  		 		<ul class="user_follow_ul">
  		 		 <li class="title">
  		 		 		<span class="memtion">
			  				关注您的
			  			</span>
  		 		 	</li>
  		 		 <c:forEach items="${listUsers}" var="users">
  		 			<li>
  		 				<div class="user_follow_info">
  		 					<div class="user_follow_left">
  		 						<img src="<%=basePath %>${users.avatarFile}" class="user_follow_head">
  		 					</div>
  		 					<div class="user_follow_right">
  		 						<div>
  		 							<font class="user_follow_name">
  		 								${users.userName}&nbsp;&nbsp;&nbsp;
  		 							</font>
  		 						
  		 						</div>
  		 						<div class="user_follow_content">
  		 							 ${users.fansCount}关注者
  		 							<span class="user_dot">●</span>
  		 							${users.questionCount}提问
  		 							<span class="user_dot">●</span>
  		 							${users.answerCount}回答
  		 							<span class="user_dot">●</span>
  		 							${users.agreeCount}赞同
  		 							
  		 							<c:if test="${users.followFlag==1}">
  		 								<span class="user_follow_gz" title="${users.userId}" name="gzSpan">取消关注</span>
  		 							</c:if>	
  		 							<c:if test="${users.followFlag==0}">
  		 								<span class="user_follow_ungz" title="${users.userId}" name="gzSpan">关注</span>
  		 							</c:if>	
  		 						</div>
  		 					</div>
  		 				</div>
  		 			</li>
  		 		 </c:forEach>
  		 		</ul>
  		 	</div>
  		  </div>	
  		</div>
  		<div class="fwzs_content_right"></div>
  	</div>
  	<%@include file="/pages/common/common_bottom.jsp" %>
  </body>
</html>
