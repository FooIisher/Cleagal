<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<%@include file="/pages/common/mytags.jsp" %>
<%@include file="/pages/common/commonjs_css.jsp" %>
<html>
  <head>
    <title>问题反馈-法务助手</title>
    <script type="text/javascript">
    $(function(){
    	setTimeout(function(){
    		location.href="<%=basePath%>";
    	}, 3000);
    });
    </script>
  </head>
  <body>
  	<%@include file="/pages/common/common_top.jsp" %>
  	<div class="fwzs_content">
  		<div class="fwzs_content_left">
  		<center style="margin-top: 40px;color: green"><h1>提交成功!</h1>
  			3秒后跳转到首页
  		</center>
  		</div><div class="fwzs_content_right"><%@include file="/pages/common/common_right.jsp" %></div>
  	</div>
  	<%@include file="/pages/common/common_bottom.jsp" %>
  </body>
</html>
