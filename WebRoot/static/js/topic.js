$(function(){
	
	 $('.fwzs_tag_li > .am-dropdown').hover(function(){
		$("#"+this.id+"_ul").addClass("fwzs_dropdown_active");
		
	 },function(){
		if(!$("#"+this.id+"_ul").is(":focus")){
			$("#"+this.id+"_ul").removeClass("fwzs_dropdown_active");
		}
	 });
	 
	 $('.fwzs_tag_li > .am-dropdown-content').hover(function(){
		$(this).addClass("fwzs_dropdown_active");
	 },function(){
		$(this).removeClass("fwzs_dropdown_active");
	 });
	 $(".fwzs_tag_li > button").click(function(){
		 location.href=$(this).attr("href");
	 });
});