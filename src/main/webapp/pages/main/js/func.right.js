/**
 * 增加页面
 * @param menuDetail
 */
function addTab(menuDetail){
	$("#newTabs").funcTabs("addTab", {
		label: menuDetail.menuName,
		url: menuDetail.menuUrl,
		useIframe: true,
		data: menuDetail,
		canClose: true
	})
}
/**
 * 选择指定的页面
 * @param index
 */
function selectTab(index){
	$("#newTabs").funcTabs("select", index);
}
$(function(){
	var tabButtons = [ {
		title : "添加快捷方式",
		classname : "add",
		click : function(selected, data) {
			createUserShortcut(data);
		}
	} ];
	$("#newTabs").funcTabs({
		id: "newTabs",
		maxTabs: 9,
		tabs: [
		       {
		    	   label: "主页",
		    	   url: "sm/welcomeAction.action",
		    	   useIframe: true
		       }
		       ],
		buttons: tabButtons
	});
	//隐藏头部菜单
	$(".up_icon").click(function(){
		$("#headcontent").slideUp("fast");
		$(".up_icon").hide();
		$(".down_icon").slideDown("fast");
	});
	//展示头部菜单
	$(".down_icon").click(function(){
		$(".down_icon").hide();
		$("#headcontent").slideDown("fast");
		$(".up_icon").show();
	});
});
/**
 * 添加快捷方式
 */
function createUserShortcut(data){
	if(data){
		var menuId = data.menuId;
		$.post("sm/mainMenuAction!doCreateUserShortcut.action", {
			"menuId" : menuId
		}, function(data) {
			if(data.result == true){
				$.dtmessagebox.alert("添加成功");
				initFavourMenu();//add by whc 08/31 for refresh favourmenu
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