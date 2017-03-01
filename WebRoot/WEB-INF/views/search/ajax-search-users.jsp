<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/pages/common/mytags.jsp" %>
 		 		<c:forEach items="${listUsers}" var="users">
  		 			<li>
  		 				<div class="search_user_info">
  		 					<div class="search_user_left">
  		 						<img src="<%=basePath %>${users.avatarFile}" class="search_user_head">
  		 					</div>
  		 					<div class="search_user_right">
  		 						<div>
  		 							<font class="search_user_name">
  		 								 <c:set var="v_param" value="<font color=red>${q}</font>" />    
		  		 						${fn:replace(users.userName, 
		                               		 q, v_param)}&nbsp;&nbsp;&nbsp;
										<c:if test="${users.sex == 0}">
											女
										</c:if>
										<c:if test="${users.sex == 1}">
											男
										</c:if>
  		 							</font>
  		 							
  		 							<a href="#" class="search_user_data">关注者 ${users.fansCount}</a>
  		 							<a href="#" class="search_user_data">案例 ${users.caseCount}</a>
  		 							<a href="#" class="search_user_data">回答 ${users.answerCount}</a>
  		 						</div>
  		 						<div class="search_user_content">
  		 							 
  		 							<c:if test="${users.oneWord==null}">
  		 							 	此人很难,什么都没留下
  		 							</c:if>
  		 							<c:if test="${users.oneWord!=null}">
  		 							 	${users.oneWord}
  		 							</c:if>
  		 							<c:if test="${users.followFlag==1}">
  		 								<span class="search_user_gz" title="${users.userId}" name="gzSpan">取消关注</span>
  		 							</c:if>	
  		 							<c:if test="${users.followFlag==0}">
  		 								<span class="search_user_ungz" title="${users.userId}" name="gzSpan">关注</span>
  		 							</c:if>	
  		 						</div>
  		 					</div>
  		 				</div>
  		 			</li>
  		 		</c:forEach> 