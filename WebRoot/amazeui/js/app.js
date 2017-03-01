(function($) {
  'use strict';

  $(function() {
    var $fullText = $('.admin-fullText');
    $('#admin-fullscreen').on('click', function() {
      $.AMUI.fullscreen.toggle();
    });

    $(document).on($.AMUI.fullscreen.raw.fullscreenchange, function() {
      $fullText.text($.AMUI.fullscreen.isFullscreen ? '退出全屏' : '开启全屏');
    });
  });
})(jQuery);

/**
 * 通过索引改变顶部系统菜单
 * @param index
 */
function changeSystem(index){
	$("li[name='system']").each(function(i,item){
		if(i==index){
			$(item).addClass("am-active");
			$("#system"+i).show();
		}else{
			$(item).removeClass("am-active");
			$("#system"+i).hide();
		}
	});
}

function closeDropdown(id){
	$("#"+id).dropdown('close');
}
