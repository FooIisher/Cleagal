<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="url" value="<%=request.getServletPath()%>"></c:set>
<link rel="stylesheet" type="text/css" href="<%=basePath %>static/css/common_top.css">
<script type="text/javascript" charset="utf-8" src="<%=basePath %>ueditor1_4_3_2/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=basePath %>ueditor1_4_3_2/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="<%=basePath %>ueditor1_4_3_2/lang/zh-cn/zh-cn.js"></script>
<script src="<%=basePath %>amazeui/js/amazeui.min.js"></script>
 
<style>
.loginbtn{
	display: table-cell !important;
padding: 0 5px;
height: 44px;
-webkit-box-sizing: border-box !important;
box-sizing: border-box !important;
font-size: 1.6rem;
line-height: 44px;
text-align: center;
color: #0e90d2;
display: block;
word-wrap: normal;
text-overflow: ellipsis;
white-space: nowrap;
overflow: hidden;
cursor: pointer;
border-right: 1px solid #dedede;

}
</style>
<script type="text/javascript">
	var isLogin = false;
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		url : basePath+"login/isLogin.shtml", 
		data : "",
		success : function(data) {
	  		 if(data.codenum == 200){
	  			isLogin = true;
	  		 }else{
	  			isLogin = false	;
	  		 }
		}
	});
   $(function(){
   		$("#searchBtn").click(function(){
   			window.location.href = basePath+"search.shtml?type=question&q="+encodeURI($("#searchInput").val());
   		});
   		$("#searchMore").click(function(){
   			window.location.href = basePath+"search.shtml?type=question&q="+encodeURI($("#searchInput").val());
   		});
   		$("#loginout").click(function(){
   			$.ajax({
   				async : false,
   				cache : false,
   				type : 'POST',
   				url : basePath+"login/loginout.shtml", 
   				data : "",
   				success : function(data) {
   			  		 if(data.codenum == 200){
   			  			 location.href=basePath;
   			  		 }
   				}
   			});
   		});
   		var firstPub = true;
   		var ue = UE.getEditor('questionEditor');
   		//添加获得焦点监听
   		ue.addListener('focus',function(){
   			if(firstPub && ue.getContent()=='<p><span style="color:#aaa">问题背景、条件详细信息（描述的越精准越容易让相关专业人士了解到您的问题，并进行详细的解答哟）</span></p>'){
   				ue.setContent("");
   				firstPub = false;
   			}
	    });
		var showFlag = false;
		$("#searchUl").hover(function(){
				showFlag = true;
           },function(){
        	  	showFlag = false;
  		});
		
		 $("#searchInput").keyup(function() {
			 searchFn();
		 });
		 $("#searchInput").blur(function(){
			 if(!showFlag){
				 $(".simpleSearch").hide();
			 }
		 });
		 $("#searchInput").focus(function(){
			 searchFn();
		 });
   });
   function searchOpen(url){
	   window.open(url);
	   $(".simpleSearch").hide();
   }
   function searchFn(){
	   if($("#searchInput").val() == ''){
	 		$(".simpleSearch").hide();
	 		return;
	 	}
	 	
	 	$.ajax({
				async : false,
				cache : false,
				type : 'POST',
				url : basePath+"search/autoCompleteSearch.shtml?q="+encodeURI($("#searchInput").val()), 
				data : "",
				success : function(data) {
			  		$(".simpleSearch").html(data); 
			  		$(".simpleSearch").show(); 
				}
		});
   }
   
	function pubQuestion(){
	   	if($("#questionTitle").val()==""){
	   		alert("请输入您的问题");
	   		return;
	   	}
	   	if($("#fwzs_wytw_topic2").val()==null || $("#fwzs_wytw_topic2").val()==""){
	   		alert("请选择问题标签");
	   		return;
	   	}
	   	if(UE.getEditor('questionEditor').getContent()=='<p><span style="color:#aaa">问题背景、条件详细信息（描述的越精准越容易让相关专业人士了解到您的问题，并进行详细的解答哟）</span></p>'){
	   		UE.getEditor('questionEditor').setContent("");
	   	}
	   	$("#saveQuestionForm").submit();
	}
	function wytw(){	
		if(isLogin){
			$('#my-popup').modal('open');
		}else{
			loginDialog();
		}
	}
	function changeTopic(){
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
	}
	function loginDialog(){
 		 $('#loginModel').modal({
	      relatedTarget: this,
	      onConfirm: function(e) {
	      	 
				
	      },
	      onCancel: function(e) {
	        
	      }
	    });
	}
	function toLogin(){
		
				var formData = new Object();
				var data=$("#loginModel input").each(function() {
					formData[this.name] =$("#"+this.name ).val();
				});
				if(formData['mobile']==''|| formData['password']==''){
					alert('手机号和密码不能为空!');
					return;
				}
			//	if(formData['validCode']==''){
			//		alert('验证码不能为空!');
			//		return;
			//	}
				$.ajax({
					async : false,
					cache : false,
					type : 'POST',
					url : "<%=basePath%>login/checkUsers.shtml",// 请求的action路径
					data : formData,
					error : function() {// 请求失败处理函数
						alert("error");
					},
					success : function(data) {
				  		if (data.success) {
							 self.location.reload(); 
						} else {
						
							if(data.msg == '3'){
								$("#validCodeInput").show();
								alert("用户名或密码错误!");
								$(".code_img").attr("src",basePath+"ImageServlet?r="+new Date().getTime()); 
							}else if(data.msg == '201'){
								alert("验证码不能为空!");
							}else if(data.msg == '202'){
								alert("验证码输入错误!");
								$(".code_img").attr("src",basePath+"ImageServlet?r="+new Date().getTime()); 
							}else{
								alert("用户名或密码错误!"); 
								$(".code_img").attr("src",basePath+"ImageServlet?r="+new Date().getTime()); 
							}
						}
					}
				});
	
	}
</script>
<!-- 登录后开始请求通知信息 -->
<c:if test="${UserSession.userId!=null }">
<script>
 $(function(){
   		//注册聊天工具
	 	$(document).FnWebIM({
				username : '${UserSession.userId}', // 用户名称
				pass : "${UserSession.truePassword}", // 用户密码
				nickName : '${UserSession.userName}', // 昵称
				imgUrl:basePath+'${UserSession.avatarFile}', 
				autoLogin : false, //boolean型，默认是否自动登录，true：自动登录，false：手动登录，默认为true
				msgRefreshTime : 1000, //number型，消息刷新时间，单位为ms
				friendRefreshTime : 10000, //number型，好友刷新时间，单位为ms
				showSecretary : false, //boolean型，默认是否显示小秘书，true：显示，false：不显示，默认为true
				sendPicture : true, //boolean型，是否允许发送图片，true：允许，false：不允许，默认为true
				msgMaxSize : 300, //number型，单条消息最大允许字符
				msgSound : true, //boolean型，是否开启声音提醒，true：开启，false:关闭，默认为true
				contextpath:'<%=serverPath%>'+"/legal_chat" 
		});	
   });

setTimeout(initNotreadNotify,5000);
function initNotreadNotify(){
		$.ajax({
			async : false,
			cache : false,
			type : 'POST',
			url : basePath+"member/notReadNotify.shtml", 
			data : "",
			error : function(msg) {//  
				setTimeout(initNotreadNotify,10000);
			},
			success : function(data) {
		  		 if(data.codenum == 200){
		  		 	if(data.data == 0){
		  		 		$(".msgCount").css("display","none");
		  		 	}else{
		  		 		$(".msgCount").css("display","inline-block");
		  		 		$(".msgCount").html(data.data);
		  		 	}
		  		 }
		  		 setTimeout(initNotreadNotify,10000); 
			}
		});

}  
</script> 

</c:if>
<div class="fwzs_top">
	<div class="fwzs_top_left">
	  <img src="<%=basePath %>static/images/logo.png" class="logo"></img>
	  <div class="am-u-lg-7">
	    <div class="am-input-group am-input-group-danger">
	      <input type="text" id = "searchInput" value="${q}" class="am-form-field" placeholder="搜索问题或昵称">
	      <ul class="am-dropdown-content simpleSearch" style="width: 450px;border:2px solid red;" id="searchUl">
		    	
		  </ul>
	      <span class="am-input-group-label fwzs_span" id ="searchBtn">搜索</span><span class="am-input-group-label fwzs_line">|</span>
	      <span class="am-input-group-label fwzs_span" onclick="wytw()">提问</span>
	    </div>
	  </div>
	</div>
	<div class="fwzs_top_right">
		<c:if test="${UserSession.userId==null }">
		<div class="fwzs_top_right_login">
			<button type="button" onclick="javascript:location.href='<%=basePath %>login.shtml'" class="am-btn am-btn-default fwzs_span">登&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;录</button>
			<button type="button" onclick="javascript:location.href='<%=basePath %>register.shtml'" class="am-btn am-btn-default fwzs_span">注&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;册</button>
		</div>
		</c:if>
		<c:if test="${UserSession.userId!=null }">
		<div class="fwzs_top_right_loginInfo">
			<img style="cursor: pointer;" onclick="javascript:location.href='<%=basePath %>member/${UserSession.userId}.shtml'" src="<%=basePath %>${ynkj:parseHeadImg(UserSession.avatarFile)}" class="am-img-thumbnail fwzs_headImg">
			<div class="am-dropdown" data-am-dropdown>
			  <div class="am-dropdown-toggle" style="margin: 0 20px;cursor: pointer;" data-am-dropdown-toggle>${UserSession.userName} <span class="am-icon-caret-down" style="padding: 0"></span></div>
			  <ul class="am-dropdown-content" style="min-width: 90px">
			    <li><a href="<%=basePath %>member/${UserSession.userId}.shtml">我的主页</a></li>
			    <li><a href="<%=basePath %>member/feedback.shtml">问题反馈</a></li>
			    <li><a href="<%=basePath %>member/password.shtml">我的设置</a></li>
			    <li><a href="#">聊天消息</a></li>
			    <li><a id="loginout" href="javascript:;">退出</a></li>
			  </ul>
			</div>
			<img onclick="javascript:location.href='<%=basePath %>member/notifications.shtml'" style="cursor: pointer;" src="<%=basePath %>static/images/icon_msg.png"></img>
			<span class="msgCount" ></span>
		</div>
		</c:if>
	</div>
</div>

<div class="fwzs_daohang_full">
	<div class="fwzs_daohang">
	<ul class="fwzs_top_left am-nav-pills am-nav-justify">
	  <li <c:if test="${fn:contains(url,'/homepage/')}">class="fwzs_active"</c:if> ><a href="<%=basePath %>homepage/index.shtml">首页</a></li>
	  <li <c:if test="${fn:contains(url,'/case/')}">class="fwzs_active"</c:if>><a href="<%=basePath %>case/index.shtml">案例</a></li>
	  <li <c:if test="${fn:contains(url,'/recommend/')}">class="fwzs_active"</c:if>><a href="<%=basePath %>recommend/index.shtml">推荐</a></li>
	  <li <c:if test="${fn:contains(url,'/tool/')}">class="fwzs_active"</c:if>><a href="<%=basePath %>tool/index.shtml">工具</a></li>
	  <li <c:if test="${fn:contains(url,'/mobile/')}">class="fwzs_active"</c:if>><a href="<%=basePath %>login2.shtml">测试接口</a><>
	  <li <c:if test="${fn:contains(url,'/fish/')}">class="fwzs_active"</c:if>><a href="<%=basePath %>fish/index.shtml">测试功能</a><>
	  
	</ul>
	</div>
</div>


<div class="am-modal am-modal-prompt" tabindex="-1" id="loginModel">
  <div class="am-modal-dialog">
    <div class="am-modal-hd">用户登录</div>
    <div >
    	<div class="am-form-group">
     	 <input  type="text"  name="mobile" id="mobile" style="width: 280px"  class="am-form-field login_input" placeholder="您的手机号">
     	</div>
     	<div class="am-form-group">
      	 <input type="password"  name="password" style="width: 280px" id="password"  class="am-form-field login_input" placeholder="密码">
     	</div>
     	<div class="am-form-group" 
     		 <c:if test="${validCodeKey==null || validCodeKey<3}">
  			 	 style="display:none" 
  			 </c:if> id="validCodeInput"
     	>
        	<img src="<%=basePath %>ImageServlet" class="code_img"  alt="会员登录验证码" 
       	 style="width:80px;height:35px;cursor: pointer;margin-left: 0px;">	
      
      		  <input  value="" name="validCode" id="validCode"  type="text"  class="am-form-field login_input" placeholder="验证码">
   		   </div>
    </div>
    <div class="am-modal-bd">
      › 如果您没有登录帐号，请先<a style="color: red" href="javascript:window.open('<%=basePath%>register.shtml');">注册会员</a>然后登录
    </div>
    <div class="am-modal-footer">
      <span class="am-modal-btn" data-am-modal-cancel>取消</span>
      <span class="loginbtn" onClick="toLogin()">登录-fish</span>
    </div>
  </div>
</div>
  <script>
	$(".code_img").click(function(){
		$(".code_img").attr("src",basePath+"ImageServlet?r="+new Date().getTime());
	});
 </script>
<div class="am-popup" id="my-popup" style="border: 4px solid RGB(65,106,170)">
  <div class="am-popup-inner">
    <div class="am-popup-hd" style="background-color: RGB(131,166,222)">
      <span style="color: white;font-size: 15px;padding-left: 15px;line-height: 40px">提问</span>
      <span data-am-modal-close class="am-close" style="color: white;">&times;</span>
    </div>
    <div class="am-popup-bd" style="background: none">
      <form id="saveQuestionForm" action="<%=basePath %>question/saveQuestion.shtml" method="post">
      	<textarea id="questionTitle" name="questionTitle" style="width: 100%;height: 60px;resize:none" placeholder="写下您的问题（如内容过多需要排版，可在下方问题补充进行详细描述）"></textarea>
      	<div style="margin: 30px 0 10px 0">问题补充（选填）</div>
      	<textarea name="questionContent" id="questionEditor" style="width:100%;height:140px;"><span style='color:#aaa'>问题背景、条件详细信息（描述的越精准越容易让相关专业人士了解到您的问题，并进行详细的解答哟）</span></textarea>
		<div style="margin: 30px 0 10px 0">问题标签（选填）</div>
		<div class="fwzs_wytw_topic">
			<div style="float: left">
			<select id="fwzs_wytw_topic1" data-am-selected onchange="changeTopic()">
			</select>
			</div>
			<div style="float: left;margin-left: 10px">
			<select name="topicIds" id="fwzs_wytw_topic2" multiple data-am-selected>
			  
			</select>
			</div>
		</div>
		<input name="anonymous" value="1" style="margin-right: 5px;margin-top: 10px;" type="checkbox">匿名
		<span type="button" style="float: right;border-radius:0;width: auto;font-size: 12px;margin-top: 10px;" onclick="pubQuestion()" type="button" class="am-btn am-btn-secondary">提问</span>
      </form>
    </div>
  </div>
</div>
