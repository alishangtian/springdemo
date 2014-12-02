var flagGreenHouse="";

var  gridResult = function(){
	pickWorksInfoResult.dtpaginggrid("setGridParam",{url:'busi/pickWorksInfoAction!getPicksWorksInfo.action?'+$('#queryForm').serialize()});
	pickWorksInfoResult.dtpaginggrid("option","page", 1);
	pickWorksInfoResult.dtpaginggrid("reload");
}

$(function() {
	$("#queryDomainBtn").click(function() {
		
		gridResult();
		
	});

	$("#addPickWorksInfoBtn").click(function() {
		
		if($("#queryHouseId").val()==""||flagGreenHouse==null||flagGreenHouse==""){
			alert("请选择温室");
			return;
		}else{
			window.open('busi/pickWorksInfoAction!addPickWorksInfo.action?prdcSeason.houseId='+$("#houseId").val(),'newwindow','height=500px,width=1000px,top=100px,left=150px,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no,toolbar=no');
		}
		 
		
	});
	
	
//	$("#aaa").change(function(){
//		alert("111111111111111");
//	});
	$("#savePickWorksInfoBtn").click(function() {
		
		

		$("#addPickWorksInfoForm").submit();	
		
		alert("添加成功");
		
		window.opener.pickWorksInfoResult.dtpaginggrid("reload");
		window.opener.pickWorksInfoResult.dtpaginggrid("reload");
		window.close();
		
		
		
	
	});
	
	$("#delPickWorksInfoBtn").click(function() {
	
		deleteDomain();
		
		});
	
	$("#cancelBtn").click(function() {
		window.close();
	});
	
	$("#resetPickWorksInfoBtn").click(function() {
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
	var selrow = pickWorksInfoResult.dtpaginggrid("option",'selarrrow') + "";
	
	if (selrow != null && selrow.length > 0) {
		$.dtmessagebox.confirm("确定要删除吗？", "询问", function(msgBtn) {
			if (msgBtn == $.dtdialog.DR_OK) {
				
				$.post("busi/pickWorksInfoAction!deletePickWorksInfo.action",{'pickWorksInfoCodeStr' : selrow}, function(data) {														
					if (data.flag) {
						$.dtmessagebox.alert("删除成功");
						pickWorksInfoResult.dtpaginggrid("reload");
						pickWorksInfoResult.dtpaginggrid("reload");
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
//	window.location.href="busi/pickWorksInfoAction!viewPickWorksInfo.action?pickWorksInfoId="+id;
	window.open('busi/pickWorksInfoAction!viewPickWorksInfo.action?pickWorksInfoId='+id,'newwindow','height=300px,width=800px,top=200px,left=250px,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no,toolbar=no');		

}

function toUpdate(id){
//	window.location.href="busi/pickWorksInfoAction!editPickWorksInfo.action?pickWorksInfoId="+id;
	window.open('busi/pickWorksInfoAction!editPickWorksInfo.action?pickWorksInfoId='+id,'newwindow','height=300px,width=800px,top=200px,left=250px,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no,toolbar=no');		

}

function deleteOne(id){
	
	$.dtmessagebox.confirm("确定要删除吗？", "询问", function(msgBtn) {
		if (msgBtn == $.dtdialog.DR_OK) {
			$.post("busi/pickWorksInfoAction!deletePickWorksInfo.action",{'pickWorksInfoCodeStr' : id}, function(data) {														
				if (data.flag) {
					$.dtmessagebox.alert("删除成功");
					pickWorksInfoResult.dtpaginggrid("reload");
					pickWorksInfoResult.dtpaginggrid("reload");
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
	var houseName = arguments[2].nodeTreeName; //给caption赋值
	pickWorksInfoResult.dtpaginggrid('setCaption','查询结果：'+houseName);

	flagGreenHouse=arguments[2].parentId;
	if (arguments[2].isParent == false) {
		
		$("#houseId").val(arguments[2].nodeTreeId);
		
		seasonSelect.dtcombobox('setRefreshUrl','busi/pickWorksInfoAction!getPrdcSeasonByGreenHouse.action?id='+arguments[2].nodeTreeId);
		seasonSelect.dtcombobox('refresh',{param:{}});
		
		pickWorksInfoResult.dtpaginggrid("setGridParam",{url:'busi/pickWorksInfoAction!getPicksWorksInfo.action?'+$('#queryForm').serialize()});
		pickWorksInfoResult.dtpaginggrid("option","page", 1);
		pickWorksInfoResult.dtpaginggrid("reload");
		
//		alert(arguments[2].nodeTreeId+","+arguments[2].parentId);
//		$.post("busi/pickWorksInfoAction!getPrdcSeasonByGreenHouse.action",
//				{"GreenHouseId":arguments[2].nodeTreeId},function(json){
//					$("#aaa").remove();
//					var jq = jQuery.parseJSON( json.abc );
//					var sel=$("<select id='aaa' name='pickWorksInfo.produceSeasonId'>");
//					$("#listDIV").append(sel);
//					for(var i=0;i<jq.length;i++){
//						sel.append($("<option>").html(jq[i].name).val(jq[i].id));
//					}
//				
//				});
		
	}
}


