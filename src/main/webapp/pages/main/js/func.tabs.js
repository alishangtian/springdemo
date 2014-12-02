/**
 * 初始化左边模式的tab页
 * @param item
 */
function onTabItemChange(item){
	$(".left-menu-tabs>.selected").removeClass("selected");
	item.addClass("selected");
	var tabContentIndex = item.attr("tabContentIndex");
	$(".left-menu-tabs-contents>[tabContentIndex]").hide();
	var content = $(".left-menu-tabs-contents>div[tabContentIndex='" + tabContentIndex + "']");
	content.show();
	if(content.data("load") != true){
		if(content.attr("onSelected")){
			if($.isFunction(content.attr("onSelected"))){
				content.attr("onSelected").call(this);
	    	}else if(typeof content.attr("onSelected") === "string"){
	    		new Function("return " + content.attr("onSelected") + "();").call(this);
	    	}
		}
		content.data("load", true);
	}
	var selectIcon = $(".left-menu-tabs>.selected-icon");
	selectIcon.show();
	selectIcon.offset({
		left: item.offset().left + item.width(),
		top: item.offset().top
	});
}
$(function(){
	onTabItemChange($(".left-menu-tabs>[tabContentIndex='1']"));
	$(".left-menu-tabs>.tabs-item").click(function(){
		onTabItemChange($(this));
	});
});