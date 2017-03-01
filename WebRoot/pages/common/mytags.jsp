<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%> 
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="ynkj" uri="http://www.yiniaokeji.com/functions" %>
<!DOCTYPE html>
<% 
String path = request.getContextPath();
String serverPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
pageContext.setAttribute("basePath", basePath);
%>
<script type="text/javascript">
	var basePath = '<%=basePath%>';
</script>

