<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/pages/common/mytags.jsp" %>
<%@include file="/pages/common/commonjs_css.jsp" %>
<html>
  <head>
    <title>工具-法务助手</title>
    <style type="text/css">
    	.fwzs_tool_ul{
    		margin-top: 80px;
    	}
    	.fwzs_tool_ul li{
    		border: none !important;cursor: pointer;text-align: center;font-size: 14px;
    	}
    	.fwzs_tool_ul li .am-thumbnail{
    		margin-bottom: 10px;width: 100%;border: none;
    	}
    </style>
    <script type="text/javascript">
    $(function(){
    	$(".fwzs_tool_ul li").click(function(){
    		location.href=$(this).attr("href");
    	});
    });	
    </script>
  </head>
  <body>
  	<%@include file="/pages/common/common_top.jsp" %>
  	<div class="fwzs_content">
  		<div class="fwzs_content_left">
	  		<ul class="am-avg-sm-2 am-thumbnails fwzs_tool_ul">
			  <li href="gstool.shtml"><img class="am-thumbnail" src="<%=basePath %>static/images/tool_gs.png" /></li>
			  <li href="ldtool.shtml"><img class="am-thumbnail" src="<%=basePath %>static/images/tool_ht.png" /></li>
			  <li href="swtool.shtml"><img class="am-thumbnail" src="<%=basePath %>static/images/tool_ht.png" /></li>
			</ul>
  		</div><div class="fwzs_content_right"><%@include file="/pages/common/common_right.jsp" %></div>
  	</div>
  	<%@include file="/pages/common/common_bottom.jsp" %>
  </body>
</html>
