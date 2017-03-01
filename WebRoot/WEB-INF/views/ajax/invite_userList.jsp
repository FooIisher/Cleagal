<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/pages/common/mytags.jsp" %>
<c:forEach items="${userList }" var="user">
	<li style="margin-top: 0">
	<div style="position: relative;width: 50px;height: 50px;float: left;">
	<img style="width: 100%" src="<%=basePath %>${ynkj:parseHeadImg(user.avatarFile)}"></img>
	<c:if test="${user.certifyStatus==2 }"><img class="fwzs_icon_vv" src="<%=basePath %>static/images/icon_vv.png"></img></c:if>
	</div>
	<div style="padding-left: 60px;">
	<span style="color: black;">${user.userName }</span>
	<br><span>回答了${user.answerCount }个问题
		<c:if test="${user.inviteFlag == 0}">
			<span onclick="inviteUser(this,'${user.userId}')" style="float: right;background-color: RGB(129,166,221);color:white;padding: 2px 10px;font-size: 12px;">邀请</span>
		</c:if>
		<c:if test="${user.inviteFlag == 1}">
			<span style="float: right;background-color: RGB(184,184,184);color:white;padding: 2px 10px;font-size: 12px;">已邀请</span>
		</c:if>
	</span>
	</div>
	</li>
</c:forEach>