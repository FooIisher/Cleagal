<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/pages/common/mytags.jsp" %>
<%@include file="/pages/common/commonjs_css.jsp" %>
<html>
  <head>
    <title>通知-法务助手</title>
  	<script type="text/javascript">
  		$(function(){
  		
  				$.ajax({
					async : false,
					cache : false,
					type : 'POST',
					url : basePath+"member/loadNotifications.shtml",//  
					data : "",
					error : function(msg) {//  
						if(msg.status ==504){
							alert("请登录");
						}
					},
					success : function(data) {
						if(data.codenum == 200){
							var listNotification = data.data.listNotification;
							var datahtml = "";
							var timeText = "";
							for(var i = 0; i < listNotification.length;i++){
								var notification = listNotification[i];
								var actionType = notification.actionType;
								var modelType = notification.modelType;
								var senderUid = notification.senderUid;
								var userName = notification.userName;
								var notifyContent = notification.notifyContent;
								var sourceId = notification.sourceId;
								var addTime = notification.addTime.substr(0,10);
								var modelText = "问题";
								if(modelType == 1){
									modelText = "问题";
								}else if(modelType == 2){
									modelText = "答案";
								}else if(modelType == 3){
									modelText = "案例";
								}else if(modelType == 4){
									modelText = "个人";
								}
								if(timeText != addTime){
									timeText = addTime;
									if(i != 0){
										datahtml+="</aside></section>";
									}
									datahtml+="<section>"+
				       		   		 "<img class='point-time' src='"+basePath +"static/images/xx_jintou.png'/>"+
				       				"<time datetime='2013-01-01'>"+
				         			"<span>"+timeText+"</span>"+
				        			"</time>"+
				        			"<aside>";
								}
								if(actionType == 0){ //审核问题
									datahtml+="<p class='brief'>系统提示，您有1个问题需要审核</p>";
								}else if(actionType == 1){ //关注
									datahtml+="<p class='brief'><a href='"+basePath+"member/"+senderUid+".shtml'>"+userName+"</a> 关注了你</p>";
								}else if(actionType == 2){ //评论
									datahtml+="<p class='brief'><a href='"+basePath+"member/"+senderUid+".shtml'>"+userName+"</a> 评论了你的"+modelText+" <a>"+notifyContent+"</a></p>"; 
								}else if(actionType == 3){ //回答
									datahtml+="<p class='brief'><a href='"+basePath+"member/"+senderUid+".shtml'>"+userName+"</a> 回答了问题  <a href='"+basePath+"question/detail"+sourceId+".shtml'>"+notifyContent+"</a></p>";
								}else if(actionType == 4){ //赞
									datahtml+="<p class='brief'><a href='"+basePath+"member/"+senderUid+".shtml'>"+userName+"</a> 赞了问题  <a href='"+basePath+"answer/detail"+sourceId+".shtml'>"+notifyContent+"</a> 中你的答案</p>";
								}else if(actionType == 5){ //邀请
									datahtml+="<p class='brief'><a href='"+basePath+"member/"+senderUid+".shtml'>"+userName+"</a> 邀请你回答问题  <a href='"+basePath+"question/detail"+sourceId+".shtml'>"+notifyContent+"</a></p>";
								}
				       			if(i+1 == listNotification.length ){
				       				datahtml+="</aside></section>";  
				       			}
							}
							$("#contentHtml").html(datahtml);
						}
					}
				});
  		
  		
  		});
  	</script>
  <link rel="stylesheet" type="text/css" href="<%=basePath %>static/css/notification.css">
  </head>
  <body>
  	<%@include file="/pages/common/common_top.jsp" %>
  	<div class="fwzs_content">
  		<div class="fwzs_content_left">
  					
  		 		<div class="content">
				    <article id="contentHtml">
				    
				     
				      
				    </article>
				  </div>
  		 		
  		</div><div class="fwzs_content_right"><%@include file="/pages/common/common_right_user.jsp" %></div>
  	</div>
  	<%@include file="/pages/common/common_bottom.jsp" %>
  </body>
</html>



