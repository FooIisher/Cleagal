<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/pages/common/mytags.jsp" %>
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
</div><div class="fwzs_answer_right">
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