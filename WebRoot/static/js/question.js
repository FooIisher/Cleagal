 
function followQuestion(questionId,callback){
		$.ajax({
			async : false,
			cache : false,
			type : 'POST',
			url : basePath+"question/followQuestion.shtml",//  
			data : "questionId="+questionId,
			error : function(msg) {//  
				if(msg.status ==504){
					alert("请登录");
				}
			},
			success : function(data) {
		  		if (data.success) {
					callback('true',questionId);
				}else{
					callback('false',questionId);
				}
			}
		});
}
function disFollowQuestion(questionId,callback){
		$.ajax({
			async : false,
			cache : false,
			type : 'POST',
			url : basePath+"question/disFollowQuestion.shtml",//   
			data : "questionId="+questionId,
			error : function(msg) {//  
				if(msg.status ==504){
					alert("请登录");
				}
			},
			success : function(data) {
		  		if (data.success) {
					callback('true',questionId);
				} else {
					callback('false',questionId);
				}
			}
		});
}

 