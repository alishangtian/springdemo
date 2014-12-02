var flagGreenHouse="";

$(function() {
	$("#queryDomainBtn").click(function() {
			produceWorksInfoResult.dtpaginggrid("setGridParam",{url:'busi/produceWorksInfoManager!getProdWorksInfo.action?'+$('#queryForm').serialize()});
			produceWorksInfoResult.dtpaginggrid("option","page", 1);
			produceWorksInfoResult.dtpaginggrid("reload");
	});

	$("#addProduceWorksInfoBtn").click(function() {
		var houseID = $("#houseId").val();
		if($("#houseId").val()==""){
			alert("请选择温室");
		}else{
			window.open('busi/produceWorksInfoManager!addProduceWorksInfo.action?prdcSeason.houseId='+$("#houseId").val(),'newwindow','height=300px,width=800px,top=200px,left=250px,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no,toolbar=no');	
		}
		
	});
	$("#saveProduceWorksInfoBtn").click(function() {		
		$("#addProduceWorksInfoForm").submit();	
		
		alert("添加成功");
		
		window.opener.produceWorksInfoResult.dtpaginggrid("reload");
		window.opener.produceWorksInfoResult.dtpaginggrid("reload");
		window.close();
		
	});
	
	$("#delProduceWorksInfoBtn").click(function() {

		deleteDomain();
		
		});
	
	$("#cancelBtn").click(function() {
		window.close();
	});
	
	$("#resetProduceWorksInfoBtn").click(function() {
		seasonSelect.dtcombobox('refresh',{param:{}});
		$("#queryStartTime").val("");
		$("#queryEndTime").val("");
	});
	
});

/**
 * 删除值域
 */
function deleteDomain() {
	var selrow = produceWorksInfoResult.dtpaginggrid("option",'selarrrow') + "";
	if (selrow != null && selrow.length > 0) {
		$.dtmessagebox.confirm("确定要删除吗？", "询问", function(msgBtn) {
			if (msgBtn == $.dtdialog.DR_OK) {
				$.post("busi/produceWorksInfoManager!deleteProduceWorksInfo.action",{'produceWorksInfoCodeStr' : selrow}, function(data) {														
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
	} else {
		$.dtmessagebox.alert("请选择要删除的数据");
	}
}

/**
 * 查看
 */
function detail(id){
//	window.location.href="busi/produceWorksInfoManager!viewProduceWorksInfo.action?produceWorksInfoId="+id;
	window.open('busi/produceWorksInfoManager!viewProduceWorksInfo.action?produceWorksInfoId='+id,'newwindow','height=300px,width=800px,top=200px,left=250px,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no,toolbar=no');	
}

function toUpdate(id){
//	window.location.href="busi/produceWorksInfoManager!editProduceWorksInfo.action?produceWorksInfoId="+id;
	window.open('busi/produceWorksInfoManager!editProduceWorksInfo.action?produceWorksInfoId='+id,'newwindow','height=300px,width=800px,top=200px,left=250px,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no,toolbar=no');	
}

function deleteOne(id){
	//window.location.href="produceWorksInfoManager!deleteProduceWorksInfo.action?produceWorksInfoCodeStr="+id;
	$.dtmessagebox.confirm("确定要删除吗？", "询问", function(msgBtn) {
		if (msgBtn == $.dtdialog.DR_OK) {
			$.post("busi/produceWorksInfoManager!deleteProduceWorksInfo.action",{'produceWorksInfoCodeStr' : id}, function(data) {														
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
function clickOper(e,treeId,node,nodeId) {
	var houseName = node.name; //给caption赋值
	produceWorksInfoResult.dtpaginggrid('setCaption','查询结果：'+houseName);
	if (!node.custId) {
		var oldHouseId = $("#houseId").val();
		if(oldHouseId == node.id) return false; 
		$("#houseId").val(node.id);
		seasonSelect.dtcombobox('setRefreshUrl','busi/produceWorksInfoManager!getPrdcSeasonByGreenHouse.action?id='+node.id);
		seasonSelect.dtcombobox('refresh',{param:{}});
		ab();
		
	}
	
	
}

 
 function ab(){	 	
	 	produceWorksInfoResult.dtpaginggrid("setGridParam",{url:'busi/produceWorksInfoManager!getProdWorksInfo.action?'+$('#queryForm').serialize()});
		produceWorksInfoResult.dtpaginggrid("option","page", 1);
		produceWorksInfoResult.dtpaginggrid("reload");
 }