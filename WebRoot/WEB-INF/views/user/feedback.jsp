<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<%@include file="/pages/common/mytags.jsp" %>
<%@include file="/pages/common/commonjs_css.jsp" %>
<html>
  <head>
    <title>问题反馈-法务助手</title>
    
    <script type="text/javascript">
    $(function(){
    	var um = UM.getEditor('myEditor');
    });
    function pubAnswer(){
    	if(isLogin == false){
 			loginDialog();
 			return;
  		}
    	$("#myEditor").val(UM.getEditor('myEditor').getContent());
    	if(!UM.getEditor('myEditor').hasContents()){
    		alert("请输入您的建议");
    		return;
    	}
    	$("#myForm").submit();
    }
    </script>
  </head>
  <body>
  	<%@include file="/pages/common/common_top.jsp" %>
  	<div class="fwzs_content">
  		<div class="fwzs_content_left">
	  		<div class="fwzs_pub_answer_content">
	  			<center style="margin-top: 40px"><h1>请输入您的建议或意见</h1></center>
	  			<form id="myForm" action="<%=basePath %>member/saveFeedback.shtml" method="post">
		  			<textarea name="content" id="myEditor" style="width:100%;height:240px;"></textarea>
		  			<span type="button" style="float: right;border-radius:0;width: auto" onclick="pubAnswer()" type="button" class="am-btn am-btn-secondary">提交</span>
	  			</form>
	  		</div>
  		</div><div class="fwzs_content_right"><%@include file="/pages/common/common_right.jsp" %></div>
  	</div>
  	<%@include file="/pages/common/common_bottom.jsp" %>
  </body>
</html>
