<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/pages/common/mytags.jsp" %>
<%@include file="/pages/common/commonjs_css.jsp" %>
<html>
  <head>
    <title>${cases.caseTitle}-法务助手</title>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>static/css/case.css">
  </head>
  <script type="text/javascript">
  function userFollowCase(caseId){
  	if(isLogin == false){
  				loginDialog();
  				return;
  			}
  	followCase(caseId,function(result,caseId){
  			if(result == 'true'){
  				$("#gzHtml").html("<a  href=\"javascript:unfollowCase('"+caseId+"');\">取消关注</a>");
  			}else{
  				alert("操作失败!");
  			}
  	});
  }
  function unfollowCase(caseId){
  if(isLogin == false){
  				loginDialog();
  				return;
  			}
  	disFollowCase(caseId,function(result,caseId){
  			if(result == 'true'){
  				$("#gzHtml").html("<a  href=\"javascript:userFollowCase('"+caseId+"');\">添加关注</a>"); 
  			}else{
  				alert("操作失败!");
  			}
  	});
  }
  function addComments(caseId){
  		 if(isLogin == false){
  				loginDialog();
  				return;
  			}
		$.ajax({
			async : false,
			cache : false,
			type : 'POST',
			url : basePath+"case/commentCase.shtml",//  
			data : "caseId="+caseId+"&message="+$("#message").val(),
			error : function(msg) {//  
				if(msg.status ==504){
					alert("请登录");
				}
			},
			success : function(data) {
		  		if (data.success) {
		  			alert("评论已提交审核！");
		  			window.location.href = basePath+"case/detail"+caseId+".shtml";
				} else {
					alert("评论失败!");
				}
			}
		 });
  }
  </script>
  <script src="<%=basePath %>static/js/case.js"></script>
  <body>
  	<%@include file="/pages/common/common_top.jsp" %>
  	<div class="fwzs_content">
  		<div class="fwzs_content_left" >
  			 <div class="caseDetail" >
  			 	   <img class="caseDetail_img" src="<%=basePath%>${cases.imgUrl}"  onerror="this.src='<%=basePath%>static/images/caselist.png'"/>
  			 	   <div class="caseDetail_head">
  			 	   		<div class="caseDetail_head_left">
  			 	   			<img src="<%=basePath %>${cases.avatatFile}" class="caseDetail_head_img">
  			 	   		</div>
  			 	   		<div class="caseDetail_head_right">
  			 	   			<div class="caseDetail_head_title">${cases.caseTitle}</div>
  			 	   			<div class="caseDetail_head_info">
	  		 	 				${cases.userName}
	  		 	 				<span class="caseDetail_dot">●</span>
	  		 	 				${cases.publishTime}
	  		 	 			</div>		
  			 	   	   			 				
  			 	   		</div>
  			 	   </div>	
  			 	   <div class="caseDetail_content">
  			 	   		${cases.caseContent}
  			 	   </div>	
  			 	   <div class="caseDetail_info">
  			 	   		<a> ${cases.commentCount}条评论 </a>
  			 	   		
  			 	   		
	  		 	 		<span class="caseDetail_dot">●</span>
	  		 	 		<span id="gzHtml">
	  		 	 		<c:if test="${cases.followFlag==1}">
							<a href="javascript:unfollowCase('${cases.caseId}');">取消关注</a>
						</c:if>
						<c:if test="${cases.followFlag==0}">
							<a href="javascript:userFollowCase('${cases.caseId}');">关注</a>
						</c:if>
	  		 	 		</span>
	  		 	 		<span class="caseDetail_dot">●</span>
	  		 	 		分享<div class="bshare-custom icon-medium" style="display: inline;"><a title="分享到新浪微博" class="bshare-sinaminiblog"></a><a title="更多平台" class="bshare-more bshare-more-icon more-style-addthis"></a></div><script type="text/javascript" charset="utf-8" src="http://static.bshare.cn/b/buttonLite.js#style=-1&amp;uuid=&amp;pophcol=2&amp;lang=zh"></script><script type="text/javascript" charset="utf-8" src="http://static.bshare.cn/b/bshareC0.js"></script>
	  		 	 	 
  			 	   </div>
  			 </div>
  			 
  			 <div class="caseDetail_comment">
  			 	<ul class="caseDetail_comment_ul">
  			 	<c:forEach items="${listCasesComments }" var="caseComment">
  			 		<li>
  			 			<div class="caseDetail_comment_left">
  			 				<img src="<%=basePath %>${caseComment.avatarFile}" class="caseDetail_comment_head">
  			 			 
  			 			</div>
  			 			<div class="caseDetail_comment_right">
  			 					<span class="caseDetail_comment_time">${caseComment.addTime}</span>
  			 					<div class="caseDetail_comment_user">${caseComment.userName}
  			 					</div>
  			 					<div class="caseDetail_comment_content">${caseComment.message}</div>
  			 					
  			 			</div>
  			 		</li>
  			 	</c:forEach>	 
  			 	 
  			 	</ul>
  			 </div>
  			 <div class="caseDetail_addComment">
  			 			<form class="am-form">
  						<fieldset>
  			 				<div class="am-form-group">
     						 <textarea style="width: 100%;resize:none;" rows="3" id = "message" id="doc"  placeholder="写出您的评论!"></textarea>
   							 </div>
    						<p><button type="button" onclick="addComments('${cases.caseId}');" class="am-btn am-btn-default" >提交</button></p>
 						</fieldset>
						</form>
  			 
  			 </div>
  			 
  		</div>
  		<div class="fwzs_content_right"></div>
  	</div>
  	<%@include file="/pages/common/common_bottom.jsp" %>
  </body>
</html>
