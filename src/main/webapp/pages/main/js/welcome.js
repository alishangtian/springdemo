var precol;
function initLayOut(){
	$.ajax({
		url:"sm/welcomeAction!doGetLayOut.action",
		type:'post',
		async:false,
		success:function(datasource){
			var data = $.parseJSON(datasource);
			if(data==null||data.length==0){
				data=[{width:'49%',panel:[]},{width:'49%',panel:[]}];
			}
			precol=data.length;
			renderLayOut(data);
		},
		error: function(e){
			$.dtmessagebox.alert(e);
		}
	});
}
function renderLayOut(data){
	$("li", $(".wrap")).panel("destroy");
	$("ul", $(".wrap")).remove();
	$.each(data, function(i, prop){
		createUl(prop);
	});
}
function createUl(prop){
	var ul = $("<ul class='ul'>");
	ul.data("data", prop);
	ul.width(prop.width);
	ul.appendTo($(".wrap"));
	$.each(prop.panel, function(i, conf){
		var li = $("<li></li>");
		li.appendTo(ul);
		li.panel({
			"title": conf.title,
			"url": conf.url,
			"height": conf.height,
			"closeClick":closeClick
		});
		
		titleObjs.push(conf.title);//存放panel标题
		
	});
	ul.height(100);//为了拖动方便
}

var titleObjs=[];
function closeClick(title,obj){
	titleObjs.pop(title);//点关闭事件，删除panel标题
}

function switchLayout(layoutsource){  
	var layout=$.parseJSON(layoutsource),
        col=layout.length;
	if(col==precol){
		$.each($("ul", $(".wrap")),function(i,ul){
			$(ul).width(layout[i].width);
			$(ul).data("data",{"width":layout[i].width});
		});
	}else{
		var liObjs=$("li", $(".wrap")).clone(true),
	        lisize=liObjs.length;
	        row=lisize/col;
	    $("ul", $(".wrap")).unbind().removeClass().remove();
	    $.each(layout,function(i,item){
	    	var ul = $("<ul class='ul'>");
    		ul.data("data",{"width":layout[i].width});
    		ul.width(layout[i].width);
    		ul.appendTo($(".wrap"));
    		ul.height(100);//为了拖动方便
    	});
	    if(liObjs!=null&&lisize>0){
	    	var uls=$("ul", $(".wrap"));
	    	$.each(liObjs,function(i,li){
		    	$(li).appendTo(uls[i%col]);
		    });
	    }
	}
	precol=col;
	sortable();
}

function sortable(){
	$(".ul").sortable({
		connectWith: $(".ul")
	});
	$(".ul").sortable({
		sort: function(){
			if(!$(".operbar").is(":visible")){
				$(".operbar").show();
				$(".operbar").animate({
					top:0
				}, 1000);
			}
		},
	    stop:function(){
	    	$(this).height(100);
	    }
	});
}
function initEvent(){
	$(".save").click(function(){
		var newLayOut = [];
		$("ul").each(function(){
			var l = {};
			l.width = $(this).data("data").width;
			l.panel = [];
			newLayOut.push(l);
			$("li", $(this)).each(function(){
				var options = $(this).panel("getData");
				l.panel.push({
					"title": options.title,
					"url": options.url,
					"height": options.height ? options.height : ""
				});
			});
		});
		$.post("sm/welcomeAction!savePageSetting.action", {
			"layOut" : JSON.stringify(newLayOut)
		}, function(data) {
			doResult(data, "保存成功");
		});
	});
	$(".operbar-close").click(function(){
		$(".operbar").hide();
	});
	$(".add").click(function(){
		addFuncDialog.showModal();
	});
}
function initLayOutOption(){
	$.ajax({
		url:"sm/welcomeAction!doGetPageLayouts.action",
		type:'post',
		async:false,
		success:function(datasource){
			$.each(datasource, function(i, prop){
				var layOutIcon = $("<div class='layout-icon'><div>");
				layOutIcon.addClass(prop.thumbnailLocation);
				layOutIcon.data("data", prop);
				$("#layOutOption").append(layOutIcon);
				layOutIcon.click(function(){
					var layOut = $(this).data("data").setting;
//					renderLayOut(layOut);
					switchLayout(layOut);
				});
			});
		},
		error: function(e){
			$.dtmessagebox.alert(e);
		}
	});
}
/**
 * 处理后台返回的数据
 * @param data
 * @param success
 */
function doResult(data, message, success){
	if(data.result == true){
		$.dtmessagebox.alert(message, "提示", function(){
			if($.isFunction(success)){
				success();
			}
		});
	}else if(data.result == false){
		$.dtmessagebox.alert(data.msg);
	}else{
		$.dtmessagebox.alert(data);
	}
}
function initFuncDialog(){
	$("#componentLocation").html("");
	$(".ul").each(function(i){
		$("#componentLocation").append($("<option value='" + i + "'>第" + (i/1 + 1) + "列</option>"));
	});
}
function cancelClick(){
	$(this).dtdialog('close');
}
function confirmClick(){
	var componentData = pageComponentList.data(), location = $("#componentLocation").val();
	var isExist=false;
	$.each(titleObjs,function(i,title){
		   if(componentData.title==title){//若此组件以存在不允许添加
			   isExist=true;
			   return;
		   }else{
			   titleObjs.push(componentData.title);
		   }
	   });
	if(isExist){
		$.dtmessagebox.alert(componentData.title+"组件已存在");
		return;
	}
	var li = $("<li></li>");
	li.appendTo($(".ul:eq(" + location + ")"));
	li.panel({
		"title": componentData.title,
		"url": componentData.url
	});
	$(this).dtdialog('close');
}
$(function(){
	initLayOut();
	sortable();
	initEvent();
	initLayOutOption();
	$(".operbar").show();
	$(".operbar").animate({
		top:0
	}, 500);
});