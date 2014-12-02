$(function() {
	$("#queryBtn").click(function() {
		initGrid();
	});
	$("#resetBtn").click(function() {
		clearForm();
	});
});
function clearForm(){
	$("#startTime").val("");
	$("#endTime").val("");
	$("#detail").val("");
}
function onSelectFuncAuth() {
	if (arguments[2].isParent == false) {
		$("#houseId").val(arguments[2].nodeTreeId);
		initGrid();
	}
}
function initGrid() {
	produceAccidents.dtpaginggrid("option", "url",
			"busi/prdcSeasonManager!queryProduceAccidents.action?"
					+ $('#queryForm').serialize());
	produceAccidents.dtpaginggrid("option", "page", 1);
	produceAccidents.dtpaginggrid("reload");
}

function operFormat(cellvalue, options, rowObject) {
	var id = rowObject.id;
	var mid = "<a style=\"text-decoration:underline\" href=\"javascript:void(0);\" onclick=\"javascript:detail('"
			+ id
			+ "');\">查看</a>&nbsp;&nbsp;"
			+ "<a style=\"text-decoration:underline\" href=\"javascript:void(0);\" onclick=\"javascript:toUpdate('"
			+ id
			+ "');\">编辑</a>&nbsp;&nbsp;"
			+ "<a style=\"text-decoration:underline\" href=\"javascript:void(0);\" onclick=\"javascript:deleteOne('"
			+ id + "');\">删除</a>&nbsp;&nbsp;";
	var start = "<div align = 'center'>";
	var end = "</div>";
	return start + mid + end;
}
