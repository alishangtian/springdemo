$(function() {
	//查询
	$("#queryDomainBtn").click(function() {
		prdcConsAccResult.dtpaginggrid("setGridParam",{url:'busi/prdcConAcciAction!selectPrdcConsAccInfo.action?'+$('#queryForm').serialize()});
		prdcConsAccResult.dtpaginggrid("option","page", 1);
		prdcConsAccResult.dtpaginggrid("reload");
	});
	//重置
	$("#resetPcaInfoBtn").click(function(){
		$("#seasonSelect").val("");
		$("#accidentsDate").val("");
	});
	//新增
	$("#addPcaInfoBtn").click(function() {
		    
			if($("#houseId").val()==""){
				alert("请选择温室");
			}else{
				var houseName = $("#houseName").val();
				var houseId = $("#houseId").val();
				alert(houseName+houseId);
				window.open('busi/prdcConAcciAction!createPrdcConsAccInfo.action?houseId='+houseId,'newwindow','height=300,width=800,top=200,left=250,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no,toolbar=no');	
			}
	});
	
});
function operFormat(cellvalue, options, rowObject){
	var id = rowObject.id;
	var mid = "<a style=\"text-decoration:underline\" href=\"javascript:void(0);\" onclick=\"javascript:detail('" + id + "');\">查看</a>&nbsp;&nbsp;"
		    + "<a style=\"text-decoration:underline\" href=\"javascript:void(0);\" onclick=\"javascript:toUpdate('" + id + "');\">编辑</a>&nbsp;&nbsp;"
		    + "<a style=\"text-decoration:underline\" href=\"javascript:void(0);\" onclick=\"javascript:toUpdateInfo('" + id + "');\">处理</a>&nbsp;&nbsp;"
			+ "<a style=\"text-decoration:underline\" href=\"javascript:void(0);\" onclick=\"javascript:deleteOne('" + id + "');\">删除</a>&nbsp;&nbsp;";
	var start = "<div align = 'center'>";
	var end = "</div>";
	return start + mid + end;
}

/**
 * 选择功能树时触发事件，加载功能菜单对应的功能
 */
function clickOper(e,treeId,node,nodeId) {
	if (arguments[2].levelTree == "2") {
		$("#houseId").val(arguments[2].nodeTreeId);
		$("#houseName").val(arguments[2].nodeTreeName);
	}
}