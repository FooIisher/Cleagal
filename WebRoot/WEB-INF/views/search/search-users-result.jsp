<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/pages/common/mytags.jsp" %>
<%@include file="/pages/common/commonjs_css.jsp" %>
<html>
  <head>
    <title>搜索结果-法务助手</title>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>static/css/search.css">
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
  			if(classaa == 'search_user_ungz'){
  				followUser(userId,function(result,userId){
  					if(result == 'true'){
      				   soon.attr("class","search_user_gz");
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
  						 soon.attr("class","search_user_ungz");
  						 soon.html("关注");
  					//	 var zanCount = parseInt($("#zanCount"+answerId).html())-1;
      				//   $("#zanCount"+answerId).html(zanCount);
  					}else{
  						alert("操作失败!");
  					}
  				}); 
  			} 
  			
  		
  		});
  		
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
  	        data: "type=people&q=${q}&pageId="+pageId,
  	        dataType: "html",
  	        success: function(data){
  	        	$("#search_user_ul").append(data);
  	        	$('#fwzs_loading').button('reset');
  	        	loading = false;
  	        }
  	    });
		}
  }
  </script>
    <script src="<%=basePath %>static/js/users.js"></script>
  <body>
  	<%@include file="/pages/common/common_top.jsp" %>
  	<div class="fwzs_content">
  		<div class="fwzs_content_left" >
  		 	<div class="search_title">
  		 	
  		 		<a href="<%=basePath %>search.shtml?type=question&q=${q}"> 问题 </a>
  		 		
  		 		<a  href="<%=basePath %>search.shtml?type=people&q=${q}"  class="search_title_a_checked" style="margin-left: 20px;"> 用户  </a>
  		 		
  		 		<a href="<%=basePath %>search.shtml?type=case&q=${q}" style="margin-left: 20px;"> 案例 </a>
  		 	
  		 	</div>
  		 	<div class="search_user">
  		 		<ul class="search_user_ul" id="search_user_ul">
  		 		 
  		 		 <c:forEach items="${listUsers}" var="users">
  		 			<li>
  		 				<div class="search_user_info">
  		 					<div class="search_user_left" style="cursor: pointer;" onclick="javascript:window.open('<%=basePath %>member/${users.userId}.shtml')" >
  		 						<img src="<%=basePath %>${users.avatarFile}" class="search_user_head">
  		 					</div>
  		 					<div class="search_user_right">
  		 						<div>
  		 							<font class="search_user_name" style="cursor: pointer;" onclick="javascript:window.open('<%=basePath %>member/${users.userId}.shtml')">
  		 								 <c:set var="v_param" value="<font color=red>${q}</font>" />    
		  		 						${fn:replace(users.userName, 
		                               		 q, v_param)}&nbsp;&nbsp;&nbsp;
										<c:if test="${users.sex == 0}">
											女
										</c:if>
										<c:if test="${users.sex == 1}">
											男
										</c:if>
  		 							</font>
  		 							
  		 							<a href="#" class="search_user_data">关注者 ${users.fansCount}</a>
  		 							<a href="#" class="search_user_data">案例 ${users.caseCount}</a>
  		 							<a href="#" class="search_user_data">回答 ${users.answerCount}</a>
  		 						</div>
  		 						<div class="search_user_content">
  		 							 
  		 							<c:if test="${users.oneWord==null}">
  		 							 	此人很难,什么都没留下
  		 							</c:if>
  		 							<c:if test="${users.oneWord!=null}">
  		 							 	${users.oneWord}
  		 							</c:if>
  		 							<c:if test="${users.followFlag==1}">
  		 								<span class="search_user_gz" title="${users.userId}" name="gzSpan">取消关注</span>
  		 							</c:if>	
  		 							<c:if test="${users.followFlag==0}">
  		 								<span class="search_user_ungz" title="${users.userId}" name="gzSpan">关注</span>
  		 							</c:if>	
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
