<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/pages/common/mytags.jsp" %>
<c:forEach items="${topicList }" var="topic">
  <option value="${topic.topicId }">${topic.topicTitle }</option>
</c:forEach>