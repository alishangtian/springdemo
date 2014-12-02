$(function(){
	$.ajax({
		url:"sm/mainMenuAction!getTopMenu.action",
		type:'post',
		async:false,
		success:function(datasource){
			var data = $.parseJSON(datasource);
			//加载头部菜单
			$(".topmenu").topmenubar({
				data : data,
				submenuclick: loadLeftMenu
			});
		},
		error: function(e){
			alert(e);
		}
	});
});
var msgList = null;
$(function(){
	//退出链接
	$("#logOutLink").click(function(event){
		event.preventDefault();
		var self = this;
		$.dtmessagebox.confirm("是否退出？","提示",function(msgBtn){
			if (msgBtn==$.dtdialog.DR_OK) window.location.href = $(self).attr("href");
			else return false;
		});
	});
	//修改密码
	$("#modifyPasswordLink").click(function(event){
		event.preventDefault();
		$(".littleF .selected").removeClass("selected");
		$(this).addClass("selected");
		addTab({
			"menuUrl": $(this).attr("href"),
			"menuName": $(this).text()
		});
	});
	//主页
	$("#mainPageLink").click(function(event){
		event.preventDefault();
		$(".littleF .selected").removeClass("selected");
		$(this).addClass("selected");
		selectTab(0);
	});
	$("#msgDownDiv").mouseover(function(){
		msgMouseOver();
	});
	$("#msgDownDiv").mouseout(function(){
		msgMouseOut();
	});
	$("#msgDownLi").mouseover(function(){
		msgMouseOver();
	});
	$("#msgDownLi").mouseout(function(){
		msgMouseOut();
	});
	//消息
	$.post("sm/sysMsg!getAllSysMsg.action", 
			{}, 
			function(data) {
			if (data.resultFlag) {
				msgList = data.sysMsgList;
				if(msgList!=null && msgList.length>0){
					for(var i=0; i<msgList.length; i++){
						var liArray = new Array();
						if(i==msgList.length-1){
							liArray.push("<li style='display:block;padding-top: 4px;padding-left: 10px;padding-bottom: 4px;'>");
						}else{
							liArray.push("<li style='display:block;padding-top: 4px;padding-left: 10px;padding-bottom: 4px;border-bottom: 1px dotted red;'>");
						}
						liArray.push("<a href='javascript:void(0);' onclick='msgClickDialog(");
						liArray.push("\""+msgList[i].dialogUrl+"\",");
						liArray.push("\""+msgList[i].dialogTitle+"\",");
						liArray.push(msgList[i].dialogWidth+",");
						liArray.push(msgList[i].dialogHeight+")'>");
						liArray.push(msgList[i].msgName);
						liArray.push("<span id='msgCount-"+msgList[i].sysMsgId+"' style='padding-left: 10px;'>(0)</span>");
						liArray.push("</a>");
						liArray.push("</li>");
						$("#msgDownUl").append(liArray.join(""));
					}
				}
			}
		});
	setInterval(monitorCountMsg,5*50*1000);
});
/**
 * 消息总数
 */
function monitorCountMsg(){
	var count = 0;
	//发生请求统计消息总数
	if(msgList!=null && msgList.length>0){
		for(var i=0; i<msgList.length; i++){
			var msgId = msgList[i].sysMsgId;
			$.ajax({async:false,
					url:msgList[i].msgCountUrl, 
					success:function(data) {
						if (data.resultFlag) {
							$("#msgCount-"+msgId).html("("+data.count+")");
							count += data.count;
						}
					}
			});
		}
	}
	//更改页面消息总数
	$("#msgCount").html('('+count+')');
	if(count==0){
		$("#msg_image_span").css("background-image","url(pages/main/image/no_msg.gif)");
	}else{
		$("#msg_image_span").css("background-image","url(pages/main/image/msg.gif)");
	}
}
/**
 * 鼠标进入消息区域
 */
function msgMouseOver(){
	$("#msgDownDiv").show();
//	$("#msg_span").css("border","1px dotted red");
	$("#msg_span").css("z-index","2000");
	$("#msgDownDiv").css("border","1px dotted red");
}
/**
 * 鼠标离开消息区域
 */
function msgMouseOut(){
	$("#msgDownDiv").hide();
//	$("#msg_span").css("border","0px");
	$("#msgDownDiv").css("border","0px");
}
/**
 * 查看信息
 */
function msgClickDialog(url,title,width,height){
	viewMsgDialog = dt$.showModalDialog({title: title,
	width:width,
	height:height,
	url:  url,
	buttons:{
		'关闭' : function() {
			viewMsgDialog.close();
		}
	}
	});
}
/**
 * 加载左边菜单
 */
function loadLeftMenu(){
	var menuItem = this;
	var menuData = menuItem.data("data");
	var menuId = menuData.menuId;
	initLeftMenu({"menuId": menuId, "subMenu": menuData.subMenu});
}
