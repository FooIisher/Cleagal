<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<%@include file="/pages/common/mytags.jsp" %>
<%@include file="/pages/common/commonjs_css.jsp" %>
<html>
  <head>
    <title>用户列表-fish</title>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>static/css/recommend.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>static/css/topic.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>static/js/jquery/jquery.js"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>static/js/jquery/jquery.min.js"/>
   
    <script src="<%=basePath %>static/js/topic.js"></script>
  </head>
  
  <script type="text/javascript">
  $(function(){
	  
	  $("#fwzs_recommend_ul").on("click",".fwzs_recommend_left",function(){
	    	location.href="<%=basePath %>member/"+$(this).attr('userId')+".shtml";
	    });
	  
	  
	  $('#fwzs_loading').on('inview.scrollspy.amui', function() {
			loadMore();
		});
		$('#fwzs_loading').scrollspy({
		   animation: 'slide-left',
		   delay: 0
		});
		
		$('#fwzs_loading').click(function(){
			loadMore();
		});

  });
  
  var pageId = 1;
  var loading = false;
 
  function loadMore(){
	  $('#fwzs_loading').button('loading');
		pageId++;
		if(!loading){ 
			loading = true;
			$.ajax({
	        type: "post",
	        url: basePath+"fish/ajaxQuaryUserIndex.shtml",
	        data: "typeId=${typeId}&pageId="+pageId,
	        dataType: "html",
	        success: function(data){
	        	$("#fwzs_userlist").append(data);
	        	$('#fwzs_loading').button('reset');
	        	loading = false;
	            }
	        });
      }
  }
	  

  

 
  </script>
   
  <body>
  	<%@include file="/pages/common/common_top.jsp" %>
  	<div class="fwzs_content">
  		<div class="fwzs_content_left">	
  		    <div class="fwzs_tag_li">
	  			<button id="All" href="<%=basePath %>fish/index.shtml" type="button" class="am-btn am-btn-default">全部</button>
	  			
	  			
	  			<!--
	  			<button id="Certify" href="<%=basePath %>fish/certify=1.shtml" type="button" class="am-btn am-btn-default ">等待认证</button>	
	  			<button id="Certify" href="<%=basePath %>fish/certify=2.shtml" type="button" class="am-btn am-btn-default ">认证通过</button>
	  			<button id="Certify" href="<%=basePath %>fish/certify=3.shtml" type="button" class="am-btn am-btn-default ">认证失败</button>
	  			<button id="Certify" href="<%=basePath %>fish/certify=0.shtml" type="button" class="am-btn am-btn-default ">未认证</button>
	  			-->
	  			<c:forEach items="${typeList}" var="type" varStatus="sta">
	  				<div id="${type.typeId }" class="am-dropdown">
					  <button class="am-btn am-btn-default am-dropdown-toggle <c:if test="${typeId == type.typeId}">fwzs_tag_active</c:if> <c:if test="${sta.count==3 || sta.count==8 || sta.count==13}">fwzs_last_btn</c:if>">${type.typeName}</button>
					  <ul class="am-dropdown-content" id="${type.typeId }_ul">
					    <c:forEach items="${type.listTypes }" var="subtype">
					    	<li><a href="index${subtype.typeId }.shtml">${subtype.typeName}</a></li>
					    </c:forEach>
					  </ul>
					</div>
				</c:forEach>
			
				
	  		</div>
  		    <div class="fwzs_recommend_user" style = "vertical-align:middle;margin-top: 5px; margin-left:5px">
  		    	<span style = "float: right;font-size:25px;color:red">${listUsersNum}</span>
  		    	<span style = "margin-top: 10px;float: right;">用户数量为：</span>
  		    </div>
  		    	
	  		<ul class="fwzs_recommend_ul" id="fwzs_userlist">
	  			<c:forEach items="${listUsers}" var="user">
	  			<li>
	  			<div class="fwzs_recommend_left" style="display:inline" userId=${user.userId }>
	  				<img src="<%=basePath %>${ynkj:parseHeadImg(user.avatarFile)}" class="am-img-thumbnail fwzs_recommend_head" onclick="javascript:window.open('<%=basePath %>member/${user.userId}.shtml')">
	  				<c:if test="${user.certifyStatus==2 }">
	  				<img class="fwzs_icon_vv" src="<%=basePath %>static/images/icon_vv.png"></img>
	  				</c:if>
	  			</div>
	  			<div class="fwzs_recommend_right" style="display:inline" >
		  			<div style="margin-top: 5px;">用户姓名：${user.userName }</div>
		  			<div style="margin-top: 5px;">用户密码：${user.password }</div>
		  			<div style="margin-top: 5px;">
	  				<c:if test="${user.sex==-1}">
	  					用户性别：未知
	  				</c:if>
		  			<c:if test="${user.sex==0}">
		  				用户性别：男性
		  			</c:if>
		  			<c:if test="${user.sex==1}">
		  				用户性别：女性
		  			</c:if>
		  		    </div>
		  			<div style="margin-top: 5px;">注册时间：${user.regTime }</div>
		  			<div class="fwzs_recommend_intro">个人介绍：${user.individualResume }</div>
		  			<div class="fwzs_recommend_chat">
		  			<button type="button" class="am-btn am-btn-success am-radius index_chat"  onclick="javascript:window.open('<%=basePath %>member/${user.userId}.shtml')">查看详细信息</button>
	  	 			</div>
	  			</div>
	  			
	  			</li>
	  			</c:forEach>
	  		</ul>
	  		<div id="fwzs_loading" style="margin-top: 10px;text-align: center;padding: 5px;cursor: pointer;" class="am-btn-danger" data-am-loading="{spinner: 'circle-o-notch', loadingText: '努力加载中...', resetText: '更多'}"></div>
	  		<div class="title"><tyTag:pageSplit pageNo="${pager.pageId}" url="getSellOrder.shtml" count="${pager.rowCount}"/></div>
  		</div><div class="fwzs_content_right"><%@include file="/pages/common/common_right.jsp" %></div>	
  	</div>
  	<%@include file="/pages/common/common_bottom.jsp" %>			
  </body>
</html>
