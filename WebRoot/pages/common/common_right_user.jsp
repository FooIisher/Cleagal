<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script src="<%=basePath %>amazeui/js/amazeui.min.js"></script>
<script>
$.ajax({
       type: "post",
       url: basePath+"member/ajaxMyFollowQuestions.shtml",
       data: "userId=${user.userId}",
       dataType: "html",
       success: function(data){
       	$("#fwzs_right_question").html(data);
       }
});
</script>
<link rel="stylesheet" type="text/css" href="<%=basePath %>static/css/common_right.css">
<div id="fwzs_right_question"></div>
