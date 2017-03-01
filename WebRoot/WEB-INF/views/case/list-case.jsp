<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/pages/common/mytags.jsp" %>
<%@include file="/pages/common/commonjs_css.jsp" %>
<link rel="stylesheet" type="text/css" href="<%=basePath %>static/css/topic.css">
<script src="<%=basePath %>static/js/topic.js"></script>
<html>
  <head>
    <title>案例列表-法务助手</title>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>static/css/case.css">
  </head>
  <script type="text/javascript">
  
   $(function(){
    	$('#fwzs_loading').on('inview.scrollspy.amui', function() {
			loadMore();
		});
		$('#fwzs_loading').scrollspy({
		   animation: 'slide-left',
		   delay: 0
		});
		$('#fwzs_loading').click(function(){
			loadMore();
		});
  	 
  });
  var pageId = 1;
  var loading = false;
  function loadMore(){
	  $('#fwzs_loading').button('loading');
		pageId++;
		if(!loading){
			loading = true;
			$.ajax({
  	        type: "post",
  	        url: basePath+"case/listajax${topicId}.shtml",
  	        data: "pageId="+pageId,
  	        dataType: "html",
  	        success: function(data){
  	        	$("#case_content").append(data);
  	        	$('#fwzs_loading').button('reset');
  	        	loading = false;
  	        }
  	    });
		}
  }
  </script>
  <body>
  	<%@include file="/pages/common/common_top.jsp" %>
  	<div class="fwzs_content">
  		<div class="fwzs_content_left" >
  			<div style="font-size: 15px;margin-top: 20px;"><i style="margin-right: 5px;" class="am-icon-tags"></i>${topic.topicTitle }</div>
  			<div class="fwzs_tag_li" style="padding-top: 0;">
	  			<button href="<%=basePath %>case/list${parentId}.shtml" type="button" class="am-btn am-btn-default <c:if test="${topicId==parentId }">fwzs_tag_active</c:if>">全部</button>
	  			<c:forEach items="${topicList }" var="topic" varStatus="sta">
	  				<button href="<%=basePath %>case/list${topic.topicId }.shtml" type="button" class="am-btn am-btn-default <c:if test="${topicId==topic.topicId }">fwzs_tag_active</c:if> <c:if test="${sta.count==4 || sta.count==9 || sta.count==12 }">fwzs_last_btn</c:if>">${topic.topicTitle }</button>
				</c:forEach>
	  		</div>
	  		<div id="case_content">
  			<c:forEach items="${listCases }" var="cases">
	  		 <div class="caselist_img"  onclick="javascript:window.open('<%=basePath %>case/detail${cases.caseId}.shtml')"  style="background-image: url('<%=basePath %>static/images/caselist.png')">
	  		 	 	<div class="caselist_background">
	  		 	 		<div class="caselist_content"> 
	  		 	 			<div class="caselist_title">${cases.caseTitle}</div>
	  		 	 			<div class="caselist_info">
	  		 	 				${cases.userName}
	  		 	 				<span class="caselist_dot">●</span>
	  		 	 				${cases.publishTime}
	  		 	 				<span class="caselist_dot">●</span>
	  		 	 				评论：${cases.commentCount}
	  		 	 				<span class="caselist_dot">●</span>
	  		 	 				收藏：${cases.followCount}
	  		 	 			</div>	
	  		 	 		</div>
	  		 	 	</div>
	  		 </div>
	  		</c:forEach> 
	  		</div>
	  		<div id="fwzs_loading" style="margin-top: 10px;text-align: center;padding: 5px;cursor: pointer;" class="am-btn-danger" data-am-loading="{spinner: 'circle-o-notch', loadingText: '努力加载中...', resetText: '更多'}"></div>
  		</div><div class="fwzs_content_right"><%@include file="/pages/common/common_right.jsp" %></div>
  	</div>
  	<%@include file="/pages/common/common_bottom.jsp" %>
  </body>
</html>
