$(function(){
	$("#fwzs_question_ul").on("click",".fwzs_show_all",function(){
		var showId = $(this).attr("showId");
		var flag = $(this).attr("flag");
		if(flag == "show"){//显示全部
			$("#show_"+showId).hide();
			$("#hide_"+showId).show();
		}else if(flag == "hide"){//收起
			$("#show_"+showId).show();
			$("#hide_"+showId).hide();
		}
	});
});