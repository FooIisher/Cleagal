function viewCaseDetail(caseId){
	window.location.href = "";
}
function followCase(caseId,callback){
		$.ajax({
			async : false,
			cache : false,
			type : 'POST',
			url : basePath+"case/followCase.shtml",//  
			data : "caseId="+caseId,
			error : function(msg) {//  
				if(msg.status ==504){
					alert("请登录");
				}
			},
			success : function(data) {
		  		if (data.success) {
					callback('true',caseId);
				}else{
					callback('false',caseId);
				}
			}
		});
}
function disFollowCase(caseId,callback){
		$.ajax({
			async : false,
			cache : false,
			type : 'POST',
			url : basePath+"case/disFollowCase.shtml",//  
			data : "caseId="+caseId,
			error : function(msg) {//  
				if(msg.status ==504){
					alert("请登录");
				}
			},
			success : function(data) {
		  		if (data.success) {
					callback('true',caseId);
				} else {
					callback('false',caseId);
				}
			}
		});
}