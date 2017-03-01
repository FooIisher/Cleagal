<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<%@include file="/pages/common/mytags.jsp" %>
<%@include file="/pages/common/commonjs_css.jsp" %>
<html>
  <head>
    <title>首页-法务助手</title>
    <script src="<%=basePath %>static/js/homepage.js"></script>
    <script src="<%=basePath %>static/js/topic.js"></script>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>static/css/topic.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>static/css/homepage.css">
    <script src="<%=basePath %>static/js/answer.js"></script>
  </head>
  <script type="text/javascript">
  $(function(){
  		$("#fwzs_question_ul").on("click",".fwzs_zan",function(){
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
  		$("#fwzs_question_ul").on("click",".fwzs_sc",function(){
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
  		
		
		//取消评论
		$("#fwzs_question_ul").on("click",".am-btn-link",function(){
			var dropdownId = $(this).attr("dropdownId");
			$("#"+dropdownId).dropdown('close');
		});
		//弹出评论
		
		$("#fwzs_question_ul").on("click",".fwzs_comment",function(){
			var answerId = $(this).attr("answerId");
			var dropdownId = $(this).attr("dropdownId");
			var loadflag = $(this).attr("loadflag");
			var _this = $(this);
			if(loadflag==0){//是否需要请求
				$.ajax({
		  	        type: "post",
		  	        url: basePath+"answer/listAnswerComment"+answerId+".shtml",
		  	        data: "",
		  	        dataType: "html",
		  	        success: function(data){
		  	        	$("#comment_"+answerId).html(data);
		  	        	$("#"+dropdownId).dropdown('toggle');
		  	        	$(_this).attr("loadflag","1");
		  	        }
		  	    });
			}else{
				$("#"+dropdownId).dropdown('toggle');
			}
			
		});
		//提交评论
		$("#fwzs_question_ul").on("click",".fwzs_submit_comment",function(){
			if(isLogin == false){
  				loginDialog();
  				return;
  			}
			var answerId = $(this).attr("answerId");
			var message = $("#message_"+answerId).val();
			if(message == ""){
				alert("请输入评论内容");
				return;
			}
			var subBtn = this;
			$(subBtn).button('loading');
			$.ajax({
	  	        type: "post",
	  	        url: basePath+"answer/commentAnswer.shtml",
	  	        data: "answerId="+answerId+"&message="+message,
	  	        dataType: "json",
	  	        success: function(data){
	  	        	if(data.codenum == 200){
	  	        		$("#message_"+answerId).val("");
	  	        		alert("评论成功，等待系统审核");
	  	        	}else{
	  	        		alert("评论失败");
	  	        	}
	  	        	$(subBtn).button('reset');
	  	        }
	  	    });
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
  	        	url: basePath+"homepage/ajaxQueryQuestionIndex.shtml",
  	        	data: "topicId=${topicId}&pageId="+pageId,
  	       		dataType: "html",
  	       		success: function(data){
  	        		$("#fwzs_question_ul").append(data);
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
  		<div class="fwzs_content_left">
	  		<div class="fwzs_tag_li">
	  			<button id="New" href="<%=basePath %>homepage/indexNew.shtml" type="button" class="am-btn am-btn-default <c:if test="${topicId=='New' }">fwzs_tag_active</c:if>">最新</button>
	  			<button id="Hot" href="<%=basePath %>homepage/indexHot.shtml" type="button" class="am-btn am-btn-default <c:if test="${topicId=='Hot' }">fwzs_tag_active</c:if>">最热</button>
	 
	  			
	  			
	  			<c:forEach items="${topicList}" var="topic" varStatus="sta">
	  				<div id="${topic.topicId }" class="am-dropdown">
					  <button class="am-btn am-btn-default am-dropdown-toggle <c:if test="${topicId == topic.topicId }">fwzs_tag_active</c:if> <c:if test="${sta.count==3 || sta.count==8 || sta.count==13 }">fwzs_last_btn</c:if>">${topic.topicTitle }</button>
					  <ul class="am-dropdown-content" id="${topic.topicId }_ul">
					    <c:forEach items="${topic.listTopics }" var="topic2">
					    	<li><a href="<%=basePath %>homepage/index${topic2.topicId }.shtml">${topic2.topicTitle }</a></li>
					    </c:forEach>
					  </ul>
					 
					</div>
				</c:forEach>
				<c:if test="${topicId!='New' && topicId!='Hot'}">
				<span style="font-size: 14px"><i style="margin-right: 5px;" class="am-icon-tags"></i>${topic1.topicTitle }<i style="margin:0 5px;" class="am-icon-angle-double-right"></i>${topic2.topicTitle }</span>
				</c:if>
	  		</div>
	  		<ul class="fwzs_answer_ul" id="fwzs_question_ul">
	  			<c:forEach items="${listQuestion }" var="question">
	  			<li>
	  			<div class="fwzs_answer_left">
	  				<c:if test="${question.answerId != null}">
		  				<img src="<%=basePath %>${ynkj:parseHeadImg(question.aavatarFile)}" class="am-img-thumbnail am-circle fwzs_answer_head">
		  				<c:if test="${question.acertifyStatus==2 }"><img class="fwzs_icon_vv" src="<%=basePath %>static/images/icon_vv.png"></img></c:if>
		  				<br><span id="${question.answerId}" class="fwzs_zan <c:if test="${question.praiseFlag == 1}">fwzs_zan_active</c:if>"><font id ="zanCount${question.answerId}">${question.agreeCount}</font></span>
	  				</c:if>
	  				<c:if test="${question.answerId == null}">
		  				<img src="<%=basePath %>${question.topicPic}" class="am-img-thumbnail am-circle fwzs_answer_head">
	  				</c:if>
	  			</div>
	  			<div class="fwzs_answer_right">
	  				<c:if test="${question.answerId == null}">
	  					<div><span class="fwzs_answer_time">${question.addTime }</span><a target="_blank" href="<%=basePath %>question/detail${question.questionId}.shtml" class="fwzs_answer_question"><img class="fwzs_icon_question" src="<%=basePath %>static/images/icon_question.png"></img>${question.questionTitle }</a></div>
	  				</c:if>
	  				<c:if test="${question.answerId != null}">
	  					<div><span class="fwzs_answer_time">${question.addTime }</span><a target="_blank" href="<%=basePath %>answer/detail${question.answerId}.shtml" class="fwzs_answer_question"><img class="fwzs_icon_question" src="<%=basePath %>static/images/icon_question.png"></img>${question.questionTitle }</a></div>
		  				<div style="font-size: 14px" id="show_${question.answerId}">
		  					<img class="fwzs_icon_answer" src="<%=basePath %>static/images/icon_answer.png"></img>
			  				<c:if test="${question.answerContent.length() > 110 }">${question.answerContent.substring(0,110) }...</c:if>
			  				<c:if test="${question.answerContent.length() <= 110 }">${question.answerContent }</c:if>
			  				<c:if test="${question.answerContent.length() > 110 }"><span class="fwzs_show_all" flag="show" showId="${question.answerId}">显示全部</span></c:if>
		  				</div>
		  				<div style="font-size: 14px;display: none" id="hide_${question.answerId}">
			  				${question.answerContent }<span class="fwzs_show_all" showId="${question.answerId}" flag="hide">收起</span>
		  				</div>
		  				<c:if test="${question.aanonymous == 0}">
		  					<div class="fwzs_answer_user">${question.auserName}回答了该问题</div>
		  				</c:if>
		  				<c:if test="${question.aanonymous != 0}">
		  					<div class="fwzs_answer_user">匿名</div>
		  				</c:if>
		  				<div class="fwzs_action_info">
			  				查看${question.aviewCount}<span class="fwzs_dot">●</span>
			  				<div id="dropdown_${question.answerId}" class="am-dropdown">
							  <span answerId="${question.answerId}" dropdownId="dropdown_${question.answerId}" loadflag="0" class="am-dropdown-toggle fwzs_action fwzs_comment">评论${question.acommentCount }</span>
							  <div class="am-dropdown-content am-form">
							    <ul id="comment_${question.answerId}" class="am-list am-list-static">
							    </ul>
							    <input id="message_${question.answerId}" type="text" placeholder="写出您的评论">
							    <div class="fwzs_commend_action">
							    	<span dropdownId="dropdown_${question.answerId}" class="am-btn am-btn-link">取消</span>
							    	<button answerId="${question.answerId}" type="button" class="am-btn am-btn-secondary fwzs_submit_comment" data-am-loading="{loadingText: '提交中...'}">提交</button>
							    </div>
							  </div>
							</div>
			  				<span class="fwzs_dot">●</span>
			  				<c:if test="${question.afollowFlag == 1}">
			  					<span id="${question.answerId}" class="fwzs_sc fwzs_action">取消收藏</span>
			  				</c:if>
			  				<c:if test="${question.afollowFlag == 0}">
			  					<span id="${question.answerId}" class="fwzs_sc fwzs_action">收藏</span>
			  				</c:if>
		  				</div>
	  				</c:if>
	  			</div></li>
	  			</c:forEach>
	  		</ul>
	  		<div id="fwzs_loading" style="margin-top: 10px;text-align: center;padding: 5px;cursor: pointer;" class="am-btn-danger" data-am-loading="{spinner: 'circle-o-notch', loadingText: '努力加载中...', resetText: '更多'}"></div>
  		</div><div class="fwzs_content_right"><%@include file="/pages/common/common_right.jsp" %></div>
  	</div>
  	<%@include file="/pages/common/common_bottom.jsp" %>
  </body>
</html>
