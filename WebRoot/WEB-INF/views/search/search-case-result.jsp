<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/pages/common/mytags.jsp" %>
<%@include file="/pages/common/commonjs_css.jsp" %>
<html>
  <head>
    <title>搜索结果-法务助手</title>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>static/css/search.css">

  </head>
  <script type="text/javascript">
  function userFollowCase(caseId){
  	if(isLogin == false){
  				loginDialog();
  				return;
  			}
  	followCase(caseId,function(result,caseId){
  			if(result == 'true'){
  				$("#gzHtml"+caseId).html("<a  href=\"javascript:unfollowCase('"+caseId+"');\">取消关注</a>");
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
  				$("#gzHtml"+caseId).html("<a  href=\"javascript:userFollowCase('"+caseId+"');\">添加关注</a>"); 
  			}else{
  				alert("操作失败!");
  			}
  			
  	});
  }
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
  	        url: basePath+"search/ajaxSearch.shtml",
  	        data: "type=case&q=${q}&pageId="+pageId,
  	        dataType: "html",
  	        success: function(data){
  	        	$("#search_case_ul").append(data);
  	        	$('#fwzs_loading').button('reset');
  	        	loading = false;
  	        }
  	    });
		}
  }
  </script>
  <script src="<%=basePath %>static/js/case.js"></script>
  <body>
  	<%@include file="/pages/common/common_top.jsp" %>
  	<div class="fwzs_content">
  		<div class="fwzs_content_left" >
  		 	<div class="search_title">
  		 		<a href="<%=basePath %>search.shtml?type=question&q=${q}"> 问题 </a>
  		 		<a href="<%=basePath %>search.shtml?type=people&q=${q}"  style="margin-left: 20px;"> 用户  </a>
  		 		<a href="<%=basePath %>search.shtml?type=case&q=${q}" class="search_title_a_checked" style="margin-left: 20px;"> 案例 </a>
  		 	</div>
  		 	<div class="search_case">
  		 		<ul class="search_case_ul" id = "search_case_ul">
  		 		<c:forEach items="${listCases }" var="cases">
  		 			<li>
  		 				<div class="search_case_info">
  		 					<div class="search_case_left">
  		 							<div   class="search_question_zan"   >
  		 							<div>
  		 								${cases.viewCount}
  		 							</div>
  		 							<div>
  		 								浏览
  		 							</div>
  		 						</div>
  		 					</div>
  		 					<div class="search_case_right">
  		 						<div class="search_case_name" onclick="javascript:window.open('<%=basePath %>case/detail${cases.caseId}.shtml')">
  		 						 	 	 <c:set var="v_param" value="<font color=red>${q}</font>" />    
		  		 						${fn:replace(cases.caseTitle, 
		                               		 q, v_param)}
  		 						</div>
  		 						<div class="search_case_data">
  		 						<span id="gzHtml${cases.caseId}">
  		 							<c:if test="${cases.followFlag == 1}">
  		 						 		<a  href="javascript:unfollowCase('${cases.caseId}');">取消关注</a>
  		 						 	</c:if>	
  		 						 	<c:if test="${cases.followFlag == 0}">
  		 						 		<a  href="javascript:userFollowCase('${cases.caseId}');">添加关注</a>
  		 						 	</c:if>	
  		 						</span> 	
  		 							<span class="search_dot">●</span>
  		 							${cases.commentCount}个评论
  		 						</div>
  		 					</div>
  		 				</div>
  		 			</li>
  		 		</c:forEach>	 
  		 		</ul>
  		 		<div id="fwzs_loading" style="margin-top: 10px;text-align: center;padding: 5px;cursor: pointer;" class="am-btn-danger" data-am-loading="{spinner: 'circle-o-notch', loadingText: '努力加载中...', resetText: '更多'}"></div>
  		 	</div>
  		</div>
  		<div class="fwzs_content_right"></div>
  	</div>
  	<%@include file="/pages/common/common_bottom.jsp" %>
  </body>
</html>
