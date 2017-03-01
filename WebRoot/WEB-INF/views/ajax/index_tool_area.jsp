<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/pages/common/mytags.jsp" %>
<c:forEach items="${areaList }" var="area">
  <option value="${area.area_id }" avgWages="${area.avg_wages }" >${area.area_name }</option>
</c:forEach>