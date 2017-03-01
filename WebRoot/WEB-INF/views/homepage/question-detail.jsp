<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<%@include file="/pages/common/mytags.jsp" %>
<%@include file="/pages/common/commonjs_css.jsp" %>
<html>
  <head>
    <title>${question.questionTitle }-法务助手</title>
    <script src="<%=basePath %>static/js/homepage.js"></script>
    <script src="<%=basePath %>static/js/topic.js"></script>
    <script src="<%=basePath %>static/js/answer.js"></script>
    <script src="<%=basePath %>static/js/question.js"></script>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>static/css/topic.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>static/css/homepage.css">
    
    <c:if test="${msg != null}">
    	<script type="text/javascript">
    		alert("${msg}");
    	</script>
    </c:if>
    <script type="text/javascript">
    $(function(){
    	var ue = UE.getEditor('myEditor');
    	$(".fwzs_gz").click(function(){
  			var soon = $(this);
  			var questionId = soon.attr("id");
  			var classaa= soon.attr("class");
  			if(!soon.hasClass("fwzs_gz_true")){
  				followQuestion(questionId,function(result,questionId){
  					if(result == 'true'){
      				   soon.addClass("fwzs_gz_true");
      				   soon.html("取消关注");
  					}else{
  						alert("操作失败!");
  					}
  				}); 
  			}else{
  				disFollowQuestion(questionId,function(result,questionId){
  					if(result == 'true'){
  						soon.removeClass("fwzs_gz_true");
  						soon.html("关注");
  					}else{
  						alert("操作失败!");
  					}
  				}); 
  			} 
  		});
    	var inviteFlag = false;
    	$("#inviteSpan").click(function(){
    		if(!inviteFlag){
    			$.ajax({
    	  	        type: "post",
    	  	        url: basePath+"recommend/inviteUsers.shtml",
    	  	        data: "questionId=${question.questionId}",
    	  	        dataType: "html",
    	  	        success: function(data){
    	  	        	$("#userList").html(data);
    	  	        	$("#inviteDropdown").dropdown('toggle');
    	  	        }
    	  	    });
    			inviteFlag = true;
    		}else{
    			$("#inviteDropdown").dropdown('toggle');
    		}
    	});
    	$("#keyword").keyup(function(){
    		if($.trim($("#keyword").val())==""){
    			return;
    		}
    		$.ajax({
	  	        type: "post",
	  	        url: basePath+"recommend/inviteUsers.shtml",
	  	        data: "questionId=${question.questionId}&keyword="+$("#keyword").val(),
	  	        dataType: "html",
	  	        success: function(data){
	  	        	$("#userList").html(data);
	  	        }
	  	    });
    	});
    	$(".fwzs_answer_agree").click(function(){
    		if(isLogin == false){
  				loginDialog();
  				return;
  			}
  			var soon = $(this);
  			var answerId = soon.attr("id");
  			var classaa= soon.attr("class");
  			if(!soon.hasClass("fwzs_zan_true")){
  				praiseAnswer(answerId,function(result,answerId){
  					if(result == 'true'){
      				   soon.addClass("fwzs_zan_true");
      				   var zanCount = parseInt($("#zanCount"+answerId).html())+1;
      				   $("#zanCount"+answerId).html(zanCount);
      					$("#zanTxt"+answerId).html("取消赞");
  					}else{
  						alert("操作失败!");
  					}
  				}); 
  			}else{
  				disPraiseAnswer(answerId,function(result,answerId){
  					if(result == 'true'){
  						soon.removeClass("fwzs_zan_true");
  						 var zanCount = parseInt($("#zanCount"+answerId).html())-1;
      				  	 $("#zanCount"+answerId).html(zanCount);
      				  	$("#zanTxt"+answerId).html("点赞");
  					}else{
  						alert("操作失败!");
  					}
  				}); 
  			} 
  		});
    	$(".fwzs_sc").click(function(){
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
		$("#fwzs_answer_ul").on("click",".am-btn-link",function(){
			var dropdownId = $(this).attr("dropdownId");
			$("#"+dropdownId).dropdown('close');
		});
		//弹出评论
		$("#fwzs_answer_ul").on("click",".fwzs_comment",function(){
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
		$("#fwzs_answer_ul").on("click",".fwzs_submit_comment",function(){
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
		if(inviteCount>=8){
      		$("#inviteCount").text("邀请人数已达上线");
      	}else{
      		$("#inviteCount").text("您还可以邀请"+(8-inviteCount)+"个人回答");
      	}
    });
    function pubAnswer(){
    	if(isLogin == false){
 			loginDialog();
 			return;
 		}
    	$("#myEditor").val(UE.getEditor('myEditor').getContent());
    	if(!UE.getEditor('myEditor').hasContents()){
    		alert("请输入您的答案");
    		return;
    	}
    	$("#myForm").submit();
    }
    
    var inviteCount = '${inviteCount}';
    function inviteUser(span,userId){
    	if(isLogin == false){
			loginDialog();
			return;
		}
    	if(inviteCount >= 8){
    		alert("邀请人数已达上线");
    		return;
    	}
    	$(span).removeAttr("onclick");
    	$.ajax({
  	        type: "post",
  	        url: basePath+"question/inviteUser.shtml",
  	        data: "questionId=${question.questionId}&userId="+userId,
  	        dataType: "json",
  	        success: function(data){
  	        	inviteCount++;
  	        	if(inviteCount>=8){
  	        		$("#inviteCount").text("邀请人数已达上线");
  	        	}else{
  	        		$("#inviteCount").text("您还可以邀请"+(8-inviteCount)+"个人回答");
  	        	}
  	        	$(span).html("已邀请");
  	        	$(span).css("background-color","RGB(184,184,184)");
  	        }
  	    });
    }
    </script>
  </head>
  <body>
  	<%@include file="/pages/common/common_top.jsp" %>
  	<div class="fwzs_content">
  		<div class="fwzs_content_left">
  			<div class="fwzs_question_detail">
	  			<div class="fwzs_question_title">${question.questionTitle }<span>分享<div class="bshare-custom icon-medium"><a title="分享到新浪微博" class="bshare-sinaminiblog"></a><a title="分享到QQ空间" class="bshare-qzone"></a><a title="分享到腾讯微博" class="bshare-qqmb"></a><a title="更多平台" class="bshare-more bshare-more-icon more-style-addthis"></a></div><script type="text/javascript" charset="utf-8" src="http://static.bshare.cn/b/buttonLite.js#style=-1&amp;uuid=&amp;pophcol=2&amp;lang=zh"></script><script type="text/javascript" charset="utf-8" src="http://static.bshare.cn/b/bshareC0.js"></script></span></div>
	  			<c:forEach items="${questionTopic }" var="topic">
	  			<button type="button" class="am-btn fwzs_question_detail_topic am-round">${topic.topic_title }</button>
	  			</c:forEach>
	  			<div class="fwzs_question_content">${question.questionContent }</div>
	  			<div class="fwzs_action_info">
	  			<c:if test="${question.followFlag == 0}">
	  				<span id="${question.questionId }" class="fwzs_gz">关注</span>
	  			</c:if>
	  			<c:if test="${question.followFlag == 1}">
	  				<span id="${question.questionId }" class="fwzs_gz fwzs_gz_true">取消关注</span>
	  			</c:if>
	  			<div class="am-dropdown" id="inviteDropdown">
				  <span questionId="${question.questionId}" class="am-dropdown-toggle fwzs_action fwzs_comment" id="inviteSpan">邀请回答</span>
				  <div class="am-dropdown-content">
				    <input type="text" id="keyword" placeholder="搜索你要邀请的人" style="width: 270px;margin-right: 20px;"><span id="inviteCount"></span>
				    <ul id="userList" class="am-list am-list-static">
				    </ul>
				  </div>
				</div>
	  			</div>
		  	</div>
	  		<ul class="fwzs_answer_ul" id="fwzs_answer_ul">
	  			<c:forEach items="${listAnswer }" var="answer">
	  			<li>
	  			<div class="fwzs_answer_left">
	  				<c:if test="${answer.praiseFlag == 0}">
	  				<div id="${answer.answerId}" class="fwzs_answer_agree">
		  				<span id="zanCount${answer.answerId }">${answer.agreeCount }</span><br>
		  				<span id="zanTxt${answer.answerId}">点赞</span>
	  				</div>
	  				</c:if>
	  				<c:if test="${answer.praiseFlag == 1}">
	  				<div id="${answer.answerId}" class="fwzs_answer_agree fwzs_zan_true">
		  				<span id="zanCount${answer.answerId }">${answer.agreeCount }</span><br>
		  				<span id="zanTxt${answer.answerId}">取消赞</span>
	  				</div>
	  				</c:if>
	  			</div><div class="fwzs_answer_right">
	  				<c:if test="${answer.anonymous == 0}">
	  				<div class="fwzs_answer_user">${answer.userName}
	  				<c:if test="${answer.certifyStatus==2 }"><img class="fwzs_icon_v" src="<%=basePath %>static/images/icon_v.png"></img></c:if>
	  				：${answer.oneWord }</div>
	  				</c:if>
	  				<c:if test="${answer.anonymous == 1}">
	  				<div class="fwzs_answer_user">匿名</div>
	  				</c:if>
	  				<div>
	  				<c:if test="${answer.agreeList.size()>0 }">
	  					<c:forEach items="${answer.agreeList }" var="user">${user.user_name }、</c:forEach>
	  					等人赞同<span class="fwzs_answer_time">${answer.addTime }</span></div>
	  				</c:if>
	  				<div>${answer.answerContent }</div>
	  				<div class="fwzs_action_info">
		  				<div id="dropdown_${answer.answerId}" class="am-dropdown">
						  <span answerId="${answer.answerId}" dropdownId="dropdown_${answer.answerId}" loadflag="0" class="am-dropdown-toggle fwzs_action fwzs_comment"><c:if test="${answer.commentCount == 0 }">添加评论</c:if><c:if test="${answer.commentCount > 0 }">${answer.commentCount}条评论</c:if></span>
						  <div class="am-dropdown-content am-form">
						    <ul id="comment_${answer.answerId}" class="am-list am-list-static">
						    </ul>
						    <input id="message_${answer.answerId}" type="text" placeholder="写出您的评论">
						    <div class="fwzs_commend_action">
						    	<span dropdownId="dropdown_${answer.answerId}" class="am-btn am-btn-link">取消</span>
						    	<button answerId="${answer.answerId}" type="button" class="am-btn am-btn-secondary fwzs_submit_comment" data-am-loading="{loadingText: '提交中...'}">提交</button>
						    </div>
						  </div>
						</div>
		  				<span class="fwzs_dot">●</span>
		  				<c:if test="${answer.followFlag == 1}">
		  					<span id="${answer.answerId}" class="fwzs_sc fwzs_action">取消收藏</span>
		  				</c:if>
		  				<c:if test="${answer.followFlag == 0}">
		  					<span id="${answer.answerId}" class="fwzs_sc fwzs_action">收藏</span>
		  				</c:if>
	  				</div>
	  			</div></li>
	  			</c:forEach>
	  		</ul>
	  		<div class="fwzs_pub_answer_content">
	  		<!-- 未登录 -->
	  		<c:if test="${UserSession.userId == null}">
	  			<center>请先登录后，再回答问题</center>
	  		</c:if>
	  		<c:if test="${UserSession.userId != null}">
	  			<div class="fwzs_pub_answer_userInfo">
	  			<img src="<%=basePath %>${ynkj:parseHeadImg(UserSession.avatarFile)}" class="am-img-thumbnail fwzs_answer_head">
	  			<span>${UserSession.userName}</span><br>
	  			<c:if test="${UserSession.oneWord == null}">此人很懒，什么都没留下</c:if>
	  			<c:if test="${UserSession.oneWord != null}">${UserSession.oneWord}</c:if>
	  			</div>
	  			<form id="myForm" action="<%=basePath %>answer/saveAnswer.shtml" method="post">
		  			<input name="questionId" value="${question.questionId }" type="hidden">
		  			<textarea name="answerContent" id="myEditor" style="width:100%;height:240px;"></textarea>
		  			<input name="anonymous" value="1" style="margin-right: 10px;margin-top: 15px;" type="checkbox">匿名
		  			<span type="button" style="float: right;border-radius:0;width: auto" onclick="pubAnswer()" type="button" class="am-btn am-btn-secondary">回答</span>
	  			</form>
	  			
	  		</c:if>
	  		</div>
  		</div><div class="fwzs_content_right"><%@include file="/pages/common/common_right.jsp" %></div>
  	</div>
  	<%@include file="/pages/common/common_bottom.jsp" %>
  </body>
</html>
