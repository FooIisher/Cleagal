<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/pages/common/mytags.jsp" %>
 <c:forEach items="${listCases }" var="cases">
	  		 <div class="caselist_img"  onclick="javascript:window.open('<%=basePath %>case/detail${cases.caseId}.shtml')"  style="background-image: url('<%=basePath %>static/images/caselist.png')">
	  		 	 	<div class="caselist_background">
	  		 	 		<div class="caselist_content"> 
	  		 	 			<div class="caselist_title">${cases.caseTitle}</div>
	  		 	 			<div class="caselist_info">
	  		 	 				${cases.userName}
	  		 	 				<span class="caselist_dot">●</span>
	  		 	 				${cases.publishTime}
	  		 	 				<span class="caselist_dot">●</span>
	  		 	 				评论：${cases.commentCount}
	  		 	 				<span class="caselist_dot">●</span>
	  		 	 				收藏：${cases.followCount}
	  		 	 			</div>	
	  		 	 		</div>
	  		 	 	</div>
	  		 </div>
</c:forEach> 