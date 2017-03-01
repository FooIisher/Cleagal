<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script>
	$.ajax({
        type: "post",
        url: basePath+"recommend/indexRecomment.shtml",
        data: "",
        dataType: "html",
        success: function(data){
        	$("#fwzs_recommend_users").html(data);
        }
    });
	$.ajax({
        type: "post",
        url: basePath+"homepage/ajaxQueryQuestionTopic.shtml",
        data: "",
        dataType: "html",
        success: function(data){
        	$("#fwzs_wytw_topic1").html(data);
        	$.ajax({
    	        type: "post",
    	        url: basePath+"homepage/ajaxQueryQuestionTopic.shtml",
    	        data: "parentId="+$("#fwzs_wytw_topic1").val(),
    	        dataType: "html",
    	        success: function(data){
    	        	$("#fwzs_wytw_topic2").html(data);
    	        	$('select').selected();
    	        }
    	    });
        	$('select').selected();
        }
    });
	function toolGS(){
		location.href="<%=basePath %>tool/gstool.shtml";
	}
	function toolLD(flag){
		location.href="<%=basePath %>tool/ldtool.shtml?flag="+flag;
	}
	function openMember(id){
		location.href="<%=basePath %>member/"+id+".shtml";
	}
	function toolSW(){
		location.href="<%=basePath %>tool/swtool.shtml";
	}
</script>
<link rel="stylesheet" type="text/css" href="<%=basePath %>static/css/common_right.css">
<img class="fwzs_rightLogo" src="<%=basePath %>static/images/right_logo.png"></img>
<img class="fwzs_wytw" onclick="wytw()" src="<%=basePath %>static/images/btn_wytw.png"></img>
<c:if test="${UserSession.userId!=null }">
	<ul class="fwzs_my_menu">
		<li><a href="<%=basePath %>member/${UserSession.userId}/asks.shtml">我的问题</a></li>
		<li><a href="<%=basePath %>member/${UserSession.userId}/answers.shtml">我的回答</a></li>
		<li><a href="<%=basePath %>member/${UserSession.userId}/collectAnswer.shtml">我的收藏</a></li>
		<li><a href="<%=basePath %>member/${UserSession.userId}/followQuestions.shtml">我关注的问题</a></li>
		<li><a href="<%=basePath %>member/invited.shtml">邀请我回答的问题</a></li>
	</ul>
</c:if>
<div class="fwzs_tool">
	<div class="fwzs_title"><span class="fwzs_title_txt">法律小工具</span><a class="fwzs_more" href="<%=basePath %>tool/index.shtml">查看全部&nbsp;<i class="am-icon-sm am-icon-angle-right"></i></a><br><span class="fwzs_title_en">LEGAL GADGETS</span></div>
	<ul>
		<li onclick="toolGS()"><img src="<%=basePath %>static/images/icon_gs.png"></img><span>工伤赔偿计算器</span></li>
		<li onclick="toolLD(0)"><img src="<%=basePath %>static/images/icon_lwht.png"></img><span>劳务合同赔偿计算器</span></li>
		
		<li onclick="toolSW()"><img src="<%=basePath %>static/images/icon_lwjf.png"></img><span>死亡赔偿计算器</span></li>
		<li class="fwzs_last" onclick="toolLD(1)"><img src="<%=basePath %>static/images/icon_lwjf.png"></img><span>劳动经济补偿金</span></li>
		
	</ul>
</div>
<c:if test="${!fn:contains(url,'recommend')}">
<div class="fwzs_tj">
	<div class="fwzs_title"><span class="fwzs_title_txt">推荐大牛</span><a class="fwzs_more" href="<%=basePath %>recommend/index.shtml">更多推荐&nbsp;<i class="am-icon-sm am-icon-angle-right"></i></a><br><span class="fwzs_title_en">RECOMEND</span></div>
	<ul id="fwzs_recommend_users">
	</ul>
</div>
</c:if>
