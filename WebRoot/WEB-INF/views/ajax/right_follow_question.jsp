<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/pages/common/mytags.jsp" %>
<div class="fwzs_gz_div" style="cursor: pointer;" onclick="javascript:location.href='<%=basePath %>member/${userId}/followees.shtml';"><font size="4">${followCount }</font><br><font size="2">关注</font></div><div class="fwzs_gz_div fwzs_gz_border"style="cursor: pointer;" onClick="javascript:location.href='<%=basePath %>/member/${userId}/followers.shtml'"><font size="4">${followedCount }</font><br><font size="2">被关注</font></div>
<div class="fwzs_tool fwzs_gz">
	<div class="fwzs_gz_title fwzs_title"><span class="fwzs_title_txt">关注了<a class="fwzs_gz_count" href="<%=basePath %>member/${userId}/followQuestions.shtml">${questionCount }个问题</a></span><a class="fwzs_more" href="#"><i class="am-icon-sm am-icon-angle-right"></i></a></div>
	<ul id="listQuestion">
		<c:forEach items="${listQuestion }" var="question">
			<li><a href="<%=basePath %>question/detail${question.questionId }.shtml">${question.questionTitle }</a></li>
		</c:forEach>
	</ul>
</div>
