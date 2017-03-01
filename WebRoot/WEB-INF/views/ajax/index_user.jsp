<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/pages/common/mytags.jsp" %>
<c:forEach items="${listUsers}" var="user">
	<li>
		<div class="fwzs_recommend_left" style="display:inline" userId=${user.userId }>
	  		<img src="<%=basePath %>${ynkj:parseHeadImg(user.avatarFile)}" class="am-img-thumbnail fwzs_recommend_head" onclick="javascript:window.open('<%=basePath %>member/${user.userId}.shtml')">
	  			<c:if test="${user.certifyStatus==2 }">
	  				<img class="fwzs_icon_vv" src="<%=basePath %>static/images/icon_vv.png"></img>
	  			</c:if>
		</div>
		<div class="fwzs_recommend_right" style="display:inline" >
			<div style="margin-top: 5px;">用户姓名：${user.userName }</div>
		 	<div style="margin-top: 5px;">用户密码：${user.password }</div>
			<div style="margin-top: 5px;">
	  			<c:if test="${user.sex==-1}">
	  				用户性别：未知
	  			</c:if>
		  		<c:if test="${user.sex==0}">
		  			用户性别：男性
		  		</c:if>
		  		<c:if test="${user.sex==1}">
		  			用户性别：女性
		  		</c:if>
		  	</div>
		  	<div style="margin-top: 5px;">注册时间：${user.regTime }</div>
		  	<div class="fwzs_recommend_intro">个人介绍：${user.individualResume }</div>
		  	<div class="fwzs_recommend_chat">
		  		<button type="button" class="am-btn am-btn-success am-radius index_chat"  onclick="javascript:window.open('<%=basePath %>member/${user.userId}.shtml')">查看详细信息</button>
	  	 	</div>
	  	</div>
	 </li>
</c:forEach>