<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/pages/common/mytags.jsp" %>
 		 		<c:forEach items="${listQuestion}" var="question">
  		 			<li>
  		 				<div class="search_question_title"> 
  		 					<a  href="<%=basePath %>question/detail${question.questionId }.shtml">
  		 					<c:set var="v_param" value="<font color=red>${q}</font>" />    
  		 					${fn:replace(question.questionTitle, 
                                q, v_param)}
  		 					</a>
  		 				</div>
  		 				<div class="search_question_info">
  		 				<c:if test="${question.answerId != null}">
  		 					<div class="search_question_left"> 
  		 						<div 
  		 							<c:if test="${question.praiseFlag == 1}">
  		 								class="search_question_zan" 
  		 							</c:if>	
  		 						 	<c:if test="${question.praiseFlag == 0}">
  		 						 		class="search_question_nozan" 
  		 						 	</c:if>	
  		 						name="zanDiv" id = "${question.answerId}">
  		 							<div id = "zanCount${question.answerId}">
  		 								${question.agreeCount}
  		 							</div>
  		 							<div id = "zanTitle${question.answerId}">
  		 								点赞
  		 							</div>
  		 						</div>
  		 					</div>
  		 				</c:if>	
  		 					<div 	<c:if test="${question.answerId != null}">
  		 								class="search_question_right"
  		 							</c:if>
  		 						>
  		 						<span class="search_question_time">${question.addTime}</span>
  		 						<div class="search_question_name">
  		 							${question.userName}    ${question.oneWord}
  		 						</div>
  		 					<c:if test="${question.answerId != null}">	
  		 						<div class="search_question_content">
  		 							${question.answerContent}
  		 							<span class="search_question_showall">显示全部</span>
  		 						</div>
  		 					</c:if>	
  		 						<div class="search_info">
  		 						<span id = "gzHtml${question.questionId}">
  		 							<c:if test="${question.followFlag == 1}">
  		 								<a  href="javascript:unfollowQuestion('${question.questionId}');">取消关注</a>
  		 						 	</c:if>	
  		 						 	<c:if test="${question.followFlag == 0}">
  		 						 		<a  href="javascript:userFollowQuestion('${question.questionId}');">添加关注</a>
  		 						 	</c:if>	
  		 						</span>	
  		 						<span class="search_dot">●</span>
  		 							${question.answerCount}个回答
  		 						</div>
  		 					</div>
  		 				</div>
  		 			</li>
  		 		</c:forEach> 