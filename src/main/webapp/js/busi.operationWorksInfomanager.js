var flagGreenHouse="";
$(function() {

	$("#queryDomainBtn").click(function() {
		operationWorksInfoResult.dtpaginggrid("setGridParam",{url:'busi/operationWorksInfoManager!getOptnWorksInfo.action?'+$('#queryForm').serialize()});
		operationWorksInfoResult.dtpaginggrid("option","page", 1);
		operationWorksInfoResult.dtpaginggrid("reload");
	});

	$("#addOperationWorksInfoBtn").click(function() {
		
		if($("#queryHouseId").val()==""||flagGreenHouse==null||flagGreenHouse==""){
			alert("请选择温室");
		}else{
		window.open('busi/operationWorksInfoManager!addOperationWorksInfo.action?prdcSeason.houseId='+$("#houseId").val(),'newwindow','height=300px,width=800px,top=200px,left=250px,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no,toolbar=no');	

		}
		//window.location.href="operationWorksInfoManager!addOperationWorksInfo.action";
	});
	
	$("#saveOperationWorksInfoBtn").click(function() {

		
		$("#addOperationWorksInfoForm").submit();	
		
		alert("添加成功");
		
		window.opener.operationWorksInfoResult.dtpaginggrid("reload");
		window.opener.operationWorksInfoResult.dtpaginggrid("reload");
		window.close();
	});
	
	$("#delOperationWorksInfoBtn").click(function() {

		deleteDomain();
		
		});
	
	$("#cancelBtn").click(function() {
		window.close();
	});
	
	$("#resetOperationWorksInfoBtn").click(function() {
		seasonSelect.dtcombobox('refresh',{param:{}});
		$("#queryStartTime").val("");
		$("#queryEndTime").val("");
		//$("#queryDomainName").val("");
	});
	
});

/**
 * 删除值域
 */
function deleteDomain() {
	var selrow = operationWorksInfoResult.dtpaginggrid("option",'selarrrow') + "";
	if (selrow != null && selrow.length > 0) {
		$.dtmessagebox.confirm("确定要删除吗？", "询问", function(msgBtn) {
			if (msgBtn == $.dtdialog.DR_OK) {
				$.post("busi/operationWorksInfoManager!deleteOperationWorksInfo.action",{'operationWorksInfoCodeStr' : selrow}, function(data) {														
					if (data.flag) {
						$.dtmessagebox.alert("删除成功");
						operationWorksInfoResult.dtpaginggrid("reload");
						operationWorksInfoResult.dtpaginggrid("reload");
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
//	window.location.href="busi/operationWorksInfoManager!viewOperationWorksInfo.action?operationWorksInfoId="+id;
	window.open('busi/operationWorksInfoManager!viewOperationWorksInfo.action?operationWorksInfoId='+id,'newwindow','height=300px,width=800px,top=200px,left=250px,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no,toolbar=no');	

}

function toUpdate(id){
//	window.location.href="busi/operationWorksInfoManager!editOperationWorksInfo.action?operationWorksInfoId="+id;
	window.open('busi/operationWorksInfoManager!editOperationWorksInfo.action?operationWorksInfoId='+id,'newwindow','height=300px,width=800px,top=200px,left=250px,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no,toolbar=no');	

}

function deleteOne(id){
	//window.location.href="operationWorksInfoManager!deleteOperationWorksInfo.action?operationWorksInfoCodeStr="+id;
	$.dtmessagebox.confirm("确定要删除吗？", "询问", function(msgBtn) {
		if (msgBtn == $.dtdialog.DR_OK) {
			$.post("busi/operationWorksInfoManager!deleteOperationWorksInfo.action",{'operationWorksInfoCodeStr' : id}, function(data) {														
				if (data.flag) {
					$.dtmessagebox.alert("删除成功");
					operationWorksInfoResult.dtpaginggrid("reload");
					operationWorksInfoResult.dtpaginggrid("reload");
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

	
	flagGreenHouse=arguments[2].parentId;
	var houseName = arguments[2].nodeTreeName; //给caption赋值
	operationWorksInfoResult.dtpaginggrid('setCaption','查询结果：'+houseName);
	if (arguments[2].isParent == false) {
		$("#houseId").val(arguments[2].nodeTreeId);
		
		seasonSelect.dtcombobox('setRefreshUrl','busi/operationWorksInfoManager!getPrdcSeasonByGreenHouse.action?id='+arguments[2].nodeTreeId);
		seasonSelect.dtcombobox('refresh',{param:{}});
		
		operationWorksInfoResult.dtpaginggrid("setGridParam",{url:'busi/operationWorksInfoManager!getOptnWorksInfo.action?'+$('#queryForm').serialize()});
		operationWorksInfoResult.dtpaginggrid("option","page", 1);
		operationWorksInfoResult.dtpaginggrid("reload");
		
//		alert(arguments[2].nodeTreeId+","+arguments[2].parentId);
//		$.post("busi/operationWorksInfoManager!getPrdcSeasonByGreenHouse.action",
//				{"GreenHouseId":arguments[2].nodeTreeId},function(json){
//					$("#aaa").remove();
//					var jq = jQuery.parseJSON( json.abc );
//					var sel=$("<select id='aaa' name='operationWorksInfo.produceSeasonId'>");
//					$("#listDIV").append(sel);
//					for(var i=0;i<jq.length;i++){
//						sel.append($("<option>").html(jq[i].name).val(jq[i].id));
//					}
//				
//				});
		
	}
}