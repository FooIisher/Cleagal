 

function praiseAnswer(answerId,callback){
		$.ajax({
			async : false,
			cache : false,
			type : 'POST',
			url : basePath+"answer/praiseAnswer.shtml",//  
			data : "answerId="+answerId,
			error : function(msg) {//  
				if(msg.status ==504){
					alert("请登录");
				}
			},
			success : function(data) {
		  		if (data.success) {
					callback('true',answerId);
				}else{
					callback('false',answerId);
				}
			}
		});
}

function disPraiseAnswer(answerId,callback){
		$.ajax({
			async : false,
			cache : false,
			type : 'POST',
			url : basePath+"answer/disPraiseAnswer.shtml",//  
			data : "answerId="+answerId,
			error : function(msg) {//  
				if(msg.status ==504){
					alert("请登录");
				}
			},
			success : function(data) {
		  		if (data.success) {
					callback('true',answerId);
				}else{
					callback('false',answerId);
				}
			}
		});
}
function followAnswer(answerId,callback){
		$.ajax({
			async : false,
			cache : false,
			type : 'POST',
			url : basePath+"answer/followAnswer.shtml",//  
			data : "answerId="+answerId,
			error : function(msg) {//  
				if(msg.status ==504){
					alert("请登录");
				}
			},
			success : function(data) {
					callback(data,answerId);
			}
		});
}

function disFollowAnswer(answerId,callback){
		$.ajax({
			async : false,
			cache : false,
			type : 'POST',
			url : basePath+"answer/disFollowAnswer.shtml",//  
			data : "answerId="+answerId,
			error : function(msg) {//  
				if(msg.status ==504){
					alert("请登录");
				}
			},
			success : function(data) {
					callback(data,answerId);
			}
		});
}