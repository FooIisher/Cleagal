<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/pages/common/mytags.jsp" %>
<c:forEach items="${commentList }" var="comment">
	<li style="margin-top: 0">
	<div style="position: relative;width: 50px;height: 50px;float: left;">
	<img style="width: 100%" src="<%=basePath %>${ynkj:parseHeadImg(comment.avatarFile)}"></img>
	<c:if test="${comment.certifyStatus==2 }"><img class="fwzs_icon_vv" src="<%=basePath %>static/images/icon_vv.png"></img></c:if>
	</div>
	<div style="padding-left: 60px;">
	<span style="color: black;">${comment.userName }</span>
	<br><span>${comment.message }<span style="float: right">${comment.addTime.substring(0,10) }</span></span>
	</div>
	</li>
</c:forEach>