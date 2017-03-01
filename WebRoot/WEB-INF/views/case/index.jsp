<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/pages/common/mytags.jsp" %>
<%@include file="/pages/common/commonjs_css.jsp" %>
<html>
  <head>
    <title>案例-法务助手</title>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>static/css/case.css">
  </head>
  <body>
  	<%@include file="/pages/common/common_top.jsp" %>
  	<div class="fwzs_content">
  		<div class="fwzs_content_left">
	  		<ul class="fwzs_case_ul">
	  		<c:forEach items="${listTopicCases }" var="topic">
	  			<li id="${topic.topicId }">
	  			<div class="fwzs_case_left">
	  				<a href="<%=basePath %>case/list${topic.topicId}.shtml" ><img src="<%=basePath %>${topic.topicPic}" class="am-img-thumbnail fwzs_case_img"></a>
	  			</div>
	  			<div class="fwzs_case_right">
	  				<a href="<%=basePath %>case/list${topic.topicId}.shtml" ><div class="fwzs_case_tag">${topic.topicTitle}<i class="am-icon-sm am-icon-angle-right fwzs_case_right_icon"></i></div></a>
	  				<c:forEach items="${topic.listCases }" var="cases">
	  					<a href="<%=basePath %>case/detail${cases.caseId}.shtml" ><div id="${cases.caseId }" class="fwzs_case_title">${cases.caseTitle }</div><div class="fwzs_case_sc">收藏 ${cases.followCount }</div></a>
	  				</c:forEach>
	  			</div>
	  			</li>
	  		</c:forEach>
	  		</ul>
  		</div>
  		<div class="fwzs_content_right"><%@include file="/pages/common/common_right.jsp" %></div>
  	</div>
  	<%@include file="/pages/common/common_bottom.jsp" %>
  </body>
</html>
