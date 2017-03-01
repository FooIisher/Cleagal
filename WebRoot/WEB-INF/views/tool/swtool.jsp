<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/pages/common/mytags.jsp" %>
<%@include file="/pages/common/commonjs_css.jsp" %>
<html>
  <head>
    <title>死亡赔偿算器-法务助手</title>
    <script type="text/javascript">
    
    
    function getResult(){
    	//$("#result").html(parseFloat($("#avg").val())+"--"+parseFloat($("#age").val())+"----");
    	var avg = parseFloat($("#avg").val());
    	var age = parseFloat($("#age").val());
    	var total = 0;
    	//$("#result").html(avg+"--"+age+"----");
    	if(age>80||age<20)
    		$("#result").html("输入的年龄不在死亡赔偿范围内！");
    	else{
    		if(age>=75){
    			$("#result").html("75-80");
    			total = 5 * 12 * avg;
    		}
    		if(age<75&&age>=60){
    			$("#result").html("60-75");
    			total = (20-(age-60)) * 12 * avg;
    		}
    		if(age<60){
    			$("#result").html("20-60");
    			total = 20 * 12 * avg;
    		} 
    			
    		
    		$("#result").html("根据相关法律，大约赔偿"+total+"元！");
    	}
    }
    	
    function reset(){
    	$("#avg").val('');
    	$("#age").val('');
    	$("#result").html('');
    	
    }
    </script>
  </head>
  <body>
  	<%@include file="/pages/common/common_top.jsp" %>
  	<div class="fwzs_content">
  		<div class="fwzs_content_left am-form am-form-horizontal" style="padding-top: 20px;">
  			<div style="text-align: center;font-size: 20px;font-weight: bold;margin-bottom: 20px;">死亡赔偿计算器</div>
			<div class="am-form-group">
		   		<label for="doc-ipt-3" style="font-size: 15px" class="am-u-sm-4 am-form-label">请输入死亡年龄</label>
		   		<div class="am-u-sm-8">
		    	<input style="width: 320px;" id="age" type="text">
		   		</div>
			</div>
			<div class="am-form-group">
		   		<label for="doc-ipt-3" style="font-size: 15px" class="am-u-sm-4 am-form-label">请输入平均工资</label>
		    	<div class="am-u-sm-8">
		    	<input style="width: 320px;" id="avg" type="text">
		    	</div>
			</div>
			<div class="am-form-group">
				<div class="am-u-sm-8 am-u-sm-offset-4">
		    		<button onclick="getResult()" type="button" class="am-btn am-btn-danger">计算赔偿金额</button>
		    		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		    	<button onclick="reset()" type="button" class="am-btn">重置</button>
		   		</div>
			</div>
			<div class="am-form-group">
				<div class="am-u-sm-8 am-u-sm-offset-4">
		    		<div id="result" style="margin-top: 20px;font-size: 18px;color: red"></div>
		    	</div>
			</div>
  		</div><div class="fwzs_content_right"><%@include file="/pages/common/common_right.jsp" %></div>
  	</div>
  	<%@include file="/pages/common/common_bottom.jsp" %>
  </body>
</html>