<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/pages/common/mytags.jsp" %>
<%@include file="/pages/common/commonjs_css.jsp" %>
<html>
  <head>
    <title>工伤计算器-法务助手</title>
    <script type="text/javascript">
    $(function(){
    	$.ajax({
            type: "post",
            url: basePath+"tool/ajaxQueryToolArea.shtml",
            data: "",
            dataType: "html",
            success: function(data){
            	$("#fwzs_province").html(data);
            	changeCity();
            	$('select').selected();
            }
        });
    });
    function changeCity(){
		$.ajax({
	        type: "post",
	        url: basePath+"tool/ajaxQueryToolArea.shtml",
	        data: "parentId="+$("#fwzs_province").val(),
	        dataType: "html",
	        success: function(data){
	        	$("#fwzs_city").html(data);
	        	$('select').selected();
	        }
	    });
	}
    function getResult(){
    	var lv = $("#level").val();
    	switch(lv){
    	case '1':
    		var money = 27*parseFloat($("#avg").val());
    		var month = 0.9*parseFloat($("#avg").val());
    		$("#result").html("￥"+money+"，每月发放￥"+month+"【不得解除劳动关系】<br>备注：误工费、治疗费、护理费等费用，以实际支出为准。");
    		break;
    	case '2':
    		var money = 25*parseFloat($("#avg").val());
    		var month = 0.85*parseFloat($("#avg").val());
    		$("#result").html("￥"+money+"，每月发放￥"+month+"【不得解除劳动关系】<br>备注：误工费、治疗费、护理费等费用，以实际支出为准。");
    		break;
    	case '3':
    		var money = 23*parseFloat($("#avg").val());
    		var month = 0.80*parseFloat($("#avg").val());
    		$("#result").html("￥"+money+"，每月发放￥"+month+"【不得解除劳动关系】<br>备注：误工费、治疗费、护理费等费用，以实际支出为准。");
    		break;
    	case '4':
    		var money = 21*parseFloat($("#avg").val());
    		var month = 0.75*parseFloat($("#avg").val());
    		$("#result").html("￥"+money+"，每月发放￥"+month+"【不得解除劳动关系】<br>备注：误工费、治疗费、护理费等费用，以实际支出为准。");
    		break;
    	case '5':
    		var money = 18*parseFloat($("#avg").val())+52*parseFloat($("#fwzs_city").find("option:selected").attr("avgWages"));
    		$("#result").html("￥"+money+"【解除劳动关系】<br>备注：误工费、治疗费、护理费等费用，以实际支出为准。");
    		break;
    	case '6':
    		var money = 16*parseFloat($("#avg").val())+44*parseFloat($("#fwzs_city").find("option:selected").attr("avgWages"));
    		$("#result").html("￥"+money+"【解除劳动关系】<br>备注：误工费、治疗费、护理费等费用，以实际支出为准。");
    		break;
    	case '7':
    		var money = 13*parseFloat($("#avg").val())+34*parseFloat($("#fwzs_city").find("option:selected").attr("avgWages"));
    		$("#result").html("￥"+money+"<br>备注：误工费、治疗费、护理费等费用，以实际支出为准。");
    		break;
    	case '8':
    		var money = 11*parseFloat($("#avg").val())+28*parseFloat($("#fwzs_city").find("option:selected").attr("avgWages"));
    		$("#result").html("￥"+money+"<br>备注：误工费、治疗费、护理费等费用，以实际支出为准。");
    		break;
    	case '9':
    		var money = 9*parseFloat($("#avg").val())+22*parseFloat($("#fwzs_city").find("option:selected").attr("avgWages"));
    		$("#result").html("￥"+money+"<br>备注：误工费、治疗费、护理费等费用，以实际支出为准。");
    		break;
    	case '10':
    		var money = 7*parseFloat($("#avg").val())+16*parseFloat($("#fwzs_city").find("option:selected").attr("avgWages"));
    		$("#result").html("￥"+money+"<br>备注：误工费、治疗费、护理费等费用，以实际支出为准。");
    		break;
    	}
    }
    function reset(){
    	$("#avg").val('');
    	$("#result").html('');
    	
    }
    </script>
  </head>
  <body>
  	<%@include file="/pages/common/common_top.jsp" %>
  	<div class="fwzs_content">
  		<div class="fwzs_content_left am-form am-form-horizontal" style="padding-top: 20px;">
  		<div style="text-align: center;font-size: 20px;font-weight: bold;margin-bottom: 20px;">工伤赔偿计算器</div>
  		<div class="am-form-group">
		    <label for="doc-ipt-3" style="font-size: 15px" class="am-u-sm-4 am-form-label">请选择省/市</label>
		    <div class="am-u-sm-8">
		      <select id="fwzs_province" data-am-selected onchange="changeCity()">
				</select>
				<select id="fwzs_city" data-am-selected>
				</select>
		    </div>
		</div>
		<div class="am-form-group">
		    <label for="doc-ipt-3" style="font-size: 15px" class="am-u-sm-4 am-form-label">请选择工伤级别</label>
		    <div class="am-u-sm-8">
		      <select id="level" data-am-selected>
	  			<option value="1">一级伤残</option>
	  			<option value="2">二级伤残</option>
	  			<option value="3">三级伤残</option>
	  			<option value="4">四级伤残</option>
	  			<option value="5">五级伤残</option>
	  			<option value="6">六级伤残</option>
	  			<option value="7">七级伤残</option>
	  			<option value="8">八级伤残</option>
	  			<option value="9">九级伤残</option>
	  			<option value="10">十级伤残</option>
			  </select>
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
