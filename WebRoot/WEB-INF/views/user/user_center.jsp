<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<c:set var="url" value="<%=request.getServletPath()%>"></c:set>
<script src="<%=basePath %>static/js/users.js"></script>
<script type="text/javascript">
$(function(){
  		$("span[name='user_center_gz']").click(function(){
  			if(isLogin == false){
  				loginDialog();
  				return;
  			}
  			var soon = $(this);
  			var userId = $(this).attr("title");
  			var classaa= soon.attr("class");
  			if(classaa.trim() == 'user_center_gz'){
  				followUser(userId,function(result,userId){
  					if(result == 'true'){
      				   soon.attr("class","user_center_gz active");
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
  						 soon.attr("class","user_center_gz ");
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
<div class="user_center">
	<div class="user_center_left">
		<img class="am-img-thumbnail" src="<%=basePath %>${ynkj:parseHeadImg(user.avatarFile)}" width="145px" height="145px">
	</div>
	<div class="user_center_right">
  		 						<font class="user_center_name">
  		 								${user.userName}&nbsp;&nbsp;&nbsp;
  		 						</font>
  		 						<c:if test="${user.certifyStatus!=2 && user.userId == UserSession.userId}">
  		 							<span onclick="javascript:location.href='<%=basePath %>/member/certify.shtml'" class="user_center_rz" title="${users.userId}" name="gzSpan">去认证</span>	
  		 						</c:if>	
  		 						<div class="user_center_content">
	  		 							<c:if test="${user.oneWord==null}">
	  		 							 	此人很懒,什么都没留下
	  		 							</c:if>
	  		 							<c:if test="${user.oneWord!=null}">
	  		 							 	${user.oneWord}
	  		 							</c:if>
	  		 					
	  		 					 </div>
	  		 					 <div class="user_center_ziliao">
	  		 					 	
	  		 					 	 <div>
	  		 					 	 	<span>${user.cityName }</span>  <span>${user.jobName }</span> 
	  		 					 	 	
	  		 					 	 	<span>
	  		 					 	 	<c:if test="${user.sex==1}">男</c:if>
	  		 					 	 	<c:if test="${user.sex==0}">女</c:if>
	  		 					 	 	</span>
	  		 					 	 </div>
	  		 					 	 <div>
	  		 					 	 	<span>
	  		 					 	 		${user.companyName}
	  		 					 	 	</span>  
	  		 					 	 </div>
	  		 					 	 
	  		 					 	 <div>
	  		 					 	 	<span>${user.school}</span>  <span>${user.profess}</span>
	  		 					 	 </div>
	  		 					 	 <c:if test="${user.userId == UserSession.userId}">
	  		 					 		 <span class="user_center_edit"  onclick="javascript:location.href='<%=basePath %>member/edit.shtml'">编辑个人资料</span>
	  		 						</c:if>
	  		 					 </div>
	  		 					 
	</div>
	<div class="user_center_data">
  		 			被邀请${user.inviteCount}
  		 			<span class="user_dot">●</span>
  		 			被赞${user.agreeCount}
  		 <c:if test="${user.followFlag==1}">			
  		 	<span class="user_center_gz active" title="${user.userId}" name="user_center_gz">取消关注</span>
  		 </c:if>
  		  <c:if test="${user.followFlag==0}">			
  		 	<span class="user_center_gz" title="${user.userId}" name="user_center_gz">关注</span>
  		 </c:if>	
  	</div>
</div>
<div class="user_center_list">
<ul class="user_center_title">	
	<li class="item <c:if test="${dynamicList != null}">active</c:if>" 
	onclick="javascript:location.href='<%=basePath%>member/${userId}.shtml'">
		动态
	</li>
	<li class="item <c:if test="${fn:contains(url,'user-question')}">active</c:if>" 
	onclick="javascript:location.href='<%=basePath%>member/${userId}/asks.shtml'">
		提问
	</li>
	<li class="item <c:if test="${fn:contains(url,'user-answer')}">active</c:if>" 
	onclick="javascript:location.href='<%=basePath%>member/${userId}/answers.shtml'">
		回答
	</li>
	<li class="item <c:if test="${fn:contains(url,'follow-answer')}">active</c:if>" 
	onclick="javascript:location.href='<%=basePath%>member/${userId}/collectAnswer.shtml'">
		收藏
	</li>
<c:if test="${user.jobId == 'lawyer' && user.certifyStatus == 2}">	
	<li class="item <c:if test="${fn:contains(url,'user-case')}">active</c:if>" 
	onclick="javascript:location.href='<%=basePath%>member/${userId}/cases.shtml'">
	 	案例
	</li>
</c:if>	
</ul>