<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/pages/common/mytags.jsp" %>
 		 		<c:forEach items="${listCases}" var="cases">
  		 			<li>
  		 				<div class="search_case_info">
  		 					<div class="search_case_left">
  		 							<div   class="search_question_zan"   >
  		 							<div>
  		 								${cases.viewCount}
  		 							</div>
  		 							<div>
  		 								浏览
  		 							</div>
  		 						</div>
  		 					</div>
  		 					<div class="search_case_right">
  		 						<div class="search_case_name" onclick="javascript:window.open('<%=basePath %>case/detail${cases.caseId}.shtml')">
  		 						 	 <c:set var="v_param" value="<font color=red>${q}</font>" />    
		  		 						${fn:replace(cases.caseTitle, 
		                               		 q, v_param)}
  		 						</div>
  		 						<div class="search_case_data">
  		 						<span id="gzHtml${cases.caseId}">
  		 							<c:if test="${cases.followFlag == 1}">
  		 						 		<a  href="javascript:unfollowCase('${cases.caseId}');">取消关注</a>
  		 						 	</c:if>	
  		 						 	<c:if test="${cases.followFlag == 0}">
  		 						 		<a  href="javascript:userFollowCase('${cases.caseId}');">添加关注</a>
  		 						 	</c:if>	
  		 						</span> 	
  		 							<span class="search_dot">●</span>
  		 							${cases.commentCount}个评论
  		 						</div>
  		 					</div>
  		 				</div>
  		 			</li>
  		 		</c:forEach> 