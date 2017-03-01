<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/pages/common/mytags.jsp" %>
<%@include file="/pages/common/commonjs_css.jsp" %>
<html>
  <head>
    <title>推荐-法务助手</title>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>static/css/recommend.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>static/css/topic.css">
    <script src="<%=basePath %>static/js/topic.js"></script>
  </head>
  <script type="text/javascript">
  $(function(){
  		$(".index_chat").click(function(){
  			var thisuserId = $(this).attr("id");
  			var thisnickName = $(this).attr("title");
  			var avatarFile = 	$(this).attr("avatar");
  			
  			
  			$(document).FnWebIM({
				username : '${UserSession.userId}', // 用户名称
				pass : "${UserSession.truePassword}", // 用户密码
				nickName : '${UserSession.userName}', // 昵称
				imgUrl:basePath+'${UserSession.avatarFile}',
				autoLogin : true, //boolean型，默认是否自动登录，true：自动登录，false：手动登录，默认为true
				msgRefreshTime : 1000, //number型，消息刷新时间，单位为ms
				friendRefreshTime : 10000, //number型，好友刷新时间，单位为ms
				showSecretary : false, //boolean型，默认是否显示小秘书，true：显示，false：不显示，默认为true
				sendPicture : true, //boolean型，是否允许发送图片，true：允许，false：不允许，默认为true
				msgMaxSize : 300, //number型，单条消息最大允许字符
				msgSound : true, //boolean型，是否开启声音提醒，true：开启，false:关闭，默认为true
				defaultWindow : thisuserId , //string型，登录后打开新聊天窗口，此处接收的参数为联系人的uid，否则会出错
				nickNameWindow : thisnickName,
				imgUrlWindow:basePath+avatarFile,
				contextpath:'<%=serverPath%>'+"/legal_chat"
				
			});	
  		});
  		
  		$("#fwzs_recommend_ul").on("click",".fwzs_recommend_left",function(){
  	    	location.href="<%=basePath %>member/"+$(this).attr('userId')+".shtml";
  	    });
  	});
  </script>
   
  <body>
  	<%@include file="/pages/common/common_top.jsp" %>
  	<div class="fwzs_content">
  		<div class="fwzs_content_left">
	  		<div class="fwzs_tag_li">
	  			<button id="New" href="<%=basePath %>recommend/indexNew.shtml" type="button" class="am-btn am-btn-default <c:if test="${topicId=='New' }">fwzs_tag_active</c:if>">最新</button>
	  			<button id="Hot" href="<%=basePath %>recommend/indexHot.shtml" type="button" class="am-btn am-btn-default <c:if test="${topicId=='Hot' }">fwzs_tag_active</c:if>">最热</button>
	  			<c:forEach items="${topicList }" var="topic" varStatus="sta">
	  				<div id="${topic.topicId }" class="am-dropdown">
					  <button class="am-btn am-btn-default am-dropdown-toggle <c:if test="${topicId == topic.topicId }">fwzs_tag_active</c:if> <c:if test="${sta.count==3 || sta.count==8 || sta.count==13}">fwzs_last_btn</c:if>">${topic.topicTitle }</button>
					  <ul class="am-dropdown-content" id="${topic.topicId }_ul">
					    <c:forEach items="${topic.listTopics }" var="topic2">
					    	<li><a href="index${topic2.topicId }.shtml">${topic2.topicTitle }</a></li>
					    </c:forEach>
					  </ul>
					</div>
				</c:forEach>
				<c:if test="${topicId!='New' && topicId!='Hot'}">
				<span style="font-size: 14px"><i style="margin-right: 5px;" class="am-icon-tags"></i>${topic1.topicTitle }<i style="margin:0 5px;" class="am-icon-angle-double-right"></i>${topic2.topicTitle }</span>
				</c:if>
	  		</div>
	  		
	  		<ul class="fwzs_recommend_ul" id="fwzs_recommend_ul">
	  			<c:forEach items="${recommendUsers}" var="user">
	  			<li>
	  			<div class="fwzs_recommend_left" userId=${user.userId }>
	  				<img src="<%=basePath %>${ynkj:parseHeadImg(user.avatarFile)}" class="am-img-thumbnail fwzs_recommend_head">
	  				<c:if test="${user.certifyStatus==2 }">
	  				<img class="fwzs_icon_vv" src="<%=basePath %>static/images/icon_vv.png"></img>
	  				</c:if>
	  			</div>
	  			<div class="fwzs_recommend_right">
	  				<div class="fwzs_recommend_user">${user.userName}</div>
	  				<div style="margin-top: 5px;">擅长：${user.adeptTagName }等案件</div>
	  				<div style="margin-top: 5px;">${user.lawsuitCount }场官司胜诉${user.winCount }场</div>
	  				<div class="fwzs_recommend_intro">${user.individualResume }</div>
	  				<div class="fwzs_recommend_chat">
	  				<button type="button" class="am-btn am-btn-success am-radius index_chat" id="${user.userId}" title="${user.userName}" avatar="${user.avatarFile}">和Ta聊天</button></div>
	  			</div></li>
	  			</c:forEach>
	  		</ul>
  		</div><div class="fwzs_content_right"><%@include file="/pages/common/common_right.jsp" %></div>	
  	</div>
  	<%@include file="/pages/common/common_bottom.jsp" %>
  </body>
</html>
