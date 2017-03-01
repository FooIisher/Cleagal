<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/pages/common/mytags.jsp" %>
<%@include file="/pages/common/commonjs_css.jsp" %>
<html>
  <head>
    <title>搜索结果-法务助手</title>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>static/css/search.css">
  </head>
  <script type="text/javascript">
  function userFollowQuestion(questionId){
  if(isLogin == false){
  				loginDialog();
  				return;
  			}
  	followQuestion(questionId,function(result,questionId){
  	if(isLogin == false){
  				loginDialog();
  				return;
  			}
  			if(result == 'true'){
  				$("#gzHtml"+questionId).html("<a  href=\"javascript:unfollowQuestion('"+questionId+"');\">取消关注</a>");
  			}else{
  				alert("操作失败!");
  			}
  	});
  }
  function unfollowQuestion(questionId){
  if(isLogin == false){
  				loginDialog();
  				return;
  			}
  	disFollowQuestion(questionId,function(result,questionId){
  			if(result == 'true'){
  				$("#gzHtml"+questionId).html("<a  href=\"javascript:userFollowQuestion('"+questionId+"');\">关注</a>");
  			}else{
  				alert("操作失败!");
  			}
  			
  	});
  }
  $(function(){
  		$("div[name='zanDiv']").click(function(){
  		if(isLogin == false){
  				loginDialog();
  				return;
  			}
  			var soon = $(this);
  			var answerId = $(this).attr("id");
  			var classaa= soon.attr("class");
  			if(classaa == 'search_question_nozan'){
  				praiseAnswer(answerId,function(result,answerId){
  					if(result == 'true'){
      				   soon.attr("class","search_question_zan");
      				   var zanCount = parseInt($("#zanCount"+answerId).html())+1;
      				   $("#zanCount"+answerId).html(zanCount);
  					}else{
  						alert("操作失败!");
  					}
  				}); 
  			}else{
  				disPraiseAnswer(answerId,function(result,answerId){
  					if(result == 'true'){
  						 soon.attr("class","search_question_nozan");
  						 var zanCount = parseInt($("#zanCount"+answerId).html())-1;
      				  	 $("#zanCount"+answerId).html(zanCount);
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
  	        data: "type=question&q=${q}&pageId="+pageId,
  	        dataType: "html",
  	        success: function(data){
  	        	$("#search_question_ul").append(data);
  	        	$('#fwzs_loading').button('reset');
  	        	loading = false;
  	        }
  	    });
		}
  }
  </script>
  <script src="<%=basePath %>static/js/question.js"></script>
   <script src="<%=basePath %>static/js/answer.js"></script>
  <body>
  	<%@include file="/pages/common/common_top.jsp" %>
  	<div class="fwzs_content">
  		<div class="fwzs_content_left" >
  		 	<div class="search_title">
  		 	
  		 		<a href="<%=basePath %>search.shtml?type=question&q=${q}" class="search_title_a_checked"> 问题 </a>
  		 		
  		 		<a  href="<%=basePath %>search.shtml?type=people&q=${q}"  style="margin-left: 20px;"> 用户  </a>
  		 		
  		 		<a href="<%=basePath %>search.shtml?type=case&q=${q}" style="margin-left: 20px;"> 案例 </a>
  		 	
  		 	</div>
  		 	<div class="search_question">
  		 		<ul class="search_question_ul" id="search_question_ul">
  		 		<c:forEach items="${listQuestion}" var="question">
  		 			<li>
  		 				<div class="search_question_title"> 
  		 					<a  href="<%=basePath %>question/detail${question.questionId }.shtml">
  		 					<c:set var="v_param" value="<font color=red>${q}</font>" />    
  		 					${fn:replace(question.questionTitle, 
                                q, v_param)}
  		 					</a>
  		 				</div>
  		 				<div class="search_question_info">
  		 				<c:if test="${question.answerId != null}">
  		 					<div class="search_question_left"> 
  		 						<div 
  		 							<c:if test="${question.praiseFlag == 1}">
  		 								class="search_question_zan" 
  		 							</c:if>	
  		 						 	<c:if test="${question.praiseFlag == 0}">
  		 						 		class="search_question_nozan" 
  		 						 	</c:if>	
  		 						name="zanDiv" id = "${question.answerId}">
  		 							<div id = "zanCount${question.answerId}">
  		 								${question.agreeCount}
  		 							</div>
  		 							<div id = "zanTitle${question.answerId}">
  		 								点赞
  		 							</div>
  		 						</div>
  		 					</div>
  		 				</c:if>	
  		 					<div 	<c:if test="${question.answerId != null}">
  		 								class="search_question_right"
  		 							</c:if>
  		 						>
  		 						<span class="search_question_time">${question.addTime}</span>
  		 						<div class="search_question_name">
  		 							${question.userName} &nbsp;   ${question.oneWord}
  		 						</div>
  		 					<c:if test="${question.answerId != null}">	
  		 						<div class="search_question_content">
  		 							${question.answerContent}
  		 							<span class="search_question_showall">显示全部</span>
  		 						</div>
  		 					</c:if>	
  		 						<div class="search_info">
  		 						<span id = "gzHtml${question.questionId}">
  		 							<c:if test="${question.followFlag == 1}">
  		 								<a  href="javascript:unfollowQuestion('${question.questionId}');">取消关注</a>
  		 						 	</c:if>	
  		 						 	<c:if test="${question.followFlag == 0}">
  		 						 		<a  href="javascript:userFollowQuestion('${question.questionId}');">添加关注</a>
  		 						 	</c:if>	
  		 						</span>	
  		 						<span class="search_dot">●</span>
  		 							${question.answerCount}个回答
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
