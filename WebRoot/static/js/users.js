 
function followUser(userId,callback){
		$.ajax({
			async : false,
			cache : false,
			type : 'POST',
			url : basePath+"member/followUser.shtml",//  
			data : "userFollowedId="+userId,
			error : function(msg) {//  
				if(msg.status ==504){
					alert("请登录");
				}
			},
			success : function(data) {
		  		if (data.success) {
					callback('true',userId);
				}else{
					callback('false',userId);
				}
			}
		});
}
function disFollowUser(userId,callback){
		$.ajax({
			async : false,
			cache : false,
			type : 'POST',
			url :  basePath+"member/disFollowUser.shtml",//    
			data : "userFollowedId="+userId,
			error : function(msg) {//  
				if(msg.status ==504){
					alert("请登录");
				}
			},
			success : function(data) {
		  		if (data.success) {
					callback('true',userId);
				} else {
					callback('false',userId);
				}
			}
		});
}

 