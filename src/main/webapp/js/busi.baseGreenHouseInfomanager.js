$(function() {
	$("#queryBaseGreenHouseInfo").click(function() {
		baseGreenHouseInfoResult.dtpaginggrid("setGridParam",{url:'busi/baseGreenHouseInfoAction!getBaseGreenHouseInfoquery.action?'+$('#queryFormH').serialize()});
		baseGreenHouseInfoResult.dtpaginggrid("option","page", 1);
		baseGreenHouseInfoResult.dtpaginggrid("reload");
	});

	/**
	 * 新增
	 */ 
	$("#addBaseGreenHouseInfo").click(function() {
		window.open('busi/baseGreenHouseInfoAction!addBaseGreenHouseInfo.action?baseId='+$('#baseId').val(),'newwindow','height=600px,width=900px,top=100px,left=150px,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no,toolbar=no');	
	});
	/**
	 * 返回
	 */
	$("#cancelBtn").click(function() {
		window.location.href="baseGreenHouseInfoAction.action";
		$("#greenHouse").css("display", "block");
		$("#base").css("display", "none");
	});
	/**
	 * 重置
	 */
	$("#resetBaseGreenHouseInfo").click(function() {
		$("#nameH").val("");
		funcInfoTree.dttree("refresh");
		$("#name").val("");
		$("#baseId").val("");
		$("#crops").val("");
	});
	/**
	 * 插入 采摘 确定
	 */
	$("#saveBaseGreenHouseInfo").click(function() {
	/*	var jd = $("#gpsJD").val();
		var wd = $("#gpsWD").val();
		$("#gps").val(jd+","+wd);*/
		var base_id = $("#baseId").val();
//		if(""==base_id){
//			alert("请选择基地！");
//			return false;
//		}
		$("#addBaseGreenHouseInfoForm").submit();	
		
		alert("添加成功");
		
		window.opener.baseGreenHouseInfoResult.dtpaginggrid("reload");
		window.opener.baseGreenHouseInfoResult.dtpaginggrid("reload");
		
		window.close();
		
	});
	
	$("#delPickWorksInfo").click(function() {

		deleteDomainInfo();
		
		});
	
	
	
});

/**
 * 删除值域
 */
function deleteDomainInfo() {
	var selrow = pickWorksInfoResult.dtpaginggrid("option",'selarrrow') + "";
	if (selrow != null && selrow.length > 0) {
		$.dtmessagebox.confirm("确定要删除吗？", "询问", function(msgBtn) {
			if (msgBtn == $.dtdialog.DR_OK) {
				$.post("busi/baseGreenHouseInfoAction!deleteBaseGreenHouseInfo.action",{'baseGreenHouseInfoCodeStr' : selrow}, function(data) {														
					if (data.flag) {
						$.dtmessagebox.alert("删除成功");
						baseGreenHouseInfoResult.dtpaginggrid("reload");
						baseGreenHouseInfoResult.dtpaginggrid("reload");
					} else {
						if (data.msg) {
							$.dtmessagebox.alert(data.msg);
						} else {
							$.dtmessagebox.alert("删除失败");
						}
					}
				});
			}
		});
	} else {
		$.dtmessagebox.alert("请选择要删除的数据");
	}
}

/**
 * 查看
 */
function detailGreenHouse(id){
/*	$.dtdialog.showModal({
		title : '温室详情',
		url : 'busi/baseGreenHouseInfoAction!viewBaseGreenHouseInfo.action?baseGreenHouseInfoId='+id,
		width : 1000,
		height : 800,
		buttons : {},
		resizable : false
		});*/
	//window.location.href="baseGreenHouseInfoAction!viewBaseGreenHouseInfo.action?baseGreenHouseInfoId="+id;
	window.open('busi/baseGreenHouseInfoAction!viewBaseGreenHouseInfo.action?baseGreenHouseInfoId='+id,'newwindow','height=600px,width=900px,top=100px,left=150px,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no,toolbar=no');	

}

function toUpdateGreenHouse(id){
	/*$.dtdialog.showModal({
		title : '修改温室',
		url : 'busi/baseGreenHouseInfoAction!editBaseGreenHouseInfo.action?baseGreenHouseInfoId='+id,
		width : 1000,
		height : 800,
		buttons : {},
		resizable : false
		});*/
	//window.location.href="baseGreenHouseInfoAction!editBaseGreenHouseInfo.action?baseGreenHouseInfoId="+id;
	window.open('busi/baseGreenHouseInfoAction!editBaseGreenHouseInfo.action?baseGreenHouseInfoId='+id,'newwindow','height=600px,width=900px,top=100px,left=150px,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no,toolbar=no');	

}

function deleteOneGreenHouse(id){
	//window.location.href="pickWorksInfoAction!deleteProduceWorksInfo.action?produceWorksInfoCodeStr="+id;
	$.dtmessagebox.confirm("确定要删除吗？", "询问", function(msgBtn) {
		if (msgBtn == $.dtdialog.DR_OK) {
			$.post("busi/baseGreenHouseInfoAction!deleteBaseGreenHouseInfo.action",{'baseGreenHouseInfoCodeStr' : id}, function(data) {														
				if (data.flag) {
					$.dtmessagebox.alert("删除成功");
					baseGreenHouseInfoResult.dtpaginggrid("reload");
					baseGreenHouseInfoResult.dtpaginggrid("reload");
				} else {
					if (data.msg) {
						$.dtmessagebox.alert(data.msg);
					} else {
						$.dtmessagebox.alert("删除失败");
					}
				}
			});
		}
	});
}

function operFormatGreenHouse(cellvalue, options, rowObject){
	var id = rowObject.id;
	var mid = "<a style=\"text-decoration:underline\" href=\"javascript:void(0);\" onclick=\"javascript:detailGreenHouse('" + id + "');\">温室查看</a>&nbsp;&nbsp;"
		    + "<a style=\"text-decoration:underline\" href=\"javascript:void(0);\" onclick=\"javascript:toUpdateGreenHouse('" + id + "');\">编辑</a>&nbsp;&nbsp;"
			+ "<a style=\"text-decoration:underline\" href=\"javascript:void(0);\" onclick=\"javascript:deleteOneGreenHouse('" + id + "');\">删除</a>&nbsp;&nbsp;";
	var start = "<div align = 'center'>";
	var end = "</div>";
	return start + mid + end;
}

/**
 * 选择功能树时触发事件，加载功能菜单对应的功能
 */
function onSelectFuncAuth() {
	var houseName = arguments[2].nodeTreeName; //给caption赋值
	baseGreenHouseInfoResult.dtpaginggrid('setCaption','查询结果：'+houseName);
//	alert(arguments[2].levelTree);
	if (arguments[2].levelTree == "1") {
//		alert("跳到温室");
		$("#greenHouse").fadeIn();
		$("#base").fadeOut();
		$("#baseId").val(arguments[2].nodeTreeId);
		baseGreenHouseInfoResult.dtpaginggrid("setGridParam",{url:'busi/baseGreenHouseInfoAction!getBaseGreenHouseInfoquery.action?'+$('#queryFormH').serialize()});
		baseGreenHouseInfoResult.dtpaginggrid("option","page", 1);
		baseGreenHouseInfoResult.dtpaginggrid("reload");
	}
	
	else{
		//alert("跳到基地");
		$("#greenHouse").fadeOut();
		$("#base").fadeIn();
		
	}

}
function onSelectFuncAuthadd() {
	funcInfoTree.dttree("refresh");
		if (arguments[2].isParent == false) {
			
			$("#baseId").val(arguments[2].nodeTreeId);
			//alert(arguments[2].nodeTreeId+","+arguments[2].parentId);
			//alert(arguments[1].nodeTreeId+","+arguments[1].parentId);
		
		}
}
