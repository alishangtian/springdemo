$(function() {
	$("#queryDomainBtn").click(function() {
		
		var equipNetTopology = {};
		equipNetTopology['parentId'] = $("#parentId").val();
		equipNetTopologyResult.dtpaginggrid("option","postData", equipNetTopology);
		equipNetTopologyResult.dtpaginggrid("option","mtype", "POST");
		equipNetTopologyResult.dtpaginggrid("option","url", "equipNetTopologyAction!getENTopology.action");
		equipNetTopologyResult.dtpaginggrid("option","page", 1);
		equipNetTopologyResult.dtpaginggrid("reload");
	});

	$("#addEquipNetTopologyBtn").click(function() {
		
		if($("#parentId").val()==null||$("#parentId").val()==""){
			alert("请选择位置");
		}else{
			var pid= $("#parentId").val();
			window.location.href="equipNetTopologyAction!addEquipNetTopology.action?parentId="+pid;
		}
		
	});
	
	$("#saveEquipNetTopologyBtn").click(function() {

	$("#addEquipNetTopologyForm").submit();
	
	});
	
	$("#delEquipNetTopologyBtn").click(function() {

		deleteDomain();
		
		});
	
	$("#cancelBtn").click(function() {
		window.location.href="produceWorksInfoManager.action";
	});
	
	$("#resetProduceWorksInfoBtn").click(function() {
		$("#queryProduceSeasonId").val("");
		$("#queryStartTime").val("");
		$("#queryEndTime").val("");
	});
	
});

/**
 * 删除值域
 */
function deleteDomain() {
	
	var selrow = equipNetTopologyResult.dtpaginggrid("option",'selarrrow') + "";
	if (selrow != null && selrow.length > 0) {
		$.dtmessagebox.confirm("确定要删除吗？", "询问", function(msgBtn) {
			if (msgBtn == $.dtdialog.DR_OK) {
				alert("delete");
				$.post("equipNetTopologyAction!deleteEquipNetTopology.action",{'equipNetTopologyCodeStr' : selrow}, function(data) {														
					if (data.flag) {
						$.dtmessagebox.alert("删除成功");
						equipNetTopologyResult.dtpaginggrid("reload");
						equipNetTopologyResult.dtpaginggrid("reload");
						window.location.reload();
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
function detail(id){
	window.location.href="produceWorksInfoManager!viewProduceWorksInfo.action?produceWorksInfoId="+id;
}

function toUpdate(id){
	window.location.href="produceWorksInfoManager!editProduceWorksInfo.action?produceWorksInfoId="+id;
}

function deleteOne(id){
	//window.location.href="produceWorksInfoManager!deleteProduceWorksInfo.action?produceWorksInfoCodeStr="+id;
	$.dtmessagebox.confirm("确定要删除吗？", "询问", function(msgBtn) {
		if (msgBtn == $.dtdialog.DR_OK) {
			$.post("produceWorksInfoManager!deleteProduceWorksInfo.action",{'produceWorksInfoCodeStr' : id}, function(data) {														
				if (data.flag) {
					$.dtmessagebox.alert("删除成功");
					produceWorksInfoResult.dtpaginggrid("reload");
					produceWorksInfoResult.dtpaginggrid("reload");
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

function operFormat(cellvalue, options, rowObject){
	var id = rowObject.id;
	var mid = "<a style=\"text-decoration:underline\" href=\"javascript:void(0);\" onclick=\"javascript:detail('" + id + "');\">查看</a>&nbsp;&nbsp;"
		    + "<a style=\"text-decoration:underline\" href=\"javascript:void(0);\" onclick=\"javascript:toUpdate('" + id + "');\">编辑</a>&nbsp;&nbsp;"
			+ "<a style=\"text-decoration:underline\" href=\"javascript:void(0);\" onclick=\"javascript:deleteOne('" + id + "');\">删除</a>&nbsp;&nbsp;";
	var start = "<div align = 'center'>";
	var end = "</div>";
	return start + mid + end;
}

/**
 * 选择功能树时触发事件，加载功能菜单对应的功能
 */
function onSelectFuncAuth() {
	//alert($("#parentId"));
	//if (arguments[2].isParent == false) {
	
		$("#parentId").val(arguments[2].id);
		alert($("#parentId").val());
		
		//alert(arguments[2].id+","+arguments[2].parentId);
	//}
}

