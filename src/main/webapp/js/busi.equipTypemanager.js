$(function() {
	initGrid();
	$("#addEquipType").click(function() {
		var level = $("#tl").val();
		if (!level) {
			$.dtmessagebox.alert("请选择父类型");
		} else if (level == 0 || level == 1) {
			$.dtdialog.showModal({
				title : '新增设备类型',
				url : 'equipInfoAction!toAddEquipTypePage.action',
				width : 500,
				height : 300,
				buttons : {},
				resizable : false
			});
		}
	});
});
function onSelectFuncAuth() {
	var level = arguments[2].level;
	if (level == 0 || level == 1) {
		$("#addEquipType").show();
		var pId = arguments[2].nodeTreeId;
		$("#tl").val(level);
		$("#tpd").val(pId);
		var url = "equipInfoAction!loadEquipTypes.action";
		var equipTypes = {};
		equipTypes['equipType.level'] = level;
		equipTypes['equipType.parentId'] = pId;
		equipTypeResult.dtpaginggrid("option", "postData", equipTypes);
		equipTypeResult.dtpaginggrid("option", "mtype", "POST");
		equipTypeResult.dtpaginggrid("option", "url", url);
		equipTypeResult.dtpaginggrid("option", "page", 1);
		equipTypeResult.dtpaginggrid("reload");
	} else {
		$("#addEquipType").hide();
	}
}
function operFormat(cellvalue, options, rowObject) {/*
	var id = rowObject.id;
	var mid = "<a style=\"text-decoration:underline\" href=\"javascript:void(0);\" onclick=\"javascript:deleteOne('"
			+ id + "');\">删除</a>&nbsp;&nbsp;";
	var start = "<div align = 'center'>";
	var end = "</div>";
	return start + mid + end;
*/
	return "暂无";
}
function initGrid() {
	var url = "equipInfoAction!loadEquipTypes.action";
	var equipTypes = {};
	equipTypes['equipType.level'] = 0;
	equipTypes['equipType.parentId'] = -1;
	equipTypeResult.dtpaginggrid("option", "postData", equipTypes);
	equipTypeResult.dtpaginggrid("option", "mtype", "POST");
	equipTypeResult.dtpaginggrid("option", "url", url);
	equipTypeResult.dtpaginggrid("option", "page", 1);
	equipTypeResult.dtpaginggrid("reload");
}
function deleteOne(id) {
}
