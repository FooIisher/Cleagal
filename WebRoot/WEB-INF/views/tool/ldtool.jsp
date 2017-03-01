<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/pages/common/mytags.jsp" %>
<%@include file="/pages/common/commonjs_css.jsp" %>
<html>
  <head>
    <title>劳务合同赔偿计算器-法务助手</title>
    <style type="text/css">
    	#button_css button{
    		width: 320px;
    	}
    </style>
    <script type="text/javascript">
   function getPayResult(){
   		var method = $("#method").val();
    	switch(method){
    	case '1':
    		var month = parseFloat($("#month").val());
    		var payavg = parseFloat($("#payavg").val());
    		$("#payresult").html("赔偿金为：￥"+(month-1)*payavg);
    		break;
    	case '2':
    		var month = parseFloat($("#month").val());
    		var payavg = parseFloat($("#payavg").val());
    		var num = month%6 > 0 ? 0.5:0;
    		$("#payresult").html("赔偿金为：￥"+payavg*(parseInt(month/6)+num));
    		break;
    	}
   
   }
 
    function getResult(){
    	var lv = $("#level").val();
    	switch(lv){
    	case '1':
    		var money = 1.5*parseFloat($("#avg").val());
    		$("#result").html("每小时发放￥"+money+"加班工资");
    		break;
    	case '2':
    		var money = 2*parseFloat($("#avg").val());
    		$("#result").html("每小时发放￥"+money+"加班工资");
    		break;
    	case '3':
    		var money = 3*parseFloat($("#avg").val());
    		$("#result").html("每小时发放￥"+money+"加班工资");;
    		break;
    	}
    }
    function reset(){
    	$("#avg").val('');
    	$("#result").html('');
    	
    }
    function payreset(){
    	$("#payavg").val('');
    	$("#payresult").html('');
    	$("#month").val('');
    }
    $(function(){
    	var flag = location.search.split("=")[1];
    	if(flag == 1){
    		$("#flag").attr("selected","selected");
    	}
    });
    </script>
  </head>
  <body>
  	<%@include file="/pages/common/common_top.jsp" %>
  	<div class="fwzs_content">
  		<div class="fwzs_content_left am-form am-form-horizontal" style="padding-top: 20px;">
  		<div style="text-align: center;font-size: 20px;font-weight: bold;margin-bottom: 20px;">劳务合同赔偿计算器</div>
  		 
		
		
			<div class="am-tabs" data-am-tabs="{noSwipe: 1}" id="doc-tab-demo-1">
			  <ul class="am-tabs-nav am-nav am-nav-tabs am-nav-justify" style="font-size: 18px">
			    <li class="am-active"><a href="javascript: void(0)">赔偿金</a></li>
			    <li><a href="javascript: void(0)">加班工资</a></li>
			  </ul>
			
			  <div class="am-tabs-bd">
			    <div class="am-tab-panel am-active">
			     		<div class="am-form-group">
						    <label for="doc-ipt-3" style="font-size: 15px" class="am-u-sm-4 am-form-label">请选择补偿类型</label>
						    <div class="am-u-sm-8" id="button_css">
							      <select id="method" data-am-selected>
						  			<option value="1">未签订书面劳动合同赔偿金</option>
						  			<option value="2" id="flag">经济补偿金</option>
								  </select>
							    </div>
							</div>
							<div class="am-form-group">
							    <label for="doc-ipt-3" style="font-size: 15px" class="am-u-sm-4 am-form-label">请输入工作月份</label>
							    <div class="am-u-sm-8">
							      <input style="width: 320px;" id="month" type="text">
							    </div>
							</div>
							<div class="am-form-group">
							    <label for="doc-ipt-3" style="font-size: 15px" class="am-u-sm-4 am-form-label">请输入月工资</label>
							    <div class="am-u-sm-8">
							      <input style="width: 320px;" id="payavg" type="text">
							    </div>
							</div>
						 
							<div class="am-form-group">
								<div class="am-u-sm-8 am-u-sm-offset-4">
							    	<button onclick="getPayResult()" type="button" class="am-btn am-btn-danger">计算赔偿金</button>
							    	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							    	<button onclick="payreset()" type="button" class="am-btn">重置</button>
							    </div>
							</div>
							<div class="am-form-group">
								<div class="am-u-sm-8 am-u-sm-offset-4">
							    	<div id="payresult" style="margin-top: 20px;font-size: 20px;color: red"></div>
							    </div>
							</div>
			    </div>
			    <div class="am-tab-panel">
			     		<div class="am-form-group">
						    <label for="doc-ipt-3" style="font-size: 15px" class="am-u-sm-4 am-form-label">请选择加班时间</label>
						    <div class="am-u-sm-8">
							      <select id="level" data-am-selected>
						  			<option value="1">标准工作日（40小时/周）</option>
						  			<option value="2">休息日（周六、周日）</option>
						  			<option value="3">法定休假日（十一、五一、春节等）</option>
						  		 
								  </select>
							    </div>
							</div>
							<div class="am-form-group">
							    <label for="doc-ipt-3" style="font-size: 15px" class="am-u-sm-4 am-form-label">请输入每小时工资</label>
							    <div class="am-u-sm-8">
							      <input style="width: 320px;" id="avg" type="text">
							    </div>
							</div>
							<div class="am-form-group">
								<div class="am-u-sm-8 am-u-sm-offset-4">
							    	<button onclick="getResult()" type="button" class="am-btn am-btn-danger">计算加班工资</button>
							    	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							    	<button onclick="reset()" type="button" class="am-btn">重置</button>
							    </div>
							</div>
							<div class="am-form-group">
								<div class="am-u-sm-8 am-u-sm-offset-4">
							    	<div id="result" style="margin-top: 20px;font-size: 20px;color: red"></div>
							    </div>
							</div>
			    </div>
			    
			  </div>
			</div>
  		</div><div class="fwzs_content_right"><%@include file="/pages/common/common_right.jsp" %></div>
  	</div>
  	<%@include file="/pages/common/common_bottom.jsp" %>
  </body>
</html>
