var leftMenuTree;
/**
 * 加载左边菜单
 * @param param
 */
function initLeftMenu(param){
	if(!param) param = {};
	if(param.subMenu){
		var setting= {
				nameCol:"menuName",
				nodesCol: "subMenu",
				root: {},
				callback: {
					click : onMenuSelect
				}
			};
		leftMenuTree = $("#menuTree").leftmenubar(setting); //dt$.tree("#menuTree", setting, param.subMenu);
		$("#menuTree").leftmenubar("load", param.subMenu);
	}else{
		var setting= {
				data:{
					key:{
						name : "deptName",
						children: "subMenu"
					}
				},
				callback: {
					click : onMenuSelect
			}};
		$.ajax({
			url:"sm/mainMenuAction!getSubMenu.action",
			type:'post',
			async:false,
			success:function(data){
				//leftMenuTree = dt$.tree("#menuTree", setting, $.parseJSON(data));
				setting.nodes = $.parseJSON(data);
				leftMenuTree = $('#menuTree').dttree(setting);
			},
			error: function(e){
				$.dtmessagebox.alert(e);
			},
			data: {"menuId": param.menuId}
		});
	}
	$("#menuListBtn").trigger("click");
}
/**
 * 选择菜单时，打开右边工作区域
 */
function onMenuSelect(){
	var menuData = arguments[2];
	if(menuData.menuUrl){
		addTab(menuData);
	}
}
/**
 * 初始化快捷方式
 * @param param
 */
function initFavourMenu(param){
	if(!param) param = {};
	$.post("sm/mainMenuAction!getFavourMenu.action", param, function(data){
		$("#favourMenu").leftmenubar({
			nameCol:"menuName",
			nodesCol: "subMenu",
			root: {},
			leafClass: "listleaf",
			buttons: [{
				classname: "delete",
				title: "删除快捷方式",
				click: function(button, data){
					var self = this;
					removeUserShortcut(data, function(){
						$(self).remove();
					});
				}
			}],
			callback: {
				click : function(){
					var prop = arguments[2];
					if(prop.menuUrl){
						addTab(prop);
					}
					return false;
				}
			}
		});
		$("#favourMenu").leftmenubar("load", $.parseJSON(data));
	});
}
/**
 * 初始化左边控件相关事件
 */
function initLeftIcon(){
	//隐藏左边菜单
	$(".left_icon").click(function(){
//		$(".hidelist").show("fast");
//		$(".hideLeftIcon").height($(".left-menu-container").height());//重新计算隐藏后的高度，使高度对齐
		$("#leftcontent").hide("fast");
		$("#leftDivId").css("height","0");
		$(".left_icon").hide();
		$(".min_left_nav").show();
//		$(".left-menu-container>.right").data("oldMarginLeft", $(".left-menu-container>.right").css("marginLeft"));//保存右边工作区域偏移量，供还原使用
//		var marginLeft = $(".hideLeftIcon").width();
//		$(".left-menu-container>.right").animate({
//			"marginLeft": marginLeft
//		});
		
		$("#rightDivId").css("margin-left","10px");
		$("#rightDivId").css("width","1240px");
		
	});
	//展示左边菜单
	$(".right_icon").click(function(){
		$(".min_left_nav").hide();
		$("#leftcontent").show("fast");
		$("#leftDivId").css("height","auto");
		$(".left_icon").show();
//		$(".left-menu-container>.right").animate({
//			"marginLeft": $(".left-menu-container>.right").data("oldMarginLeft")
//		});
//		$(".hidelist").hide();
//		$(".list").show("fast");
		
		$("#rightDivId").css("margin-left","0");
		$("#rightDivId").css("width","1010px");
		
	});
	//展示菜单
	$("#menuListBtn").click(function(){
		$("#menuTitle").html($(this).attr("title"));
		$("#favourMenu").hide("fast");
		$("#menuTree").show("fast");
	});
	//展示快捷方式
	$("#menuShortCutBtn").click(function(){
		$("#menuTitle").html($(this).attr("title"));
		$("#menuTree").hide("fast");
		$("#favourMenu").show("fast");
	});
}
/**
 * 删除快捷方式
 */
function removeUserShortcut(data, callback){
	if(data){
		var menuId = data.menuId;
		$.post("sm/mainMenuAction!removeUserShortcut.action", {
			"menuId" : menuId
		}, function(data) {
			if(data.result == true){
				if($.isFunction(callback)){
					callback();
				}
				$.dtmessagebox.alert("删除成功");
			}else if(data.result == false){
				$.dtmessagebox.alert(data.msg);
			}else{
				$.dtmessagebox.alert(data);
			}
		});
	}else{
		$.dtmessagebox.alert("指定页面没有可用的链接");
	}
}
$(function(){
	initFavourMenu();
	initLeftIcon();
});
