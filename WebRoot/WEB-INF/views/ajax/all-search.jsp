<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/pages/common/mytags.jsp" %>
			<c:if test="${ fn:length(listUsers) >  0}">
				<li class="am-dropdown-header">用户</li>
			</c:if>	
			<c:forEach items="${listUsers}" var="users">
		    	<li>
		    		<div class="simpleSearch">
  		 					<div class="search_left" style="cursor: pointer;" 
  		 			onclick="searchOpen('<%=basePath%>member/${users.user_id}.shtml')" >
  		 						<img src="<%=basePath%>${users.avatar_file}" class="search_head">
  		 					</div>
  		 					<div class="search_right">
  		 						<div>
  		 							<font class="search_name" style="cursor: pointer;" onclick="javascript:window.open('http://localhost:80/Clegal/member/198957.shtml')">
		  		 						${users.user_name}&nbsp;&nbsp;&nbsp;
  		 							</font>
  		 						</div>
  		 						<div class="search_content">
  		 							<c:if test="${users.oneWord==null}">
  		 							 	此人很难,什么都没留下
  		 							</c:if>
  		 							<c:if test="${users.oneWord!=null}">
  		 							 	${users.oneWord}
  		 							</c:if>
  		 						</div>
  		 					</div>
  		 				</div>
		    	</li>
		    </c:forEach>	
		   
		   	<c:if test="${fn:length(listCases) >  0}">
				<li class="am-dropdown-header">案例</li>
			</c:if>	
			 <c:forEach items="${listCases}" var="cases">
		    	<li><a href="javascript:;" class="am-text-truncate" onclick="searchOpen('<%=basePath %>case/detail${cases.case_id}.shtml')">${cases.case_title}</a></li>
		    </c:forEach>
		    
		    <c:if test="${fn:length(listQuestion) >  0}">
				<li class="am-dropdown-header">问题</li>
			</c:if>	
			 <c:forEach items="${listQuestion}" var="question">
		    	<li><a href="javascript:;" class="am-text-truncate" onclick="searchOpen('<%=basePath %>question/detail${question.question_id }.shtml')">${question.question_title}</a></li>
		    </c:forEach>	
		   
		    	<li style="background-color: #DEDEDE;text-align: center;cursor: pointer;" id="searchMore">查看更多搜索结果</li>